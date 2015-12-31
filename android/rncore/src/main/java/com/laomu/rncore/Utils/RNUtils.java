package com.laomu.rncore.utils;

import java.io.File;

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
}
