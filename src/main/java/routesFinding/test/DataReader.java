package routesFinding.test;
import com.alibaba.excel.EasyExcel;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import routesFinding.dataImporter.*;
import util.CsvReader;

public class DataReader {

    public List<CustomerData> customerData(){
        String csvFilePath = "D:/greedy_vrp-grasp_binpacking/container-loading-optimization/src/main/java/routesFinding/test/data/supplier.csv";
        List<CustomerData> customersList = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(csvFilePath))) {
            String[] nextRecord;

            // Skip the header line
            csvReader.readNext();
            while ((nextRecord = csvReader.readNext()) != null) {
                // Process each line
                CustomerData customerData = new CustomerData(
                        nextRecord[0], nextRecord[1], nextRecord[2], nextRecord[3],
                        Double.parseDouble(nextRecord[4]), Double.parseDouble(nextRecord[5]),
                        Integer.parseInt(nextRecord[6]), Integer.parseInt(nextRecord[7]),
                        Integer.parseInt(nextRecord[8])
                );
                // Do something with the LocationData object
//                System.out.println(customerData);
                customersList.add(customerData);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        return customersList;
    }

    public List<OrderDetailData> orderDetailData(){
        String csvFilePath = "D:/greedy_vrp-grasp_binpacking/container-loading-optimization/src/main/java/routesFinding/test/data/order_detail.csv";
        List<OrderDetailData> orderDetailsListData = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(csvFilePath))) {
            String[] nextRecord;

            // Skip the header line
            csvReader.readNext();
            while ((nextRecord = csvReader.readNext()) != null) {
                // Process each line
                OrderDetailData orderDetailData = new OrderDetailData(
                        nextRecord[0], nextRecord[1], nextRecord[2],
                        nextRecord[3], nextRecord[4],
                        Integer.parseInt(nextRecord[5]), Integer.parseInt(nextRecord[6]),
                        Double.parseDouble(nextRecord[7]), nextRecord[8], nextRecord[9]);
                // Do something with the LocationData object
//                System.out.println(orderDetailData);
                orderDetailsListData.add(orderDetailData);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        return orderDetailsListData;
    }

    public List<OrderHeadData> orderHeadData(){
        String csvFilePath = "D:/greedy_vrp-grasp_binpacking/container-loading-optimization/src/main/java/routesFinding/test/data/order_head.csv";
        List<OrderHeadData> orderHeadsListData = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(csvFilePath))) {
            String[] nextRecord;

            // Skip the header line
            csvReader.readNext();
            while ((nextRecord = csvReader.readNext()) != null) {
                // Process each line
                OrderHeadData orderHeadData = new OrderHeadData(
                        nextRecord[0], nextRecord[1], nextRecord[2],
                        nextRecord[3], nextRecord[4],
                        nextRecord[5], nextRecord[6],
                        nextRecord[7], nextRecord[8],
                        nextRecord[9], nextRecord[10], nextRecord[11], nextRecord[12]
                );
//                System.out.println(orderHeadData);
                orderHeadsListData.add(orderHeadData);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        return orderHeadsListData;
    }

    public List<EquipmentData> equipmentData(){
        String csvFilePath = "D:/greedy_vrp-grasp_binpacking/container-loading-optimization/src/main/java/routesFinding/test/data/equipment.csv";
        List<EquipmentData> equipmentDataList = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(csvFilePath))) {
            String[] nextRecord;

            csvReader.readNext();

            while ((nextRecord = csvReader.readNext()) != null) {
                EquipmentData equipmentData = new EquipmentData(
                        nextRecord[0], nextRecord[1], nextRecord[2],
                        (int) (Double.parseDouble(nextRecord[3])*1000), (int) (Double.parseDouble(nextRecord[4])*1000),
                        (int) (Double.parseDouble(nextRecord[5])*1000), Double.parseDouble(nextRecord[6]),
                        Double.parseDouble(nextRecord[7]), Integer.parseInt(nextRecord[8]),
                        Double.parseDouble(nextRecord[9]), Double.parseDouble(nextRecord[10]),
                        Integer.parseInt(nextRecord[11]), nextRecord[12]
                );

                // Do something with the VehicleData object
//                System.out.println(equipmentData);
                equipmentDataList.add(equipmentData);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        return equipmentDataList;
    }

    public List<LoadingRuleData> loadingRuleData(){
        String csvFilePath = "D:/greedy_vrp-grasp_binpacking/container-loading-optimization/src/main/java/routesFinding/test/data/loading_rule.csv";
        List<LoadingRuleData> loadingRuleList = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(csvFilePath))) {
            String[] nextRecord;

            // Skip the header line
            csvReader.readNext();

            while ((nextRecord = csvReader.readNext()) != null) {
                // Process each line
                LoadingRuleData loadingRuleData = new LoadingRuleData(
                        nextRecord[0], nextRecord[1], nextRecord[2],
                        nextRecord[3], Integer.parseInt(nextRecord[4]),
                        Integer.parseInt(nextRecord[5])
                );

                // Do something with the LoadingRuleData object
//                System.out.println(loadingRuleData);
                loadingRuleList.add(loadingRuleData);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        return loadingRuleList;

    }

    public List<SKUpfepData> skUpfepsData() {
        String csvFilePath = "D:/greedy_vrp-grasp_binpacking/container-loading-optimization/src/main/java/routesFinding/test/data/pfep.csv";
        List<SKUpfepData> skUpfepDataList = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(csvFilePath))) {
            String[] nextRecord;
            // Skip the header line
            csvReader.readNext();
            while ((nextRecord = csvReader.readNext()) != null) {
                // Process each line
                SKUpfepData sku = new SKUpfepData(
                        nextRecord[0], nextRecord[1], nextRecord[2],
                        (int) (Double.parseDouble(nextRecord[3])*1000), (int)(Double.parseDouble(nextRecord[4])*1000),
                        (int) (Double.parseDouble(nextRecord[5])*1000),
                        nextRecord[6].isEmpty() ? null : Double.parseDouble(nextRecord[6]),
                        nextRecord[7].isEmpty() ? null : Double.parseDouble(nextRecord[7]),
                        nextRecord[8], nextRecord[9], nextRecord[10].isEmpty() ? null : Double.parseDouble(nextRecord[10])
                );

                // Do something with the SKUData object
//                System.out.println(sku);
                skUpfepDataList.add(sku);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        return skUpfepDataList;
    }

    public List<DistanceServiceTimeData> distanceServiceTimesData(){
        String csvFilePath = "D:/greedy_vrp-grasp_binpacking/container-loading-optimization/src/main/java/routesFinding/test/data/service_time.csv";
        List<DistanceServiceTimeData> distanceServiceTimesListData = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(csvFilePath))) {
            String[] nextRecord;

            // Skip the header line
            csvReader.readNext();

            while ((nextRecord = csvReader.readNext()) != null) {
                // Process each line
                DistanceServiceTimeData distanceServiceTimeData = new DistanceServiceTimeData(
                        nextRecord[0], nextRecord[1],
                        Integer.parseInt(nextRecord[2]), (int) (Double.parseDouble(nextRecord[3])*1000)
                );

                // Do something with the LocationData object
//                System.out.println(distanceServiceTimeData);
                distanceServiceTimesListData.add(distanceServiceTimeData);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        return distanceServiceTimesListData;

    }

    public static void main(String[] args) {
        DataReader dataReader = new DataReader();
        dataReader.customerData();
        dataReader.orderDetailData();
        dataReader.orderHeadData();
        dataReader.equipmentData();
        dataReader.loadingRuleData();
        dataReader.skUpfepsData();
        dataReader.distanceServiceTimesData();
    }

}
