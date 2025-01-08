package report;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.base.container.Vehicle;
import core.report.LPPackageEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
混合线路和装车结果
 */

public class SummaryReport {
    public Vehicle vehicle;
    public List<String> routes;
    public List<Integer> distances;
    public List<Integer> durations;
    public List<Report> bppResults;

    public SummaryReport(Vehicle vehicle, List<String> routes, List<Integer> distances, List<Integer> durations, List<Report> bppResults){
        this.vehicle = vehicle;
        this.routes = routes;
        this.distances = distances;
        this.durations = durations;
        this.bppResults = bppResults;
    }

    public void getReport() {
        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("status", 100);
        dataMap.put("msg", "执行成功");

        List<Map<String, Object>> dataList = new ArrayList<>();

        // route
        for(int i=0;i<routes.size();i++){
            Map<String, Object> vehicle = new HashMap<>();
            vehicle.put("vehicle", this.vehicle.getType());

            List<Map<String, Object>> vehicleData = new ArrayList<>();
            Map<String, Object> vehicleDataItem = new HashMap<>();
            vehicleDataItem.put("order", "");
            vehicleDataItem.put("start_time", "2023-08-15 17:11:57");
            vehicleDataItem.put("arrivial_time", "2023-08-15 21:28:19");
            vehicleDataItem.put("distance", 43427.0);
            vehicleDataItem.put("loading_rate", 0.34631379962192815);

            List<Map<String, Object>> vehicleStops = new ArrayList<>();
            List<Map<String, Object>> vehicleSupplier = new ArrayList<>();
            Map<String, Object> vehicleSupplierItem = new HashMap<>();
            vehicleSupplierItem.put("supplier", routes.get(i));

            List<Map<String, Object>> vehicleSupplierData = new ArrayList<>();
            List<Map<String, Object>> stopsDataList = new ArrayList<>();
            Map<String, Object> vehicleSupplierDataItem = new HashMap<>();

            for(LPPackageEvent item: this.bppResults.get(i).getPackedItems()){
                vehicleSupplierDataItem.put("sku_id",item.skuId());
                vehicleSupplierDataItem.put("ship_unit_spec", item.skuType());
                vehicleSupplierDataItem.put("length", item.l_origin()/1000);
                vehicleSupplierDataItem.put("width", item.w_origin()/1000);
                vehicleSupplierDataItem.put("height", item.h_origin()/1000);
                vehicleSupplierDataItem.put("rotation", getRotation(item.l_origin(), item.w_origin(),item.l(),item.w()));
                vehicleSupplierDataItem.put("cox", item.x()/1000);
                vehicleSupplierDataItem.put("coy", item.y()/1000);
                vehicleSupplierDataItem.put("coz", item.z()/1000);
                vehicleSupplierData.add(vehicleSupplierDataItem);
                stopsDataList.add(vehicleSupplierDataItem);
            }
            vehicleSupplierItem.put("data", stopsDataList);



        }

    }

    public String getRotation(int lOrigin, int wOrigin, int l, int w){
        if(lOrigin==l && wOrigin==w){
            return "XYZ";
        } else{
            return "YXZ";
        }
    }


}
