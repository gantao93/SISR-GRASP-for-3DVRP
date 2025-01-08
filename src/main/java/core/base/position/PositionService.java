package core.base.position;

import core.Config;
import core.base.container.Container;
import core.base.container.constraints.*;
import core.base.item.*;
import util.collection.LPListMap;
import core.opt.construction.strategy.Strategy;
import java.util.ArrayList;
import java.util.List;


public class PositionService {
    /**
     * Returns all possible and valid insert positions for this item.
     */
    public static List<PositionCandidate> findPositionCandidates(Container container, Item item) {
        List<PositionCandidate> candidates = new ArrayList<>();
        // 已经放置的物品
        List<Item> occupiedItems = container.getItems();
//        System.out.println("当前item="+item);
//        System.out.println("打印所有的activePosition -> "+container.getActivePositions());
        int itemW = item.w; // 长
        int itemL = item.l; // 宽
        int itemH = item.h; //高
        int nbrOfActivePositions = container.getActivePositions().size();

        // Check weight capacity of container
        if(container.getLoadedWeight() + item.weight > container.getMaxWeight()) {
            return candidates;
        }

        int width = Integer.parseInt(String.valueOf(item.w));
        int length = Integer.parseInt(String.valueOf(item.l));
        int height = Integer.parseInt(String.valueOf(item.h));
        // X = itemW | Y = itemL | Z = itemH
        if(container.getContainerTypeName().equals("package")) {
            for (RotationType rotation: RotationType.values()) {
                switch (rotation){
                    case XYZ:
                        itemW = width;
                        itemL = length;
                        itemH = height;
                        break;
                    case XZY:
                        itemW = width;
                        itemL = height;
                        itemH = length;
                        break;
                    case YXZ:
                        itemW = length;
                        itemL = width;
                        itemH = height;
                        break;
                    case YZX:
                        itemW = length;
                        itemL = height;
                        itemH = width;
                        break;
                    case ZXY:
                        itemW = height;
                        itemL = width;
                        itemH = length;
                        break;
                    case ZYX:
                        itemW = height;
                        itemL = length;
                        itemH = width;
                }

                // For every active position
                for (int k = nbrOfActivePositions - 1; k >= 0; k--) {
                    Position pos = container.getActivePositions().get(k);
                    // Check overlapping with walls
                    if((pos.x() + itemW) > container.getWidth())
                        continue;
                    if((pos.y() + itemL) > container.getLength())
                        continue;
                    if((pos.z() + itemH) > container.getHeight())
                        continue;
                    // Check overlapping restrictions
                    if (OverlappingChecker.checkOverlapping(container, item, itemW, itemL, pos, itemH)){
                        continue;
                    }
                    // Check stacking restrictions
                    if(!StackingChecker.checkStackingRestrictions(container, pos, item, itemW, itemL)) {
                        continue;
                    }
                    // Check support restrictions
                    if(!CompleteSupportChecker.check(pos, itemW, itemL, occupiedItems, 0.7)) {
                        continue;
                    }
                    // Create RotatedPosition if this item is rotated
                    candidates.add(PositionCandidate.of(pos, item, rotation));
                }
            }
        } else {
            for (TwoRotationType rotation: TwoRotationType.values()) {
                switch (rotation){
                    case XYZ:
                        itemW = width;
                        itemL = length;
                        itemH = height;
                        break;
                    case YXZ:
                        itemW = length;
                        itemL = width;
                        itemH = height;
                }

                // For every active position
                for (int k = nbrOfActivePositions - 1; k >= 0; k--) {
                    Position pos = container.getActivePositions().get(k);
                    // Check overlapping with walls
                    if((pos.x() + itemW) > container.getWidth())
                        continue;
                    if((pos.y() + itemL) > container.getLength())
                        continue;
                    if((pos.z() + itemH) > container.getHeight())
                        continue;
                    // Check overlapping restrictions
                    if (OverlappingChecker.checkOverlapping(container, item, itemW, itemL, pos, itemH)){
                        continue;
                    }
                    // 特殊stacking restrictions
                    if(!SpecialStackedLimitChecker.check(container, pos, item, Config.getInstance().getCompatibleItems())){
                        continue;
                    }
                    // Check permissible axle load
                    if (container.getContainerTypeName().equals("vehicle")) {
                        if(!AxleLoadChecker.checkPermissibleAxleLoad(container, item, pos))
                            continue;
                    }
                    // Check support restrictions
                    if(!CompleteSupportChecker.check(pos, itemW, itemL, occupiedItems,0.7)) {
                        continue;
                    }
                    // Create RotatedPosition if this item is rotated
                    candidates.add(PositionCandidate.of(pos, item, rotation)
                    );
                }
            }
        }
        return candidates;
    }


    /**
     * If it is a stacking position (z > 0), then the immersive depth of lower items
     * must be checked. If this is the case, then the height of given item is reduced.
     */
    private static int retrieveHeight(Item item, Position pos, Container container) {
        if(pos.z() == 0) {
            return item.h;
        }

        int minImmersiveDepth = getMinImmersiveDepthOfBelow(pos, item, container);
        int newHeight = item.h - minImmersiveDepth;
        return (newHeight <= 0) ? 1 : newHeight;
    }

    private static int getMinImmersiveDepthOfBelow(Position pos, Item newItem, Container container) {
        LPListMap<Integer, Integer> zMap = container.getBaseData().getZMap();

        if(!zMap.containsKey(pos.z())) {
            return 0;
        }

        int minImmersiveDepthOfBelow = Integer.MAX_VALUE;

        List<Integer> zItems = zMap.get(pos.z());
        for (int i = zItems.size() - 1; i >= 0; i--) {
            Item lowerItem = container.getItems().get(zItems.get(i));
            if(lowerItem.zh == pos.z() &&
                    lowerItem.x < pos.x() + newItem.w &&
                    lowerItem.xw > pos.x() &&
                    lowerItem.y < pos.y() + newItem.l &&
                    lowerItem.yl > pos.y()) {
                minImmersiveDepthOfBelow = Math.min(minImmersiveDepthOfBelow, lowerItem.getImmersiveDepth());
            }
        }

        return minImmersiveDepthOfBelow;
    }
}
