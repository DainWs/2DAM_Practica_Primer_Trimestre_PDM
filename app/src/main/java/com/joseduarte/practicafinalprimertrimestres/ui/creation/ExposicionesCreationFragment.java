package com.joseduarte.practicafinalprimertrimestres.ui.creation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.joseduarte.practicafinalprimertrimestres.R;
import com.joseduarte.practicafinalprimertrimestres.SourcesManager;
import com.joseduarte.practicafinalprimertrimestres.db.DBController;
import com.joseduarte.practicafinalprimertrimestres.db.tables.ExponenTable;
import com.joseduarte.practicafinalprimertrimestres.db.tables.ExposicionesTable;
import com.joseduarte.practicafinalprimertrimestres.list.ExpandableListDataBuilder;
import com.joseduarte.practicafinalprimertrimestres.list.ModelsSpinnerAdapter;
import com.joseduarte.practicafinalprimertrimestres.list.MyExpandableListAdapter;
import com.joseduarte.practicafinalprimertrimestres.models.Artistas;
import com.joseduarte.practicafinalprimertrimestres.models.Exponen;
import com.joseduarte.practicafinalprimertrimestres.models.Exposiciones;
import com.joseduarte.practicafinalprimertrimestres.models.Models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ExposicionesCreationFragment extends Fragment {

    private View root;

    private boolean adding = true;
    private Exposiciones exposicion;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if(getArguments() != null) {
            adding = getArguments().getBoolean("addMode");
            if(!adding){
                exposicion = (Exposiciones)getArguments().getSerializable("model");
            }
        }
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.model_creation_exhibitions, container, false);

        final String title = getContext().getString(R.string.artist_tittle);
        final List<String> listTitles = new ArrayList<>();
        listTitles.add(title);

        Button cancelButton = root.findViewById(R.id.cancel_btn);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        Button add_artist = root.findViewById(R.id.add_artist_btn);
        Button confirmButton = root.findViewById(R.id.confirm_btn);
        if(adding) {


            final List<Artistas> models = new ArrayList<>();

            HashMap<String, List<? extends Models>> map =
                    ExpandableListDataBuilder.getData(title, models);
            MyExpandableListAdapter expandableAdapter =
                    new MyExpandableListAdapter(getContext(), listTitles, map);

            final ExpandableListView expandableListView =
                    root.findViewById(R.id.expandable_list_view);
            expandableListView.setAdapter(expandableAdapter);

            add_artist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final View view = inflater.inflate(R.layout.model_creation_exhibit_dialog, null, false);

                    final Spinner artList = (Spinner)view.findViewById(R.id.model_artist_spinner);

                    DBController db = new DBController(view.getContext());
                    final List<Models> artistList = db.getListModels(Artistas.class);

                    SpinnerAdapter artAdapter = new ArrayAdapter<Models>(
                            view.getContext(),
                            android.R.layout.simple_spinner_item,
                            artistList);

                    ModelsSpinnerAdapter modelsSpinnerAdapter = new ModelsSpinnerAdapter(
                            artAdapter,
                            getContext(),
                            getContext().getString(R.string.spinner_select_artist)
                    );

                    artList.setAdapter(modelsSpinnerAdapter);

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                            .setTitle(getContext().getString(R.string.add_artist_to_exhibition))
                            .setView(view)
                            .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String errorMessage = "";
                                    int errorCount = 0;

                                    Artistas art = null;
                                    if(artList.getSelectedItemPosition() != 0) {
                                        art = (Artistas) artList.getSelectedItem();
                                    } else {
                                        artList.setBackgroundColor(0xff8080);
                                        errorMessage += getContext().getString(R.string.spinner_select_artist) + "\n";
                                        errorCount++;
                                    }

                                    if (errorCount > 0) {
                                        Toast toast = Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG);
                                        toast.show();
                                    } else {
                                        if(!models.contains(art)) {
                                            models.add(art);

                                            HashMap<String, List<? extends Models>> map =
                                                    ExpandableListDataBuilder.getData(title, models);
                                            MyExpandableListAdapter expandableAdapter =
                                                    new MyExpandableListAdapter(getContext(), listTitles, map);

                                            expandableListView.setAdapter(expandableAdapter);
                                            dialog.dismiss();
                                        }else{
                                            Toast toast = Toast.makeText(getContext(), getContext().getString(R.string.list_has_artist), Toast.LENGTH_LONG);
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
            });

            confirmButton.setOnClickListener(
                    new View.OnClickListener() {
                         @Override
                         public void onClick(View view) {
                             EditText idExposicionET = (EditText) root.findViewById(R.id.model_exposition_id);
                             EditText nombreExpET = (EditText) root.findViewById(R.id.model_exposition_name);
                             EditText descrET = (EditText) root.findViewById(R.id.model_description);
                             EditText fechaIniET = (EditText) root.findViewById(R.id.model_fech_ini);
                             EditText fechaFinET = (EditText) root.findViewById(R.id.model_fech_fin);

                             int exposicionId = -1;
                             String exposicionName = "";
                             String exposicionDesc = "";
                             Date exposicionFechIni = null;
                             Date exposicionFechFin = null;

                             String errorMessage = "";
                             int errorCount = 0;

                             if (!idExposicionET.getText().toString().isEmpty()) {
                                 try {
                                     exposicionId = Integer.parseInt(idExposicionET.getText().toString());
                                 } catch (Exception ex) {
                                     idExposicionET.setTextColor(0xff8080);
                                     idExposicionET.setBackgroundColor(0xff8080);
                                     errorMessage += R.string.invalid_number_message;
                                     errorCount++;
                                 }
                             } else {
                                 idExposicionET.setTextColor(0xff8080);
                                 idExposicionET.setBackgroundColor(0xff8080);
                                 errorMessage += R.string.field_error_message_edittext + R.string.field_exhibition_id + "\n";
                                 errorCount++;
                             }

                             if (!nombreExpET.getText().toString().isEmpty()) {
                                 exposicionName = nombreExpET.getText().toString();
                             } else {
                                 nombreExpET.setTextColor(0xff8080);
                                 nombreExpET.setBackgroundColor(0xff8080);
                                 errorMessage += R.string.field_error_message_edittext + R.string.field_exhibition_name + "\n";
                                 errorCount++;
                             }

                             if (!descrET.getText().toString().isEmpty()) {
                                 exposicionDesc = descrET.getText().toString();
                             } else {
                                 descrET.setTextColor(0xff8080);
                                 descrET.setBackgroundColor(0xff8080);
                                 errorMessage += R.string.field_error_message_edittext + R.string.field_exhibition_description + "\n";
                                 errorCount++;
                             }

                             if (!fechaIniET.getText().toString().isEmpty()) {
                                 try {
                                     String date = fechaIniET.getText().toString();
                                     exposicionFechIni = new SimpleDateFormat("dd/MM/yyyy")
                                             .parse(date.replaceAll("-", "/"));
                                 } catch (Exception ex) {
                                     fechaIniET.setTextColor(0xff8080);
                                     fechaFinET.setBackgroundColor(0xff8080);
                                     errorMessage += R.string.invalid_date_message;
                                     errorCount++;
                                 }
                             } else {
                                 fechaIniET.setTextColor(0xff8080);
                                 fechaIniET.setBackgroundColor(0xff8080);
                                 errorMessage += R.string.field_error_message_edittext + R.string.field_exhibition_fech_ini + "\n";
                                 errorCount++;
                             }

                             if (!fechaFinET.getText().toString().isEmpty()) {
                                 try {
                                     String date = fechaFinET.getText().toString();
                                     exposicionFechFin = new SimpleDateFormat("dd/MM/yyyy")
                                             .parse(date.replaceAll("-", "/"));
                                 } catch (Exception ex) {
                                     fechaFinET.setTextColor(0xff8080);
                                     fechaFinET.setBackgroundColor(0xff8080);
                                     errorMessage += R.string.invalid_date_message;
                                     errorCount++;
                                 }
                             } else {
                                 fechaFinET.setTextColor(0xff8080);
                                 fechaFinET.setBackgroundColor(0xff8080);
                                 errorMessage += R.string.field_error_message_edittext + R.string.field_exhibition_fech_fin;
                                 errorCount++;
                             }

                             if (errorCount > 0) {
                                 Toast toast = Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG);
                                 toast.show();
                             } else {
                                 try {
                                     Exposiciones exposicion = new Exposiciones(exposicionId, exposicionName, exposicionDesc, exposicionFechIni, exposicionFechFin);
                                     System.out.println(exposicion.getIdExposiciones());

                                     DBController db = new DBController(getContext());
                                     if (db.add(Exposiciones.class, exposicion)) {

                                         for (Artistas artista : models) {
                                             Exponen exponen = new Exponen(exposicion, artista);
                                             db.add(Exponen.class, exponen);
                                         }

                                         getActivity().onBackPressed();
                                     } else {
                                         throw new Exception();
                                     }


                                 } catch (Exception ex) {
                                     ex.printStackTrace();
                                     Toast toast = Toast.makeText(getContext(), R.string.on_db_operations_error, Toast.LENGTH_LONG);
                                     toast.show();
                                 }
                             }
                         }
                    }
            );
        }else{

            DBController db = SourcesManager.getDBController();
            //SELECT * FROM EXPONEN WHERE IDEXPOSICION=models.id
            String exponenSql = ExponenTable.makeQuery("WHERE " + ExposicionesTable.makeWhereSentence(ExponenTable.ID_EXPOSICION_FIELD));
            final List<Models> listExponen = db.query(Exponen.class, exponenSql, exposicion.getPrimaryKeys());

            HashMap<String, List<? extends Models>> map =
                    ExpandableListDataBuilder.getData(title, listExponen);
            MyExpandableListAdapter expandableAdapter =
                    new MyExpandableListAdapter(getContext(), listTitles, map);

            final ExpandableListView expandableListView =
                    root.findViewById(R.id.expandable_list_view);
            expandableListView.setAdapter(expandableAdapter);

            final EditText idExposicionET = (EditText)root.findViewById(R.id.model_exposition_id);
            final EditText nombreExpET = (EditText)root.findViewById(R.id.model_exposition_name);
            final EditText descrET = (EditText)root.findViewById(R.id.model_description);
            final EditText fechaIniET = (EditText)root.findViewById(R.id.model_fech_ini);
            final EditText fechaFinET = (EditText)root.findViewById(R.id.model_fech_fin);

            idExposicionET.setEnabled(false);
            idExposicionET.setText(exposicion.getIdExposiciones());
            nombreExpET.setText(exposicion.getNombreExp());
            descrET.setText(exposicion.getDescripcion());
            fechaIniET.setText(
                    new SimpleDateFormat("dd/MM/yyyy").format(exposicion.getFechaInicio())
            );
            fechaFinET.setText(
                    new SimpleDateFormat("dd/MM/yyyy").format(exposicion.getFechaFin())
            );

            confirmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int exposicionId = exposicion.getIdExposiciones();
                    String exposicionName =exposicion.getNombreExp();
                    String exposicionDesc = exposicion.getDescripcion();
                    Date exposicionFechIni = exposicion.getFechaInicio();
                    Date exposicionFechFin = exposicion.getFechaFin();

                    String errorMessage = "";
                    int errorCount = 0;

                    if(!nombreExpET.getText().toString().isEmpty()){
                        exposicionName = nombreExpET.getText().toString();
                    }

                    if(!descrET.getText().toString().isEmpty()){
                        exposicionDesc = descrET.getText().toString();
                    }

                    if(!fechaIniET.getText().toString().isEmpty()){
                        try {
                            String date = fechaIniET.getText().toString();
                            exposicionFechIni = new SimpleDateFormat("dd/MM/yyyy")
                                    .parse(date.replaceAll("-","/"));
                        }catch (Exception ex) {
                            fechaIniET.setTextColor(0xff8080);
                            fechaFinET.setBackgroundColor(0xff8080);
                            errorMessage += R.string.invalid_date_message;
                            errorCount++;
                        }
                    }

                    if(!fechaFinET.getText().toString().isEmpty()){
                        try {
                            String date = fechaFinET.getText().toString();
                            exposicionFechFin = new SimpleDateFormat("dd/MM/yyyy")
                                    .parse(date.replaceAll("-","/"));
                        }catch (Exception ex) {
                            fechaFinET.setTextColor(0xff8080);
                            fechaFinET.setBackgroundColor(0xff8080);
                            errorMessage += R.string.invalid_date_message;
                            errorCount++;
                        }
                    }

                    if (errorCount <= 0) {
                        try{

                            Exposiciones exposicion = new Exposiciones(exposicionId, exposicionName, exposicionDesc, exposicionFechIni, exposicionFechFin);

                            DBController db = new DBController(getContext());
                            if(db.update(Exposiciones.class, exposicion)) {
                                getActivity().onBackPressed();
                            }else {
                                throw new Exception();
                            }


                        }catch (Exception ex){
                            Toast toast = Toast.makeText(getContext(), R.string.on_db_operations_error, Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }
                }
            });
        }



        return root;
    }
}
