package com.rnnote;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;

import com.facebook.react.LifecycleState;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.shell.MainReactPackage;
import com.facebook.soloader.SoLoader;
import com.laomu.rncore.RNCore;
import com.laomu.rncore.callback.RenderCallback;

public class MainActivity extends Activity implements DefaultHardwareBackBtnHandler {

    private ReactInstanceManager mReactInstanceManager;
    private ReactRootView mReactRootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RNCore.init(getApplication());
        mReactRootView = new ReactRootView(this);

        mReactInstanceManager = ReactInstanceManager.builder()
                .setApplication(getApplication())
                .setBundleAssetName("index.android.bundle")
                .setJSMainModuleName("index.android")
                .addPackage(new MainReactPackage())
                .setUseDeveloperSupport(BuildConfig.DEBUG)
                .setInitialLifecycleState(LifecycleState.RESUMED)
                .build();

        mReactRootView.startReactApplication(mReactInstanceManager, "RNNote", null);

        setContentView(mReactRootView);

//        String mouduleName = "RNNote";
//        String moudleUrl = "http://192.168.31.238:8081/index.android.bundle?platform=android";
//
//        RNCore.getInstance().render(moudleUrl,mouduleName,new RenderCallback(){
//
//            @Override
//            public void onCreatedInstanceManager(ReactInstanceManager reactInstanceManager) {
//                mReactInstanceManager = reactInstanceManager;
//            }
//
//            @Override
//            public void onRenderFinish(ReactRootView reactRootView) {
//                setContentView(reactRootView);
//            }
//        });
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU && mReactInstanceManager != null) {
            mReactInstanceManager.showDevOptionsDialog();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void onBackPressed() {
      if (mReactInstanceManager != null) {
        mReactInstanceManager.onBackPressed();
      } else {
        super.onBackPressed();
      }
    }

    @Override
    public void invokeDefaultOnBackPressed() {
      super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mReactInstanceManager != null) {
            mReactInstanceManager.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

//        if (mReactInstanceManager != null) {
//            mReactInstanceManager.onResume(this, this);
//        }
    }
}
