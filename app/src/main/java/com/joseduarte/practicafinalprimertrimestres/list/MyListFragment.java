package com.joseduarte.practicafinalprimertrimestres.list;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.joseduarte.practicafinalprimertrimestres.MyPreferences;
import com.joseduarte.practicafinalprimertrimestres.R;
import com.joseduarte.practicafinalprimertrimestres.SourcesManager;
import com.joseduarte.practicafinalprimertrimestres.list.MyListAdapter;
import com.joseduarte.practicafinalprimertrimestres.models.Exposiciones;
import com.joseduarte.practicafinalprimertrimestres.models.Models;

import java.util.List;

public abstract class MyListFragment extends Fragment {

    protected MyListAdapter myListAdapter;
    protected List<Models> models;
    protected Class<? extends Models> modelClass;

    public MyListFragment() {
        modelClass = Exposiciones.class;
        models = SourcesManager.getDBController().getListModels(modelClass);
        myListAdapter = new MyListAdapter(models) {
            @Override
            public boolean onTouchListener(View view, MotionEvent event, int position) {
                return onTouch(view, event, position);
            }
        };
        updateList();
    }

    public MyListFragment(@NonNull Class<? extends Models> modelClass) {
        this.modelClass = modelClass;
        models = SourcesManager.getDBController().getListModels(modelClass);
        myListAdapter = new MyListAdapter(models){
            @Override
            public boolean onTouchListener(View view, MotionEvent event, int position) {
                return onTouch(view, event, position);
            }
        };
        updateList();
    }

    public MyListFragment(@NonNull Class<? extends Models> modelClass, List<Models> models) {
        this.modelClass = modelClass;
        this.models = models;
        myListAdapter = new MyListAdapter(models){
            @Override
            public boolean onTouchListener(View view, MotionEvent event, int position) {
                return onTouch(view, event, position);
            }
        };
        updateList();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list_container, container, false);
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.list);

        recyclerView.setAdapter(myListAdapter);

        FloatingActionButton addItemBtn = (FloatingActionButton)root.findViewById(R.id.add_item_to_list_btn);
        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAddItemBtn(v);
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        models = SourcesManager.getDBController().getListModels(modelClass);
        updateList();
    }

    public void updateList() {
        myListAdapter.update(models);
    }

    private TransitionDrawable transition;
    private Long lastTouchTime;
    private Long lastTouchDurationTime;
    public boolean onTouch(View view, MotionEvent event, int position) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            transition = (TransitionDrawable) view.getBackground();
            transition.startTransition(1000);
            lastTouchTime = System.currentTimeMillis();

        } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
            transition.resetTransition();

            lastTouchDurationTime = System.currentTimeMillis() - lastTouchTime;
            if(lastTouchDurationTime > 1000) {
                onLongClick(view, position);
            } else {
                onClick(view, position);
            }
        }
        return true;
    }

    public void onClick(View view, int position) {

    }

    public boolean onLongClick(View view, final int position) {

        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        getActivity().getMenuInflater().inflate(R.menu.onlongclick_item_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return onMenuItemClicked(item, position);
            }
        });
        popupMenu.show();
        return true;
    }

    public boolean onMenuItemClicked(MenuItem item, int position) {

        int id = item.getItemId();

        if(id == R.id.item_onlongclick_modify_option) {
            onClickEditItemBtn(position);
        }
        else if(id == R.id.item_onlongclick_delete_option) {
            onClickDeleteItemBtn(position);
        }

        return true;
    }

    public abstract void onClickAddItemBtn(View view);

    public abstract void onClickEditItemBtn(int position);

    public abstract void onClickDeleteItemBtn(int position);


    public void navigateTo(View view, int navResource) {
        Navigation.findNavController(view).navigate(navResource);
    }

    public void navigateTo(View view, int navResource, Bundle b) {
        Navigation.findNavController(view).navigate(navResource, b);
    }
}
