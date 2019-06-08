package eliptic_curve;

import field.Field;
import field.FieldElement;

import java.math.BigInteger;

public class Point {
    private FieldElement x;
    private FieldElement y;

    public Point(Field field) {
        x = new FieldElement(field.m);
        y = new FieldElement(field.m);
    }

    public Point(FieldElement x, FieldElement y) {
        this.x = x;
        this.y = y;
    }

    public Point(BigInteger x, BigInteger y,Field field){
        this.x = new FieldElement(x,field.m);
        this.y = new FieldElement(y,field.m);
    }
    public static Point getZeroPoint(Field field){
        return new Point(field);
    }

    public FieldElement getX() {
        return x;
    }

    public void setX(FieldElement x) {
        this.x = x;
    }

    public FieldElement getY() {
        return y;
    }

    public void setY(FieldElement y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "eliptic_curve.Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public boolean isNull(Field field) {
        Point point = new Point(field);
        return field.cmp(point.getX(),x)==0&&field.cmp(point.getY(),y)==0;
    }
}
