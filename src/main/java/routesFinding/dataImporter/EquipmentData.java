package routesFinding.dataImporter;

public class EquipmentData {
    private String projectCode;
    private String vehicleId;
    private String vehicleName;
    private int length;
    private int width;
    private int height;
    private double maxVolume;
    private double maxWeight;
    private int availableQty;
    private double maxRate;
    private double minRate;
    private int maxStops;
    private String remark;


    public EquipmentData(String projectCode, String vehicleId, String vehicleName,
                         int length, int width, int height,
                         double maxVolume, double maxWeight, int availableQty,
                         double maxRate, double minRate, int maxStops, String remark) {
        this.projectCode = projectCode;
        this.vehicleId = vehicleId;
        this.vehicleName = vehicleName;
        this.length = length;
        this.width = width;
        this.height = height;
        this.maxVolume = maxVolume;
        this.maxWeight = maxWeight;
        this.availableQty = availableQty;
        this.maxRate = maxRate;
        this.minRate = minRate;
        this.maxStops = maxStops;
        this.remark = remark;
    }


    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }


    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
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

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public double getMaxVolume() {
        return maxVolume;
    }

    public void setMaxVolume(double maxVolume) {
        this.maxVolume = maxVolume;
    }

    public double getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(double maxWeight) {
        this.maxWeight = maxWeight;
    }

    public int getAvailableQty() {
        return availableQty;
    }

    public void setAvailableQty(int availableQty) {
        this.availableQty = availableQty;
    }

    public double getMaxRate() {
        return maxRate;
    }

    public void setMaxRate(double maxRate) {
        this.maxRate = maxRate;
    }

    public double getMinRate() {
        return minRate;
    }

    public void setMinRate(double minRate) {
        this.minRate = minRate;
    }

    public int getMaxStops() {
        return maxStops;
    }

    public void setMaxStops(int maxStops) {
        this.maxStops = maxStops;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    @Override
    public String toString() {
        return "Equipment{" +
                "projectCode='" + projectCode + '\'' +
                ", vehicleId='" + vehicleId + '\'' +
                ", vehicleName='" + vehicleName + '\'' +
                ", length=" + length +
                ", width=" + width +
                ", height=" + height +
                ", maxVolume=" + maxVolume +
                ", maxWeight=" + maxWeight +
                ", availableQty=" + availableQty +
                ", maxRate=" + maxRate +
                ", minRate=" + minRate +
                ", maxStops=" + maxStops +
                ", remark='" + remark + '\'' +
                '}';
    }
}
