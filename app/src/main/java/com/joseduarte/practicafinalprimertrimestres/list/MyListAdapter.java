package com.joseduarte.practicafinalprimertrimestres.list;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.joseduarte.practicafinalprimertrimestres.R;
import com.joseduarte.practicafinalprimertrimestres.models.Models;
import com.joseduarte.practicafinalprimertrimestres.models.Trabajos;

import java.util.List;

public abstract class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder> {

    private List<Models> models;

    public MyListAdapter(List<Models> items) {
        models = items;
    }

    public void update(List<Models> items) {
        models = items;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.applyModel(models.get(position));
    }

    @Override
    public int getItemCount() {
        return models.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mDescriptionView;
        public final ImageView mImageView;
        public Models mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mView.setBackground(mView.getResources().getDrawable(R.drawable.app_transition_color));

            mView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return onTouchListener(v, event, ViewHolder.this.getLayoutPosition());
                }
            });

            mIdView = (TextView) view.findViewById(R.id.item_identifier);
            mDescriptionView = (TextView) view.findViewById(R.id.item_description);
            mImageView = (ImageView)view.findViewById(R.id.item_image);
        }

        public void applyModel(@NonNull Models model){
            if(model instanceof Trabajos) {
                mImageView.setImageURI(model.getImageUri());
            }
            else {
                mImageView.setImageURI(null);
                mImageView.setImageResource(0);
                mImageView.setVisibility(View.INVISIBLE);
                mImageView.setBackground(null);
                mImageView.setAdjustViewBounds(false);
            }
            mIdView.setText(model.getModelIdentifier());
            mDescriptionView.setText(model.getModelDescription());
            mItem = model;
        }

        public Models getModel() {
            return mItem;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mDescriptionView.getText() + "'";
        }
    }

    public abstract boolean onTouchListener(View view, MotionEvent event, int position);

}