package com.joseduarte.practicafinalprimertrimestres.ui.lists;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.joseduarte.practicafinalprimertrimestres.R;
import com.joseduarte.practicafinalprimertrimestres.SourcesManager;
import com.joseduarte.practicafinalprimertrimestres.db.DBController;
import com.joseduarte.practicafinalprimertrimestres.db.tables.ArtistasTable;
import com.joseduarte.practicafinalprimertrimestres.db.tables.ExponenTable;
import com.joseduarte.practicafinalprimertrimestres.db.tables.TrabajosTable;
import com.joseduarte.practicafinalprimertrimestres.list.MyListFragment;
import com.joseduarte.practicafinalprimertrimestres.models.Artistas;
import com.joseduarte.practicafinalprimertrimestres.models.Comentarios;
import com.joseduarte.practicafinalprimertrimestres.models.Exponen;
import com.joseduarte.practicafinalprimertrimestres.models.Exposiciones;
import com.joseduarte.practicafinalprimertrimestres.models.Models;
import com.joseduarte.practicafinalprimertrimestres.models.Trabajos;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ArtistsFragment extends MyListFragment {

    public ArtistsFragment(){
        super(Artistas.class);
    }

    public ArtistsFragment(List<Models> models) {
        super(Artistas.class, models);
    }

    @Override
    public void onClick(View view, int position) {
        Bundle b = new Bundle();
        b.putSerializable("modelClass", Artistas.class);
        b.putSerializable("model", models.get(position));
        b.putSerializable("title", models.get(position).getModelIdentifier());

        Artistas artistas = (Artistas) models.get(position);
        Calendar artistBirthdayCalendar = Calendar.getInstance();
        artistBirthdayCalendar.setTime(artistas.getFechaNacimiento());

        Calendar currentDayCalendar = Calendar.getInstance();
        currentDayCalendar.setTime(new Date());

        Boolean isBirthday = (artistBirthdayCalendar.get(Calendar.DAY_OF_MONTH)==currentDayCalendar.get(Calendar.DAY_OF_MONTH));
        isBirthday = isBirthday && (artistBirthdayCalendar.get(Calendar.MONTH)==currentDayCalendar.get(Calendar.MONTH));
        if(isBirthday) {
            Toast toast = Toast.makeText(getContext(),
                    getContext().getString(R.string.happy_birthday) + " " + artistas.getNombre() + " !!!!!!!!!!",
                    Toast.LENGTH_LONG);

            toast.show();
        }

        navigateTo(getView(), R.id.action_artistas_list_to_models_information, b);
    }

    @Override
    public void onClickAddItemBtn(View view) {
        Bundle b = new Bundle();
        b.putBoolean("addMode", true);

        navigateTo(getView(), R.id.action_artist_list_new_artist, b);

        updateList();
    }


    @Override
    public void onClickEditItemBtn(int position) {
        final Artistas artistas = (Artistas)models.get(position);

        Bundle b = new Bundle();
        b.putBoolean("addMode", false);
        b.putSerializable("model", artistas);

        navigateTo(getView(), R.id.action_artist_list_new_artist, b);

        updateList();
    }

    @Override
    public void onClickDeleteItemBtn(final int position) {
        final Artistas artistas = (Artistas)models.get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle(getContext().getString(R.string.on_delete_dialog_tittle) + artistas.getModelIdentifier())
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        DBController db = SourcesManager.getDBController();
                        if(db != null) {
                            if(db.remove(Artistas.class, artistas)) {
                                models.remove(position);
                                updateList();
                            }
                            else {
                                Toast toast = Toast.makeText(getContext(), R.string.on_db_deleting_error, Toast.LENGTH_LONG);
                                toast.show();
                            }
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        final AlertDialog dialog = builder.create();
        dialog.show();
    }
}
