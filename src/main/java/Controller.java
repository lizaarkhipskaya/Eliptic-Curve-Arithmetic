import java.math.BigInteger;

public class Controller {
    public static void main(String[] args) {
        ElipticCurve curve = ElipticCurve.builder()
                .A(FieldElement.getOne())
                .B(new FieldElement(new BigInteger("5FF6108462A2DC8210AB403925E638A19C1455D21", 16)))
                .N(new BigInteger("400000000000000000002BEC12BE2262D39BCF14D", 16))
                .P(new Point(new FieldElement(new BigInteger("72D867F93A93AC27DF9FF01AFFE74885C8C540420", 16)), new FieldElement(new BigInteger("224A9C3947852B97C5599D5F4AB81122ADC3FD9B", 16))))
                .build();
        BigInteger d = new BigInteger("1025E40BD97DB012B7A1D79DE8E12932D247F61C6", 16);

        //Point doubl = curve.doublePoint(curve.P);
        //Point mulpl = curve.multiply(curve.P,BigInteger.valueOf(9));
        //System.out.println(doubl.toString());
        //System.out.println(mulpl.toString());

       // Point doubl = curve.doublePoint(curve.P);
        //Point p4FromDouble = curve.doublePoint(doubl);
        //Point p8 = curve.doublePoint(p4FromDouble);
        //Point p9 = curve.add(p8,curve.P);

        //BigInteger pX = curve.P.getX().getBigInteger();
        //System.out.println(pX.toString(16));
         Point Q = curve.multiply(curve.P,d);
         System.out.println(Q.getX().getBigInteger().toString(2));

         Point Q_must_hsve = new Point(new FieldElement(new BigInteger("42A7D756D70E1C9BA62D2CB43707C35204EF3C67C",16)),new FieldElement(new BigInteger("5310AE5E560464A95DC80286F17EB762EC544B15B",16)));
         System.out.println(Q_must_hsve.getX().getBigInteger().toString(2));
     /*  FieldElement  exponent = new FieldElement(new BigInteger("11692013098647223345629478661730264157247460343806"));
        System.out.println("exponent from calculation "+exponent.toString());
        FieldElement e1 = new FieldElement(BigInteger.valueOf(11));
        FieldElement e2 = e1.inverse();
        System.out.println(e1.multiply(e2).toString());
        */
    //    System.out.println(b1.multiply(b3).add(b2.multiply(b3)).toString());
     //FieldElement p2 = p1pow4.inverse();
     //FieldElement probablyOne = p2.multiply(p1pow4);



    }
}
