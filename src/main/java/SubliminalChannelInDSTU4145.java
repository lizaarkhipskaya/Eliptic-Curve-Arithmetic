import eliptic_curve.ElipticCurve;
import eliptic_curve.Point;
import field.FieldElement;

import java.math.BigInteger;
import java.util.Random;

public class SubliminalChannelInDSTU4145 {
    public static final BigInteger TWO   = BigInteger.valueOf(2);
    public  ElipticCurve curve;

    public SubliminalChannelInDSTU4145(ElipticCurve curve){
        this.curve = curve;
    }

    public void determineSubliminalChannel(){

    }

    public void narrowbandChannel(int message){{
        String messageString = BigInteger.valueOf(message).toString(2);
        char[] messageInArray = messageString.toCharArray();
        for(char c :
        messageInArray){
            print("Bit in subliminal message ",c);
            if(c =='0')
                getBitInFirstNarrowbandChannel(null,0);
            else
                getBitInFirstNarrowbandChannel(null,1);
        }
    }

    }
    public BigInteger getBitInFirstNarrowbandChannel(BigInteger message, int subliminalbit){
        BigInteger e = null;
        BigInteger r = null;
        int counter = 0;
        do{
            do{
                e = BigInteger.probablePrime(163, new Random());
            }while (!e.isProbablePrime(50));
            print("e maybe right",e.toString(16));
            r = GetSignOnlyWithR(e,message);
            counter++;
        }while (!checkIfRightBit(isQuadricResudo(r,e),subliminalbit));

        BigInteger s = e.add(getD().multiply(r)).mod(curve.N);
        print("s ",s.toString(16));
        print("e in subliminal message",e.toString(16));
        print("counter",counter);
        return e;
    }

    public boolean checkIfRightBit(boolean isQuadric, int suliminalBit){
        if(isQuadric&&suliminalBit==1) return true;
        if(!isQuadric&&suliminalBit==0) return true;
        return false;
    }

    public BigInteger GetSignOnlyWithR(BigInteger e, BigInteger message){
        BigInteger d = getD();
        FieldElement h = getH(message);

        Point Q = curve.getInverse(curve.multiply(curve.P,d));
        print("Q",Q);
        Point eP = curve.multiply(curve.P,e);
        print("eP",eP);

        FieldElement F = eP.getX();
        print("F",F);
        FieldElement y = h.multiply(F);
        print(" y ",y);
        return y.getBigInteger();
    }
    public boolean isQuadricResudo(BigInteger b, BigInteger module){//rename
        BigInteger bEulerTest = b.modPow(module.subtract(BigInteger.ONE).divide(TWO),module);
        print("euler test",bEulerTest);
        return bEulerTest.equals(BigInteger.ONE);
    }

    public BigInteger broadbandChannel(BigInteger message, BigInteger sumliminalMessage){
        BigInteger d = getD();
        FieldElement h = getH(message);

        Point Q = curve.getInverse(curve.multiply(curve.P,d));
        print("Q",Q);
        BigInteger e = sumliminalMessage;

        Point eP = curve.multiply(curve.P,e);
        print("eP",eP);

        FieldElement F = eP.getX();
        FieldElement y = h.multiply(F);
        print(" y ",y);

        BigInteger r = y.getBigInteger();
        BigInteger s = e.add(d.multiply(r)).mod(curve.N);

        print("s ",s.toString(16));
        print("r ",r.toString(16));

        Point sP = curve.multiply(curve.P,s);
        print("sP" ,sP);

        Point rQ = curve.multiply(Q,r);
        print("rQ",rQ);

        Point R = curve.add(curve.multiply(curve.P,s),curve.multiply(Q,r));
        print("R",R);

        FieldElement y1 = h.multiply(R.getX());
        print("y_tilda",y1);

        print("check sign:",y1.getBigInteger().equals(r));

        BigInteger e1 = s.subtract(d.multiply(r)).mod(curve.N);
        print("dr ",d.multiply((r)).mod(curve.N).toString(16));
        print("subliminal message",e1.toString(16));

        return e1;
    }

    private BigInteger getD(){
        return new BigInteger("183F60FDF7951FF47D67193F8D073790C1C9B5A3E",16);
    }

    private FieldElement getH(BigInteger message){
        return new FieldElement(new BigInteger("3A2EB95B7180166DDF73532EEB76EDAEF52247FF",16));
    }

    public void print(String message,Object o){
        System.out.println(message+" "+o);
    }

    public void print(String message,Object o1,Object o2){
        System.out.println(message+" "+o1+" "+o2);
    }

    public void print(String message, Point p){
        System.out.println(message+" "+p.getX().getBigInteger().toString(16)+" "+p.getY().getBigInteger().toString(16));
    }

    public void print(String message, FieldElement f){
        System.out.println(message+" "+f.getBigInteger().toString(16));
    }

    public void print(String message, BigInteger b){
        System.out.println(message+" "+b.toString(16));
    }

}
