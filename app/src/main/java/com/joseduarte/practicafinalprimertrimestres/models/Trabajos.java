package com.joseduarte.practicafinalprimertrimestres.models;

import android.content.ContentValues;
import android.net.Uri;

import androidx.annotation.NonNull;

import java.io.File;

public class Trabajos implements Models {
    private final String nombreTrab;
    private String descripcion;
    private String tamanio;
    private float peso;
    private Artistas artista;
    private File foto;

    private Trabajos(@NonNull TrabajosBuilder builder){
        nombreTrab = builder.nombreTrab;
        descripcion = builder.descripcion;
        artista = builder.artista;
        foto = builder.foto;
        tamanio = builder.tamanio;
        peso = builder.peso;
    }

    public String getNombreTrab() {
        return nombreTrab;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(@NonNull String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTamanio() {
        return tamanio;
    }

    public void setTamanio(@NonNull String tamanio) {
        this.tamanio = tamanio;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(@NonNull float peso) {
        this.peso = peso;
    }

    public Artistas getArtista() {
        return artista;
    }

    public void setArtista(@NonNull Artistas artista) {
        this.artista = artista;
    }

    public File getFoto() {
        return foto;
    }

    public void setFoto(@NonNull File foto) {
        this.foto = foto;
    }

    @Override
    public String getModelIdentifier() {
        return nombreTrab;
    }

    @Override
    public String getModelDescription() {
        return descripcion;
    }

    @Override
    public Uri getImageUri() {
        return Uri.fromFile(foto);
    }

    @Override
    public String[] getPrimaryKeys() {
        return new String[] {
                nombreTrab
        };
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put("NombreTrab", nombreTrab);
        values.put("Descripcion", descripcion);
        values.put("Tamanio", tamanio);
        values.put("Peso", peso);
        values.put("DniPasaporte", artista.getDniPasaporte());
        values.put("Foto", foto.getAbsolutePath());
        return values;
    }

    public static class TrabajosBuilder {
        private final String nombreTrab;
        private String descripcion = "";
        private String tamanio = "";
        private float peso = 0;
        private Artistas artista;
        private File foto;

        public TrabajosBuilder(@NonNull String nombreTrab) {
            this.nombreTrab = nombreTrab;
        }

        public TrabajosBuilder setDescripcion(@NonNull String descripcion) {
            this.descripcion = descripcion;
            return this;
        }

        public TrabajosBuilder setArtista(@NonNull Artistas artista) {
            this.artista = artista;
            return this;
        }

        public TrabajosBuilder setFoto(@NonNull File foto) {
            this.foto = foto;
            return this;
        }

        public TrabajosBuilder setFoto(@NonNull String rute) {
            this.foto = new File(rute);
            return this;
        }

        public TrabajosBuilder setTamanio(@NonNull String tamanio) {
            this.tamanio = tamanio;
            return this;
        }

        public TrabajosBuilder setPeso(@NonNull float peso) {
            this.peso = peso;
            return this;
        }

        public Trabajos build(){
            return new Trabajos(this);
        }
    }

    @Override
    public String toString() {
        return getModelIdentifier();
    }
}
