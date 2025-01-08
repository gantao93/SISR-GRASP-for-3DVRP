package core.opt.construction.multitype;

import core.base.GRASPModel;
import core.base.container.Container;
import core.base.item.Item;
import core.exception.Exception;
import core.opt.Packer;
import core.opt.construction.onetype.SingleBinAddHeuristic;
import core.opt.construction.strategy.Strategy;

import java.util.*;
import java.util.stream.Collectors;

public class NContainerNTypeMinCostPacker implements Packer {
    @Override
    public void execute(GRASPModel model) throws Exception {
        // 记录每个箱子装载的物品列表
        HashMap<Container, HashSet<Item>> containerItemsHashMap = new HashMap<Container, HashSet<Item>>();
        // 箱型列表
        List<Container> containers = getContainers(model);
        // 物品列表
        List<Item> items = Arrays.asList(model.getItems());
        HashSet<Item> itemsHashSet = new HashSet<>(items);

        Container container = model.getContainerTypes()[0].newInstance();
        String containerType = container.getContainerTypeName();
        Strategy strategy = model.getParameter().getPreferredPackingStrategy(containerType);

        HashSet<Item> plannedItems = new HashSet<>();
        // 遍历每个箱子，获取每个箱子装载物品列表
        for (Container con : containers) {
            System.out.println("正在处理箱子"+con.getLength());
            Container plannedItemList = new SingleBinAddHeuristic(strategy, model.getStatusManager())
                    .getLoadedPlan(
                            items,
                            con
                    );

            List<Item> loadItems = plannedItemList.getItems();
            HashSet<Item> loadItemsHashSet = new HashSet<>(loadItems);
            containerItemsHashMap.put(con, loadItemsHashSet);
            plannedItems.addAll(loadItemsHashSet);
        }
        //存放已选择的箱子的集合
        ArrayList<Container> selects = new ArrayList<Container>();
        //定义一个临时的集合，在遍历的过程中，存放箱子装载的物品和当前还没有未装载物品的交集
        HashSet<Item> tempSet = new HashSet<Item>();
        Container maxKey = null;
        // 所有物品能被装下
        while (plannedItems.size() != 0) {
            maxKey = null;
            for (Container containerKey : containerItemsHashMap.keySet()) {
                //先将交集清空
                tempSet.clear();
                HashSet<Item> itemsSet = containerItemsHashMap.get(containerKey);
                //将其对应的未装载的物品添加到tempSet
                tempSet.addAll(itemsSet);
                tempSet.retainAll(itemsHashSet);
                if (tempSet.size() > 0 && (maxKey == null || tempSet.size() > containerItemsHashMap.get(maxKey).size())) {
                    maxKey = containerKey;
                }

            }
            if (maxKey != null) {
                selects.add(maxKey);
                plannedItems.removeAll(containerItemsHashMap.get(maxKey));
            }

        }
        List<Container> plannedContainers = new ArrayList<>(selects);
        // 所有物品能够被装下
        if (plannedItems.size() == itemsHashSet.size()) {
            List<Item> unplannedItems = new ArrayList<>();
            // Put result into model
            model.setContainers(plannedContainers.toArray(new Container[0]));
            model.setUnplannedItems(unplannedItems.toArray(new Item[0]));
        } else {
            HashSet<Item> itemsDifference = new HashSet<>(itemsHashSet);
            itemsDifference.removeAll(plannedItems);
            List<Item> unplannedItems = new ArrayList<>(itemsDifference);
            // Put result into model
            model.setContainers(plannedContainers.toArray(new Container[0]));
            model.setUnplannedItems(unplannedItems.toArray(new Item[0]));
        }

    }

    private List<Container> getContainers(GRASPModel model) {
        return Arrays.stream(model.getContainerTypes())
                .map(Container::newInstance)
                .collect(Collectors.toList());
    }

}
