/*
 * MIT License
 *
 * Copyright (c) 2018. JUAN CALVOPINA M
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.jcalvopinam.messageencryptor.core;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.jcalvopinam.messageencryptor.utils.TextFormatter;

/**
 * @author Juan Calvopina
 */
public class Crypt {

    private static final String AES_MODE = "AES/CBC/PKCS5Padding";
    private static final String AES = "AES";
    private static final String ENCODING = "UTF-8";

    private static final String HASH_ALGORITHM = "SHA-256";
    private static final String SALT = "d68a1c8a0a8b8710f7c771065165867fc8e73b50ee6809a7e9f53873b38e3e0d";

    private static final byte[] ivBytes = {0x42, 0x59, (byte) 0xAF, 0x51, (byte) 0xFF, (byte) 0xB3,
                                           0x02, 0x68, 0x62, (byte) 0xCE, (byte) 0xDA, 0x11, 0x00, (byte) 0xE9, 0x44,
                                           0x01};

    private static final IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);

    private static final String NOT_BE_NULL = "The password/message must not be null";

    private static SecretKeySpec generateKey(final String password)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        // Create a SHA-256 hash of the password
        final MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);

        // Get the password and get bytes using ENCODING.
        // It should not be necessary to get with AES, but I do it nonetheless
        byte[] bytes = (password + SALT).getBytes(ENCODING);
        digest.update(bytes, 0, bytes.length);

        // Get the HASH
        byte[] key = digest.digest();

        // Using SHA-256, this returns a 256bit key
        return new SecretKeySpec(key, AES);
    }


    public static String encrypt(final String password, String message) {
        if (checkParametersNull(password, message)) {
            return null;
        }

        try {
            final SecretKeySpec key = generateKey(password);

            Cipher cipher = Cipher.getInstance(AES_MODE);
            cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
            byte[] cipherText = cipher.doFinal(message.getBytes(ENCODING));

            return Base64.getEncoder().encodeToString(cipherText);

        } catch (UnsupportedEncodingException | InvalidKeyException | InvalidAlgorithmParameterException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException e) {
            TextFormatter.print(":: An error occurred while encrypting");
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static String decrypt(final String password, String message) {
        if (checkParametersNull(password, message)) {
            return null;
        }

        try {
            final SecretKeySpec key = generateKey(password);

            byte[] decodedCipherText = Base64.getDecoder().decode(message);

            Cipher cipher = Cipher.getInstance(AES_MODE);
            cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);

            byte[] decryptedBytes = cipher.doFinal(decodedCipherText);
            return new String(decryptedBytes, ENCODING);

        } catch (UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeyException | InvalidAlgorithmParameterException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException | IllegalArgumentException e) {
            TextFormatter.print(":: An error occurred while decrypting");
            System.out.println(e.getMessage());
            return null;
        }
    }

    private static boolean checkParametersNull(String password, String message) {
        if (password == null || message == null) {
            TextFormatter.print(":: An error occurred in the parameters");
            System.out.println(NOT_BE_NULL);
            return true;
        }
        return false;
    }

}
