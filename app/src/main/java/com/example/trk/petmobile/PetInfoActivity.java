package com.example.trk.petmobile;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import static com.example.trk.petmobile.R.id.btnAsi;
import static com.example.trk.petmobile.R.id.imageToUpload;

public class PetInfoActivity extends AppCompatActivity implements View.OnClickListener{
    private static final int RESULT_LOAD_IMAGE =1;
    ImageView imLoadpet;
    EditText etLoadpet;
    Button btnLoadpet;
    Button btngitAsi;
    Button btngitHastalik;
    Button btngitTedavi;
    Button btngitRandevu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_info);

        imLoadpet=(ImageView)findViewById(imageToUpload);
        etLoadpet = (EditText) findViewById(R.id.etUploadName);
        btnLoadpet = (Button) findViewById(R.id.btnUploadImage);
        btngitAsi = (Button) findViewById(R.id.btnAsi);
        btngitHastalik = (Button) findViewById(R.id.btnHastalik);
        btngitTedavi = (Button) findViewById(R.id.btnTedavi);
        btngitRandevu = (Button) findViewById(R.id.btnRandevu);


        imLoadpet.setOnClickListener(this);
        btnLoadpet.setOnClickListener(this);
        btngitAsi.setOnClickListener(this);
        btngitHastalik.setOnClickListener(this);
        btngitTedavi.setOnClickListener(this);
        btngitRandevu.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case imageToUpload:
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE );
                break;
            case R.id.btnUploadImage:
                Intent galleryIntent2 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent2, RESULT_LOAD_IMAGE );
                break;
            case R.id.btnAsi:
                Intent intentLoadasi = new Intent(PetInfoActivity.this, VaccineActvity.class);
                startActivity(intentLoadasi);
                break;
            case R.id.btnHastalik:
                Intent intentLoadhastalik = new Intent(PetInfoActivity.this, ThreatmentActivity.class);
                startActivity(intentLoadhastalik);
                break;
            case R.id.btnTedavi:
                Intent intentLoadtedavi = new Intent(PetInfoActivity.this, ThreatmentActivity.class);
                startActivity(intentLoadtedavi);
                break;
            case R.id.btnRandevu:
                Intent intentLoadrandevu = new Intent(PetInfoActivity.this, AppointmentActivity.class);
                startActivity(intentLoadrandevu);
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if((requestCode != RESULT_LOAD_IMAGE )&&( resultCode != RESULT_OK )&& (data != null)){
            Uri selectedImage= data.getData();
            ImageView imageToUpload = (ImageView)findViewById(R.id.imageToUpload);
            imageToUpload.setImageURI(selectedImage);



        }
    }
}
