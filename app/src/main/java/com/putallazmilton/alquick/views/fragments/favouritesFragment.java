package com.putallazmilton.alquick.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.firebase.ui.database.FirebaseIndexRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.putallazmilton.alquick.views.descripcionActivity;

import java.util.ArrayList;
import java.util.Iterator;


public class favouritesFragment extends Fragment {


    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private RecyclerView rv;
    private DatabaseReference myRefFav;
    private DatabaseReference myRefAll;
    private FirebaseIndexRecyclerAdapter adapter;
    private FirebaseStorage storage;
    private StorageReference storageRef;

    private String key;


    public favouritesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.fragment_favourites, container, false);
        rv = (RecyclerView) view.findViewById(R.id.recyclerviewfav);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);


        rv.setHasFixedSize(true);
        myRefFav=databaseReference.child("likes").child(AccessToken.getCurrentAccessToken().getUserId()).getRef();
        myRefAll=databaseReference.child("usuarios").getRef();

        adapter = new FirebaseIndexRecyclerAdapter<Alquiler,AlquilerHolder>(Alquiler.class, R.layout.item, AlquilerHolder.class, myRefFav,myRefAll) {


            @Override
            protected void populateViewHolder(final AlquilerHolder alquilerHolder, final Alquiler alquiler, int position) {
                key = adapter.getRef(position).getKey().toString();
                alquilerHolder.setPropietario(alquiler.getPropietario());
                alquilerHolder.setId(key);
                alquilerHolder.getBotonstar().setChecked(true);
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
