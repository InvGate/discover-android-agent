package com.invgate.discover.androidagent.services;

import android.app.Activity;
import android.net.Uri;

import com.invgate.discover.androidagent.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class LogExport {

    public static String getLogs() {
        try {
            Process process = Runtime.getRuntime().exec("logcat -t 100");
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            StringBuilder log=new StringBuilder();
            String line;
            String separator = System.getProperty("line.separator");
            while ((line = bufferedReader.readLine()) != null) {
                log.append(line);
                log.append(separator);
            }

            return log.toString();
        } catch (IOException e) {
            return "";
        }
    }

    public static File saveLogs(Activity context) {

        try {
            FileWriter writer;
            File outputDir = context.getCacheDir(); // context being the Activity pointer
            File tempFile = File.createTempFile(
                context.getString(R.string.error_log_file_name),
                context.getString(R.string.error_log_file_ext),
                outputDir
            );

            writer = new FileWriter(tempFile);

            /** Saving the contents to the file*/
            writer.write(getLogs());

            /** Closing the writer object */
            writer.close();

            return tempFile;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}
