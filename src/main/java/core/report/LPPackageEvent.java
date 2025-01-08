package core.report;


import core.base.item.RotationType;

public record LPPackageEvent(
		 String id,
		 int x,
		 int y,
		 int z,
		 int w,
		 int l,
		 int h,
		 int w_origin,
		 int l_origin,
		 int h_origin,

		 String skuId,
		 String skuType,

		 long stackingGrp,
		 float weight,
		 float weightLimit,
		 boolean isInvalid,
		 LoadType type,
		 float usedVolumeInContainer,
		 float usedWeightInContainer,
		 int nbrStacksInContainer
) {
}
