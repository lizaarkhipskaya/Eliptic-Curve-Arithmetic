import eliptic_curve.ElipticCurve;
import eliptic_curve.Point;
import field.FieldElement;

import java.math.BigInteger;

public class Controller {
    public static void main(String[] args) {
        ElipticCurve curve = ElipticCurve.builder()
                .A(FieldElement.getOne())
                .B(new FieldElement(new BigInteger("5FF6108462A2DC8210AB403925E638A19C1455D21", 16)))
                .N(new BigInteger("400000000000000000002BEC12BE2262D39BCF14D",16))
                .P(new Point(new FieldElement(new BigInteger("72D867F93A93AC27DF9FF01AFFE74885C8C540420", 16)),
                        new FieldElement(new BigInteger("0224A9C3947852B97C5599D5F4AB81122ADC3FD9B", 16))))
                .build();
        SubliminalChannelInDSTU4145 dstu = new SubliminalChannelInDSTU4145(curve);
        dstu.narrowbandChannel(31);
    }
}
