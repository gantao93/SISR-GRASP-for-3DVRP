package core.opt.objectives;

import core.exception.Exception;
import core.opt.GRASPBase;
import core.opt.construction.onetype.OneContainerOneTypePacker;
import core.report.LoadType;
import core.base.GRASPModel;
import core.base.item.Item;
import core.opt.construction.multitype.OneContainerNTypeAddPacker;
import core.opt.construction.onetype.OneContainerOneTypeAddPacker;

/**
 * This solver tries to create very fastly a reasonable solution.
 *
 * It uses only construction heuristics.
 *
 * Goal: All items should be packed into a single set of container types.
 *       If items are not fitting, then they will be placed in separate list. (unplanned)
 *
 */
public class FastFixedContainerSolver extends GRASPBase {

    private final OneContainerOneTypePacker oneTypePacker = new OneContainerOneTypePacker();
    private final OneContainerOneTypeAddPacker oneTypeAddPacker = new OneContainerOneTypeAddPacker();
    private final OneContainerNTypeAddPacker nTypeAddPacker = new OneContainerNTypeAddPacker();

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
                oneTypePacker.execute(model);
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
