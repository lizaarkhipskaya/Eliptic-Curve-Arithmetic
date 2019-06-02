import java.math.BigInteger;
import java.util.Arrays;

public class FieldMath {
    public static Field field = new Field();

    public FieldElement add(FieldElement e1, FieldElement e2){
        FieldElement e3 = new FieldElement();
        for(int i = 0 ; i < field.m; i++){
            e3.element[i] = e1.element[i]^e2.element[i];
        }
        return e3;
    }

    public FieldElement multiply(FieldElement e1, FieldElement e2){
        int[] e3 = new int[2*field.m];
        FieldElement e4 = new FieldElement();
        for(int i = 0 ; i < field.m; i++){
            if(e2.element[i] == 1){
                for(int j = 0 ; j<field.m;j++){
                    e3[i+j] = e3[i+j]^e1.element[j];
                }
            }
        }
        for(int i = 2*field.m-1; i>field.m-1; i--){
            if(e3[i] ==1){
                for(int j = 0 ; j < 5 ; j++){
                    e3[i - (field.polynom[0]-field.polynom[j])] = e3[i - (field.polynom[0] - field.polynom[j])]^0x1;
                }
            }
        }
        for(int i = 0; i < field.m; i++){
            e4.element[i] = e3[i];
        }
        return e4;
    }

    public FieldElement pow2(FieldElement e1){
        int[] e2 = new int[2*field.m];
        for(int i = 0 ; i < field.m*2-1;i++){
            e2[i] = e1.element[i/2];
            e2[i+1] = 0;
            i++;
        }
        e2[field.m*2-1] = e1.element[field.m-1];
        for(int i = 2*field.m-1; i>field.m-1; i--){
            if(e2[i] ==1){
                for(int j = 0 ; j < 5 ; j++){
                    e2[i - (field.polynom[0]-field.polynom[j])] = e2[i - (field.polynom[0] - field.polynom[j])]^0x1;
                }
            }
        }
        FieldElement e3 = new FieldElement();
        for (int i = 0 ; i < field.m ; i++){
            e3.element[i] = e2[i];
        }
        return e3;
    }

    public FieldElement pow(FieldElement e1, FieldElement e2){
        FieldElement e3 = e1;
        FieldElement result = FieldElement.getOne();
        for(int i = 0 ; i < e2.getLength() ; i++){
            if(e2.element[i]==1){
               // System.out.println("e3 before"+e3.toString());
                result = multiply(result,e3);
               // System.out.println("e3"+e3.toString());
            }
            e3 = pow2(e3);
        }
        return result;
    }

    public FieldElement inverse(FieldElement e1){
        FieldElement exponent = sub(shiftRight(FieldElement.getOne(),field.m),FieldElement.getTwo());
        return pow(e1,exponent);
    }

    public FieldElement shiftRight(FieldElement e1, int shift){
        FieldElement e2 = new FieldElement();
        for(int i = shift; i < field.m; i++){
            e2.element[i] = e1.element[i-shift];
        }
        return e2;
    }

    public FieldElement sub(FieldElement e1, FieldElement e2){
        int g = cmp(e1,e2);
        FieldElement e3 = new FieldElement();
        if(g!=-1){
            int n = 0 , borrow = 0;
            for(int i =  0; i < field.m; i++){
                n = e1.element[i] - e2.element[i] - borrow;
                if(n >=0){
                    e3.element[i] = n;
                    borrow = 0;
                }else {
                    e3.element[i] = n&0x1;
                    borrow = 1;
                }
            }
        }
        return e3;
    }

    int cmp(FieldElement e1, FieldElement e2){
        int i = field.m;
        while (e1.element[i] == e2.element[i])
            i--;
        if(i == -1)
            return 0;
        if(e1.element[i] > e2.element[i])
            return 1;
        return -1;

    }

}
