package com.joseduarte.practicafinalprimertrimestres.appliers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.joseduarte.practicafinalprimertrimestres.R;
import com.joseduarte.practicafinalprimertrimestres.SourcesManager;
import com.joseduarte.practicafinalprimertrimestres.db.DBController;
import com.joseduarte.practicafinalprimertrimestres.db.tables.ComentariosTable;
import com.joseduarte.practicafinalprimertrimestres.db.tables.ExponenTable;
import com.joseduarte.practicafinalprimertrimestres.db.tables.ExposicionesTable;
import com.joseduarte.practicafinalprimertrimestres.list.ExpandableListDataBuilder;
import com.joseduarte.practicafinalprimertrimestres.list.MyExpandableListAdapter;
import com.joseduarte.practicafinalprimertrimestres.list.MyListAdapter;
import com.joseduarte.practicafinalprimertrimestres.models.Artistas;
import com.joseduarte.practicafinalprimertrimestres.models.Comentarios;
import com.joseduarte.practicafinalprimertrimestres.models.Exponen;
import com.joseduarte.practicafinalprimertrimestres.models.Exposiciones;
import com.joseduarte.practicafinalprimertrimestres.models.Models;
import com.joseduarte.practicafinalprimertrimestres.models.Trabajos;
import com.joseduarte.practicafinalprimertrimestres.list.ModelsSpinnerAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ExposicionesApplier implements Applier {
    @Override
    public void applyTo(final View view, final Models models) {
        Exposiciones exp = (Exposiciones)models;
        ((TextView)view.findViewById(R.id.id_exhibition_tv)).setText("ID : " + ((Exposiciones)exp).getIdExposiciones());
        ((TextView)view.findViewById(R.id.name_exhibition_tv)).setText(exp.getNombreExp());
        ((TextView)view.findViewById(R.id.desciption_exhibition_tv)).setText(exp.getDescripcion());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        ((TextView)view.findViewById(R.id.fech_ini_exhibitions_tv)).setText(
                view.getContext().getString(R.string.field_exhibition_fech_ini)
                        + " : " + formatter.format(exp.getFechaInicio()));
        ((TextView)view.findViewById(R.id.fech_fin_exhibitions_tv)).setText(
                view.getContext().getString(R.string.field_exhibition_fech_fin)
                        + " : " + formatter.format(exp.getFechaFin()));


        final RecyclerView commentView = view.findViewById(R.id.comments_list);

        DBController db = SourcesManager.getDBController();
        //SELECT * FROM EXPONEN WHERE IDEXPOSICION=models.id
        String exponenSql = ExponenTable.makeQuery("WHERE " + ExposicionesTable.makeWhereSentence(ExponenTable.ID_EXPOSICION_FIELD));
        List<Models> listExponen = db.query(Exponen.class, exponenSql, models.getPrimaryKeys());

        List<Artistas> listArtistas = new ArrayList<>();
        for (Models model : listExponen) {
            listArtistas.add(((Exponen)model).getArtista());
        }

        List<String> titles = new ArrayList<>();
        titles.add(view.getContext().getString(R.string.artist_tittle));

        HashMap<String, List<? extends Models>> map = ExpandableListDataBuilder.getData(
                view.getContext().getString(R.string.artist_tittle),
                listArtistas
        );

        final MyExpandableListAdapter expandableListAdapter =
                new MyExpandableListAdapter(view.getContext(), titles, map);

        ExpandableListView expandableListView = (ExpandableListView) view.findViewById(R.id.expandable_list_view);

        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {

            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {

            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Artistas artistas = (Artistas)expandableListAdapter.getChild(groupPosition, childPosition);

                return false;
            }
        });

        //SELECT * FROM COMENTARIOS WHERE IDEXPOSICION=models.id
        String comentatiosSql = ComentariosTable.makeQuery(
                "WHERE " + ExposicionesTable
                        .makeWhereSentence(ComentariosTable.ID_EXPOSICION_FIELD));
        final List<Models> listComments = db.query(
                Comentarios.class,
                comentatiosSql,
                models.getPrimaryKeys()
        );

        final MyListAdapter adapter = new MyListAdapter(listComments) {
            @Override
            public boolean onTouchListener(View view, MotionEvent event, int position) {
                return true;
            }
        };

        commentView.setAdapter(adapter);

        Button newCommentaryBtn = view.findViewById(R.id.new_commentary_btn);
        newCommentaryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View dialogView = View.inflate(view.getContext(), R.layout.model_creation_commentary_dialog, null);

                DBController db = new DBController(dialogView.getContext());
                List<Models> exposiciones = db.getListModels(Exposiciones.class);

                final Spinner expositionSpinner = (Spinner)dialogView.findViewById(R.id.model_exposition_spinner);
                List<Exposiciones> expositionList = new ArrayList<>();
                expositionList.add((Exposiciones)models);

                SpinnerAdapter expAdapter = new ArrayAdapter<Exposiciones>(
                        dialogView.getContext(),
                        android.R.layout.simple_spinner_item,
                        expositionList);

                expositionSpinner.setAdapter(expAdapter);
                expositionSpinner.setEnabled(false);

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

                                Exposiciones exp = null;

                                Trabajos trb = null;
                                if(workSpinner.getSelectedItemPosition() != 0) {
                                    trb = (Trabajos) workSpinner.getSelectedItem();
                                } else {
                                    workSpinner.setBackgroundColor(0xFFC2C2);
                                    errorMessage += context.getString(R.string.spinner_select_work) + "\n";
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

                                        Comentarios comentario = new Comentarios((Exposiciones) models, trb, commentaryText);

                                        DBController db = new DBController(view.getContext());
                                        if(db.add(Comentarios.class, comentario)) {
                                            listComments.add(comentario);
                                            adapter.update(listComments);
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
        });

    }


}
