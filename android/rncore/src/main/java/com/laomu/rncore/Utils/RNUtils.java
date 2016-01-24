package com.laomu.rncore.utils;

import android.net.Uri;
import android.text.TextUtils;

import java.io.File;
import java.net.URI;

/**
 * Created by ${yipengmu} on 15/12/31.
 */
public class RNUtils {

    public static boolean checkFileMergedSucc(String mFile) {
        try {
            File mergedFile = new File(mFile);
            if(mergedFile !=null && mergedFile.isFile()&& mergedFile.exists()){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }


    public static String getModuleNameFromUrl(String remoteUrl, String verifiedModuleName) {
        String moduleName = "";
        if (!TextUtils.isEmpty(verifiedModuleName)) {
            return verifiedModuleName;
        }
        try {
            String decodedUrl = Uri.decode(remoteUrl);
            URI uri = URI.create(decodedUrl);
            if(uri.getPort() == 80 || uri.getPort() == -1){
                moduleName = uri.getHost()  + uri.getPath();
            }else {
                moduleName = uri.getHost()  + ":" +  uri.getPort() + uri.getPath();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return moduleName;
    }
}
