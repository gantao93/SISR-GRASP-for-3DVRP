package core.base.container;


public class SKU {
    private int id;
    private String skuId;
    private String type;
    private int length;
    private int width;
    private int height;
    private int x;
    private int y;
    private int z;
    private float weight;
    private int number;
    private String customer;
    private int maxStackedNum;

    private int group;//节点访问顺序

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHeight() {
        return height;
    }

    public int getNumber() {
        return number;
    }
    public float getWeight() {
        return weight;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public int getMaxStackedNum() {
        return maxStackedNum;
    }

    public void setMaxStackedNum(int maxStackedNum) {
        this.maxStackedNum = maxStackedNum;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }
}
