package core.report;

import core.base.container.Container;


/**
 * Summary of a values for a route.
 * Events can be added, where the values are updated directly.
 */
public class ContainerReportSummary {

	private int nbrOfLoadedPackages = 0;
	private int nbrOfUnLoadedPackages = 0;
	private float maxUsedVolume = 0;
	private float maxVolume = 0;
	private float maxUsedWeight = 0;

	public ContainerReportSummary(Container con) {
		maxVolume = con.getHeight() * con.getLength() * con.getWidth();
	}
	
	public void add(LPPackageEvent e) {
		if(e.type() == LoadType.LOAD) {
			nbrOfLoadedPackages++;
			// Only loaded items increase the max loaded volume/weight values
			maxUsedVolume += e.usedVolumeInContainer();
			maxUsedWeight = Math.max(maxUsedWeight, e.usedWeightInContainer());
		} else if(e.type() == LoadType.UNLOAD) {
			nbrOfUnLoadedPackages++;
		}
	}

	/**
	 * @return the nbrOfLoadedPackages
	 */
	public final int getNbrOfLoadedPackages() {
		return nbrOfLoadedPackages;
	}

	/**
	 * @return the nbrOfUnLoadedPackages
	 */
	public final int getNbrOfUnLoadedPackages() {
		return nbrOfUnLoadedPackages;
	}

	/**
	 * @return the maxUsedVolume
	 */
	public final float getMaxUsedVolume() {
		return maxUsedVolume;
	}

	/**
	 * @return the maxUsedWeight
	 */
	public final float getMaxUsedWeight() {
		return maxUsedWeight;
	}
	
	/**
	 * 
	 * @return max volume
	 */
	public float getMaxVolume() {
		return maxVolume;
	}
	
	/**
	 * 
	 * @return utilization of container
	 */
	public float getUtilization() {
		return (maxVolume > 0) ? maxUsedVolume / maxVolume : 0;
	}
}
