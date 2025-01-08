package core.base.container;

import java.math.BigDecimal;

public class Vehicle {
    private String type;

    private int length;

    private int width;

    private int height;


    public String getType() {
        return type;
    }

    public int getLength() {
        return length;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public BigDecimal getVolume(){
        BigDecimal containerLength = BigDecimal.valueOf(length);
        BigDecimal containerWidth = BigDecimal.valueOf(width);
        BigDecimal containerHeight = BigDecimal.valueOf(height);
        BigDecimal containerVolume = containerLength.multiply(containerWidth).multiply(containerHeight);
        return containerVolume;
    }
}
