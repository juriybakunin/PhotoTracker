package test.example.phototracker;

import tenet.lib.base.TenetApp;

public class App extends TenetApp {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static App get() {
        return (App)getApp();
    }
}
