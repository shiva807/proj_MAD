package com.example.bmseth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class warden_details extends Fragment {

    private FirebaseAuth mauth;
    private FirebaseUser user;
    private DatabaseReference ref,ref2;
    TextView hostel,name,contact;
    private String hname;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_warden_details);
        mauth=FirebaseAuth.getInstance();
        user=mauth.getCurrentUser();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_warden_details, container, false );
        hostel=(TextView)view.findViewById(R.id.textView47);
        name=(TextView)view.findViewById(R.id.textView48);
        contact=(TextView)view.findViewById(R.id.textView49);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        ref=FirebaseDatabase.getInstance().getReference("StudentsRoomDetails").child(user.getUid());

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                    hname=dataSnapshot.child("hostelno").getValue(String.class);
                hostel.setText(hname);
                ref2=FirebaseDatabase.getInstance().getReference("Hostels").child(hname);
                ref2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists())
                        {
                            String name1 = dataSnapshot.child("Warden name").getValue(String.class);
                            name.setText(name1);
                            String contact1 = dataSnapshot.child("Warden Contact").getValue(String.class);
                            contact.setText(contact1);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                Toast.makeText(getContext(),"ref2 ;"+ref2.toString(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //ref=FirebaseDatabase.getInstance().getReference("Hostels").child(hname);
        //Log.i("ref -",ref.toString());
       /* ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    String name1 = dataSnapshot.child("Warden name").getValue(String.class);
                    name.setText(name1);
                    String contact1 = dataSnapshot.child("Warden Contact").getValue(String.class);
                    contact.setText(contact1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/
    }
}
