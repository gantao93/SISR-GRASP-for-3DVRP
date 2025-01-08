package core.base.position;

import core.base.item.Item;
import core.base.item.Position;
import core.base.item.RotationType;
import core.base.item.TwoRotationType;

/**
 * This class means a candidate where and how an item can be placed into container.
 */
public record PositionCandidate (
        Position position,
        Item item,
        RotationType rotationType
){

    public static PositionCandidate of(Position position, Item item, RotationType isRotated) {
        return new PositionCandidate(position, item, isRotated);
    }
    public static PositionCandidate of(Position position, Item item, TwoRotationType twoRotationType) {
        RotationType rotationType;
        if (twoRotationType == TwoRotationType.XYZ) {
            rotationType = RotationType.XYZ;
        } else if (twoRotationType == TwoRotationType.YXZ) {
            rotationType = RotationType.YXZ;
        } else {
            // 处理其他情况，可以抛出异常或进行默认处理
            rotationType = RotationType.XYZ; // 这里使用了默认值
        }
        return new PositionCandidate(position, item, rotationType);
    }
}
