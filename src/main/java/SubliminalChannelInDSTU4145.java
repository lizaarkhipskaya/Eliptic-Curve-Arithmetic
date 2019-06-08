import eliptic_curve.ElipticCurve;
import eliptic_curve.Point;
import field.FieldElement;
import hash.Hash;
import org.apache.commons.lang3.ArrayUtils;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SubliminalChannelInDSTU4145 {

    public static final BigInteger TWO   = BigInteger.valueOf(2);
    public  ElipticCurve curve;

    public SubliminalChannelInDSTU4145(ElipticCurve curve){
        this.curve = curve;
    }

    public void getNumbersForDetermineSubliminalChannel(int n ,BigInteger e){
        long h0 = Hash.getHash(e.toByteArray(),0l);
        byte[]  arrayForHash = null;
        Point eP = null;
        Long h0Long = null;
        while(n>0){
            arrayForHash = ArrayUtils.addAll(ByteBuffer.allocate(8).putLong(h0).array(),e.toByteArray());
            h0 = Hash.getHash(arrayForHash,0l);
            print("h0", Long.toHexString(h0));
            eP = curve.multiply(curve.P,new BigInteger(Long.toHexString(h0),16));
            print("eP",eP);
            n--;
        }
    }

    public void determineSubliminalChannel(int n , BigInteger e, BigInteger message, List<BigInteger> subliminalMessages){

        long h0 = Hash.getHash(e.toByteArray(),0l);
        print("init h0",Long.toHexString(h0));
        byte[]  arrayForHash = null;

        Point Q = null;
        Point ePinit = null;
        Point eP = null;
        Point sP = null;
        Point rQ = null;
        Point R = null;

        BigInteger d = null;
        BigInteger r = null;
        BigInteger s = null;
        BigInteger e1 = null;
        BigInteger subMes = null;

        FieldElement h = null;
        FieldElement F = null;
        FieldElement y = null;
        FieldElement y1 = null;


        for (BigInteger subliminalMessage:
             subliminalMessages) {
            arrayForHash = ArrayUtils.addAll(ByteBuffer.allocate(8).putLong(h0).array(),e.toByteArray());
            h0 = Hash.getHash(arrayForHash,0l);
            print("h0", Long.toHexString(h0));
            ePinit = curve.multiply(curve.P,new BigInteger(Long.toHexString(h0),16));

            d = getD();
            h = getH(message);

            Q = curve.getInverse(curve.multiply(curve.P,d));
            print("Q",Q);

            eP = curve.add(ePinit,curve.multiply(curve.P,subliminalMessage));
            print("eP",eP);

            F = eP.getX();
            print("F",F);
            y = curve.field.multiply(h,F);
            print(" y ",y);

            r = y.getBigInteger(curve.field.m);
            s = (new BigInteger(Long.toHexString(h0),16).add(subliminalMessage)).add(d.multiply(r)).mod(curve.N);

            print("s ",s.toString(16));
            print("r ",r.toString(16));

            sP = curve.multiply(curve.P,s);
            print("sP" ,sP);

            rQ = curve.multiply(Q,r);
            print("rQ",rQ);

            R = curve.add(curve.multiply(curve.P,s),curve.multiply(Q,r));
            print("R",R);

            y1 = curve.field.multiply(h,R.getX());
            print("y_tilda",y1);

            print("check sign:",y1.getBigInteger(curve.field.m).equals(r));

            print("get subliminal message");
            subMes = getSubliminalMessageFrom_eP_inDetermineChannel(ePinit,eP);
            print("subliminal message after algorithm",subMes);

            n--;
        }


    }

    public BigInteger getSubliminalMessageFrom_eP_inDetermineChannel(Point ePinit, Point eP){
        BigInteger message = BigInteger.ZERO;
        print("in get fuction ePinit",ePinit);
        print("in get function eP ",eP);
        print("in get function eP multiple sub message ", curve.add(ePinit,curve.multiply(curve.P,new BigInteger("34"))));

        print("time nano",System.nanoTime());
        print("time",System.currentTimeMillis());
        while (true){
            ePinit = curve.add(curve.P,ePinit);
            message = message.add(BigInteger.ONE);
            if(ePinit.getX().getBigInteger(curve.field.m).equals(eP.getX().getBigInteger(curve.field.m))&&ePinit.getY().getBigInteger(curve.field.m).equals(eP.getY().getBigInteger(curve.field.m)))
                break;
        }
        print("time nano",System.nanoTime());
        print("time",System.currentTimeMillis());
        print("calculated message",message.toString(10));
        return message;
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
            counter++;
            if(counter>3){
                System.out.println("heeelp");
            }
            do{
                e = BigInteger.probablePrime(163, new Random());
            }while (!e.isProbablePrime(50));
            print("e maybe right",e.toString(16));
            r = GetSignOnlyWithR(e,message);

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
        FieldElement y = curve.field.multiply(h,F);
        print(" y ",y);
        return y.getBigInteger(curve.field.m);
    }
    public boolean isQuadricResudo(BigInteger b, BigInteger module){//rename
        BigInteger bEulerTest = b.modPow(module.subtract(BigInteger.ONE).divide(TWO),module);
        print("euler test",bEulerTest);
        return bEulerTest.equals(BigInteger.ONE);
    }

    public BigInteger broadbandChannel(BigInteger message, BigInteger subliminalMessage){
        BigInteger d = getD();
        FieldElement h = getH(message);

        Point Q = curve.getInverse(curve.multiply(curve.P,d));
        print("Q",Q);
        BigInteger e = subliminalMessage;

        Point eP = curve.multiply(curve.P,e);
        print("eP",eP);

        FieldElement F = eP.getX();
        FieldElement y = curve.field.multiply(h,F);
        print(" y ",y);

        BigInteger r = y.getBigInteger(curve.field.m);
        BigInteger s = e.add(d.multiply(r)).mod(curve.N);

        print("s ",s.toString(16));
        print("r ",r.toString(16));

        Point sP = curve.multiply(curve.P,s);
        print("sP" ,sP);

        Point rQ = curve.multiply(Q,r);
        print("rQ",rQ);

        Point R = curve.add(curve.multiply(curve.P,s),curve.multiply(Q,r));
        print("R",R);

        FieldElement y1 = curve.field.multiply(h,R.getX());
        print("y_tilda",y1);

        print("check sign:",y1.getBigInteger(curve.field.m).equals(r));

        BigInteger e1 = s.subtract(d.multiply(r)).mod(curve.N);
        print("dr ",d.multiply((r)).mod(curve.N).toString(16));
        print("subliminal message",e1.toString(16));

        return e1;
    }

    private BigInteger getD(){
        return new BigInteger("183F60FDF7951FF47D67193F8D073790C1C9B5A3E",16);
    }

    private FieldElement getH(BigInteger message){
        return new FieldElement(new BigInteger("3A2EB95B7180166DDF73532EEB76EDAEF52247FF",16),curve.field.m);
    }

    public void print(String message,Object o){
        System.out.println(message+" "+o);
    }

    public void print(String message,Object o1,Object o2){
        System.out.println(message+" "+o1+" "+o2);
    }

    public void print(String message, Point p){
        System.out.println(message+" "+p.getX().getBigInteger(curve.field.m).toString(16)+" "+p.getY().getBigInteger(curve.field.m).toString(16));
    }

    public void print(String message, FieldElement f){
        System.out.println(message+" "+f.getBigInteger(curve.field.m).toString(16));
    }

    public void print(String message){
        System.out.println(message);
    }

    public void print(String message, BigInteger b){
        System.out.println(message+" "+b.toString(16));
    }

}
