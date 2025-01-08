package core.opt.construction.strategy;

import core.base.container.Container;
import core.base.item.Item;
import core.base.position.PositionCandidate;
//import core.base.position.TouchingPerimeterService;
import core.opt.construction.strategy.TouchingPerimeterService;
import core.exception.Exception;
import core.exception.ExceptionType;

import java.util.List;

/**
 * The strategy is used in construction heuristic to choose best possible insert position.
 *
 * This type of strategy chooses the position with the highest touching perimeter value.
 * This value describes the contact with walls or other items.
 * High value means much contact.
 *
 * If multiple positions have the highest touching perimeter value, then the
 * strategy HighestLowerLeft is used to decide.
 *
 */
public class TouchingPerimeter extends BaseStrategy {

	private final HighestLowerLeft fallbackStrategy = new HighestLowerLeft();

	private final boolean considerWalls;
	private final boolean considerBaseFloor;

	public TouchingPerimeter() {
		considerWalls = true;
		considerBaseFloor = true;
	}

	public TouchingPerimeter(boolean considerWalls, boolean considerBaseFloor) {
		this.considerWalls = considerWalls;
		this.considerBaseFloor = considerBaseFloor;
	}

	@Override
	public PositionCandidate choose(Item item, Container container, List<PositionCandidate> posList) throws Exception {
		if(posList == null || posList.isEmpty()) {
			throw new Exception(ExceptionType.ILLEGAL_STATE, "List of positions must be not empty or null.");
		}
		if (container.getContainerTypeName() == "vehicle"){
			List<PositionCandidate> filteredPositions = getPositionWithMinValue(
					posList,
					(PositionCandidate candidate) ->
							// Negative to find min value
							-TouchingPerimeterService.getTouchingPerimeter(
									container,
									candidate,
									1,
									true,
									false
							)
			);

			// Return found position or check further
			if(filteredPositions.size() == 1) {
				return filteredPositions.get(0);
			} else if(filteredPositions.isEmpty()) {
				throw new Exception(ExceptionType.ILLEGAL_STATE, "There must be at least one position.");
			} else {
				return fallbackStrategy.choose(item, container, filteredPositions);
			}
		} else if(container.getContainerTypeName() == "pallet"){
			List<PositionCandidate> filteredPositions = getPositionWithMinValue(
					posList,
					(PositionCandidate candidate) ->
							// Negative to find min value
							-TouchingPerimeterService.getTouchingPerimeter(
									container,
									candidate,
									1,
									false,
									true
							)
			);

			// Return found position or check further
			if(filteredPositions.size() == 1) {
				return filteredPositions.get(0);
			} else if(filteredPositions.isEmpty()) {
				throw new Exception(ExceptionType.ILLEGAL_STATE, "There must be at least one position.");
			} else {
				return fallbackStrategy.choose(item, container, filteredPositions);
			}
		} else {
			List<PositionCandidate> filteredPositions = getPositionWithMinValue(
				posList,
				(PositionCandidate candidate) ->
						// Negative to find min value
						-TouchingPerimeterService.getTouchingPerimeter(
								container,
								candidate,
								1,
								true,
								true
						)
			);

			// Return found position or check further
			if(filteredPositions.size() == 1) {
				return filteredPositions.get(0);
			} else if(filteredPositions.isEmpty()) {
				throw new Exception(ExceptionType.ILLEGAL_STATE, "There must be at least one position.");
			} else {
				return fallbackStrategy.choose(item, container, filteredPositions);
			}
		}
//		List<PositionCandidate> filteredPositions = getPositionWithMinValue(
//				posList,
//				(PositionCandidate candidate) ->
//						// Negative to find min value
//						-TouchingPerimeterService.getTouchingPerimeter(
//								container,
//								candidate,
//								1,
//								true,
//								true
//						)
//		);
//
//		// Return found position or check further
//		if(filteredPositions.size() == 1) {
//			return filteredPositions.get(0);
//		} else if(filteredPositions.isEmpty()) {
//			throw new Exception(ExceptionType.ILLEGAL_STATE, "There must be at least one position.");
//		} else {
//			return fallbackStrategy.choose(item, container, filteredPositions);
//		}
	}
}
