package core.opt.construction.strategy;

import core.Config;
import core.base.container.Container;
import core.base.item.Item;
import core.base.item.ItemType;
import core.base.position.PositionCandidate;
import core.exception.Exception;
import core.exception.ExceptionType;

import java.util.List;

/*
DSC堆叠规则：根据可堆叠物品类类型，优先堆叠，堆不下去再放车厢底面
 */

public class DscSpecialStackedStrategy extends BaseStrategy{
    private final BaseStrategy fallbackStrategy = new TouchingPerimeter();
    @Override
    public PositionCandidate choose(Item item, Container container, List<PositionCandidate> candidates) throws Exception {
        if(candidates == null || candidates.isEmpty()) {
            throw new Exception(ExceptionType.ILLEGAL_STATE, "List of positions must be not empty or null.");
        }
        if(candidates.size() == 1) {
            return candidates.get(0);
        }
        // 遍历已放置物品，判断当前物品和已放置物品是否可堆叠，可堆叠再遍历候选位置，找到该放置物品上方的位置，如果没有，放到底面
        List<Item> plannedItems = container.getItems();
        for(Item itm:plannedItems){
            if(isCompatibleWithTwoItems(Config.getInstance().getCompatibleItems(),item.getType(),itm.getType())){
                for(PositionCandidate pos:candidates){
                    if(pos.position().x()==itm.getX() && pos.position().y()==itm.getY() && pos.position().z()==itm.getZ()+ itm.getH()){
                        return pos;
                    }
                }
            }
        }
        for(PositionCandidate pos:candidates){
            if(pos.position().z()==0){
                return pos;
            }
        }

        return fallbackStrategy.choose(item, container, candidates);
    }

    private static boolean isCompatibleWithTwoItems(List<ItemType> itemsCompatibleInfo, String item1Type, String item2Type){
        if(itemsCompatibleInfo.contains(ItemType.of(item1Type, item2Type)) || itemsCompatibleInfo.contains(ItemType.of(item2Type, item1Type))){
            return true;
        }
        return false;
    }

}
