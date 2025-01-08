package solver;

import core.GRASP;
import core.base.item.Position;
import core.opt.GRASPOptType;
import report.Report;

import java.io.IOException;
import java.util.List;

public class PackageSolver implements SolverInterface {
    private GRASP grasp = new GRASP();

    public PackageSolver() {
        this.grasp.setTypeOfOptimization(GRASPOptType.BEST_FIXED_CONTAINER_PACKER);
    }

    public void addPackage(int length, int width, int height, int maxWeight){
        this.grasp.addContainer().setLength(length).setWidth(width).setHeight(height).setMaxWeight(maxWeight).setContainerType("package");
    }

    public void addItem(int length, int width, int height, float weight, int quantity, String skuId, String type, int group){
        for (int i = 1; i <= quantity; i++){
            this.grasp.addItem().setLength(length).setWidth(width).setHeight(height).setWeight(weight).setSpinnable(true).setSkuId(skuId).setType(type).setGroup(group);
        }
    }

    public void optimizer(){
        grasp.executeLoadPlanning();
    }

    public Report getReport() {
        ReportSolver report = new ReportSolver();
        return report.getResult(grasp);
    }
}
