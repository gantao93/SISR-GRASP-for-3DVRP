package routesFinding.localSearch;

import core.base.container.SKU;
import core.base.container.Vehicle;
import core.base.item.ItemType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static routesFinding.calculate.Neighbours.buildNeighbours;
import static routesFinding.calculate.RoutesDistance.getRoutesDistance;
import static routesFinding.localSearch.recreate.AbsentRoutes.getRuinRoutes;
import static routesFinding.localSearch.ruin.Ruin.getAbsents;
import static routesFinding.localSearch.recreate.Recreate.recreate;

public class SA {

    public static List<List<String>> SISRSolver(
            Vehicle vehicle,
            String depot,
            List<String> customerList,
            int[][] distanceMatrix,
            int[][] timeMatrix,
            Map<String,List<SKU>> customerItemsMapping,
            List<ItemType> itemsCompatibleInfo,
            int nbrOfIterations,
            List<List<String>> initRoute) {
        List<List<String>> orderRoutes = localSearch(
                vehicle,
                depot,
                customerList,
                distanceMatrix,
                timeMatrix,
                customerItemsMapping,
                itemsCompatibleInfo,
                nbrOfIterations,
                initRoute,
                100.0f,
                1.0f,
                10.0f,
                10.0f,
                0.01f,
                10,
                0);
        return orderRoutes;
    }

    public static List<List<String>> localSearch(Vehicle vehicle,
                                                 String depot,
                                                 List<String> customerList,
                                                 int[][] distanceMatrix,
                                                 int[][] timeMatrix,
                                                 Map<String,List<SKU>> customerItemsMapping,
                                                 List<ItemType> itemsCompatibleInfo,
                                                 int nbrOfIterations,
                                                 List<List<String>> initRoute,
                                                 double initTemperature,
                                                 double finalTemperature,
                                                 double c_bar,
                                                 double L_max,
                                                 double m_alpha,
                                                 int verboseStep,
                                                 double sol_distance) {
        // The depot point should not be included in the routes.
        List<List<String>> bestRoute;
        if (initRoute == null) {
            bestRoute = new ArrayList<>();
            // 初始化每个节点一个路线
            for(String node: customerList){
                bestRoute.add(new ArrayList<>(Arrays.asList(node)));
            }
        } else {
            bestRoute = initRoute;
        }
        if (nbrOfIterations<=0) {
            return bestRoute;
        }
        // 计算路线总里程
        double bestDistance = getRoutesDistance(depot, customerList, bestRoute, distanceMatrix);

        List<List<String>> lastRoute = bestRoute;
        double lastDistance = bestDistance;

        int[][] neighbours = buildNeighbours(distanceMatrix);

        // cooling constant c
        double c = Math.pow((finalTemperature/initTemperature), (1.0/nbrOfIterations));
        double temperature = initTemperature;

        for (int iter = 0 ; iter<nbrOfIterations ; iter++) {
            // Ruin 算子I
            List<String> absents = getAbsents(depot, customerList, lastRoute, neighbours, null, c_bar, L_max, m_alpha);
            // Remove nodes.
            List<List<String>> absentRoute = getRuinRoutes(lastRoute, absents);
            // Recreate
            List<List<String>> currentRoute = recreate(vehicle, customerItemsMapping, itemsCompatibleInfo, depot, customerList, distanceMatrix, absentRoute, absents, 0);

            // Check and update
            double currentDistance = getRoutesDistance(depot, customerList, currentRoute, distanceMatrix);
            if (currentRoute.size() < lastRoute.size() ||
                    (currentRoute.size() == lastRoute.size() && currentDistance < (lastDistance - temperature * Math.log(Math.random())))) {
                lastRoute = currentRoute;
                lastDistance = currentDistance;
                if (currentRoute.size() < bestRoute.size() || currentDistance < bestDistance) {
                    bestRoute = currentRoute;
                    bestDistance = currentDistance;
                }
            }
            temperature *= c;

            if (verboseStep > 0 && ((iter+1)%verboseStep==0 || (iter+1)==nbrOfIterations)) {
                System.out.println("# iter: "+(iter+1)+"/"+nbrOfIterations);
                System.out.println("    --best route size: "+bestRoute.size()+", "+bestDistance);
                System.out.println("    --last route size: "+lastRoute.size()+", "+lastDistance);
                System.out.println("    --curr route size: "+currentRoute.size()+", "+currentDistance);
            }

            if (sol_distance > 0 && bestDistance < (sol_distance-1e-6)) {
                System.out.println("# iter: "+(iter+1)+"/"+nbrOfIterations);
                System.out.println("    --best route size: "+bestRoute.size()+", "+bestDistance);
                System.out.println("    --last route size: "+lastRoute.size()+", "+lastDistance);
                System.out.println("    --curr route size: "+currentRoute.size()+", "+currentDistance);
                break;
            }
        }

        return bestRoute;
    }

