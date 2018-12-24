package com.example.trk.petmobile;

        import android.app.Activity;
        import android.app.AlertDialog;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.database.Cursor;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.net.Uri;
        import android.os.Bundle;
        import android.provider.MediaStore;
        import android.support.design.widget.FloatingActionButton;
        import android.support.design.widget.Snackbar;
        import android.util.Base64;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.widget.Button;
        import android.widget.ImageView;

        import java.io.ByteArrayOutputStream;

public class PetInfoActivity extends Activity implements OnClickListener {
    ImageView imageView;
    Integer REQUEST_CAMERA=1, SELECT_FILE=0;

    Button buttonNewPic;
    // private Button buttonImage;
    Button btngitAsi;
    Button btngitHastalik;
    Button btngitTedavi;
    Button btngitRandevu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_info);

        SharedPreferences preferences = getSharedPreferences("myprefs",MODE_PRIVATE);

        imageView = (ImageView) findViewById(R.id.imageToUpload);

        buttonNewPic = (Button) findViewById(R.id.btnUploadImage);
        btngitAsi = (Button) findViewById(R.id.btnAsi);
        btngitHastalik = (Button) findViewById(R.id.btnHastalik);
        btngitTedavi = (Button) findViewById(R.id.btnTedavi);
        btngitRandevu = (Button) findViewById(R.id.btnRandevu);

        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
               SelectedImage();
            }
        });
        buttonNewPic.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectedImage();
            }
        });
        btngitAsi.setOnClickListener(this);
        btngitHastalik.setOnClickListener(this);
        btngitTedavi.setOnClickListener(this);
        btngitRandevu.setOnClickListener(this);

        String img_str=preferences.getString("userphoto", "");
        if (!img_str.equals("")){
            //decode string to image
            String base=img_str;
            byte[] imageAsBytes = Base64.decode(base.getBytes(), Base64.DEFAULT);
            imageView.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length) );
            imageView.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length) );
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLoadNewActivity = new Intent(PetInfoActivity.this, AskActivity.class);
                startActivity(intentLoadNewActivity);
                Snackbar.make(view, "Veterinere Sor", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
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
    private void SelectedImage(){
        final CharSequence[] items = {"Kamera", "Galeri", "İptal"};
        AlertDialog.Builder builder= new AlertDialog.Builder(PetInfoActivity.this);
        builder.setTitle("Resim Ekle");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(items[i].equals("Kamera")){
                    Intent intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                }else if (items[i].equals("Galeri")){
                    Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent.createChooser(intent, "Dosya Seç"), SELECT_FILE);

                }else if(items[i].equals("İptal")){
                    dialogInterface.dismiss();
                }

            }
        });
        builder.show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == REQUEST_CAMERA){
                Bundle bundle = data.getExtras();
                final Bitmap bmp= (Bitmap) bundle.get("data");
                imageView.setImageBitmap(bmp);
            }else if(requestCode == SELECT_FILE){
                Uri selectedImageUri = data.getData();
                imageView.setImageURI(selectedImageUri);
            }
        }
    }

    public void setProfileImage(View view){
        ImageView ivphoto = (ImageView)findViewById(R.id.imageToUpload);
        //code image to string
        ivphoto.buildDrawingCache();
        Bitmap bitmap = ivphoto.getDrawingCache();
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
        byte[] image=stream.toByteArray();
        //System.out.println("byte array:"+image);
        //final String img_str = "data:image/png;base64,"+ Base64.encodeToString(image, 0);
        //System.out.println("string:"+img_str);
        String img_str = Base64.encodeToString(image, 0);
        //decode string to image
        String base=img_str;
        byte[] imageAsBytes = Base64.decode(base.getBytes(), Base64.DEFAULT);
        ImageView ivsavedphoto = (ImageView)this.findViewById(R.id.imageToUpload);
        ivsavedphoto.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes,0, imageAsBytes.length) );
        //save in sharedpreferences
        SharedPreferences preferences = getSharedPreferences("myprefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("userphoto",img_str);
        editor.commit();
    }

}

