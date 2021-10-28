package com.joseduarte.practicafinalprimertrimestres.ui.lists;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.joseduarte.practicafinalprimertrimestres.R;
import com.joseduarte.practicafinalprimertrimestres.SourcesManager;
import com.joseduarte.practicafinalprimertrimestres.db.DBController;
import com.joseduarte.practicafinalprimertrimestres.db.tables.ComentariosTable;
import com.joseduarte.practicafinalprimertrimestres.db.tables.ExposicionesTable;
import com.joseduarte.practicafinalprimertrimestres.db.tables.TrabajosTable;
import com.joseduarte.practicafinalprimertrimestres.list.ModelsSpinnerAdapter;
import com.joseduarte.practicafinalprimertrimestres.list.MySearchableItemListFragment;
import com.joseduarte.practicafinalprimertrimestres.models.Comentarios;
import com.joseduarte.practicafinalprimertrimestres.models.Exposiciones;
import com.joseduarte.practicafinalprimertrimestres.models.Models;
import com.joseduarte.practicafinalprimertrimestres.models.Trabajos;

import java.util.ArrayList;
import java.util.List;

public class CommentsSearchableFragment extends MySearchableItemListFragment {

    private Exposiciones exposicionSelected;
    private Trabajos trabajoSelected;

    public CommentsSearchableFragment() {
        super(Comentarios.class, 2);
    }

