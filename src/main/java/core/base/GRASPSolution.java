package core.base;

import core.base.container.Container;
import core.base.fleximport.DataManager;
import core.base.item.Item;
import core.report.ContainerReport;
import core.report.LPPackageEvent;
import core.report.LPReport;


public class GRASPSolution {

	private final GRASPModel model;
	private final DataManager dataManager;

	public GRASPSolution(GRASPModel model, DataManager dataManager) {
		this.model = model;
		this.dataManager = dataManager;
	}

	public LPReport getReport() {
		LPReport rep = new LPReport();

		// Add packed containers to report
		for (Container con : model.getContainers()) {
			String containerTypeName = dataManager.getContainerTypeName(con.getContainerType());
			ContainerReport cRep = new ContainerReport(containerTypeName, con);

			for (Item item : con.getHistory()) {
				LPPackageEvent e = new LPPackageEvent(
						dataManager.getItemId(item.externalIndex),
						item.x,
						item.y,
						item.z,
						item.w,
						item.l,
						item.h,
						item.w_origin,
						item.l_origin,
						item.h_origin,
						item.skuId,
						item.type,
						item.stackingGroup,
						item.weight,
						item.stackingWeightLimit,
						false, // isInvalid
						item.loadingType,
						item.getVolume(),
						item.getWeight(),
						0 // NbrOfStacks
				);
				cRep.add(e);
			}

			rep.add(cRep);
		}

		// Add unplanned items to report
		for (Item unplannedItem : model.getUnplannedItems()) {
			LPPackageEvent e = new LPPackageEvent(
					dataManager.getItemId(unplannedItem.externalIndex),
					-1,
					-1,
					-1,
					unplannedItem.w,
					unplannedItem.l,
					unplannedItem.h,
					unplannedItem.w_origin,
					unplannedItem.l_origin,
					unplannedItem.h_origin,
					unplannedItem.skuId,
					unplannedItem.type,
					unplannedItem.stackingGroup,
					unplannedItem.weight,
					unplannedItem.stackingWeightLimit,
					false, // isInvalid
					unplannedItem.loadingType,
					unplannedItem.getVolume(),
					0,
					0 // NbrOfStacks
			);

			rep.addUnplannedPackages(e);
		}

		return rep;
	}

	public GRASPModel getModel() {
		return model;
	}
}
