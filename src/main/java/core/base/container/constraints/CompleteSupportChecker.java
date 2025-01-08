package core.base.container.constraints;

import core.base.item.Item;
import core.base.item.Position;
import core.base.position.PositionCandidate;

import java.util.ArrayList;
import java.util.List;

public class CompleteSupportChecker {

    public static boolean check(Position position, int itemW, int itemL, List<Item> occupiedItems, double supportRtio) {
        if (position.z() == 0) {
            return true;
        }
        long supportArea = 0l;
        for(Item itm:occupiedItems) {
            if (itm == null) {break;}
            if (position.z() == itm.getZ()+itm.getH()) {
                supportArea += (Math.min(itm.getX()+itm.getW(), position.x()+itemW)
                        - Math.max(itm.getX(), position.x()))
                        * (Math.min(itm.getY()+itm.getL(), position.y()+itemL)
                        - Math.max(itm.getY(), position.y()));
            }

            if ((double) supportArea / (double) (itemW * itemL) >= supportRtio) {
                return true;
            }
        }
        return false;
    }

}
