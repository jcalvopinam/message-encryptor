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

import java.io.Console;
import java.util.Scanner;

import com.jcalvopinam.messageencryptor.core.Crypt;
import com.jcalvopinam.messageencryptor.utils.TextFormatter;

public class MessageEncryptorApplication {

    private static Scanner input = new Scanner(System.in);
    private static char[] password;
    private static String message;

    public static void main(String[] args) {
        showMenu();
        process();
    }

    private static void showMenu() {
        System.out.println("+---------------------------------+");
        System.out.println("| Welcome to MessageEncryptor App |");
        System.out.println("+----------------------------------");
        showOptions();
    }

    private static void process() {
        String option = input.next();

        while (!isValidOption(option)) {
            TextFormatter.println(":: Option not valid!");

            showOptions();
            option = input.next();
        }

        switch (option) {
            case "0":
                System.exit(0);
                break;
            case "1":
                cryptMessage();
                break;
            case "2":
                decryptMessage();
                break;
        }
    }

    private static void cryptMessage() {
        hidePassword();
        String encrypt = Crypt.encrypt(password.toString(), message);
        printResponse(encrypt, ":: Message Encrypted");
    }

    private static void decryptMessage() {
        hidePassword();
        String decrypt = Crypt.decrypt(password.toString(), message);
        printResponse(decrypt, ":: Message Decrypted");
    }

    private static void showOptions() {
        System.out.println("[1] Encrypt");
        System.out.println("[2] Decrypt");
        System.out.println("[0] Exit\n");
        TextFormatter.print(":: Option ");
    }

    private static boolean isValidOption(String value) {
        return value.matches("([0-2])");
    }

    private static void hidePassword() {
        Console console = System.console();
        if (console == null) {
            TextFormatter.print(":: Enter the password");
            password = input.next().toCharArray();

            TextFormatter.print(":: Enter the message");
            message = input.next();
        } else {
            password = System.console().readPassword(TextFormatter.padRight(":: Enter the password"));
            message = System.console().readLine(TextFormatter.padRight(":: Enter the message"));
        }
    }

    private static void printResponse(String crypt, String message) {
        if (crypt != null) {
            TextFormatter.print(message);
            System.out.println(crypt);
        }
    }

}
