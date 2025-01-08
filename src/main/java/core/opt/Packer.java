package core.opt;

import core.exception.Exception;
import core.base.GRASPModel;

/**
 * Construction heuristics are packers, which create a solution out of an model.
 *
 * In some cases a planning strategy can be given, which influences the way of construction.
 */
public interface Packer {

    void execute(GRASPModel model) throws Exception;
}
