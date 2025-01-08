package core.base.container.constraints;

import core.base.container.Container;
import core.base.container.ParameterType;
import core.base.item.Item;
import core.base.item.Position;

/**
 * Checks, whether the given item at given position would be valid, if the
 * permissible axle load is restricted.
 */
public class AxleLoadChecker {

    /**
     *
     * @return
     *   true, if item is valid at this position. Or if there is no axle load parameter.
     *   false, if item is invalid at this position.
     */
    public static boolean checkPermissibleAxleLoad(Container container, Item item, Position pos) {
        var axleLoadParameter = (AxleLoadParameter) container.getParameter().get(ParameterType.AXLE_LOAD);
        if(axleLoadParameter == null || axleLoadParameter.axleDistance() == 0)
            return true;

        var totalWeight = container.getLoadedWeight() + item.getWeight();

        // Center of truck
        var centerOfTruck = axleLoadParameter.axleDistance() / 2f;
        var centerOfLoad =
                Math.max(
                        container.getItems().stream().mapToDouble(Item::getYl).max().orElse(0),
                        pos.y() + item.l)
                        / 2f;
        var padY = Math.max(0, centerOfTruck - centerOfLoad);

        // Get current center of gravity for y(length), which is the direction of axles.
        var currentCenterOfGravityForY = container.getBaseData().getCenterOfGravityForY();
        var newCenterOfGravityForY = (currentCenterOfGravityForY + ((pos.y() + (item.l / 2f)) * item.getWeight())) / totalWeight;

        // Major formular to calculate the load at one of the 2 axles
        var loadAtSecondAxle = (totalWeight * (newCenterOfGravityForY + padY)) / axleLoadParameter.axleDistance();
        var loadAtFirstAxle = totalWeight - loadAtSecondAxle;

        return (loadAtFirstAxle <= axleLoadParameter.firstPermissibleAxleLoad()) &&
                (loadAtSecondAxle <= axleLoadParameter.secondPermissibleAxleLoad());
    }
}
