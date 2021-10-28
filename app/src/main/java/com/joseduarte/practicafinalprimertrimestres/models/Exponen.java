package com.joseduarte.practicafinalprimertrimestres.models;

import android.content.ContentValues;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.joseduarte.practicafinalprimertrimestres.R;

public class Exponen implements Models {

    private final Exposiciones exposicion;
    private final Artistas artista;

    public Exponen(@NonNull Exposiciones exposicion, @NonNull Artistas artista) {
        this.exposicion = exposicion;
        this.artista = artista;
    }

    public Exposiciones getExposicion() {
        return exposicion;
    }

    public Artistas getArtista() {
        return artista;
    }

    @Override
    public String getModelIdentifier() {
        return artista.getModelIdentifier()+ R.string.expose_id_text +exposicion.getModelIdentifier();
    }

    @Override
    public String getModelDescription() {
        return "";
    }

    @Override
    public Uri getImageUri() {
        return Uri.EMPTY;
    }

    @Override
    public String[] getPrimaryKeys() {
        return new String[] {
                exposicion.getIdExposiciones()+"",
                artista.getDniPasaporte()
        };
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put("IdExposicion", exposicion.getIdExposiciones());
        values.put("DniPasaporte", artista.getDniPasaporte());
        return values;
    }

    @Override
    public String toString() {
        return getModelIdentifier();
    }
}
