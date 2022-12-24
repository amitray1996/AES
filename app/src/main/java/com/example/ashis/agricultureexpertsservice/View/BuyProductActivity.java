package com.example.ashis.agricultureexpertsservice.View;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
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
import com.example.ashis.agricultureexpertsservice.Model.Shop;
import com.example.ashis.agricultureexpertsservice.R;
import com.example.ashis.agricultureexpertsservice.utils.SharedPrefUtils;
import org.json.JSONObject;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BuyProductActivity extends AppCompatActivity {


    static final String PRODUCT_ID = "product_id";
    static final String TOTAL_QUANTITY = "total_quantity";
    static final String TOTAL_AMOUT = "total_amount";
    static final String SHOPPING_DATA = "shop_data";

    ProgressDialog progressDialog;

    String Url = "https://collegeproject-a59bc-default-rtdb.firebaseio.com/order/";

    EditText address;
    EditText contactName;
    EditText contactNumber;
    Button confirmOrder;
    TextView totalPrice;

    String productId;
    Integer totalQuantity;
    Float price;
    Shop shopData;

    Boolean hasError = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_product);

        productId = getIntent().getStringExtra(PRODUCT_ID);
        totalQuantity = getIntent().getIntExtra(TOTAL_QUANTITY, 0);
        price = getIntent().getFloatExtra(TOTAL_AMOUT, 0f);
        shopData = (Shop) getIntent().getSerializableExtra(SHOPPING_DATA);

        if (totalQuantity == 0) {
            finish();
        }

        address = findViewById(R.id.field_address);
        contactName = findViewById(R.id.field_contact_name);
        contactNumber = findViewById(R.id.field_contact_number);
        confirmOrder = findViewById(R.id.confirm_order);
        totalPrice = findViewById(R.id.total_price);

        totalPrice.setText(new DecimalFormat("##.##").format(price));

        getSupportActionBar().setTitle("Buy Product");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        confirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveOrderInformation();

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

    private void saveOrderInformation() {

        progressDialog = new ProgressDialog(BuyProductActivity.this);
        progressDialog.setMessage("Processing");
        progressDialog.show();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date orderDate = new Date();

        final JSONObject object = new JSONObject();

        if (address.getText().toString().trim().isEmpty()) {
            address.setError("Delivery address is required");
        } else if (contactName.getText().toString().trim().isEmpty()) {
            contactName.setError("Contact name is required");
            return;
        } else if (contactNumber.getText().toString().trim().isEmpty()) {
            contactNumber.setError("Contact number is required");
        } else {

            try {
                object.put("productId", shopData.getProductId());
                object.put("orderDate", format.format(orderDate));
                object.put("quantity", totalQuantity);
                object.put("total_amount", price);
                object.put("payment_type", "Cash On Delivery");
                object.put("contact_name", contactName.getText().toString());
                object.put("contact_number", contactNumber.getText().toString());
                object.put("delivery_address", address.getText().toString());
                object.put("product_image_url", shopData.getImageUrl());
                object.put("product_name", shopData.getTitle());
                object.put("product_of", shopData.getUploadBy());
                object.put("order_by", SharedPrefUtils.getStringPreference(getApplicationContext(), "localId"));
                object.put("order_by_name", SharedPrefUtils.getStringPreference(getApplicationContext(), "fullName"));

            } catch (Exception e) {
                return;
            }

            String savePost = Url + "/.json";

            final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, savePost, object, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    progressDialog.dismiss();

                    try {

                    } catch (Exception e) {

                        e.printStackTrace();
                        Toast.makeText(BuyProductActivity.this, "Error. Try again!", Toast.LENGTH_SHORT).show();
                    } finally {

                        Toast.makeText(BuyProductActivity.this, "Order Successful", Toast.LENGTH_SHORT).show();
                        finish();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    progressDialog.dismiss();
                    Toast.makeText(BuyProductActivity.this, "Error while saving your information.", Toast.LENGTH_SHORT).show();
                }
            });

            requestQueue.add(objectRequest);
        }

    }
}