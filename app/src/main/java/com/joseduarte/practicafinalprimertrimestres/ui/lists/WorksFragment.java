package com.joseduarte.practicafinalprimertrimestres.ui.lists;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.joseduarte.practicafinalprimertrimestres.R;
import com.joseduarte.practicafinalprimertrimestres.SourcesManager;
import com.joseduarte.practicafinalprimertrimestres.db.DBController;
import com.joseduarte.practicafinalprimertrimestres.list.ModelsSpinnerAdapter;
import com.joseduarte.practicafinalprimertrimestres.list.MyListFragment;
import com.joseduarte.practicafinalprimertrimestres.models.Artistas;
import com.joseduarte.practicafinalprimertrimestres.models.Exposiciones;
import com.joseduarte.practicafinalprimertrimestres.models.Models;
import com.joseduarte.practicafinalprimertrimestres.models.Trabajos;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class WorksFragment extends MyListFragment {

    public WorksFragment(){
        super(Trabajos.class);
    }

    public WorksFragment(List<Models> models) {
        super(Trabajos.class, models);
    }

    @Override
    public void onClick(View view, int position) {
        Bundle b = new Bundle();
        b.putSerializable("modelClass", Trabajos.class);
        b.putSerializable("model", models.get(position));
        b.putSerializable("title", models.get(position).getModelIdentifier());

        navigateTo(getView(), R.id.action_work_list_to_models_information, b);
    }

    @Override
    public void onClickAddItemBtn(View view) {
        Bundle b = new Bundle();
        b.putBoolean("addMode", true);

        navigateTo(getView(), R.id.action_work_list_new_work, b);
    }

    @Deprecated
    @Override
    public void onClickEditItemBtn(int position) {
        final Trabajos trabajos = (Trabajos)models.get(position);

        Bundle b = new Bundle();
        b.putBoolean("addMode", false);
        b.putSerializable("model", trabajos);

        navigateTo(getView(), R.id.action_work_list_new_work, b);
    }

    @Override
    public void onClickDeleteItemBtn(final int position) {
        final Trabajos trabajos = (Trabajos)models.get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle(getContext().getString(R.string.on_delete_dialog_tittle) + trabajos.getModelIdentifier())
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBController db = SourcesManager.getDBController();
                        if(db != null) {
                            if(db.remove(Trabajos.class, trabajos)) {
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
