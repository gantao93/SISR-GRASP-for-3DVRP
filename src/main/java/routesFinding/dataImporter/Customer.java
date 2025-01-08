package routesFinding.dataImporter;

import core.base.container.SKU;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String locationId;
    private double lon;
    private double lat;
    private Location location;
    private List<SKU> skuList;
    private int duration;


    public void addSku(SKU sku) {
        if (this.skuList == null) {
            this.skuList = new ArrayList<>();
        }
        this.skuList.add(sku);
    }


    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationName) {
        this.locationId = locationName;
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<SKU> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<SKU> skuList) {
        this.skuList = skuList;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }


}
