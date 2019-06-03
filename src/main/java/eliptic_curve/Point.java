package eliptic_curve;

import field.FieldElement;

import java.math.BigInteger;

public class Point {
    private FieldElement x;
    private FieldElement y;

    public Point() {
        x = new FieldElement();
        y = new FieldElement();
    }

    public Point(FieldElement x, FieldElement y) {
        this.x = x;
        this.y = y;
    }

    public Point(BigInteger x, BigInteger y){
        this.x = new FieldElement(x);
        this.y = new FieldElement(y);
    }
    public static Point getZeroPoint(){
        return new Point();
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

    public boolean isNull() {
        Point point = new Point();
        return point.getX().cmp(x)==0&&point.getY().cmp(y)==0;
    }
}
