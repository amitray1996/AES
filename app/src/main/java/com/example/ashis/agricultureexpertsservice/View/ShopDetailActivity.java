package com.example.ashis.agricultureexpertsservice.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Visibility;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.ashis.agricultureexpertsservice.Model.Shop;
import com.example.ashis.agricultureexpertsservice.R;
import com.example.ashis.agricultureexpertsservice.utils.SharedPrefUtils;

import org.json.JSONObject;

import static android.view.View.GONE;
import static com.example.ashis.agricultureexpertsservice.utils.Config.GET_UPLOADED_INFO;

public class ShopDetailActivity extends AppCompatActivity {

    ImageView profile;
    TextView title;
    ImageView minus;
    TextView totalBuy;
    ImageView plus;
    TextView price;
    TextView description;
    Button buy;
    TextView kg;

    private static int counter = 1;
    private String stringVal;

    Shop shop;

    float totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);

        shop = (Shop) getIntent().getSerializableExtra("shopData");

        Log.i("shop data",shop.toString());

        profile = findViewById(R.id.crops_profile);
        title = findViewById(R.id.title_name);
        minus = findViewById(R.id.minus);
        totalBuy = findViewById(R.id.total_buy);
        plus = findViewById(R.id.plus);
        price = findViewById(R.id.buy_price);
        description = findViewById(R.id.description);
        buy = findViewById(R.id.buy_button);
        kg = findViewById(R.id.kilogram);


        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(counter ==0){

                    return;

                }

                counter--;
                stringVal = Integer.toString(counter);

                totalPrice = counter * Integer.parseInt(shop.getPricePerUnit());

                totalBuy.setText(stringVal);
                price.setText(String.valueOf(totalPrice));
            }
        });

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                counter++;
                stringVal = Integer.toString(counter);

                totalPrice = counter * Integer.parseInt(shop.getPricePerUnit());
                totalBuy.setText(stringVal);
                price.setText(String.valueOf(totalPrice));
            }
        });

        getSupportActionBar().setTitle("Detail");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        title.setText(shop.getTitle());
        Glide.with(getApplicationContext()).load(shop.getImageUrl()).into(profile);
        description.setText(shop.getDescription());
        price.setText(shop.getPricePerUnit());

        String userId = SharedPrefUtils.getStringPreference(getApplicationContext(), "localId");

        Log.i("CHECK-FOR_CURRENT-USER", "user id "+userId);
        Log.i("CHECK-FOR_CURRENT-USER", "upload by "+shop.getUploadBy());

        if(userId.equals(shop.getUploadBy())){
            buy.setVisibility(GONE);
        }

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ShopDetailActivity.this, BuyProductActivity.class);
                intent.putExtra(BuyProductActivity.TOTAL_QUANTITY, counter);
                intent.putExtra(BuyProductActivity.TOTAL_AMOUT, totalPrice);
                intent.putExtra(BuyProductActivity.PRODUCT_ID, shop.getProductId());
                intent.putExtra(BuyProductActivity.SHOPPING_DATA, shop);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case android.R.id.home:
                finish();
                break;
        }

        return false;
    }



    public void getListData(){

        String Url = GET_UPLOADED_INFO + "/.json";

        final RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, Url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONObject object = new JSONObject();
                    String title = object.getString("title");
                    String imageUrl = object.getString("imageUrl");
                    String description = object.getString("description");
                    String quantity = object.getString("totalQuantity");
                    String price = object.getString("pricePerUnit");


                }catch (Exception ex){
                    ex.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(objectRequest);
    }




}
