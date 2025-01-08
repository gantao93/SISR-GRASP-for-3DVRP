package routesFinding.cluster;

import routesFinding.dataImporter.Location;

import java.util.*;

public class KmeansCluster {

    private Map<String, Location> customerLocations;
    private int k; // Number of clusters
    private List<Location> centroids;
    private List<List<String>> clusters;

    public KmeansCluster(Map<String, Location> customerLocations, int k) {
        this.customerLocations = customerLocations;
        this.k = k;
        this.centroids = initializeCentroids();
        this.clusters = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            clusters.add(new ArrayList<>());
        }
    }


    private List<Location> initializeCentroids() {
        List<Location> centroids = new ArrayList<>();
        Random random = new Random();
        List<Location> locations = new ArrayList<>(customerLocations.values());
        for (int i = 0; i < k; i++) {
            int randomIndex = random.nextInt(locations.size());
            Location randomLocation = locations.get(randomIndex);
            centroids.add(randomLocation);
            locations.remove(randomLocation); // Ensure unique centroids
        }
        return centroids;
    }

    public void runKMeans(int maxIterations) {
        for (int iteration = 0; iteration < maxIterations; iteration++) {
            assignToClusters();
            updateCentroids();
        }
    }

    private void assignToClusters() {
        // Clear previous cluster assignments
        for (List<String> cluster : clusters) {
            cluster.clear();
        }

        for (Map.Entry<String, Location> entry : customerLocations.entrySet()) {
            String customer = entry.getKey();
            Location location = entry.getValue();
            int closestCentroidIndex = findClosestCentroid(location);
            clusters.get(closestCentroidIndex).add(customer);
        }
    }

    private int findClosestCentroid(Location location) {
        int closestCentroidIndex = 0;
        double minDistance = Double.MAX_VALUE;

        for (int i = 0; i < centroids.size(); i++) {
            Location centroid = centroids.get(i);
            double distance = calculateEuclideanDistance(location, centroid);

            if (distance < minDistance) {
                minDistance = distance;
                closestCentroidIndex = i;
            }
        }

        return closestCentroidIndex;
    }

    private double calculateEuclideanDistance(Location point1, Location point2) {
        double sum = Math.pow(point1.lat() - point2.lat(), 2) + Math.pow(point1.lon() - point2.lon(), 2);
        return Math.sqrt(sum);
    }

    private void updateCentroids() {
        for (int i = 0; i < k; i++) {
            if (!clusters.get(i).isEmpty()) {
                Location newCentroid = calculateCentroid(clusters.get(i));
                centroids.set(i, newCentroid);
            }
        }
    }

    private Location calculateCentroid(List<String> cluster) {
        double sumX = 0.0;
        double sumY = 0.0;

        for (String customer : cluster) {
            Location location = customerLocations.get(customer);
            sumX += location.lon();
            sumY += location.lat();
        }

        double centroidX = sumX / cluster.size();
        double centroidY = sumY / cluster.size();

        return new Location(centroidX, centroidY);
    }

    public List<List<String>> getClusters() {
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
        customerLocations.put("Customer5", new Location(1.0, 0.6));
        customerLocations.put("Customer6", new Location(9.0, 11.0));
        customerLocations.put("Customer7", new Location(19.0, 21.0));
        customerLocations.put("Customer8", new Location(29.0, 11.0));
        customerLocations.put("Customer9", new Location(19.0, 11.0));
        customerLocations.put("Customer10", new Location(9.0, 21.0));
        customerLocations.put("Customer11", new Location(19.0, 18.0));
        customerLocations.put("Customer12", new Location(9.0, 111.0));
        customerLocations.put("Customer13", new Location(49.0, 11.0));


        int k = 3; // Number of clusters

        KmeansCluster kMeans = new KmeansCluster(customerLocations, k);
        kMeans.runKMeans(100); // Specify the maximum number of iterations

        List<List<String>> clusters = kMeans.getClusters();
        for (int i = 0; i < clusters.size(); i++) {
            System.out.println("Cluster " + (i + 1) + ": " + clusters.get(i));
        }
    }


}


