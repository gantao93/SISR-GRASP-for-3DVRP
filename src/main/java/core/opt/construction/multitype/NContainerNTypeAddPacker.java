package core.opt.construction.multitype;

import core.base.container.Container;
import core.base.item.Item;
import core.exception.Exception;
import core.opt.construction.strategy.Strategy;
import core.base.GRASPModel;
import core.opt.Packer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This packer puts the items in a sequence for each container type into single container.
 * Items will only be added to a container.
 */
public class NContainerNTypeAddPacker implements Packer {

	@Override
	public void execute(GRASPModel model) throws Exception {

		Container container = model.getContainerTypes()[0].newInstance();
		String containerType = container.getContainerTypeName();

		Strategy strategy = model.getParameter().getPreferredPackingStrategy(containerType);
//		MultiBinAddHeuristic heuristic = new MultiBinAddHeuristic(strategy, model.getStatusManager());
		MultiBinGreedyHeuristic heuristic = new MultiBinGreedyHeuristic(strategy, model.getStatusManager());

		List<Container> containers = new ArrayList<>();
		List<Item> unplannedItems = Arrays.asList(model.getItems());
		while(!unplannedItems.isEmpty()) {
			// Create one container per type
			List<Container> newContainers = getContainers(model);
			// Try to insert items in containers
			List<Item> restItems = heuristic.createLoadingPlan(unplannedItems, newContainers);

			containers.addAll(newContainers);
			// Rest containers will go into next round
			unplannedItems = restItems;
		}

		// Put result into model
		model.setContainers(containers.toArray(new Container[0]));
		model.setUnplannedItems(unplannedItems.toArray(new Item[0]));
	}

	private List<Container> getContainers(GRASPModel model) {
		return Arrays.stream(model.getContainerTypes())
				.map(Container::newInstance)
				.collect(Collectors.toList());
	}
}
