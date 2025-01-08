package core.opt;


public enum GRASPEnvType {

    PACKAGE("package"),

    PALLET("pallet"),

    VEHICLE("vehicle")
    ;
    public String value;

    GRASPEnvType(String value){
        this.value=value;
    }
}

