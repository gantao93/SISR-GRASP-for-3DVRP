package solver;

import core.base.item.Position;
import report.Report;

import java.io.IOException;
import java.util.List;

public interface SolverInterface {
    void addPackage(int length, int width, int height, int maxWeight);

    void addItem(int length, int width, int height, float weight, int quantity, String skuId, String type, int group);

    void optimizer();

    Report getReport();
}
