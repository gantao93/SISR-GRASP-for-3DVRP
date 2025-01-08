package util;

import java.util.Comparator;

public class ArrayIndexComparator implements Comparator<Integer>{
    private final int[] array;
    private final boolean reverse;
    public ArrayIndexComparator(int[] array, boolean reverse)
    {
        this.array = array;
        this.reverse = reverse;
    }

    public Integer[] createIndexArray()
    {
        Integer[] indexes = new Integer[array.length];
        for (int i = 0; i < array.length; i++)
        {
            indexes[i] = i; // Autoboxing
        }
        return indexes;
    }

    @Override
    public int compare(Integer index1, Integer index2)
    {
        if (reverse) return array[index1]<(array[index2])?1:array[index1]>(array[index2])?-1:0;
        return array[index1]>(array[index2])?1:array[index1]<(array[index2])?-1:0;
    }
}