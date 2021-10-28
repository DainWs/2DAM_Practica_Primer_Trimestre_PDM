package com.joseduarte.practicafinalprimertrimestres.models;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.joseduarte.practicafinalprimertrimestres.R;
import com.joseduarte.practicafinalprimertrimestres.SourcesManager;

import org.jetbrains.annotations.NotNull;

public class Comentarios implements Models {
    private final Exposiciones exposicion;
    private final Trabajos trabajo;
    private String comentario;

    public Comentarios(@NotNull Exposiciones exposicion, @NonNull Trabajos trabajo, @NonNull String comentario) {
        this.exposicion = exposicion;
        this.trabajo = trabajo;
        this.comentario = comentario;
    }

    public Exposiciones getExposicion() {
        return exposicion;
    }

    public Trabajos getTrabajo() {
        return trabajo;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(@NonNull String comentario) {
        this.comentario = comentario;
    }

    @Override
    public String getModelIdentifier() {
        return trabajo.getModelIdentifier() +" "
                + SourcesManager.getContext().getString(R.string.comment_id_text)
                + " " + exposicion.getModelIdentifier();
    }

    @Override
    public String getModelDescription() {
        return comentario;
    }

    @Override
    public Uri getImageUri() {
        return Uri.EMPTY;
    }

    @Override
    public String[] getPrimaryKeys() {
        return new String[] {
                exposicion.getIdExposiciones()+"",
                trabajo.getNombreTrab()
        };
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put("IdExposicion", exposicion.getIdExposiciones());
        values.put("NombreTrab", trabajo.getNombreTrab());
        values.put("Comentario", comentario);
        return values;
    }

    @Override
    public String toString() {
        return getModelIdentifier();
    }
}
