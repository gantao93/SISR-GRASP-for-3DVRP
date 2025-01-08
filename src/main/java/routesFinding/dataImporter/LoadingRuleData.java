package routesFinding.dataImporter;

public class LoadingRuleData {
    private String projectCode;
    private String loadingRuleId;
    private String shipUnitSpec1;
    private String shipUnitSpec2;
    private int compatible;
    private int consider;

    public LoadingRuleData(String projectCode, String loadingRuleId, String shipUnitSpec1,
                           String shipUnitSpec2, int compatible, int consider) {
        this.projectCode = projectCode;
        this.loadingRuleId = loadingRuleId;
        this.shipUnitSpec1 = shipUnitSpec1;
        this.shipUnitSpec2 = shipUnitSpec2;
        this.compatible = compatible;
        this.consider = consider;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getLoadingRuleId() {
        return loadingRuleId;
    }

    public void setLoadingRuleId(String loadingRuleID) {
        this.loadingRuleId = loadingRuleID;
    }

    public String getShipUnitSpec1() {
        return shipUnitSpec1;
    }

    public void setShipUnitSpec1(String shipUnitSpec1) {
        this.shipUnitSpec1 = shipUnitSpec1;
    }

    public String getShipUnitSpec2() {
        return shipUnitSpec2;
    }

    public void setShipUnitSpec2(String shipUnitSpec2) {
        this.shipUnitSpec2 = shipUnitSpec2;
    }

    public int getCompatible() {
        return compatible;
    }

    public void setCompatible(int compatible) {
        this.compatible = compatible;
    }

    public int getConsider() {
        return consider;
    }

    public void setConsider(int consider) {
        this.consider = consider;
    }
    @Override
    public String toString() {
        return "LoadingRule{" +
                "projectCode='" + projectCode + '\'' +
                ", loadingRuleId='" + loadingRuleId + '\'' +
                ", shipUnitSpec1='" + shipUnitSpec1 + '\'' +
                ", shipUnitSpec2='" + shipUnitSpec2 + '\'' +
                ", compatible=" + compatible +
                ", consider=" + consider +
                '}';
    }
}
