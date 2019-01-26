package app.component.base.delegate;

import android.content.Context;
import android.content.res.Configuration;

import java.util.ArrayList;
import java.util.List;

public class AppLifeCycleDelegateManager implements IAppLifeCycle {
    private List<IAppLifeCycle> lifeCycles=new ArrayList<>();
    public AppLifeCycleDelegateManager(Builder builder)
    {
        this.lifeCycles=builder.lifeCycles;
    }

    @Override
    public void attachBaseContext(Context base) {
        for (IAppLifeCycle lifeCycle:lifeCycles)
        {
            lifeCycle.attachBaseContext(base);
        }
    }

    @Override
    public void onCreate() {
        for (IAppLifeCycle lifeCycle:lifeCycles)
        {
            lifeCycle.onCreate();
        }
    }

    @Override
    public void onLowMemory() {
        for (IAppLifeCycle lifeCycle:lifeCycles)
        {
            lifeCycle.onLowMemory();
        }
    }

    @Override
    public void onTrimMemory(int level) {
        for (IAppLifeCycle lifeCycle:lifeCycles)
        {
            lifeCycle.onTrimMemory(level);
        }
    }

    @Override
    public void onTerminate() {
        for (IAppLifeCycle lifeCycle:lifeCycles)
        {
            lifeCycle.onTerminate();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        for (IAppLifeCycle lifeCycle:lifeCycles)
        {
            lifeCycle.onConfigurationChanged(newConfig);
        }
    }


    public static class Builder
    {
        private List<IAppLifeCycle> lifeCycles=new ArrayList<>();

        public Builder addDelegate(IAppLifeCycle lifeCycle) {
            this.lifeCycles.add(lifeCycle);
            return this;
        }

        public Builder addDelegates(List<IAppLifeCycle> lifeCycles) {
            this.lifeCycles.addAll(lifeCycles);
            return this;
        }

        public AppLifeCycleDelegateManager build()
        {
            return new AppLifeCycleDelegateManager(this);
        }
    }
}
