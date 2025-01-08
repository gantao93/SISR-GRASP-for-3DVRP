package core.base.item;


public record Space(int l, int w, int h) {

    public static Space of(int l, int w, int h) {
        return new Space(l, w, h);
    }

    @Override
    public String toString() {
        return "(w:" + w +
                " l:" + l +
                " h:" + h + ")";
    }
}
