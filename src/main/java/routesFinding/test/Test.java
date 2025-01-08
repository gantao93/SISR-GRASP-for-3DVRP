package routesFinding.test;

import app.GraspBinPacker;
import core.Config;
import core.base.container.SKU;
import core.base.container.Vehicle;
import core.base.item.ItemType;
import core.base.item.Position;
import core.base.item.PositionType;
import core.report.LPPackageEvent;
import core.report.LPReport;
import report.Report;
import routesFinding.dataImporter.*;
import routesFinding.dataImporter.processing.DistanceTimeData;
import routesFinding.dataImporter.processing.DistanceTimeMatrixByOrder;
import routesFinding.dataImporter.processing.StackingRule;
import routesFinding.dataImporter.processing.VehicleGenerator;
import routesFinding.dataImporter.processing.Aggregator;
import routesFinding.dataImporter.splitOrders.OrdersSplitted;
import routesFinding.initSolution.GreedyInitSolution;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import routesFinding.initSolution.GreedyKmeansInitSolution;
import routesFinding.initSolution.GreedySecterClusterInitSolution;

import static routesFinding.localSearch.SA.SISRSolver;

public class Test {

    public static void main(String args[]){
        // 指定订单 50个
//        List<String> specialOrders = new ArrayList<>(Arrays.asList(
//                "SGMW-23052700180","SGMW-23052700567","SGMW-23052700653", //SGMW-8450554
//                "SGMW-23052700772","SGMW-23052700428", //SGMW-8450499
//                "SGMW-23052700853","SGMW-23052700251", //SGMW-8450532
//                "SGMW-23052700564", //SGMW-8450731
//                "SGMW-23052700161","SGMW-23052700075", //SGMW-8450552
//                "SGMW-23052700164","SGMW-23052700551","SGMW-23052700723", //SGMW-8450651
//                "SGMW-23052700504","SGMW-23052700074", //SGMW-8450036
//                "SGMW-23052700088", //SGMW-8450575
//                "SGMW-23052700406","SGMW-23052700148","SGMW-23052700191", //SGMW-8320179
//                "SGMW-23052700662","SGMW-23052700576", //SGMW-8450055
//                "SGMW-23052700473","SGMW-23052700215",
//                "SGMW-23052700238","SGMW-23052700410","SGMW-23052700840",
//                "SGMW-23052700669",
//                "SGMW-23052700201","SGMW-23052700803",
//                "SGMW-23052700444","SGMW-23052700272",
//                "SGMW-23052700666",
//                "SGMW-23052700482","SGMW-23052700439",
//                "SGMW-23052700219","SGMW-23052700864","SGMW-23052700090",
//                "SGMW-23052700458",
//                "SGMW-23052700691","SGMW-23052700046"
//        ));
        // 指定订单 30个
        List<String> specialOrders = new ArrayList<>(Arrays.asList(
                "SGMW-23052700180","SGMW-23052700567","SGMW-23052700653", //SGMW-8450554
                "SGMW-23052700772","SGMW-23052700385","SGMW-23052700643","SGMW-23052700428","SGMW-23052700729", //SGMW-8450499
                "SGMW-23052700853","SGMW-23052700251","SGMW-23052700810", //SGMW-8450532
                "SGMW-23052700779","SGMW-23052700908","SGMW-23052700048","SGMW-23052700564", //SGMW-8450731
                "SGMW-23052700161","SGMW-23052700290","SGMW-23052700806",//SGMW-8450552
                "SGMW-23052700164","SGMW-23052700895","SGMW-23052700981", //SGMW-8450651
                "SGMW-23052700504","SGMW-23052700074","SGMW-23052700246", //SGMW-8450036
                "SGMW-23052700088", //SGMW-8450575
                "SGMW-23052700406","SGMW-23052700062", //SGMW-8320179
                "SGMW-23052700662","SGMW-23052700232","SGMW-23052700576" //SGMW-8450055
        ));

        // 读取数据
        DataReader dataReader = new DataReader();
        List<CustomerData> customerDatas = dataReader.customerData();
        List<SKUpfepData> skuPfepDatas = dataReader.skUpfepsData();
        List<OrderHeadData> orderHeadDatas = dataReader.orderHeadData();
        List<OrderDetailData> orderDetailDatas = dataReader.orderDetailData();
        List<EquipmentData> equipmentDatas = dataReader.equipmentData();
        List<LoadingRuleData> loadingRuleDatas = dataReader.loadingRuleData();
        List<DistanceServiceTimeData> distanceServiceTimeDatas = dataReader.distanceServiceTimesData();

        // orderHeadDatash和orderDetailDatas筛选订单
        orderHeadDatas.removeIf(orderHeadData -> !specialOrders.contains(orderHeadData.getOrderID()));
        orderDetailDatas.removeIf(orderDetailData -> !specialOrders.contains(orderDetailData.getOrderID()));

        // 数据聚合操作，得到Customer
        Aggregator aggregator = new Aggregator();
        List<OrderHeadDetailSkuSupplier> customers = aggregator.aggregateCustomers(customerDatas, orderHeadDatas, orderDetailDatas, skuPfepDatas);

        // 中心点
        String depot = "SGMW-1000";

        List<Customer> allCustomers = OrderHeadDetailSkuSupplier.groupByLocation(customers);
        List<OrderDimension> allOrders = OrderHeadDetailSkuSupplier.groupByOrder(customers);

        // 加入depot客户
        Customer customer = new Customer();
        customer.setLocationId(depot);
        customer.setLocation(new Location(24.32319,109.37565));
        customer.setLat(24.32319);
        customer.setLon(109.37565);
        customer.setDuration(0);
        customer.setSkuList(new ArrayList<>());
        allCustomers.add(customer);

        String depotOrder = "depot-dummyOrder";
        // depot节点的订单设置为dummy
        OrderDimension orderDimension = new OrderDimension();
        orderDimension.setOrderID(depotOrder);
        orderDimension.setNode(depot);
        orderDimension.setSkuList(new ArrayList<>());
        allOrders.add(orderDimension);

        // 订单与物品的关系表
        Map<String,List<SKU>> orderItemsMapping = new HashMap<>();
        for(OrderDimension orderDimension1:allOrders){
            String order = orderDimension1.getOrderID();
            List<SKU> skuList = orderDimension1.getSkuList();
            orderItemsMapping.put(order,skuList);
        }

        // order list
        List<String> orderList = new ArrayList<>();
        for(OrderDimension orderDimension1:allOrders){
            orderList.add(orderDimension1.getOrderID());
        }

        // customer list(location_id list)
        Map<String, Location> cusLocationMapping = new HashMap<>();
        for(Customer cus:allCustomers){
            cusLocationMapping.put(cus.getLocationId(),cus.getLocation());
        }

        // 订单与customer的对应关系
        Map<String, String> orderCustomerMapping = new HashMap<>();
        for(OrderDimension orderDimension1:allOrders){
            orderCustomerMapping.put(orderDimension1.getOrderID(),orderDimension1.getNode());
        }

        // 订单位置
        Map<String, Location> orderLocationMapping = new HashMap<>();
        for(String or:orderList){
            String node = orderCustomerMapping.get(or);
            orderLocationMapping.put(or, cusLocationMapping.get(node));
        }

        // customer与订单的对应关系
        Map<String, List<String>> customerOrderMapping = new HashMap<>();
        for (Map.Entry<String, String> entry : orderCustomerMapping.entrySet()) {
            String orderID = entry.getKey();
            String customer1 = entry.getValue();
            // If the customer is already in the mapping, add the order to their list
            if (customerOrderMapping.containsKey(customer1)) {
                customerOrderMapping.get(customer1).add(orderID);
            } else {
                // If the customer is not in the mapping, create a new list and add the order
                List<String> orders = new ArrayList<>();
                orders.add(orderID);
                customerOrderMapping.put(customer1, orders);
            }
        }

        // （订单维度）距离矩阵、时间矩阵
        DistanceTimeMatrixByOrder distanceTimeData = new DistanceTimeMatrixByOrder(allOrders, allCustomers, distanceServiceTimeDatas);
        int[][] distanceMatrix = distanceTimeData.getDistanceMatrix();
        int[][] serveTimeMatrix = distanceTimeData.getTimeMatrix();
        StackingRule stackingRule = new StackingRule(loadingRuleDatas);
        List<ItemType> compatibleItems = stackingRule.getCompatibleItemsList();

        // 堆叠规则
        Config config = Config.getInstance();
        config.setCompatibleItems(compatibleItems);

        // 选车（随机选和最大体积的）
        VehicleGenerator vehicleGenerator = new VehicleGenerator(equipmentDatas);
        Vehicle vehicle = vehicleGenerator.getMaxOneVehicle();
        System.out.printf("vehicle w=%s, l=%s, h=%s", vehicle.getWidth(), vehicle.getLength(),vehicle.getHeight());

//        // *******************************************采样处理**********************************************
//        Map<String, Integer> dataMap = new HashMap<>();
//        String filePath = "D:/greedy_vrp-grasp_binpacking/container-loading-optimization/src/main/java/routesFinding/test/data/itemNum.json";
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            JsonNode rootNode = objectMapper.readTree(new File(filePath));
//            Iterator<Map.Entry<String, JsonNode>> fields = rootNode.fields();
//            while (fields.hasNext()) {
//                Map.Entry<String, JsonNode> entry = fields.next();
//                String key = entry.getKey();
//                int value = (int) entry.getValue().asDouble();
//                dataMap.put(key, value);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        for (Map.Entry<String, List<SKU>> entry : orderItemsMapping.entrySet()){
//            for(SKU sku: entry.getValue()){
//                String skuId = sku.getSkuId();
//                sku.setNumber(dataMap.get(skuId));
//            }
//        }
//        Map<String, List<SKU>> orderItemsMapping1 = new HashMap<>();
//        for (Map.Entry<String, List<SKU>> entry : orderItemsMapping.entrySet()){
//            List<SKU> skulist = entry.getValue();
//            if(skulist.size()!=0){
//                orderItemsMapping1.put(entry.getKey(), Collections.singletonList(skulist.get(0))); //订单中只取一个
//            } else {orderItemsMapping1.put(entry.getKey(), skulist);}
//        }

        // ********************************************************************
        // initsolution
        // ********************************************************************
        /*
        1、纯贪心
         */
        GreedyInitSolution greedyInitsolution = new GreedyInitSolution(
                vehicle,
                orderList,
                distanceMatrix,
                serveTimeMatrix,
                orderItemsMapping,
                orderCustomerMapping,
                compatibleItems
                );
        List<List<String>> initRoute = greedyInitsolution.generateRoutes();

        /*
        2、kmeans聚类
         */
//        GreedyKmeansInitSolution greedyKmeansInitSolution = new GreedyKmeansInitSolution(
//                vehicle,
//                depotOrder,
//                orderList,
//                distanceMatrix,
//                serveTimeMatrix,
//                orderItemsMapping1,
//                orderLocationMapping,
//                compatibleItems
//        );
//        greedyKmeansInitSolution.generateRoutes(2);

        /*
        3、扇形聚类
         */
//        GreedySecterClusterInitSolution greedySecterClusterInitSolution = new GreedySecterClusterInitSolution(
//                vehicle,
//                depotOrder,
//                orderList,
//                distanceMatrix,
//                serveTimeMatrix,
//                orderItemsMapping1,
//                orderLocationMapping,
//                compatibleItems,
//                depotLocation
//        );
//        List<List<String>> initRoute = greedySecterClusterInitSolution.generateRoutes(5);
//        // 订单到客户节点的转化
//        List<List<String>> realTours1 = new ArrayList<>();
//        for(List<String> tou:initRoute){
//            List<String> realTour1 = new ArrayList<>();
//            for(int i=0;i<tou.size();i++){
//                if(i == tou.size()-1 || !orderCustomerMapping.get(tou.get(i)).equals(orderCustomerMapping.get(tou.get(i+1)))){
//                    realTour1.add(orderCustomerMapping.get(tou.get(i)));
//                }
//            }
//            realTours1.add(realTour1);
//        }
//        System.out.println(realTours1);

        // *********************************************************************
        // ruin-recreate
        // *********************************************************************
        int nbrOfIterations = 100;
        List<List<String>> routes = SISRSolver(vehicle, depotOrder, orderList, distanceMatrix, serveTimeMatrix, orderItemsMapping, compatibleItems, nbrOfIterations, initRoute);

        // 订单到客户节点的转化
        List<List<String>> realTours = new ArrayList<>();
        for(List<String> tou:routes){
            System.out.println(tou);
            List<String> realTour = new ArrayList<>();
            for(int i=0;i<tou.size();i++){
                if(i == tou.size()-1 || !orderCustomerMapping.get(tou.get(i)).equals(orderCustomerMapping.get(tou.get(i+1)))){
                    realTour.add(orderCustomerMapping.get(tou.get(i)));
                }
            }
            realTours.add(realTour);
        }
        System.out.println(realTours);
        // *********************************************************************
        // 测试ruin-recreate路线的装载率
        // *********************************************************************
        for(List<String> nodeList: routes){
            List<SKU> oneRouteItems = new ArrayList<>();
            for(int j=0; j<nodeList.size(); j++){
                String cus = nodeList.get(j);
                List<SKU> cusItem = orderItemsMapping.get(cus);
                // 添加物品节点顺序属性
                for (SKU sku : cusItem) {
                    sku.setGroup(j);
                }
                oneRouteItems.addAll(cusItem);
            }
            // 装箱
            Report report = new GraspBinPacker(vehicle, oneRouteItems, compatibleItems).run();
            System.out.println("unloaded items qty = "+report.getUnpackedItems().size());
            System.out.println(report.getLoadingRate());
            // 输出物品和对应位置，可视化
            List<LPPackageEvent> itemsInfo = report.getPackedItems();
            List<List<Integer>> items = new ArrayList<>();
            List<List<Integer>> positions = new ArrayList<>();
            for(LPPackageEvent lp:itemsInfo){
                List<Integer> item = new ArrayList<>(Arrays.asList(lp.w(),lp.l(),lp.h()));
                List<Integer> position = new ArrayList<>(Arrays.asList(lp.x(),lp.y(),lp.z()));
                items.addAll(Collections.singleton(item));
                positions.addAll(Collections.singleton(position));
            }
            System.out.println("items:"+items);
            System.out.println("position:"+positions);
        }
        System.exit(0);
    }
}
