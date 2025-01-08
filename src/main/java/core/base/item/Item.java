package core.base.item;


import core.base.fleximport.ContainerData;
import core.report.LoadType;
import util.collection.Indexable;
import java.util.Comparator;

/**
 * An item is the entity, which is placed into a container. It contains
 * all master data, parameter and planning information (like the current position).
 */
public class Item implements Indexable {

	public int size, volume, bottomArea, h, origH;
	public int x, y, z, xw, yl, zh, w, l;

	public int l_origin = l; // 旋转后的长宽高
	public int w_origin = w;
	public int h_origin = h;
	public boolean spinable, stackable;  //旋转
	public int loadingLoc, unLoadingLoc;
	
	// Binary representation, where only one bit can be active
	public long stackingGroup;
	// Allowed container types (cooled, dangerous goods, etc.)
//	public Set<Integer> allowedContainerSet;
	// Allowed items that can be stacked on top (binary representation)
	public long allowedStackingGroups;
	// How many different items can be below this item, if it is stacked.
	public int nbrOfAllowedStackedItems;
	// height, which reduces height of upper item, when something is stacked upon.
	public int immersiveDepth;

	public float weight;
	public float stackingWeightLimit;
	
	/* Unique index of this item object*/
	public int externalIndex;
	/* Type of item: loading or unloading */
	public LoadType loadingType;

	public RotationType rotationType;
	public String skuId; // DSC业务，该物品id

	public String type; // DSC业务，该物品类型（料架、围板箱、纸箱、木箱等）

	public int group; // DSC业务，节点访问顺序对应的索引

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public int num;
	/* External externalIndex of this order. There can be two items
	 * with the same order externalIndex (up- and unloading) */
	public int orderIndex = -1;
	/* Idx in data structure of its holding container */
	/* -1 if its unpacked */
	public int index = -1;
	/* Idx of the container, where the item is packed in. */
	/* -1 if its unpacked */
	public int containerIndex = -1;
	
	// Defines if this item is loaded (true) or unloaded (false)
	public boolean isLoading = false;
	// Defines if this item was rotated (true) or not rotated (false)
	public boolean isRotated = false;

	public Item() {
		this.x = this.y = this.z = this.xw = this.yl = this.zh = -1;
		this.stackable = true;
	}

	public void postInit() {
		this.size = w * l;
		this.volume = h * w * l;
		this.loadingType = (isLoading) ? LoadType.LOAD : LoadType.UNLOAD;
	}
	
	public void rotateXY() {
		int tmp = w;
		w = l;
		l = tmp;
		isRotated = !isRotated;
	}
	public void rotateXZ() {
		int tmp = h;
		h = w;
		w = tmp;
		isRotated = !isRotated;
	}
	public void rotateYZ() {
		int tmp = h;
		h = l;
		l = tmp;
		isRotated = !isRotated;
	}
	public void rotateXZ_YZ() {
		int tmp = h;
		h = w;
		w = tmp;

		int tmp2 = h;
		h = l;
		l = tmp2;

		isRotated = !isRotated;
	}
	public void rotateXY_YZ() {
		int tmp = w;
		w = l;
		l = tmp;

		int tmp2 = h;
		h = l;
		l = tmp2;

		isRotated = !isRotated;
	}


	public void setPosition(Position pos) {
		x = pos.x();
		y = pos.y();
		z = pos.z();
		xw = x + w;
		yl = y + l;
		zh = z + h;
	}

	public void clearPosition() {
		this.x = this.y = this.z = this.xw = this.yl = this.zh = -1;
	}

	@Override
	public String toString() {
		return "("+w+","+l+","+h+")";
//		return "Item "+this.externalIndex+" "+loadingLoc+" "+unLoadingLoc+" ("+w+","+l+","+h+") ["+x+", "+y+", "+z+" "+(this.isRotated?"R":"")+"]"+ " "+stackingGroup;
	}

	public void reset() {
		clearPosition();
		this.index = -1;
		this.containerIndex = -1;
		if(this.rotationType==RotationType.XZY){
			rotateYZ();
		}
		if(this.rotationType==RotationType.YZX){
			rotateXZ_YZ();
		}
		if(this.rotationType==RotationType.YXZ){
			rotateXY();
		}
		if(this.rotationType==RotationType.ZXY){
			rotateXY_YZ();
		}
		if(this.rotationType==RotationType.ZYX){
			rotateXZ();
		}
		this.isLoading = false;
	}

