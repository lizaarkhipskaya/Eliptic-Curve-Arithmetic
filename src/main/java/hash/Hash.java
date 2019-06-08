package hash;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class Hash {

    public final static int size = 214748363;
    public  final  static BigInteger FF = new BigInteger("ffffffffffffffff",16);

    public static byte[] padding(byte[]  b){
        int padding = 8-b.length-1;
        byte[] res = new byte[8];
        for( int i = 0 ; i< b.length;i++){
            res[i] = b[i];
        }
        if(b.length<8) {
            res[b.length] = (byte) 128;
            while (padding > 0) {
                res[8 - padding] = 0;
                padding--;
            }

        }
        return res;
    }

    public static long getHash(byte[] M, long H0){
        long H = H0;
        byte[] m ;
        long h ;

            for (int i = 0; i < M.length; i += 8) {//Check right order in m

                if (i + 8 < M.length) {
                    m = Arrays.copyOfRange(M, i, i + 8);
                } else {
                    m = padding(Arrays.copyOfRange(M, i,
                            M.length));
                }
                h = H;
                H = iterationFunctionG(ByteBuffer.wrap(m).getLong(), h);
            }
        return  H;
    }

    private static long iterationFunctionG(long m, long h) {
        return Encryption.GetCrypto(m,h)^m;
    }
}
