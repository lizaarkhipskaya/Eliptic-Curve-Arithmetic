import java.math.BigInteger;
import java.util.Arrays;

public class CalculateService {
 /*   public static final ElipticCurve E = new ElipticCurve(BigInteger.ONE,new BigInteger("5FF6108462A2DC8210AB403925E638A19C1455D21",16),new BigInteger("400000000000000000002BEC12BE2262D39BCF14D",16));
    public static final BigInteger TWO = BigInteger.valueOf(2);
    public static Point doublePoint(Point P){
        if(isNull(P)){
            return P;
        }
        Point R = new Point();
        BigInteger lyambda = P.getX().modPow(TWO,E.N).add(E.B).mod(E.N);
        //BigInteger x_R = P.getX().modPow(TWO, E.N).add(E.B.multiply(P.getX().modPow(TWO, E.N).modInverse(E.N))).mod(E.N);
        BigInteger x_R = lyambda.modPow(TWO,E.N);
        //BigInteger y_R = P.getX().modPow(TWO, E.N).add((P.getX().add(P.getY().multiply((P.getX().modInverse(E.N))))).multiply(x_R)).add(x_R).mod(E.N);
        BigInteger y_R = P.getY().add(BigInteger.ONE).add(lyambda.multiply(x_R.add(P.getX()))).mod(E.N);
        R.setX(x_R);
        R.setY(y_R);
        if(R.getX().equals(BigInteger.ZERO)){
            System.out.println("ZEROOOOOOOO");
            return R;
        }
        return R;
    }
    public static Point multiple(Point P, BigInteger n){
        String nInString = n.toString(2);
        Point R = getNull(P);
        char[]  array = nInString.toCharArray();
        int length = array.length;
        for (char b:
                array) {
            R = doublePoint(R);
            if(b=='1'){
                R = add(R,P);
                //System.out.println(R.getX().toString(10)+" "+R.getY().toString(10));
            }
        }
        return R;
    }

    public static Point add(Point P, Point Q) {
        if (isNull(P)){
            return Q;
        }
        if (isNull(Q)){
            return P;
        }
        Point R = new Point();
        ///R.setX(((P.getY().add(Q.getY())).multiply((P.getX().add(Q.getX())).modInverse(E.N))).pow(2).mod(E.N).add(((P.getY().add(Q.getY())).multiply((P.getX().add(Q.getX())).modInverse(E.N)))).add(P.getX()).add(Q.getX()).add(E.B).mod(E.N));
        //R.setY(P.getY().add(Q.getY()).multiply(P.getX().add(Q.getY()).modInverse(E.N)).multiply(P.getX().add(R.getX())).mod(E.N).add(R.getX()).add(P.getY()).mod(E.N));
        BigInteger lyambda = (P.getY().add(Q.getY())).multiply((P.getX().add(Q.getX())).modInverse(E.N));
        BigInteger x_R = (lyambda.modPow(TWO,E.N).add(P.getX()).add(Q.getX())).mod(E.N);
        BigInteger y_R = (P.getY().add(BigInteger.ONE).add(lyambda.multiply(x_R.add(P.getX())))).mod(E.N);
        R.setX(x_R);
        R.setY(y_R);
        return R;
    }
    public static Point getReverse(Point p){
        return new Point(p.getX(),p.getY().add(p.getX()).mod(E.N));
    }
    public static Point getNull(Point p){
        return new Point(null,null);
    }
    public static boolean isNull(Point p){
       return  p.getY()==null&&p.getX()==null;
    }
*/
}
