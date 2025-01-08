package core.opt.construction.onetype;

import core.base.container.Container;
import core.opt.construction.strategy.BaseStrategy;
import core.base.GRASPModel;
import core.base.item.Item;
import core.base.monitor.StatusCode;
import core.base.position.PositionCandidate;
import core.base.position.PositionService;
import core.exception.Exception;
import core.opt.Packer;
import core.report.LoadType;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Item packer for single container with adding and removing items
 * 
 * This packer puts the items in a sequence into one single container.
 * It is able to add and to remove the items with respect to their loading type (LOAD, UNLOAD).
 * There is no optimization in container allocation or item sequence.
 * 同时考虑装和卸
 */
public class OneContainerOneTypePacker implements Packer {

	@Override
	public void execute(GRASPModel model) throws Exception {
		Container container = model.getContainerTypes()[0].newInstance();
		String containerType = container.getContainerTypeName();
		BaseStrategy strategy = model.getParameter().getPreferredPackingStrategy(containerType).getStrategy();
		
		Map<Integer, Item> loadedItemMap = new HashMap<>();

		List<Item> unplannedItemList = new ArrayList<>();
		ArrayList<PositionCandidate> occupiedPosition = new ArrayList<>();
		// For all items with respect to given sort order
//		Item[] items = model.getItems();

		// 按体积降序
		Item[] items = Arrays.stream(model.getItems()).sorted(Comparator.comparing(Item::getVolume).reversed())
				.collect(Collectors.toList()).toArray(new Item[0]);
		// Reset eventual presets
		resetItems(items);

		for (int i = 0; i < items.length; i++) {
			Item item = items[i];

			if(item.loadingType == LoadType.LOAD) {
				PositionCandidate insertPosition = null;

				// Check if item is allowed to this container type
				if(container.isItemAllowed(item)) {
					// Fetch existing insert positions
					List<PositionCandidate> candidates = PositionService.findPositionCandidates(container, item);
//					System.out.println("加入旋转状态后的所有候选位置 -> "+candidates);
					if(!candidates.isEmpty()) {
						// Choose according to select strategy
						insertPosition = strategy.choose(item, container, candidates);
						occupiedPosition.add(insertPosition);
//						System.out.println("策略最终选择的位置 -> "+insertPosition);
					}
				}

				// Add item to container
				if(insertPosition != null) {						
					container.add(
							insertPosition.item(),
							insertPosition.position(),
							insertPosition.rotationType()
					);
					loadedItemMap.put(item.externalIndex, item);
				} else {
					model.getStatusManager().fireMessage(StatusCode.RUNNING, "Item " + item.index + " could not be added.");
					unplannedItemList.add(item);
				}
			} else {
				// Remove item from container
				// It is not checked if item was really loaded to container.
				// Before removing the unloading item must be replaced by the loaded item object
				// for index problems
				if(loadedItemMap.containsKey(item.externalIndex))
					container.remove(loadedItemMap.get(item.externalIndex));
			}
		}

		// Put result into model
		model.setContainers(new Container[]{container});
		model.setUnplannedItems(unplannedItemList.toArray(new Item[0]));
	}

	public void ItemOrderByVolume(GRASPModel model){
		Container container = model.getContainerTypes()[0].newInstance();
		String containerType = container.getContainerTypeName();
		BaseStrategy strategy = model.getParameter().getPreferredPackingStrategy(containerType).getStrategy();
		Map<Integer, Item> loadedItemMap = new HashMap<>();
		List<Item> unplannedItemList = new ArrayList<>();
		ArrayList<PositionCandidate> occupiedPosition = new ArrayList<>();
		// For all items with respect to given sort order
//		Item[] items = model.getItems();
		// 按体积降序
		Item[] items = Arrays.stream(model.getItems()).sorted(Comparator.comparing(Item::getVolume).reversed())
				.collect(Collectors.toList()).toArray(new Item[0]);
		// Reset eventual presets
		resetItems(items);
		for (int i = 0; i < items.length; i++) {
			Item item = items[i];

			if(item.loadingType == LoadType.LOAD) {
				PositionCandidate insertPosition = null;

				// Check if item is allowed to this container type
				if(container.isItemAllowed(item)) {
					// Fetch existing insert positions
					List<PositionCandidate> candidates = PositionService.findPositionCandidates(container, item);
//					System.out.println("加入旋转状态后的所有候选位置 -> "+candidates);
					if(!candidates.isEmpty()) {
						// Choose according to select strategy
						insertPosition = strategy.choose(item, container, candidates);
						occupiedPosition.add(insertPosition);
//						System.out.println("策略最终选择的位置 -> "+insertPosition);
					}
				}

				// Add item to container
				if(insertPosition != null) {
					container.add(
							insertPosition.item(),
							insertPosition.position(),
							insertPosition.rotationType()
					);
					loadedItemMap.put(item.externalIndex, item);
				} else {
					model.getStatusManager().fireMessage(StatusCode.RUNNING, "Item " + item.index + " could not be added.");
					unplannedItemList.add(item);
				}
			} else {
				if(loadedItemMap.containsKey(item.externalIndex))
					container.remove(loadedItemMap.get(item.externalIndex));
			}
		}
	}

	public void ItemOrderByBottomArea(GRASPModel model){
		Container container = model.getContainerTypes()[0].newInstance();
		String containerType = container.getContainerTypeName();
		BaseStrategy strategy = model.getParameter().getPreferredPackingStrategy(containerType).getStrategy();
		Map<Integer, Item> loadedItemMap = new HashMap<>();
		List<Item> unplannedItemList = new ArrayList<>();
		ArrayList<PositionCandidate> occupiedPosition = new ArrayList<>();

		// For all items with respect to given sort order
//		Item[] items = model.getItems();
		// 按底面积降序
		Item[] items = Arrays.stream(model.getItems()).sorted(Comparator.comparing(Item::getBottomArea).reversed())
				.collect(Collectors.toList()).toArray(new Item[0]);
		// Reset eventual presets
		resetItems(items);
		for (int i = 0; i < items.length; i++) {
			Item item = items[i];

			if(item.loadingType == LoadType.LOAD) {
				PositionCandidate insertPosition = null;

				// Check if item is allowed to this container type
				if(container.isItemAllowed(item)) {
					// Fetch existing insert positions
					List<PositionCandidate> candidates = PositionService.findPositionCandidates(container, item);
//					System.out.println("加入旋转状态后的所有候选位置 -> "+candidates);
					if(!candidates.isEmpty()) {
						// Choose according to select strategy
						insertPosition = strategy.choose(item, container, candidates);
						occupiedPosition.add(insertPosition);
//						System.out.println("策略最终选择的位置 -> "+insertPosition);
					}
				}

				// Add item to container
				if(insertPosition != null) {
					container.add(
							insertPosition.item(),
							insertPosition.position(),
							insertPosition.rotationType()
					);
					loadedItemMap.put(item.externalIndex, item);
				} else {
					model.getStatusManager().fireMessage(StatusCode.RUNNING, "Item " + item.index + " could not be added.");
					unplannedItemList.add(item);
				}
			} else {
				if(loadedItemMap.containsKey(item.externalIndex))
					container.remove(loadedItemMap.get(item.externalIndex));
			}
		}
	}

	private void resetItems(Item[] items) {
		for (int i = items.length - 1; i >= 0; i--) {
			items[i].reset();
		}
	}

}
