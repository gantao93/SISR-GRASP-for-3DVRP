package routesFinding.initSolution;

import app.GraspBinPacker;
import core.base.container.SKU;
import core.base.container.Vehicle;
import core.base.item.ItemType;
import core.report.LPPackageEvent;
import report.Report;
import routesFinding.cluster.KmeansCluster;
import routesFinding.dataImporter.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GreedyKmeansInitSolution {
    private Vehicle vehicle;
    private String depot;
    private List<String> customer;
    private int[][] distanceMatrix;
    private int[][] timeMatrix;
    private Map<String,List<SKU>> customerItemsMapping;
    private Map<String, Location> customerLocations;
    private List<ItemType> itemsCompatibleInfo;

    public GreedyKmeansInitSolution(Vehicle vehicle,
                                    String depot,
                                    List<String> customer,
                                    int[][] distanceMatrix,
                                    int[][] timeMatrix,
                                    Map<String,List<SKU>> customerItemsMapping,
                                    Map<String, Location> customerLocations,
                                    List<ItemType> itemsCompatibleInfo) {
        this.vehicle = vehicle;
        this.depot = depot;
        this.customer = customer;
        this.distanceMatrix = distanceMatrix;
        this.timeMatrix = timeMatrix;
        this.customerItemsMapping = customerItemsMapping;
        this.customerLocations = customerLocations;
        this.itemsCompatibleInfo = itemsCompatibleInfo;

        this.customerLocations.remove(depot);
    }

    /*
    计算当前节点的最近的邻居节点
     */
    public String findNearestNeighbor(String currentNode, List<String> remainingNodesList) {
        // find currentNode在remainingNodesList中的索引inx，查distanceMatrix第inx行中的最小值对应的值的索引inx2,inx2对应的
        // customer即为所求
        if(remainingNodesList.contains(currentNode)){
            remainingNodesList.remove(currentNode);
        }
        double minDistance = Double.POSITIVE_INFINITY;
        String nearNode = null;
        int curCusInx = customer.indexOf(currentNode);
        for (String cus:remainingNodesList){
            int cusInx = customer.indexOf(cus);
            double distance = distanceMatrix[curCusInx][cusInx];
            if(distance<minDistance){
                minDistance = distance;
                nearNode = cus;
            }
        }
        return nearNode;
    }

    /*
    List<LPPackageEvent> unPackedItems 转化为List<SKU> unPackedItems
     */
    public List<SKU> lPPackageEventToSKUClass(List<LPPackageEvent> items){
        if(items.size()==0 || items == null){
            return new ArrayList<>();
        }
        List<SKU> itemsList = new ArrayList<>();
        for(LPPackageEvent lpItem:items){
            SKU sku = new SKU();
            sku.setWidth(lpItem.w());
            sku.setLength(lpItem.l());
            sku.setHeight(lpItem.h());
            sku.setX(lpItem.x());
            sku.setY(lpItem.y());
            sku.setZ(lpItem.z());
            itemsList.add(sku);
        }
        return itemsList;
    }
    /*
    合并两个物品列表
     */
    public List<SKU> combineVisitedNodesItems(List<SKU> visitedNodesItems, List<SKU> nearestNodesItems){
        if(visitedNodesItems.size()==0){
            return nearestNodesItems;
        }
        List<SKU> combinedSkuList = Stream.of(visitedNodesItems, nearestNodesItems)
                .filter(list -> list != null)
                .flatMap(List::stream)
                .collect(Collectors.toList());
        return combinedSkuList;
    }


    public void generateRoutes(int clusterNum){
        List<List<String>> tours = new ArrayList<>();
        List<Report> bppResults = new ArrayList<>();
        KmeansCluster kMeans = new KmeansCluster(customerLocations,clusterNum);
        kMeans.runKMeans(100);
        List<List<String>> clusters = kMeans.getClusters();
        for(List<String> cluster: clusters){
            List<String> customersList = cluster.stream().collect(Collectors.toList());

            int routeNum = 0;
            while(customersList.size() != 0) {
                List<String> route = new ArrayList<>();
                double totalDistance = 0.00;
                int totalDuration = 0;
                String curNode = depot;
                List<SKU> oneRouteCumulativeItemslist = new ArrayList<>();
                List<SKU> unloadedItems = new ArrayList<>();
                Report lastReport = null;
                int i = 0;
                while(unloadedItems.isEmpty() && customersList.size() != 0) {
                    String nearNode = findNearestNeighbor(curNode, customersList);
                    List<SKU> nearNodeItems = customerItemsMapping.get(nearNode);
                    // 添加物品节点顺序属性
                    for (SKU sku : nearNodeItems) {
                        sku.setGroup(i);
                    }
                    oneRouteCumulativeItemslist = combineVisitedNodesItems(oneRouteCumulativeItemslist, nearNodeItems);
                    // 执行grasp装箱
                    System.out.printf("%s route ,%s run grasp",routeNum, i);

                    Report report = new GraspBinPacker(vehicle, oneRouteCumulativeItemslist, itemsCompatibleInfo).run();
                    // container信息保存到report中
                    List<LPPackageEvent> unPackedItems = report.getUnpackedItems();
                    // LPPackageEvent -> SKU
                    unloadedItems = lPPackageEventToSKUClass(unPackedItems);

                    if(!unPackedItems.isEmpty()){
                        if(i==0){
                            System.out.printf("current node %s unloaded ",nearNode);
                        } else{
                            break;
                        }
                    }

                    // 更新route, distance, arrivialtime
                    route.add(nearNode);
                    // curNode和nearNode在距离矩阵中的索引
                    int curNodeInx = customer.indexOf(curNode);
                    int nextNodeInx = customer.indexOf(nearNode);
                    totalDistance += distanceMatrix[curNodeInx][nextNodeInx];
                    totalDuration += timeMatrix[curNodeInx][nextNodeInx];
                    // 更新当前节点
                    curNode = nearNode;
                    // 更新剩下的节点，删除已串点的node
                    customersList.remove(nearNode);
                    lastReport = report;
                    i += 1;
                }

                tours.add(route);
                routeNum += 1;
                bppResults.add(lastReport);

            }
        }

        // 打印测试
        for(int i=0; i<tours.size(); i++) {
            System.out.println(String.format("route %s route %s", i+1, tours.get(i)));
            System.out.printf("loading rate: %s",bppResults.get(i).getLoadingRate());
            System.out.println("\n");
        }

    }

}
