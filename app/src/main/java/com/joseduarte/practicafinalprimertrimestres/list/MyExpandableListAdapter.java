package com.joseduarte.practicafinalprimertrimestres.list;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.joseduarte.practicafinalprimertrimestres.R;
import com.joseduarte.practicafinalprimertrimestres.models.Models;
import com.joseduarte.practicafinalprimertrimestres.models.Trabajos;

import java.util.HashMap;
import java.util.List;

public class MyExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> expandableListTitle;
    private HashMap<String, List<? extends Models>> expandableListDetail;

    public MyExpandableListAdapter(Context context, List<String> expandableListTitle,
                                   HashMap<String, List<? extends Models>> expandableListDetail) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
    }

    public void update(String title, List<? extends Models> newModels) {
        expandableListDetail.put(title, newModels);
        notifyDataSetChanged();
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.expandableListDetail
                .get(this.expandableListTitle.get(listPosition))
                .get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.fragment_item, null);
        }

        Models model = (Models) getChild(listPosition, expandedListPosition);

        TextView mIdView = (TextView) convertView.findViewById(R.id.item_identifier);
        TextView mDescriptionView = (TextView) convertView.findViewById(R.id.item_description);
        ImageView mImageView = (ImageView)convertView.findViewById(R.id.item_image);

        mIdView.setText(model.getModelIdentifier());
        mDescriptionView.setText(model.getModelDescription());

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

        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.expandable_list_group, null);
        }

        String listTitle = (String) getGroup(listPosition);

        TextView listTitleTV = (TextView) convertView.findViewById(R.id.list_title);

        listTitleTV.setTypeface(null, Typeface.BOLD);
        listTitleTV.setText(listTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}
