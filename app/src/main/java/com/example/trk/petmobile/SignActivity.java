package com.example.trk.petmobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class SignActivity extends AppCompatActivity implements TextWatcher, CompoundButton.OnCheckedChangeListener {

    private EditText Name;
    private EditText Password;
    private TextView Info;
    private Button Login;
    private Button Register;
    private int counter = 5;
    private CheckBox rem_userpass;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static final String PREF_NAME = "prefs";
    private static final String KEY_REMEMBER = "remember";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASS = "password";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_sign);

            sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
            Name = (EditText)findViewById(R.id.etName);
            Password = (EditText)findViewById(R.id.etPassword);
            rem_userpass = (CheckBox)findViewById(R.id.rememberme);

            if(sharedPreferences.getBoolean(KEY_REMEMBER, false))
                rem_userpass.setChecked(true);
            else
                rem_userpass.setChecked(false);

            Name.setText(sharedPreferences.getString(KEY_USERNAME,""));
            Password.setText(sharedPreferences.getString(KEY_PASS,""));

            Name.addTextChangedListener(this);
            Password.addTextChangedListener(this);
            rem_userpass.setOnCheckedChangeListener(this);


           // Name = (EditText)findViewById(R.id.etName);
           // Password = (EditText)findViewById(R.id.etPassword);
            Info = (TextView)findViewById(R.id.tvInfo);
            Login = (Button)findViewById(R.id.btnLogin);
            Register = (Button) findViewById(R.id.btnregister) ;

            Info.setText("No of attempts remaining: 5");

            Login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    validate(Name.getText().toString(), Password.getText().toString());
                }
            });
            Register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intentLoadNewActivity = new Intent(SignActivity.this, RegisterActivity.class);
                    startActivity(intentLoadNewActivity);}
            });

        }

    private void validate(String userName, String userPassword){
        if((userName.equals("admin")) && (userPassword.equals("1234"))){
            Intent intent = new Intent(SignActivity.this, MenuActivity.class);
            startActivity(intent);
        }else{
            counter--;

            Info.setText("No of attempts remaining: " + String.valueOf(counter));

            if(counter == 0){
                Login.setEnabled(false);
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        managePrefs();
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        managePrefs();
    }
    private void managePrefs(){
        if(rem_userpass.isChecked()){
            editor.putString(KEY_USERNAME, Name.getText().toString().trim());
            editor.putString(KEY_PASS, Password.getText().toString().trim());
            editor.putBoolean(KEY_REMEMBER, true);
            editor.apply();
        }else{
            editor.putBoolean(KEY_REMEMBER, false);
            editor.remove(KEY_PASS);//editor.putString(KEY_PASS,"");
            editor.remove(KEY_USERNAME);//editor.putString(KEY_USERNAME, "");
            editor.apply();
        }
    }
}
