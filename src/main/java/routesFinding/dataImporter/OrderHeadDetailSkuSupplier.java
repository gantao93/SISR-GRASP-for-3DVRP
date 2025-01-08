package routesFinding.dataImporter;

import core.base.container.SKU;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderHeadDetailSkuSupplier{
    private String orderID;
    private String locationID;
    private String skuID;
    private int length;
    private int width;
    private int height;
    private int skuNum;
    private String skuType;
    private double lat;
    private double lon;
    private int serveTime;

    public OrderHeadDetailSkuSupplier(
            String orderID,
            String locationID,
            String skuID,
            int length,
            int width,
            int height,
            int skuNum,
            String skuType,
            double lat,
            double lon,
            int serveTime
            ){
        this.orderID = orderID;
        this.locationID = locationID;
        this.skuID = skuID;
        this.length = length;
        this.width = width;
        this.height = height;
        this.skuNum = skuNum;
        this.skuType = skuType;
        this.lat = lat;
        this.lon = lon;
        this.serveTime = serveTime;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getLocationID() {
        return locationID;
    }

    public void setLocationID(String locationID) {
        this.locationID = locationID;
    }

    public String getSkuID() {
        return skuID;
    }

    public void setSkuID(String skuID) {
        this.skuID = skuID;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getSkuNum() {
        return skuNum;
    }

    public void setSkuNum(int skuNum) {
        this.skuNum = skuNum;
    }

    public String getSkuType() {
        return skuType;
    }

    public void setSkuType(String skuType) {
        this.skuType = skuType;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public int getServeTime() {
        return serveTime;
    }

    public void setServeTime(int serveTime) {
        this.serveTime = serveTime;
    }

    public static List<Customer> groupByLocation(List<OrderHeadDetailSkuSupplier> orderList) {
        Map<String, List<OrderHeadDetailSkuSupplier>> groupedByLocation = orderList.stream()
                .collect(Collectors.groupingBy(OrderHeadDetailSkuSupplier::getLocationID));

        List<Customer> customerList = groupedByLocation.entrySet().stream()
                .map(entry -> {
                    Customer customer = new Customer();
                    customer.setLocationId(entry.getKey());

                    customer.setLat(entry.getValue().get(0).getLat());
                    customer.setLon(entry.getValue().get(0).getLon());
                    customer.setLocation(new Location(entry.getValue().get(0).getLat(),entry.getValue().get(0).getLon()));

                    List<SKU> skuList = entry.getValue().stream()
                            .map(order -> {
                                SKU sku = new SKU();
                                sku.setSkuId(order.getSkuID());
                                sku.setType(order.getSkuType());
                                sku.setCustomer(order.locationID);
                                sku.setLength(order.getLength());
                                sku.setWidth(order.getWidth());
                                sku.setHeight(order.getHeight());
                                sku.setNumber(order.getSkuNum());
                                return sku;
                            })
                            .collect(Collectors.toList());

                    customer.setSkuList(skuList);
                    return customer;
                })
                .collect(Collectors.toList());

        return customerList;
    }

    public static List<OrderDimension> groupByOrder(List<OrderHeadDetailSkuSupplier> orderList) {
        Map<String, List<OrderHeadDetailSkuSupplier>> groupedByOrder = orderList.stream()
                .collect(Collectors.groupingBy(OrderHeadDetailSkuSupplier::getOrderID));

        List<OrderDimension> orderDimensions = groupedByOrder.entrySet().stream()
                .map(entry -> {
                    OrderDimension orderDimension = new OrderDimension();
                    orderDimension.setOrderID(entry.getKey());
                    orderDimension.setNode(entry.getValue().get(0).getLocationID()); // Assuming the first location is used as the node

                    List<SKU> skuList = entry.getValue().stream()
                            .map(order -> {
                                SKU sku = new SKU();
                                sku.setSkuId(order.getSkuID());
                                sku.setType(order.getSkuType());
                                sku.setCustomer(order.locationID);
                                sku.setLength(order.getLength());
                                sku.setWidth(order.getWidth());
                                sku.setHeight(order.getHeight());
                                sku.setNumber(order.getSkuNum());
                                return sku;
                            })
                            .collect(Collectors.toList());

                    orderDimension.setSkuList(skuList);
                    return orderDimension;
                })
                .collect(Collectors.toList());

        return orderDimensions;
    }



}
