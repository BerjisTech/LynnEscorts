package tech.berjis.lynnescorts;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class EditProfileActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    DatabaseReference dbRef;
    String UID, U_Phone;

    ImageView back;
    TextView updateProfile, services, pricing;
    EditText userEmail, userName, firstName, lastName, userDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        mAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();
        UID = mAuth.getCurrentUser().getUid();
        U_Phone = mAuth.getCurrentUser().getPhoneNumber();
        dbRef.keepSynced(true);


        back = findViewById(R.id.back);
        userEmail = findViewById(R.id.userEmail);
        userName = findViewById(R.id.userName);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        updateProfile = findViewById(R.id.updateProfile);
        userDescription = findViewById(R.id.userDescription);
        services = findViewById(R.id.services);
        pricing = findViewById(R.id.pricing);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        updateProfile.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });

        loadUserData();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void updateProfile() {

        String user_email = userEmail.getText().toString();
        String user_name = userName.getText().toString();
        String first_name = firstName.getText().toString();
        String last_name = lastName.getText().toString();
        String user_description = userDescription.getText().toString();

        dbRef.child("Users").child(UID).child("user_phone").setValue(U_Phone.substring(U_Phone.length() - 12));
        dbRef.child("Users").child(UID).child("user_image").setValue("");

        if (user_name.isEmpty()) {
            userName.setError("You need a user name", getDrawable(android.R.drawable.ic_dialog_alert));
            userName.requestFocus();
            return;
        }

        dbRef.child("Users").child(UID).child("user_name").setValue(user_name);

        if (first_name.isEmpty()) {
            firstName.setError("This field is required", getDrawable(android.R.drawable.ic_dialog_alert));
            firstName.requestFocus();
            return;
        }

        dbRef.child("Users").child(UID).child("first_name").setValue(first_name);

        if (last_name.isEmpty()) {
            lastName.setError("This field is required", getDrawable(android.R.drawable.ic_dialog_alert));
            lastName.requestFocus();
            return;
        }

        dbRef.child("Users").child(UID).child("last_name").setValue(last_name);

        if (user_email.isEmpty()) {
            userEmail.setError("This field is required to process your payments", getDrawable(android.R.drawable.ic_dialog_alert));
            userEmail.requestFocus();
            return;
        }

        dbRef.child("Users").child(UID).child("user_email").setValue(user_email);

        if (user_description.isEmpty()) {
            userDescription.setError("This field is required", getDrawable(android.R.drawable.ic_dialog_alert));
            userDescription.requestFocus();
            return;
        }

        dbRef.child("Users").child(UID).child("user_description").setValue(user_description);

        Toast.makeText(this, "Profile Successfully Update", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        dbRef.child("Users").child(UID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.child("first_name").exists() ||
                        !dataSnapshot.child("last_name").exists() ||
                        !dataSnapshot.child("user_name").exists()) {
                    new AlertDialog
                            .Builder(EditProfileActivity.this)
                            .setTitle("Incomplete Profile")
                            .setMessage("You have a few important profile details that you haven't updated")
                            .setPositiveButton("Update Now", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    EditProfileActivity.super.finish();
                                }
                            })
                            .setNegativeButton("Later", null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                } else {
                    EditProfileActivity.super.finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loadUserData() {
        dbRef.child("Users").child(UID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("first_name").exists()) {
                    firstName.setText(dataSnapshot.child("first_name").getValue().toString());
                }
                if (dataSnapshot.child("last_name").exists()) {
                    lastName.setText(dataSnapshot.child("last_name").getValue().toString());
                }
                if (dataSnapshot.child("user_name").exists()) {
                    userName.setText(dataSnapshot.child("user_name").getValue().toString());
                }
                if (dataSnapshot.child("user_email").exists()) {
                    userEmail.setText(dataSnapshot.child("user_email").getValue().toString());
                }
                if (dataSnapshot.child("user_description").exists()) {
                    userDescription.setText(dataSnapshot.child("user_description").getValue().toString());
                }

                if (dataSnapshot.child("first_name").exists() &&
                        dataSnapshot.child("last_name").exists() &&
                        dataSnapshot.child("user_name").exists() &&
                        dataSnapshot.child("user_email").exists()) {
                    services.setText(HtmlCompat.fromHtml("Add your <strong>services</strong> ", HtmlCompat.FROM_HTML_MODE_LEGACY));
                    pricing.setText(HtmlCompat.fromHtml("or set up you <strong>pricing</strong>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                    services.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(EditProfileActivity.this, ServicesActivity.class));
                        }
                    });
                    pricing.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(EditProfileActivity.this, PricingActivity.class));
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
