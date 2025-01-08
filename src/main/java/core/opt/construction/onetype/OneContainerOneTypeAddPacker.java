package core.opt.construction.onetype;

import core.base.container.Container;
import core.base.container.ContainerType;
import core.base.item.Item;
import core.exception.Exception;
import core.opt.construction.strategy.Strategy;
import core.base.GRASPModel;
import core.opt.Packer;

import java.util.Arrays;
import java.util.List;

/**
 * This packer puts the items in a sequence into one container with one container type.
 * Items will only be added to a container.
 */
public class OneContainerOneTypeAddPacker implements Packer {

	@Override
	public void execute(GRASPModel model) throws Exception {
		Container container = model.getContainerTypes()[0].newInstance();
		String containerType = container.getContainerTypeName();
		Strategy strategy = model.getParameter().getPreferredPackingStrategy(containerType);

		List<Item> unplannedItemList = new SingleBinAddHeuristic(strategy, model.getStatusManager())
				.createLoadingPlan(
						Arrays.asList(model.getItems()),
						container
				);

		// Put result into model
		model.setContainers(new Container[]{container});
		model.setUnplannedItems(unplannedItemList.toArray(new Item[0]));
	}

}
