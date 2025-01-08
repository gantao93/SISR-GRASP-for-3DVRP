package core.opt.construction.onetype;

import core.base.container.Container;
import core.opt.construction.strategy.BaseStrategy;
import core.opt.construction.strategy.Strategy;
import core.base.item.Item;
import core.base.monitor.StatusCode;
import core.base.monitor.StatusManager;
import core.base.position.PositionCandidate;
import core.base.position.PositionService;
import core.exception.Exception;

import java.util.ArrayList;
import java.util.List;

/**
 * The used algorithm to add items is a greedy heuristic. It takes the order of given items
 * and places one after another to the best available position in container. The best position
 * is chosen by a strategy.
 */
public class SingleBinAddHeuristic {
	private final BaseStrategy strategy;
	private final StatusManager statusManager;

	public SingleBinAddHeuristic(Strategy s, StatusManager statusManager) {
		this.strategy = s.getStrategy();
		this.statusManager = statusManager;
	}

	public List<Item> createLoadingPlan(List<Item> items, Container container) throws Exception {
		List<Item> unplannedItemList = new ArrayList<>();

		// Reset eventual presets
		resetItems(items);

		for (Item item : items) {
			PositionCandidate insertPosition = null;

			// Check if item is allowed to this container type
			if (container.isItemAllowed(item)) {
				// Fetch existing insert positions
				List<PositionCandidate> posList = PositionService.findPositionCandidates(container, item);
				if (!posList.isEmpty()) {
					// Choose according to select strategy
					insertPosition = strategy.choose(item, container, posList);
				}
			}

			// Add item to container
			if (insertPosition != null) {
				container.add(insertPosition.item(), insertPosition.position(), insertPosition.rotationType());
			} else {
				statusManager.fireMessage(StatusCode.RUNNING, "Item " + item.index + " could not be added.");
				unplannedItemList.add(item);
			}
		}

		return unplannedItemList;
	}

	public Container getLoadedPlan(List<Item> items, Container container) throws Exception {
		List<Item> unplannedItemList = new ArrayList<>();
		List<Item> plannedItemList = new ArrayList<>();

		// Reset eventual presets
		resetItems(items);

		for (Item item : items) {
			PositionCandidate insertPosition = null;

			// Check if item is allowed to this container type
			if (container.isItemAllowed(item)) {
				// Fetch existing insert positions
				List<PositionCandidate> posList = PositionService.findPositionCandidates(container, item);
				if (!posList.isEmpty()) {
					// Choose according to select strategy
					insertPosition = strategy.choose(item, container, posList);
				}
			}

			// Add item to container
			if (insertPosition != null) {
				container.add(insertPosition.item(), insertPosition.position(), insertPosition.rotationType());

			} else {
				statusManager.fireMessage(StatusCode.RUNNING, "Item " + item.index + " could not be added.");
				unplannedItemList.add(item);
			}
		}

		return container;
	}
	private void resetItems(List<Item> items) {
		for (Item item : items) {
			item.reset();
		}
	}
}
