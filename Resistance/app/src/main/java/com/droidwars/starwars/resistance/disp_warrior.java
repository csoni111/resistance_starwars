package com.droidwars.starwars.resistance;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.droidwars.starwars.resistance.ListAdapter.CustomAdapter;
import com.droidwars.starwars.resistance.ListAdapter.ListModel;

import java.util.ArrayList;

public class disp_warrior extends AppCompatActivity {
    DbOperator mydb;
    ListView list;
    CustomAdapter adapter;
    public  disp_warrior CustomListView = null;
    public  ArrayList<ListModel> CustomListViewValuesArr = new ArrayList<ListModel>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disp_warrior);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CustomListView = this;
        /******** Take some data in Arraylist ( CustomListViewValuesArr ) ***********/
        mydb = new DbOperator(this);
        CustomListViewValuesArr = mydb.getAllWarriorDetails();
        mydb.close();
        list = ( ListView )findViewById( R.id.listView );

        /**************** Create Custom Adapter *********/
        adapter = new CustomAdapter( CustomListView, CustomListViewValuesArr );
        list.setAdapter(adapter);

    }
    /*****************  This function used by adapter ****************/
    public void onItemClick(int mPosition)
    {
        //ListModel tempValues = ( ListModel ) CustomListViewValuesArr.get(mPosition);
        Bundle dataBundle = new Bundle();
        dataBundle.putInt("id", mPosition);

        Intent showDetails = new Intent(getApplicationContext(),warrior_det.class);

        showDetails.putExtras(dataBundle);
        startActivityForResult(showDetails, 1);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {

            if(resultCode == RESULT_OK){;
                Intent intent = getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                startActivity(intent);

            }
            if (resultCode == RESULT_CANCELED) {
                //Do nothing
            }
        }
    }

}
