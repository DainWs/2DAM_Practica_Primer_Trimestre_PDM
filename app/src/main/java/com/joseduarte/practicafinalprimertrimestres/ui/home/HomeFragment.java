package com.joseduarte.practicafinalprimertrimestres.ui.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.joseduarte.practicafinalprimertrimestres.R;
import com.joseduarte.practicafinalprimertrimestres.db.DBController;
import com.joseduarte.practicafinalprimertrimestres.list.ModelsSpinnerAdapter;
import com.joseduarte.practicafinalprimertrimestres.models.Comentarios;
import com.joseduarte.practicafinalprimertrimestres.models.Exposiciones;
import com.joseduarte.practicafinalprimertrimestres.models.Models;
import com.joseduarte.practicafinalprimertrimestres.models.Trabajos;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment {

    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home, container, false);

        TransitionDrawable transition = (TransitionDrawable) root.findViewById(R.id.home_tittle).getBackground();
        transition.startTransition(6000);

        AppCompatImageView exbutton = root.findViewById(R.id.exhibition_list_btn);
        AppCompatImageView arbutton = root.findViewById(R.id.artist_list_btn);
        AppCompatImageView cobutton = root.findViewById(R.id.comment_list_btn);
        AppCompatImageView wobutton = root.findViewById(R.id.works_list_btn);

        exbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateTo(R.id.action_home_to_exhibitions_list);
            }
        });

        arbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateTo(R.id.action_home_to_artists_list);
            }
        });

        cobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateTo(R.id.action_home_to_comments_list);
            }
        });

        wobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateTo(R.id.action_home_to_works_list);
            }
        });

        AppCompatButton newExButton = root.findViewById(R.id.new_exhibition_btn);
        AppCompatButton newArButton = root.findViewById(R.id.new_artist_btn);
        AppCompatButton newCoButton = root.findViewById(R.id.new_commentary_btn);
        AppCompatButton newWoButton = root.findViewById(R.id.new_work_btn);

        newExButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putBoolean("addMode", true);
                navigateTo(R.id.action_home_new_exhibition, b);
            }
        });

        newArButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putBoolean("addMode", true);
                navigateTo(R.id.action_home_new_artist, b);
            }
        });

        newCoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View dialogView = View.inflate(getContext(), R.layout.model_creation_commentary_dialog, null);

                DBController db = new DBController(dialogView.getContext());
                List<Models> exposiciones = db.getListModels(Exposiciones.class);

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

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
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

        newWoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putBoolean("addMode", true);
                navigateTo(R.id.action_home_new_work, b);
            }
        });

        return root;
    }

    public void navigateTo(int navResource) {
        Navigation.findNavController(root).navigate(navResource);
    }

    public void navigateTo(int navResource, Bundle b) {
        Navigation.findNavController(root).navigate(navResource, b);
    }

}