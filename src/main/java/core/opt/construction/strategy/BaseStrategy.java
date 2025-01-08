package core.opt.construction.strategy;

import core.base.container.Container;
import core.base.item.Item;
import core.base.position.PositionCandidate;
import core.exception.Exception;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;


public abstract class BaseStrategy {

	public abstract PositionCandidate choose(Item item, Container container, List<PositionCandidate> posList) throws Exception;

	protected List<PositionCandidate> getPositionWithMinValue(List<PositionCandidate> candidates,
															  Function<PositionCandidate, Float> positionValue) {
		if(candidates == null) {
			return new ArrayList<>();
		}

		if(candidates.size() <= 1) {
			return candidates;
		}

		float[] distances = new float[candidates.size()];
		for (int i = distances.length - 1; i >= 0; i--) {
			distances[i] = positionValue.apply(candidates.get(i));
		}

		float minValue = Float.MAX_VALUE;
		for (int i = candidates.size() - 1; i >= 0; i--) {
			minValue = Math.min(minValue, distances[i]);
		}

		// Search all positions with max value
		List<PositionCandidate> filteredPositions = new ArrayList<>();
		for (int i = distances.length - 1; i >= 0; i--) {
			if(distances[i] == minValue) {
				filteredPositions.add(candidates.get(i));
			}
		}
		return filteredPositions;
	}
}
