package com.putallazmilton.alquick.views.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StringLoader;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FacebookAuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.putallazmilton.alquick.R;

import com.putallazmilton.alquick.adapters.AlquilerHolder;
import com.putallazmilton.alquick.adapters.CustomSwipeAdapter;
import com.putallazmilton.alquick.identitys.Alquiler;
import com.putallazmilton.alquick.views.LoginActivity;
import com.putallazmilton.alquick.views.agregarActivity;
import com.putallazmilton.alquick.views.descripcionActivity;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Iterator;

import static com.putallazmilton.alquick.R.layout.activity_descripcion;
import static com.putallazmilton.alquick.R.layout.item;

/**
 * Created by Milton on 24/01/2017.
 */

public class homeFragment extends Fragment {

    private RecyclerView rv;
    private StorageReference storageRef;
    private FirebaseStorage storage;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private String key;
    private FirebaseRecyclerAdapter adapter;







    ArrayList<Alquiler> problemas =new ArrayList<>();

    public homeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database  = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl("gs://alquick-9eab4.appspot.com");




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_home, container, false);











        rv= (RecyclerView) view.findViewById(R.id.recyclerviewhome);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);


        rv.setHasFixedSize(true);


        adapter = new FirebaseRecyclerAdapter<Alquiler, AlquilerHolder>(Alquiler.class, R.layout.item, AlquilerHolder.class, databaseReference.child("usuarios").getRef()) {


            @Override
            public void populateViewHolder(final AlquilerHolder alquilerHolder, final Alquiler alquiler, int position) {

                key = adapter.getRef(position).getKey().toString();

                alquilerHolder.setId(key);

                databaseReference.child("likes").child(AccessToken.getCurrentAccessToken().getUserId()).child(alquilerHolder.getId().getText().toString()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            alquilerHolder.getBotonstar().setChecked(true);
                        } else {
                            alquilerHolder.getBotonstar().setChecked(false);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                alquilerHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent inte = new Intent(getContext(),descripcionActivity.class);
                        inte.putExtra("alq",alquiler);

                        inte.putExtra("key",alquilerHolder.getId().getText());

                        startActivity(inte);
                    }
                });
                alquilerHolder.setTipoAlq(alquiler.getTipo());
                alquilerHolder.getBotonstar().setText(null);
                alquilerHolder.getBotonstar().setTextOn(null);
                alquilerHolder.getBotonstar().setTextOff(null);
                alquilerHolder.getBotonstar().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(alquilerHolder.getBotonstar().isChecked()){
                            databaseReference.child("likes").child(AccessToken.getCurrentAccessToken().getUserId()).child(alquilerHolder.getId().getText().toString()).setValue(alquilerHolder.getId().getText().toString());
                        }
                        if (!alquilerHolder.getBotonstar().isChecked()){
                            databaseReference.child("likes").child(AccessToken.getCurrentAccessToken().getUserId()).child(alquilerHolder.getId().getText().toString()).removeValue();

                        }

                    }
                });


                storageRef.child("images/"+key+"_1.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(getContext()).load(uri.toString()).into(alquilerHolder.getImage());
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                storageRef.child("images/"+key+"_2.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Glide.with(getContext()).load(uri.toString()).into(alquilerHolder.getImage());
                                    }
                                })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                storageRef.child("images/"+key+"_3.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {
                                                        Glide.with(getContext()).load(uri.toString()).into(alquilerHolder.getImage());
                                                    }
                                                })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                storageRef.child("images/"+key+"_4.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                    @Override
                                                                    public void onSuccess(Uri uri) {
                                                                        Glide.with(getContext()).load(uri.toString()).into(alquilerHolder.getImage());
                                                                    }
                                                                })
                                                                        .addOnFailureListener(new OnFailureListener() {
                                                                            @Override
                                                                            public void onFailure(@NonNull Exception e) {
                                                                                Glide.clear(alquilerHolder.getImage());
                                                                            }
                                                                        });
                                                            }
                                                        });
                                            }
                                        });
                            }
                        });

            }
        };



        rv.setLayoutManager(llm);



       rv.setAdapter(adapter);


        return view;


    }

}

