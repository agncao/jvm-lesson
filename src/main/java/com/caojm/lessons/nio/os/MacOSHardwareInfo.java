package com.caojm.lessons.nio.os;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class MacOSHardwareInfo {
    public static void main(String[] args) {
        try {
            List<String> dataAdapters=new ArrayList<String>();
            File disFolder = new File("/Users/caojm/projects/gis/Space2024.4.4/dis");
            if(disFolder.exists()) {
                addDISFile(dataAdapters,disFolder);
            }

            String os = System.getProperty("os.name");
            os = os.toUpperCase();
            System.out.println(os);
            System.out.println(os.startsWith("MAC"));
            Process process = Runtime.getRuntime().exec("system_profiler SPHardwareDataType");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().startsWith("Hardware UUID")) {
                    String cpuId = line.split(":")[1].trim();
                    System.out.println("Hardware UUID: " + cpuId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addDISFile(List<String> dataAdapters,File f) {
        if(f.isDirectory()) {
            File[] plist = f.listFiles();
            for(File cf:plist) {
                addDISFile(dataAdapters,cf);
            }
        }else {
            String name = f.getName();
            if(f.isFile()&&name.endsWith(".js")) {
                String path = f.getAbsolutePath();
                path = path.substring(path.indexOf(File.separator+"dis"+File.separator)+5);
                dataAdapters.add(path);
            }
        }
    }

}