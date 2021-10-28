package com.joseduarte.practicafinalprimertrimestres.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.SurfaceControl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.joseduarte.practicafinalprimertrimestres.db.tables.ArtistasTable;
import com.joseduarte.practicafinalprimertrimestres.db.tables.ComentariosTable;
import com.joseduarte.practicafinalprimertrimestres.db.tables.ExponenTable;
import com.joseduarte.practicafinalprimertrimestres.db.tables.ExposicionesTable;
import com.joseduarte.practicafinalprimertrimestres.db.tables.TrabajosTable;
import com.joseduarte.practicafinalprimertrimestres.models.Artistas;
import com.joseduarte.practicafinalprimertrimestres.models.Artistas.ArtistasBuilder;
import com.joseduarte.practicafinalprimertrimestres.models.Comentarios;
import com.joseduarte.practicafinalprimertrimestres.models.Exponen;
import com.joseduarte.practicafinalprimertrimestres.models.Exposiciones;
import com.joseduarte.practicafinalprimertrimestres.models.Models;
import com.joseduarte.practicafinalprimertrimestres.models.Trabajos;
import com.joseduarte.practicafinalprimertrimestres.models.Trabajos.TrabajosBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBController extends SQLiteOpenHelper {

    /**
     * Las variables estaticas de las tablas se encuentrar en las clases
     * @see {@link ArtistasTable}
     *      {@link ComentariosTable}
     *      {@link ExponenTable}
     *      {@link ExposicionesTable}
     *      {@link TrabajosTable}
     */

    private static final int VERSION_BASEDATOS = 6;
    private static final String NOMBRE_BASEDATOS = "DB_Showroom.db";

    public DBController(@Nullable Context context) {
        super(context, NOMBRE_BASEDATOS, null, VERSION_BASEDATOS);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ExposicionesTable.INS_EXPOSICIONES);
        db.execSQL(ArtistasTable.INS_ARTISTAS);
        db.execSQL(ExponenTable.INS_EXPONEN);
        db.execSQL(TrabajosTable.INS_TRABAJOS);
        db.execSQL(ComentariosTable.INS_COMENTARIOS);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ComentariosTable.NOMBRE_TABLA);
        db.execSQL("DROP TABLE IF EXISTS " + TrabajosTable.NOMBRE_TABLA);
        db.execSQL("DROP TABLE IF EXISTS " + ExponenTable.NOMBRE_TABLA);
        db.execSQL("DROP TABLE IF EXISTS " + ArtistasTable.NOMBRE_TABLA);
        db.execSQL("DROP TABLE IF EXISTS " + ExposicionesTable.NOMBRE_TABLA);

        onCreate(db);
    }

    /**
     * recoge una lista de un model, la lista se recoge mediante una sentencia SELECT * FROM
     * @param modelClass es la clase del models, indica de que tabla quieres la lista
     * @return lista de modelos seleccionados
     * @see {@link Models}
     */
    public List<Models> getListModels(@NonNull Class<? extends Models> modelClass) {
        List<Models> lista = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = null;
        if (Exposiciones.class.equals(modelClass)) {
            c = db.query(
                    ExposicionesTable.NOMBRE_TABLA,
                    ExposicionesTable.COLUMNAS_TABLA,
                    null,
                    null,
                    null,
                    null,
                    null
            );
        }
        else if (Artistas.class.equals(modelClass)) {
            c = db.query(
                    ArtistasTable.NOMBRE_TABLA,
                    ArtistasTable.COLUMNAS_TABLA,
                    null,
                    null,
                    null,
                    null,
                    null
            );
        }
        else if (Comentarios.class.equals(modelClass)) {
            c = db.query(
                    ComentariosTable.NOMBRE_TABLA,
                    ComentariosTable.COLUMNAS_TABLA,
                    null,
                    null ,
                    null ,
                    null,
                    null
            );
        }
        else if (Exponen.class.equals(modelClass)) {
            c = db.query(
                    ExponenTable.NOMBRE_TABLA,
                    ExponenTable.COLUMNAS_TABLA,
                    null,
                    null,
                    null,
                    null,
                    null
            );
        }
        else if (Trabajos.class.equals(modelClass)) {
            c = db.query(
                    TrabajosTable.NOMBRE_TABLA,
                    TrabajosTable.COLUMNAS_TABLA,
                    null,
                    null ,
                    null ,
                    null,
                    null
            );
        }


        if(c != null && c.moveToFirst()) {
                do {
                    lista.add(newModelFromCursor(modelClass, c));
                } while (c.moveToNext());
            c.close();
        }
        db.close();

        return lista;
    }

    /**
     * crea un modelo del tipo indicado con los datos ACTUALES del cursor, este metodo no mueve
     * el cursor de su posición actual.
     * @param modelClass es la clase del models, indica de que tabla quieres la lista
     * @param c es el cursor.
     * @return el modelo
     * @see {@link Models}
     * @see {@link Cursor}
     */
    private Models newModelFromCursor(@NonNull Class<? extends Models> modelClass, @NonNull Cursor c) {
        Models model = null;
        if (Exposiciones.class.equals(modelClass)) {
            //ID EXPOSICION
            int id = c.getInt(c.getColumnIndex(ExposicionesTable.ID_EXPOSICION_FIELD));

            //NOMBRE EXPOSICION
            String name = c.getString(c.getColumnIndex(ExposicionesTable.NOMBRE_EXPOSICION_FIELD));

            //DESCRIPCION
            String description = c.getString(c.getColumnIndex(ExposicionesTable.DESCRIPCION_EXPOSICION_FIELD));

            //FECHAS
            Date dateIni = new Date(c.getLong(c.getColumnIndex(ExposicionesTable.FECHA_INICIO_EXPOSICION_FIELD)));
            Date dateFin = new Date(c.getLong(c.getColumnIndex(ExposicionesTable.FECHA_FIN_EXPOSICION_FIELD)));

            model = new Exposiciones(id, name, description, dateIni, dateFin);
        }
        else if (Artistas.class.equals(modelClass)) {
            model = new ArtistasBuilder(
                        c.getString(c.getColumnIndex(ArtistasTable.DNI_ARTISTA_FIELD))//DNI o CIF
                    )
                    //NOMBRE DEL ARTISTA
                    .setNombre(c.getString(c.getColumnIndex(ArtistasTable.NOMBRE_ARTISTA_FIELD)))
                    //DIRECCION DEL ARTISTA
                    .setDireccion(c.getString(c.getColumnIndex(ArtistasTable.DIRECCION_ARTISTA_FIELD)))
                    .setPoblacion(c.getString(c.getColumnIndex(ArtistasTable.POBLACION_ARTISTA_FIELD)))
                    .setProvincia(c.getString(c.getColumnIndex(ArtistasTable.PROVINCIA_ARTISTA_FIELD)))
                    .setPais(c.getString(c.getColumnIndex(ArtistasTable.PAIS_ARTISTA_FIELD)))
                    //INFORMACION DE CONTACTO
                    .setMovilTrabajo(c.getInt(c.getColumnIndex(ArtistasTable.MOVIL_TRABAJO_ARTISTA_FIELD)))
                    .setMovilPersonal(c.getInt(c.getColumnIndex(ArtistasTable.MOVIL_PERSONAL_ARTISTA_FIELD)))
                    .setTelefonoFijo(c.getInt(c.getColumnIndex(ArtistasTable.TELEFONO_FIJO_ARTISTA_FIELD)))
                    .setEmail(c.getString(c.getColumnIndex(ArtistasTable.EMAIL_ARTISTA_FIELD)))
                    .setWebBlog(c.getString(c.getColumnIndex(ArtistasTable.WEBBLOG_ARTISTA_FIELD)))
                    //FECHA NACIMIENTO
                    .setFechaNacimiento(c.getLong(c.getColumnIndex(ArtistasTable.FECHA_NACIMIENTO_ARTISTA_FIELD)))
                    //CREAMOS AL ARTISTA
                    .build();
        }
        else if (Comentarios.class.equals(modelClass)) {
            //EXPOSICION
            int idExposicion = c.getInt(c.getColumnIndex(ComentariosTable.ID_EXPOSICION_FIELD));
            Exposiciones exposicion = (Exposiciones) get(Exposiciones.class, new String[]{idExposicion+""});

            //TRABAJO
            String nombreTrabajo = c.getString(c.getColumnIndex(ComentariosTable.NOMBRE_TRABAJO_FIELD));
            Trabajos trabajo = (Trabajos) get(Trabajos.class, new String[]{nombreTrabajo});
            //COMENTARIO
            String comentario = c.getString(c.getColumnIndex(ComentariosTable.COMENTARIO_COMENTARIOS_FIELD));

            model = new Comentarios(exposicion, trabajo, comentario);
        }
        else if (Exponen.class.equals(modelClass)) {
            //EXPOSICION
            int idExposicion = c.getInt(c.getColumnIndex(ExponenTable.ID_EXPOSICION_FIELD));
            Exposiciones exposicion = (Exposiciones) get(Exposiciones.class, new String[]{idExposicion+""});

            //ARTISTA
            String dniArtista = c.getString(c.getColumnIndex(ExponenTable.DNI_ARTISTA_FIELD));
            Artistas artista = (Artistas) get(Artistas.class, new String[]{dniArtista});

            model = new Exponen(exposicion, artista);
        }
        else if (Trabajos.class.equals(modelClass)) {
            //ARTISTA
            String dniArtista = c.getString(c.getColumnIndex(TrabajosTable.DNI_ARTISTA_FIELD));
            Artistas artista = (Artistas) get(Artistas.class, new String[]{dniArtista});

            String nomTrabajo = c.getString(c.getColumnIndex(TrabajosTable.NOMBRE_TRABAJO_FIELD));
            model = new TrabajosBuilder(nomTrabajo)
                    .setDescripcion(c.getString(c.getColumnIndex(TrabajosTable.DESCRIPCION_TRABAJO_FIELD)))
                    .setTamanio(c.getString(c.getColumnIndex(TrabajosTable.TAMANIO_TRABAJO_FIELD)))
                    .setPeso(c.getFloat(c.getColumnIndex(TrabajosTable.PESO_TRABAJO_FIELD)))
                    .setArtista(artista)
                    .setFoto(c.getString(c.getColumnIndex(TrabajosTable.FOTO_TRABAJO_FIELD)))
                    .build();
        }

        return model;
    }

    public boolean add(@NonNull Class<? extends Models> modelClass,@NonNull Models models) {


        long result = -1;
        if(!has(modelClass, models)) {
            SQLiteDatabase db = getWritableDatabase();
            if (Exposiciones.class.equals(modelClass)) {
                result = db.insert(
                        ExposicionesTable.NOMBRE_TABLA,
                        null,
                        models.getContentValues()
                );
            }
            else if (Artistas.class.equals(modelClass)) {
                result =db.insert(
                        ArtistasTable.NOMBRE_TABLA,
                        null,
                        models.getContentValues()
                );
            }
            else if (Comentarios.class.equals(modelClass)) {
                result = db.insert(
                        ComentariosTable.NOMBRE_TABLA,
                        null,
                        models.getContentValues()
                );
            }
            else if (Exponen.class.equals(modelClass)) {
                result = db.insert(
                        ExponenTable.NOMBRE_TABLA,
                        null,
                        models.getContentValues()
                );
            }
            else if (Trabajos.class.equals(modelClass)) {
                result = db.insert(
                        TrabajosTable.NOMBRE_TABLA,
                        null,
                        models.getContentValues()
                );
            }
            db.close();
        }

        return (result!=-1);
    }

    public boolean update(@NonNull Class<? extends Models> modelClass,@NonNull Models models) {


        long result = -1;
        if(has(modelClass, models)) {
            SQLiteDatabase db = getWritableDatabase();
            if (Exposiciones.class.equals(modelClass)) {
                result = db.update(
                        ExposicionesTable.NOMBRE_TABLA,
                        models.getContentValues(),
                        ExposicionesTable.DEFAULT_WHERE_SENTENCE,
                        models.getPrimaryKeys()
                );
            }
            else if (Artistas.class.equals(modelClass)) {
                result = db.update(
                        ArtistasTable.NOMBRE_TABLA,
                        models.getContentValues(),
                        ArtistasTable.DEFAULT_WHERE_SENTENCE,
                        models.getPrimaryKeys()
                );
            }
            else if (Comentarios.class.equals(modelClass)) {
                result = db.update(
                        ComentariosTable.NOMBRE_TABLA,
                        models.getContentValues(),
                        ComentariosTable.DEFAULT_WHERE_SENTENCE,
                        models.getPrimaryKeys()
                );
            }
            else if (Exponen.class.equals(modelClass)) {
                result = db.update(
                        ExponenTable.NOMBRE_TABLA,
                        models.getContentValues(),
                        ExponenTable.DEFAULT_WHERE_SENTENCE,
                        models.getPrimaryKeys()
                );
            }
            else if (Trabajos.class.equals(modelClass)) {
                result = db.update(
                        TrabajosTable.NOMBRE_TABLA,
                        models.getContentValues(),
                        TrabajosTable.DEFAULT_WHERE_SENTENCE,
                        models.getPrimaryKeys()
                );
            }
            db.close();
        }



        return (result!=-1);
    }

    public boolean remove(@NonNull Class<? extends Models> modelClass,@NonNull Models models) {

            long result = -1;
            if(has(modelClass, models)) {
                if (Exposiciones.class.equals(modelClass)) {
                    SQLiteDatabase db = getWritableDatabase();
                    db.delete(
                            ExponenTable.NOMBRE_TABLA,
                            ExposicionesTable.makeWhereSentence(ExponenTable.ID_EXPOSICION_FIELD),
                            models.getPrimaryKeys()
                    );

                    db.delete(
                            ComentariosTable.NOMBRE_TABLA,
                            ExposicionesTable.makeWhereSentence(ComentariosTable.ID_EXPOSICION_FIELD),
                            models.getPrimaryKeys()
                    );

                    result = db.delete(
                            ExposicionesTable.NOMBRE_TABLA,
                            ExposicionesTable.DEFAULT_WHERE_SENTENCE,
                            models.getPrimaryKeys()
                    );
                    db.close();
                }
                else if (Artistas.class.equals(modelClass)) {

                    List<Models> modelsList = query(
                            Trabajos.class,
                            "SELECT * FROM " + TrabajosTable.NOMBRE_TABLA + " " +
                                    TrabajosTable.makeWhereSentence(TrabajosTable.DNI_ARTISTA_FIELD),
                            models.getPrimaryKeys()
                    );

                    for (Models model: modelsList) {
                        remove(Trabajos.class, model);
                    }

                    SQLiteDatabase db = getWritableDatabase();
                    db.delete(
                            ExponenTable.NOMBRE_TABLA,
                            ArtistasTable.makeWhereSentence(ExponenTable.DNI_ARTISTA_FIELD),
                            models.getPrimaryKeys()
                    );

                    result += db.delete(
                            ArtistasTable.NOMBRE_TABLA,
                            ArtistasTable.DEFAULT_WHERE_SENTENCE,
                            models.getPrimaryKeys()
                    );
                    db.close();
                }
                else if (Comentarios.class.equals(modelClass)) {
                    SQLiteDatabase db = getWritableDatabase();
                    result = db.delete(
                            ComentariosTable.NOMBRE_TABLA,
                            ComentariosTable.DEFAULT_WHERE_SENTENCE,
                            models.getPrimaryKeys()
                    );
                    db.close();
                }
                else if (Exponen.class.equals(modelClass)) {
                    SQLiteDatabase db = getWritableDatabase();
                    result = db.delete(
                            ExponenTable.NOMBRE_TABLA,
                            ExponenTable.DEFAULT_WHERE_SENTENCE,
                            models.getPrimaryKeys()
                    );
                    db.close();
                }
                else if (Trabajos.class.equals(modelClass)) {

                    SQLiteDatabase db = getWritableDatabase();
                    db.delete(
                            ComentariosTable.NOMBRE_TABLA,
                            TrabajosTable.makeWhereSentence(ComentariosTable.NOMBRE_TRABAJO_FIELD),
                            models.getPrimaryKeys()
                    );

                    result = db.delete(
                            TrabajosTable.NOMBRE_TABLA,
                            TrabajosTable.DEFAULT_WHERE_SENTENCE,
                            models.getPrimaryKeys()
                    );
                    db.close();
                }

            }

        return (result!=-1);
    }

    public boolean remove(@NonNull Class<? extends Models> modelClass,@NonNull String[] keys) {

        long result = -1;
        if(has(modelClass, keys)) {
            SQLiteDatabase db = getWritableDatabase();
            if (Exposiciones.class.equals(modelClass)) {
                result = db.delete(
                        ExposicionesTable.NOMBRE_TABLA,
                        ExposicionesTable.DEFAULT_WHERE_SENTENCE,
                        keys
                );
            }
            else if (Artistas.class.equals(modelClass)) {
                db.delete(
                        TrabajosTable.NOMBRE_TABLA,
                        TrabajosTable.makeWhereSentence(TrabajosTable.DNI_ARTISTA_FIELD),
                        keys
                );

                result += db.delete(
                        ArtistasTable.NOMBRE_TABLA,
                        ArtistasTable.DEFAULT_WHERE_SENTENCE,
                        keys
                );
            }
            else if (Comentarios.class.equals(modelClass)) {
                result = db.delete(
                        ComentariosTable.NOMBRE_TABLA,
                        ComentariosTable.DEFAULT_WHERE_SENTENCE,
                        keys
                );
            }
            else if (Exponen.class.equals(modelClass)) {
                result = db.delete(
                        ExponenTable.NOMBRE_TABLA,
                        ExponenTable.DEFAULT_WHERE_SENTENCE,
                        keys
                );
            }
            else if (Trabajos.class.equals(modelClass)) {

                db.delete(
                        ComentariosTable.NOMBRE_TABLA,
                        TrabajosTable.makeWhereSentence(ComentariosTable.NOMBRE_TRABAJO_FIELD),
                        keys
                );

                result = db.delete(
                        TrabajosTable.NOMBRE_TABLA,
                        TrabajosTable.DEFAULT_WHERE_SENTENCE,
                        keys
                );
            }
            db.close();
        }

        return (result!=-1);
    }

    public Models get(@NonNull Class<? extends Models> modelClass,@NonNull String[] primaryKeyModel) {
        SQLiteDatabase db = getReadableDatabase();
        Models model = null;

        Cursor c = null;
        if (Exposiciones.class.equals(modelClass)) {
            c = db.rawQuery(
                    ExposicionesTable.SELECT_BY_PRIMARY_KEY,
                    primaryKeyModel
            );
        }
        else if (Artistas.class.equals(modelClass)) {
            c = db.rawQuery(
                    ArtistasTable.SELECT_BY_PRIMARY_KEY,
                    primaryKeyModel
            );
        }
        else if (Comentarios.class.equals(modelClass)) {
            c = db.rawQuery(
                    ComentariosTable.SELECT_BY_PRIMARY_KEY,
                    primaryKeyModel
            );
        }
        else if (Exponen.class.equals(modelClass)) {
            c = db.rawQuery(
                    ExponenTable.SELECT_BY_PRIMARY_KEY,
                    primaryKeyModel
            );
        }
        else if (Trabajos.class.equals(modelClass)) {
            c = db.rawQuery(
                    TrabajosTable.SELECT_BY_PRIMARY_KEY,
                    primaryKeyModel
            );
        }

        if(c != null && c.getCount() == 1 && c.moveToFirst()) {
            model = newModelFromCursor(modelClass, c);
        }
        c.close();
        db.close();

        return model;
    }

    public boolean has(@NonNull Class<? extends Models> modelClass,@NonNull  Models models) {
        SQLiteDatabase db = getReadableDatabase();
        int resultCount = 0;

        if (Exposiciones.class.equals(modelClass)) {
            Cursor c = db.rawQuery(
                    ExposicionesTable.SELECT_BY_PRIMARY_KEY,
                    models.getPrimaryKeys()
            );
            resultCount = c.getCount();
            c.close();
        }
        else if (Artistas.class.equals(modelClass)) {
            Cursor c = db.rawQuery(
                    ArtistasTable.SELECT_BY_PRIMARY_KEY,
                    models.getPrimaryKeys()
            );
            resultCount = c.getCount();
            c.close();
        }
        else if (Comentarios.class.equals(modelClass)) {
            Cursor c = db.rawQuery(
                    ComentariosTable.SELECT_BY_PRIMARY_KEY,
                    models.getPrimaryKeys()
            );
            resultCount = c.getCount();
            c.close();
        }
        else if (Exponen.class.equals(modelClass)) {
            Cursor c = db.rawQuery(
                    ExponenTable.SELECT_BY_PRIMARY_KEY,
                    models.getPrimaryKeys()
            );
            resultCount = c.getCount();
            c.close();
        }
        else if (Trabajos.class.equals(modelClass)) {
            Cursor c = db.rawQuery(
                    TrabajosTable.SELECT_BY_PRIMARY_KEY,
                    models.getPrimaryKeys()
            );
            resultCount = c.getCount();
            c.close();
        }

        db.close();
        if(resultCount > 0) return true;
        return false;
    }

    public boolean has(@NonNull Class<? extends Models> modelClass,@NonNull  String[] keys) {
        SQLiteDatabase db = getReadableDatabase();
        int resultCount = 0;

        if (Exposiciones.class.equals(modelClass)) {
            Cursor c = db.rawQuery(
                    ExposicionesTable.SELECT_BY_PRIMARY_KEY,
                    keys
            );
            resultCount = c.getCount();
            c.close();
        }
        else if (Artistas.class.equals(modelClass)) {
            Cursor c = db.rawQuery(
                    ArtistasTable.SELECT_BY_PRIMARY_KEY,
                    keys
            );
            resultCount = c.getCount();
            c.close();
        }
        else if (Comentarios.class.equals(modelClass)) {
            Cursor c = db.rawQuery(
                    ComentariosTable.SELECT_BY_PRIMARY_KEY,
                    keys
            );
            resultCount = c.getCount();
            c.close();
        }
        else if (Exponen.class.equals(modelClass)) {
            Cursor c = db.rawQuery(
                    ExponenTable.SELECT_BY_PRIMARY_KEY,
                    keys
            );
            resultCount = c.getCount();
            c.close();
        }
        else if (Trabajos.class.equals(modelClass)) {
            Cursor c = db.rawQuery(
                    TrabajosTable.SELECT_BY_PRIMARY_KEY,
                    keys
            );
            resultCount = c.getCount();
            c.close();
        }

        db.close();
        if(resultCount > 0) return true;
        return false;
    }

    public List<Models> query(Class<? extends Models> modelClass, String query, String[] args) {
        List<Models> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.rawQuery(query, args);
        if(c.moveToFirst()){
            do{
               list.add(newModelFromCursor(modelClass, c));
            }while (c.moveToNext());
        }

        c.close();
        db.close();
        return list;
    }
}
