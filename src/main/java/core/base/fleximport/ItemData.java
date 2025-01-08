package core.base.fleximport;

import core.base.item.Item;

import java.io.Serializable;

/**
 * Depot data class defines all fields which can be used
 * in GRASP suite. Users can call the set-Methods to insert
 * data into this object. There is no predefined sequence of 
 * doing that. All fields are initialized by default values 
 * in that way, that restrictions are always holds.
 * 
 * Each set-method returns the depot data object itself, so
 * that the set-methods can be called in one way.
 * 
 * There is no way to clear a value back to default value. The
 * user has to do that by himself. All values can be set multiple times, 
 * where last set value is overwritten with only one exception:
 * It is possible to set multiple time windows. It is not possible
 * to clear the list of inserted time windows.
 */
public class ItemData implements Serializable,Comparable<Item> {

	private static final long serialVersionUID = -8970875565639368202L;
	
	protected String externID = "";
	protected String shipmentID = "default_shipment";

	protected int width = -1;
	protected int length = -1;
	protected int height = -1;

	protected int w_origin = width;
	protected int l_origin = length;
	protected int h_origin = height;
	protected int immersiveDepth = 0;
	
	protected float weight = 0;
	protected float stackingWeightLimit = Float.MAX_VALUE;
	
	protected String stackingGroup = "default_stacking_group";
	protected String allowedStackingGroups = "default_stacking_group";
//	protected String allowedContainerSet = "default_container_type"; // delete 0628

//	ContainerBase containerBase = new ContainerBase;
//	protected String allowedContainerSet = containerBase.getContainerTypeName();

	protected int nbrOfAllowedStackedItems = Integer.MAX_VALUE;
	
	protected String loadingLocation = "";
	protected String unloadingLocation = "";
	
	protected boolean spinnable = true;

	protected String skuId;
	protected String type;
	protected int group;

	public final ItemData setSkuId(String skuId) {
		this.skuId = skuId;
		return this;
	}

	public final ItemData setType(String type) {
		this.type = type;
		return this;
	}

	public final ItemData setGroup(int group) {
		this.group = group;
		return this;
	}

	/**
	 * @param externID the externID to set
	 */
	public final ItemData setExternID(String externID) {
		this.externID = externID;
		return this;
	}

	/**
	 * @param shipmentID the shipmentID to set
	 */
	public final ItemData setShipmentID(String shipmentID) {
		this.shipmentID = shipmentID;
		return this;
	}

	/**
	 * @param width the width to set
	 */
	public final ItemData setWidth(int width) {
		this.width = width;
		this.w_origin = width;
		return this;
	}

	/**
	 * @param length the length to set
	 */
	public final ItemData setLength(int length) {
		this.length = length;
		this.l_origin = length;
		return this;
	}

	/**
	 * @param height the height to set
	 */
	public final ItemData setHeight(int height) {
		this.height = height;
		this.h_origin = height;
		return this;
	}


	public final ItemData setWHat(int width) {
		this.w_origin = width;
		return this;
	}

	public final ItemData setLHat(int length) {
		this.l_origin = length;
		return this;
	}

	public final ItemData setHHat(int height) {
		this.h_origin = height;
		return this;
	}


	/**
	 * @param weight the weight to set
	 */
	public final ItemData setWeight(float weight) {
		this.weight = weight;
		return this;
	}

	/**
	 * @param stackingWeightLimit the stackingWeightLimit to set
	 */
	public final ItemData setStackingWeightLimit(float stackingWeightLimit) {
		this.stackingWeightLimit = stackingWeightLimit;
		return this;
	}

	/**
	 * @param stackingGroup the stackingGroup to set
	 */
	public final ItemData setStackingGroup(String stackingGroup) {
		this.stackingGroup = stackingGroup;
		return this;
	}

	/**
	 * @param allowedStackingGroups the allowedStackingGroups to set
	 */
	public final ItemData setAllowedStackingGroups(String allowedStackingGroups) {
		this.allowedStackingGroups = allowedStackingGroups;
		return this;
	}

	/**
	 * @param allowedContainerSet the allowedContainerSet to set
	 */
	// delete 0628
//	public final ItemData setAllowedContainerSet(String allowedContainerSet) {
//		this.allowedContainerSet = allowedContainerSet;
//		return this;
//	}

	/**
	 * @param loadingLocation the loadingLocation to set
	 */
	public final ItemData setLoadingLocation(String loadingLocation) {
		this.loadingLocation = loadingLocation;
		return this;
	}

