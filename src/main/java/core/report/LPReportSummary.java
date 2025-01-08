package core.report;

import core.base.container.Container;

import java.util.HashMap;
import java.util.Map;


public class LPReportSummary {

	private static final int LENGTH = 2;
	
	private int nbrOfUsedVehicles = 0;
	private int nbrOfNotLoadedPackages = 0;
	private float utilizationSum = 0;
	
	private final Map<Container, float[]> dataMap = new HashMap<>();
	
	public void add(ContainerReport t) {
		if(!dataMap.containsKey(t.getContainer()))
			dataMap.put(t.getContainer(), new float[LENGTH]);
		float[] data = dataMap.get(t.getContainer());
		
		ContainerReportSummary routeSummary = t.getSummary();
		
		data[0]++;
		nbrOfUsedVehicles++;
		
		utilizationSum += routeSummary.getUtilization();
	}
	
	public void addUnplannedPackage(LPPackageEvent pkg) {
		if(pkg.type() == LoadType.LOAD)
			nbrOfNotLoadedPackages++;
	}

	public float getNbrOfUsedVehicles(Container veh) {
		return dataMap.get(veh)[0];
	}
	
	public float getNbrOfNotLoadedPackages(Container veh) {
		return dataMap.get(veh)[1];
	}
	
	public float getNbrOfUsedVehicles() {
		return nbrOfUsedVehicles;
	}
	
	public float getNbrOfNotLoadedPackages() {
		return nbrOfNotLoadedPackages;
	}
	
	public float getUtilization() {
		return (nbrOfUsedVehicles > 0) ? utilizationSum / nbrOfUsedVehicles : 0;
	}
}
