package com.joseduarte.practicafinalprimertrimestres.models;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import java.util.Date;

public class Exposiciones implements Models {

    private final int idExposiciones;
    private String nombreExp;
    private String descripcion;
    private Date fechaInicio;
    private Date fechaFin;

    public Exposiciones(@NonNull int idExposiciones, @NonNull String nombreExp, @NonNull String descripcion,
                         @NonNull Date fechaInicio, @NonNull Date fechaFin) {
        this.idExposiciones = idExposiciones;
        this.nombreExp = nombreExp;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public int getIdExposiciones() {
        return idExposiciones;
    }

    public String getNombreExp() {
        return nombreExp;
    }

    public void setNombreExp(@NonNull String nombreExp) {
        this.nombreExp = nombreExp;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(@NonNull String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(@NonNull Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(@NonNull Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    @Override
    public String getModelIdentifier() {
        return nombreExp+ " [" +idExposiciones+ "]";
    }

    @Override
    public String getModelDescription() {
        return descripcion;
    }

    @Override
    public Uri getImageUri() {
        return Uri.EMPTY;
    }

    @Override
    public String[] getPrimaryKeys() {
        return new String[] {idExposiciones+""};
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put("IdExposicion", idExposiciones);
        values.put("NombreExp", nombreExp);
        values.put("Descripcion", descripcion);
        values.put("FechaInicio", fechaInicio.getTime());
        values.put("FechaFin", fechaFin.getTime());
        return values;
    }

    @Override
    public String toString() {
        return getModelIdentifier();
    }
}