	/**
	 * @param unloadingLocation the unloadingLocation to set
	 */
	public final ItemData setUnloadingLocation(String unloadingLocation) {
		this.unloadingLocation = unloadingLocation;
		return this;
	}

	/**
	 * @param spinnable the spinnable to set
	 */
	public final ItemData setSpinnable(boolean spinnable) {
		this.spinnable = spinnable;
		return this;
	}

	/**
	 * @param nbrOfAllowedStackedItems is the number of allowed items below this item,
	 *                                 when it will be stacked. 1 means, that this item must
	 *                                 be stacked only one one other item.
	 */
	public final ItemData setNbrOfAllowedStackedItems(int nbrOfAllowedStackedItems) {
		this.nbrOfAllowedStackedItems = nbrOfAllowedStackedItems;
		return this;
	}

	/**
	 * @param immersiveDepth - If items have special form groups at the top or bottom (shoulder or feet), then
	 *                       during stacking the lower and upper item dives into each other and the overall height
	 *                       is reduced. The amount of reduced height by diving into each other is the immersive depth.
	 */
	public final ItemData setImmersiveDepth(int immersiveDepth) {
		this.immersiveDepth = immersiveDepth;
		return this;
	}

	/////////////////////////////////////////////////////////////
	public int getVolume(){
		return length*width*height;
	}
	@Override
	public int compareTo(Item o) {
		return this.getVolume() - o.getVolume(); // 只根据体积升序排序
	}
	/**
	 * @return the externID
	 */
	String getExternID() {
		return externID;
	}

	/**
	 * @return the shipmentID
	 */
	String getShipmentID() {
		return shipmentID;
	}

	/**
	 * @return the width
	 */
	int getWidth() {
		return width;
	}

	/**
	 * @return the length
	 */
	int getLength() {
		return length;
	}

	/**
	 * @return the height
	 */
	int getHeight() {
		return height;
	}

	/**
	 * @return the weight
	 */
	float getWeight() {
		return weight;
	}

	/**
	 * @return the stackingWeightLimit
	 */
	float getStackingWeightLimit() {
		return stackingWeightLimit;
	}

	/**
	 * @return the stackingGroup
	 */
	String getStackingGroup() {
		return stackingGroup;
	}

	/**
	 * @return the allowedStackingGroups
	 */
	String getAllowedStackingGroups() {
		return allowedStackingGroups;
	}

	/**
	 * @return the allowedContainerSet
	 */
	// delete 0628
//	String getAllowedContainerTypes() {
//		return allowedContainerSet;
//	}

	/**
	 * @return the loadingLocation
	 */
	String getLoadingLocation() {
		return loadingLocation;
	}

	/**
	 * @return the unloadingLocation
	 */
	String getUnloadingLocation() {
		return unloadingLocation;
	}

	int getImmersiveDepth() {
		return immersiveDepth;
	}

	/**
	 * @return the spinnable
	 */
	boolean isSpinable() {
		return spinnable;
	}

	public Item createLoadingItem(DataManager manager) {
		Item i = createItem(manager);
		i.setLoading(true); // is Loading

		i.postInit();

		return i;
	}

	public Item createUnLoadingItem(DataManager manager) {
		Item i = createItem(manager);
		i.setLoading(false); // is Unloading

		i.postInit();

		return i;
	}

	private Item createItem(DataManager manager) {
		Item i = new Item();

		i.setExternalIndex(manager.getItemIdx(externID));
		i.setOrderIndex(manager.getShipmentIdx(shipmentID));
		i.setLoadingLoc(manager.getLocationIdx(loadingLocation));
		i.setUnLoadingLoc(manager.getLocationIdx(unloadingLocation));
		i.setW(width);
		i.setL(length);
		i.setH(height);
		i.setWeight(weight);

		i.setWHat(w_origin);
		i.setLHat(l_origin);
		i.setHHat(h_origin);

		i.setStackingWeightLimit(stackingWeightLimit);
//		i.setAllowedContainerSet(manager.getContainerTypes(allowedContainerSet)); // delete 0628
		i.setStackingGroup(manager.getStackingGroupIdx(stackingGroup));
		i.setAllowedStackingGroups(manager.getStackingGroups(allowedStackingGroups));
		i.setNbrOfAllowedStackedItems(nbrOfAllowedStackedItems);
		i.setImmersiveDepth(immersiveDepth);
		i.setSpinable(spinnable);
		i.setType(type);
		i.setGroup(group);
		i.setSkuId(skuId);
		return i;
	}
}
