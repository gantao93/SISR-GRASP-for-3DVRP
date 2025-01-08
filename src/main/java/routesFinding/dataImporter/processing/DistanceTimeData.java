package routesFinding.dataImporter.processing;

import routesFinding.dataImporter.DistanceServiceTimeData;
import routesFinding.dataImporter.Customer;

import java.util.ArrayList;
import java.util.List;

public class DistanceTimeData {
    private List<Customer> customerList;
    private List<DistanceServiceTimeData> distanceServiceTimeDataList;

    public DistanceTimeData(List<Customer> customerList, List<DistanceServiceTimeData> distanceServiceTimeDataList){
        this.customerList = customerList;
        this.distanceServiceTimeDataList = distanceServiceTimeDataList;
    }

    public int[][] getDistanceMatrix() {
        int size = customerList.size();
        int[][] distanceMatrix = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(i==j){
                    distanceMatrix[i][j] = 0;
                }else {
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
        return distanceMatrix;
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
        return timeMatrix;

    }


    public int[][] getDistanceMatrix1() {
        int size = customerList.size();
        int[][] distanceMatrix = new int[size][size];

        for (int i = 0; i < size; i++) {
            Customer c1 = customerList.get(i);
            for (int j = 0; j < size; j++) {
                Customer c2 = customerList.get(j);
                if(c1.getLocationId()==c2.getLocationId()){
                    distanceMatrix[i][j] = 0;
                } else {
                    int distance = calculateDistance(c1.getLocationId(), c2.getLocationId());
                    distanceMatrix[i][j] = distance;
                    distanceMatrix[j][i] = distance;
                }
            }
        }

        return distanceMatrix;
    }

    public int[][] getTimeMatrix1() {
        int size = customerList.size();
        int[][] timeMatrix = new int[size][size];

        for (int i = 0; i < size; i++) {
            Customer c1 = customerList.get(i);
            for (int j = 0; j < size; j++) {
                Customer c2 = customerList.get(j);
                if(c1.getLocationId()==c2.getLocationId()){
                    timeMatrix[i][j]=0;}
                else{
                    int timeValue = calculateTime(c1.getLocationId(), c2.getLocationId());
                    timeMatrix[i][j] = timeValue;
                    timeMatrix[j][i] = timeValue;
                }

            }
        }
        return timeMatrix;
    }


    public int calculateDistance(String loc1, String loc2){
        // 查表
        int distance = (int) Math.pow(10,5);
        for(DistanceServiceTimeData distanceServiceTimeData: distanceServiceTimeDataList) {
            if((distanceServiceTimeData.getSourceLocationID() == loc1 && distanceServiceTimeData.getDestLocationID() == loc2) ||
                    (distanceServiceTimeData.getSourceLocationID() == loc2 && distanceServiceTimeData.getDestLocationID() == loc1)) {
                distance = distanceServiceTimeData.getDistance();
                break;
            }
        }
        return distance;
    }

    public int calculateTime(String loc1, String loc2){
        // 查表
        int timeValue = (int) Math.pow(10,5);
        for(DistanceServiceTimeData distanceServiceTimeData: distanceServiceTimeDataList) {
            if((distanceServiceTimeData.getSourceLocationID() == loc1 && distanceServiceTimeData.getDestLocationID() == loc2) ||
                    (distanceServiceTimeData.getSourceLocationID() == loc2 && distanceServiceTimeData.getDestLocationID() == loc1)) {
                timeValue = distanceServiceTimeData.getServiceTime();
                break;
            }
        }
        return timeValue;
    }
}
