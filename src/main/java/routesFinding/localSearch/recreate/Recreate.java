package routesFinding.localSearch.recreate;

import java.math.BigDecimal;
import java.util.*;

import app.GraspBinPacker;
import core.base.container.SKU;
import core.base.container.Vehicle;
import core.base.item.ItemType;
import core.report.LPPackageEvent;
import report.Report;
import static routesFinding.calculate.RoutesDistance.getRouteDistance;


public class Recreate {
    public static List<List<String>> recreate(Vehicle vehicle,
                                              Map<String,List<SKU>> customerItemsMapping,
                                              List<ItemType> itemsCompatibleInfo,
                                              String depot,
                                              List<String> customerList,
                                              int[][] distanceMatrix,
                                              List<List<String>> absentRoute,
                                              List<String> absents,
                                              int lastLength) {
        // TODO sort(A) by total volume
        Collections.shuffle(absents);

        List<String> newAbsents = new ArrayList<>();
        List<Integer> toKeep = new ArrayList<>();
        List<List<String>> currentRoute = absentRoute;

        for (int i = 0 ; i < absents.size() ; i++) {
            String c = absents.get(i);
            List<double[]> probablePosition = new ArrayList<>();
            for (int ir = 0 ; ir < absentRoute.size() ; ir++) {
                List<String> r = absentRoute.get(ir);
                ArrayList<double[]> valids = getValid(depot, customerList, distanceMatrix, r, c);
                for (double[] v : valids) {
                    probablePosition.add(new double[]{ir,v[0],v[1]});
                }
            }

            double[] adding_pos = new double[]{-1,-1,-1};
            if (probablePosition.size()>0) {
                Collections.sort(probablePosition, new Comparator<double[]>() {
                    public int compare(double[] content0, double[] content1) {
                        if (content0[2]<content1[2]) {
                            return -1;
                        } else if (content0[2]>content1[2]) {
                            return 1;
                        }
                        return 0;
                    }
                });
                // 验证有效性
                // (int) Math.floor(probablePosition.size()/6)-1
                System.out.println("probablePosition size = "+ probablePosition.size());

                for(double [] routeInxInsertPosition: probablePosition.subList(0,10)){
                    List<String> route = absentRoute.get((int) routeInxInsertPosition[0]);
                    int insertPos = (int) routeInxInsertPosition[1];
                    List<String> newRoute = insertNode(route,insertPos,c);

                    // 1、如果物品总体积超过车，continue
                    BigDecimal itemVolumes = new BigDecimal(0);
                    for(String node:newRoute){
                        List<SKU> skuList = customerItemsMapping.get(node);
                        for(SKU sku:skuList){
                            BigDecimal itemLength = BigDecimal.valueOf(sku.getLength());
                            BigDecimal itemWidth = BigDecimal.valueOf(sku.getWidth());
                            BigDecimal itemHeight = BigDecimal.valueOf(sku.getHeight());
                            BigDecimal itemNum = BigDecimal.valueOf(sku.getNumber());
                            BigDecimal itemVolume = itemLength.multiply(itemWidth).multiply(itemHeight).multiply(itemNum);
                            itemVolumes = itemVolumes.add(itemVolume);
                        }
                    }
                    BigDecimal maxLoadingRate = new BigDecimal(0.8);
                    if(itemVolumes.multiply(maxLoadingRate).compareTo(vehicle.getVolume()) >= 0){
                        continue;
                    }

                    // 2、底面积判断
                    List<SKU> allItems = new ArrayList<>();
                    for(String node:newRoute) {
                        List<SKU> skuList = customerItemsMapping.get(node);
                        allItems.addAll(skuList);
                    }
                    if(preCheckBinpacking(vehicle,allItems,itemsCompatibleInfo)){
                        continue;
                    }

                    // 验证route在位置insertPos插入c是否满足装箱
                    if (checkBinpacking(route,c,insertPos,customerItemsMapping,vehicle,itemsCompatibleInfo)){
                        adding_pos = routeInxInsertPosition;
                        break;
                    }
                }
            } else if (lastLength>0 && lastLength <= currentRoute.size()) {
                toKeep.add(i);
                continue;
            }

            currentRoute = updateRoute(currentRoute, c, adding_pos);
        }
        for (int i : toKeep) {
            newAbsents.add(absents.get(i));
        }
        absents.clear();
        for (String i : newAbsents) {
            absents.add(i);
        }
        return currentRoute;
    }

