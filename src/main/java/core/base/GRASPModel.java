package core.base;

import core.base.container.Container;
import core.base.fleximport.ContainerData;
import core.base.item.Item;
import core.base.monitor.StatusManager;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GRASPModel {

	protected Item[] items;

	/* Only one object per container type, solution will copy these objects */
	protected final Container[] containerTypes;

	protected final GRASPParameter parameter;
	private StatusManager statusManager;

	/* Result objects */
	private Container[] containers = new Container[0];
	private Item[] unplannedItems = new Item[0];

	/**
	 * Initialize an optimization model object with the given input data. It contains the general
	 * parameter for all optimization procedures. It holds no solution information.
	 */
	public GRASPModel(Item[] items, Container[] containerTypes, GRASPParameter parameter, StatusManager statusManager) {
		this.items = items;
		this.containerTypes = containerTypes;
		this.parameter = parameter;
		this.statusManager = statusManager;
	}

	public Container[] getContainerTypes() {
		return containerTypes;
	}

	public GRASPParameter getParameter() {
		return parameter;
	}

	public Item[] getItems() {
		return items;
	}


	/**
	 * @return the containers
	 */
	public final Container[] getContainers() {
		return containers;
	}

	/**
	 * @param containers the containers to set
	 */
	public final void setContainers(Container[] containers) {
		this.containers = containers;
	}

	/**
	 * @return the unplannedItems
	 */
	public final Item[] getUnplannedItems() {
		return unplannedItems;
	}

	/**
	 * @param unplannedItems the unplannedItems to set
	 */
	public final void setUnplannedItems(Item[] unplannedItems) {
		this.unplannedItems = unplannedItems;
	}

	public void setItems(Item[] items) {
		this.items = items;
	}

	public StatusManager getStatusManager() {
		return statusManager;
	}
}
