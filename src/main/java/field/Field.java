package field;

import java.math.BigInteger;
import java.util.Arrays;

public class Field {
    public  int m = 163;
    public  int[] polynom = {163,7,6,3,0};

    public Field() {
    }

    public Field(int m, int[] polynom) {
        this.m = m;
        this.polynom = polynom;
    }

    public   FieldElement getOne(){
        int[] e3 = new int[m];
        e3[0] = 1;
        return new FieldElement(e3,m);
    }

    public  FieldElement getTwo(){
        int[] e3 = new int[m];
        e3[1] = 1;
        return new FieldElement(e3,m);
    }

    public  FieldElement getZero(){
        return new FieldElement(m);
    }

    public   FieldElement valueOf(BigInteger b1){
        int length = b1.bitLength();

        if(length<=m){
            return new FieldElement(b1,m);
        }

        char[] b1InFieldCharArray = Arrays.copyOfRange(b1.toString(2).toCharArray(),length-m-1,length-1);

        int[] b1BitArray = new  int[m];

        for(int i = 0 ; i < m;i++){
            b1BitArray[i] = Integer.valueOf(b1InFieldCharArray[m-1-i]+"");
        }

        return new FieldElement(b1BitArray,m);
    }

    public FieldElement add(FieldElement e1, FieldElement e2){
        FieldElement e3 = new FieldElement(m);
        for(int i = 0 ; i < m; i++){
            e3.element[i] = e1.element[i]^e2.element[i];
        }
        return e3;
    }

    public FieldElement multiply(FieldElement e1, FieldElement e2){
        int[] e3 = new int[2*m];
        FieldElement e4 = new FieldElement(m);
        for(int i = 0 ; i < m; i++){
            if(e2.element[i] == 1){
                for(int j = 0 ; j<m;j++){
                    e3[i+j] = e3[i+j]^e1.element[j];
                }
            }
        }
        for(int i = 2*m-1; i>m-1; i--){
            if(e3[i] ==1){
                for(int j = 0 ; j < 5 ; j++){
                    e3[i - (polynom[0]-polynom[j])] = e3[i - (polynom[0] - polynom[j])]^0x1;
                }
            }
        }
        for(int i = 0; i < m; i++){
            e4.element[i] = e3[i];
        }
        return e4;
    }

    public FieldElement pow2(FieldElement e1){
        int[] e2 = new int[2*m];
        e2[0] = e1.element[0];
        for(int i = 1 ; i < m;i++){
            e2[i*2] = e1.element[i];
        }
        for(int i = 2*m-1; i>m-1; i--){
            if(e2[i] ==1){
                for(int j = 0 ; j < 5 ; j++){
                    e2[i - (polynom[0]-polynom[j])] = e2[i - (polynom[0] - polynom[j])]^0x1;
                }
            }
        }
        FieldElement e3 = new FieldElement(m);
        for (int i = 0 ; i < m ; i++){
            e3.element[i] = e2[i];
        }
        return e3;
    }

    public FieldElement pow(FieldElement e1,FieldElement e2){
        FieldElement e3 = e1;
        FieldElement result = getOne();
        for(int i = 0 ; i < e2.getLength(m) ; i++){
            if(e2.element[i]==1){
                result = multiply(result,e3);
            }
            e3 = pow2(e3);
        }
        return result;
    }

    public FieldElement inverse(FieldElement e1){
        BigInteger exponent = BigInteger.valueOf(2).pow(m).subtract(BigInteger.valueOf(2));
        FieldElement exp = new FieldElement(exponent,m);
        return pow(e1,exp);
    }

    public FieldElement shiftRight(FieldElement e1,int shift){
        FieldElement e2 = new FieldElement(m);
        for(int i = shift; i < m; i++){
            e2.element[i] = e1.element[i-shift];
        }
        return e2;
    }

    public FieldElement sub(FieldElement e1,FieldElement e2){
        int g = cmp(e1,e2);
        FieldElement e3 = new FieldElement(m);
        if(g!=-1){
            int n = 0 , borrow = 0;
            for(int i =  0; i < m; i++){
                n =e1.element[i] - e2.element[i] - borrow;
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

    public  int cmp(FieldElement e1,FieldElement e2){
        int i = m-1;
        while (e1.element[i] == e2.element[i]) {
            i--;
            if(i == -1)
                return 0;
        }
        if(e1.element[i] > e2.element[i])
            return 1;
        return -1;

    }

}
