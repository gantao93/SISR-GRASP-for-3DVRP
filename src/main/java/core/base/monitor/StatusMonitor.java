package core.base.monitor;

/**
 * Interface for Status Monitor Objects, which can be used to
 * transfer planning informations from inside the XFVRP package
 * to the user.
 */
public interface StatusMonitor {

	/**
	 * Method is called, when a message occurs in the planning suite.
	 * 
	 * @param code Type of the message
	 * @param message The message itself
	 */
	public void getMessage(StatusCode code, String message);
	
}
