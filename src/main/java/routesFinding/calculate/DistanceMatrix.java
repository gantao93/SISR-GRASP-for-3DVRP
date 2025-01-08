package routesFinding.calculate;

public class DistanceMatrix {
    public static double[][] getDistanceMatrix(double[][] data) {
        double[][] distanceMatrix = new double[data.length][data.length];
        for (int i = 0 ; i<data.length ; i++) {
            for (int j = 0 ; j<data.length ; j++) {
                if (i!=j) distanceMatrix[i][j] = getDistance(data[i][0], data[i][1], data[j][0], data[j][1]);
            }
        }
        return distanceMatrix;
    }

    public static double getDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1-x2,2) + Math.pow(y1-y2,2));
    }
}