    private static ArrayList<double[]> getValid(String depot, List<String> customers, int[][] distanceMatrix, List<String> r, String c) {
        ArrayList<double[]> valids = new ArrayList<>();
        double dist = getRouteDistance(depot, customers, r, distanceMatrix);
        double tmp_time = 0;
        String curr_node = depot;
        for (int i = 0 ; i<(r.size()+1) ; i++) {
            String next_node = i==r.size()?depot:r.get(i);

            // todo customerTimeMapping = {customer: {"startTime":,"endTime":, "serverTime":}}
//            tmp_time = Math.max(tmp_time, customerTimeMapping.get(curr_node).get("startTime")); // 开始到达时间
//            tmp_time += customerTimeMapping.get(curr_node).get("serverTime"); //加上服务时间
//            // 满足时间窗约束
//            // curr_node和C在时间矩阵中的索引
//            int curNodeInx = customers.indexOf(curr_node);
//            int cNodeInx = customers.indexOf(c);
//            if (tmp_time + timeMatrix[curNodeInx][cNodeInx] > customerTimeMapping.get(curr_node).get("endTime")) {
//                break;
//            }

//            tmp_time = Math.max(tmp_time, data[curr_node][3]); // 开始到达时间
//            tmp_time += data[curr_node][5]; //加上服务时间
//            // 满足时间窗约束
//            if (tmp_time + distanceMatrix[curr_node][c] > data[c][4]) {
//                break;
//            }

            List<String> new_r = insertNode(r, i, c);
            if (checkValid(false)) {
                double new_dist = getRouteDistance(depot, customers, new_r, distanceMatrix);
                valids.add(new double[]{i, new_dist-dist});
            }
//            tmp_time += distanceMatrix[curr_node][next_node];
            curr_node = i==r.size()?depot:r.get(i);
        }
        return valids;
    }

    private static List<List<String>> updateRoute(List<List<String>> absentRoute, String c, double[] adding_pos) {
        if (adding_pos[0]==-1) {
            absentRoute.add(new ArrayList<>(Arrays.asList(c)));
            return absentRoute;
        }
        List<String> new_r = insertNode(absentRoute.get((int)adding_pos[0]), (int)adding_pos[1], c);
        absentRoute.set((int)adding_pos[0], new_r);
        return absentRoute;
    }

    /*
    三个参数：一个整数数组old_r，一个整数pos，以及一个整数c。该方法将整数c插入到数组old_r的指定位置pos，
    并返回一个新的数组new_r，其中包含更新后的内容
     */
    private static List<String> insertNode(List<String> old_r, int pos, String c) {
        List<String> new_r = new ArrayList<>();
        for (int i = 0 ; i<old_r.size()+1 ; i++) {
            if (i<pos) {
                new_r.add(i,old_r.get(i));
            } else if (i>pos) {
                new_r.add(i,old_r.get(i-1));
            } else {
                new_r.add(i,c);
            }
        }
        return new_r;
    }

    /*
    检查时间窗是否满足
     */
    private static boolean checkValid(boolean timeWindowIsConsider){
        if(!timeWindowIsConsider){
            return true;
        }
        return false;
    }
    private static boolean checkValid(double[][] data, int[][] distanceMatrix, int[] r, int c, boolean timeWindowIsConsider) {
        if(!timeWindowIsConsider){
            return true;
        }
        double time_current = 0;
        int curr_node = 0;
        for (int i = 0 ; i<(r.length+1) ; i++) {
            int next_node = i==r.length?0:r[i];
            // 计算到达下一个节点的时间
            time_current += distanceMatrix[curr_node][next_node];
            time_current = Math.max(data[next_node][3], time_current);
            if (time_current <= data[next_node][4]) {
                // 离开当前节点的时间
                time_current += data[next_node][5];
            } else {
                return false;
            }
            curr_node = i==r.length?0:r[i];
        }
        return true;
    }

    public static boolean checkBinpacking(List<String> route, String c, int insertPos, Map<String,List<SKU>> customerItemsMapping, Vehicle vehicle, List<ItemType> itemsCompatibleInfo){
        List<SKU> oneRouteItems = new ArrayList<>();
        List<String> newRoute = insertNode(route,insertPos,c);
        for(int p=0; p<newRoute.size(); p++){
            String node = newRoute.get(p);
            List<SKU> nodeItem = customerItemsMapping.get(node);
            // 添加物品节点顺序属性
            for (SKU sku : nodeItem) {
                sku.setGroup(p);
            }
            oneRouteItems.addAll(nodeItem);
        }
        // 装箱
        Report report = new GraspBinPacker(vehicle, oneRouteItems, itemsCompatibleInfo).run();
        List<LPPackageEvent> unPackedItems = report.getUnpackedItems();
        return unPackedItems.size()==0;
    }

    public static boolean preCheckBinpacking(Vehicle vehicle, List<SKU> skuList, List<ItemType> itemsCompatibleInfo){
        // 用底面积判断
        // 按照最大堆叠层数（最大堆叠层数为不超过车高度），计算所有物品的底面积和
        int itemsBottomArea = 0;
        for(SKU item:skuList){
            int num = item.getNumber();
            int height = item.getHeight();
            if(num*height >= vehicle.getHeight()){
                int bottomNum = (int) Math.floor(num*height/vehicle.getHeight());
                itemsBottomArea += bottomNum*item.getWidth()*item.getLength();
            } else {
                itemsBottomArea += item.getWidth()*item.getLength();
            }
        }
        int vehicleBottomArea = vehicle.getLength() * vehicle.getWidth();
        if(itemsBottomArea > vehicleBottomArea){
            return true;
        }
        return false;
    }

}
