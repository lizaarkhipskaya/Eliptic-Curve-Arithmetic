import java.math.BigInteger;

public class Controller {
    public static void main(String[] args) {
        ElipticCurve curve = ElipticCurve.builder()
                .A(FieldElement.getOne())
                .B(new FieldElement(new BigInteger("5FF6108462A2DC8210AB403925E638A19C1455D21", 16)))
                .N(new BigInteger("400000000000000000002BEC12BE2262D39BCF14D",16))
                .P(new Point(new FieldElement(new BigInteger("72D867F93A93AC27DF9FF01AFFE74885C8C540420", 16)), new FieldElement(new BigInteger("0224A9C3947852B97C5599D5F4AB81122ADC3FD9B", 16))))
                .build();
        BigInteger d = new BigInteger("183F60FDF7951FF47D67193F8D073790C1C9B5A3E",16);
        //System.out.println(curve.doublePoint(curve.P).toString());
        Point G = curve.multiply(curve.P,d);
        //Point G3 = curve.add(curve.doublePoint(G),G);
        //Point Q2 = new Point(new FieldElement(new BigInteger("1100", 2)), new FieldElement(new BigInteger("101", 2)));
        //System.out.println(new FieldElement(new BigInteger("1010", 2)).toString());
        System.out.println(G.getX().getBigInteger().toString(16));
        /*FieldElement alpha6 = new FieldElement(new BigInteger("1100",2));
        FieldElement inv = alpha6.inverse();
        System.out.println(inv.toString());*/
        //FieldElement inv2 = alpha6.inverse();
    }
}
