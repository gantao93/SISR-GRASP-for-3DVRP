package core.opt;

import core.exception.Exception;
import core.exception.ExceptionType;
import core.opt.improvement.LocalSearchPacker;
import core.opt.objectives.BestFixedContainerSolver;
import core.opt.objectives.BestMinContainerSolver;
import core.opt.objectives.FastFixedContainerSolver;
import core.opt.objectives.FastMinContainerSolver;
import core.opt.objectives.MultiBinBestMinCostSolver;

import java.lang.reflect.InvocationTargetException;


public enum GRASPOptType {
	
	SINGLE_CONTAINER_OPTIMIZER(LocalSearchPacker.class),
	FAST_FIXED_CONTAINER_PACKER(FastFixedContainerSolver.class),
	BEST_FIXED_CONTAINER_PACKER(BestFixedContainerSolver.class),
	FAST_MIN_CONTAINER_PACKER(FastMinContainerSolver.class),
	BEST_MIN_CONTAINER_PACKER(BestMinContainerSolver.class),

	MULTIBIN_BEST_MIN_COST_PACKER(MultiBinBestMinCostSolver.class)
	;
	
	private final Class<? extends GRASPBase> clazz;

	GRASPOptType(Class<? extends GRASPBase> clazz) {
		this.clazz = clazz;
	}
	
	/**
	 * Creates an instance of the chosen opt type class in clazz.
	 * 
	 * @return An object instance
	 */
	public GRASPBase createInstance() throws Exception {
		try {
			return (GRASPBase) Class.forName(clazz.getName()).getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | ClassNotFoundException | NoSuchMethodException e) {
			throw new Exception(ExceptionType.ILLEGAL_STATE, "no copy of optimization procedure possible", e);
		}
	}
}
