package com.example.shahir.orderyourfood;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shahir.orderyourfood.R;
import com.facebook.FacebookSdk;
import com.facebook.accountkit.AccountKit;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {
    int test=0;
    /*Button btnSignIn;btnSignUp;*/
    Button btnContinue;
    TextView txtSlogan;

    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());

        AccountKit.initialize(this);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/restaurant_font.otf")
                .setFontAttrId(R.attr.fontPath)
                .build());

        setContentView(R.layout.activity_main);

        printKeyHash();


        /*btnSignIn=(Button)findViewById(R.id.btnSignIn);
        btnSignUp=(Button) findViewById(R.id.btnSignUp);*/

        btnContinue=(Button) findViewById(R.id.btn_continue);

        txtSlogan=(TextView) findViewById(R.id.txtSlogan);
        Typeface face=Typeface.createFromAsset(getAssets(),"fonts/NABILA.TTF");
        txtSlogan.setTypeface(face);


/*        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Hello",Toast.LENGTH_SHORT).show();
                Intent signIn=new Intent(MainActivity.this,SignIn.class);
                startActivity(signIn);
            }
        });*/


        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Welcome",Toast.LENGTH_SHORT).show();
                Intent signUp=new Intent(MainActivity.this,SignUp.class);
                startActivity(signUp);
            }
        });

    }

    private void printKeyHash() {
        try{
            PackageInfo info=getPackageManager().getPackageInfo("com.example.shahir.orderyourfood",
                    PackageManager.GET_SIGNATURES);

            for(Signature signature:info.signatures){
                MessageDigest md=MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(),Base64.DEFAULT));
            }
        }catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
