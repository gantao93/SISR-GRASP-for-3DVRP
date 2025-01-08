package core.base.container.constraints;

import core.base.container.AddContainer;
import core.base.container.Container;
import util.collection.IndexedArrayList;
import core.base.item.Item;
import core.base.item.Position;
import core.base.item.Space;

import java.util.List;


public class OverlappingChecker {
//    public static boolean checkOverlapping(Container container, Item item, int itemW, int itemL, Position pos, int itemH) {
//        if (container instanceof AddContainer) {
//            return checkOverlappingWithSpaces((AddContainer) container, pos, itemW, itemL, itemH);
//        } else {
//            return checkOverlappingWithItems(container, item, itemW, itemL, pos, itemH);
//        }
//    }
    // 修改于0627

    public static boolean checkOverlapping(Container container, Item item, int itemW, int itemL, Position pos, int itemH) {
        if (container instanceof AddContainer) {
//            System.out.println("AddContainer约束 "+container.getContainerTypeName());
            return checkOverlappingWithSpaces((AddContainer) container, pos, itemW, itemL, itemH);
        } else {
            if (container.getContainerTypeName() == "vehicle"){
                System.out.println("装车-先进后出约束 "+container.getContainerTypeName());
                return checkOverlappingWithItemsVehicle(container, item, itemW, itemL, pos, itemH);
            } else {
                return checkOverlappingWithItems(container, item, itemW, itemL, pos, itemH);
            }
        }
    }
    // add 0628 TODO
//    public static boolean checkOverlapping(Container container, Item item, int itemW, int itemL, Position pos, int itemH) {
//        if (container.getContainerTypeName().equals("vehicle")){
////            System.out.println("装车-先进后出约束 "+container.getContainerTypeName());
//            return checkOverlappingWithItemsVehicle(container, item, itemW, itemL, pos, itemH) && checkOverlappingWithSpaces((AddContainer) container, pos, itemW, itemL, itemH);
//        } else {
//            return checkOverlappingWithItems(container, item, itemW, itemL, pos, itemH) && checkOverlappingWithSpaces((AddContainer) container, pos, itemW, itemL, itemH);
//        }
//
//    }
    /**
     * 检查给定容器中的物品是否与指定位置上的物品重叠。它接受一个容器对象、一个物品对象、物品的宽度、长度、位置和高度作为参数。它遍历容器中的所有物品，
     * 并检查每个物品是否与指定位置上的物品重叠。如果存在重叠，函数将返回true，否则返回false
     */
    private static boolean checkOverlappingWithItems(Container container, Item item, int itemW, int itemL, Position pos, int itemH) {
        IndexedArrayList<Item> items = (IndexedArrayList<Item>) container.getItems();

        for (int idx = items.length() - 1; idx >= 0; idx--) {
            Item otherItem = items.get(idx);
            if (otherItem == null)
                continue;

            if (otherItem.x < (pos.x() + itemW) && otherItem.xw > pos.x() &&
                    otherItem.y < (pos.y() + itemL) && otherItem.yl > pos.y() &&
                    otherItem.z < (pos.z() + itemH) && otherItem.zh > pos.z()
            ) {
                return true;
            }
        }
        return false;
    }

    /*
    首先调用checkOverlappingWithItems函数来检查物品是否与容器中的其他物品重叠。如果存在重叠，函数将立即返回true。否则，它继续遍历容器中的所有物品，
    并使用LastInFirstOutChecker.checkLIFO函数检查是否满足"last in, first out"条件。如果满足条件，函数将返回true，否则返回false
     */
    private static boolean checkOverlappingWithItemsVehicle(Container container, Item item, int itemW, int itemL, Position pos, int itemH) {
        boolean b = OverlappingChecker.checkOverlappingWithItems(container, item, itemW, itemL, pos, itemH);
        IndexedArrayList<Item> items = (IndexedArrayList<Item>) container.getItems();
        if (b) {
            return true;
        }
        for (int idx = items.length() - 1; idx >= 0; idx--) {
            Item otherItem = items.get(idx);
            // check last in, first out condition
            if (LastInFirstOutChecker.checkLIFO(container, otherItem, pos, item, itemW)) {
                return true;
            }
        }

        return false;
    }

    /*
    用于检查给定容器中的空间是否足够容纳指定大小的物品。它接受一个AddContainer对象、一个位置、物品的宽度、长度和高度作为参数。函数首先获取容器中与指定位置相关的所有空间。
    然后，它遍历这些空间，并检查每个空间是否足够容纳指定大小的物品。如果存在足够的空间，函数将返回false，否则返回true
     */
    private static boolean checkOverlappingWithSpaces(AddContainer container, Position pos, int itemW, int itemL, int itemH) {
        List<Space> spaces = container.getSpace(pos);
//        System.out.println("checkOverlappingWithSpaces 中的pos "+pos);
//        System.out.println("checkOverlappingWithSpaces 中的space "+spaces);
        // If item is fitting into one of the spaces, then it is okay.
//        // TODO 删除
//        if(spaces == null){
//            return false;
//        }
        for (Space space : spaces) {
            // Is item fitting into space
            if (space.l() >= itemL &&
                    space.w() >= itemW &&
                    space.h() >= itemH)
                return false;
        }
        return true;
    }
}
