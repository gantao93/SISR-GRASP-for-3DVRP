package core.opt.improvement;

import core.opt.construction.onetype.OneContainerOneTypePacker;
import core.base.GRASPModel;
import core.base.item.Item;
import core.base.monitor.StatusCode;
import core.exception.Exception;
import core.opt.GRASPBase;

import java.util.Arrays;
import java.util.Random;

//SingleBinOptimizedPacker
public class LocalSearchPacker extends GRASPBase {

    private OneContainerOneTypePacker packer = new OneContainerOneTypePacker();

    private Random rand = new Random(1234);
    private int timeLimitSeconds = 2*60; // 120 seconds time limit

    @Override
    public void execute(GRASPModel model) throws Exception {
        System.out.println("************** Single Container Optimization ***************");
        System.out.println("***************** Construction Phase ***********************");
        System.out.println("************************************************************");
        packer.execute(model);
        model.getStatusManager().fireMessage(StatusCode.RUNNING,"Init "+model.getUnplannedItems().length);

        System.out.println("************** Single Container Optimization ***************");
        System.out.println("***************** Local Search Phase ***********************");
        System.out.println("************************************************************");
        doRelocateLocalSearch(model); // 迭代次数
//        doRelocateLocalSearchTimeLimit(model); //迭代时间

    }

    private void doBestSwap(GRASPModel model) throws Exception {
        packer.execute(model);
        model.getStatusManager().fireMessage(StatusCode.RUNNING,"Init Swap Next "+model.getUnplannedItems().length);

        Item[] items = model.getItems();
        int[] bestValue = new int[]{model.getUnplannedItems().length,-1,-1, 0};
        for (int i = 0; i < items.length - 2; i++) {
            // Change item queue
            swap(items, i, i + 1);
            // Pack
            packer.execute(model);

            model.getStatusManager().fireMessage(StatusCode.RUNNING,i + " " + model.getUnplannedItems().length);
            // Check if there are unplanned items
            if (model.getUnplannedItems().length < bestValue[0]) {
                bestValue[0] = model.getUnplannedItems().length;
                bestValue[1] = i;
                bestValue[2] = i + 1;
                bestValue[3] = 1;

                if(model.getUnplannedItems().length == 0) {
                    return;
                }
            }
            // Change back
            swap(items, i, i + 1);
        }

        if (bestValue[3] == 1) {
            swap(items, bestValue[1], bestValue[2]);
        }
    }

    private void doSwapNextLocalSearch(GRASPModel model) throws Exception {
        packer.execute(model);
        model.getStatusManager().fireMessage(StatusCode.RUNNING,"Init Swap Next LS "+model.getUnplannedItems().length);

        Item[] items = model.getItems();
        int[] bestValue = new int[]{model.getUnplannedItems().length,-1,-1, 1};
        while(bestValue[3] == 1) {
            bestValue[3] = 0;

            for (int i = 0; i < items.length - 2; i++) {
                // Change item queue
                swap(items, i, i + 1);
                // Pack
                packer.execute(model);
                // Check if there are unplanned items
                if (model.getUnplannedItems().length < bestValue[0]) {
                    setBestMove(model, bestValue, i, i + 1);
                }
                // Change back
                swap(items, i, i + 1);
            }

            if (bestValue[3] == 1) {
                swap(items, bestValue[1], bestValue[2]);
            }
        }
    }

    private void doSwapLocalSearch(GRASPModel model) throws Exception {
        packer.execute(model);
        model.getStatusManager().fireMessage(StatusCode.RUNNING,"Init Swap "+model.getUnplannedItems().length);

        Item[] items = model.getItems();
        int[] bestValue = new int[]{model.getUnplannedItems().length,-1,-1, 1};
        while(bestValue[3] == 1) {
            bestValue[3] = 0;

            for (int i = 0; i < items.length - 2; i++) {
                for (int j = i + 1; j < items.length - 1; j++) {
                    // Change item queue
                    swap(items, i, j);
                    // Pack
                    packer.execute(model);
                    // Check if there are unplanned items
                    if (model.getUnplannedItems().length < bestValue[0]) {
                        setBestMove(model, bestValue, i, j);
                        if (model.getUnplannedItems().length == 0) {
                            return;
                        }
                    }
                    // Change back
                    swap(items, i, j);
                }
            }

            if (bestValue[3] == 1) {
                swap(items, bestValue[1], bestValue[2]);
            }
        }
    }

