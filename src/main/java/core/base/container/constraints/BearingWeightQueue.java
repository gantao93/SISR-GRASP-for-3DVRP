package core.base.container.constraints;

import core.base.container.ZItemGraph;
import core.base.item.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A queue of stacked items, where each item is returned next, when all upper items
 * are processed.
 */
public class BearingWeightQueue {

    private final boolean[] isProcessed;
    private final Map<Integer, int[]> upperItems = new HashMap<>();

    public BearingWeightQueue(int nbrOfItems) {
        this.isProcessed = new boolean[nbrOfItems];
    }

    /**
     * Adds an item to queue with all their upper items.
     *
     * The given item means as processed.
     */
    public void add(Item newItem, ZItemGraph graph) {
        List<Item> uppers = graph.getItemsAbove(newItem);
        int[] upperIdx = new int[uppers.size()];
        for (int i = upperIdx.length - 1; i >= 0; i--) {
            Item upperItem = uppers.get(i);
            upperIdx[i] = upperItem.index;

            // If unprocessed upper items were found,
            // add them to queue instead of this item
            if(!isProcessed[upperItem.index]) {
                add(upperItem, graph);
                return;
            }
        }

        upperItems.put(newItem.index, upperIdx);
    }

    /**
     * Get next item, where all upper items are processed.
     */
    public int getNext() {
        for (Integer idx : upperItems.keySet()) {
            if(areAllProcessed(idx)) {
                upperItems.remove(idx);
                return idx;
            }
        }
        return -1;
    }

    public boolean hasMore() {
        return upperItems.size() > 0;
    }

    private boolean areAllProcessed(int itemIdx) {
        int[] upperIdx = upperItems.get(itemIdx);
        boolean isOK = true;
        for (int i = upperIdx.length - 1; i >= 0; i--) {
            isOK &= isProcessed[upperIdx[i]];
        }

        return isOK;
    }

    public void setProcessed(int index) {
        isProcessed[index] = true;
    }
}
