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
import android.widget.Spinner;
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

import static com.example.ashis.agricultureexpertsservice.utils.Config.BASE_URL_DB;

public class AddProductActivity extends AppCompatActivity {

    ImageView productImage;
    EditText pName;
    EditText pDesc;
    EditText pRate;
    EditText pQuantity;
    Spinner pTypes;
    Button saveProduct;

    public static final int PICK_IMAGES = 1;
    ProgressDialog progressDialog;
    String Url = BASE_URL_DB+"product/";

    Uri selectedImageUrl;
    Uri downloadImage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        storageReference = FirebaseStorage.getInstance().getReference();
        productImage = findViewById(R.id.product_image);
        pName = findViewById(R.id.p_name);
        pDesc = findViewById(R.id.p_des);
        pRate = findViewById(R.id.p_rate);
        pQuantity = findViewById(R.id.p_quantity);
        pTypes = findViewById(R.id.p_types);
        saveProduct = findViewById(R.id.save_product);

        getSupportActionBar().setTitle("Add Product");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        saveProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                savePostInformation();

            }
        });

        productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"),PICK_IMAGES);

            }
        });

    }

    private void validation(){

        if (pName.getText().toString().trim().isEmpty()){

            pName.setError("Enter product name");
        }else if (pDesc.getText().toString().trim().isEmpty()){
            pDesc.setError("Enter product description");
        }else if (pQuantity.getText().toString().trim().isEmpty()){
            pQuantity.setError("Enter product quantity");
        }else if (pRate.getText().toString().trim().isEmpty()){
            pRate.setError("Enter product Rate");
        }else {

            savePostInformation();
        }
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
                        Glide.with(getApplicationContext()).load(selectedImage).into(productImage);
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

        progressDialog = new ProgressDialog(AddProductActivity.this);
        progressDialog.setMessage("Saving Your Information");
        progressDialog.show();


        if(selectedImageUrl == null){

            progressDialog.dismiss();
            Toast.makeText(AddProductActivity.this, "Please select image", Toast.LENGTH_SHORT).show();
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

                        try{

                            object.put("title",pName.getText().toString().trim());
                            object.put("description",pDesc.getText().toString().trim());
                            object.put("totalQuantity",pQuantity.getText().toString().trim());
                            object.put("pricePerUnit",pRate.getText().toString().trim());
                            object.put("type",pTypes.getSelectedItem().toString().trim());
                            object.put("imageUrl",String.valueOf(downloadImage));
                            object.put("uploadBy", SharedPrefUtils.getStringPreference(getApplicationContext(),"localId"));


                        }catch (Exception e){
                        }

                        String saveProduct = Url + "/.json";

                        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, saveProduct, object, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                progressDialog.dismiss();
                                try {

                                }catch (Exception e){

                                    e.printStackTrace();
                                    Toast.makeText(AddProductActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                }finally {
                                    Toast.makeText(AddProductActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                                    finish();
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.dismiss();

                                Toast.makeText(AddProductActivity.this, "Error saving Information", Toast.LENGTH_SHORT).show();

                            }
                        });

                        requestQueue.add(jsonObjectRequest);


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
