package core.base.monitor;

/**
 * The default status monitor, which prints the messages to the 
 * command line.
 */
public class DefaultStatusMonitor implements StatusMonitor {

	/*
	 * (non-Javadoc)
	 * @see de.fhg.iml.vlog.xfvrp.base.monitor.StatusMonitor#getMessage(de.fhg.iml.vlog.xfvrp.base.monitor.StatusCode, java.lang.String)
	 */
	@Override
	public void getMessage(StatusCode code, String message) {
		System.out.println("["+code.name()+"] "+message);
	}
	
}
