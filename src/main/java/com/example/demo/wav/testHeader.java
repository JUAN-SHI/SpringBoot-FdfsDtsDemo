package com.example.demo.wav;



import java.io.File;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by shijuan on 2019/7/4.
 */
public class testHeader {

    /**
     * 获取WAV音频采样率
     * @param args
     */

    public static void main(String[] args) {
        File f = new File("/Users/shijuan/Downloads/test.wav");
        RandomAccessFile rdf = null;
        try {
            rdf = new RandomAccessFile(f,"r");
            System.out.println("sample rate: " + toInt(read(rdf, 24, 4))); // 采样率、音频采样级别 8000 = 8KHz

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int toInt(byte[] b) {
        return ((unsignedByteToInt(b[3])<<24) + ((unsignedByteToInt(b[2]) << 16)) + (unsignedByteToInt(b[1]) << 8)) + ((unsignedByteToInt(b[0]) << 0));
    }




    public static byte[] read(RandomAccessFile rdf, int pos, int length) throws IOException {
        rdf.seek(pos);
        byte result[] = new byte[length];
        for (int i = 0; i < length; i++) {
            result[i] = rdf.readByte();
        }
        return result;
    }

    public static int unsignedByteToInt(byte b) {
        return (int) b & 0xFF;
    }
}
