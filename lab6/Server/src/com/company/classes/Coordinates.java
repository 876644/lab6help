package com.company.classes;

public class Coordinates implements Comparable<Coordinates>{//типизированный интерфейс, то есть, требует указания конкретного класса, с которым он связан.
    private Double x;
    private Integer y;

    public Coordinates( Double x, Integer y){
        this.x = x;
        this.y = y;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    @Override
    public int compareTo(Coordinates o) {
        return 0;
    }
}
