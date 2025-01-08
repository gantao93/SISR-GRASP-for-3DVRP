package core.opt.improvement;

import core.base.GRASPModel;
import core.base.item.Item;
import core.base.monitor.StatusCode;
import core.exception.Exception;
import core.opt.GRASPBase;
import core.opt.Packer;

import java.util.*;


/*
物品增加一个组的属性，组表示物品所属的客户节点
多个组的物品一起装箱，组间按固定顺序（节点访问顺序）排序，组内采用shffle随机顺序
 */

public class ItemOrderByGroupRandomSearchPacker extends GRASPBase {

    private final Packer packer;

    private final Random rand = new Random(1234);
    private int nbrOfIterations = 3000;

    public ItemOrderByGroupRandomSearchPacker(Packer packer) {
        this.packer = packer;
    }

    @Override
    public void execute(GRASPModel model) throws Exception {
        System.out.println("***************** Construction Phase ***********************");
        packer.execute(model);
        model.getStatusManager().fireMessage(StatusCode.RUNNING, "Init Random search "+model.getUnplannedItems().length);
        System.out.println("***************** Local Search Phase-ItemOrderByGroupRandomSearch ***********************");

        Item[] bestItems = Arrays.copyOf(model.getItems(), model.getItems().length);
        int bestValue = model.getUnplannedItems().length;

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
//        System.out.print(String.format("#################### last execute packer.execute(model) #################### \n"));
        model.setItems(bestItems);
        packer.execute(model);
    }

    private Item[] perturb(Item[] items){
        // 组间按固定顺序，组内shffle
        Arrays.sort(items, Comparator.comparingInt(item -> item.getGroup()));
        // 找到每个 group 的起始和结束索引，然后对每个 group 内部进行 shuffle
        int start = 0;
        while (start < items.length) {
            int end = start + 1;
            while (end < items.length && items[end].getGroup() == items[start].getGroup()) {
                end++;
            }
            shuffleGroup(items, start, end);
            start = end;
        }

        return items;
    }

    private void shuffleGroup(Item[] items, int start, int end) {
        // 使用 Collections.shuffle 对数组中的元素进行 shuffle
        List<Item> itemList = Arrays.asList(Arrays.copyOfRange(items, start, end));
        Collections.shuffle(itemList, rand);
        for (int i = start; i < end; i++) {
            items[i] = itemList.get(i - start);
        }
    }

//    private Item[] perturb(Item[] items) {
//        Collections.shuffle(Arrays.asList(items), rand);
//        return items;
//    }

}
