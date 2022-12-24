package com.example.ashis.agricultureexpertsservice.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
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
import com.example.ashis.agricultureexpertsservice.DashboardActivity;
import com.example.ashis.agricultureexpertsservice.R;
import com.example.ashis.agricultureexpertsservice.utils.SharedPrefUtils;

import org.json.JSONObject;

import static com.example.ashis.agricultureexpertsservice.utils.Config.GET_USER_INFO;

public class LoginInActivity extends AppCompatActivity {

    EditText login;
    EditText password;
    Button loginButton;
    TextView newAccount;
    EditText full_name;
    EditText address;
    EditText occupation;
    EditText number;

    String Url = "https://www.googleapis.com/identitytoolkit/v3/relyingparty/verifyPassword?key=AIzaSyBJ06Ul1DQq2iv3nFULA4ltqzengGb8rAY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_in);


        loginButton = findViewById(R.id.log_in_button);
        login = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        newAccount = findViewById(R.id.create_account);
        full_name = findViewById(R.id.full_name);
        address = findViewById(R.id.address);
        occupation = findViewById(R.id.occupation);
        number = findViewById(R.id.number);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validation();
            }
        });

        newAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginInActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });


    }

    private void validation(){

        if (login.getText().toString().trim().isEmpty()){

            login.setError("Enter email address");

        }else if(password.getText().toString().trim().isEmpty()){
            password.setError("Enter password");

        }else{

            loginProcess();
        }
    }

    private void loginProcess(){

        final ProgressDialog progressDialog = new ProgressDialog(LoginInActivity.this);
        progressDialog.setMessage("Loading");
        progressDialog.show();

        JSONObject object = new JSONObject();

        String emailTxt = login.getText().toString().trim();
        String passwordTxt = password.getText().toString().trim();

        if(emailTxt.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(emailTxt).matches()){
            progressDialog.dismiss();
            login.setError("Invalid email address");
            return;
        }else if(passwordTxt.isEmpty()){
            progressDialog.dismiss();
            password.setError("Password is required");
            return;
        }

        try {

            object.put("email",login.getText().toString().trim());
            object.put("password",password.getText().toString().trim());

        }catch (Exception e){
        }

        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,Url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                progressDialog.dismiss();
                Log.i("Json Request",response.toString());

                try {

                    if (response.has("error")) {

                        Log.i("LOGIN-API-CALL","got error "+response);

                        Integer code = response.getInt("code");
                        String message = response.getString("message");

                        Toast.makeText(LoginInActivity.this, message, Toast.LENGTH_SHORT).show();

                    }else if (response.has("localId")){

                        Log.i("LOGIN-API-CALL","got success response "+response.getString("localId"));

                        String localId = response.getString("localId");

                        SharedPrefUtils.setStringPreference(getApplicationContext(),"localId",localId);

                        getUserInfo(localId);
                    }

                }catch (Exception e){
                    e.printStackTrace();
                    Log.i("LOGIN-API-CALL","got error on catch"+e.getLocalizedMessage());

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("LOGIN-API-CALL","error "+error.getLocalizedMessage());
                progressDialog.dismiss();
                login.setError("Email & password Does not match");
            }
        });

        requestQueue.add(jsonObjectRequest);

    }

    private void getUserInfo(String localId){

        String url = GET_USER_INFO+localId+"/.json";

        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put("email",login.getText().toString().trim());
            jsonObject.put("fullName",full_name.getText().toString().trim());
            jsonObject.put("occupation",occupation.getText().toString().trim());
            jsonObject.put("phoneNumber",number.getText().toString().trim());
            jsonObject.put("address",address.getText().toString().trim());

        }catch (Exception e){

        }

        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                Log.i("Json Request",response.toString());

                try {

                    if (response.has("error")){

                        Integer code = response.getInt("code");
                        String message = response.getString("message");

                    }else {


                        String email = response.getString("email");
                        SharedPrefUtils.setStringPreference(getApplicationContext(),"email",email);
                        String address = response.getString("address");
                        SharedPrefUtils.setStringPreference(getApplicationContext(),"address",address);
                        String occupation = response.getString("occupation");
                        SharedPrefUtils.setStringPreference(getApplicationContext(),"occupation",occupation);
                        String fullName = response.getString("fullName");
                        SharedPrefUtils.setStringPreference(getApplicationContext(),"fullName",fullName);
                        String phoneNumber = response.getString("phoneNumber");
                        SharedPrefUtils.setStringPreference(getApplicationContext(),"phoneNumber",phoneNumber);
                        String userType = response.getString("userType");
                        SharedPrefUtils.setStringPreference(getApplicationContext(),"userType",userType);

                        String imageUrl = response.getString("imageUrl");
                        SharedPrefUtils.setStringPreference(getApplicationContext(),"imageUrl", imageUrl);
                    }

                }catch (Exception e){

                    e.printStackTrace();
                }finally {

                    Log.i("logged in user id", SharedPrefUtils.getStringPreference(getApplicationContext(),"localId"));

                    if(SharedPrefUtils.getStringPreference(getApplicationContext(),"userType").equalsIgnoreCase("expert")){

                        Intent intent = new Intent(LoginInActivity.this,PostNavigationActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                    }else{

                        Intent intent = new Intent(LoginInActivity.this,DashboardActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonObjectRequest);

    }


}
