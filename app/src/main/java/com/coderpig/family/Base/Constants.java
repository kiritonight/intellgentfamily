package com.coderpig.family.Base;

public class Constants {
    public static final int RQUEST_PORT = 8997;
    public static final int SERVER_PORT = 8998;
    public static final String PATH = "G:/tmp/temp/";
    public static final byte[] PICTURE_PACKAGE_HEAD = { (byte) 0xFF, (byte) 0xCF, (byte) 0xFA, (byte) 0xBF, (byte) 0xF6,
            (byte) 0xAF, (byte) 0xFE, (byte) 0xFF };
    public static final int CACHE_SIZE = 1024 * 2;


    public static void main(String[] args) {
        for(int i=0;i<PICTURE_PACKAGE_HEAD.length;i++){
//			System.out.println(PICTURE_PACKAGE_HEAD[i]);
            System.out.println(Byte.toString(PICTURE_PACKAGE_HEAD[i]));
        }
    }
}