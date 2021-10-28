package com.joseduarte.practicafinalprimertrimestres.list;

import android.content.Context;

import androidx.annotation.NonNull;

import com.joseduarte.practicafinalprimertrimestres.R;
import com.joseduarte.practicafinalprimertrimestres.SourcesManager;
import com.joseduarte.practicafinalprimertrimestres.db.DBController;
import com.joseduarte.practicafinalprimertrimestres.models.Artistas;
import com.joseduarte.practicafinalprimertrimestres.models.Comentarios;
import com.joseduarte.practicafinalprimertrimestres.models.Exposiciones;
import com.joseduarte.practicafinalprimertrimestres.models.Models;
import com.joseduarte.practicafinalprimertrimestres.models.Trabajos;

import java.util.HashMap;
import java.util.List;

public abstract class ExpandableListDataBuilder {
    public static HashMap<String, List<Models>> getData( @NonNull Class<? extends Models> modelsClass,
                                                         @NonNull Context context) {
        HashMap<String, List<Models>> expandableListDetail = new HashMap<>();
        DBController db = SourcesManager.getDBController();
        String expandableTitle = "";

        if(db != null) {
            List<Models> data = db.getListModels(modelsClass);

            if(Exposiciones.class.isAssignableFrom(modelsClass)) {
                expandableTitle = context.getString(R.string.exhibition_title);
            }
            else if(Artistas.class.isAssignableFrom(modelsClass)) {
                expandableTitle = context.getString(R.string.artist_tittle);
            }
            else if(Comentarios.class.isAssignableFrom(modelsClass)) {
                expandableTitle = context.getString(R.string.commentary_title);
            }
            else if(Trabajos.class.isAssignableFrom(modelsClass)) {
                expandableTitle = context.getString(R.string.work_title);
            }

            expandableListDetail.put(expandableTitle, data);
        }

        return expandableListDetail;
    }

    public static HashMap<String, List<? extends Models>> getData( @NonNull Class<? extends Models> modelsClass,
                                                         @NonNull Context context,
                                                         @NonNull List<? extends Models> models) {
        HashMap<String, List<? extends Models>> expandableListDetail = new HashMap<>();
        String expandableTitle = "";


        if(Exposiciones.class.isAssignableFrom(modelsClass)) {
           expandableTitle = context.getString(R.string.exhibition_title);
        }
        else if(Artistas.class.isAssignableFrom(modelsClass)) {
            expandableTitle = context.getString(R.string.artist_tittle);
        }
        else if(Comentarios.class.isAssignableFrom(modelsClass)) {
            expandableTitle = context.getString(R.string.commentary_title);
        }
        else if(Trabajos.class.isAssignableFrom(modelsClass)) {
            expandableTitle = context.getString(R.string.work_title);
        }

        expandableListDetail.put(expandableTitle, models);

        return expandableListDetail;
    }

    public static HashMap<String, List<? extends Models>> getData(@NonNull String title, @NonNull List<? extends Models> models) {
        HashMap<String, List<? extends Models>> expandableListDetail = new HashMap<>();
        expandableListDetail.put(title, models);
        return expandableListDetail;
    }

    public static HashMap<String, List<? extends Models>> getData(@NonNull String[] titles, @NonNull List<? extends Models>[] models) {
        HashMap<String, List<? extends Models>> expandableListDetail = new HashMap<>();

        for (int i = 0; i < titles.length; i++) {
            expandableListDetail.put(titles[i], models[i]);
        }

        return expandableListDetail;
    }
}
