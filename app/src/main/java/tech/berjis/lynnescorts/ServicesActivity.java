package tech.berjis.lynnescorts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ServicesActivity extends AppCompatActivity {
    ImageView back;

    RadioGroup inCallRadio, photosVideos, videoCalls;
    RadioButton inCallRadioBtn, photosVideosBtn, videoCallsBtn;

    FirebaseAuth mAuth;
    DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        mAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();
        dbRef.keepSynced(true);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServicesActivity.super.finish();
            }
        });

        inCallRadio = findViewById(R.id.inCallRadio);
        photosVideos = findViewById(R.id.photosVideos);
        videoCalls = findViewById(R.id.videoCalls);

        inCallRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selectedId = inCallRadio.getCheckedRadioButtonId();
                inCallRadioBtn = findViewById(selectedId);
                Toast.makeText(ServicesActivity.this, inCallRadioBtn.getText(), Toast.LENGTH_SHORT).show();
                /*switch(checkedId)
                {
                    case R.id.firstRadioButton:
                        //Implement logic
                        break;
                    case R.id.secondRadioButton:
                        //Implement logic
                        break;
                }*/
            }
        });

        photosVideos.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selectedId = photosVideos.getCheckedRadioButtonId();
                photosVideosBtn = findViewById(selectedId);
                Toast.makeText(ServicesActivity.this, photosVideosBtn.getText(), Toast.LENGTH_SHORT).show();
                /*switch(checkedId)
                {
                    case R.id.firstRadioButton:
                        //Implement logic
                        break;
                    case R.id.secondRadioButton:
                        //Implement logic
                        break;
                }*/
            }
        });

        videoCalls.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selectedId = videoCalls.getCheckedRadioButtonId();
                videoCallsBtn = findViewById(selectedId);
                Toast.makeText(ServicesActivity.this, videoCallsBtn.getText(), Toast.LENGTH_SHORT).show();
                /*switch(checkedId)
                {
                    case R.id.firstRadioButton:
                        //Implement logic
                        break;
                    case R.id.secondRadioButton:
                        //Implement logic
                        break;
                }*/
            }
        });
    }
}
