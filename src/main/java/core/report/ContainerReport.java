package core.report;

import core.base.container.Container;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/** 

 * LPReport of a route by a list of events
 * 
 * The container report represents the data content of
 * a load planning solution for this specific container.
 *
 */
public class ContainerReport implements Iterable<LPPackageEvent> {

	private final ContainerReportSummary summary;
	private final String containerTypeName;
	private final Container container;
	private final List<LPPackageEvent> packageEventList = new ArrayList<>();
	
	public ContainerReport(String containerTypeName, Container con) {
		this.containerTypeName = containerTypeName;
		this.container = con;
		this.summary = new ContainerReportSummary(con);
	}
	
	public void add(LPPackageEvent e) {
		summary.add(e);
		packageEventList.add(e);
	}

	public Container getContainer() {
		return container;
	}

	public ContainerReportSummary getSummary() {
		return summary;
	}
	
	public List<LPPackageEvent> getPackageEvents() {
		return packageEventList;
	}

	public String getContainerTypeName() {
		return containerTypeName;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<LPPackageEvent> iterator() {
		return packageEventList.iterator();
	}
}
