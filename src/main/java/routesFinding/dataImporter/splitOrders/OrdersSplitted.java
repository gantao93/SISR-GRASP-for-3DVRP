package routesFinding.dataImporter.splitOrders;

import app.GraspBinPacker;
import core.base.container.SKU;
import core.base.container.Vehicle;
import core.base.item.ItemType;
import core.report.LPPackageEvent;
import report.Report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrdersSplitted {
    private Vehicle vehicle;
    private Map<String, List<SKU>> customerItemsMapping;
    private List<ItemType> itemsCompatibleInfo;

    public OrdersSplitted(
            Vehicle vehicle,
            Map<String, List<SKU>> customerItemsMapping,
            List<ItemType> itemsCompatibleInfo
    ){
        this.vehicle = vehicle;
        this.customerItemsMapping = customerItemsMapping;
        this.itemsCompatibleInfo = itemsCompatibleInfo;
    }

    public Map<String, List<SKU>> split(){
        Map<String, List<SKU>> spiltedItems = new HashMap<>();
        for(Map.Entry<String, List<SKU>> entry: customerItemsMapping.entrySet()){
            String customer = entry.getKey();
            if(!customer.equals("SGMW-1000")) {
                spiltedItems.put(customer, new ArrayList<>());
            }
            List<SKU> items = entry.getValue();
            Report report = new GraspBinPacker(vehicle, items, itemsCompatibleInfo).run();
            List<LPPackageEvent> unPackedItems = report.getUnpackedItems();
            if(unPackedItems.isEmpty()){
                spiltedItems.put(customer, items);
            } else {
                List<SKU> unloadedItems = lPPackageEventToSKUClass(unPackedItems);
                // 获取差集
                List<SKU> difference = new ArrayList<>(items);
                difference.removeAll(unloadedItems);
                spiltedItems.put(customer, difference);
            }

        }
        return spiltedItems;
    }

    public List<SKU> lPPackageEventToSKUClass(List<LPPackageEvent> items){
        if(items.size()==0){
            return new ArrayList<>();
        }
        List<SKU> itemsList = new ArrayList<>();
        for(LPPackageEvent lpItem:items){
            SKU sku = new SKU();
            sku.setWidth(lpItem.w());
            sku.setLength(lpItem.l());
            sku.setHeight(lpItem.h());
            sku.setX(lpItem.x());
            sku.setY(lpItem.y());
            sku.setZ(lpItem.z());
            itemsList.add(sku);
        }
        return itemsList;
    }
}
