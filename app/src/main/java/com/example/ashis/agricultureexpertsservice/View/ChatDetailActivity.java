package com.example.ashis.agricultureexpertsservice.View;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ashis.agricultureexpertsservice.Model.ChatDetail;
import com.example.ashis.agricultureexpertsservice.R;
import com.example.ashis.agricultureexpertsservice.ViewHolder.ChatDetailAdapter;
import com.example.ashis.agricultureexpertsservice.utils.SharedPrefUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static com.example.ashis.agricultureexpertsservice.View.SignUpActivity.GET_FROM_GALLERY;

public class ChatDetailActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<ChatDetail> chatDetailList = new ArrayList<>();
    public static final String CHAT_INFO_URL = "https://collegeproject-a59bc-default-rtdb.firebaseio.com/chat/";
    ChatDetailAdapter chatDetailAdapter;

    EditText sendMessageField;
    ImageView sendButton;
    String userId;
    ImageView collections;
    ImageView attachFiles;

    StorageReference storageReference;

    Uri downloadUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_detail);

        storageReference = FirebaseStorage.getInstance().getReference();
        userId = getIntent().getStringExtra("userId");
        recyclerView = findViewById(R.id.chat_detail_recycler_view);
        sendMessageField = findViewById(R.id.message_field);
        sendButton = findViewById(R.id.sendBtn);
        collections = findViewById(R.id.collections);
        attachFiles = findViewById(R.id.attach_files);

        getSupportActionBar().setTitle("Chat");

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);


                sendMessage();
            }
        });

        collections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
            }
        });

        chatDetailAdapter = new ChatDetailAdapter(getApplicationContext(), chatDetailList);

        recyclerView.setAdapter(chatDetailAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getChatDetail();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                finish();
                break;
        }

        return false;
    }


    private void getChatDetail() {

        chatDetailList.clear();

        String Url = CHAT_INFO_URL + SharedPrefUtils.getStringPreference(getApplicationContext(), "localId") + "/" + userId + "/.json";
        Log.i("chat url", Url);

        final RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, Url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Iterator<String> keys = response.keys();

                while (keys.hasNext()) {

                    String key = keys.next();

                    try {


                        if (response.get(key) instanceof JSONObject) {

                            JSONObject object = response.getJSONObject(key);

                            String sender_name = object.getString("sender_name");
                            String chat_time = object.getString("chattime");
                            String senderImageUrl = object.getString("sender_image_url");
                            String messagesType = object.getString("message_type");

                            ChatDetail chat = new ChatDetail();

                            chat.setChattime(chat_time);
                            chat.setSender_image_URL(senderImageUrl);
                            chat.setMessage_type(messagesType);
                            chat.setSender_name(sender_name);

                            if(object.has("message")){

                                String message = object.getString("message");
                                chat.setMessage(message);

                            }else{

                                chat.setMessage("");
                            }

                            if(object.has("message_image_url")){

                                chat.setMessageImageUrl(object.getString("message_image_url"));

                            }else{
                                chat.setMessageImageUrl("");
                            }

                            chatDetailList.add(chat);
                            chatDetailAdapter.notifyDataSetChanged();
                        }


                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(objectRequest);

    }

    private void sendMessage() {


        String Url = CHAT_INFO_URL + SharedPrefUtils.getStringPreference(getApplicationContext(), "localId") + "/" + userId + "/.json";

        if(downloadUrl == null && sendMessageField.getText().toString().trim().isEmpty()){

            sendMessageField.setError("Please enter message");
            return;
        }

        Calendar cal = Calendar.getInstance();
        Date currentLocalTime = cal.getTime();

        SimpleDateFormat fmtOut = new SimpleDateFormat("MMM, dd hh:mm");
        String localtime =  fmtOut.format(currentLocalTime);

        JSONObject param = new JSONObject();
        try{

            param.put("chattime",localtime);
            param.put("message",sendMessageField.getText().toString().trim());
            param.put("message_type","sent");
            param.put("sender_image_url",SharedPrefUtils.getStringPreference(getApplicationContext(),"imageUrl"));
            param.put("sender_name",SharedPrefUtils.getStringPreference(getApplicationContext(),"fullName"));

            if(downloadUrl != null){
                param.put("message_image_url",downloadUrl);
            }

        }catch (Exception ex){

        }


        final RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, Url, param, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                updateMessageOnReceiver();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(ChatDetailActivity.this, "Sending message fail", Toast.LENGTH_SHORT).show();

            }
        });

        queue.add(objectRequest);

    }


    private void updateMessageOnReceiver(){

        String Url = CHAT_INFO_URL + userId+"/" + SharedPrefUtils.getStringPreference(getApplicationContext(), "localId") + "/.json";

        if(downloadUrl == null && sendMessageField.getText().toString().trim().isEmpty()){

            sendMessageField.setError("Please enter message");
            return;
        }

        Calendar cal = Calendar.getInstance();
        Date currentLocalTime = cal.getTime();

        SimpleDateFormat fmtOut = new SimpleDateFormat("MMM, dd hh:mm");
        String localtime =  fmtOut.format(currentLocalTime);

        JSONObject param = new JSONObject();
        try{

            param.put("chattime",localtime);
            param.put("message",sendMessageField.getText().toString().trim());
            param.put("message_type","receive");
            param.put("sender_image_url",SharedPrefUtils.getStringPreference(getApplicationContext(),"imageUrl"));
            param.put("sender_name",SharedPrefUtils.getStringPreference(getApplicationContext(),"fullName"));

            if(downloadUrl != null){

                param.put("message_image_url",downloadUrl);
            }

        }catch (Exception ex){

        }


        final RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, Url, param, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Toast.makeText(ChatDetailActivity.this, "Message Send", Toast.LENGTH_SHORT).show();
                sendMessageField.setText("");
                getChatDetail();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(ChatDetailActivity.this, "Sending message fail", Toast.LENGTH_SHORT).show();

            }
        });

        queue.add(objectRequest);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {

            Uri selectedImage = data.getData();
            Bitmap bitmap = null;

            try {

                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);

                long time = Calendar.getInstance().getTimeInMillis();

                StorageReference riversRef = storageReference.child("images/"+time);

              //  Uri file = Uri.fromFile(new File(String.valueOf(selectedImage)));

                riversRef.putFile(selectedImage)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // Get a URL to the uploaded content
                                downloadUrl = taskSnapshot.getDownloadUrl();

                                sendMessage();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle unsuccessful uploads
                                // ...
                            }
                        });


            } catch (FileNotFoundException e) {

                e.printStackTrace();
            } catch (IOException e) {

                e.printStackTrace();
            }


        }



    }


}