package core.opt.objectives;

import core.exception.Exception;
import core.opt.GRASPBase;
import core.opt.construction.onetype.NContainerOneTypeAddPacker;
import core.report.LoadType;
import core.base.GRASPModel;
import core.base.item.Item;
import core.opt.construction.multitype.NContainerNTypeAddPacker;

/**
 * This solver tries to create very fastly a reasonable solution.
 *
 * It uses only construction heuristics.
 *
 * Goal: Place all items into a some instances of container types. The number of containers
 *       shall be minimal.
 */
public class FastMinContainerSolver extends GRASPBase {

    private final NContainerOneTypeAddPacker oneTypeAddPacker = new NContainerOneTypeAddPacker();
    private final NContainerNTypeAddPacker nTypeAddPacker = new NContainerNTypeAddPacker();

    @Override
    public void execute(GRASPModel model) throws Exception {
        if(isOnlyAddingItems(model)) {
            if(model.getContainerTypes().length > 1) {
                nTypeAddPacker.execute(model);
            } else {
                oneTypeAddPacker.execute(model);
            }
        } else {
            if(model.getContainerTypes().length > 1) {
                throw new UnsupportedOperationException("Currently add/removing and multiple container types is not supported");
            } else {
                throw new UnsupportedOperationException("Currently add/removing and single container types is not supported");
            }
        }
    }

    private boolean isOnlyAddingItems(GRASPModel model) {
        for (Item item : model.getItems()) {
            if(item.loadingType == LoadType.UNLOAD) {
                return false;
            }
        }

        return true;
    }
}
