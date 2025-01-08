package routesFinding.dataImporter;


import core.base.container.SKU;

public class OrderHeadDetailSku extends OrderHeadDetail {

    private String skuId;
    private int length;
    private int width;
    private int height;

    private String skuType;

    public OrderHeadDetailSku(OrderHeadDetail orderHeadDetail, SKUpfepData sku) {
        super(
            orderHeadDetail.getOrderID(),
            orderHeadDetail.getLocationID(),
            orderHeadDetail.getSkuID(),
            orderHeadDetail.getSkuNum(),
            orderHeadDetail.getSkuType()
        );
        this.skuId = sku.getSkuId();
        this.length = sku.getLength();
        this.width = sku.getWidth();
        this.height = sku.getHeight();
        this.skuType = orderHeadDetail.getSkuType();
    }

//    public OrderHeadDetailSku(String orderID, String locationID, String skuId, int length, int width, int height, String skuType) {
//        this.
//
//    }

    @Override
    public String getSkuType() {
        return skuType;
    }

    @Override
    public void setSkuType(String skuType) {
        this.skuType = skuType;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
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

    public SKU toSku(String customer) {

        SKU sku = new SKU();

        sku.setSkuId(this.getSkuID());
        sku.setLength(this.getLength());
        sku.setWidth(this.getWidth());
        sku.setHeight(this.getHeight());
        sku.setType(this.getSkuType());
        sku.setNumber(1);
        sku.setCustomer(customer);

        return sku;

    }
}