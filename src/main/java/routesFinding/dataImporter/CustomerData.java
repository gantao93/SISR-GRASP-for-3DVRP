package routesFinding.dataImporter;
import lombok.AllArgsConstructor;
import lombok.Data;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.NoArgsConstructor;

public class CustomerData {
    private String projectCode;

    private String locationId;

    private String locationName;

    private String address;

    private double lat;

    private double lon;

    private Location location;

    private int duration;

    private int startTime;

    private int endTime;



    // Constructor
    public CustomerData(String projectCode, String locationId, String locationName, String address,
                        double latitude, double longitude, int loadingTime, int workingHourStart, int workingHourEnd) {
        this.projectCode = projectCode;
        this.locationId = locationId;
        this.locationName = locationName;
        this.address = address;
        this.lat = latitude;
        this.lon = longitude;
        this.location = new Location(lon,lat);
        this.duration = loadingTime;
        this.startTime = workingHourStart;
        this.endTime = workingHourEnd;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getLocationID() {
        return locationId;
    }

    public void setLocationID(String locationID) {
        this.locationId = locationID;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public Location getLocation(){
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "CustomerData{" +
                "projectCode='" + projectCode + '\'' +
                ", locationId='" + locationId + '\'' +
                ", locationName='" + locationName + '\'' +
                ", address='" + address + '\'' +
                ", latitude=" + lat +
                ", longitude=" + lon +
                ", loadingTime=" + duration +
                ", workingHourStart=" + startTime +
                ", workingHourEnd=" + endTime +
                '}';
    }

}
