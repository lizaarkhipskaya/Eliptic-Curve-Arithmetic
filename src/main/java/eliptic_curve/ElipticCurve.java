package eliptic_curve;

import field.Field;
import field.FieldElement;
import lombok.Builder;

import java.math.BigInteger;

@Builder
public class ElipticCurve {
    public Field field;
    public FieldElement A;
    public FieldElement B;
    public BigInteger N;
    public Point P;


    public Point add(Point p1, Point p2){
        if(p1.isNull(field)){
            return new Point(p2.getX(),p2.getY());
        }
        if(p2.isNull(field)){
            return new Point(p1.getX(),p1.getY());
        }


        //FieldElement lyambda = (p1.getY().add(p2.getY())).multiply((p1.getX().add(p2.getX())).inverse());
        FieldElement lyambda = field.multiply(field.add(p1.getY(),p2.getY()),field.inverse(field.add(p1.getX(),p2.getX())));
        //lyambda =              field.multiply(field.add(p1.getY(),p2.getY()),field.inverse(field.add(p1.getX(),p2.getX())));
        //FieldElement p3X = lyambda.pow2().add(lyambda).add(p1.getX()).add(p2.getX()).add(A);
        FieldElement p3X = field.add(field.pow2(lyambda),field.add(lyambda,field.add(p1.getX(),field.add(p2.getX(),A))));
        //FieldElement p3Y =  p1.getY().add(p3X).add(lyambda.multiply(p3X.add(p1.getX())));
        FieldElement p3Y =  field.add(field.add(p1.getY(),p3X),field.multiply(lyambda,field.add(p3X,p1.getX())));
        return new Point(p3X,p3Y);
    }

    public Point doublePoint(Point p1){
        if(p1.isNull(field)){
            return Point.getZeroPoint(field);
        }
        //FieldElement lyambda = p1.getX().add(p1.getY().multiply(p1.getX().inverse()));
        FieldElement lyambda =field.add(p1.getX(),field.multiply(p1.getY(),field.inverse(p1.getX())));
        //FieldElement p2X = p1.getX().pow2().add(B.multiply(p1.getX().pow2().inverse()));
        FieldElement p2X = field.add(field.pow2(p1.getX()),field.multiply(B,field.inverse(field.pow2(p1.getX()))));
        //FieldElement p2Y = p1.getX().pow2().add(p2X.multiply(lyambda.add(FieldElement.getOne())));
        FieldElement p2Y = field.add(field.pow2(p1.getX()),field.multiply(p2X,field.add(lyambda,field.getOne())));
        return new Point(p2X,p2Y);
    }

    public Point multiply(Point p1, BigInteger e){

        char[] eInCharArray = e.toString(2).toCharArray();
        int[] eInBitArray = new int[eInCharArray.length];

        for(int i = 0 ; i < eInCharArray.length; i++){
            eInBitArray[i] = Integer.valueOf(eInCharArray[eInCharArray.length-1-i]+"");
        }

        Point r = new Point(field);
        Point p2 = new Point(p1.getX(),p1.getY());

        for (int i = 0 ; i < eInBitArray.length ; i++){
            if(eInBitArray[i] == 1){
                r = add(r,p2);
            }
            p2 = doublePoint(p2);
        }
        return r;
    }

    public Point getInverse(Point p1){
        return new Point(p1.getX(),field.add(p1.getX(),p1.getY()));
    }
}
