package core.opt.construction.multitype;

import core.base.container.Container;
import core.base.item.Item;
import core.base.position.PositionService;
import core.exception.Exception;
import core.opt.construction.strategy.BaseStrategy;
import core.opt.construction.strategy.Strategy;
import core.base.monitor.StatusCode;
import core.base.monitor.StatusManager;
import core.base.position.PositionCandidate;

import java.util.*;
import java.util.stream.Collectors;


//public class MultiBinGreedyHeuristic {
//    private final BaseStrategy strategy;
//    private final StatusManager statusManager;
//
//    public MultiBinGreedyHeuristic(Strategy s, StatusManager statusManager) {
//        this.strategy = s.getStrategy();
//        this.statusManager = statusManager;
//    }
//
//    public List<Item> createLoadingPlan(List<Item> items, List<Container> containers) throws Exception {
//        ArrayList<PositionCandidate> occupiedPosition = new ArrayList<>();
//        // Reset eventual presets
//        resetItems(items);
//        // all items volumes
//        int unplannedItemsTotalVolume = calculateItemsTotalVolume(items);
//
//        // 初始化箱子排序，按箱子体积从大到小排序
//        List<Container> containerListOrder = containers.stream().sorted(Comparator.comparing(Container::getVolume).reversed())
//                .collect(Collectors.toList());
//        List<Item> remainingItems = items;
//        Container maxVolumeContainer = containerListOrder.get(0);
//
//        while (containerListOrder.size()!=0) {
//            // 一个箱子装不下的情形
//            int threshold = (int) Math.round(maxVolumeContainer.getVolume()/0.7);
//            if (unplannedItemsTotalVolume >= threshold) {
//                List<Item> unplannedItemList = new ArrayList<>();
//                // 选择最大的箱子装一部分物品，剩余物品同样以贪心思路
//                for (Item item : remainingItems) {
//                    PositionCandidate insertPosition = getBestInsertPosition(item, maxVolumeContainer, occupiedPosition, strategy);
//                    // Add item to container
//                    if (insertPosition != null) {
//                        maxVolumeContainer.add(insertPosition.item(), insertPosition.position(), insertPosition.rotationType());
//                    } else {
//                        statusManager.fireMessage(StatusCode.RUNNING, "Item " + item.index + " could not be added.");
//                        unplannedItemList.add(item);
//                    }
//                }
//                // 更新剩余箱子列表
//                containerListOrder.remove(maxVolumeContainer);
//                // 更新剩余箱子中最大体积箱子
//                if(!containerListOrder.isEmpty()){
//                    maxVolumeContainer = containerListOrder.get(0);
//                }
//                int remainingItemsTotalVolume = calculateItemsTotalVolume(unplannedItemList);
//                // 更新剩余物品总体积
//                unplannedItemsTotalVolume = remainingItemsTotalVolume;
//                // 更新剩余物品
//                remainingItems = unplannedItemList;
//
//            } else {
//                // 剩余物品一个箱子可以装下的情形, 用体积无法预估装不装得下
//                HashMap<Container, Integer> containerGapHashMap = new HashMap<>();
//                for (Container con : containerListOrder) {
//                    int volumeUpper = (int) Math.round(con.getVolume()/0.7);
//                    int gap = volumeUpper - unplannedItemsTotalVolume;
//                    if (gap > 0) {
//                        containerGapHashMap.put(con, gap);
//                    }
//                }
//                List<Container> containerList = containerGapHashMap.entrySet()
//                        .stream()
//                        .sorted(Map.Entry.comparingByValue())
//                        .map(Map.Entry::getKey)
//                        .collect(Collectors.toList());
//
//                containerListOrder = containerList;
//                //从最小往大一个一个试
//                List<Item> curUnplannedItems = new ArrayList<>();
//                Container container = containerListOrder.get(containerListOrder.size()-1);
//                for (Item item : remainingItems) {
//                    PositionCandidate insertPosition = getBestInsertPosition(item, container, occupiedPosition, strategy);
//                    // Add item to container
//                    if (insertPosition != null) {
//                        container.add(insertPosition.item(), insertPosition.position(), insertPosition.rotationType());
//                    } else {
//                        statusManager.fireMessage(StatusCode.RUNNING, "Item " + item.index + " could not be added.");
//                        curUnplannedItems.add(item);
//                    }
//                }
////                // 更新剩余箱子列表
//                containerListOrder.remove(container);
////                // 更新剩余箱子中最大体积箱子
////                container = containerListOrder.get(containerListOrder.size()-1);
////                // 更新remainingItems
////                remainingItems = curUnplannedItems;
////
////                for (Item item : remainingItems) {
////                    PositionCandidate insertPosition = getBestInsertPosition(item, container, occupiedPosition, strategy);
////                    // Add item to container
////                    if (insertPosition != null) {
////                        container.add(insertPosition.item(), insertPosition.position(), insertPosition.rotationType());
////                    } else {
////                        statusManager.fireMessage(StatusCode.RUNNING, "Item " + item.index + " could not be added.");
////                        curUnplannedItems.add(item);
////                    }
////                }
//                // 更新remainingItems
//                remainingItems = curUnplannedItems;
//                if(remainingItems.size() == 0){
//                    break;
//                }
//            }
//        }
//        return remainingItems;
//    }
//
//
//    private PositionCandidate getBestInsertPosition(Item item, Container container, ArrayList<PositionCandidate> occupiedPosition, BaseStrategy strategy) throws Exception {
//        // Check if item is allowed to this container type
//        PositionCandidate insertPosition = null;
//        if (container.isItemAllowed(item)) {
//            // Fetch existing insert positions
//            List<PositionCandidate> posList = PositionService.findPositionCandidates(container, item, occupiedPosition);
//            System.out.println("加入旋转状态后的所有候选位置 -> "+posList);
//            if (!posList.isEmpty()) {
//                // Choose according to select strategy
//                insertPosition = strategy.choose(item, container, posList);
//                occupiedPosition.add(insertPosition);
//                System.out.println("策略最终选择的位置 -> "+insertPosition);
//                return insertPosition;
//            }
//        }
//        return null;
//    }
//
//    private void resetItems(List<Item> items) {
//        for (Item item : items) {
//            item.reset();
//        }
//    }
//
//    private Integer calculateItemsTotalVolume(List<Item> items){
//        int itemsTotalVolume = 0;
//        for (Item item : items) {
//            itemsTotalVolume += item.volume;
//        }
//        return itemsTotalVolume;
//    }
//}


