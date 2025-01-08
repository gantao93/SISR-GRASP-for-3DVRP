package util;


import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * CSV File Reader Based on EasyExcel
 */
@Slf4j
public class CsvReader<T> {

    @Getter
    private final List<T> cachedDataList = new ArrayList<>();

    public List<T> readCsv(String filePath, Class<T> clazz) {
        System.out.printf("Reading CSV file from %s\n", filePath);
        EasyExcelFactory.read(filePath, clazz, new ReadListener<T>() {

            @Override
            public void invoke(T data, AnalysisContext context) {
                cachedDataList.add(data);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
                System.out.printf("Successfully reading CSV file from %s\n", filePath);
            }

        }).excelType(ExcelTypeEnum.CSV).sheet().doRead();

        return cachedDataList;
    }
}

