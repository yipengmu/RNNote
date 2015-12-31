package com.laomu.rncore.download;

import android.content.Context;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * Created by ${yipengmu} on 15/12/31.
 */
public class DownloadManager {

    private static DownloadManager ins ;
    private OkHttpClient mOkHttpClient;

    private DownloadManager(Context ctx) {

        mOkHttpClient = new OkHttpClient();

        int cacheSize = 10 * 1024 * 1024; //  10 MiB
        Cache cache = new Cache(ctx.getExternalCacheDir(), cacheSize);
        mOkHttpClient.setCache(cache);
    }
    public static DownloadManager getInstance(Context ctx){
        if(ins == null) {
            ins = new DownloadManager(ctx);
        }

        return ins;
    }

    /**
     * 下载文件
     *
     * @param ctx     application context
     * @param fileUrl 待下载的文件URL
     * @return 文件下载后的保存路径
     */
    public String downloadFile(Context ctx, String fileUrl) {
        if (ctx == null || fileUrl == null) {
            throw new IllegalArgumentException("context or fileUrl must not be null.");
        }

        try {
            URL url = new URL(fileUrl);

            File fileDir = ctx.getFilesDir();
            if (fileDir != null && fileDir.exists()) {
                String saveFilePath = fileDir.getPath() + url.getPath().substring(url.getPath().lastIndexOf("/"));
                return downloadFile(fileUrl, saveFilePath);
            }
        } catch (MalformedURLException e) {
        }

        return null;
    }

    /**
     * 下载文件
     *
     * @param fileUrl      待下载的文件URL
     * @param saveFilePath 文件下载后的保存路径
     * @return 文件下载后的保存路径
     */
    public String downloadFile(String fileUrl, String saveFilePath) {
        Request.Builder builder = new Request.Builder();
        builder.url(fileUrl);
        Request request = builder.
                addHeader("User-Agent", "custorm ua").build();

        OutputStream output = null;
        BufferedInputStream input = null;
        try {
            mOkHttpClient.setReadTimeout(30, TimeUnit.SECONDS);
            mOkHttpClient.setConnectTimeout(30, TimeUnit.SECONDS);
            Response response = mOkHttpClient.newCall(request).execute();
            InputStream in = response.body().byteStream();

            input = new BufferedInputStream(in);
            output = new FileOutputStream(saveFilePath);

            byte[] data = new byte[1024];
            int count = 0;
            long total = 0;
            while ((count = input.read(data)) != -1) {
                total += count;
                output.write(data, 0, count);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (input != null) {
                    input.close();
                }

                if (output != null) {
                    output.flush();
                    output.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return saveFilePath;
    }
}
