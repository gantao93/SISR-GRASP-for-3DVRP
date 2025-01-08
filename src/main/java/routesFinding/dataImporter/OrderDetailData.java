package routesFinding.dataImporter;

public class OrderDetailData {
    private String projectCode;
    private String orderID;
    private String orderName;
    private String skuID;
    private String skuName;
    private int pcs;
    private int innerCount;
    private double numberCarton;
    private String remark;
    private String shipUnitSpec;

    public OrderDetailData(String projectCode, String orderReleaseXID, String orderReleaseName,
                           String skuID, String skuDescription, int pcs, int innerCount,
                           double numberOfCarton, String remark, String shipUnitSpec) {
        this.projectCode = projectCode;
        this.orderID = orderReleaseXID;
        this.orderName = orderReleaseName;
        this.skuID = skuID;
        this.skuName = skuDescription;
        this.pcs = pcs;
        this.innerCount = innerCount;
        this.numberCarton = numberOfCarton;
        this.remark = remark;
        this.shipUnitSpec = shipUnitSpec;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getSkuID() {
        return skuID;
    }

    public void setSkuID(String skuID) {
        this.skuID = skuID;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public int getPcs() {
        return pcs;
    }

    public void setPcs(int pcs) {
        this.pcs = pcs;
    }

    public int getInnerCount() {
        return innerCount;
    }

    public void setInnerCount(int innerCount) {
        this.innerCount = innerCount;
    }

    public double getNumberCarton() {
        return numberCarton;
    }

    public void setNumberCarton(double numberCarton) {
        this.numberCarton = numberCarton;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getShipUnitSpec() {
        return shipUnitSpec;
    }

    public void setShipUnitSpec(String shipUnitSpec) {
        this.shipUnitSpec = shipUnitSpec;
    }

    @Override
    public String toString() {
        return "OrderReleaseData{" +
                "projectCode='" + projectCode + '\'' +
                ", orderReleaseXID='" + orderID + '\'' +
                ", orderReleaseName='" + orderName + '\'' +
                ", skuID='" + skuID + '\'' +
                ", skuDescription='" + skuName + '\'' +
                ", pcs=" + pcs +
                ", innerCount=" + innerCount +
                ", numberOfCarton=" + numberCarton +
                ", remark='" + remark + '\'' +
                ", shipUnitSpec='" + shipUnitSpec + '\'' +
                '}';
    }
}
