package routesFinding.initSolution;

import app.GraspBinPacker;
import core.base.container.SKU;
import core.base.container.Vehicle;
import core.base.item.ItemType;
import core.base.item.Position;
import core.base.item.PositionType;
import core.report.LPPackageEvent;
import report.Report;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class GreedyInitSolution {
    private Vehicle vehicle;
    private List<String> orderList;
    private int[][] distanceMatrix;
    private int[][] timeMatrix;
    private Map<String,List<SKU>> orderItemsMapping;
    Map<String, String> orderCustomerMapping;
    private List<ItemType> itemsCompatibleInfo;

    public GreedyInitSolution(Vehicle vehicle,
                              List<String> orderList,
                              int[][] distanceMatrix,
                              int[][] timeMatrix,
                              Map<String,List<SKU>> orderItemsMapping,
                              Map<String, String> orderCustomerMapping,
                              List<ItemType> itemsCompatibleInfo) {
        this.vehicle = vehicle;
        this.orderList = orderList;
        this.distanceMatrix = distanceMatrix;
        this.timeMatrix = timeMatrix;
        this.orderItemsMapping = orderItemsMapping;
        this.orderCustomerMapping = orderCustomerMapping;
        this.itemsCompatibleInfo = itemsCompatibleInfo;
    }

    /*
    计算当前订单的最近的邻居节点
     */
    public String findNearestOrderNeighbor(String currentOrder, List<String> remainingOrderList) {
        if(remainingOrderList.contains(currentOrder)){
            remainingOrderList.remove(currentOrder);
        }
        double minDistance = Double.POSITIVE_INFINITY;
        String nearOrder = null;
        int curOrderInx = orderList.indexOf(currentOrder);
        for (String od:remainingOrderList){
            int cusInx = orderList.indexOf(od);
            double distance = distanceMatrix[curOrderInx][cusInx];
            if(distance<minDistance){
                minDistance = distance;
                nearOrder = od;
            }
        }
        return nearOrder;
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

    public List<List<String>> generateRoutes(){
        List<List<String>> tours = new ArrayList<>();
        List<Report> bppResults = new ArrayList<>();
        List<String> ordersList = orderList.stream().collect(Collectors.toList());
        String depotNode = "depot-dummyOrder";
        int totalDistance = 0;
        int totalDuration = 0;
        int routeNum=0;
        while (ordersList.size()!=0){
            List<String> orderRoute = new ArrayList<>();
            String curNode = depotNode;
            List<SKU> oneRouteCumulativeItemslist = new ArrayList<>();
            List<SKU> unloadedItems = new ArrayList<>();
            Report lastReport = null;
            int i = 0;
            while(unloadedItems.isEmpty() && ordersList.size() != 0) {
                String nearNode = findNearestOrderNeighbor(curNode, ordersList);
                List<SKU> nearNodeItems = orderItemsMapping.get(nearNode);
                System.out.println("\n");
                System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                List<List<Integer>> itemsize = new ArrayList<>();
                for(SKU sku:nearNodeItems){
                    itemsize.add(Arrays.asList(sku.getWidth(),sku.getLength(),sku.getHeight(),sku.getNumber()));
                }
                System.out.printf("curnode=%s, order=%s, items=%s\n",curNode,nearNode,itemsize);
                // 添加物品节点顺序属性
                for (SKU sku : nearNodeItems) {
                    sku.setGroup(i);
                }
                oneRouteCumulativeItemslist = combineVisitedNodesItems(oneRouteCumulativeItemslist, nearNodeItems);
                // 执行grasp装箱
                System.out.printf("%s route ,%s run grasp \n",routeNum, i);

                Report report = new GraspBinPacker(vehicle, oneRouteCumulativeItemslist, itemsCompatibleInfo).run();
                // container信息保存到report中
                List<LPPackageEvent> unPackedItems = report.getUnpackedItems();
                // LPPackageEvent -> SKU
                unloadedItems = lPPackageEventToSKUClass(unPackedItems);
                if(unloadedItems.size()>0){
                    System.out.println(unloadedItems.size());
                }

                if(!unPackedItems.isEmpty()){
                    if(i==0){
                        System.out.printf("current order %s unloaded in vehicle, need to split order ",nearNode);
                    } else{
                        // 打印已装物品和坐标
                        List<List<Integer>> itemSize = new ArrayList<>();
                        List<List<Integer>> position = new ArrayList<>();
                        List<LPPackageEvent> packedItem = report.getPackedItems();
                        for(LPPackageEvent lp:packedItem){
                            itemSize.add(Arrays.asList(lp.w(),lp.l(),lp.h()));
                            position.add(Arrays.asList(lp.x(),lp.y(),lp.z()));
                        }
                        System.out.println(itemSize);
                        System.out.println(position);
                        int curNodeInx = orderList.indexOf(curNode);
                        int dummyOrderInx = orderList.indexOf("depot-dummyOrder");
                        totalDistance += distanceMatrix[curNodeInx][dummyOrderInx];
                        totalDuration += timeMatrix[curNodeInx][dummyOrderInx];
                        break;
                    }
                }
                // 更新route, distance, arrivialtime
                orderRoute.add(nearNode);

                // 更新当前节点
                curNode = nearNode;
                // 更新剩下的节点，删除已串点的node
                ordersList.remove(nearNode);
                lastReport = report;
                i += 1;
            }
            List<String> dummyOrderRoute = new ArrayList<>();
            for(String node:orderRoute){
                dummyOrderRoute.add(node);
            }
            int head = 0;
            int tail = orderRoute.size()+1;
            dummyOrderRoute.add(head, depotNode);
            dummyOrderRoute.add(tail, depotNode);
            int oneRouteDistance = 0;
            int oneRouteDuration = 0;
            for(int t=0; t<dummyOrderRoute.size()-1; t++){
                int preIndex = orderList.indexOf(dummyOrderRoute.get(t));
                int postIndex = orderList.indexOf(dummyOrderRoute.get(t+1));
                oneRouteDistance += distanceMatrix[preIndex][postIndex];
                oneRouteDuration += timeMatrix[preIndex][postIndex];
            }
            totalDistance += oneRouteDistance;
            totalDuration += oneRouteDuration;

            tours.add(orderRoute);
            routeNum += 1;
            bppResults.add(lastReport);

        }
        // 订单到客户节点的转化
        List<List<String>> realTours = new ArrayList<>();
        for(List<String> tou:tours){
            System.out.println(tou);
            List<String> realTour = new ArrayList<>();
            for(int i=0;i<tou.size();i++){
                if(i == tou.size()-1 || !this.orderCustomerMapping.get(tou.get(i)).equals(this.orderCustomerMapping.get(tou.get(i+1)))){
                    realTour.add(this.orderCustomerMapping.get(tou.get(i)));
                }
            }
            realTours.add(realTour);
        }
        // 打印测试
        for(int i=0; i<tours.size(); i++) {
            System.out.println(String.format("route %s  %s", i+1, realTours.get(i)));
            System.out.printf("loading rate: %s",bppResults.get(i).getLoadingRate());
            System.out.println("\n");
        }
        System.out.println("total distance: "+ totalDistance);

        return tours;

    }

}
