package app;

import core.base.container.Container;
import core.base.container.SKU;
import core.base.container.Vehicle;
import core.base.item.ItemType;
import core.base.item.Position;
import core.base.item.PositionType;
import report.Report;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.TimeProcess;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class App {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger("Container Loading Optimization Logger");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long startTime = System.currentTimeMillis();
        logger.info("Algorithm starts at " + df.format(startTime) + ".");

        Vehicle vehicle = new Vehicle();
        vehicle.setType("VEHICLE11");
        vehicle.setLength(9600);
        vehicle.setWidth(2300);
        vehicle.setHeight(2300);

        List<SKU> skuList = new ArrayList<>();
        SKU sku = new SKU();
        sku.setId(0);
        sku.setType("料架");
        sku.setLength(1500);
        sku.setWidth(800);
        sku.setHeight(1500);
        sku.setNumber(5);
        sku.setCustomer("武汉仓库");
        sku.setMaxStackedNum(15);
        skuList.add(sku);
        SKU sku1 = new SKU();
        sku1.setId(1);
        sku1.setType("料架");
        sku1.setLength(1500);
        sku1.setWidth(900);
        sku1.setHeight(1300);
        sku1.setNumber(4);
        sku1.setCustomer("上海仓库");
        sku1.setMaxStackedNum(15);
        skuList.add(sku1);
        // 如何初始化container信息的？
        List<ItemType> stackedItemList = new ArrayList<>();

        System.out.println("----正在执行第一次装箱---------");
//        List<Position> initActivePosition = new ArrayList<>();
//        initActivePosition.add(new Position(-1,0,0,0, PositionType.ROOT));

        Report report = new GraspBinPacker(vehicle, skuList,stackedItemList).run();
        // container信息保存到report中
        System.out.println(report);
        System.out.println(report.getPackedItems());
        System.out.println(report.getUnoccupiedPosition());
        List<Position> activePosition = report.getUnoccupiedPosition();
        System.out.println("----正在执行第二次装箱---------");
        List<SKU> skuList2 = new ArrayList<>();
        SKU sku2 = new SKU();
        sku2.setId(2);
        sku2.setType("料架");
        sku2.setLength(10);
        sku2.setWidth(90);
        sku2.setHeight(80);
        sku2.setNumber(3);
        sku2.setCustomer("上海仓库");
        sku2.setMaxStackedNum(15);
        skuList2.add(sku2);
        Report report1 = new GraspBinPacker(vehicle, skuList2,stackedItemList).run();
        System.out.println(report1.getPackedItems());
        System.out.println(report1.getUnoccupiedPosition());
        long endTime = System.currentTimeMillis();
        logger.info("Algorithm ends at " + df.format(endTime) + ", and total running time is " + TimeProcess.getDuration(endTime - startTime));
        System.exit(0);
    }
}
