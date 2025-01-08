package core.opt.construction.onetype;

import core.base.container.Container;
import core.opt.construction.strategy.Strategy;
import core.base.GRASPModel;
import core.base.item.Item;
import core.exception.Exception;
import core.opt.GRASPBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * The packer NContainerOneTypeAddPacker plans a set of items into a set of containers. It uses only one
 * container type. All items will be packed into containers, because the number of containers is
 * unlimited. The ordering of items is predefined in the first step and will not changed during the
 * pack process.
 * The packer considers only items which will be added to a container. The adding and removing will
 * not be provided.
	输入箱子尺寸，数量可以无限使用，装下所有物品所使用的最小箱子数量，在3L-CVRP场景中用于同一个route的物品装车。
 */
public class NContainerOneTypeAddPacker extends GRASPBase {

	@Override
	public void execute(GRASPModel model) throws Exception {
		Container container = model.getContainerTypes()[0].newInstance();
		String containerType = container.getContainerTypeName();
		Strategy strategy = model.getParameter().getPreferredPackingStrategy(containerType);
		SingleBinAddHeuristic heuristic = new SingleBinAddHeuristic(strategy, model.getStatusManager());

		List<Container> containerList = new ArrayList<>();
		List<Item> unpackedItems = Arrays.asList(model.getItems());
		
		int containerIdx = 0;
		while(unpackedItems.size() > 0 && hasMoreContainer(model, containerIdx++)) {
			// Create new container
			Container currentContainer = createContainer(model);

			// Try to pack all unplanned items into the current empty container. The order
			// of items is untouched by this planning. Each unplanned item will be checked.
			unpackedItems = heuristic.createLoadingPlan(unpackedItems, currentContainer);

			// Escape: When no item could be loaded into container, then stop the further planning
			if(currentContainer.getItems().size() == 0) {
				break;
			}
			
			containerList.add(currentContainer);
		}
		
		// Write created containers to model. There are no unplanned items.
		model.setContainers(containerList.toArray(new Container[0]));
		model.setUnplannedItems(unpackedItems.toArray(new Item[0]));
	}

	private boolean hasMoreContainer(GRASPModel model, int containerIdx) {
		return containerIdx < model.getParameter().getMaxNbrOfContainer();
	}

	private Container createContainer(GRASPModel model) {
		return model.getContainerTypes()[0].newInstance();
	}
}
