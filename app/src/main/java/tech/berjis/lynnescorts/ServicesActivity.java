package tech.berjis.lynnescorts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ServicesActivity extends AppCompatActivity {
    ImageView back;

    RadioGroup inCallRadio, photosVideos, videoCalls;
    RadioButton inCallRadioBtn, photosVideosBtn, videoCallsBtn;

    FirebaseAuth mAuth;
    DatabaseReference dbRef;
    String UID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_services);

        mAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();
        UID = mAuth.getCurrentUser().getUid();
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

        loadServices();
        saveService();

    }

    private void loadServices() {
        dbRef.child("Services").child(UID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("InOutCalls").exists()) {
                    String serviceValue = dataSnapshot.child("InOutCalls").getValue().toString();

                    if (serviceValue.equals("Yes, I offer both")) {
                        inCallRadio.check(R.id.bothCalls);
                    }
                    if (serviceValue.equals("I ONLY offer INCALLS")) {
                        inCallRadio.check(R.id.inCalls);
                    }
                    if (serviceValue.equals("I ONLY offer OUTCALLS")) {
                        inCallRadio.check(R.id.outCalls);
                    }
                    if (serviceValue.equals("No, I don't offer any incalls or outcalls.")) {
                        inCallRadio.check(R.id.noCall);
                    }

                }

                if (dataSnapshot.child("Media").exists()) {
                    String serviceValue = dataSnapshot.child("Media").getValue().toString();

                    if (serviceValue.equals("Yes, I sell both photos and videos")) {
                        photosVideos.check(R.id.bothMedia);
                    }
                    if (serviceValue.equals("I ONLY sell PHOTOS")) {
                        photosVideos.check(R.id.photos);
                    }
                    if (serviceValue.equals("I ONLY sell VIDEOS")) {
                        photosVideos.check(R.id.videos);
                    }
                    if (serviceValue.equals("No, I don't sell digital media")) {
                        photosVideos.check(R.id.noMedia);
                    }
                }

                if (dataSnapshot.child("VideoCalls").exists()) {
                    String serviceValue = dataSnapshot.child("VideoCalls").getValue().toString();

                    if (serviceValue.equals("Yes, I do video calls with any paying clients")) {
                        videoCalls.check(R.id.yesVideoCalls);
                    }
                    if (serviceValue.equals("No, I don't do video calls")) {
                        videoCalls.check(R.id.noVideoCalls);
                    }
                }

                if (!dataSnapshot.child("InOutCalls").exists() ||
                        !dataSnapshot.child("Media").exists() ||
                        dataSnapshot.child("VideoCalls").exists()) {
                    dbRef.child("Services").child(UID).child("InOutCalls").setValue("Yes, I offer both");
                    dbRef.child("Services").child(UID).child("Media").setValue("Yes, I sell both photos and videos");
                    dbRef.child("Services").child(UID).child("VideoCalls").setValue("Yes, I do video calls with any paying clients");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void saveService() {
        inCallRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selectedId = inCallRadio.getCheckedRadioButtonId();
                inCallRadioBtn = findViewById(selectedId);
                String selected = inCallRadioBtn.getText().toString();

                dbRef.child("Services").child(UID).child("InOutCalls").setValue(selected);

                //Toast.makeText(ServicesActivity.this, inCallRadioBtn.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        photosVideos.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selectedId = photosVideos.getCheckedRadioButtonId();
                photosVideosBtn = findViewById(selectedId);
                String selected = photosVideosBtn.getText().toString();

                dbRef.child("Services").child(UID).child("Media").setValue(selected);

                //Toast.makeText(ServicesActivity.this, photosVideosBtn.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        videoCalls.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selectedId = videoCalls.getCheckedRadioButtonId();
                videoCallsBtn = findViewById(selectedId);
                String selected = videoCallsBtn.getText().toString();

                dbRef.child("Services").child(UID).child("VideoCalls").setValue(selected);


                //Toast.makeText(ServicesActivity.this, videoCallsBtn.getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
