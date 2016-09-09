package com.droidwars.starwars.resistance;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class warrior_det extends AppCompatActivity {
    DbOperator myDB;
    ImageView ivImage;
    TextView name;
    TextView lso_date;
    TextView aff;
    TextView species;
    TextView gender;
    TextView planet;
    int id = 0;
    View v = null;
    int sb_shown = 0;
    int edited = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warrior_det);
        v = findViewById(R.id.CL_main);
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("Warrior Details");
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getInt("id");
            myDB = new DbOperator(this);
            ContentValues details = myDB.getData(id);
            ivImage = (ImageView) findViewById(R.id.w_img);
            name = (TextView) findViewById(R.id.name);
            lso_date = (TextView) findViewById(R.id.date);
            aff = (TextView) findViewById(R.id.aff);
            species = (TextView) findViewById(R.id.species);
            gender = (TextView) findViewById(R.id.gender);
            planet = (TextView) findViewById(R.id.planet);

            name.setText(details.get("NAME").toString());
            lso_date.setText(details.get("LSO").toString());
            aff.setText(details.get("AFF").toString());
            species.setText(details.get("SPECIES").toString());
            gender.setText(details.get("GENDER").toString());
            planet.setText(details.get("PLANET").toString());


            Picasso.with(this).load(details.get("IMAGE").toString()).fit().centerCrop().error(R.drawable.default_pic).into(ivImage);

        } else
            finish();

    }

    @Override
    public void onBackPressed() {
        //Log.d("CDA", "onBackPressed Called");
        if (sb_shown == 1) {
            myDB.deleteWarrior(id);
            myDB.close();
            Intent returnIntent = new Intent();
            returnIntent.putExtra("Result", "Deleted Warrior");
            setResult(RESULT_OK, returnIntent);
            finish();
        }
        if (edited == 1) {
            myDB.close();
            Intent returnIntent = new Intent();
            returnIntent.putExtra("Result", "Warrior Edited");
            setResult(RESULT_OK, returnIntent);
            finish();
        } else {
            myDB.close();
            Intent returnIntent = new Intent();
            setResult(RESULT_CANCELED, returnIntent);
            finish();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_warrior_det, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int item_id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (item_id == R.id.action_edit) {
            Bundle dataBundle = new Bundle();
            dataBundle.putInt("id", id);
            Intent intent = new Intent(getApplicationContext(), addWarrior.class);
            intent.putExtras(dataBundle);
            startActivityForResult(intent, 1);

        } else if (item_id == R.id.action_delete) {
            new AlertDialog.Builder(this)
                    .setTitle("Confirm Removal")
                    .setMessage("Do you really want to Remove this Warrior?")
                    .setIcon(android.R.drawable.ic_menu_delete)

                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {

                            delete();

                        }
                    })
                    .setNegativeButton(android.R.string.no, null).show();
        } else if (item_id == R.id.action_washare) {
            Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
            whatsappIntent.setType("text/plain");
            whatsappIntent.setPackage("com.whatsapp");
            String message = "Name : " + name.getText().toString() + "\nGender : " + gender.getText().toString() + "\nSpecies : " + species.getText().toString() + "\nPlanet : " + planet.getText().toString() + "\nSide : " + aff.getText().toString() + "\nLast Spotted on : " + lso_date.getText().toString();
            whatsappIntent.putExtra(Intent.EXTRA_TEXT, "");
            try {
                this.startActivity(whatsappIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(this, "Whatsapp has not been Installed.", Toast.LENGTH_SHORT).show();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {

            if (resultCode == RESULT_OK) {

                Intent intent = getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                startActivity(intent);
                edited = 1;


            }
            if (resultCode == RESULT_CANCELED) {
                //Do nothing
            }
        }
    }

    public void delete() {
        sb_shown = 1;
        Snackbar sb = Snackbar.make(v, "Warrior Removed!", Snackbar.LENGTH_LONG);

        sb.setAction("Undo", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sb_shown = 0;
                Snackbar.make(view, "Warrior restored!", Snackbar.LENGTH_SHORT).show();
            }
        });

        sb.setCallback(new Snackbar.Callback() {

            @Override
            public void onDismissed(Snackbar snackbar, int event) {
                if (event == DISMISS_EVENT_SWIPE || event == DISMISS_EVENT_TIMEOUT) {
                    myDB.deleteWarrior(id);
                    myDB.close();
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("Result", "Deleted Warrior");
                    setResult(RESULT_OK, returnIntent);
                    finish();

                }
            }


        });
        sb.show();


    }
}
