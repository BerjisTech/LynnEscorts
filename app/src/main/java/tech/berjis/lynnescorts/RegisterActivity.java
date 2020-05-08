package tech.berjis.lynnescorts;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hbb20.CountryCodePicker;

public class RegisterActivity extends AppCompatActivity {


    TextView sendPhone;
    CountryCodePicker ccp;
    EditText mPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mPhone = findViewById(R.id.userphone);
        ccp = findViewById(R.id.country_code);
        sendPhone = findViewById(R.id.sendPhone);


        sendPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile = mPhone.getText().toString().trim();

                if(mobile.isEmpty() || mobile.length() < 9){
                    mPhone.setError("Enter a valid mobile");
                    mPhone.requestFocus();
                    return;
                }

                Intent intent = new Intent(RegisterActivity.this, VerifyPhoneActivity.class);
                intent.putExtra("mobile", mobile);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}