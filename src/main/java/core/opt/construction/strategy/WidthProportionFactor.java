package core.opt.construction.strategy;

import core.base.container.Container;
import core.base.item.Item;
import core.base.item.RotationType;
import core.base.position.PositionCandidate;
import core.exception.Exception;
import core.exception.ExceptionType;

import java.util.List;

/**
 * The strategy is used in construction heuristic to choose best possible insert position.
 *
 * This type of strategy chooses position, which width is a good proportion of container width.
 * As alternative strategy it uses the TouchingPerimeter
 *
 */
public class WidthProportionFactor extends BaseStrategy {

	private final BaseStrategy fallbackStrategy = new HighestLowerLeft();

	@Override
	public PositionCandidate choose(Item item, Container container, List<PositionCandidate> candidates) throws Exception {
		if(candidates == null || candidates.isEmpty()) {
			throw new Exception(ExceptionType.ILLEGAL_STATE, "List of positions must be not empty or null.");
		}

		if(candidates.size() == 1) {
			return candidates.get(0);
		}

		List<PositionCandidate> filteredPositions = getPositionWithMinValue(
				candidates,
				(PositionCandidate candidate) -> getDeviationOfProportion(candidate, container)
		);

		if(filteredPositions.size() == 1) {
			return filteredPositions.get(0);
		}

		if(filteredPositions.isEmpty()) {
			throw new Exception(ExceptionType.ILLEGAL_STATE, "There must be at least one position.");
		}

		return fallbackStrategy.choose(item, container, filteredPositions);
	}

	float getDeviationOfProportion(PositionCandidate candidate, Container container) {
		int conWidth = container.getWidth();
		int spaceWidth = conWidth - candidate.position().x();
		int itemWidth =  (candidate.rotationType()!= RotationType.XYZ) ? candidate.item().l : candidate.item().w;

		float proportion = spaceWidth / (float)itemWidth;
		if(proportion < 0)
			return Float.MAX_VALUE;

		int bestProportion = (int) proportion;
		float deviation = Math.abs(proportion - bestProportion);

		return Math.round(deviation * 100);
	}
}
