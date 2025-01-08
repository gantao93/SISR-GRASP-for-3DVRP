package routesFinding.dataImporter;

public class DistanceServiceTimeData {
    private String sourceLocationID;
    private String destLocationID;
    private int serviceTime;
    private int distance;
    public DistanceServiceTimeData(String sourceLocationXID, String destLocationXID,
                                   int serviceTimeValue, int distanceValue) {
        this.sourceLocationID = sourceLocationXID;
        this.destLocationID = destLocationXID;
        this.serviceTime = serviceTimeValue;
        this.distance = distanceValue;
    }


    public String getSourceLocationID() {
        return sourceLocationID;
    }

    public void setSourceLocationID(String sourLocationID) {
        this.sourceLocationID = sourLocationID;
    }

    public String getDestLocationID() {
        return destLocationID;
    }

    public void setDestLocationID(String destLocationID) {
        this.destLocationID = destLocationID;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
    @Override
    public String toString() {
        return "DistanceServiceTime{" +
                "sourceLocationID='" + sourceLocationID + '\'' +
                ", destLocationID='" + destLocationID + '\'' +
                ", serviceTime=" + serviceTime +
                ", distance=" + distance +
                '}';
    }
}
