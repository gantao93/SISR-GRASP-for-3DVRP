package routesFinding.calculate;

import util.ArrayIndexComparator;

import java.util.Arrays;

public class Neighbours {
    /*
    计算每个节点的邻居（按照距离从小到大罗列所有邻居）
     */
    public static int[][] buildNeighbours(int[][] distanceMatrix) {

        int[][] neighbours = new int[distanceMatrix.length][distanceMatrix.length];
        for (int i = 0 ; i<distanceMatrix.length ; i++) {
            ArrayIndexComparator comparator = new ArrayIndexComparator(distanceMatrix[i], false);
            Integer[] indexes = comparator.createIndexArray();
            Arrays.sort(indexes, comparator);
            neighbours[i] = Arrays.stream(indexes).mapToInt(Integer::intValue).toArray();
        }
        return neighbours;
    }
}
