package com.joseduarte.practicafinalprimertrimestres.ui.lists;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.joseduarte.practicafinalprimertrimestres.R;
import com.joseduarte.practicafinalprimertrimestres.SourcesManager;
import com.joseduarte.practicafinalprimertrimestres.db.DBController;
import com.joseduarte.practicafinalprimertrimestres.db.tables.ExponenTable;
import com.joseduarte.practicafinalprimertrimestres.db.tables.TrabajosTable;
import com.joseduarte.practicafinalprimertrimestres.models.Artistas;
import com.joseduarte.practicafinalprimertrimestres.models.Exposiciones;
import com.joseduarte.practicafinalprimertrimestres.list.MyListFragment;
import com.joseduarte.practicafinalprimertrimestres.models.Models;
import com.joseduarte.practicafinalprimertrimestres.models.Trabajos;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExhibitionsFragment extends MyListFragment {

    public ExhibitionsFragment() {
        super(Exposiciones.class);
    }

    public ExhibitionsFragment(List<Models> models) {
        super(Exposiciones.class, models);
    }

    @Override
    public void onClick(View view, int position) {
        Bundle b = new Bundle();
        b.putSerializable("modelClass", Exposiciones.class);
        b.putSerializable("model", models.get(position));
        b.putSerializable("title", models.get(position).getModelIdentifier());

        navigateTo(getView(), R.id.action_exhibitions_list_to_models_information, b);

    }

    @Override
    public void onClickAddItemBtn(View view) {
        Bundle b = new Bundle();
        b.putBoolean("addMode", true);

        navigateTo(getView(), R.id.action_exhibitions_list_new_exhibitions, b);
    }

    @Deprecated
    @Override
    public void onClickEditItemBtn(int position) {
        final Exposiciones exposiciones = (Exposiciones)models.get(position);

        Bundle b = new Bundle();
        b.putBoolean("addMode", false);
        b.putSerializable("model", exposiciones);

        navigateTo(getView(), R.id.action_exhibitions_list_new_exhibitions, b);
    }

    @Override
    public void onClickDeleteItemBtn(final int position) {
        final Exposiciones exposicion = (Exposiciones)models.get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle(getContext().getString(R.string.on_delete_dialog_tittle) + exposicion.getModelIdentifier())
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBController db = SourcesManager.getDBController();

                        if(db != null) {
                            if(db.remove(Exposiciones.class, exposicion)) {
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