public class MultiBinGreedyHeuristic {
    private final BaseStrategy strategy;
    private final StatusManager statusManager;

    public MultiBinGreedyHeuristic(Strategy s, StatusManager statusManager) {
        this.strategy = s.getStrategy();
        this.statusManager = statusManager;
    }

    public List<Item> createLoadingPlan(List<Item> items, List<Container> containers) throws Exception {
        ArrayList<PositionCandidate> occupiedPosition = new ArrayList<>();
        resetItems(items);
        int unplannedItemsTotalVolume = calculateItemsTotalVolume(items);

        List<Container> containerListOrder = containers.stream()
                .sorted(Comparator.comparing(Container::getVolume))//.reversed())
                .collect(Collectors.toList());

        // 将items按照体积从大到小排序
        List<Item> sortedItems = items.stream()
                .sorted(Comparator.comparing(Item::getVolume).reversed())
                .collect(Collectors.toList());
        List<Item> remainingItems = new ArrayList<>(sortedItems);
//        List<Item> remainingItems = items;

        Container maxVolumeContainer = containerListOrder.get(0);

        while (!containerListOrder.isEmpty()) {
            int threshold = (int) Math.round(maxVolumeContainer.getVolume() / 0.7);
            if (unplannedItemsTotalVolume >= threshold) {
                List<Item> unplannedItemList = new ArrayList<>();
                for (Item item : remainingItems) {
                    PositionCandidate insertPosition = getBestInsertPosition(item, maxVolumeContainer, strategy);
                    if (insertPosition != null) {
                        maxVolumeContainer.add(insertPosition.item(), insertPosition.position(), insertPosition.rotationType());
                    } else {
                        statusManager.fireMessage(StatusCode.RUNNING, "Item " + item.index + " could not be added.");
                        unplannedItemList.add(item);
                    }
                }
                containerListOrder.remove(maxVolumeContainer);
//                // 选用新箱子，重新更新occupiedPosition
//                occupiedPosition = new ArrayList<>();
                if (!containerListOrder.isEmpty()) {
                    maxVolumeContainer = containerListOrder.get(0);
                }
                int remainingItemsTotalVolume = calculateItemsTotalVolume(unplannedItemList);
                unplannedItemsTotalVolume = remainingItemsTotalVolume;
                remainingItems = unplannedItemList;
            } else {
                HashMap<Container, Integer> containerGapHashMap = new HashMap<>();
                for (Container con : containerListOrder) {
                    int volumeUpper = (int) Math.round(con.getVolume() / 0.7);
                    int gap = volumeUpper - unplannedItemsTotalVolume;
                    if (gap > 0) {
                        containerGapHashMap.put(con, gap);
                    }
                }
                List<Container> containerList = containerGapHashMap.entrySet()
                        .stream()
                        .sorted(Map.Entry.comparingByValue())
                        .map(Map.Entry::getKey)
                        .collect(Collectors.toList());

                containerListOrder = containerList;
                List<Item> curUnplannedItems = new ArrayList<>();
                Container container = containerListOrder.get(containerListOrder.size() - 1);
//                // 选用新箱子，重新更新occupiedPosition
//                occupiedPosition = new ArrayList<>();
                for (Item item : remainingItems) {
                    PositionCandidate insertPosition = getBestInsertPosition(item, container, strategy);
                    if (insertPosition != null) {
                        container.add(insertPosition.item(), insertPosition.position(), insertPosition.rotationType());
                    } else {
                        statusManager.fireMessage(StatusCode.RUNNING, "Item " + item.index + " could not be added.");
                        curUnplannedItems.add(item);
                    }
                }
                containerListOrder.remove(container);
                remainingItems = curUnplannedItems;
                if (remainingItems.isEmpty()) {
                    break;
                }
            }
        }
        return remainingItems;
    }

    private PositionCandidate getBestInsertPosition(Item item, Container container, BaseStrategy strategy) throws Exception {
        PositionCandidate insertPosition = null;
        if (container.isItemAllowed(item)) {
            List<PositionCandidate> posList = PositionService.findPositionCandidates(container, item);
            if (!posList.isEmpty()) {
                insertPosition = strategy.choose(item, container, posList);
                return insertPosition;
            }
        }
        return null;
    }

    private void resetItems(List<Item> items) {
        items.forEach(Item::reset);
    }

    private Integer calculateItemsTotalVolume(List<Item> items) {
        return items.stream().mapToInt(Item::getVolume).sum();
    }
}
