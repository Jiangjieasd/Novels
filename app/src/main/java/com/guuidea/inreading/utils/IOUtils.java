package com.guuidea.inreading.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by guuidea on 17-5-11.
 */

public class IOUtils {

    private IOUtils(){}

    public static void close(Closeable closeable){
        if (closeable == null) return;
        try {
            closeable.close();
        } catch (IOException e) {
            e.printStackTrace();
            //close error
        }
    }
}
