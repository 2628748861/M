package app.component.base;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import java.util.ArrayList;
import java.util.List;

import app.component.base.delegate.IAppLifeCycle;


public final class ManifestParser {
    public static final String DEFAULT_APP_VALUE = "com.config.app";
    private  Context context;
    public ManifestParser(Context context) {
        this.context = context;
    }

    public List<IAppLifeCycle> parseAppLifeCycles() {
        List<IAppLifeCycle> modules = new ArrayList();
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            if (appInfo.metaData != null) {
                for (String key : appInfo.metaData.keySet()) {
                    if (DEFAULT_APP_VALUE.equals(appInfo.metaData.get(key))) {
                        modules.add((IAppLifeCycle)parseModule(key));
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("Unable to find metadata to parse ConfigModule", e);
        }

        return modules;
    }

    private static Object parseModule(String className) {
        Class<?> clazz;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Unable to find ConfigModule implementation", e);
        }

        Object module;
        try {
            module = clazz.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException("Unable to instantiate ConfigModule implementation for " + clazz, e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unable to instantiate ConfigModule implementation for " + clazz, e);
        }

        if (!(module instanceof IAppLifeCycle)) {
            throw new RuntimeException("Expected instanceof ConfigModule, but found: " + module);
        }
        return module;
    }
}
