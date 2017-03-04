package com.putallazmilton.alquick.views;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;


import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.putallazmilton.alquick.R;
import com.putallazmilton.alquick.identitys.Alquiler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static com.google.android.gms.tasks.Tasks.await;
import static com.google.android.gms.tasks.Tasks.whenAll;


public class agregarActivity extends AppCompatActivity {

    File photoFile1,photoFIle2,photoFile3,photoFile4= null;
    ProgressBar pb;
    ImageView iv1,iv2,iv3,iv4;
    String user;
    Spinner tvtipo;
    TextView tvdireccion,tvambientes,tvtelefono,tvcelular;
    boolean exito;
    String PathImagen1,PathImagen2,PathImagen3,PathImagen4;
    Button dialogcamera,dialoggalery;
    TextView idtipoalquiler;
    int clic;
    Dialog dialog;
    boolean c1,c2,c3,c4;
    File file1,file2,file3,file4;
    Button btnconfirmar;
    boolean continuar;
    int contador;
    FirebaseDatabase database;
    DatabaseReference myRef;
    String key;
    Alquiler alquiler;



    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);
         database= FirebaseDatabase.getInstance();
         myRef= database.getReference();
        tvdireccion = (TextView) findViewById(R.id.direccion);
        tvtipo = (Spinner) findViewById(R.id.tipo);
        idtipoalquiler = (TextView) findViewById(R.id.idtipoalquiler);
        continuar=true;
        tvambientes = (TextView) findViewById(R.id.ambientes);
        tvtelefono = (TextView) findViewById(R.id.tel);
        contador=1;
        tvcelular = (TextView) findViewById(R.id.cel);
        c1=false;
        c2=false;
        c3=false;
        c4=false;
        file1=null;
        file2=null;
        file3=null;
        file4=null;
        iv1= (ImageView)findViewById(R.id.imageview);
        iv2= (ImageView) findViewById(R.id.imageview1);
        iv3= (ImageView) findViewById(R.id.imageview2);
        iv4= (ImageView) findViewById(R.id.imageview3);
        String[] DayOfWeek = {"Casa","Depto","Hostel"};

        clic=0;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.row, R.id.tipodealquiler, DayOfWeek);
        tvtipo.setAdapter(adapter);




        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clic=1;
                mostrarAlert();
            }
        });

        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clic=2;
                mostrarAlert();
            }
        });

        iv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clic=3;
                mostrarAlert();
            }
        });

        iv4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                clic=4;
                mostrarAlert();
            }
        });
        pb = (ProgressBar) findViewById(R.id.progressBar2);
        PathImagen1="";
        PathImagen2="";
        PathImagen3="";
        PathImagen4="";




        btnconfirmar = (Button) findViewById(R.id.btnconfirmar);







        btnconfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                GraphRequest request = GraphRequest.newMeRequest(
                        AccessToken.getCurrentAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                try {
                                    if (!(PathImagen1=="" && PathImagen2=="" && PathImagen3=="" && PathImagen4=="")) {

                                            if(!tvambientes.getText().toString().trim().equals("")) {
                                                if (!tvdireccion.getText().toString().trim().equals("")) {
                                                    String tel,cel;
                                                    tel=tvtelefono.getText().toString().trim();
                                                    cel=tvcelular.getText().toString().trim();
                                                    if (!(tel.isEmpty() && cel.isEmpty())) {

                                                        tvdireccion.setVisibility(View.GONE);
                                                        tvtipo.setVisibility(View.GONE);
                                                        tvambientes.setVisibility(View.GONE);
                                                        tvtelefono.setVisibility(View.GONE);
                                                        tvcelular.setVisibility(View.GONE);
                                                        btnconfirmar.setVisibility(View.GONE);
                                                        pb.setVisibility(View.VISIBLE);
                                                        iv1.setVisibility(View.GONE);
                                                        iv2.setVisibility(View.GONE);
                                                        iv3.setVisibility(View.GONE);
                                                        iv4.setVisibility(View.GONE);
                                                        idtipoalquiler.setVisibility(View.GONE);


                                                        // StorageMetadata metadata = new StorageMetadata.Builder().setContentType("image/jpg").build();

                                                        String idface = AccessToken.getCurrentAccessToken().getUserId();

                                                        if (cel.isEmpty()){
                                                            alquiler = new Alquiler(tvdireccion.getText().toString(), tvtipo.getSelectedItem().toString(), Integer.parseInt(tvambientes.getText().toString()), Long.parseLong(tvtelefono.getText().toString()),object.getString("name"), idface);
                                                        } else if (tel.isEmpty()) {
                                                            alquiler = new Alquiler( Integer.parseInt(tvambientes.getText().toString()),tvdireccion.getText().toString(), tvtipo.getSelectedItem().toString(), Long.parseLong(tvcelular.getText().toString()),object.getString("name"), idface);
                                                        } else {
                                                            alquiler = new Alquiler(tvdireccion.getText().toString(), tvtipo.getSelectedItem().toString(), Integer.parseInt(tvambientes.getText().toString()), Long.parseLong(tvtelefono.getText().toString()),Long.parseLong(tvcelular.getText().toString()),object.getString("name"), idface);
                                                        }



                                                        key = myRef.push().getKey();




                                                        uploadImages(key);

                                                        myRef.child("usuarios").child(key).setValue(alquiler);
                                                        myRef.child("from").child(AccessToken.getCurrentAccessToken().getUserId()).child(key).setValue(key);





                                                    } else {
                                                        Toast.makeText(getApplicationContext(),"Debe ingresar un numero de contacto.",Toast.LENGTH_SHORT).show();
                                                    }
                                                } else {
                                                    Toast.makeText(getApplicationContext(),"Debe ingresar una direccion.",Toast.LENGTH_SHORT).show();
                                                }
                                            } else {
                                                Toast.makeText(getApplicationContext(),"Debe ingresar cantidad de ambientes.",Toast.LENGTH_SHORT).show();
                                            }

                                    } else {
                                        Toast.makeText(getApplicationContext(),"Debe ingresar al menos una imagen.",Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });


                request.executeAsync();



            }

        });

    }

    private void goToHome(){
       // Toast.makeText(getApplication().getApplicationContext(), "Se ha publicado correctamente!", Toast.LENGTH_LONG).show();


        finish();
    }


    private void uploadImages(String key){




        FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReferenceFromUrl("gs://alquick-9eab4.appspot.com");
        StorageReference photoRef = storageRef.child("images");
            if (!(PathImagen1.equals(""))) {

              String fileName = key + "_"+contador+".jpg";
                contador++;
                file1=new File(PathImagen1);
                Uri file = Uri.fromFile(file1);



                 photoRef.child(fileName).putFile(file)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                if(c1){
                                    file1.delete();
                                }

                                    goToHome();
                                String nombre = taskSnapshot.getMetadata().getName().split("\\.(?=[^\\.]+$)")[0];
                                String url = taskSnapshot.getDownloadUrl().toString();
                                myRef.child("urls").child(nombre).setValue(url);

                            }
                        });


            }
            if (!(PathImagen2.equals(""))) {
                String fileName = key + "_"+contador+".jpg";
                contador++;
                file2=new File(PathImagen2);
                Uri file = Uri.fromFile(file2);


             photoRef.child(fileName).putFile(file)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    if(c2){
                                        file2.delete();
                                    }

                                        goToHome();
                                    String nombre = taskSnapshot.getMetadata().getName().split("\\.(?=[^\\.]+$)")[0];
                                    String url = taskSnapshot.getDownloadUrl().toString();
                                    myRef.child("urls").child(nombre).setValue(url);
                                }
                            });

            }
            if (!(PathImagen3.equals(""))) {
                String fileName = key + "_"+contador+".jpg";
                contador++;
                file3=new File(PathImagen3);
                Uri file = Uri.fromFile(file3);





                 photoRef.child(fileName).putFile(file)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    if(c3){
                                        file3.delete();
                                    }

                                        goToHome();
                                    String nombre = taskSnapshot.getMetadata().getName().split("\\.(?=[^\\.]+$)")[0];
                                    String url = taskSnapshot.getDownloadUrl().toString();
                                    myRef.child("urls").child(nombre).setValue(url);

                                }
                            });

            }
            if (!(PathImagen4.equals(""))) {
                String fileName = key + "_"+contador+".jpg";
                contador++;
                file4=new File(PathImagen4);
                Uri file = Uri.fromFile(file4);






               photoRef.child(fileName).putFile(file)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    if(c4){
                                        file4.delete();
                                    }

                                        goToHome();
                                    String nombre = taskSnapshot.getMetadata().getName().split("\\.(?=[^\\.]+$)")[0];
                                    String url = taskSnapshot.getDownloadUrl().toString();
                                    myRef.child("urls").child(nombre).setValue(url);


                                }
                            });

            }











    }


    private void mostrarAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater =getLayoutInflater();


        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
         dialog= builder.setView(inflater.inflate(R.layout.dialog, null)).create();

        dialog.show();
        dialogcamera= (Button) dialog.findViewById(R.id.dialogcamera);
        dialoggalery= (Button) dialog.findViewById(R.id.dialoggalery);
        dialoggalery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                galleryIntent();
            }
        });

        dialogcamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraIntent();
            }
        });

    }


 private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),1);
        dialog.dismiss();
    }


    private void cameraIntent()
    {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        File file = new File(Environment.getExternalStorageDirectory()+File.separator + "image"+clic+".jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));

        startActivityForResult(intent, 0);
        dialog.dismiss();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                if(clic==1){
                    iv1.setImageBitmap(onSelectFromGalleryResult(data));
                    iv1.setScaleType(ImageView.ScaleType.CENTER_CROP);

                } else
                if (clic==2){
                    iv2.setImageBitmap(onSelectFromGalleryResult(data));
                    iv2.setScaleType(ImageView.ScaleType.CENTER_CROP);
                } else
                if (clic==3){
                    iv3.setImageBitmap(onSelectFromGalleryResult(data));
                    iv3.setScaleType(ImageView.ScaleType.CENTER_CROP);
                } else
                if (clic==4){
                    iv4.setImageBitmap(onSelectFromGalleryResult(data));
                    iv4.setScaleType(ImageView.ScaleType.CENTER_CROP);
                }



            }
            else if (requestCode == 0) {

                if(clic==1){
                    PathImagen1=Environment.getExternalStorageDirectory()+File.separator + "image1.jpg";
                    File file = new File(PathImagen1);
                    iv1.setImageBitmap(decodeSampledBitmapFromFile(file.getAbsolutePath(), 1000, 700));
                    iv1.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    c1=true;
                } else
                if (clic==2){
                    PathImagen2=Environment.getExternalStorageDirectory()+File.separator + "image2.jpg";
                    File file = new File(PathImagen2);
                    iv2.setImageBitmap(decodeSampledBitmapFromFile(file.getAbsolutePath(), 1000, 700));
                    iv2.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    c2=true;
                } else
                if (clic==3){
                    PathImagen3=Environment.getExternalStorageDirectory()+File.separator + "image3.jpg";
                    File file = new File(PathImagen3);
                    iv3.setImageBitmap(decodeSampledBitmapFromFile(file.getAbsolutePath(), 1000, 700));
                    iv3.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    c3=true;
                } else
                if (clic==4){
                    PathImagen4=Environment.getExternalStorageDirectory()+File.separator + "image4.jpg";
                    File file = new File(PathImagen4);
                    iv4.setImageBitmap(decodeSampledBitmapFromFile(file.getAbsolutePath(), 1000, 700));
                    iv4.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    c4=true;
                }



            }
        }
    }



    public static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight)
    { // BEST QUALITY MATCH

        //First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize, Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        int inSampleSize = 1;

        if (height > reqHeight)
        {
            inSampleSize = Math.round((float)height / (float)reqHeight);
        }
        int expectedWidth = width / inSampleSize;

        if (expectedWidth > reqWidth)
        {
            //if(Math.round((float)width / (float)reqWidth) > inSampleSize) // If bigger SampSize..
            inSampleSize = Math.round((float)width / (float)reqWidth);
        }

        options.inSampleSize = inSampleSize;

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(path, options);
    }



    @SuppressWarnings("deprecation")
    private Bitmap onSelectFromGalleryResult(Intent data) {
        Bitmap bmp=null;

        if (data != null) {
            try {
                bmp = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                if(clic==1){
                    PathImagen1= getRealPathFromURI(data.getData());
                } else
                if (clic==2){
                    PathImagen2= getRealPathFromURI(data.getData());
                } else
                if (clic==3){
                    PathImagen3= getRealPathFromURI(data.getData());
                } else
                if (clic==4){
                    PathImagen4= getRealPathFromURI(data.getData());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return bmp;
    }

    public String getRealPathFromURI(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
}