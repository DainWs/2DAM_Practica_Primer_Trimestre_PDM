package com.joseduarte.practicafinalprimertrimestres.models;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import java.io.File;
import java.io.Serializable;

public interface Models extends Serializable {

    public String getModelIdentifier();
    public String getModelDescription();
    public Uri getImageUri();
    public String[] getPrimaryKeys();
    public ContentValues getContentValues();

}
