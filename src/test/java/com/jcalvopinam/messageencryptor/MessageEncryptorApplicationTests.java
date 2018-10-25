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

package com.jcalvopinam.messageencryptor;

import com.jcalvopinam.messageencryptor.core.Crypt;
import org.junit.Assert;
import org.junit.Test;

public class MessageEncryptorApplicationTests {

    private static final String PASSWORD = "123";
    private static final String MESSAGE = "Hello, World!";
    private static final String MESSAGE_ENCRYPTED = "3Gk0aUeeBdox2debiyIE1w==";

    @Test
    public void testEncryptWhenMessageIsNotNull() {
        String encrypt = Crypt.encrypt(PASSWORD, MESSAGE);
        Assert.assertNotNull(encrypt);
    }

    @Test
    public void testEncryptWhenPasswordIsNull() {
        String encrypt = Crypt.encrypt(null, MESSAGE);
        Assert.assertNull(encrypt);
    }

    @Test
    public void testEncryptWhenMessageIsNull() {
        String encrypt = Crypt.encrypt(PASSWORD, null);
        Assert.assertNull(encrypt);
    }

    @Test
    public void testDecryptWhenMessageIsNotNull() {
        String decrypt = Crypt.decrypt(PASSWORD, MESSAGE_ENCRYPTED);
        Assert.assertNotNull(decrypt);
    }

    @Test
    public void testDecryptWhenPasswordIsNull() {
        String encrypt = Crypt.decrypt(null, MESSAGE);
        Assert.assertNull(encrypt);
    }

    @Test
    public void testDecryptWhenMessageIsNull() {
        String encrypt = Crypt.decrypt(PASSWORD, null);
        Assert.assertNull(encrypt);
    }

}
