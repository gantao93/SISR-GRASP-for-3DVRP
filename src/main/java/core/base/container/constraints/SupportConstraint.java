package core.base.container.constraints;

import core.base.container.Container;
import core.base.item.Position;
import core.base.item.RotationType;
import core.base.item.TwoRotationType;
import core.base.position.PositionCandidate;
import core.base.item.Item;

import java.util.List;


public class SupportConstraint {
    public static boolean check(Container container, Item item, Position pos, RotationType rotation, List<PositionCandidate> candidates, double threshold) {
        // Calculate the dimensions of the item based on its rotation
        int itemW = item.w; // 长
        int itemL = item.l; // 宽
        int itemH = item.h; //高
        int width = item.w;
        int length = item.l;
        int height = item.h;
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

        // Calculate the support area of the item at the given position
        int supportArea = calculateSupportArea(container, itemW, itemL, pos);

        // Calculate the total area of the item's base (e.g., width * length)
        int itemBaseArea = itemW * itemL;

        // Calculate a support ratio (support area / item base area)
        double supportRatio = (double) supportArea / itemBaseArea;

        // Check if the support ratio meets the threshold requirement
        if (supportRatio >= threshold) {
            // The item is adequately supported at this position and rotation
            return true;
        } else {
            // The support constraint is not met
            return false;
        }
    }

    public static boolean check(Container container, Item item, Position pos, TwoRotationType rotation, List<PositionCandidate> candidates, double threshold) {
        // Calculate the dimensions of the item based on its rotation
        int itemW = item.w; // 长
        int itemL = item.l; // 宽
        int itemH = item.h; //高
        int width = item.w;
        int length = item.l;
        int height = item.h;
        switch (rotation){
            case XYZ:
                itemW = width;
                itemL = length;
                break;
            case YXZ:
                itemW = length;
                itemL = width;
        }
        // Calculate the support area of the item at the given position
        int supportArea = calculateSupportArea(container, itemW, itemL, pos);
        // Calculate the total area of the item's base (e.g., width * length)
        int itemBaseArea = itemW * itemL;
        // Calculate a support ratio (support area / item base area)
        double supportRatio = (double) supportArea / itemBaseArea;
        // Check if the support ratio meets the threshold requirement
        if (supportRatio >= threshold) {
            // The item is adequately supported at this position and rotation
            return true;
        } else {
            // The support constraint is not met
            return false;
        }
    }

    // Calculate the support area based on the item's dimensions and position
    private static int calculateSupportArea(Container container, int itemW, int itemL, Position pos) {
        // Get the position's coordinates
        int posX = pos.x();
        int posY = pos.y();

        // Calculate the maximum supported width and length
        int supportedWidth = Math.min(itemW, container.getWidth() - posX);
        int supportedLength = Math.min(itemL, container.getLength() - posY);

        // Calculate the support area
        int supportArea = supportedWidth * supportedLength;

        return supportArea;
    }

}
