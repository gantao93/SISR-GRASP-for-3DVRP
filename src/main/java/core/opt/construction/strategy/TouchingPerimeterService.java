package core.opt.construction.strategy;

import core.base.container.Container;
import core.base.item.Item;
import core.base.item.Position;
import core.base.item.RotationType;
import core.base.position.PositionCandidate;

import java.util.ArrayList;
import java.util.List;


public class TouchingPerimeterService {

    public static float getTouchingPerimeter(
            Container container,
            PositionCandidate candidate,
            int itemTouchValue,
            boolean considerWalls,
            boolean considerBaseFloor) {
        Item item = candidate.item();
        Position pos = candidate.position();

        int value = 0;
        int w = item.w;
        int l = item.l;
        int h = item.h;
//        if(candidate.isRotated()) {
//            w = item.l;
//            l = item.w;
//        }
        if(candidate.rotationType()== RotationType.YXZ) {
            w = item.l;
            l = item.w;
        } else if(candidate.rotationType()== RotationType.XZY){
            w = item.h;
            h = item.w;
        } else if (candidate.rotationType()==RotationType.YZX) {
            l = item.w;
            w = item.h;
            h = item.l;
        } else if (candidate.rotationType()==RotationType.ZXY) {
            l = item.h;
            w = item.l;
            h = item.w;
        } else if (candidate.rotationType() == RotationType.ZYX) {
            l = item.h;
            h = item.l;
        }


        int xw = pos.x() + w;
        int yl = pos.y() + l;
        int zh = pos.z() + h;

        List<Integer> list;

        // x-Achse
        {
            if(pos.x() == 0)
                // If walls must be considers, full side area is added
                if(considerWalls)
                    value += h * l;

            if(xw == container.getWidth())
                // If walls must be considers, full side area is added
                if(considerWalls)
                    value += h * l;

            List<Integer> xItemList = new ArrayList<>();
            list = container.getBaseData().getXMap().get(pos.x()); if(list != null) xItemList.addAll(list);
            list = container.getBaseData().getXMap().get(xw); if(list != null) xItemList.addAll(list);

            if(xItemList.size() > 0) {
                // Check all items, which touches pos.x
                for (int j = xItemList.size() - 1; j >= 0; j--) {
                    int index = xItemList.get(j);
                    Item i = container.getItems().get(index);

                    if(i.xw == pos.x() || i.x == xw) {
                        // Check length and height
                        if(i.y > yl || i.yl < pos.y())
                            continue;
                        if(i.z > zh || i.zh < pos.z())
                            continue;

                        // If items touch themselves, calculate the cutting plane
                        int yLength = Math.min(yl, i.yl) - Math.max(i.y, pos.y());
                        int zLength = Math.min(zh, i.zh) - Math.max(i.z, pos.z());
                        value += yLength * zLength * itemTouchValue;
                    }
                }
            }
        }

        // Y-Achse
        {
            if(pos.y() == 0)
                // If walls must be considers, full side area is added
                if(considerWalls)
                    value += h * w;
            if(yl == container.getLength())
                // If walls must be considers, full side area is added
                if(considerWalls)
                    value += h * w;

            List<Integer> yItemList = new ArrayList<>();
            list = container.getBaseData().getYMap().get(pos.y()); if(list != null) yItemList.addAll(list);
            list = container.getBaseData().getYMap().get(yl); if(list != null) yItemList.addAll(list);

            if(yItemList.size() > 0) {
                // Check all items, which touches pos.y
                for (int j = yItemList.size() - 1; j >= 0; j--) {
                    int index = yItemList.get(j);
                    Item i = container.getItems().get(index);

                    if(i.yl == pos.y() || i.y == yl) {
                        // Check width and height
                        if(i.x > xw || i.xw < pos.x())
                            continue;
                        if(i.z > zh || i.zh < pos.z())
                            continue;

                        // If items touch themselves, calculate the cutting plane
                        int xLength = Math.min(xw, i.xw) - Math.max(i.x, pos.x());
                        int zLength = Math.min(zh, i.zh) - Math.max(i.z, pos.z());
                        value += xLength * zLength * itemTouchValue;
                    }
                }
            }
        }

        // Z-Achse
        {
            if(pos.z() == 0)
                // If walls must be considers, full side area is added
                if(considerBaseFloor)
                    value += w * l;

            if(zh == container.getHeight())
                // If walls must be considers, full side area is added
                if(considerWalls)
                    value += w * l;

            List<Integer> zItemList = new ArrayList<>();
            list = container.getBaseData().getZMap().get(pos.z()); if(list != null) zItemList.addAll(list);
            list = container.getBaseData().getZMap().get(zh); if(list != null) zItemList.addAll(list);

            // Check all items, which touches pos.z
            for (int j = zItemList.size() - 1; j >= 0; j--) {
                int index = zItemList.get(j);
                Item i = container.getItems().get(index);

                if(i.zh == pos.z() || i.z == zh) {
                    // Check length and width
                    if(i.y > yl || i.yl < pos.y())
                        continue;
                    if(i.x > xw || i.xw < pos.x())
                        continue;

                    // If items touch themselves, calculate the cutting plane
                    int yLength = Math.min(yl, i.yl) - Math.max(i.y, pos.y());
                    int xLength = Math.min(xw, i.xw) - Math.max(i.x, pos.x());
                    value += yLength * xLength * itemTouchValue;
                }
            }
        }

        return value;
    }
}
