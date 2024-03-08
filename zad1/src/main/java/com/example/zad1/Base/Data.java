package com.example.zad1.Base;

import java.io.Serializable;

public class Data implements Serializable {
    private final double x;
    private final double y;

    public Data(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Data data)) return false;

        return new org.apache.commons.lang3.builder.EqualsBuilder().append(getX(), data.getX()).append(getY(), data.getY()).isEquals();
    }

    @Override
    public int hashCode() {
        return new org.apache.commons.lang3.builder.HashCodeBuilder(17, 37).append(getX()).append(getY()).toHashCode();
    }

    @Override
    public String toString() {
        return "Data{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
