package core.base.item;

public enum TwoRotationType {
    XYZ(0),
    YXZ(1);

    public int getRotationType() {
        return rotationType;
    }

    private int rotationType;

    TwoRotationType(int rotationType) {
        this.rotationType = rotationType;
    }

}
