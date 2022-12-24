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
import android.util.Log;
import android.util.Patterns;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;

public class SignUpActivity extends AppCompatActivity {

    Button next;
    ImageView profile;
    EditText full_name;
    EditText email;
    EditText number;
    EditText address;
    EditText occupation;
    EditText password;
    EditText confirm_password;
    Button nextExperts;

    String imageEncoded;

    Uri selectedImageUrl;

    StorageReference storageReference;

    String Url = "https://collegeproject-a59bc-default-rtdb.firebaseio.com/user/";
    String Url2 = "https://www.googleapis.com/identitytoolkit/v3/relyingparty/signupNewUser?key=AIzaSyBJ06Ul1DQq2iv3nFULA4ltqzengGb8rAY";
    public static final int GET_FROM_GALLERY = 1;
    public static final String MY_PREF_NAME = "AgricultureExpertService";

    ProgressDialog progressDialog;
    Uri downloadImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        storageReference = FirebaseStorage.getInstance().getReference();
        getSupportActionBar().setTitle("Signup");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        next = findViewById(R.id.login_next);
        profile = findViewById(R.id.profile);
        full_name = findViewById(R.id.full_name);
        email = findViewById(R.id.email);
        number = findViewById(R.id.number);
        address = findViewById(R.id.address);
        occupation = findViewById(R.id.occupation);
        password = findViewById(R.id.password);
        confirm_password = findViewById(R.id.confirm_password);
        nextExperts = findViewById(R.id.login_experts_next);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

                startActivityForResult(chooserIntent, 1);

            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(SignUpActivity.this, "Please select", Toast.LENGTH_SHORT).show();

//            Intent intent = new Intent(SignUpActivity.this, DashboardActivity.class);
//            startActivity(intent);
                SignUp("farmer");
            }
        });


        nextExperts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUp("expert");
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                finish();
        }

        return true;
    }

    private void SignUp(final String types) {

        progressDialog = new ProgressDialog(SignUpActivity.this);
        progressDialog.setMessage("Loading");
        progressDialog.show();

        String emailTxt = email.getText().toString().trim();

        JSONObject object = new JSONObject();
        try {

            object.put("email", emailTxt);
            object.put("password", password.getText().toString().trim());
            object.put("returnSecureToken", false);
        } catch (Exception e) {

        }

        String fullNameTxt = full_name.getText().toString().trim();
        String numberTxt = number.getText().toString().trim();
        String addressTxt = address.getText().toString().trim();
        String occupationTxt = occupation.getText().toString().trim();

        if (selectedImageUrl == null) {
            progressDialog.dismiss();
            Toast.makeText(SignUpActivity.this, "Please select image", Toast.LENGTH_SHORT).show();
            return;
        } else if (fullNameTxt.isEmpty()) {
            progressDialog.dismiss();
            full_name.setError("Name is required");
            return;
        } else if (emailTxt.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(emailTxt).matches()) {
            progressDialog.dismiss();
            email.setError("Invalid email address");
            return;
        } else if (numberTxt.length() != 10) {
            progressDialog.dismiss();
            number.setError("Invalid phone number");
            return;
        } else if (addressTxt.isEmpty()) {
            progressDialog.dismiss();
            address.setError("Address is required");
            return;
        } else if (occupationTxt.isEmpty()) {
            progressDialog.dismiss();
            occupation.setError("Occupation is required");
            return;
        }

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "https://www.googleapis.com/identitytoolkit/v3/relyingparty/signupNewUser?key=AIzaSyBJ06Ul1DQq2iv3nFULA4ltqzengGb8rAY", object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                progressDialog.dismiss();
                Log.i("Jason Response", response.toString());

                try {

                    if (response.has("error")) {

                        Integer code = response.getInt("code");
                        String message = response.getString("message");
                        email.setError(message);

                    } else if (response.has("localId")) {

                        String localId = response.getString("localId");

                        SaveUserInformation(localId, types);

                    }

                } catch (Exception e) {
                    e.printStackTrace();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error.networkResponse.statusCode == 400) {

                    Log.i("network error res", error.networkResponse.data.toString());
                    email.setError("Email address already register.");

                } else {

                    error.printStackTrace();
                    Log.i("error msg", error.getLocalizedMessage());
                    Toast.makeText(SignUpActivity.this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }

                progressDialog.dismiss();


            }
        });

        requestQueue.add(jsonObjectRequest);

    }

    private void SaveUserInformation(final String localId, final String types) {

        progressDialog = new ProgressDialog(SignUpActivity.this);
        progressDialog.setMessage("Loading");
        progressDialog.show();

        if (selectedImageUrl == null) {

            progressDialog.dismiss();
            Toast.makeText(SignUpActivity.this, "Please select image", Toast.LENGTH_SHORT).show();
            return;
        }

        long time = Calendar.getInstance().getTimeInMillis();

        StorageReference riversRef = storageReference.child("images/" + time);

        //  Uri file = Uri.fromFile(new File(String.valueOf(selectedImage)));

        riversRef.putFile(selectedImageUrl)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        downloadImage = taskSnapshot.getDownloadUrl();

                        final JSONObject jsonObject = new JSONObject();
                        try {

                            jsonObject.put("fullName", full_name.getText().toString().trim());
                            jsonObject.put("email", email.getText().toString().trim());
                            jsonObject.put("phoneNumber", number.getText().toString().trim());
                            jsonObject.put("address", address.getText().toString().trim());
                            jsonObject.put("occupation", occupation.getText().toString().trim());
                            jsonObject.put("imageUrl", downloadImage);
                            jsonObject.put("userType", types);


                        } catch (Exception e) {

                        }

                        String saveUserInfoUrl = Url + localId + "/.json";

                        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, saveUserInfoUrl, jsonObject, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                progressDialog.dismiss();

                                try {


                                } catch (Exception e) {

                                    e.printStackTrace();
                                    Toast.makeText(SignUpActivity.this, "Error occured!", Toast.LENGTH_SHORT).show();

                                } finally {

                                    Toast.makeText(SignUpActivity.this, "Succcessfully Signup Account", Toast.LENGTH_SHORT).show();
                                    finish();
                                }


                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                progressDialog.dismiss();
                                Toast.makeText(SignUpActivity.this, "Error while saving user info", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {

            Uri selectedImage = data.getData();
            Bitmap bitmap = null;

            try {

                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);

                if (selectedImage != null) {
                    Glide.with(getApplicationContext()).load(selectedImage).into(profile);
                    this.selectedImageUrl = selectedImage;
                }


            } catch (FileNotFoundException e) {

                e.printStackTrace();
            } catch (IOException e) {

                e.printStackTrace();
            }


        }
    }

}
