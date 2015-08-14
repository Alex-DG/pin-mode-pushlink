package com.android.sample.pinmode;

import android.app.Application;
import android.provider.Settings;

/**
 * Created by alexandrediguida on 12/08/15.
 */
public class PushlinkSetup extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Auxiliar.API_KEY = "dbcem68v4qjeut5m";
        Auxiliar.DEVICE_ID = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
    }
}
