package routesFinding.dataImporter;

public class OrderHeadDetail {
    private String orderID;
    private String locationID;
    private String skuID;
    private int skuNum;
    private String skuType;

    public OrderHeadDetail(OrderHeadData orderHead, OrderDetailData orderDetail) {
        this.orderID = orderHead.getOrderID();
        this.locationID = orderHead.getSourceLocationID();
        this.skuID = orderDetail.getSkuID();
        this.skuNum = (int) orderDetail.getNumberCarton();
        this.skuType = orderDetail.getShipUnitSpec();
    }

    public OrderHeadDetail(String orderID, String locationID, String skuID, int skuNum, String skuType) {
        this.orderID = orderID;
        this.locationID = locationID;
        this.skuID = skuID;
        this.skuNum = skuNum;
        this.skuType = skuType;
    }


    // Getter methods

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

}