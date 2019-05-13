package com.check.wq.checkapp.Utils;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

/**
 * Created by xiehui on 2017/2/23.
 */
public class MyApplication extends Application {
    private static Context context;
    public static Resources resourcesInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        MyApplication.context=getApplicationContext();
        resourcesInstance=getApplicationContext().getResources();
    }
    public static Resources getAppContext() {
        return resourcesInstance;
    }
}
