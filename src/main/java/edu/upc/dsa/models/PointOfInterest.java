package edu.upc.dsa.models;

import java.util.ArrayList;
import java.util.List;

public class PointOfInterest {
    private int x;
    private int y;
    private ElementType type;
    private List<String> usersVisited;

    public PointOfInterest(int x, int y, ElementType type) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.usersVisited = new ArrayList<>();
    }

    public int getX() {return x;}
    public void setX(int x) {this.x = x;}

    public int getY() {return y;}
    public void setY(int y) {this.y = y;}

    public ElementType getType() {return type;}
    public void setType(ElementType type) {this.type = type;}

    public List<String> getUsersVisited() {
        return usersVisited;
    }

    public void addVisitor(String userId) {
        this.usersVisited.add(userId);
    }

    @Override
    public String toString() {
        return "PointOfInterest{" +
                "x=" + x +
                ", y=" + y +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        PointOfInterest that = (PointOfInterest) obj;

        return x == that.x && y == that.y && type == that.type;
    }

    @Override
    public int hashCode() {
        int result = Integer.hashCode(x);
        result = 31 * result + Integer.hashCode(y);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}