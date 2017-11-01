package com.example.hanny.testvolley;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextView tvRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvRes = (TextView) findViewById(R.id.tvResponses);

        String email = "hanny562@hotmail.com";
        String password = "P@ssw0rd!";
        String item_id = "A12345";
        //httpRequestPost(email,password);
        //tvRes.setText(email+password);
        httpRequestGET(item_id);
        httpRequestPost(email, password);
    }

    private void httpRequestPost(final String email, final String password) {
        com.android.volley.RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "http://10.3.200.177/login.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        //tvRes.setText(response);
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                        try {
                            JSONObject jObj = new JSONObject(response);
                            String uid = jObj.getString("uid");
                            JSONObject user = jObj.getJSONObject("user");
                            String email = user.getString("email");
                            String create_time = user.getString("created_at");

                            tvRes.setText("UID : " + uid + "\n" + "Email : " + email);

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        //Log.d("Error.Response", response);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);

                return params;
            }
        };
        queue.add(postRequest);
    }

    private void httpRequestGET(final String item_id) {
        com.android.volley.RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "http://10.3.200.177/item.php?item_id=" + item_id;

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());
                        Toast.makeText(getApplicationContext(), response.toString(),Toast.LENGTH_LONG).show();

                        try
                        {
                            response = response.getJSONObject("item");
                            String item_id = response.getString("item_id");
                            String item_name = response.getString("item_name");
                            String price = response.getString("price");

                            tvRes.setText("Item Id : " + item_id + "\n" + "Item Name : " + item_name + "\n" + "Price : RM " + price);
                        }catch (Exception e)
                        {

                        }


                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                    }
                }

        );

//        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        // response
//                        Log.d("Response", response.toString());
//                        //tvRes.setText(response);
//                        Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
//
//                        try {
//                            JSONObject jObj = new JSONObject(response.to);
//                            JSONObject item = jObj.getJSONObject("item");
//                            String item_id = item.getString("item_id");
//                            String item_name = item.getString("item_name");
//                            String price = item.getString("price");
//                            String quantity = item.getString("quantity");
//
//                            tvRes.setText("Item Id : " + item_id + "\n" + "item_name : " + item_name + "\n" + "Price : " + price + "\n" + "Quantity : " + quantity);
//
//                        } catch (Exception e) {
//                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // error
//                        //Log.d("Error.Response", response);
//                    }
//                }
//        );
        queue.add(getRequest);
    }


}
