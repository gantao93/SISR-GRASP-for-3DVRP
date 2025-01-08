package solver;

import core.GRASP;
import core.base.container.Container;
import core.base.item.Position;
import core.report.ContainerReport;
import core.report.ContainerReportSummary;
import core.report.LPPackageEvent;
import core.report.LPReport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import report.Report;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.constant.Constable;
import java.math.RoundingMode;
import java.util.*;
import java.math.BigDecimal;


public class ReportSolver {

    public Report getResult(GRASP grasp) {
        LPReport report = grasp.getReport();
        Report summaryReport = new Report();
        for(ContainerReport rep: report.getContainerReports()) {
            Container container = rep.getContainer();
            int length = rep.getContainer().getLength();
            int width = rep.getContainer().getWidth();
            int height = rep.getContainer().getHeight();
            summaryReport.setContainer(container);
            List<LPPackageEvent> packedItemList = new ArrayList<>();
            for (LPPackageEvent item : rep.getPackageEvents()) {
                packedItemList.add(item);
            }
            summaryReport.setPackedItems(packedItemList);
            BigDecimal itemsTotalVolumes = BigDecimal.valueOf(0);
            for(LPPackageEvent lpPackageEvent:packedItemList){
                BigDecimal itemLength = BigDecimal.valueOf(lpPackageEvent.l());
                BigDecimal itemWidth = BigDecimal.valueOf(lpPackageEvent.w());
                BigDecimal itemHeight = BigDecimal.valueOf(lpPackageEvent.h());
                BigDecimal itemVolume = itemLength.multiply(itemWidth).multiply(itemHeight);
                itemsTotalVolumes = itemsTotalVolumes.add(itemVolume);
            }
            System.out.println("packed items volume "+itemsTotalVolumes);
            BigDecimal containerLength = BigDecimal.valueOf(length);
            BigDecimal containerWidth = BigDecimal.valueOf(width);
            BigDecimal containerHeight = BigDecimal.valueOf(height);
            BigDecimal containerVolume = containerLength.multiply(containerWidth).multiply(containerHeight);
            System.out.println("container volume "+containerVolume);
            BigDecimal loadingRate = itemsTotalVolumes.divide(containerVolume,3, RoundingMode.HALF_UP);
            System.out.println("loading rate "+ loadingRate);
            summaryReport.setLoadingRate(loadingRate);

            List<LPPackageEvent> unpackedItemList = new ArrayList<>();
            for (LPPackageEvent item : report.getUnplannedPackages()) {
                unpackedItemList.add(item);
            }
            summaryReport.setUnpackedItems(unpackedItemList);

            List<Position> activePositions = container.getActivePositions();
            summaryReport.setUnoccupiedPosition(activePositions);
        }

        return summaryReport;
    }
}
