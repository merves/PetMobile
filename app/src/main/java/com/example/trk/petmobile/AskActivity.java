package com.example.trk.petmobile;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class AskActivity extends AppCompatActivity {

    String number= "0123456789" ; //after connection get number from database
    String mail ="a@gmail.com";
    String message;

    EditText etdetail;
    ImageButton ibaddimg;
    ImageButton ibaddvoice ;
    ImageButton ibaddfile;
    ImageButton ibcall;
    ImageButton ibmsg ;
    ImageButton ibmail;
    ImageButton ibask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask);

        etdetail = (EditText) findViewById(R.id.etdetay);
        ibaddimg = (ImageButton) findViewById(R.id.imbaddimg);
        ibaddvoice = (ImageButton) findViewById(R.id.imbaddvoice);
        ibaddfile = (ImageButton) findViewById(R.id.imbaddfile);
        ibcall = (ImageButton) findViewById(R.id.imbcall);
        ibmsg = (ImageButton) findViewById(R.id.imbmsg);
        ibmail = (ImageButton) findViewById(R.id.imbemail);
        ibask = (ImageButton) findViewById(R.id.imbfask);

        ibaddimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ibaddvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ibaddfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ibcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callVeterinary();

            }
        });
        ibmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
        ibmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
            }
        });
        ibask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askAllVet();
            }
        });
    }

    private void askAllVet() {
        // send question to all veterinary
    }

    private void sendMessage() {
        message= etdetail.getText().toString();

        Intent intent2 = new Intent(Intent.ACTION_VIEW);
        intent2.setData(Uri.parse("smsto:"+number));
        intent2.putExtra("sms_body",message);
        startActivity(intent2);

    }


    private void callVeterinary() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("tel:"+number));

        startActivity(intent);
    }


    private void sendMail() {
        /*String recipientList = mEditTextTo.getText().toString();
        String[] recipients = recipientList.split(",");*/

        message= etdetail.getText().toString();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, mail);
        //intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Choose an email client"));
    }
}
