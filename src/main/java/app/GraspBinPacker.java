package app;

import core.base.container.Container;
import core.base.container.ContainerType;
import core.base.container.SKU;
import core.base.container.Vehicle;
import core.base.item.Item;
import core.base.item.ItemType;
import core.base.item.Position;
import report.Report;
import solver.SolverGenerator;
import solver.SolverInterface;


import java.util.List;

public class GraspBinPacker {
    private Vehicle vehicle;
    private List<SKU> itemList;
    private List<ItemType> stackedItemList;

    public GraspBinPacker(Vehicle vehicle, List<SKU> itemList, List<ItemType> stackedItemList) {
        this.vehicle = vehicle;
        this.itemList = itemList;
        this.stackedItemList = stackedItemList;
    }

    public Report run(){
        SolverInterface solver = SolverGenerator.setSolverByType(ContainerType.VEHICLE);

        solver.addPackage(vehicle.getLength(), vehicle.getWidth(), vehicle.getHeight(), 9999);

        for(SKU item:itemList) {
            solver.addItem(item.getLength(), item.getWidth(), item.getHeight(), item.getWeight(), item.getNumber(), item.getSkuId(), item.getType(), item.getGroup());
        }

        solver.optimizer();
        return solver.getReport();
    }
}
