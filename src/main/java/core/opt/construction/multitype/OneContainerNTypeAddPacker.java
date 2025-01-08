package core.opt.construction.multitype;

import core.base.container.Container;
import core.base.item.Item;
import core.exception.Exception;
import core.opt.construction.strategy.Strategy;
import core.base.GRASPModel;
import core.opt.Packer;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This packer puts the items in a sequence for each container type into single container.
 * Items will only be added to a container.
 */
public class OneContainerNTypeAddPacker implements Packer {

	@Override
	public void execute(GRASPModel model) throws Exception {
		Container container = model.getContainerTypes()[0].newInstance();
		String containerType = container.getContainerTypeName();
		Strategy strategy = model.getParameter().getPreferredPackingStrategy(containerType);

		// Create one container per type
		List<Container> containers = getContainers(model);
		// Try to insert items in containers
//		List<Item> unplannedItems = new MultiBinAddHeuristic(strategy, model.getStatusManager())
//				.createLoadingPlan(Arrays.asList(model.getItems()), containers);
		List<Item> unplannedItems = new MultiBinGreedyHeuristic(strategy, model.getStatusManager())
				.createLoadingPlan(Arrays.asList(model.getItems()), containers);

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
