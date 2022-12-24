package com.example.ashis.agricultureexpertsservice.View;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.ashis.agricultureexpertsservice.R;
import com.example.ashis.agricultureexpertsservice.utils.SharedPrefUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;

public class AddPostActivity extends AppCompatActivity {

    ImageView addImage;
    EditText title;
    EditText description;
    Button savePost;
    ImageView backgroundImage;

    public static final int PICK_IMAGES = 1;
    ProgressDialog progressDialog;

    String Url = "https://collegeproject-a59bc-default-rtdb.firebaseio.com/post/";
    StorageReference storageReference;
    Uri selectedImageUrl;
    Uri downloadImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);


        storageReference = FirebaseStorage.getInstance().getReference();
        addImage = findViewById(R.id.add_image);
        title = findViewById(R.id.add_title);
        description = findViewById(R.id.add_description);
        savePost = findViewById(R.id.save_post);
        backgroundImage = findViewById(R.id.background_image);

        getSupportActionBar().setTitle("Add Post");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"),PICK_IMAGES);

            }
        });

        savePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                savePostInformation();

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case android.R.id.home:
                finish();
        }

        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == PICK_IMAGES) {

            if (requestCode == PICK_IMAGES && resultCode == Activity.RESULT_OK) {

                Uri selectedImage = data.getData();
                Bitmap bitmap = null;

                try {

                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);

                    if (selectedImage != null) {
                        Glide.with(getApplicationContext()).load(selectedImage).into(backgroundImage);
                        selectedImageUrl = selectedImage;
                    }


                } catch (FileNotFoundException e) {

                    e.printStackTrace();
                } catch (IOException e) {

                    e.printStackTrace();
                }

            }
        }
    }

    private void savePostInformation(){

        progressDialog = new ProgressDialog(AddPostActivity.this);
        progressDialog.setMessage("Saving Your Post");
        progressDialog.show();


        if(selectedImageUrl == null){

            progressDialog.dismiss();
            Toast.makeText(AddPostActivity.this, "Please select image", Toast.LENGTH_SHORT).show();
            return;
        }

        long time = Calendar.getInstance().getTimeInMillis();

        StorageReference riversRef = storageReference.child("images/"+time);

        //  Uri file = Uri.fromFile(new File(String.valueOf(selectedImage)));

        riversRef.putFile(selectedImageUrl)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        downloadImage = taskSnapshot.getDownloadUrl();

                        final JSONObject object = new JSONObject();

                        try {

                            object.put("title",title.getText().toString().trim());
                            object.put("description",description.getText().toString().trim());
                            object.put("imageUrl",downloadImage);
                            object.put("uploadBy", SharedPrefUtils.getStringPreference(getApplicationContext(),"localId"));

                        }catch (Exception e){
                        }


                        String savePost = Url + "/.json";

                        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, savePost, object, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                progressDialog.dismiss();

                                try {

                                }catch (Exception e){

                                    e.printStackTrace();
                                    Toast.makeText(AddPostActivity.this, "Error Occured", Toast.LENGTH_SHORT).show();
                                }finally {

                                    Toast.makeText(AddPostActivity.this, "Successfully installed", Toast.LENGTH_SHORT).show();
                                    finish();
                                }


                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                progressDialog.dismiss();
                                Toast.makeText(AddPostActivity.this, "Error while saving your information.", Toast.LENGTH_SHORT).show();
                            }
                        });

                        requestQueue.add(objectRequest);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });


    }


}
