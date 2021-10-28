package com.joseduarte.practicafinalprimertrimestres;

import android.content.Context;
import android.graphics.Bitmap;

import com.joseduarte.practicafinalprimertrimestres.db.DBController;

public class SourcesManager {

    private static DBController dbController;
    private static Bitmap bitmap;
    private static Context context;

    public static DBController getDBController() {
        return dbController;
    }

    public static Bitmap getLastImage() {
        return bitmap;
    }

    public static Context getContext() {
        return context;
    }

    static void setController(DBController controller) {
        dbController = controller;
    }

    static void setContext(Context mContext) {
        context = mContext;
    }

    static void setLastImage(Bitmap image) {
        bitmap = image;
    }


}
