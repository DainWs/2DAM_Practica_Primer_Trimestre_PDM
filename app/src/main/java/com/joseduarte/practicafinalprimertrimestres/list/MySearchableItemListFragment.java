package com.joseduarte.practicafinalprimertrimestres.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.joseduarte.practicafinalprimertrimestres.R;
import com.joseduarte.practicafinalprimertrimestres.SourcesManager;
import com.joseduarte.practicafinalprimertrimestres.db.DBController;
import com.joseduarte.practicafinalprimertrimestres.models.Comentarios;
import com.joseduarte.practicafinalprimertrimestres.models.Exposiciones;
import com.joseduarte.practicafinalprimertrimestres.models.Models;
import com.joseduarte.practicafinalprimertrimestres.models.Trabajos;

import java.util.List;

public abstract  class MySearchableItemListFragment extends MyListFragment{

    protected List<Models> exposicionesList;
    protected List<Models> trabajosList;

    public MySearchableItemListFragment() {
        super(Comentarios.class);
    }

    public MySearchableItemListFragment(@NonNull Class<? extends Models> modelClass, int searchableBox) {
        super(modelClass);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list_searchable_item, container, false);
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.list);

        recyclerView.setAdapter(myListAdapter);

        DBController db = new DBController(root.getContext());
        exposicionesList = db.getListModels(Exposiciones.class);
        trabajosList = db.getListModels(Trabajos.class);

        Spinner expSpinner = root.findViewById(R.id.searchable_exposition_item);
        SpinnerAdapter expAdapter = new ArrayAdapter<Models>(
                root.getContext(),
                android.R.layout.simple_spinner_item,
                exposicionesList);

        expSpinner.setAdapter(new ModelsSpinnerAdapter(
                expAdapter,
                root.getContext(),
                root.getContext()
                        .getString(R.string.spinner_select_exhibition)
        ));

        expSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                onSpinnerExpClicked(parentView, selectedItemView, position, id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

        Spinner trabSpinner = root.findViewById(R.id.searchable_trabajo_item);
        SpinnerAdapter trabAdapter = new ArrayAdapter<Models>(
                root.getContext(),
                android.R.layout.simple_spinner_item,
                trabajosList);

        trabSpinner.setAdapter(new ModelsSpinnerAdapter(
                trabAdapter,
                root.getContext(),
                root.getContext()
                        .getString(R.string.spinner_select_work)
        ));

        trabSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                onSpinnerTraClicked(parentView, selectedItemView, position, id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

        FloatingActionButton addItemBtn = (FloatingActionButton)root.findViewById(R.id.add_item_to_list_btn);
        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAddItemBtn(v);
            }
        });
        return root;
    }

    public abstract void onSpinnerExpClicked(AdapterView<?> parent, View view, int position, long id);
    public abstract void onSpinnerTraClicked(AdapterView<?> parent, View view, int position, long id);


}
