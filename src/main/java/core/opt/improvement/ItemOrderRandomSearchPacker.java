package core.opt.improvement;

import core.base.item.Item;
import core.exception.Exception;
import core.base.GRASPModel;
import core.base.monitor.StatusCode;
import core.opt.GRASPBase;
import core.opt.Packer;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;


//public class GRASProgress extends GRASPBase {
//
//    private final Packer packer;
//
//    private final Random rand = new Random();
//    private int nbrOfIterations = 10000;
//
//    public GRASProgress(Packer packer) {
//        this.packer = packer;
//    }
//
//    @Override
//    public void execute(GRASPModel model) throws Exception {
//        System.out.println("************** Multi Container Optimization ****************");
//        System.out.println("***************** Construction Phase ***********************");
//        System.out.println("************************************************************");
//        packer.execute(model);
//        model.getStatusManager().fireMessage(StatusCode.RUNNING, "Init Random search "+model.getUnplannedItems().length);
//
//        System.out.println("************** Multi Container Optimization ****************");
//        System.out.println("***************** Local Search Phase ***********************");
//        System.out.println("************************************************************");
//
//        Item[] bestItems = Arrays.copyOf(model.getItems(), model.getItems().length);
//        int bestValue = model.getUnplannedItems().length;
//        for (int k = 0; k < nbrOfIterations; k++) {
//            System.out.print(String.format("######################################## 当前迭代次数 %s ######################################## \n", k));
//            Item[] items = Arrays.copyOf(bestItems, bestItems.length);
//            // Make random move in search space, shuffle items order
//            model.setItems(perturb(items));
//
//            // Pack
//            packer.execute(model);
//
//            // Check if there are unplanned items
//            if (model.getUnplannedItems().length < bestValue) {
//                bestItems = Arrays.copyOf(model.getItems(), model.getItems().length);
//                model.getStatusManager().fireMessage(StatusCode.RUNNING, "Better " + model.getUnplannedItems().length);
//                bestValue = model.getUnplannedItems().length;
//
//                if (model.getUnplannedItems().length == 0) {
//                    break;
//                }
//            }
//        }
//
//        // Reset best solution
//        System.out.print(String.format("#################### 最后一次执行packer.execute(model) #################### \n"));
//        model.setItems(bestItems);
//        packer.execute(model);
//    }
//
//    private Item[] perturb(Item[] items) {
//        Collections.shuffle(Arrays.asList(items), rand);
//        return items;
//    }
//}


public class ItemOrderRandomSearchPacker extends GRASPBase {

    private final Packer packer;

    private final Random rand = new Random(1234);
//    private int timeLimitSeconds = 120; // 120 seconds time limit
    private int nbrOfIterations = 2000;

    public ItemOrderRandomSearchPacker(Packer packer) {
        this.packer = packer;
    }

    @Override
    public void execute(GRASPModel model) throws Exception {
        System.out.println("***************** Container Optimization *******************");
        System.out.println("***************** Construction Phase ***********************");
        System.out.println("************************************************************");
        packer.execute(model);
        model.getStatusManager().fireMessage(StatusCode.RUNNING, "Init Random search "+model.getUnplannedItems().length);

        System.out.println("***************** Container Optimization *******************");
        System.out.println("***************** Local Search Phase ***********************");
        System.out.println("************************************************************");

        Item[] bestItems = Arrays.copyOf(model.getItems(), model.getItems().length);
        int bestValue = model.getUnplannedItems().length;

//        long startTime = System.currentTimeMillis();
//        long lastPrintTime = startTime;
//        while ((System.currentTimeMillis() - startTime)/1000 < timeLimitSeconds) {
//            long currentTime = System.currentTimeMillis();
//            if ((currentTime - lastPrintTime) >= 1000) {
//                lastPrintTime = currentTime;
//                System.out.print(String.format("######################################## iteration time %s s ######################################## \n", (currentTime - startTime) / 1000));
//            }
//
//            Item[] items = Arrays.copyOf(bestItems, bestItems.length);
//            // Make random move in search space, shuffle items order
//            model.setItems(perturb(items));
//
//            // Pack
//            packer.execute(model);
//
//            // Check if there are unplanned items
//            if (model.getUnplannedItems().length < bestValue) {
//                bestItems = Arrays.copyOf(model.getItems(), model.getItems().length);
//                model.getStatusManager().fireMessage(StatusCode.RUNNING, "Better " + model.getUnplannedItems().length);
//                bestValue = model.getUnplannedItems().length;
//
//                if (model.getUnplannedItems().length == 0) {
//                    break;
//                }
//            }
//        }

        for (int k = 0; k < nbrOfIterations; k++) {
//            System.out.print(String.format("######################################## iteration number %s ######################################## \n", k+1));
            Item[] items = Arrays.copyOf(bestItems, bestItems.length);
            // Make random move in search space
            model.setItems(perturb(items));
            // Pack
            packer.execute(model);
            // Check if there are unplanned items
            if (model.getUnplannedItems().length < bestValue) {
                bestItems = Arrays.copyOf(model.getItems(), model.getItems().length);
                model.getStatusManager().fireMessage(StatusCode.RUNNING, "Better " + model.getUnplannedItems().length);
                bestValue = model.getUnplannedItems().length;

                if (model.getUnplannedItems().length == 0) {
                    break;
                }
            }
        }

        // Reset best solution
        System.out.print(String.format("#################### last execute packer.execute(model) #################### \n"));
        model.setItems(bestItems);
        packer.execute(model);
    }

    private Item[] perturb(Item[] items) {
        Collections.shuffle(Arrays.asList(items), rand);
        return items;
    }
}
