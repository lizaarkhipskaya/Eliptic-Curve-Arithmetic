package field;

import java.math.BigInteger;
import java.util.Arrays;

public class FieldElement {
    public static final Field field = new Field();
    public int[] element;
    public int length;

    public FieldElement(int[] element) {
        this.element = element;
        this.length = getLength();
    }

    public FieldElement() {
        element = new int[field.m];
        length = 0;
    }

    public FieldElement(int[] element, int length) {
        this.element = element;
        this.length = length;
    }

    public FieldElement(BigInteger b1){//test
        //int length = b1.bitCount();
        int length = b1.bitLength();
        //System.out.println("b1 "+b1.toString(10)+" "+length);
        //System.out.println("b1 "+b1.toString(10)+" "+bitL);
        if(length > field.m){
            throw  new IllegalArgumentException("length > "+field.m);
        }
        //System.out.println("b1 to string "+b1.toString(2));
        char[] b1CharArray = b1.toString(2).toCharArray();
        //System.out.println("b1 "+b1.toString(10)+" "+Arrays.toString(b1CharArray));
        int[] b1BitArray = new int[field.m];

        for(int i = 0 ; i < length;i++){
            b1BitArray[i] = Integer.valueOf(b1CharArray[length-1-i]+"");
        }

        this.length = length;
        this.element = b1BitArray;
    }

    public static FieldElement getFieldElement(BigInteger b1){
        int length = b1.bitLength();

        if(length<=field.m){
            return new FieldElement(b1);
        }

        System.out.println(length);
        System.out.println(length-field.m-1);
        System.out.println(length-1);

        char[] b1InFieldCharArray = Arrays.copyOfRange(b1.toString(2).toCharArray(),length-field.m-1,length-1);

        System.out.println("in char"+b1InFieldCharArray.length);
        int[] b1BitArray = new  int[field.m];

        for(int i = 0 ; i < field.m;i++){
            b1BitArray[i] = Integer.valueOf(b1InFieldCharArray[field.m-1-i]+"");
        }

        return new FieldElement(b1BitArray);
    }

    public BigInteger getBigInteger(){
        char[] b1CharArray = new char[field.m];
        for(int i = 0 ; i < field.m ; i++){
            b1CharArray[i] =  (char)(element[field.m-i-1]+'0');
        }
        //System.out.println("in getBigInteger()"+String.valueOf(b1CharArray));
        return new BigInteger(String.valueOf(b1CharArray),2);
    }
    public int getLength(){
        int length;
        for(length = field.m-1; length>-1; length--){
            if(element[length] == 1)
                break;
        }
        return length+1;
    }

    public int getLastPosition(){
        return getLength()-1;
    }

    public static FieldElement getOne(){
        int[] e3 = new int[field.m];
        e3[0] = 1;
        return new FieldElement(e3);
    }

    public static FieldElement getTwo(){
        int[] e3 = new int[field.m];
        e3[1] = 1;
        return new FieldElement(e3);
    }

    public static FieldElement getZero(){
        return new FieldElement();
    }

    @Override
    public String toString() {
        return "field.FieldElement{" +
                "element=" + Arrays.toString(element) +
                '}';
    }



    public FieldElement add(FieldElement e2){
        FieldElement e3 = new FieldElement();
        for(int i = 0 ; i < field.m; i++){
            e3.element[i] = element[i]^e2.element[i];
        }
        return e3;
    }

    public FieldElement multiply(FieldElement e2){
        int[] e3 = new int[2*field.m];
        FieldElement e4 = new FieldElement();
        for(int i = 0 ; i < field.m; i++){
            if(e2.element[i] == 1){
                for(int j = 0 ; j<field.m;j++){
                    e3[i+j] = e3[i+j]^element[j];
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

    public FieldElement pow2(){
        int[] e2 = new int[2*field.m];
        e2[0] = element[0];
        //System.out.println("do exponent");
        for (int a:element){
          //  System.out.print(a);
        }
        for(int i = 1 ; i < field.m;i++){
            e2[i*2] = element[i];
        }
        //System.out.println("posle");
        for (int a:e2){
          //  System.out.print(a);
        }
        //System.out.println();
        //e2[field.m*2-1] = element[field.m-1];
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

    public FieldElement pow(FieldElement e2){
        FieldElement e3 = this;
        FieldElement result = FieldElement.getOne();
        for(int i = 0 ; i < e2.getLength() ; i++){
            if(e2.element[i]==1){
                // System.out.println("e3 before"+e3.toString());
                result = result.multiply(e3);
                // System.out.println("e3"+e3.toString());
            }
            e3 = e3.pow2();
        }
        return result;
    }

    public FieldElement inverse(){
        //field.FieldElement exponent = field.FieldElement.getOne().shiftRight(field.m).sub(field.FieldElement.getTwo());
        BigInteger exponent = BigInteger.valueOf(2).pow(field.m).subtract(BigInteger.valueOf(2));
        FieldElement exp = new FieldElement(exponent);
        //System.out.println("exponent"+exp.toString());
        return this.pow(exp);
    }

    public FieldElement shiftRight(int shift){
        FieldElement e2 = new FieldElement();
        for(int i = shift; i < field.m; i++){
            e2.element[i] = element[i-shift];
        }
        return e2;
    }

    public FieldElement sub(FieldElement e2){
        int g = this.cmp(e2);
        FieldElement e3 = new FieldElement();
        if(g!=-1){
            int n = 0 , borrow = 0;
            for(int i =  0; i < field.m; i++){
                n =element[i] - e2.element[i] - borrow;
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

    public  int cmp(FieldElement e2){
        int i = field.m-1;
        while (element[i] == e2.element[i]) {
            i--;
            if(i == -1)
                return 0;
        }
        if(element[i] > e2.element[i])
            return 1;
        return -1;

    }



}