    static List<List<String>> minFleetLocalSearch(Vehicle vehicle,
                                          String depot,
                                          List<String> customerList,
                                          int[][] distanceMatrix,
                                          int[][] timeMatrix,
                                          Map<String,List<SKU>> customerItemsMapping,
                                          List<ItemType> itemsCompatibleInfo,
                                          int nbrOfIterations,
                                          List<List<String>> initRoute,
                                          double c_bar,
                                          double L_max,
                                          double m_alpha,
                                          int verboseStep) {
        // The depot point should not be included in the routes.
        List<List<String>> bestRoute;
        if (initRoute == null) {
            bestRoute = new ArrayList<>();
            // 初始化每个节点一个路线
            for(String node: customerList){
                bestRoute.add(new ArrayList<>(Arrays.asList(node)));
            }
        } else {
            bestRoute = initRoute;
        }
        if (nbrOfIterations<=0) {
            return bestRoute;
        }
        // 计算路线总里程
        double bestDistance = getRoutesDistance(depot, customerList, bestRoute, distanceMatrix);

        List<List<String>> lastRoute = bestRoute;
        double lastDistance = bestDistance;

        int[][] neighbours = buildNeighbours(distanceMatrix);

        List<String> absents = new ArrayList<>();

        // No need to count the number of appearance of the depot point. Be careful to the indices!
        int[] absentCount = new int[customerList.size()-1];

        for (int iter = 0 ; iter<nbrOfIterations ; iter++) {
            List<String> newAbsents = new ArrayList<>();
            for (String node : absents) {
                newAbsents.add(node);
            }
            // Ruin 算子I
            newAbsents = getAbsents(depot, customerList, lastRoute, neighbours, newAbsents, c_bar, L_max, m_alpha);
            // Remove nodes.
            List<List<String>> absentRoute = getRuinRoutes(lastRoute, absents);
            // Recreate
            List<List<String>> currentRoute = recreate(vehicle, customerItemsMapping, itemsCompatibleInfo, depot, customerList, distanceMatrix, absentRoute, newAbsents, lastRoute.size());

            // Check and update
            if (newAbsents.size()==0) {
                bestRoute = currentRoute;
                absents = new ArrayList<>();
                double[] routeAbsentCounts = new double[bestRoute.size()];
                for (int i = 0 ; i<bestRoute.size() ; i++) {
                    List<String> r = bestRoute.get(i);
                    double count = 0.0;
                    for (int node=0;node<r.size();node++) {
                        count += absentCount[node-1];
                    }
                    routeAbsentCounts[i] = count;
                }
                absents = new ArrayList<>();
                for (String i : bestRoute.get(indexOfMin(routeAbsentCounts))) {
                    absents.add(i);
                }
                lastRoute = getRuinRoutes(lastRoute, absents);
            } else {
                int countSumNew = 0;
                int count_sum_old = 0;
                for (int node=0;node<newAbsents.size();node++) {
                    countSumNew += absentCount[node-1];
                }
                for (int node=0;node<absents.size();node++) {
                    count_sum_old += absentCount[node-1];
                }
                if (newAbsents.size()<absents.size() || countSumNew<count_sum_old) {
                    lastRoute = currentRoute;
                    absents = newAbsents;
                }
                for (int node=0;node<newAbsents.size();node++) {
                    absentCount[node-1]++;
                }
            }

            if (verboseStep>0 && ((iter+1)%verboseStep==0 || (iter+1)==nbrOfIterations)) {
                System.out.println("# iter: "+(iter+1)+"/"+nbrOfIterations);
                System.out.println("    --fleet route: "+bestRoute.size()+", "+getRoutesDistance(depot, customerList, bestRoute, distanceMatrix));
            }
        }

        return bestRoute;
    }
    
    private static int indexOfMin(double[] a) {
        int loc = 0;
        double min = a[loc];
        for (int i = 1; i < a.length; i++) {
            if (a[i] < min) {
                min = a[i];
                loc = i;
            }
        }
        return loc;
    }
}
