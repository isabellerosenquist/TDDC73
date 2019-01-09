package com.example.isabelle.lab21;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Isabelle on 2016-11-29.
 */

public class ExpandableListViewAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> listParentData;
    private HashMap<String, List<String>> listChildData;
    private int selectedGroup = -1, selectedChild = -1;

    public void updateSelection(int selectedGroup, int selectedChild) {
        this.selectedGroup = selectedGroup;
        this.selectedChild = selectedChild;
        notifyDataSetChanged();
    }

    public ExpandableListViewAdapter(Context context, List<String> listParentData, HashMap<String, List<String>>listChildData ){
        this.context = context;
        this.listParentData = listParentData;
        this.listChildData = listChildData;
    }

    @Override
    public int getGroupCount() {
        return this.listParentData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listChildData.get(this.listParentData.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listParentData.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.listChildData.get(this.listParentData.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.parent_layout, null);
        }

       /* if(isExpanded) {
            convertView.setBackgroundColor(R.color.colorAccent);
        }*/

        String headerTitle = (String) getGroup(groupPosition);
        TextView textParent = (TextView) convertView.findViewById(R.id.parent_layout);
        textParent.setText(headerTitle);

        return convertView ;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.child_layout, null);
        }

        String childText = (String) getChild(groupPosition, childPosition);

        TextView textviewChild = (TextView)convertView.findViewById(R.id.child_layout);

        textviewChild.setText(childText);

        if (selectedGroup == groupPosition && selectedChild == childPosition) {
            textviewChild.setBackgroundColor(Color.GREEN);
        } else {
            textviewChild.setBackgroundColor(Color.WHITE);
        }

        return convertView;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}


//Rött fält när det inte finns snestreck
// Barn avmarkerad när man skriver fel i fältet, markeras sen när