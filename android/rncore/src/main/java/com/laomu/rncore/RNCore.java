package com.laomu.rncore;

import com.facebook.react.LifecycleState;
import com.facebook.react.ReactInstanceManager;

/**
 * Created by ${yipengmu} on 15/12/31.
 */
public class RNCore {
    private static RNCore ins;
    private ReactInstanceManager.Builder mBuilder;

    public RNCore() {
    }

    public static RNCore getInstance() {
        if (ins == null) {
            ins = new RNCore();

        }
        return ins;
    }

    public ReactInstanceManager renderReactInstanceManager(String bundlePath, String moduleName) {
        ReactInstanceManager instanceManager = null;
        instanceManager = fetchBuilder(bundlePath, moduleName).build();
        return instanceManager;
    }

    private ReactInstanceManager.Builder fetchBuilder(String bundlePath, String moduleName) {
        if(mBuilder == null){
            mBuilder =  ReactInstanceManager.builder()
//                    .setApplication(mApplication)
//                    .addPackage(PackageHolder.holdAliRnPackage())
                    .setJSMainModuleName(moduleName)
                    .setJSBundleFile(bundlePath)
                    .setUseDeveloperSupport(false)
                    .setInitialLifecycleState(LifecycleState.RESUMED);
        }
        return mBuilder;
    }
}
