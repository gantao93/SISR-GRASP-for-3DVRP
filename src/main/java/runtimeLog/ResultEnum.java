package runtimeLog;

public enum ResultEnum {
    SUCCESS("Success"),
    InFeasible("No Feasible Solution"),
    FAIL("Fail");

    String value;

    ResultEnum(String value){
        this.value = value;
    }
}