    @Override
    public void onClickAddItemBtn(View view) {
        final View dialogView = View.inflate(getContext(), R.layout.model_creation_commentary_dialog, null);

        final DBController db = new DBController(dialogView.getContext());
        final List<Models> exposiciones = db.getListModels(Exposiciones.class);

        final Spinner expositionSpinner = (Spinner)dialogView.findViewById(R.id.model_exposition_spinner);
        SpinnerAdapter expAdapter = new ArrayAdapter<Models>(
                dialogView.getContext(),
                android.R.layout.simple_spinner_item,
                exposiciones);

        expositionSpinner.setAdapter(new ModelsSpinnerAdapter(
                expAdapter,
                dialogView.getContext(),
                dialogView.getContext()
                        .getString(R.string.spinner_select_exhibition)
        ));

        List<Models> works = db.getListModels(Trabajos.class);

        final Spinner workSpinner = (Spinner)dialogView.findViewById(R.id.model_work_spinner);
        SpinnerAdapter workAdapter = new ArrayAdapter<Models>(
                dialogView.getContext(),
                android.R.layout.simple_spinner_item,
                works);

        workSpinner.setAdapter(new ModelsSpinnerAdapter(
                workAdapter,
                dialogView.getContext(),
                dialogView.getContext()
                        .getString(R.string.spinner_select_work)
        ));

        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext())
                .setTitle(R.string.commentary_title)
                .setView(dialogView)
                .setPositiveButton(R.string.confirm,null)
                .setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }
                );

        final AlertDialog dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialogInterface) {

                Button button = ((AlertDialog)dialogInterface).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        EditText commentary = (EditText)dialogView.findViewById(R.id.model_comment_commentary);

                        Context context = dialogView.getContext();
                        String errorMessage = "";
                        int errorCount = 0;

                        Exposiciones exp = null;
                        if(expositionSpinner.getSelectedItemPosition() != 0) {
                            exp = (Exposiciones) expositionSpinner.getSelectedItem();
                        }else {
                            expositionSpinner.setBackgroundColor(0xFFC2C2);
                            errorMessage +=  context.getString(R.string.select_something)+ context.getString(R.string.exhibition_title) + "\n";
                            errorCount++;
                        }

                        Trabajos trb = null;
                        if(workSpinner.getSelectedItemPosition() != 0) {
                            trb = (Trabajos) workSpinner.getSelectedItem();
                        } else {
                            expositionSpinner.setBackgroundColor(0xFFC2C2);
                            errorMessage += context.getString(R.string.select_something) + context.getString(R.string.work_title) + "\n";
                            errorCount++;
                        }

                        String commentaryText = "";
                        if(!commentary.getText().toString().isEmpty()){
                            commentaryText = commentary.getText().toString();
                        } else {
                            commentary.setTextColor(0xFFC2C2);
                            commentary.setBackgroundColor(0xFFC2C2);
                            errorMessage += context.getString(R.string.field_error_message_edittext) + context.getString(R.string.field_comments_commentary);
                            errorCount++;
                        }

                        if (errorCount > 0) {
                            Toast toast = Toast.makeText(view.getContext(), errorMessage, Toast.LENGTH_LONG);
                            toast.show();
                        } else {
                            try{

                                Comentarios comentario = new Comentarios(exp, trb, commentaryText);

                                DBController db = new DBController(view.getContext());
                                if(db.add(Comentarios.class, comentario)) {
                                    models.add(comentario);
                                    myListAdapter.update(models);
                                    dialog.dismiss();
                                }


                            }catch (Exception ex){
                                Toast toast = Toast.makeText(view.getContext(), R.string.on_db_operations_error, Toast.LENGTH_LONG);
                                toast.show();
                            }
                        }
                    }
                });
            }
        });
        dialog.show();
    }

    @Override
    public void onClickEditItemBtn(int position) {
        final View dialogView = View.inflate(getContext(), R.layout.model_creation_commentary_dialog, null);

        final Comentarios comment = (Comentarios) models.get(position);

        List<Exposiciones> exposiciones = new ArrayList<>();
        exposiciones.add(comment.getExposicion());

        final Spinner expositionSpinner = (Spinner)dialogView.findViewById(R.id.model_exposition_spinner);
        SpinnerAdapter expAdapter = new ArrayAdapter<Exposiciones>(
                dialogView.getContext(),
                android.R.layout.simple_spinner_item,
                exposiciones);
        expositionSpinner.setAdapter(expAdapter);
        expositionSpinner.setEnabled(false);

        List<Trabajos> works = new ArrayList<>();
        works.add(comment.getTrabajo());

        final Spinner workSpinner = (Spinner)dialogView.findViewById(R.id.model_work_spinner);
        SpinnerAdapter workAdapter = new ArrayAdapter<Trabajos>(
                dialogView.getContext(),
                android.R.layout.simple_spinner_item,
                works);
        workSpinner.setAdapter(workAdapter);
        workSpinner.setEnabled(false);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle(R.string.exhibition_title)
                .setView(dialogView)
                .setPositiveButton(R.string.confirm,null)
                .setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }
                );

        final AlertDialog dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialogInterface) {

                Button button = ((AlertDialog)dialogInterface).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        EditText commentary = (EditText)dialogView.findViewById(R.id.model_comment_commentary);

                        Context context = dialogView.getContext();
                        String errorMessage = "";
                        int errorCount = 0;

                        String commentaryText = "";
                        if(!commentary.getText().toString().isEmpty()){
                            commentaryText = commentary.getText().toString();
                        } else {
                            commentary.setTextColor(0xFFC2C2);
                            commentary.setBackgroundColor(0xFFC2C2);
                            errorMessage += context.getString(R.string.field_error_message_edittext) + context.getString(R.string.field_comments_commentary);
                            errorCount++;
                        }

                        if (errorCount > 0) {
                            Toast toast = Toast.makeText(view.getContext(), errorMessage, Toast.LENGTH_LONG);
                            toast.show();
                        } else {
                            try{

                                Comentarios comentario = new Comentarios(comment.getExposicion(), comment.getTrabajo(), commentaryText);

                                DBController db = new DBController(view.getContext());
                                if(db.update(Comentarios.class, comentario)) {
                                    models.add(comentario);
                                    myListAdapter.update(models);
                                    dialog.dismiss();
                                }


                            }catch (Exception ex){
                                Toast toast = Toast.makeText(view.getContext(), R.string.on_db_operations_error, Toast.LENGTH_LONG);
                                toast.show();
                            }
                        }
                    }
                });
            }
        });
        dialog.show();
    }

    @Override
    public void onClickDeleteItemBtn(final int position) {
        final Comentarios comment = (Comentarios)models.get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle(getContext().getString(R.string.on_delete_dialog_tittle) + comment.getModelIdentifier())
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBController db = SourcesManager.getDBController();
                        if(db != null) {
                            if(db.remove(Comentarios.class, comment)) {
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


    public void onSpinnerExpClicked(AdapterView<?> parent, View view, int position, long id){
        if(position == 0) {
            exposicionSelected = null;
        }
        else {
            exposicionSelected = (Exposiciones) exposicionesList.get(position - 1);
        }
        selectConsultOfList();
    }

    public void onSpinnerTraClicked(AdapterView<?> parent, View view, int position, long id){
        if(position == 0) {
            trabajoSelected = null;
        }
        else {
            trabajoSelected = (Trabajos) trabajosList.get(position - 1);
        }
        selectConsultOfList();
    }

    public void selectConsultOfList() {
        String sql = "SELECT * FROM " + ComentariosTable.NOMBRE_TABLA +" WHERE ";
        String[] keys = new String[0];

        if(exposicionSelected != null) {
            keys = new String[] {exposicionSelected.getIdExposiciones()+""};
            sql += ExposicionesTable
                    .makeWhereSentence(ComentariosTable.ID_EXPOSICION_FIELD);
        }

        if(exposicionSelected != null && trabajoSelected != null) {
            sql += " AND ";
        }

        if(trabajoSelected != null) {
            keys = new String[] {trabajoSelected.getNombreTrab()};
            sql += TrabajosTable
                    .makeWhereSentence(ComentariosTable.NOMBRE_TRABAJO_FIELD);
        }

        if(exposicionSelected != null && trabajoSelected != null) {
            keys = new String[] {
                    exposicionSelected.getIdExposiciones()+"",
                    trabajoSelected.getNombreTrab()
            };
        }

        if(exposicionSelected != null || trabajoSelected != null) {
            models = new DBController(getContext())
                    .query(
                            Comentarios.class,
                            sql,
                            keys
                    );
        }
        else {
            models = SourcesManager.getDBController().getListModels(modelClass);
        }

        updateList();

    }
}
