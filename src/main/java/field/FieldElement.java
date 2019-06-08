package field;

import java.math.BigInteger;
import java.util.Arrays;

public  class FieldElement {
    public int[] element;
    public int length;

    public FieldElement(int[] element, int m) {
        this.element = element;
        this.length = getLength(m);
    }

    public FieldElement(int m) {
        element = new int[m];
        length = 0;
    }

    /**
     * @param m is field extension
     * @param b1 is number to convert to FieldElement
     */
    public FieldElement(BigInteger b1,int m){

        int length = b1.bitLength();

        if(length > m){
            throw  new IllegalArgumentException("length > "+m);
        }
        char[] b1CharArray = b1.toString(2).toCharArray();
        int[] b1BitArray = new int[m];

        for(int i = 0 ; i < length;i++){
            b1BitArray[i] = Integer.valueOf(b1CharArray[length-1-i]+"");
        }

        this.length = length;
        this.element = b1BitArray;
    }

    /**
     * @param m is field extension
     * @return converted FieldElement to BogInteger
     */
    public BigInteger getBigInteger(int m){
        char[] b1CharArray = new char[m];
        for(int i = 0 ; i < m ; i++){
            b1CharArray[i] =  (char)(element[m-i-1]+'0');
        }
        return new BigInteger(String.valueOf(b1CharArray),2);
    }
    public int getLength(int m){
        int length;
        for(length = m-1; length>-1; length--){
            if(element[length] == 1)
                break;
        }
        return length+1;
    }

    public int getLastPosition(int m){
        return getLength(m)-1;
    }
    @Override
    public String toString() {
        return "field.FieldElement{" +
                "element=" + Arrays.toString(element) +
                '}';
    }
}
