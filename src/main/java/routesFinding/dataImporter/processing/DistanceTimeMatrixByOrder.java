package routesFinding.dataImporter.processing;

import routesFinding.dataImporter.Customer;
import routesFinding.dataImporter.DistanceServiceTimeData;
import routesFinding.dataImporter.OrderDimension;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class DistanceTimeMatrixByOrder {

    private List<OrderDimension> orderList;
    private List<Customer> customerList;
    private List<DistanceServiceTimeData> distanceServiceTimeDataList;

    public DistanceTimeMatrixByOrder(List<OrderDimension> orderList, List<Customer> customerList, List<DistanceServiceTimeData> distanceServiceTimeDataList){
        this.orderList = orderList;
        this.customerList = customerList;
        this.distanceServiceTimeDataList = distanceServiceTimeDataList;
    }

    public int[][] getDistanceMatrix(){
        int size = customerList.size();
        int[][] distanceMatrix = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(i==j){
                    distanceMatrix[i][j] = 0;
                } else {
                    distanceMatrix[i][j] = Integer.MAX_VALUE;
                }
            }
        }
        List<String> locationList = new ArrayList<>();
        for(Customer cus:customerList){
            locationList.add(cus.getLocationId());
        }
        for(DistanceServiceTimeData distanceServiceTimeData: distanceServiceTimeDataList){
            String loc1 = distanceServiceTimeData.getSourceLocationID();
            String loc2 = distanceServiceTimeData.getDestLocationID();
            int distance = distanceServiceTimeData.getDistance();
            if(locationList.contains(loc1) && locationList.contains(loc2)) {
                int loc1Index = locationList.indexOf(loc1);
                int loc2Index = locationList.indexOf(loc2);
                distanceMatrix[loc1Index][loc2Index] = distance;
                distanceMatrix[loc2Index][loc1Index] = distance;
            }
        }

        int size1 = orderList.size();
        int[][] distanceMatrixOrder = new int[size1][size1];
        for (int i = 0; i < size1; i++) {
            for (int j = 0; j < size1; j++) {
                if(i==j){
                    distanceMatrixOrder[i][j] = 0;
                } else {
                    distanceMatrixOrder[i][j] = Integer.MAX_VALUE;
                }
                String loc1 = orderList.get(i).getNode();
                String loc2 = orderList.get(j).getNode();
                int loc1Index = locationList.indexOf(loc1);
                int loc2Index = locationList.indexOf(loc2);
                int value = distanceMatrix[loc2Index][loc1Index];
                distanceMatrixOrder[i][j] = value;
            }
        }

        return distanceMatrixOrder;
    }

    public int[][] getTimeMatrix(){
        int size = customerList.size();
        int[][] timeMatrix = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(i==j){
                    timeMatrix[i][j] = 0;
                } else{
                    timeMatrix[i][j] = Integer.MAX_VALUE;
                }

            }
        }
        List<String> locationList = new ArrayList<>();
        for(Customer cus:customerList){
            locationList.add(cus.getLocationId());
        }

        for(DistanceServiceTimeData distanceServiceTimeData: distanceServiceTimeDataList){
            String loc1 = distanceServiceTimeData.getSourceLocationID();
            String loc2 = distanceServiceTimeData.getDestLocationID();
            int time = distanceServiceTimeData.getServiceTime();
            if(locationList.contains(loc1) && locationList.contains(loc2)){
                int loc1Index = locationList.indexOf(loc1);
                int loc2Index = locationList.indexOf(loc2);
                timeMatrix[loc1Index][loc2Index] = time;
                timeMatrix[loc2Index][loc1Index] = time;
            }

        }
        int size1 = orderList.size();
        int[][] timeMatrixOrder = new int[size1][size1];
        for (int i = 0; i < size1; i++) {
            for (int j = 0; j < size1; j++) {
                if(i==j){
                    timeMatrixOrder[i][j] = 0;
                } else{
                    timeMatrixOrder[i][j] = Integer.MAX_VALUE;
                }
                String loc1 = orderList.get(i).getNode();
                String loc2 = orderList.get(j).getNode();
                int loc1Index = locationList.indexOf(loc1);
                int loc2Index = locationList.indexOf(loc2);
                int value = timeMatrix[loc2Index][loc1Index];
                timeMatrixOrder[i][j] = value;
            }
        }
        return timeMatrixOrder;

    }
    public int customerInOrderListIndex(String location){
        return IntStream.range(0, orderList.size())
                .filter(i -> orderList.get(i).getNode().equals(location))
                .findFirst()
                .orElse(-1);
    }

}
