package solver;

import core.GRASP;
import core.base.item.Position;
import core.opt.GRASPOptType;
import report.Report;

import java.io.IOException;
import java.util.List;

public class PalletSolver implements SolverInterface {
    private GRASP grasp = new GRASP();

    public PalletSolver() {
        this.grasp.setTypeOfOptimization(GRASPOptType.FAST_FIXED_CONTAINER_PACKER);
    }
    @Override
    public void addPackage(int length, int width, int height, int maxWeight) {
        this.grasp.addContainer().setLength(length).setWidth(width).setHeight(height).setMaxWeight(maxWeight).setContainerType("pallet");
    }

    @Override
    public void addItem(int length, int width, int height, float weight, int quantity, String skuId, String type, int group){
        for (int i = 1; i <= quantity; i++){
            this.grasp.addItem().setLength(length).setWidth(width).setHeight(height).setWeight(weight).setSkuId(skuId).setType(type).setGroup(group);
        }
    }

    @Override
    public void optimizer() {
        grasp.executeLoadPlanning();
    }

    @Override
    public Report getReport() {
        ReportSolver report = new ReportSolver();
        return report.getResult(grasp);
    }

}
