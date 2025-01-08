package routesFinding.dataImporter;

public class SKUpfepData {
    private String projectCode;
    private String skuId;
    private String skuName;
    private int length;
    private int width;
    private int height;
    private Double maxHeight;  // Use Double to allow for null values
    private Double maxStackedLevel;  // Use Double to allow for null values
    private String shipUnitSpec;
    private String remark;
    private Double innerCount;

    public SKUpfepData(String projectCode, String skuId, String skuDesc,
                       int length, int width, int height,
                       Double maxHeight, Double maxStackedLevel,
                       String shipUnitSpec, String remark, Double innerCount) {
        this.projectCode = projectCode;
        this.skuId = skuId;
        this.skuName = skuDesc;
        this.length = length;
        this.width = width;
        this.height = height;
        this.maxHeight = maxHeight;
        this.maxStackedLevel = maxStackedLevel;
        this.shipUnitSpec = shipUnitSpec;
        this.remark = remark;
        this.innerCount = innerCount;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuID) {
        this.skuId = skuID;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
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

    public String getShipUnitSpec() {
        return shipUnitSpec;
    }

    public void setShipUnitSpec(String shipUnitSpec) {
        this.shipUnitSpec = shipUnitSpec;
    }

    public Double getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(Double maxHeight) {
        this.maxHeight = maxHeight;
    }

    public Double getMaxStackedLevel() {
        return maxStackedLevel;
    }

    public void setMaxStackedLevel(Double maxStackedLevel) {
        this.maxStackedLevel = maxStackedLevel;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Double getInnerCount() {
        return innerCount;
    }

    public void setInnerCount(Double innerCount) {
        this.innerCount = innerCount;
    }
    @Override
    public String toString() {
        return "SKU{" +
                "projectCode='" + projectCode + '\'' +
                ", skuId='" + skuId + '\'' +
                ", skuDesc='" + skuName + '\'' +
                ", length=" + length +
                ", width=" + width +
                ", height=" + height +
                ", maxHeight=" + maxHeight +
                ", maxStackedLevel=" + maxStackedLevel +
                ", shipUnitSpec='" + shipUnitSpec + '\'' +
                ", remark='" + remark + '\'' +
                ", innerCount=" + innerCount +
                '}';
    }
}
