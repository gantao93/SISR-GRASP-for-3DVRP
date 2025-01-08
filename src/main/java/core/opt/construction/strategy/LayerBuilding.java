package core.opt.construction.strategy;

import core.base.container.Container;
import core.base.item.Item;
import core.base.item.Position;
import core.base.position.PositionCandidate;
import core.exception.Exception;
import core.exception.ExceptionType;

import java.util.List;


public class LayerBuilding extends BaseStrategy {
    @Override
    public PositionCandidate choose(Item item, Container container, List<PositionCandidate> candidates) throws Exception {
        if(candidates == null || candidates.isEmpty()) {
            throw new Exception(ExceptionType.ILLEGAL_STATE, "List of positions must be not empty or null.");
        }

        List<PositionCandidate> filteredPositions = getPositionWithMinValue(
                candidates,
                this::getDistanceL
        );

        if(filteredPositions.size() > 1) {
            filteredPositions = getPositionWithMinValue(
                    filteredPositions,
                    this::getDistance
            );
        }

        if(filteredPositions.isEmpty()) {
            throw new Exception(ExceptionType.ILLEGAL_STATE, "There must be at least one position.");
        }
        return filteredPositions.get(0);
    }

    float getDistance(PositionCandidate candidate) {
        if(candidate == null) {
            return Float.MAX_VALUE;
        }

        Position p = candidate.position();
        return (float)Math.pow(
                (p.x() * p.x()) +
                        (p.y() * p.y()) +
                        (p.z() * p.z()), 0.5
        );
    }

//    float getDistanceL(PositionCandidate p) {
//        return p == null ? Float.MAX_VALUE : (float) p.position().x() * -1;
//    }
    float getDistanceL(PositionCandidate candidate) {
        if (candidate == null) {
            return Float.MAX_VALUE;
        }

        if (candidate.position().z() == 0) {
            // 优先选择z=0的位置
            return -candidate.position().z();
        }

        // 其他位置的距离设为正无穷大
        return Float.MAX_VALUE;
    }
}
