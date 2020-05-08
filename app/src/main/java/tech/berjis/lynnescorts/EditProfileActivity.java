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
    TextView updateProfile;
    EditText userEmail, userName, firstName, lastName;

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
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void updateProfile() {

        String user_email = userEmail.getText().toString();
        final String user_name = userName.getText().toString();
        String first_name = firstName.getText().toString();
        String last_name = lastName.getText().toString();

        dbRef.child("Users").child(UID).child("user_email").setValue(user_email);
        dbRef.child("Users").child(UID).child("user_phone").setValue(U_Phone.substring(U_Phone.length() - 12));
        dbRef.child("Users").child(UID).child("user_image").setValue("");

        if(user_name.isEmpty()){
            userName.setError("You need a user name", getDrawable(android.R.drawable.ic_dialog_alert));
            userName.requestFocus();
            return;
        }

        dbRef.child("Users").child(UID).child("user_name").setValue(user_name);

        if(first_name.isEmpty()){
            firstName.setError("This field is required", getDrawable(android.R.drawable.ic_dialog_alert));
            firstName.requestFocus();
            return;
        }

        dbRef.child("Users").child(UID).child("first_name").setValue(first_name);

        if(last_name.isEmpty()){
            lastName.setError("This field is required", getDrawable(android.R.drawable.ic_dialog_alert));
            lastName.requestFocus();
            return;
        }

        dbRef.child("Users").child(UID).child("last_name").setValue(last_name);

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
                }
                else{
                    EditProfileActivity.super.finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
