package com.example.isabelle.lab21;

import android.graphics.Color;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {
    ExpandableListViewAdapter listViewAdapter;
    ExpandableListView expandableListView;
    List<String> listDataParent;
    HashMap<String, List<String>> listDataChild;
    EditText searchtext;
    boolean cleartext;
    int selectedIndex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expandableListView = (ExpandableListView)findViewById(R.id.lvExp);

        prepareListData();

        listViewAdapter = new ExpandableListViewAdapter(this, listDataParent, listDataChild);

        expandableListView.setAdapter(listViewAdapter);

        searchtext = (EditText)findViewById(R.id.editText);
        searchtext.setText("/");
        searchtext.setSelection(searchtext.getText().length());

        searchtext.addTextChangedListener(searchWatcher);

        cleartext = true;

        expandableListView.setChoiceMode(ExpandableListView.CHOICE_MODE_SINGLE);


       expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {

                searchtext.setBackgroundColor(Color.WHITE);
                collapseAll(groupPosition);
                searchtext.setText("/" + listDataParent.get(groupPosition));
                searchtext.setSelection(searchtext.getText().length());
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                if(cleartext){
                    searchtext.setText("/");
                    searchtext.setSelection(searchtext.getText().length());
                }
                else
                    cleartext = false;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                searchtext.setText("/"+listDataParent.get(groupPosition)+"/"+listDataChild.get(listDataParent.get(groupPosition)).get(childPosition));
                searchtext.setSelection(searchtext.getText().length());

                parent.setItemChecked(selectedIndex, false);
                int index = parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition, childPosition));
                selectedIndex = index;
                parent.setItemChecked(index, true);

                return false;
            }
        });
    }

    private void collapseAll(int groupPosition){

        for(int i = 0; i < listViewAdapter.getGroupCount(); i++){

            if(i != groupPosition && expandableListView.isGroupExpanded(i))
                expandableListView.collapseGroup(i);
        }

    };

    private final TextWatcher searchWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            if (!s.toString().equals("")) {

                SearchText(s.toString());
            }
        }
    };

                // '/
                //searchtext.setText("/");
             /*   cleartext = false;
                Log.e("LOG", "before search 1");

               if(!SearchText(s.toString().substring(1))){
                   Log.e("LOG", "after search 1 - false");

                   collapseAll(-1);

                   searchtext.setBackgroundColor(Color.RED);
               }

                String substring2 = "";

                boolean lower = false;

                for(int k = 1; k < s.toString().length(); k++){
                    char it = s.toString().charAt(k);

                    if(lower){
                        substring2 += it;

                        Log.e("LOG", "before search 2");
                        if(!SearchText(substring2)){
                            Log.e("LOG", "after search 2 - false");

                            Log.e("LOG", substring2);

                            collapseAll(-1);

                            searchtext.setBackgroundColor(Color.RED);
                        }

                    }
                    if(it == '/'){
                        lower = true;
                    }

                }
                if (!substring2.equals("")) {
                    selectChild(substring2);
                }

                cleartext = true;

            }
        }
    };
*/

    public void selectChild(String substring2){

        //System.out.println("Substring2: " + substring2);
        boolean isFound = false;

        int saveGroup = -1;
        int saveChild = -1;

        for(int i = 0; i <listViewAdapter.getGroupCount(); i++){

            if(!expandableListView.isGroupExpanded(i))
                continue;

            for(int j = 0; j < listViewAdapter.getChildrenCount(i); j++){

                Boolean isLast = false;
                if (j == listViewAdapter.getChildrenCount(i)-1)
                    isLast = true;
                TextView textView2 = (TextView)listViewAdapter.getChildView(i, j, isLast, null, null).findViewById(R.id.child_layout);
                if(substring2.equals(textView2.getText())){
                    isFound = true;
                    saveChild = j;
                    saveGroup = i;
                    break;
                }
            }

            if(isFound)
                break;
        }
        if(saveChild != -1 && saveGroup != -1 && isFound){
            expandableListView.setItemChecked(selectedIndex,false);
            int index = expandableListView.getFlatListPosition(ExpandableListView.getPackedPositionForChild(saveGroup, saveChild));
            selectedIndex = index;
            expandableListView.setItemChecked(selectedIndex,true);
            
        }
    }

    private void SearchText(String s){
        Log.e("LOG", "Inside search text ----");
        String top = "";
        String child = "";
        boolean matchesTop = false;
        boolean matchesChild = false;

        StringTokenizer st = new StringTokenizer(s.substring(1), "/");

            top = st.nextToken();

            //child = st.nextToken();

            for (int j = 0; j < listViewAdapter.getGroupCount(); j++) {

                if (top.equals(listDataParent.get(j)) ) {
                    if (!expandableListView.isGroupExpanded(j)) {

                        expandableListView.expandGroup(j);
                        Log.d("===============", j + "");
                    }

                    Log.e("LOG", "246 expand true");
                    while (st.hasMoreTokens()) {
                        child = st.nextToken();
                        Log.e("LOG", "read child");
                        // if (child != "") {
                        for (int k = 0; k < listViewAdapter.getChildrenCount(j); k++) {
                            if (child.equals(listViewAdapter.getChild(j, k))) {

                                int selectedIndex = expandableListView.getFlatListPosition(ExpandableListView.getPackedPositionForChild(j, k));
                                expandableListView.setItemChecked(selectedIndex, false);

                            } else if ((listViewAdapter.getChild(j, k)).toString().contains(child)) {
                                matchesChild = true;
                            }
                        }
                        //}
                    }

                } else if (listDataParent.get(j).contains(top)) {
                    matchesTop = true;
                }

                //break;


                if (!matchesTop || !matchesChild) {
                    // Set text to white
                    searchtext.setBackgroundColor(Color.WHITE);
                } else {
                    // Set text to red
                    searchtext.setBackgroundColor(Color.RED);
                }
            }

        //return false;
    };





    private void prepareListData(){

        listDataParent = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        listDataParent.add("Light");
        listDataParent.add("Medium");
        listDataParent.add("Dark");

        List<String> light = new ArrayList<String>();
        light.add("Red");
        light.add("Yellow");
        light.add("Green");
        light.add("Blue");

        List<String> medium = new ArrayList<String>();
        medium.add("Purple");
        medium.add("Magenta");
        medium.add("Orange");
        medium.add("Turqoise");

        List<String> dark = new ArrayList<String>();
        dark.add("Grey");
        dark.add("Pink");
        dark.add("Brown");
        dark.add("Marine");

        listDataChild.put(listDataParent.get(0), light);
        listDataChild.put(listDataParent.get(1), medium);
        listDataChild.put(listDataParent.get(2), dark);
    }
}
