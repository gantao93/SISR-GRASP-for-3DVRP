package core.base.container.constraints;


/*
DSC场景堆叠逻辑
两个物品间是否可堆叠
 */

import core.base.container.Container;
import core.base.item.Item;
import core.base.item.ItemType;
import core.base.item.Position;

import java.util.List;


public class SpecialStackedLimitChecker {
    public static boolean check(Container container, Position pos, Item item, List<ItemType> itemsCompatibleInfo){
        if(pos.z()==0){
            return true;
        }
        // 根据container中已放置的物品，已放置物品的位置，判断当前物品item的位置pos是否可行
        List<Item> placedItems = container.getItems();
        for(Item itm:placedItems){
            // 放置的位置(itm.getX(),itm.getY(),itm.getZ())
            // 判断当前位置pos是否在某个物品上方，是则接着判断上下两物品是否可堆叠，不是返回true
            if(itm.getZ()+itm.h == pos.z()){
                // 判断当前物品itm和item属性（itm.getType(), item.getType()）是否兼容
                if(isCompatibleWithTwoItems(itemsCompatibleInfo, itm.getType(), item.getType()) && isBottomSameWithTwoItems(itm, item)){
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isCompatibleWithTwoItems(List<ItemType> itemsCompatibleInfo, String item1Type, String item2Type){
        if(itemsCompatibleInfo.contains(ItemType.of(item1Type, item2Type)) || itemsCompatibleInfo.contains(ItemType.of(item2Type, item1Type))){
            return true;
        }
        return false;
    }

    private static boolean isBottomSameWithTwoItems(Item item1, Item item2){
        if(item1.w == item2.w && item1.l == item2.l){
            return true;
        }
        return false;
    }

}



