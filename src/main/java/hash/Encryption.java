package hash;

import java.io.BufferedReader;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;

public class Encryption {
    private long M;
    private int L;
    private int R ;
    private long K_Key ;
    private int[] K_KEYS = new int[4];

    private Encryption(long M, long K){
        this.M = M;
        this.K_Key = K;
        initK_KEYS();
        initTABLE_S();
        SplitMessageIntoLR();
    }
    public final static String[] TABLE_S_String = {"99", "7c", "77", "7b","f2", "6b", "6f", "c5", "30", "01", "67", "2b", "fe", "d7", "ab", "76",
            "ca", "82", "c9", "7d", "fa", "59", "47", "f0", "ad", "d4", "a2", "af", "9c", "a4", "72", "c0",
            "b7", "fd", "93", "26", "36", "3f", "f7", "cc", "34", "a5", "e5", "f1", "71", "d8", "31", "15",
            "04", "c7", "23", "c3","18", "96", "05", "9a", "07", "12", "80", "e2", "eb", "27", "b2", "75",
            "09", "83", "2c", "1a", "1b", "6e","5a", "a0", "52", "3b", "d6", "b3", "29", "e3", "2f", "84",
            "53", "d1", "00", "ed", "20", "fc", "b1", "5b", "6a", "cb", "be", "39", "4a", "4c", "58", "cf",
            "d0", "ef", "aa", "fb", "43", "4d", "33", "85", "45", "f9", "02", "7f", "50", "3c", "9f", "a8",
            "51", "a3", "40", "8f", "92", "9d", "38", "f5", "bc", "b6", "da", "21", "10", "ff", "f3", "d2",
            "cd", "0c", "13", "ec", "5f", "97", "44", "17","c4", "a7", "7e", "3d", "64", "5d", "19", "73",
            "60", "81", "4f", "dc","22", "2a", "90", "88", "46", "ee", "b8", "14", "de", "5e", "0b", "db",
            "e0", "32", "3a", "0a", "49", "06", "24", "5c", "c2", "d3", "ac", "62", "91", "95", "e4", "79",
            "e7", "c8", "37", "6d", "8d", "d5", "4e", "a9", "6c", "56", "f4", "ea", "65", "7a", "ae", "08",
            "ba", "78", "25", "2e", "1c", "a6", "b4", "c6", "e8", "dd", "74", "1f", "4b", "bd", "8b", "8a",
            "70", "3e", "b5", "66", "48", "03", "f6", "0e", "61", "35", "57", "b9", "86", "c1", "1d", "9e",
            "e1", "f8", "98", "11", "69", "d9", "8e", "94", "9b", "1e", "87", "e9", "ce", "55", "28", "df",
            "8c", "a1", "89", "0d", "bf", "e6", "42", "68", "41", "99", "2d", "0f", "b0", "54", "bb", "16"};

    public static int[] TABLE_S = new int[256];

    public static void initTABLE_S(){
        for(int i = 0 ; i < 256; i++){
            TABLE_S[i] = Integer.valueOf(TABLE_S_String[i],16);
        }
    }
    public void initK_KEYS() {
        int k = (int)(K_Key>>32);
        int k2 = (int)K_Key;
        K_KEYS[0] = ((k&0xff)<<24)  + ((k&0xff00) << 8) +((k&0xff0000) >> 8) +((k>>24)&0xff);
        K_KEYS[1] = ((k2&0xff)<<24)  + ((k2&0xff00) << 8) +((k2&0xff0000) >> 8) +((k2>>24)&0xff);
        K_KEYS[3] = ~K_KEYS[0];
        K_KEYS[2] = ~K_KEYS[1];

    }


    public void SplitMessageIntoLR()
    {
        int l = (int)(M>>32);
        int r = (int)M;
        L = ((l&0xff)<<24)  + ((l&0xff00) << 8) +((l&0xff0000) >> 8) +(l>>24);
        R = ((r&0xff)<<24)  + ((r&0xff00) << 8) +((r&0xff0000) >> 8) +(r>>24);
    }
    public static long GetCrypto(long M, long K){//Refactor name

        Encryption e = new Encryption(M,K);
        e.Rounder();
        long res = Long.rotateLeft((e.R&0xff),56)  + Long.rotateLeft(e.R&0xff00,40) +Long.rotateLeft(e.R&0xff0000,24) +Long.rotateLeft((e.R>>24)&0xff,32)+ Long.rotateLeft((e.L&0xff),24)  + ((e.L&0xff00) << 8) +((e.L&0xff0000) >> 8) +((e.L>>24)&0xff);
        return   res;
    }
    public void Rounder(){
        for(int i = 1 ; i < 5 ; i++){
            Raund(i);
        }
    }
    public void Raund(int i){
        int R_i = R;
        R = L;
        L = F(K_KEYS[i-1],R_i)^R;
    }

    public int F(int K, int R){
        return Integer.rotateLeft(S(K^R),13);
    }

    public int S(int K){
        byte[] bytes = ByteBuffer.allocate(4).putInt(K).array();
        for(int i = 0 ; i < bytes.length; i++) {
            bytes[i] = (byte) TABLE_S[Byte.toUnsignedInt(bytes[i])];
        }
        return ByteBuffer.wrap(bytes).getInt();
    }
}

