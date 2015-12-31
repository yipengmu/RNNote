package com.laomu.rncore.callback;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;

/**
 * Created by ${yipengmu} on 15/12/31.
 */
public interface RenderCallback {

    public void onCreatedInstanceManager(ReactInstanceManager reactInstanceManager);

    public void onRenderFinish(ReactRootView reactRootView);
}
