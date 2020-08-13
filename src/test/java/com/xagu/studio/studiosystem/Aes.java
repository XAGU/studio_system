package com.xagu.studio.studiosystem;

import kotlin.UByte;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * @author xagu
 * Created on 2020/8/7
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */

public class Aes {

    public static void main(String[] args) throws UnsupportedEncodingException {
        String username = "haozhi";
        String password = "a123456";
        String str3 = "usr=" + username + "&pwd=" + password + "&plf=1&tye=1&v=3.4.0";
        StringBuilder sb = new StringBuilder();
        sb.append("p=");
        String string = Aes.getString(str3);
        Intrinsics.checkExpressionValueIsNotNull(string, "Aes.getString(param)");
        Charset charset = Charsets.UTF_8;
        byte[] bytes = string.getBytes(charset);
        Intrinsics.checkExpressionValueIsNotNull(bytes, "(this as java.lang.String).getBytes(charset)");
        sb.append(Aes.bytes2HexStr(bytes));
        System.out.println("http://android.556a.com/api/v1/usr?" + sb.toString());
        String ll = "31611724103e133c10424d5d432d4f0d4c4411106a5e70767d63455d1d151576482418517a665d1120564d073161052018151220591d570d0c6a511e1613404165077b732e2c52015e11012f5b7e17426f6615523d47305832333b34103e036d085a0b1f04784e1f1706444d68557a7c797e5d0457501b6e033b2c073930581c760d5c0d66610d202e3f152a565a031a1a6a165c4a592a1c2c4678747c765f055a5e1539192e012c38255415760d4d552b2c1e3818681b32";
        String decrypt = Aes.decrypt(ll);
        System.out.println(decrypt);
    }

    public static byte[] hexStr2Bytes(String str) {
        if (str.length() < 1) {
            return null;
        }
        byte[] bArr = new byte[(str.length() / 2)];
        for (int i = 0; i < str.length() / 2; i++) {
            int i2 = i * 2;
            int i3 = i2 + 1;
            bArr[i] = (byte) ((Integer.parseInt(str.substring(i2, i3), 16) * 16) + Integer.parseInt(str.substring(i3, i2 + 2), 16));
        }
        return bArr;
    }

    public static String bytes2HexStr(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < bArr.length; i++) {
            if ((bArr[i] & UByte.MAX_VALUE) < 16) {
                stringBuffer.append("0");
            }
            stringBuffer.append(Long.toHexString((long) (bArr[i] & UByte.MAX_VALUE)));
        }
        return stringBuffer.toString();
    }

    public static String getString(String str) {
        char[] charArray = str.toCharArray();
        char[] charArray2 = "JCdPqJfO2x9/6Hc//+uuHdBFMOg0nr7LjKssVD9pT7o=".toCharArray();
        String str2 = "";
        for (int i = 0; i < charArray.length; i++) {
            str2 = str2 + ((char) (charArray[i] ^ charArray2[i % 44]));
        }
        return str2;
    }

    public static String decrypt(String str) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        try {
            byte[] hexStr2Bytes = hexStr2Bytes(str);
            if (hexStr2Bytes == null) {
                return null;
            }
            return getString(new String(hexStr2Bytes));
        } catch (Exception unused) {
            return null;
        }
    }
}
