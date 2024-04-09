package com.caojm.lessons.nio.os;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MacOSCPUSerialNumber {

    public static void main(String[] args) {
        try {
            String[] command = {"/usr/sbin/ioreg", "-l"};
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("IOPlatformSerialNumber")) { // 注意：这并不是CPU序列号
                    System.out.println(line.trim());
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
