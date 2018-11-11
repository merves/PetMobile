package com.example.trk.petmobile;

        import android.app.Activity;
        import android.content.Intent;
        import android.database.Cursor;
        import android.graphics.BitmapFactory;
        import android.net.Uri;
        import android.os.Bundle;
        import android.provider.MediaStore;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.widget.Button;
        import android.widget.ImageView;

public class PetInfoActivity extends Activity implements OnClickListener {

    private static final int IMAGE_PICK = 1;
    private static final int IMAGE_CAPTURE = 2;

    private ImageView imageView;
    private Button buttonNewPic;
    private Button buttonImage;
    Button btngitAsi;
    Button btngitHastalik;
    Button btngitTedavi;
    Button btngitRandevu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_info);

        this.imageView = (ImageView) this.findViewById(R.id.imageToUpload);
        this.buttonNewPic = (Button) this.findViewById(R.id.btnUploadImage);
        this.buttonImage = (Button) this.findViewById(R.id.btnUpCamera);
        btngitAsi = (Button) findViewById(R.id.btnAsi);
        btngitHastalik = (Button) findViewById(R.id.btnHastalik);
        btngitTedavi = (Button) findViewById(R.id.btnTedavi);
        btngitRandevu = (Button) findViewById(R.id.btnRandevu);

        this.buttonImage.setOnClickListener(new ImagePickListener());
        this.buttonNewPic.setOnClickListener(new TakePictureListener());
        btngitAsi.setOnClickListener(this);
        btngitHastalik.setOnClickListener(this);
        btngitTedavi.setOnClickListener(this);
        btngitRandevu.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
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

    class ImagePickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "Bir Fotoðraf Seçin"), IMAGE_PICK);
        }
    }

    class TakePictureListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, IMAGE_CAPTURE);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case IMAGE_PICK:
                    this.imageFromGallery(resultCode, data);
                    break;
                case IMAGE_CAPTURE:
                    this.imageFromCamera(resultCode, data);
                    break;
                default:
                    break;
            }
        }
    }

    private void imageFromCamera(int resultCode, Intent data) {
        String[] projection = {
                MediaStore.Images.Thumbnails._ID, // The columns we want
                MediaStore.Images.Thumbnails.IMAGE_ID,
                MediaStore.Images.Thumbnails.KIND,
                MediaStore.Images.Thumbnails.DATA};
        String selection = MediaStore.Images.Thumbnails.KIND + "=" + // Select only mini's
                MediaStore.Images.Thumbnails.MINI_KIND;

        String sort = MediaStore.Images.Thumbnails._ID + " DESC";

        Cursor myCursor = this.managedQuery(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, projection, selection, null, sort);

        long imageId = 0l;

        try{
            myCursor.moveToFirst();
            imageId = myCursor.getLong(myCursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails.IMAGE_ID));
        }
        finally{myCursor.close();}

        String[] largeFileProjection = {
                MediaStore.Images.ImageColumns._ID,
                MediaStore.Images.ImageColumns.DATA
        };

        String largeFileSort = MediaStore.Images.ImageColumns._ID + " DESC";
        myCursor = this.managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, largeFileProjection, null, null, largeFileSort);
        try{
            myCursor.moveToFirst();
        }
        finally{myCursor.close();}
        Uri uriLargeImage = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, String.valueOf(imageId));
//Uri uriThumbnailImage = Uri.withAppendedPath(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, String.valueOf(thumbnailImageId));
        imageView.setImageURI(uriLargeImage);
    }

    private void imageFromGallery(int resultCode, Intent data) {
        Uri selectedImage = data.getData();
        String [] filePathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String filePath = cursor.getString(columnIndex);
        this.imageView.setImageBitmap(BitmapFactory.decodeFile(filePath));
        cursor.close();

    }

}

/*
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import static com.example.trk.petmobile.R.id.btnAsi;
import static com.example.trk.petmobile.R.id.imageToUpload;

public class PetInfoActivity extends AppCompatActivity implements View.OnClickListener{
    private static final int IMAGE_PICK = 1;
    private static final int IMAGE_CAPTURE = 2;
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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Ask veterinary", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

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


    class ImagePickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "Bir Fotoðraf Seçin"), IMAGE_PICK);
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
*/