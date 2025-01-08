package routesFinding.dataImporter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OrderHeadData {
    private String projectCode;
    private String orderID;
    private String orderName;
    private String sourceLocationID;
    private String sourceLocationName;
    private String destLocationID;
    private String destLocationName;
    private LocalDateTime lateDeliveryDate;
    private LocalDateTime insertDate;
    private String orderType;
    private boolean isSplittable;
    private String orStatus;
    private int priority;


    public OrderHeadData(String projectCode, String orderReleaseXID, String orderReleaseName,
                         String sourceLocationXID, String sourceLocationName,
                         String destLocationXID, String destLocationName,
                         String lateDeliveryDate, String insertDate,
                         String orderType, String isSplittable, String orStatus, String priority) {
        this.projectCode = projectCode;
        this.orderID = orderReleaseXID;
        this.orderName = orderReleaseName;
        this.sourceLocationID = sourceLocationXID;
        this.sourceLocationName = sourceLocationName;
        this.destLocationID = destLocationXID;
        this.destLocationName = destLocationName;
        this.lateDeliveryDate = LocalDateTime.parse(lateDeliveryDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.insertDate = LocalDateTime.parse(insertDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.orderType = orderType;
        this.isSplittable = "Y".equalsIgnoreCase(isSplittable);
        this.orStatus = orStatus;
        this.priority = Integer.parseInt(priority);
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

    public String getSourceLocationID() {
        return sourceLocationID;
    }

    public void setSourceLocationID(String sourceLocationID) {
        this.sourceLocationID = sourceLocationID;
    }

    public String getSourceLocationName() {
        return sourceLocationName;
    }

    public void setSourceLocationName(String sourceLocationName) {
        this.sourceLocationName = sourceLocationName;
    }

    public String getDestLocationID() {
        return destLocationID;
    }

    public void setDestLocationID(String destLocationID) {
        this.destLocationID = destLocationID;
    }

    public String getDestLocationName() {
        return destLocationName;
    }

    public void setDestLocationName(String destLocationName) {
        this.destLocationName = destLocationName;
    }

    public LocalDateTime getLateDeliveryDate() {
        return lateDeliveryDate;
    }

    public void setLateDeliveryDate(LocalDateTime lateDeliveryDate) {
        this.lateDeliveryDate = lateDeliveryDate;
    }

    public LocalDateTime getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(LocalDateTime insertDate) {
        this.insertDate = insertDate;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public boolean isSplittable() {
        return isSplittable;
    }

    public void setSplittable(boolean splittable) {
        isSplittable = splittable;
    }

    public String getOrStatus() {
        return orStatus;
    }

    public void setOrStatus(String orStatus) {
        this.orStatus = orStatus;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "OrderData{" +
                "projectCode='" + projectCode + '\'' +
                ", orderReleaseXID='" + orderID + '\'' +
                ", orderReleaseName='" + orderName + '\'' +
                ", sourceLocationXID='" + sourceLocationID + '\'' +
                ", sourceLocationName='" + sourceLocationName + '\'' +
                ", destLocationXID='" + destLocationID + '\'' +
                ", destLocationName='" + destLocationName + '\'' +
                ", lateDeliveryDate=" + lateDeliveryDate +
                ", insertDate=" + insertDate +
                ", orderType='" + orderType + '\'' +
                ", isSplittable=" + isSplittable +
                ", orStatus='" + orStatus + '\'' +
                ", priority=" + priority +
                '}';
    }
}