	@Override
	public int getIdx() {
		return index;
	}

	@Override
	public void setIdx(int idx) {
		this.index = idx;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getVolume() {
		return w * l * h;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public int getBottomArea() {return w*l;}
	public void setBottomArea(int bottomArea) {this.bottomArea = bottomArea;}

	public void setNum(int num){
		this.num = num;
	}

	public int getNum() {
		return num;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
		this.origH = h;
	}
	public int getHHat() {
		return h_origin;
	}

	public void setHHat(int h_hat) {
		this.h_origin = h_hat;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}

	public int getXw() {
		return xw;
	}

	public void setXw(int xw) {
		this.xw = xw;
	}

	public int getYl() {
		return yl;
	}

	public void setYl(int yl) {
		this.yl = yl;
	}

	public int getZh() {
		return zh;
	}

	public void setZh(int zh) {
		this.zh = zh;
	}

	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}

	public int getWHat() {
		return w_origin;
	}

	public void setWHat(int w_hat) {
		this.w_origin = w_hat;
	}

	public int getL() {
		return l;
	}

	public void setL(int l) {
		this.l = l;
	}
	public int getLHat() {
		return l_origin;
	}

	public void setLHat(int l_hat) {
		this.l_origin = l_hat;
	}

	public boolean isSpinable() {
		return spinable;
	}

	public void setSpinable(boolean spinable) {
		this.spinable = spinable;
	}

	public boolean isStackable() {
		return stackable;
	}

	public void setStackable(boolean stackable) {
		this.stackable = stackable;
	}

	public int getLoadingLoc() {
		return loadingLoc;
	}

	public void setLoadingLoc(int loadingLoc) {
		this.loadingLoc = loadingLoc;
	}

	public int getUnLoadingLoc() {
		return unLoadingLoc;
	}

	public void setUnLoadingLoc(int unLoadingLoc) {
		this.unLoadingLoc = unLoadingLoc;
	}

	public long getStackingGroup() {
		return stackingGroup;
	}

	public void setStackingGroup(long stackingGroup) {
		this.stackingGroup = stackingGroup;
	}

	// delete 0628
//	public Set<Integer> getAllowedContainerSet() {
//		return allowedContainerSet;
//	}

// delete 0628
//	public void setAllowedContainerSet(Set<Integer> allowedContainerSet) {
//		this.allowedContainerSet = allowedContainerSet;
//	}

	public long getAllowedStackingGroups() {
		return allowedStackingGroups;
	}

	public void setAllowedStackingGroups(long allowedStackingGroups) {
		this.allowedStackingGroups = allowedStackingGroups;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public float getStackingWeightLimit() {
		return stackingWeightLimit;
	}

	public void setStackingWeightLimit(float stackingWeightLimit) {
		this.stackingWeightLimit = stackingWeightLimit;
	}

	public int getExternalIndex() {
		return externalIndex;
	}

	public void setExternalIndex(int externalIndex) {
		this.externalIndex = externalIndex;
	}

	public LoadType getLoadingType() {
		return loadingType;
	}

	public void setLoadingType(LoadType loadingType) {
		this.loadingType = loadingType;
	}

	public int getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(int orderIndex) {
		this.orderIndex = orderIndex;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getContainerIndex() {
		return containerIndex;
	}

	public void setContainerIndex(int containerIndex) {
		this.containerIndex = containerIndex;
	}

	public boolean isLoading() {
		return isLoading;
	}

	public void setLoading(boolean loading) {
		isLoading = loading;
	}

	public boolean isRotated() {
		return isRotated;
	}

	public void setRotated(boolean rotated) {
		isRotated = rotated;
	}

	public int getGroup() {
		return group;
	}

	public void setGroup(int group) {
		this.group = group;
	}

	public RotationType getRotationType(){
		return rotationType;
	}

	public void setRotationType(RotationType rotationType) {
		this.rotationType = rotationType;
	}

	public int getNbrOfAllowedStackedItems() {
		return nbrOfAllowedStackedItems;
	}

	public void setNbrOfAllowedStackedItems(int nbrOfAllowedStackedItems) {
		this.nbrOfAllowedStackedItems = nbrOfAllowedStackedItems;
	}

	public int getImmersiveDepth() {
		return immersiveDepth;
	}

	public void setImmersiveDepth(int immersiveDepth) {
		this.immersiveDepth = immersiveDepth;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Item item)) return false;
		return index == item.index;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

}
