package core.base.container.constraints;

import core.base.container.Container;
import core.base.container.ParameterType;
import core.base.item.Item;
import core.base.container.AddRemoveContainer;
import core.base.item.Position;


public class LastInFirstOutChecker {
    public static boolean checkLIFO(Container container, Item otherItem, Position pos, Item newItem, int itemW) {

        if(!(container instanceof AddRemoveContainer)) {
            return false;
        }

        float lifoImportance = (Float)container.getParameter().get(ParameterType.LIFO_IMPORTANCE);

        if(lifoImportance == 1) {
            if(otherItem.yl <= pos.y() && otherItem.x < (pos.x() + itemW) && otherItem.xw > pos.x()) {
                return newItem.unLoadingLoc > otherItem.unLoadingLoc;
            }
        }
        return false;

    }
}
