package app.component.base.app;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import app.component.base.ManifestParser;
import app.component.base.delegate.AppLifeCycleDelegateManager;
import app.component.base.delegate.IAppLifeCycle;


public class MApplication extends Application {
    IAppLifeCycle cycleManager;
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        cycleManager=onCreateAppLifeCycleDelegate(base).build();
        cycleManager.attachBaseContext(base);
    }

    /**创建LifeCycler管理器，可自定添加LifeCycle实现
     * @return
     */
    protected AppLifeCycleDelegateManager.Builder onCreateAppLifeCycleDelegate(Context base)
    {
        return new AppLifeCycleDelegateManager.Builder()
                .addDelegates(new ManifestParser(base).parseAppLifeCycles());
    }


    @Override
    public void onCreate() {
        super.onCreate();
        cycleManager.onCreate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        cycleManager.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        cycleManager.onTerminate();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        cycleManager.onTrimMemory(level);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        cycleManager.onConfigurationChanged(newConfig);
    }
}
