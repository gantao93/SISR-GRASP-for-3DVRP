package routesFinding.dataImporter;

import core.base.container.SKU;

import java.util.List;

public class OrderDimension {
    private String orderID;
    private String node;
    private List<SKU> skuList;

//    public OrderDimension(String orderID,String node,List<SKU> skuList){
//        this.orderID = orderID;
//        this.node = node;
//        this.skuList = skuList;
//    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public List<SKU> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<SKU> skuList) {
        this.skuList = skuList;
    }
}
