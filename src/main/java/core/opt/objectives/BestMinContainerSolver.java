package core.opt.objectives;

import core.base.GRASPModel;
import core.base.item.Item;
import core.exception.Exception;
import core.opt.GRASPBase;
import core.opt.construction.multitype.NContainerNTypeAddPacker;
import core.opt.construction.onetype.NContainerOneTypeAddPacker;
import core.opt.improvement.ItemOrderRandomSearchPacker;
import core.report.LoadType;

/**
 * It uses construction+local search heuristics.
 *
 * Goal: Place all items into a some instances of container types. The number of containers
 *       shall be minimal.
 *       满足全部物品被装载的最小化箱子数量
 *
 */

public class BestMinContainerSolver extends GRASPBase {
    private final NContainerOneTypeAddPacker nContainerOneTypeAddPacker = new NContainerOneTypeAddPacker();
    private final NContainerNTypeAddPacker nContainerNTypeAddPacker = new NContainerNTypeAddPacker();
    @Override
    public void execute(GRASPModel model) throws Exception {
        if(isOnlyAddingItems(model)) {
            if(model.getContainerTypes().length > 1) {
                new ItemOrderRandomSearchPacker(nContainerNTypeAddPacker).execute(model);
            } else {
                nContainerOneTypeAddPacker.execute(model);
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
