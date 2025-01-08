package util;
import core.GRASP;

public class Application {

	public static void main(String[] args) {
		try {
			new GRASP().executeLoadPlanning();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
