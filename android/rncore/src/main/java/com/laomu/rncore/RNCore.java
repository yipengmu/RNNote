package com.laomu.rncore;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.facebook.react.LifecycleState;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactPackage;
import com.facebook.react.shell.MainReactPackage;
import com.laomu.rncore.callback.RenderCallback;
import com.laomu.rncore.task.RenderTask;

/**
 * Created by ${yipengmu} on 15/12/31.
 *
 * 1.cd rn-home
 *
 * 2.react-native start
 *
 * 3.curl http://localhost:8081/index.android.bundle?platform=android -o test.js
 *
 */
public class RNCore {
    private static RNCore ins;
    private ReactInstanceManager.Builder mBuilder;
    private static Application mApplication;

    public RNCore() {
    }

    public static void init(Application app){
        mApplication = app;
    }

    public static RNCore getInstance() {
        if (ins == null) {
            ins = new RNCore();

        }
        return ins;
    }

    public ReactInstanceManager createReactInstanceManager(String bundlePath, String moduleName) {
        return fetchBuilder(bundlePath, moduleName).build();
    }

    public ReactInstanceManager.Builder fetchBuilder(String bundlePath, String moduleName) {
        if(mBuilder == null){
            mBuilder =  ReactInstanceManager.builder()
                    .setApplication(mApplication)
//                    .addPackage(PackageHolder.holdAliRnPackage())
                    .setJSMainModuleName(moduleName)
                    .setJSBundleFile(bundlePath)
                    .addPackage(new MainReactPackage())
                    .setUseDeveloperSupport(false)
                    .setInitialLifecycleState(LifecycleState.RESUMED);
        }
        return mBuilder;
    }

    public ReactInstanceManager.Builder getBuilder() {
        return mBuilder;
    }


    /**
     * 默认下载全量jsbundle ,不执行base.js拼装逻辑
     *
     * @param jsBundleUrl 可以是remoteUrl(http | https |ftp) 也可以是localUrl(/xxx/xx)
     * */
    public void render(String jsBundleUrl, String moduleName, @NonNull RenderCallback callback) {
        new RenderTask(mApplication,jsBundleUrl,moduleName,callback,"").execute();
    }
}
