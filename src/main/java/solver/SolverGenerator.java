package solver;

import runtimeLog.ErrorEnum;
import runtimeLog.RuntimeLog;

import core.base.container.ContainerType;


public class SolverGenerator {
    public static SolverInterface setSolverByType(String packageType) {
        if (packageType.equals("package")){
            return new PackageSolver();
        } else if (packageType.equals("pallet")) {
            return new PalletSolver();
        } else if (packageType.equals("vehicle")) {
            return new VehicleSolver();
        } else {
            RuntimeLog.errExitSystem(ErrorEnum.NO_SUPPORTD_SCENARIOS, SolverGenerator.class, "仅支持装箱、装托和装车场景，不支持其他");
            return null;
        }
    }
    public static SolverInterface setSolverByType(ContainerType packageType) {
        if (packageType.equals(ContainerType.PACKAGE)){
            return new PackageSolver();
        } else if (packageType.equals(ContainerType.PALLET)) {
            return new PalletSolver();
        } else if (packageType.equals(ContainerType.VEHICLE)) {
            return new VehicleSolver();
        } else {
            RuntimeLog.errExitSystem(ErrorEnum.NO_SUPPORTD_SCENARIOS, SolverGenerator.class, "仅支持装箱、装托和装车场景，不支持其他");
            return null;
        }
    }

}
