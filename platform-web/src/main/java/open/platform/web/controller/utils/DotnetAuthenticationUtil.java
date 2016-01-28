package open.platform.web.controller.utils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.LongBuffer;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DotnetAuthenticationUtil {
	
	private final static Logger logger = LoggerFactory.getLogger(
			DotnetAuthenticationUtil.class.getName());

    private static final String ENCODING = "UTF-16LE";

    public static byte[] hexToByte(String s) throws IOException {
        int i = s.length() / 2;
        byte abyte0[] = new byte[i];
        int j = 0;
        if (s.length() % 2 != 0)
            throw new IOException("hexadecimal string with odd number of characters");
        for (int k = 0; k < i; k++) {
            char c = s.charAt(j++);
            int l = "0123456789abcdef0123456789ABCDEF".indexOf(c);
            if (l == -1)
                throw new IOException("hexadecimal string contains non hex character");
            int i1 = (l & 0xf) << 4;
            c = s.charAt(j++);
            l = "0123456789abcdef0123456789ABCDEF".indexOf(c);
            i1 += l & 0xf;
            abyte0[k] = (byte) i1;
        }

        return abyte0;
    }

    public static byte[] decrypt(String str, String key)
            throws Exception {
        if (null == str || str.trim().length() < 1) {
            return null;
        }

        byte[] keybytes = hexToByte(key);
        byte[] ivbytes = new byte[16];

        SecretKeySpec skeySpec = new SecretKeySpec(keybytes, "AES");


        IvParameterSpec iv = new IvParameterSpec(ivbytes);


        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

        byte[] encrypted1 = hexToByte(str);

        byte[] original = cipher.doFinal(encrypted1);

        return original;
    }
    
    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";

        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) hs = hs + "0" + stmp;
            else hs = hs + stmp;

        }
        return hs.toUpperCase();
    }

    public static String encrypt(String str, String key)
            throws Exception {
        if (null == str || str.trim().length() < 1) {
            return null;
        }

        byte[] keybytes = hexToByte(key);
        byte[] ivbytes = new byte[16];


        SecretKeySpec skeySpec = new SecretKeySpec(keybytes, "AES");


        IvParameterSpec iv = new IvParameterSpec(ivbytes);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

        byte[] encrypted1 = str.getBytes(ENCODING);

        byte[] original = cipher.doFinal(encrypted1);

        String originalString = byte2hex(original);
        return originalString;

    }

    public static byte[] makeTicketBlob(byte[] random, byte version, byte[] username,
                                        byte[] issueDate, byte persist, byte[] expires, byte[] userdata, byte[] appPath) throws Exception {
        if (random.length != 8)
            throw new Exception("random");
        byte[] buffer = null;
        int bufferLength = 7;
        bufferLength += random.length;
        bufferLength += username.length;
        bufferLength += issueDate.length;
        bufferLength += expires.length;
        if (userdata != null)
            bufferLength += userdata.length;
        bufferLength += appPath.length;
        buffer = new byte[bufferLength];
        int pos = 0;
        //int len = 0;

        System.arraycopy(random, 0, buffer, pos, random.length);
        pos += random.length;
        buffer[pos] = version;
        pos++;
        System.arraycopy(username, 0, buffer, pos, username.length);
        pos += username.length;
        buffer[pos] = 0;
        pos++;
        buffer[pos] = 0;
        pos++;
        System.arraycopy(issueDate, 0, buffer, pos, issueDate.length);
        pos += issueDate.length;
        buffer[pos] = persist;
        pos++;
        System.arraycopy(expires, 0, buffer, pos, expires.length);
        pos += expires.length;
        if (userdata != null) {
            System.arraycopy(userdata, 0, buffer, pos, userdata.length);
            pos += userdata.length;
        }
        buffer[pos] = 0;
        pos++;
        buffer[pos] = 0;
        pos++;
        System.arraycopy(appPath, 0, buffer, pos, appPath.length);
        pos += appPath.length;
        buffer[pos] = 0;

        return buffer;
    }

    /**
     * 从解密后字串中得出utf16le编码内容。utf16le双字节存储，以连续0000结尾
     *
     * @param ticketBytes
     * @param start       ticketBytes的起始位置
     * @return 单独内容。长度
     */
    private static byte[] readUtf16le(byte[] ticketBytes, int start) {
        int end = checkUtf16leEnd(ticketBytes, start);
        if (end < 0) {
            return new byte[0];
        } else {
            int len = end - start;
            byte[] desc = new byte[len];
            System.arraycopy(ticketBytes, start, desc, 0, len);
            return desc;
        }
    }

    private static int checkUtf16leEnd(byte[] ticketBytes, int start) {
        int end = ticketBytes.length - start;   //默认到末尾

        for (int i = start; i < ticketBytes.length - 1; i += 2) {
            byte i1 = ticketBytes[i];
            byte i2 = ticketBytes[i + 1];
            if (i1 == 0 && i2 == 0) {
                end = i;
                break;
            }
        }
        return end;
    }

    public static FormsAuthenticationTicket parseTicket(byte[] ticketBytes) throws Exception {

        FormsAuthenticationTicket ticket = null;
        int pos = 8;

        int version = (int) ticketBytes[pos];
        pos++;

        //分析出用户名。utf16-le编码是两字节。所以结束也是0000
        byte[] usernames = readUtf16le(ticketBytes, pos);
        pos += usernames.length + 2;

        //分析创建时间
        byte[] createTimeBytes = new byte[8];
        System.arraycopy(ticketBytes, pos, createTimeBytes, 0, createTimeBytes.length);
        pos += createTimeBytes.length;

        //分析是否持久
        int isPersist = (int) ticketBytes[pos];
        pos++;

        //分析出过期时间
        byte[] expireTimeBytes = new byte[8];
        System.arraycopy(ticketBytes, pos, expireTimeBytes, 0, expireTimeBytes.length);
        pos += expireTimeBytes.length;

        //分析出userdata
        byte[] userdatas = readUtf16le(ticketBytes, pos);
        pos += userdatas.length + 2;

        //分析出路径

        byte[] paths = readUtf16le(ticketBytes, pos);
        pos += paths.length + 2;

        try {

            String username = new String(usernames, ENCODING);

            String userData = new String(userdatas, ENCODING);

            String appPath = new String(paths, ENCODING);

            Date createTime = ByteArrayToDate(createTimeBytes);
            Date expiration = ByteArrayToDate(expireTimeBytes);
            boolean isPersistent = isPersist == 1;
            ticket = new FormsAuthenticationTicket(username, userData, appPath, createTime, expiration, version, isPersistent);

        } catch (Exception e) {
        	logger.error("parseTicket error!", e);
            throw e;
        }
        return ticket;
    }


    public static byte[] md5HashForData(byte[] buf, int start, int length, String validationKeyString) throws NoSuchAlgorithmException, IOException {

        java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
        md.getDigestLength();
        byte[] validationKeyBytes = hexToByte(validationKeyString);
        int num = length + validationKeyBytes.length; //

        byte[] dst = new byte[num];
        System.arraycopy(buf, start, dst, 0, length);

        System.arraycopy(validationKeyBytes, 0, dst, length, validationKeyBytes.length);
        md.update(dst);

        return md.digest();
    }

    @SuppressWarnings("unused")
    private static boolean isSignatureVerified(byte[] decryptedData, String validationKeyString) throws IOException, NoSuchAlgorithmException {
        byte[] signatureeBytes = md5HashForData(decryptedData, 0, decryptedData.length - 20, validationKeyString);
        if (signatureeBytes.length < 20) {
            byte[] dst = new byte[20];
            System.arraycopy(signatureeBytes, 0, dst, 0, signatureeBytes.length);
            signatureeBytes = dst;
        }

        for (int i = 0; i < 20; i++) {
            if (signatureeBytes[i] != decryptedData[(decryptedData.length - 20) + i])
                return false;
        }
        return true;
    }

    public static FormsAuthenticationTicket getFormsAuthenticationTicket(String desc, String key) throws Exception {
        return parseTicket(decrypt(desc, key));
    }


    public static byte[] longToByteArray(long l) {
        byte[] bArray = new byte[8];
        ByteBuffer bBuffer = ByteBuffer.wrap(bArray);
        bBuffer.order(ByteOrder.LITTLE_ENDIAN);
        LongBuffer lBuffer = bBuffer.asLongBuffer();
        lBuffer.put(0, l);
        return bArray;
    }

    public static long byteArrayToLong(byte[] bArray) {
        ByteBuffer bBuffer = ByteBuffer.wrap(bArray);
        bBuffer.order(ByteOrder.LITTLE_ENDIAN);
        LongBuffer lBuffer = bBuffer.asLongBuffer();
        long l = lBuffer.get(0);
        return l;
    }

    public static byte[] DateToByteArray(Date date) {
        long longDate = date.getTime();
        longDate *= 10000;
        longDate += 116444736000000000L;
        return longToByteArray(longDate);
    }

    public static Date ByteArrayToDate(byte[] bytes) throws Exception {
        if (bytes.length != 8)
            throw new Exception("must be 8 bytes");
        long date = byteArrayToLong(bytes);
        return new Date((date - 116444736000000000L) / 10000);
    }

}
