package com.example.ashis.agricultureexpertsservice.View;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ashis.agricultureexpertsservice.Model.Chat;
import com.example.ashis.agricultureexpertsservice.Model.ChatExpert;
import com.example.ashis.agricultureexpertsservice.Model.User;
import com.example.ashis.agricultureexpertsservice.R;
import com.example.ashis.agricultureexpertsservice.ViewHolder.ChatAdapter;
import com.example.ashis.agricultureexpertsservice.utils.SharedPrefUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


public class ChatFragment extends Fragment {

    List<Chat> chatList = new ArrayList<>();
    ChatExpert chatExpert;
    public static final String CHAT_INFO_URL = "https://collegeproject-a59bc-default-rtdb.firebaseio.com/chat/";
    ChatAdapter chatAdapter;
    List<User> userData = new ArrayList<>();

    ProgressDialog progressDialog;
    TextView createNewMessage;

    String userId;

    User selectedUser;

    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        createNewMessage = view.findViewById(R.id.request);

        createNewMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.create_new_chat_dialog_layout);
                Window window = dialog.getWindow();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


                final AutoCompleteTextView autoCompleteTextView = dialog.findViewById(R.id.search_user_field);

                Log.i("user data",userData.toString());
                ArrayAdapter<User> adapter = new ArrayAdapter<User>
                        (getContext(), android.R.layout.select_dialog_item, userData);

                autoCompleteTextView.setAdapter(adapter);


                autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        selectedUser = (User) parent.getItemAtPosition(position);
                        userId = userData.get(position).getUserId();
                    }
                });

                Button dialogButton = (Button) dialog.findViewById(R.id.sendMessage);
                final EditText message = dialog.findViewById(R.id.message);
                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();

                        sendMessage(message.getText().toString().trim());


                    }
                });
                dialog.show();

            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.chat_recycler_view);

         chatAdapter = new ChatAdapter(getContext(),chatList);

        recyclerView.setAdapter(chatAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

       getUserData();

        return view;

    }

    private void sendMessage(final String message) {

        String Url = CHAT_INFO_URL + SharedPrefUtils.getStringPreference(getContext(), "localId") + "/" + selectedUser.getUserId() + "/.json";

        if(message.isEmpty()){

            Toast.makeText(getActivity(), "Message is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Sending message");
        progressDialog.show();


        Calendar cal = Calendar.getInstance();
        Date currentLocalTime = cal.getTime();

        SimpleDateFormat fmtOut = new SimpleDateFormat("MMM, dd hh:mm");
        String localtime =  fmtOut.format(currentLocalTime);

        JSONObject param = new JSONObject();
        try{

            param.put("chattime",localtime);
            param.put("message",message);
            param.put("message_type","sent");
            param.put("sender_image_url",SharedPrefUtils.getStringPreference(getContext(),"imageUrl"));
            param.put("sender_name",SharedPrefUtils.getStringPreference(getContext(),"fullName"));

        }catch (Exception ex){

        }


        final RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, Url, param, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                updateLastMsg(message);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Sending message fail", Toast.LENGTH_SHORT).show();

            }
        });

        queue.add(objectRequest);

    }

    private void updateLastMsg(final String message){

        String Url = CHAT_INFO_URL + SharedPrefUtils.getStringPreference(getContext(), "localId") + "/" + selectedUser.getUserId() + "/.json";


        Calendar cal = Calendar.getInstance();
        Date currentLocalTime = cal.getTime();

        SimpleDateFormat fmtOut = new SimpleDateFormat("MMM, dd hh:mm");
        String localtime =  fmtOut.format(currentLocalTime);

        JSONObject param = new JSONObject();
        try{

            param.put("last_message",message);
            param.put("last_message_time",localtime);
            param.put("message_by_image",selectedUser.getImageUrl());
            param.put("message_by",selectedUser.getFullName());

        }catch (Exception ex){

        }


        final RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.PATCH, Url, param, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                updateMessageOnReceiver(message);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Sending message fail", Toast.LENGTH_SHORT).show();

            }
        });

        queue.add(objectRequest);

    }


    private void updateMessageOnReceiver(final String message){

        String Url = CHAT_INFO_URL + selectedUser.getUserId()+"/" + SharedPrefUtils.getStringPreference(getActivity(), "localId") + "/.json";


        Calendar cal = Calendar.getInstance();
        Date currentLocalTime = cal.getTime();

        SimpleDateFormat fmtOut = new SimpleDateFormat("MMM, dd hh:mm");
        String localtime =  fmtOut.format(currentLocalTime);

        JSONObject param = new JSONObject();
        try{

            param.put("chattime",localtime);
            param.put("message",message);
            param.put("message_type","receive");
            param.put("sender_image_url",SharedPrefUtils.getStringPreference(getContext(),"imageUrl"));
            param.put("sender_name",SharedPrefUtils.getStringPreference(getContext(),"fullName"));


        }catch (Exception ex){

        }


        final RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, Url, param, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                updateLastReceiverMsg(message);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Sending message fail", Toast.LENGTH_SHORT).show();

            }
        });

        queue.add(objectRequest);


    }

    private void updateLastReceiverMsg(final String message){

        String Url = CHAT_INFO_URL + selectedUser.getUserId()+"/" + SharedPrefUtils.getStringPreference(getActivity(), "localId") + "/.json";


        Calendar cal = Calendar.getInstance();
        Date currentLocalTime = cal.getTime();

        SimpleDateFormat fmtOut = new SimpleDateFormat("MMM, dd hh:mm");
        String localtime =  fmtOut.format(currentLocalTime);

        JSONObject param = new JSONObject();
        try{

            param.put("last_message",message);
            param.put("last_message_time",localtime);
            param.put("message_by_image",SharedPrefUtils.getStringPreference(getContext(),"imageUrl"));
            param.put("message_by",SharedPrefUtils.getStringPreference(getContext(),"fullName"));

        }catch (Exception ex){

        }


        final RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.PATCH, Url, param, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Toast.makeText(getActivity(), "Message Send", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

                getChatData();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Sending message fail", Toast.LENGTH_SHORT).show();

            }
        });

        queue.add(objectRequest);

    }

    private void getChatData(){

        String Url = CHAT_INFO_URL + SharedPrefUtils.getStringPreference(getContext(),"localId")+ "/.json";

        Log.i("chat url",Url);

        chatList.clear();

        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, Url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                progressDialog.dismiss();

                Log.i("chat data",response.toString());

                Iterator<String> keys = response.keys();

                Log.i("keys", keys.toString());

                while (keys.hasNext()) {

                    String key = keys.next();

                    try {

                        if (response.get(key) instanceof JSONObject) {

                            JSONObject resultObj = response.getJSONObject(key);

                            String time = resultObj.getString("last_message_time");
                            String message = resultObj.getString("last_message");
                            String sender_imageUrl = resultObj.getString("message_by_image");
                            String sender_name = resultObj.getString("message_by");


                            Log.i("image Url", sender_imageUrl);

                            Chat chat = new Chat();

                            chat.setChatId(key);
                            chat.setLast_message_time(time);
                            chat.setLast_message(message);
                            chat.setMessage_by(sender_name);
                            chat.setMessage_by_image(sender_imageUrl);

                            chatList.add(chat);
                            chatAdapter.notifyDataSetChanged();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Error in loading", Toast.LENGTH_SHORT).show();

            }
        });

        requestQueue.add(objectRequest);
    }


    private void getUserData(){

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading");
        progressDialog.show();

        String Url = "https://collegeproject-a59bc-default-rtdb.firebaseio.com/user/.json";

        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, Url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try{

                    Iterator<String> keys = response.keys();

                    while (keys.hasNext()) {

                        String key = keys.next();

                        try {

                            if (response.get(key) instanceof JSONObject) {

                                JSONObject resultObj = response.getJSONObject(key);

                                Log.i("key",key);

                                User user = new User();
                                user.setUserId(key);
                                user.setFullName(resultObj.getString("fullName"));
                                user.setImageUrl(resultObj.getString("imageUrl"));

                                userData.add(user);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }


                }catch (Exception ex){

                }finally {

                    getChatData();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.show();
                Toast.makeText(getActivity(), "Data not found", Toast.LENGTH_SHORT).show();

            }
        });

        requestQueue.add(objectRequest);
    }



}
