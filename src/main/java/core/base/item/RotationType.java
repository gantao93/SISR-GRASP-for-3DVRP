package core.base.item;

public enum RotationType {

    XYZ(0),
    XZY(1),
    YXZ(2),
    YZX(3),
    ZXY(4),
    ZYX(5);

    public int getRotationType() {
        return rotationType;
    }

    private int rotationType;

    RotationType(int rotationType) {
        this.rotationType = rotationType;
    }
}
