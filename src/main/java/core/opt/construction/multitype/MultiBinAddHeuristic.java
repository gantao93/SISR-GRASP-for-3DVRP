package core.opt.construction.multitype;

import core.base.container.Container;
import core.base.fleximport.ContainerData;
import core.base.item.Item;
import core.base.position.PositionService;
import core.exception.Exception;
import core.opt.construction.strategy.BaseStrategy;
import core.opt.construction.strategy.Strategy;
import core.base.monitor.StatusCode;
import core.base.monitor.StatusManager;
import core.base.position.PositionCandidate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MultiBinAddHeuristic {

    private final BaseStrategy strategy;
    private final StatusManager statusManager;

    public MultiBinAddHeuristic(Strategy s, StatusManager statusManager) {
        this.strategy = s.getStrategy();
        this.statusManager = statusManager;
    }

    public List<Item> createLoadingPlan(List<Item> items, List<Container> containers) throws Exception {
        List<Item> unplannedItems = new ArrayList<>();
        ArrayList<PositionCandidate> occupiedPosition = new ArrayList<>();
        // Reset eventual presets
        resetItems(items);

        for (Item item : items) {
            List<ContainerPosition> containerPositions = getBestContainerPositions(item, containers, occupiedPosition, strategy);

            // Add item to container
            if (!containerPositions.isEmpty()) {
                insertIntoContainer(containerPositions);
            } else {
                statusManager.fireMessage(StatusCode.RUNNING, "Item " + item.index + " could not be added.");
                unplannedItems.add(item);
            }
        }

        return unplannedItems;
    }

    private List<ContainerPosition> getBestContainerPositions(Item item, List<Container> containers, ArrayList<PositionCandidate> occupiedPosition, BaseStrategy strategy) throws Exception {
        List<ContainerPosition> containerPositions = new ArrayList<>();
        // TODO 待优化 遍历箱子，每个箱子执行单箱算法
        // 按箱子体积从大到小排序
        List<Container> containerListOrder = containers.stream().sorted(Comparator.comparing(Container::getVolume))
                .collect(Collectors.toList());
        for (Container container : containerListOrder) {
            PositionCandidate bestPosition = getBestInsertPosition(item, container, strategy);
            if(bestPosition != null) {
                item.setRotationType(bestPosition.rotationType());
                containerPositions.add(new ContainerPosition(container, bestPosition));
            }
        }

        return containerPositions;
    }

    private PositionCandidate getBestInsertPosition(Item item, Container container, BaseStrategy strategy) throws Exception {
        // Check if item is allowed to this container type
        PositionCandidate insertPosition = null;
        if (container.isItemAllowed(item)) {
            // Fetch existing insert positions
            List<PositionCandidate> posList = PositionService.findPositionCandidates(container, item);
//            System.out.println("加入旋转状态后的所有候选位置 -> "+posList);
            if (!posList.isEmpty()) {
                // Choose according to select strategy
                insertPosition = strategy.choose(item, container, posList);
//                System.out.println("策略最终选择的位置 -> "+insertPosition);
                return insertPosition;
            }
        }

        return null;
    }

    // TODO 待优化，高度优先（选择高度最小的），其次Width(选择宽度最小的)，最后Lengh(最小的)
    private void insertIntoContainer(List<ContainerPosition> containerPositions) {
        // Simply take first - Could be improved later
        Collections.sort(containerPositions, new Comparator<ContainerPosition>() {
            @Override
            public int compare(ContainerPosition cp1, ContainerPosition cp2) {
                // Compare by height first (ascending order)
                int heightComparison = Integer.compare(
                        cp1.getPosition().position().z(),
                        cp2.getPosition().position().z()
                );
                if (heightComparison != 0) {
                    return heightComparison;
                }
                // If heights are the same, compare by width (ascending order)
                int widthComparison = Integer.compare(
                        cp1.getPosition().position().x(),
                        cp2.getPosition().position().x()
                );
                if (widthComparison != 0) {
                    return widthComparison;
                }
                // If heights and widths are the same, compare by length (ascending order)
                return Integer.compare(
                        cp1.getPosition().position().y(),
                        cp2.getPosition().position().y()
                );
            }
        });

        ContainerPosition containerPosition = containerPositions.get(0);
        containerPosition.getContainer().add(
                containerPosition.getPosition().item(),
                containerPosition.getPosition().position(),
                containerPosition.getPosition().rotationType()
        );
    }

    private void resetItems(List<Item> items) {
        for (Item item : items) {
            item.reset();
        }
    }

}
