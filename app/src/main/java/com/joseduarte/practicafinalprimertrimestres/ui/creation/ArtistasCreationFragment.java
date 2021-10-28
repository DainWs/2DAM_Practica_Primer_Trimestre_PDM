package com.joseduarte.practicafinalprimertrimestres.ui.creation;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.joseduarte.practicafinalprimertrimestres.R;
import com.joseduarte.practicafinalprimertrimestres.db.DBController;
import com.joseduarte.practicafinalprimertrimestres.models.Artistas;
import com.joseduarte.practicafinalprimertrimestres.models.Comentarios;
import com.joseduarte.practicafinalprimertrimestres.models.Exposiciones;
import com.joseduarte.practicafinalprimertrimestres.models.Trabajos;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ArtistasCreationFragment extends Fragment {

    private View root;
    private Spinner artList;
    private ImageView imageV;

    private boolean adding = true;
    private Artistas models;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if(getArguments() != null) {
            adding = getArguments().getBoolean("addMode");
            if(!adding){
                models = (Artistas)getArguments().getSerializable("model");
            }
        }
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.model_creation_artist, container, false);

        Button confirmBtn = root.findViewById(R.id.confirm_btn);
        Button cancelBtn = root.findViewById(R.id.cancel_btn);

        if(!adding){
            root.findViewById(R.id.dni_artist_tv).setEnabled(false);
        }

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickConfirm(v);
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickCancel(v);
            }
        });

        return root;
    }


    public void onClickConfirm(View view) {
        if(adding) {
            Context context = root.getContext();
            String errorMessage = "";
            int errorCount = 0;

            EditText dniET = root.findViewById(R.id.model_artist_dni);
            EditText nameET = root.findViewById(R.id.model_artist_name);
            EditText addressET = root.findViewById(R.id.model_address);
            EditText townET = root.findViewById(R.id.model_town);
            EditText provinceET = root.findViewById(R.id.model_province);
            EditText countryET = root.findViewById(R.id.model_country);
            EditText workPhET = root.findViewById(R.id.model_work_phone);
            EditText persoPhET = root.findViewById(R.id.model_personal_phone);
            EditText landlPhET = root.findViewById(R.id.model_landline_phone);
            EditText webblogET = root.findViewById(R.id.model_webblog);
            EditText emailET = root.findViewById(R.id.model_email);
            EditText nacET = root.findViewById(R.id.model_fech_nac);

            Date nacimiento = null;
            if (!nacET.getText().toString().isEmpty()) {
                try {
                    String date = nacET.getText().toString();
                    nacimiento = new SimpleDateFormat("dd/MM/yyyy")
                            .parse(date.replaceAll("-", "/"));
                } catch (Exception ex) {
                    nacET.setTextColor(0xff8080);
                    nacET.setBackgroundColor(0xff8080);
                    errorMessage += R.string.invalid_date_message;
                    errorCount++;
                }
            } else {
                nacET.setTextColor(0xff8080);
                nacET.setBackgroundColor(0xff8080);
                errorMessage += context.getString(R.string.field_error_message_edittext) + context.getString(R.string.field_artist_fech_naci);
                errorCount++;
            }

            int telefonoTrab = 0;
            if (!workPhET.getText().toString().isEmpty()) {
                try {
                    telefonoTrab = Integer.parseInt(
                            workPhET.getText().toString().replaceAll(" ", "").replaceAll("\\+", "")
                    );
                } catch (Exception ex) {
                    workPhET.setTextColor(0xff8080);
                    workPhET.setBackgroundColor(0xff8080);
                    errorMessage += R.string.invalid_number_message;
                    errorCount++;
                }
            } else {
                workPhET.setTextColor(0xff8080);
                workPhET.setBackgroundColor(0xff8080);
                errorMessage += context.getString(R.string.field_error_message_edittext) + context.getString(R.string.field_artist_tlf_job);
                errorCount++;
            }

            if (!emailET.getText().toString().isEmpty()) {
            } else {
                emailET.setTextColor(0xff8080);
                emailET.setBackgroundColor(0xff8080);
                errorMessage += context.getString(R.string.field_error_message_edittext) + context.getString(R.string.field_artist_email);
                errorCount++;
            }

            if (!dniET.getText().toString().isEmpty()) {
            } else {
                dniET.setTextColor(0xff8080);
                dniET.setBackgroundColor(0xff8080);
                errorMessage += context.getString(R.string.field_error_message_edittext) + context.getString(R.string.field_artist_email);
                errorCount++;
            }

            if (!nameET.getText().toString().isEmpty()) {
            } else {
                nameET.setTextColor(0xFFC2C2);
                nameET.setBackgroundColor(0xFFC2C2);
                errorMessage += context.getString(R.string.field_error_message_edittext) + context.getString(R.string.field_artist_email);
                errorCount++;
            }

            int telefonoPersonal = 0;
            try {
                telefonoPersonal = Integer.parseInt(
                        persoPhET.getText().toString().replaceAll(" ", "").replaceAll("\\+", "")
                );
            } catch (Exception ex) {
            }

            int telefonoFijo = 0;
            try {
                telefonoFijo = Integer.parseInt(
                        landlPhET.getText().toString().replaceAll(" ", "").replaceAll("\\+", "")
                );
            } catch (Exception ex) {
            }

            if (errorCount > 0) {
                Toast toast = Toast.makeText(view.getContext(), errorMessage, Toast.LENGTH_LONG);
                toast.show();
            } else {
                try {

                    Artistas artistas = new Artistas.ArtistasBuilder(dniET.getText().toString())
                            .setNombre(nameET.getText().toString())
                            .setDireccion(addressET.getText().toString())
                            .setPoblacion(townET.getText().toString())
                            .setProvincia(provinceET.getText().toString())
                            .setPais(countryET.getText().toString())
                            .setMovilTrabajo(telefonoTrab)
                            .setMovilPersonal(telefonoPersonal)
                            .setTelefonoFijo(telefonoFijo)
                            .setEmail(emailET.getText().toString())
                            .setWebBlog(webblogET.getText().toString())
                            .setFechaNacimiento(nacimiento)
                            .build();

                    DBController db = new DBController(view.getContext());
                        if (db.add(Artistas.class, artistas)) {
                            getActivity().onBackPressed();
                        } else {
                            throw new Exception();
                        }


                } catch (Exception ex) {
                    Toast toast = Toast.makeText(view.getContext(), R.string.on_db_operations_error, Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        }
        else {
            Context context = root.getContext();
            String errorMessage = "";
            int errorCount = 0;

            EditText dniET = root.findViewById(R.id.model_artist_dni);
            EditText nameET = root.findViewById(R.id.model_artist_name);
            EditText addressET = root.findViewById(R.id.model_address);
            EditText townET = root.findViewById(R.id.model_town);
            EditText provinceET = root.findViewById(R.id.model_province);
            EditText countryET = root.findViewById(R.id.model_country);
            EditText workPhET = root.findViewById(R.id.model_work_phone);
            EditText persoPhET = root.findViewById(R.id.model_personal_phone);
            EditText landlPhET = root.findViewById(R.id.model_landline_phone);
            EditText webblogET = root.findViewById(R.id.model_webblog);
            EditText emailET = root.findViewById(R.id.model_email);
            EditText nacET = root.findViewById(R.id.model_fech_nac);

            Artistas.ArtistasBuilder builder = new Artistas.ArtistasBuilder(models.getDniPasaporte());

            Date nacimiento = models.getFechaNacimiento();
            if (!nacET.getText().toString().isEmpty()) {
                try {
                    String date = nacET.getText().toString();
                    nacimiento = new SimpleDateFormat("dd/MM/yyyy")
                            .parse(date.replaceAll("-", "/"));
                } catch (Exception ex) {
                    nacET.setTextColor(0xff8080);
                    nacET.setBackgroundColor(0xff8080);
                    errorMessage += R.string.invalid_date_message;
                    errorCount++;
                }
            }

            int telefonoTrab = models.getMovilTrabajo();
            if (!workPhET.getText().toString().isEmpty()) {
                try {
                    telefonoTrab = Integer.parseInt(
                            workPhET.getText().toString().replaceAll(" ", "").replaceAll("\\+", "")
                    );
                } catch (Exception ex) {
                    workPhET.setTextColor(0xff8080);
                    workPhET.setBackgroundColor(0xff8080);
                    errorMessage += R.string.invalid_number_message;
                    errorCount++;
                }
            }

            if (!emailET.getText().toString().isEmpty()) {
            } else {
                builder.setEmail(models.getEmail());
            }

            if (!nameET.getText().toString().isEmpty()) {
            } else {
                builder.setNombre(models.getNombre());
            }

            int telefonoPersonal = models.getMovilPersonal();
            try {
                telefonoPersonal = Integer.parseInt(
                        persoPhET.getText().toString().replaceAll(" ", "").replaceAll("\\+", "")
                );
            } catch (Exception ex) {
            }

            int telefonoFijo = models.getTelefonoFijo();
            try {
                telefonoFijo = Integer.parseInt(
                        landlPhET.getText().toString().replaceAll(" ", "").replaceAll("\\+", "")
                );
            } catch (Exception ex) {
            }

            if (errorCount > 0) {
                Toast toast = Toast.makeText(view.getContext(), errorMessage, Toast.LENGTH_LONG);
                toast.show();
            } else {
                try {

                    Artistas artistas = builder
                            .setFechaNacimiento(nacimiento)
                            .setMovilTrabajo(telefonoTrab)
                            .setMovilPersonal(telefonoPersonal)
                            .setTelefonoFijo(telefonoFijo)
                            .build();

                    DBController db = new DBController(view.getContext());
                    if (db.update(Artistas.class, artistas)) {
                        getActivity().onBackPressed();
                    } else {
                        throw new Exception();
                    }

                } catch (Exception ex) {
                    Toast toast = Toast.makeText(view.getContext(), R.string.on_db_operations_error, Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        }
    }

    public void onClickCancel(View view) {
        getActivity().onBackPressed();
    }
}
