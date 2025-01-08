package core.base;

import core.base.container.ContainerType;
import core.base.container.GroundContactRule;
import core.opt.construction.strategy.Strategy;


public class GRASPParameter {

	private float lifoImportance = 0f;
	private int maxNbrOfContainer = Integer.MAX_VALUE;
	private Strategy preferredPackingStrategy_package = Strategy.BOTTOM_WIDTH_PROPORTION;
//	private Strategy preferredPackingStrategy_package = Strategy.LAYER_BUILDING;
	private Strategy preferredPackingStrategy_vehicle = Strategy.HIGH_LOW_LEFT;
	private Strategy preferredPackingStrategy_vehicle_dsc = Strategy.DSC_SPECIAL_STACKED_RULE;
	private Strategy preferredPackingStrategy = Strategy.TOUCHING_PERIMETER;
	private Strategy preferredPackingStrategy_pallet = Strategy.LAYER_BUILDING;

	private int nbrOfAllowedStackedItems = Integer.MAX_VALUE;
	private GroundContactRule groundContactRule = GroundContactRule.FREE;

	public void clear() {
		// TODO Auto-generated method stub
		
	}

	public float getLifoImportance() {
		return lifoImportance;
	}

	public void setLifoImportance(float lifoImportance) {
		this.lifoImportance = lifoImportance;
	}

	public Strategy getPreferredPackingStrategy() {
		return preferredPackingStrategy;
	}

	public Strategy getPreferredPackingStrategy(String containerType) {
		if (containerType.equals("package")) {
			return preferredPackingStrategy_package;
		} else if (containerType.equals("pallet")) {
			return preferredPackingStrategy_pallet;
		} else {
//			return preferredPackingStrategy_vehicle;
			return preferredPackingStrategy_vehicle_dsc;
		}
	}
	/**
	 * The packing strategy is used by the placing algorithm to choose the best
	 * next insert position.
	 *
	 * This value must be choosen from enum:
	 *  - HIGH_LOW_LEFT : Choose highest, deepest and most left position
	 *  - TOUCHING_PERIMETER : Choose the position, where new item will touch as most as possible already placed items. If there are multiple best positions, choose with HIGH_LOW_LEFT.
	 *  - WIDTH_PROPORTION : Choose the position, where the width of the new item is nearest to a full proportion of the container width. If there are multiple best positions, choose with TOUCHING_PERIMETER.
	 *
	 * Default: HIGH_LOW_LEFT
	 */
	public void setPreferredPackingStrategy(Strategy preferredPackingStrategy) {
		this.preferredPackingStrategy = preferredPackingStrategy;
	}

	public int getMaxNbrOfContainer() {
		return maxNbrOfContainer;
	}

	public void setMaxNbrOfContainer(int maxNbrOfContainer) {
		this.maxNbrOfContainer = maxNbrOfContainer;
	}

	public int getNbrOfAllowedStackedItems() {
		return nbrOfAllowedStackedItems;
	}

	/**
	 * This value defines the maximal number of items, which are allowed to be
	 * placed/stacked on top of an item.
	 *
	 * If value is set to 1, then the algorithm can place/stack only one other item on top of any item.
	 *
	 * Default: No limitation
	 */
	public void setNbrOfAllowedStackedItems(int nbrOfAllowedStackedItems) {
		this.nbrOfAllowedStackedItems = nbrOfAllowedStackedItems;
	}

	public GroundContactRule getGroundContactRule() {
		return groundContactRule;
	}

	public void setGroundContactRule(GroundContactRule groundContactRule) {
		this.groundContactRule = groundContactRule;
	}
}
