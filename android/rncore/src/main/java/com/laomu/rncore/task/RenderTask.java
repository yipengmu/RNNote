package com.laomu.rncore.task;

import android.app.Application;
import android.os.AsyncTask;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.laomu.rncore.RNCore;
import com.laomu.rncore.Utils.RNLog;
import com.laomu.rncore.Utils.RNUtils;
import com.laomu.rncore.callback.RenderCallback;
import com.laomu.rncore.download.DownloadManager;

/**
 * Created by ${yipengmu} on 15/12/31.
 */
public class RenderTask extends AsyncTask {


    private Application rApplication;
    private String rBundleUrl;
    private String rModuleName;
    private RenderCallback rCallback;

    public RenderTask(Application application, String remoteUrl, String moduleName, RenderCallback callback) {
        rApplication = application;
        rBundleUrl = remoteUrl;
        rModuleName = moduleName;
        rCallback = callback;
    }

    @Override
    protected Object doInBackground(Object[] params) {

        String jsbundleFinalPath = rBundleUrl;
        if(rBundleUrl.startsWith("http://") || rBundleUrl.startsWith("https://") || rBundleUrl.startsWith("ftp://")){
                jsbundleFinalPath = DownloadManager.getInstance(rApplication).downloadFile(rApplication, rBundleUrl);
            //url是remoteUrl
            RNLog.d("RenderReactTask : remote bundle path " + jsbundleFinalPath);
        }else {
            //url是本地 filepatch
            RNLog.d("RenderReactTask : local bundle path " + jsbundleFinalPath);
        }
        return jsbundleFinalPath;
    }

    @Override
    protected void onPostExecute(Object mURL) {
        super.onPostExecute(mURL);

        String mergedFilePath = (String) mURL;
        if(RNUtils.checkFileMergedSucc(mergedFilePath)){
            RNLog.d(" bundle file check succ : " + mergedFilePath);
        }else {
            RNLog.e(" bundle file check failed : " + mergedFilePath);
        }
        ReactInstanceManager instanceManager = RNCore.getInstance().renderReactInstanceManager(mergedFilePath,rModuleName);

        rCallback.onCreatedInstanceManager(instanceManager);

        ReactRootView reactRootView = new ReactRootView(rApplication);
        reactRootView.startReactApplication(instanceManager,rModuleName);


        rCallback.onRenderFinish(reactRootView);
    }
}
