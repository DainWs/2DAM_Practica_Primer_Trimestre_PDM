package com.joseduarte.practicafinalprimertrimestres.models;

import android.content.ContentValues;
import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import java.io.File;
import java.util.Date;

public class Artistas implements Models {
    private final String dniPasaporte;
    private String nombre;
    private String direccion;
    private String poblacion;
    private String provincia;
    private String pais;
    private int movilTrabajo;
    private int movilPersonal;
    private int telefonoFijo;
    private String email;
    private String webBlog;
    private Date fechaNacimiento;

    private Artistas(@NonNull ArtistasBuilder builder) {
        dniPasaporte = builder.dniPasaporte;
        nombre = builder.nombre;
        direccion = builder.direccion;
        poblacion = builder.poblacion;
        provincia = builder.provincia;
        pais = builder.pais;
        movilTrabajo = builder.movilTrabajo;
        movilPersonal = builder.movilPersonal;
        telefonoFijo = builder.telefonoFijo;
        email = builder.email;
        webBlog = builder.webBlog;
        fechaNacimiento = builder.fechaNacimiento;
    }

    public String getDniPasaporte() {
        return dniPasaporte;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public String getProvincia() {
        return provincia;
    }

    public String getPais() {
        return pais;
    }

    public int getMovilTrabajo() {
        return movilTrabajo;
    }

    public int getMovilPersonal() {
        return movilPersonal;
    }

    public int getTelefonoFijo() {
        return telefonoFijo;
    }

    public String getEmail() {
        return email;
    }

    public String getWebBlog() {
        return webBlog;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setNombre(@NonNull String nombre) {
        this.nombre = nombre;
    }

    public void setDireccion(@NonNull String direccion) {
        this.direccion = direccion;
    }

    public void setPoblacion(@NonNull String poblacion) {
        this.poblacion = poblacion;
    }

    public void setProvincia(@NonNull String provincia) {
        this.provincia = provincia;
    }

    public void setPais(@NonNull String pais) {
        this.pais = pais;
    }

    public void setMovilTrabajo(@NonNull int movilTrabajo) {
        this.movilTrabajo = movilTrabajo;
    }

    public void setMovilPersonal(@NonNull int movilPersonal) {
        this.movilPersonal = movilPersonal;
    }

    public void setTelefonoFijo(@NonNull int telefonoFijo) {
        this.telefonoFijo = telefonoFijo;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    public void setWebBlog(@NonNull String webBlog) {
        this.webBlog = webBlog;
    }

    @Override
    public String getModelIdentifier() {
        return nombre + " [" + dniPasaporte + "]";
    }

    @Override
    public String getModelDescription() {
        return direccion + ", " +poblacion+ ", " +poblacion+ ", " +pais+ ".";
    }

    @Override
    public Uri getImageUri() {
        return Uri.EMPTY;
    }

    @Override
    public String[] getPrimaryKeys() {
        return new String[] {dniPasaporte};
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put("DniPasaporte", dniPasaporte);
        values.put("Nombre", nombre);
        values.put("Direccion", direccion);
        values.put("Poblacion", poblacion);
        values.put("Provincia", provincia);
        values.put("Pais", pais);
        values.put("MovilTrabajo", movilTrabajo);
        values.put("MovilPersonal", movilPersonal);
        values.put("TelefonoFijo", telefonoFijo);
        values.put("Email", email);
        values.put("WebBlog", webBlog);
        values.put("FechaNacimiento",fechaNacimiento.getTime());
        return values;
    }

    //BUILDER
    public static class ArtistasBuilder {
        private final String dniPasaporte;
        private String nombre;
        private String direccion = "";
        private String poblacion = "";
        private String provincia = "";
        private String pais = "";
        private int movilTrabajo = 0;
        private int movilPersonal = 0;
        private int telefonoFijo = 0;
        private String email = "";
        private String webBlog = "";
        private Date fechaNacimiento = new Date();

        public ArtistasBuilder(@NonNull String dniPasaporte) {
            this.dniPasaporte = dniPasaporte;
        }

        public ArtistasBuilder setNombre(@NonNull String nombre) {
            this.nombre = nombre;
            return this;
        }

        public ArtistasBuilder setDireccion(@NonNull String direccion) {
            this.direccion = direccion;
            return this;
        }

        public ArtistasBuilder setMovilTrabajo(@NonNull int movilTrabajo) {
            this.movilTrabajo = movilTrabajo;
            return this;
        }

        public ArtistasBuilder setEmail(@NonNull String email) {
            this.email = email;
            return this;
        }

        public ArtistasBuilder setFechaNacimiento(@NonNull Date fechaNacimiento) {
            this.fechaNacimiento = fechaNacimiento;
            return this;
        }

        public ArtistasBuilder setFechaNacimiento(@NonNull Long fechaNacimientoTime) {
            this.fechaNacimiento = new Date(fechaNacimientoTime);
            return this;
        }

        public ArtistasBuilder setPoblacion(@NonNull String poblacion) {
            this.poblacion = poblacion;
            return this;
        }

        public ArtistasBuilder setProvincia(@NonNull String provincia) {
            this.provincia = provincia;
            return this;
        }

        public ArtistasBuilder setPais(@NonNull String pais) {
            this.pais = pais;
            return this;
        }

        public ArtistasBuilder setMovilPersonal(@NonNull int movilPersonal) {
            this.movilPersonal = movilPersonal;
            return this;
        }

        public ArtistasBuilder setTelefonoFijo(@NonNull int telefonoFijo) {
            this.telefonoFijo = telefonoFijo;
            return this;
        }

        public ArtistasBuilder setWebBlog(@NonNull String webBlog) {
            this.webBlog = webBlog;
            return this;
        }

        public ArtistasBuilder applyData(Object[] data) {
            setDireccion((String)data[0]);
            setPoblacion((String)data[1]);
            setProvincia((String)data[2]);
            setPais((String)data[3]);
            setMovilTrabajo((Integer)data[4]);
            setMovilPersonal((Integer)data[5]);
            setTelefonoFijo((Integer)data[6]);
            setEmail((String)data[7]);
            setWebBlog((String)data[8]);
            setFechaNacimiento(new Date((Long)data[9]));
            return this;
        }

        public Artistas build() {
            return new Artistas(this);
        }
    }


    @Override
    public String toString() {
        return getModelIdentifier();
    }
}
