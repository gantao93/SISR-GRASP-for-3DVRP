package core.opt;

import core.exception.Exception;
import core.base.GRASPModel;


public abstract class GRASPBase {
	/**
	 * 
	 * @param model Model contains items, container types, the resulting containers and rejected items
	 */
	public abstract void execute(GRASPModel model) throws Exception;
}
