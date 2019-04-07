package com.cody.http.cat.holder;

import android.content.Context;

/**
 * Created by xu.yi. on 2019/4/5.
 * HttpCore
 */
public class ContextHolder {

    private static Context context;

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        ContextHolder.context = context;
    }

}
