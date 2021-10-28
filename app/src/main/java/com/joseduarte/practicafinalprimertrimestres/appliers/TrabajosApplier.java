package com.joseduarte.practicafinalprimertrimestres.appliers;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.joseduarte.practicafinalprimertrimestres.R;
import com.joseduarte.practicafinalprimertrimestres.db.DBController;
import com.joseduarte.practicafinalprimertrimestres.db.tables.ComentariosTable;
import com.joseduarte.practicafinalprimertrimestres.db.tables.ExposicionesTable;
import com.joseduarte.practicafinalprimertrimestres.list.ModelsSpinnerAdapter;
import com.joseduarte.practicafinalprimertrimestres.list.MyListAdapter;
import com.joseduarte.practicafinalprimertrimestres.models.Comentarios;
import com.joseduarte.practicafinalprimertrimestres.models.Exposiciones;
import com.joseduarte.practicafinalprimertrimestres.models.Models;
import com.joseduarte.practicafinalprimertrimestres.models.Trabajos;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class TrabajosApplier implements Applier {
    @Override
    public void applyTo(final View view, final Models models) {
        Trabajos trabajo = (Trabajos)models;

        TextView nameTV = view.findViewById(R.id.work_name_tv);
        TextView descTV = view.findViewById(R.id.work_description_tv);
        TextView sizeTV = view.findViewById(R.id.work_size_tv);
        TextView weightTV = view.findViewById(R.id.work_weight_tv);
        TextView arNameTV = view.findViewById(R.id.work_artist_name);
        TextView arDNITV = view.findViewById(R.id.work_artist_dni);

        ImageView photoIV = view.findViewById(R.id.work_image);

        nameTV.setText(trabajo.getNombreTrab());
        descTV.setText(trabajo.getDescripcion());
        sizeTV.setText(trabajo.getTamanio());
        weightTV.setText(trabajo.getPeso()+"");
        arNameTV.setText(trabajo.getArtista().getNombre());
        arDNITV.setText(trabajo.getArtista().getDniPasaporte());

        if (ContextCompat.checkSelfPermission(view.getContext(), Manifest.permission.READ_EXTERNAL_STORAGE )
                == PackageManager.PERMISSION_GRANTED) {
            photoIV.setImageURI(trabajo.getImageUri());
        }

        final DBController db = new DBController(view.getContext());
        final RecyclerView commentView = view.findViewById(R.id.comments_list);

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
                final Spinner expositionSpinner = (Spinner)dialogView.findViewById(R.id.model_exposition_spinner);
                List<Models> exposicionesList = db.getListModels(Exposiciones.class);

                SpinnerAdapter expoAdapter = new ArrayAdapter<Models>(
                        dialogView.getContext(),
                        android.R.layout.simple_spinner_item,
                        exposicionesList);

                expositionSpinner.setAdapter(new ModelsSpinnerAdapter(
                        expoAdapter,
                        dialogView.getContext(),
                        dialogView.getContext()
                                .getString(R.string.spinner_select_exhibition)
                ));

                final Spinner workSpinner = (Spinner)dialogView.findViewById(R.id.model_work_spinner);
                List<Trabajos> workList = new ArrayList<>();
                workList.add((Trabajos)models);

                SpinnerAdapter workAdapter = new ArrayAdapter<Trabajos>(
                        dialogView.getContext(),
                        android.R.layout.simple_spinner_item,
                        workList);

                workSpinner.setAdapter(workAdapter);
                workSpinner.setEnabled(false);


                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext())
                        .setTitle(R.string.work_title)
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
                                } else {
                                    expositionSpinner.setBackgroundColor(0xff8080);
                                    errorMessage += context.getString(R.string.spinner_select_exhibition) + "\n";
                                    errorCount++;
                                }

                                String commentaryText = "";
                                if(!commentary.getText().toString().isEmpty()){
                                    commentaryText = commentary.getText().toString();
                                } else {
                                    commentary.setTextColor(0xff8080);
                                    commentary.setBackgroundColor(0xff8080);
                                    errorMessage += context.getString(R.string.field_error_message_edittext) + context.getString(R.string.field_comments_commentary);
                                    errorCount++;
                                }

                                if (errorCount > 0) {
                                    Toast toast = Toast.makeText(view.getContext(), errorMessage, Toast.LENGTH_LONG);
                                    toast.show();
                                } else {
                                    try{

                                        Comentarios comentario = new Comentarios(exp, (Trabajos) models, commentaryText);

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
