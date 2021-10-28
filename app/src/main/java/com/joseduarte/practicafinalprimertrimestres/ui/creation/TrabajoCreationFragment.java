package com.joseduarte.practicafinalprimertrimestres.ui.creation;

import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.joseduarte.practicafinalprimertrimestres.R;
import com.joseduarte.practicafinalprimertrimestres.SourcesManager;
import com.joseduarte.practicafinalprimertrimestres.db.DBController;
import com.joseduarte.practicafinalprimertrimestres.list.ModelsSpinnerAdapter;
import com.joseduarte.practicafinalprimertrimestres.models.Artistas;
import com.joseduarte.practicafinalprimertrimestres.models.Models;
import com.joseduarte.practicafinalprimertrimestres.models.Trabajos;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TrabajoCreationFragment extends Fragment {

    public static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int REQUEST_IMAGE_PICK = 3;
    public static final int REQUEST_PERMISSION = 2;



    private View root;
    private Spinner artList;
    private ImageView imageV;
    private Bitmap imageBitmap;

    private boolean adding = true;
    private Trabajos models;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if(getArguments() != null) {
            adding = getArguments().getBoolean("addMode");
            if(!adding){
                models = (Trabajos)getArguments().getSerializable("model");
            }
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == TrabajoCreationFragment.REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK) {
            Bundle extras = data.getExtras();
            System.out.println(extras.get("data"));
            imageBitmap = (Bitmap) extras.get("data");
            imageV.setImageBitmap(imageBitmap);
        } else if (requestCode == TrabajoCreationFragment.REQUEST_IMAGE_PICK && resultCode == getActivity().RESULT_OK) {
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                imageV.setImageBitmap(imageBitmap);
            } catch (IOException e) { }
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.model_creation_works, container, false);

        artList = (Spinner)root.findViewById(R.id.model_artist_spinner);

        DBController db = new DBController(root.getContext());
        List<Models> artistList = db.getListModels(Artistas.class);

        SpinnerAdapter artAdapter = new ArrayAdapter<Models>(
                root.getContext(),
                android.R.layout.simple_spinner_item,
                artistList);

        ModelsSpinnerAdapter modelsSpinnerAdapter = new ModelsSpinnerAdapter(
                artAdapter,
                getContext(),
                getContext().getString(R.string.spinner_select_artist)
        );

        if(!adding){
            root.findViewById(R.id.model_work_name).setEnabled(false);
        }

        artList.setAdapter(modelsSpinnerAdapter);

        imageV = (ImageView)root.findViewById(R.id.model_work_photo);

        FloatingActionButton searchPhotoBtn = root.findViewById(R.id.search_in_gallery_btn);
        FloatingActionButton newPhotoBtn = root.findViewById(R.id.new_photo_btn);

        Button confirmBtn = root.findViewById(R.id.confirm_btn);
        Button cancelBtn = root.findViewById(R.id.cancel_btn);

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

        searchPhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //COMPROBANDO QUE LA APP TENGA PERMISOS PARA ACCEDER A LA GALERÍA. SI NO SE LOS PEDIMOS
                //AL USUARIO
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE )
                        == PackageManager.PERMISSION_DENIED) {

                    ActivityCompat.requestPermissions(
                            getActivity(),
                            new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_PERMISSION
                    );

                } else {
                    searchPhoto();
                }
            }
        });

        newPhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //COMPROBANDO QUE LA APP TENGA PERMISOS PARA ACCEDER A LA CAMARA. SI NO SE LOS PEDIMOS
                //AL USUARIO
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA )
                        == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(
                            getActivity(),
                            new String[] {Manifest.permission.CAMERA},
                            REQUEST_PERMISSION
                    );

                } else {
                    makePhoto();
                }
            }
        });

        return root;
    }

    public void makePhoto() {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePicture.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePicture, REQUEST_IMAGE_CAPTURE);
        }
    }

    public void searchPhoto() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if(pickPhoto.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(pickPhoto, REQUEST_IMAGE_PICK);
        }
    }

    public void onClickConfirm (View view) {
        if(adding) {

            boolean hasPermissions = false;

            EditText nameET = (EditText) root.findViewById(R.id.model_work_name);
            EditText descET = (EditText) root.findViewById(R.id.model_description);
            EditText sizeET = (EditText) root.findViewById(R.id.model_work_size);
            EditText weightET = (EditText) root.findViewById(R.id.model_work_weight);

            String name = "";
            String desc = "";
            String size = "";
            float weight = 0f;

            String errorMessage = "";
            int errorCount = 0;

            if (!nameET.getText().toString().isEmpty()) {
                name = nameET.getText().toString();
            } else {
                nameET.setTextColor(0xFFC2C2);
                nameET.setBackgroundColor(0xFFC2C2);
                errorMessage += getContext().getString(R.string.field_error_message_edittext) + getContext().getString(R.string.field_work_name) + "\n";
                errorCount++;
            }

            if (!descET.getText().toString().isEmpty()) {
                desc = descET.getText().toString();
            } else {
                descET.setTextColor(0xFFC2C2);
                descET.setBackgroundColor(0xFFC2C2);
                errorMessage += getContext().getString(R.string.field_error_message_edittext) + getContext().getString(R.string.field_work_description) + "\n";
                errorCount++;
            }

            if (!sizeET.getText().toString().isEmpty()) {
                size = sizeET.getText().toString();
            } else {
                sizeET.setTextColor(0xFFC2C2);
                sizeET.setBackgroundColor(0xFFC2C2);
                errorMessage += getContext().getString(R.string.field_error_message_edittext) + getContext().getString(R.string.field_work_size) + "\n";
                errorCount++;
            }

            if (!weightET.getText().toString().isEmpty()) {
                try {
                    String weightText = weightET.getText().toString();
                    weight = Float.parseFloat(weightText);
                } catch (Exception ex) {
                    weightET.setTextColor(0xFFC2C2);
                    weightET.setBackgroundColor(0xFFC2C2);
                    errorMessage += R.string.invalid_number_message;
                    errorCount++;
                }
            } else {
                weightET.setTextColor(0xFFC2C2);
                weightET.setBackgroundColor(0xFFC2C2);
                errorMessage += getContext().getString(R.string.field_error_message_edittext) + getContext().getString(R.string.field_work_weight) + "\n";
                errorCount++;
            }

            Artistas art = null;
            if (artList.getSelectedItemPosition() != 0) {
                art = (Artistas) artList.getSelectedItem();
            } else {
                artList.setBackgroundColor(0xFFC2C2);
                errorMessage += getContext().getString(R.string.spinner_select_artist) + "\n";
                errorCount++;
            }

            try {
                if (imageV.getDrawable() != null) {
                } else if (SourcesManager.getLastImage() != null) {
                    imageV.setImageBitmap(SourcesManager.getLastImage());
                } else {
                    errorCount++;
                    errorMessage += getContext().getString(R.string.image_not_found);
                }

                //CHECK PERMISSIONS
                int permissionCheck = ContextCompat.checkSelfPermission(
                        getActivity(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                );

                if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(
                            getActivity(),
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            REQUEST_PERMISSION
                    );
                } else hasPermissions = true;
            } catch (Exception ex) {
                errorCount++;
                errorMessage += getContext().getString(R.string.error_io_operations);
            }

            if (errorCount > 0) {
                Toast toast = Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG);
                toast.show();
            } else {
                try {
                    String extStore = Environment.getExternalStorageDirectory().getAbsolutePath();
                    File picturesStore = new File(extStore + "/" + Environment.DIRECTORY_PICTURES);

                    String imageFileName = name + "_" + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + ".png";
                    File file = new File(picturesStore, imageFileName);
                    if (hasPermissions) {

                        if (file.exists()) file.delete();

                        FileOutputStream out = new FileOutputStream(file);
                        imageBitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
                        out.flush();
                        out.close();
                    } else {
                        throw new Exception();
                    }

                    Trabajos trabajos = new Trabajos.TrabajosBuilder(name)
                            .setDescripcion(desc)
                            .setPeso(weight)
                            .setTamanio(size)
                            .setArtista(art)
                            .setFoto(file)
                            .build();

                    DBController db = new DBController(getContext());

                        if (db.add(Trabajos.class, trabajos)) {
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
        }else{

            boolean hasPermissions = false;

            EditText nameET = (EditText) root.findViewById(R.id.model_work_name);
            EditText descET = (EditText) root.findViewById(R.id.model_description);
            EditText sizeET = (EditText) root.findViewById(R.id.model_work_size);
            EditText weightET = (EditText) root.findViewById(R.id.model_work_weight);

            String name = models.getNombreTrab();
            String desc = models.getDescripcion();
            String size = models.getTamanio();
            float weight = models.getPeso();

            String errorMessage = "";
            int errorCount = 0;

            if (!descET.getText().toString().isEmpty()) {
                desc = descET.getText().toString();
            }

            if (!sizeET.getText().toString().isEmpty()) {
                size = sizeET.getText().toString();
            }

            if (!weightET.getText().toString().isEmpty()) {
                try {
                    String weightText = weightET.getText().toString();
                    weight = Float.parseFloat(weightText);
                } catch (Exception ex) {
                    weightET.setTextColor(0xFFC2C2);
                    weightET.setBackgroundColor(0xFFC2C2);
                    errorMessage += R.string.invalid_number_message;
                    errorCount++;
                }
            }

            Artistas art = models.getArtista();
            if (artList.getSelectedItemPosition() != 0) {
                art = (Artistas) artList.getSelectedItem();
            }

            if(imageBitmap != null) {
                try {
                    if (imageV.getDrawable() != null) {
                    } else if (SourcesManager.getLastImage() != null) {
                        imageV.setImageBitmap(SourcesManager.getLastImage());
                    }

                    //CHECK PERMISSIONS
                    int permissionCheck = ContextCompat.checkSelfPermission(
                            getActivity(),
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    );

                    if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                        ActivityCompat.requestPermissions(
                                getActivity(),
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                REQUEST_PERMISSION
                        );
                    } else hasPermissions = true;
                } catch (Exception ex) {
                    errorCount++;
                    errorMessage += getContext().getString(R.string.error_io_operations);
                }
            }
            if (errorCount > 0) {
                Toast toast = Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG);
                toast.show();
            } else {
                File file = models.getFoto();
                try {
                    if(imageBitmap!=null) {
                        String extStore = Environment.getExternalStorageDirectory().getAbsolutePath();
                        File picturesStore = new File(extStore + "/" + Environment.DIRECTORY_PICTURES);

                        String imageFileName = name + "_" + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + ".png";
                        file = new File(picturesStore, imageFileName);
                        if (hasPermissions) {

                            if (file.exists()) file.delete();

                            FileOutputStream out = new FileOutputStream(file);
                            imageBitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
                            out.flush();
                            out.close();
                        } else {
                            throw new Exception();
                        }
                    }
                    Trabajos trabajos = new Trabajos.TrabajosBuilder(models.getNombreTrab())
                            .setDescripcion(desc)
                            .setPeso(weight)
                            .setTamanio(size)
                            .setArtista(art)
                            .setFoto(file)
                            .build();

                    DBController db = new DBController(getContext());

                        if (db.update(Trabajos.class, trabajos)) {
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

    public void onClickCancel(View view) {
        getActivity().onBackPressed();
    }

}
