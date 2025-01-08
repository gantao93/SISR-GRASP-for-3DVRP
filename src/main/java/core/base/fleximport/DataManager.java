package core.base.fleximport;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class DataManager implements Serializable {
	private static final long serialVersionUID = 3987431565021981666L;

	private int maxItemID = 0;
	private int maxShipmentID = 1;
	private int maxLocationID = 0;
	private long maxStackingGroupID = 1;
	private int maxContainerTypeID = 1;

	private final Map<String, Integer> itemMap = new HashMap<>();
	private final Map<Integer, String> itemIdMap = new HashMap<>();
	private final Map<String, Integer> shipmentMap = new HashMap<>();
	private final Map<String, Integer> locationMap = new HashMap<>();
	private final Map<String, Long> stackingGroupMap = new HashMap<>();
	private final Map<String, Integer> containerTypeMap = new HashMap<>();

	public DataManager() {
		containerTypeMap.put("package", 0);
		containerTypeMap.put("pallet", 1);
		containerTypeMap.put("vehicle", 2);
		stackingGroupMap.put("default_stacking_group", 0L);
		shipmentMap.put("default_shipment", 0);
		locationMap.put("", -1);
	}

	public void add(ItemData itemData) {
		addItem(itemData.getExternID());
		addShipment(itemData.getShipmentID());
		addLocation(itemData.getLoadingLocation());
		addLocation(itemData.getUnloadingLocation());
		addStackingGroup(itemData.getStackingGroup(), itemData.getAllowedStackingGroups());
//		addContainerTypes(itemData.getAllowedContainerTypes()); // delete 0628
	}

	public void add(ContainerData conData) {
		addContainerType(conData.getContainerType());
	}

	public void addItem(String itemID) {
		if(!itemMap.containsKey(itemID)) {
			itemMap.put(itemID, maxItemID);
			itemIdMap.put(maxItemID, itemID);
			maxItemID++;
		}
	}

	public void addShipment(String shipmentID) {
		if(!shipmentMap.containsKey(shipmentID))
			shipmentMap.put(shipmentID, maxShipmentID++);
	}

	public void addLocation(String locationID) {
		if(!locationMap.containsKey(locationID))
			locationMap.put(locationID, maxLocationID++);
	}

//	public void addContainerType(String containerType) {
//		if(!containerTypeMap.containsKey(containerType))
//			System.out.println("containerTypeMap elems "+containerType);
//			containerTypeMap.put(containerType, maxContainerTypeID++);
//	}
	public void addContainerType(String containerType) {
		if(containerType != null) {
			switch (containerType) {
				case "package":
					containerTypeMap.put(containerType, 0);
					break;
				case "pallet":
					containerTypeMap.put(containerType, 1);
					break;
				case "vehicle":
					containerTypeMap.put(containerType, 2);
					break;
				default:
					containerTypeMap.put(containerType, 3);
					break;
			}
		}
	}

	public void addStackingGroup(String stackingGroupID, String stackingGroups) {
		stackingGroupID = stackingGroupID.trim().toLowerCase();
		if(!stackingGroupMap.containsKey(stackingGroupID))
			stackingGroupMap.put(stackingGroupID, maxStackingGroupID++);

		String[] arr = stackingGroups.split(",");
		for (String s : arr) {
			s = s.trim().toLowerCase();
			if(!stackingGroupMap.containsKey(s))
				stackingGroupMap.put(s, maxStackingGroupID++);
		}
	}

	public void addContainerTypes(String containerTypes) {
		String[] arr = containerTypes.split(",");

		for (String s : arr) {
			System.out.println("arr s: "+s);
			if(!containerTypeMap.containsKey(s))
				containerTypeMap.put(s, maxContainerTypeID++);
		}
	}

	public int getItemIdx(String itemID) {
		return itemMap.get(itemID);
	}

	public String getItemId(int itemIdx) {
		return itemIdMap.get(itemIdx);
	}

	public int getShipmentIdx(String shipmentID) {
		return shipmentMap.get(shipmentID);
	}

	public int getLocationIdx(String locationID) {
		return locationMap.get(locationID.trim().toLowerCase());
	}

	public long getStackingGroupIdx(String stackingGroup) {
		return 1L << stackingGroupMap.get(stackingGroup.trim().toLowerCase());
	}

	public int getContainerTypeIdx(String containerType) {
		return containerTypeMap.get(containerType);
	}

	public String getContainerTypeName(int index) {
//		System.out.println("containerTypeMap "+containerTypeMap);
		for (Map.Entry<String, Integer> entry : containerTypeMap.entrySet()) {
			if(entry.getValue() == index)
				return entry.getKey();
		}

		return "not found";
	}
	// 新增于0627
//	public String getContainerTypeName(String containerTypeName) {
//		return containerTypeName;
//	}
	// add 0628


	public Set<Integer> getContainerTypes(String allowedContainerSet) {
		String[] arr = allowedContainerSet.split(",");

		Set<Integer> res = new HashSet<>(arr.length);
		for (String s : arr)
			res.add(containerTypeMap.get(s));

		return res;
	}

	public long getStackingGroups(String allowedStackingGroups) {
		long res = 0L;

		String[] arr = allowedStackingGroups.split(",");
		for (String s : arr) {
			s = s.trim().toLowerCase();
			if(stackingGroupMap.containsKey(s)) {
				res += 1L << stackingGroupMap.get(s);
			}
		}

		return res;
	}

	/**
	 *
	 */
	public void clear() {
		maxItemID = 0;
		maxShipmentID = 1;
		maxLocationID = 0;
		maxStackingGroupID = 1;
		maxContainerTypeID = 2;

		itemMap.clear();
		shipmentMap.clear();
		locationMap.clear();
		stackingGroupMap.clear();
		containerTypeMap.clear();
	}

	public void clearItems() {
		maxItemID = 0;
		maxShipmentID = 1;
		itemIdMap.clear();
		itemMap.clear();
		shipmentMap.clear();
		shipmentMap.put("default_shipment", 0);
	}
}
