package com.droidwars.starwars.resistance;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.Calendar;

public class addWarrior extends AppCompatActivity {

    private static final int REQUEST_CAMERA = 1;
    private static final int SELECT_FILE = 2;


    ImageView ivImage;
    private DbOperator mydb;
    TextView name;

    RadioGroup r1;
    RadioButton rb1;

    Spinner species_dropdown;
    Spinner gender_dropdown;
    Spinner planet_dropdown;
    TextView txtDate;
    String imageurl = "0";
    boolean new_w = true;
    int id;


    private int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_warrior);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        name = (TextView) findViewById(R.id.name_txt);
        txtDate = (TextView) findViewById(R.id.lso_date);
        r1 = (RadioGroup) findViewById(R.id.radioGroup);
        ivImage = (ImageView) findViewById(R.id.img_warrior);
        mydb = new DbOperator(this);

        Picasso.with(this).load(R.drawable.ic_menu_camera).resize(60, 60).onlyScaleDown().centerInside().into(ivImage);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        species_dropdown = (Spinner) findViewById(R.id.species_dropDown);
        String[] items0 = new String[]{"Select Species", "Human", "Mammalian", "Reptilian", "Avian", "Craniopod", "Droid", "Humanoid"};
        ArrayAdapter<String> adapter0 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items0);
        species_dropdown.setAdapter(adapter0);

        gender_dropdown = (Spinner) findViewById(R.id.gender_dropDown);
        String[] items1 = new String[]{"Select Gender", "Male", "Female", "Machine"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items1);
        gender_dropdown.setAdapter(adapter1);

        planet_dropdown = (Spinner) findViewById(R.id.planet_dropDown);
        String[] items2 = new String[]{"Select Planet", "Alderaan", "Bespin", "Coruscant", "Dagobah", "Endor", "Geonosis", "Hoth", "Kamino", "Mustafar", "Naboo", "Tatooine", "Utapau", "Yavin "};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items2);
        planet_dropdown.setAdapter(adapter2);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            RadioButton ls = (RadioButton) findViewById(R.id.rb_ls);
            RadioButton ds = (RadioButton) findViewById(R.id.rb_ds);
            id = extras.getInt("id");
            ContentValues details = mydb.getData(id);
            name.setText(details.get("NAME").toString());
            txtDate.setText(details.get("LSO").toString());

            if (details.get("AFF").toString().equals("Light Side")) {
                ls.setChecked(true);
            } else if (details.get("AFF").toString().equals("Dark Side")) {
                ds.setChecked(true);
            }
            species_dropdown.setSelection(adapter0.getPosition(details.get("SPECIES").toString()));
            gender_dropdown.setSelection(adapter1.getPosition(details.get("GENDER").toString()));
            planet_dropdown.setSelection(adapter2.getPosition(details.get("PLANET").toString()));
            imageurl = details.get("IMAGE").toString();
            Picasso.with(this).load(imageurl).centerInside().fit().error(R.drawable.default_pic).into(ivImage);

            new_w = false;
        }
    }

    public void onBackPressed() {
        mydb.close();

        finish();
        addWarrior.this.overridePendingTransition(R.anim.anim1, R.anim.anim5);
    }

    public void choose_img(View view) {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(addWarrior.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    File directory = new File(Environment.getExternalStorageDirectory() + "/Droid Wars/images");
                    if (!directory.exists()) {
                        directory.mkdirs();
                    }
//
                    File destination = new File(directory, System.currentTimeMillis() + ".jpg");
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    //File imgFile = new File("Cache directory", "img.png");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(destination));
                    imageurl = "file://" + destination.getAbsolutePath();
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECT_FILE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            if (requestCode == REQUEST_CAMERA) {
                //Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
//
                //ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                //thumbnail.compress(Bitmap.CompressFormat.PNG, 100, bytes);
                //File directory = new File(Environment.getExternalStorageDirectory()+"/Droid Wars/images");
                //if(!directory.exists())
                //{
                //    directory.mkdirs();
                //    //System.out.println("Success");
                //}
//
                //File destination = new File(directory,
                //        System.currentTimeMillis() + ".jpg");
                //FileOutputStream fo;
                //try {
//
                //    destination.createNewFile();
                //    fo = new FileOutputStream(destination);
                //    fo.write(bytes.toByteArray());
                //    fo.close();
                //} catch (IOException e) {
                //    e.printStackTrace();
                //}
                //ivImage.setImageBitmap(thumbnail);
                Picasso.with(this).load(imageurl).centerInside().fit().error(R.drawable.default_pic).into(ivImage);
            } else if (requestCode == SELECT_FILE) {
                Uri targetUri = data.getData();

                try {
                    Picasso.with(this).load(targetUri).into(ivImage);
                    //bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                    //ivImage.setImageBitmap(bitmap);
                    imageurl = targetUri.toString();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                //Uri selectedImageUri = data.getData();
                //selectedImagePath = getPath(selectedImageUri);
                //System.out.println("Image Path : " + selectedImagePath);
                //ivImage.setImageURI(selectedImageUri);
            }
        }
    }
    //public String getPath(Uri uri) {
    //    String[] projection = { MediaStore.Images.Media.DATA };
    //    Cursor cursor = managedQuery(uri, projection, null, null, null);
    //    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
    //    cursor.moveToNext();
    //    return cursor.getString(column_index);
    //}

    public void date_picker_dialog(View view) {
        final Calendar c = Calendar.getInstance();

        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        txtDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                    }
                }, mYear, mMonth, mDay);
        dpd.show();
    }

    public boolean save_details(View view) {

        if (name.length() == 0) {

            Toast.makeText(getApplicationContext(), "Name is required!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (species_dropdown.getSelectedItemId() == 0) {
            Toast.makeText(getApplicationContext(), "Please select species!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (gender_dropdown.getSelectedItemId() == 0) {
            Toast.makeText(getApplicationContext(), "Please select gender!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (planet_dropdown.getSelectedItemId() == 0) {
            Toast.makeText(getApplicationContext(), "Please select last known presence!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (r1.getCheckedRadioButtonId() == -1) {
            Toast.makeText(getApplicationContext(), "Please Select your Affiliation!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (txtDate.length() == 0) {
            Toast.makeText(getApplicationContext(), "Last Spotted Date is required!", Toast.LENGTH_SHORT).show();
            return false;
        }
        rb1 = (RadioButton) findViewById(r1.getCheckedRadioButtonId());
        if (new_w) {
            if (mydb.insertWarrior(name.getText().toString(), rb1.getText().toString(), species_dropdown.getSelectedItem().toString(), gender_dropdown.getSelectedItem().toString(), txtDate.getText().toString(), planet_dropdown.getSelectedItem().toString(), imageurl)) {
                Toast.makeText(getApplicationContext(), "Warrior Added", Toast.LENGTH_SHORT).show();
                mydb.close();
                finish();
                return true;
            } else {
                Toast.makeText(getApplicationContext(), "Some Error! :(", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            if (mydb.updateWarrior(id, name.getText().toString(), rb1.getText().toString(), species_dropdown.getSelectedItem().toString(), gender_dropdown.getSelectedItem().toString(), txtDate.getText().toString(), planet_dropdown.getSelectedItem().toString(), imageurl)) {
                Toast.makeText(getApplicationContext(), "Warrior Updated", Toast.LENGTH_SHORT).show();
                mydb.close();
                Intent returnIntent = new Intent();
                returnIntent.putExtra("Result", "Edited Warrior");
                setResult(RESULT_OK, returnIntent);
                finish();
                return true;
            } else {
                Toast.makeText(getApplicationContext(), "Some Error! :(", Toast.LENGTH_SHORT).show();
                return false;
            }
        }


    }
}