    private void doRelocateLocalSearch(GRASPModel model) throws Exception {
        packer.execute(model);
        model.getStatusManager().fireMessage(StatusCode.RUNNING,"Init RelocateLS "+model.getUnplannedItems().length);

        Item[] items = model.getItems();
        Item[] bestItems = Arrays.copyOf(items, items.length);
        int[] bestValue = new int[]{model.getUnplannedItems().length,-1,-1, 1};
        for (int k = 0; k < 10; k++) {
            model.getStatusManager().fireMessage(StatusCode.RUNNING,"iter "+k);
            bestValue[3] = 1;
            while (bestValue[3] == 1) {
                bestValue[3] = 0;

                for (int i = 0; i < items.length - 1; i++) {
                    for (int j = 0; j < items.length; j++) {
                        if (j == i + 1 || j == i)
                            continue;
                        // Change item queue
                        move(items, i, j);
                        // Pack
                        packer.execute(model);

                        model.getStatusManager().fireMessage(StatusCode.RUNNING,i+" "+j+" "+bestValue[0]);

                        // Check if there are unplanned items
                        if (model.getUnplannedItems().length < bestValue[0]) {
                            setBestMove(model, bestValue, i, j);

                            bestItems = Arrays.copyOf(items, items.length);

                            if (model.getUnplannedItems().length == 0) {
                                return;
                            }
                        }
                        // Change back
                        if (i < j) {
                            move(items, j - 1, i);
                        } else {
                            move(items, j, i + 1);
                        }
                    }
                }

                if (bestValue[3] == 1) {
                    swap(items, bestValue[1], bestValue[2]);
                }
            }

            // Make random move in search space
            perturb(items);
        }

        // Reset best solution
        System.out.print(String.format("#################### last execute packer.execute(model) #################### \n"));
        model.setItems(bestItems);
        packer.execute(model);
    }

    private void setBestMove(GRASPModel model, int[] bestValue, int value1, int value2) {
        bestValue[0] = model.getUnplannedItems().length;
        bestValue[1] = value1;
        bestValue[2] = value2;
        bestValue[3] = 1;
        model.getStatusManager().fireMessage(StatusCode.RUNNING,"Better " + Arrays.toString(bestValue));
    }

    private void perturb(Item[] items) {
        if(items.length == 1){
            return;
        }
        for (int n = 0; n < 4; n++) {
            int i, j;
            do {
                i = rand.nextInt(items.length);
                j = rand.nextInt(items.length);
            } while(i == j);
            swap(items, i, j);
        }
    }

    /**
     * Exchange the position of two items at given positions
     */
    private void swap(Item[] items, int indexA, int indexB) {
        Item b = items[indexB];
        items[indexB] = items[indexA];
        items[indexA] = b;
    }

    /**
     * Exchange the position of two items at given positions
     */
    private void move(Item[] items, int indexSrc, int indexDst) {
        Item src = items[indexSrc];
        if(indexSrc < indexDst) {
            System.arraycopy(items, indexSrc + 1, items, indexSrc, indexDst - indexSrc);
            items[indexDst - 1] = src;
        } else {
            System.arraycopy(items, indexDst, items, indexDst + 1, indexSrc - indexDst);
            items[indexDst] = src;
        }
    }

    /*
    doRelocateLocalSearch 限制时间
     */
    private void doRelocateLocalSearchTimeLimit(GRASPModel model) throws Exception {
        packer.execute(model);
        model.getStatusManager().fireMessage(StatusCode.RUNNING, "Init RelocateLS " + model.getUnplannedItems().length);

        Item[] items = model.getItems();
        Item[] bestItems = Arrays.copyOf(items, items.length);
        int[] bestValue = new int[]{model.getUnplannedItems().length, -1, -1, 1};

        long startTime = System.currentTimeMillis();
        long lastPrintTime = startTime;
        while ((System.currentTimeMillis() - startTime) / 1000 < timeLimitSeconds) {
            long currentTime = System.currentTimeMillis();
            if ((currentTime - lastPrintTime) >= 1000) {
                lastPrintTime = currentTime;
                System.out.print(String.format("######################################## iteration time %s s ######################################## \n", (currentTime - startTime) / 1000));
            }
            model.getStatusManager().fireMessage(StatusCode.RUNNING, "iter " + bestValue[3]);
            bestValue[3] = 1;
            while (bestValue[3] == 1) {
                bestValue[3] = 0;
                for (int i = 0; i < items.length - 1; i++) {
                    for (int j = 0; j < items.length; j++) {
                        if (j == i + 1 || j == i)
                            continue;
                        // Change item queue
                        move(items, i, j);
                        // Pack
                        packer.execute(model);

                        model.getStatusManager().fireMessage(StatusCode.RUNNING, i + " " + j + " " + bestValue[0]);

                        // Check if there are unplanned items
                        if (model.getUnplannedItems().length < bestValue[0]) {
                            setBestMove(model, bestValue, i, j);

                            bestItems = Arrays.copyOf(items, items.length);

                            if (model.getUnplannedItems().length == 0) {
                                return;
                            }
                        }
                        // Change back
                        if (i < j) {
                            move(items, j - 1, i);
                        } else {
                            move(items, j, i + 1);
                        }
                    }
                }

                if (bestValue[3] == 1) {
                    swap(items, bestValue[1], bestValue[2]);
                }
            }

            // Make random move in search space
            perturb(items);
        }
    }
}
