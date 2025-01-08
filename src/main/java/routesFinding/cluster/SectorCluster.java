package routesFinding.cluster;

import routesFinding.dataImporter.Location;

import java.util.*;

public class SectorCluster {
    private Location depotLocation;
    private Map<String, Location> customerLocationMapping;

    public SectorCluster(Location depotLocation, Map<String, Location> customerLocations) {
        this.depotLocation = depotLocation;
        this.customerLocationMapping = customerLocations;
    }

    public List<List<String>> clustering(int num) {
        Map<String, double[]> angleMap = new HashMap<>();
        // 计算每个客户的角度
        Set<String> keys = customerLocationMapping.keySet();
        for(String cus:keys){
            Location location = customerLocationMapping.get(cus);
            double angle = Math.atan2(location.lon()-depotLocation.lon(), location.lat()-depotLocation.lat());
            angle = Math.toDegrees(angle) >= 0 ? Math.toDegrees(angle) : Math.toDegrees(angle) + 360;

            angleMap.put(cus, new double[]{angle});
        }
        // 将[0, 360)等分为num个区域
        double regionWidth = 360.0 / num;
        List<double[]> regions = new ArrayList<>();
        for(int i=0; i<num; i++){
            regions.add(new double[]{i*regionWidth, (i+1)*regionWidth});
        }
        //判断每个客户属于哪个区域
        List<List<String>> clusters = new ArrayList<>();
        for(int i=0; i<num;i++){
            clusters.add(new ArrayList<>());
        }

        for (Map.Entry<String, double[]> entry:angleMap.entrySet()) {
            String customer = entry.getKey();
            double angle = entry.getValue()[0];
            for (int i=0; i<num; i++) {
                double[] region = regions.get(i);
                if(region[0] <= angle && angle < region[1]){
                    clusters.get(i).add(customer);
                    break;
                }
            }
        }
        // Filter out empty clusters
        clusters.removeIf(List::isEmpty);
        return clusters;
    }

    public static void main(String[] args) {
        // Example usage:
        Map<String, Location> customerLocations = new HashMap<>();
        customerLocations.put("Customer1", new Location(1.0, 2.0));
        customerLocations.put("Customer2", new Location(1.5, 1.8));
        customerLocations.put("Customer3", new Location(5.0, 8.0));
        customerLocations.put("Customer4", new Location(8.0, 8.0));
        customerLocations.put("Customer5", new Location(241.0, 0.6));
        customerLocations.put("Customer6", new Location(9.0, 11.0));
        customerLocations.put("Customer7", new Location(19.0, 21.0));
        customerLocations.put("Customer8", new Location(129.0, 11.0));
        customerLocations.put("Customer9", new Location(19.0, 11.0));
        customerLocations.put("Customer10", new Location(9.0, 21.0));
        customerLocations.put("Customer11", new Location(119.0, 18.0));
        customerLocations.put("Customer12", new Location(229.0, 111.0));
        customerLocations.put("Customer13", new Location(249.0, 11.0));


        Location depotLocation = new Location(10.0, 20.0);

        SectorCluster sectorCluster = new SectorCluster(depotLocation, customerLocations);


        List<List<String>> clusters = sectorCluster.clustering(7);

        for (int i = 0; i < clusters.size(); i++) {
            System.out.println("Cluster " + (i + 1) + ": " + clusters.get(i));
        }
    }


}
