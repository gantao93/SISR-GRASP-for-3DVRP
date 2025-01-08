package core.base.position;

import core.base.container.Container;
import core.base.item.Position;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class MaxWidthAreaService {
    /**
     * 计算容器中最大的空闲区域的面积。
     *
     * @param container 包含位置信息的容器对象
     * @return 最大空闲区域的面积
     */
    public float getMaxEmptyArea(Container container) {
        // 获取容器中的位置列表，并按 x 坐标升序排序
        List<Position> posList = new ArrayList<>(container.getActivePositions());
        posList.sort(Comparator.comparingInt(Position::x));

        // 计算相邻两个位置之间的空闲区域的面积，并累加到总面积中
        float area = 0;
        for (int i = 1; i < posList.size(); i++) {
            Position prevPos = posList.get(i - 1);
            Position pos = posList.get(i);
            area += (pos.x() - prevPos.x()) * (container.getLength() - prevPos.y());
        }

        // 计算最后一个位置到容器右侧边界的空闲区域的面积，并累加到总面积中
        Position lastPos = posList.get(posList.size() - 1);
        area += (container.getWidth() - lastPos.x()) * container.getLength();

        return area;
    }
}
