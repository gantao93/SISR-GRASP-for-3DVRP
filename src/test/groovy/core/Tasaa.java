package core;

import com.alibaba.excel.EasyExcel;
import routesFinding.dataImporter.CustomerData;
import util.CsvReader;

import java.util.List;

public class Tasaa {
    public static void main(String[] args) {
        String csvFilePath = "D:/greedy_vrp-grasp_binpacking/container-loading-optimization/src/main/java/routesFinding/test/data/supplier.csv";
        List<CustomerData> customersList = new CsvReader().readCsv(csvFilePath, CustomerData.class);
        System.out.println(1111);
    }
}
