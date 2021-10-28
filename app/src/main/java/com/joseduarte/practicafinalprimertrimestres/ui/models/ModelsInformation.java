package com.joseduarte.practicafinalprimertrimestres.ui.models;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.joseduarte.practicafinalprimertrimestres.R;
import com.joseduarte.practicafinalprimertrimestres.appliers.Applier;
import com.joseduarte.practicafinalprimertrimestres.appliers.ArtistasApplier;
import com.joseduarte.practicafinalprimertrimestres.appliers.ExposicionesApplier;
import com.joseduarte.practicafinalprimertrimestres.appliers.TrabajosApplier;
import com.joseduarte.practicafinalprimertrimestres.models.Artistas;
import com.joseduarte.practicafinalprimertrimestres.models.Comentarios;
import com.joseduarte.practicafinalprimertrimestres.models.Exposiciones;
import com.joseduarte.practicafinalprimertrimestres.models.Models;
import com.joseduarte.practicafinalprimertrimestres.models.Trabajos;

public class ModelsInformation extends Fragment {

    private int viewResource = R.layout.model_information_exhibitions;

    private Applier applier = new ExposicionesApplier();

    private Class<? extends Models> modelClass;
    private Models models;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null) {
            modelClass = (Class<? extends Models>)getArguments().getSerializable("modelClass");
            models = (Models)getArguments().getSerializable("model");

            if(models instanceof Exposiciones) {
                viewResource = R.layout.model_information_exhibitions;
                applier = new ExposicionesApplier();
            }
            else if(models instanceof Artistas) {
                viewResource = R.layout.model_information_artists;
                applier = new ArtistasApplier();
            }
            else if(models instanceof Trabajos) {
                viewResource = R.layout.model_information_works;
                applier = new TrabajosApplier();
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(viewResource, container, false);

        applier.applyTo(root, models);

        return root;
    }
}
