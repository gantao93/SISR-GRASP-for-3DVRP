package routesFinding.calculate;

import java.util.ArrayList;
import java.util.List;

public class RoutesDistance {

    public static double getRoutesDistance(String depot, List<String> customers, List<List<String>> routes, int[][] distanceMatrix) {
        double total_distance = 0.0;
        for (List<String> r : routes) {
            total_distance += getRouteDistance(depot, customers, r, distanceMatrix);
        }
        return total_distance;
    }

    public static double getRouteDistance(String depot, List<String> customers, List<String> route, int[][] distanceMatrix) {
        String lastNode = depot;
        double distance = 0.0;
        for (int i=0; i<route.size(); i++) {
            String curNode = route.get(i);
            // curNode和nearNode在距离矩阵中的索引
            int curNodeInx = customers.indexOf(curNode);
            int lastNodeInx = customers.indexOf(lastNode);
            distance += distanceMatrix[lastNodeInx][curNodeInx];
            lastNode = curNode;
            if(i == route.size()-1){
                distance += distanceMatrix[customers.indexOf(depot)][curNodeInx];
            }
        }
        return distance;
    }
}
