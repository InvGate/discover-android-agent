package com.invgate.discover.androidagent.services;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;

public class Storage {

    public static boolean externalMemoryAvailable() {
        return android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
    }

    public static long getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long availableBlocks = stat.getAvailableBlocksLong();
        return formatSize(availableBlocks * blockSize);
    }

    public static long getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long totalBlocks = stat.getBlockCountLong();
        return formatSize(totalBlocks * blockSize);
    }

    public static long getAvailableExternalMemorySize() {
        String secStore = System.getenv("SECONDARY_STORAGE");
        if (secStore.contains(":")) {
            secStore = "/storage";
        }
        if (externalMemoryAvailable() && secStore != null) {
            File path = new File(secStore);
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSizeLong();
            long availableBlocks = stat.getAvailableBlocksLong();
            return formatSize(availableBlocks * blockSize);
        } else {
            return 0;
        }
    }

    public static long getTotalExternalMemorySize() {
        String secStore = System.getenv("SECONDARY_STORAGE");
        if (secStore.contains(":")) {
            secStore = "/storage";
        }
        if (externalMemoryAvailable() && secStore != null) {
            File path = new File(secStore);
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSizeLong();
            long totalBlocks = stat.getBlockCountLong();
            return formatSize(totalBlocks * blockSize);
        } else {
            return 0;
        }
    }

    public static long formatSize(long size) {
        String suffix = null;

        if (size >= 1024) {
            size /= 1024;
            if (size >= 1024) {
                size /= 1024;
            }
        }

        return size;
    }
}
