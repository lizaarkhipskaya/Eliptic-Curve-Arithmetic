import eliptic_curve.ElipticCurve;
import eliptic_curve.Point;
import field.Field;
import field.FieldElement;
import hash.Hash;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Controller {
    public static void main(String[] args) {
        Field field = new Field(173,new int[]{173,10,2,1,0});
        ElipticCurve curve = ElipticCurve.builder()
                .field(field)
                .A(field.getOne())
                .B(new FieldElement(new BigInteger("5FF6108462A2DC8210AB403925E638A19C1455D21", 16),field.m))
                .N(new BigInteger("400000000000000000002BEC12BE2262D39BCF14D",16))
                .P(new Point(new FieldElement(new BigInteger("72D867F93A93AC27DF9FF01AFFE74885C8C540420", 16),field.m),
                        new FieldElement(new BigInteger("0224A9C3947852B97C5599D5F4AB81122ADC3FD9B", 16),field.m)))
                .build();
        SubliminalChannelInDSTU4145 dstu = new SubliminalChannelInDSTU4145(curve);
        //dstu.narrowbandChannel(31);

        /*BigInteger e = BigInteger.probablePrime(160,new Random());
        System.out.println(e.toString(16));

        List<BigInteger> subMessages = new ArrayList<>();

        subMessages.add(new BigInteger("00100",2));
        subMessages.add(new BigInteger("0010001001",2));
        subMessages.add(new BigInteger("001000100110010",2));
        subMessages.add(new BigInteger("00100010011001001110",2));
        subMessages.add(new BigInteger("0010001001100100111010001",2));
        subMessages.add(new BigInteger("001000100110010011101000101111",2));

        subMessages.stream().forEach(mes -> System.out.println(mes.toString(16)));

        dstu.getNumbersForDetermineSubliminalChannel(subMessages.size(),e);
        dstu.determineSubliminalChannel(subMessages.size(),e,null,subMessages);*/
        //dstu.broadbandChannel(new BigInteger("787"),new BigInteger("1025E40BD97DB012B7A1D79DE8E12932D247F61C6",16));
       /*FieldElement f1 = new FieldElement(new BigInteger("EB15382A36B352332EC9D1EB159B47A508A09ABA9BF",16),field.m);
        FieldElement f2 = new FieldElement(new BigInteger("19383D6EF91A8A28A7B5ED1638461F656A5382FDD411",16),field.m);
        FieldElement f3 = new FieldElement(new BigInteger("1FEB670D6FD21A3F04F7A132A4F7843DC79A9C6D06C4",16),field.m);

        System.out.println(field.multiply(f1,f2).getBigInteger(field.m).toString(16));
        System.out.println(field.pow2(f1).getBigInteger(field.m).toString(16));
        System.out.println(field.pow(f1,f3).getBigInteger(field.m).toString(16));
        System.out.println(field.inverse(f1).getBigInteger(field.m).toString(16));*/
       Point p1 = new Point(new BigInteger("EB15382A36B352332EC9D1EB159B47A508A09ABA9BF",16),new BigInteger("19383D6EF91A8A28A7B5ED1638461F656A5382FDD411",16),field);
       Point p2 = curve.doublePoint(curve.doublePoint(p1));
       Point p3 = curve.add(p1,curve.add(p1,curve.doublePoint(p1)));
        System.out.println(p2.getX().getBigInteger(field.m).toString(16));
        System.out.println(p3.getX().getBigInteger(field.m).toString(16));


    }
}
