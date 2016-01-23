package com.droidwars.starwars.resistance.ListAdapter;

/**
 * Created by Chirag on 18-01-2016.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.droidwars.starwars.resistance.R;
import com.droidwars.starwars.resistance.disp_warrior;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;


/********* Adapter class extends with BaseAdapter and implements with OnClickListener ************/
public class CustomAdapter extends BaseAdapter implements View.OnClickListener {

    /*********** Declare Used Variables *********/
    private Activity activity;
    private ArrayList data;
    private static LayoutInflater inflater=null;

    ListModel tempValues=null;
    String side;



    /*************  CustomAdapter Constructor *****************/
    public CustomAdapter(Activity a, ArrayList d) {

        /********** Take passed values **********/
        activity = a;
        data=d;


        /***********  Layout inflater to call external xml layout () ***********/
        inflater = ( LayoutInflater )activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    /******** What is the size of Passed Array_list Size ************/
    public int getCount() {

        if(data.size()<=0)
            return 1;
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    /********* Create a holder Class to contain inflated xml file elements *********/
    public static class ViewHolder{

        public TextView text_name;
        public TextView text_gender;
        public ImageView img_warrior;

    }

    /****** Depends upon data size called for each row , Create each ListView row *****/
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        ViewHolder holder;

        if(convertView==null){

            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/

            vi = inflater.inflate(R.layout.tabitem, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.text_name = (TextView) vi.findViewById(R.id.text_name);
            holder.text_gender=(TextView)vi.findViewById(R.id.text_gender);
            holder.img_warrior=(ImageView)vi.findViewById(R.id.img_warrior);

            /************  Set holder with LayoutInflater ************/
            vi.setTag( holder );
        }
        else
            holder=(ViewHolder)vi.getTag();

        if(data.size()<=0)
        {
            holder.text_name.setText("No Data");

        }
        else
        {
            /***** Get each Model object from Arraylist ********/
            tempValues = null;
            tempValues = ( ListModel ) data.get( position );

            /************  Set Model values in Holder elements ***********/

            holder.text_name.setText(tempValues.getWarriorName());
            holder.text_gender.setText(tempValues.getGender());

            Picasso.with(activity.getApplicationContext()).load(tempValues.getWarriorImage()).placeholder(R.drawable.default_pic).centerInside().fit().error(R.drawable.default_pic).into(holder.img_warrior);


            side = tempValues.getSide();

            if (side.equals("Light Side") ) {

                vi.setBackgroundColor(Color.parseColor("#87CEFA"));
            } else if (side.equals("Dark Side")) {

                vi.setBackgroundColor(Color.parseColor("#FF8484"));
            }
            vi.setOnClickListener(new OnItemClickListener( tempValues.getId() ));
        }
        return vi;
    }

    @Override
    public void onClick(View v) {
        Log.v("CustomAdapter", "=====Row button clicked=====");
    }

    /********* Called when Item click in ListView ************/
    private class OnItemClickListener  implements View.OnClickListener {
        private int mPosition;

        OnItemClickListener(int position){
            mPosition = position;
        }

        @Override
        public void onClick(View arg0) {


            disp_warrior sct = (disp_warrior)activity;



            sct.onItemClick(mPosition);
        }
    }


}