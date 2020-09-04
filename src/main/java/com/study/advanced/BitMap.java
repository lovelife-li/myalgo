package com.study.advanced;

public class BitMap { // Java 中 char 类型占 16bit，也即是 2 个字节
    private char[] bytes;
    private int nbits;

    public BitMap(int nbits) {
        this.nbits = nbits;
        this.bytes = new char[nbits / 16 + 1];
    }

    public void set(int k) {
        if (k > nbits) {
            return;
        }
        int byteIndex = k / 16;
        int bitIndex = k % 16;
        bytes[byteIndex] |= (1 << bitIndex);
    }

    public boolean get(int k) {
        if (k > nbits) {
            return false;
        }
        int byteIndex = k / 16;
        int bitIndex = k % 16;
        return (bytes[byteIndex] & (1 << bitIndex)) != 0;
    }

    public static void main(String[] args) {
      BitMap bitMap = new BitMap(10);
      bitMap.set(9);
      bitMap.set(8);
      boolean b = bitMap.get(9);
      System.out.println(b);
      System.out.println(bitMap.get(6));
      System.out.println(bitMap.get(8));
    }

}