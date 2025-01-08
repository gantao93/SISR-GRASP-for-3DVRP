package core.opt.objectives;

import core.base.item.Item;
import core.exception.Exception;
import core.opt.GRASPBase;
import core.opt.construction.multitype.OneContainerNTypeAddPacker;
import core.opt.construction.onetype.OneContainerOneTypeAddPacker;
import core.opt.construction.onetype.OneContainerOneTypePacker;
import core.opt.improvement.ItemOrderByGroupRandomSearchPacker;
import core.opt.improvement.ItemOrderRandomSearchPacker;
import core.report.LoadType;
import core.base.GRASPModel;

/**
 * Goal: Create a solution with minimal number of unplanned items.
 * 最大化装载率
 * Heuristic: Shuffling of items order. Neighborhood search is very heavy even for small problems.
 *
 */
public class BestFixedContainerSolver extends GRASPBase {

    private final OneContainerOneTypePacker oneTypePacker = new OneContainerOneTypePacker();
    private final OneContainerOneTypeAddPacker oneTypeAddPacker = new OneContainerOneTypeAddPacker();
    private final OneContainerNTypeAddPacker nTypeAddPacker = new OneContainerNTypeAddPacker();

    @Override
    public void execute(GRASPModel model) throws Exception {
        if(isOnlyAddingItems(model)) {
            if(model.getContainerTypes().length > 1) {
                new ItemOrderRandomSearchPacker(nTypeAddPacker).execute(model);
            } else {
//                new ItemOrderRandomSearchPacker(oneTypeAddPacker).execute(model);
                new ItemOrderByGroupRandomSearchPacker(oneTypeAddPacker).execute(model);
            }
        } else {
            if(model.getContainerTypes().length > 1) {
                throw new UnsupportedOperationException("Currently add/removing and multiple container types is not supported");
            } else {
                new ItemOrderByGroupRandomSearchPacker(oneTypePacker).execute(model);
            }
        }
    }

    // 物品只装不卸
    private boolean isOnlyAddingItems(GRASPModel model) {
        for (Item item : model.getItems()) {
            if(item.loadingType == LoadType.UNLOAD) {
                return false;
            }
        }

        return true;
    }
}
