package com.telehuz.doittest.util;

//

import android.content.Context;
import android.net.Uri;

import java.io.File;

public class FileUtils {
    private FileUtils() {} //private constructor to enforce Singleton pattern

    public static File getFile(Context context, Uri uri) {
        return new File("");
    }
}