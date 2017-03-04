package com.putallazmilton.alquick.views;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.putallazmilton.alquick.R;
import com.putallazmilton.alquick.adapters.CustomSwipeAdapter;
import com.putallazmilton.alquick.identitys.Alquiler;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class descripcionActivity extends AppCompatActivity {
    Alquiler alquiler;
    TextView tipo,direccion,ambientes,telefono,celular;
    ViewPager viewPager;
    CustomSwipeAdapter adapter;
    String key;
    FirebaseDatabase database;
    DatabaseReference myRef;
    ArrayList<String> urls;
    Context ctx;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descripcion);
        database= FirebaseDatabase.getInstance();
        myRef= database.getReference();
        ctx = this;
        key = getIntent().getStringExtra("key");
        alquiler =(Alquiler)getIntent().getExtras().get("alq");
        tipo = (TextView) findViewById(R.id.tipoalqdesc);
        direccion = (TextView) findViewById(R.id.direalqdesc);
        ambientes = (TextView) findViewById(R.id.ambalqdesc);
        telefono = (TextView) findViewById(R.id.telalqdesc);
        celular = (TextView) findViewById(R.id.celalqdesc);
        tipo.setText(alquiler.getTipo());
        ambientes.setText(String.valueOf(alquiler.getAmbientes()));
        direccion.setText(alquiler.getDireccion());
        telefono.setText(String.valueOf(alquiler.getTelefono()));
        celular.setText(String.valueOf(alquiler.getCelular()));
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        urls = new ArrayList<String>();


        myRef.child("urls").startAt(key,key).getRef().addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {

                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            if((data.getKey().equals(key+"_1")) ||(data.getKey().equals(key+"_2")) || (data.getKey().equals(key+"_3")) || (data.getKey().equals(key+"_4"))) {
                                String url = data.getValue(String.class);
                                urls.add(url);
                            }
                        }
                    } else {
                        // User does not exist at this point.
                    }

                    adapter  = new CustomSwipeAdapter(ctx,urls);
                    viewPager.setAdapter(adapter);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



    }


}
