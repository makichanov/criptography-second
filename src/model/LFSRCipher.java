package model;

public class LFSRCipher {

    private final byte[] message;
    private long key;
    private static final long BIT_MASK = 0b100000_00000000_00000000_00000000_00000000L;
    private final StringBuilder originalBits;
    private final StringBuilder encryptedBits;

    public StringBuilder getOriginalBits() {
        return originalBits;
    }

    public StringBuilder getEncryptedBits() {
        return encryptedBits;
    }

    public StringBuilder getKeyBits() {
        return keyBits;
    }

    private final StringBuilder keyBits;

    public LFSRCipher(long key, byte[] message) {
        this.message = message;
        this.key = key;
        this.keyBits = new StringBuilder();
        this.originalBits = new StringBuilder();
        this.encryptedBits = new StringBuilder();
    }

    public byte[] encrypt() {
        byte[] encryptedMessage = new byte[message.length];
        for (int i = 0; i < message.length; i++) {
            byte keyByte = getNextKeyByte();
            encryptedMessage[i] = (byte) (keyByte ^ message[i]);
            if (i < 20) {
                keyBits.append(normalizeToByte(Integer.toBinaryString(keyByte)));
                originalBits.append(normalizeToByte(Integer.toBinaryString(message[i])));
                encryptedBits.append(normalizeToByte(Integer.toBinaryString(encryptedMessage[i])));
            }
        }
        return encryptedMessage;
    }

    public byte[] decrypt() {
        byte[] decryptedMessage = new byte[message.length];
        for (int i = 0; i < message.length; i++) {
            byte keyByte = getNextKeyByte();
            decryptedMessage[i] = (byte) (keyByte ^ message[i]);
            if (i < 20) {
                keyBits.append(normalizeToByte(Integer.toBinaryString(keyByte)));
                originalBits.append(normalizeToByte(Integer.toBinaryString(decryptedMessage[i])));
                encryptedBits.append(normalizeToByte(Integer.toBinaryString(message[i])));
            }
        }
        return decryptedMessage;
    }

    private byte getNextKeyByte() {
        int result = 0;
        for (int i = 0; i < 8; i++) {
            int bit = getLastBit();
            key <<= 1;
            key += bit;
            result += (key & BIT_MASK) >>> 37;
            result <<= 1;
        }
        result >>>= 1;
        return (byte) (255 & result);
    }

    private int getLastBit() {
        String binaryForm = Long.toBinaryString(key);
        if (binaryForm.length() < .2) {
            return 0;
        }
        int secondBit = binaryForm.charAt(binaryForm.length() - 2) - '0';
        if (binaryForm.length() < 10) {
            return secondBit;
        }
        int tenthBit = binaryForm.charAt(binaryForm.length() - 10) - '0';
        if (binaryForm.length() < 12) {
            return secondBit ^ tenthBit;
        }
        int twelveBit = binaryForm.charAt(binaryForm.length() - 12) - '0';
        if (binaryForm.length() < 37) {
            return secondBit ^ tenthBit ^ twelveBit;
        }
        int lastBit = binaryForm.charAt(binaryForm.length() - 37) - '0';
        return secondBit ^ tenthBit ^ twelveBit ^ lastBit;
    }

    private String normalizeToByte(String bate) {
        String longByte = "0000000" + bate;
        return longByte.substring(longByte.length() - 8);
    }
}
