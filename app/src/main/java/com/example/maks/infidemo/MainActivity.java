package com.example.maks.infidemo;

import android.app.ProgressDialog;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.example.maks.infidemo.adapter.SimpleItemRecyclerViewAdapter;
import com.example.maks.infidemo.model.AboutItem;
import com.example.maks.infidemo.model.AboutItemDTO;
import com.example.maks.infidemo.utils.AppConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.maks.infidemo.utils.NetworkUtil.isInternetConnectionAvailable;

public class MainActivity extends AppCompatActivity {

    ProgressDialog pDialog ;
    RecyclerView mRecyclerView;
    List<AboutItem> posts = new ArrayList<AboutItem>();
    SimpleItemRecyclerViewAdapter mAdapter;
    SwipeRefreshLayout mySwipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");

        mRecyclerView =(RecyclerView) findViewById(R.id.recycle_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));


        mySwipeRefreshLayout = findViewById(R.id.swiperefresh);
        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Log.i(MainActivity.class.getName(), "onRefresh called from SwipeRefreshLayout");

                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.
                        getData(false);
                    }
                }
        );
getData(true);

    }


    private void setupRecyclerView( List<AboutItem> items) {

        mAdapter  = new SimpleItemRecyclerViewAdapter(items);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void getData(boolean showProgress) {
        //request user data from url

        if (!isInternetConnectionAvailable(getBaseContext())) {
            showNetworkErrorSnackBar();
        } else {
            if (showProgress) {
                pDialog = new ProgressDialog(this);
                pDialog.setMessage("Loading contacts...");
                pDialog.setCancelable(false);
                pDialog.show();
            }
            StringRequest req = new StringRequest(AppConstants.API_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d(MainActivity.class.getName(), response.toString());
                            if (pDialog.isShowing()) pDialog.hide();
                            mySwipeRefreshLayout.setRefreshing(false);
                            Gson gson = new GsonBuilder().create();

//parse as a User array (which we convert into a list)
                            AboutItemDTO items = gson.fromJson(response, AboutItemDTO.class);
                            setTitle(items.getTitle());
                            posts = items.getRows();
                            setupRecyclerView(posts);

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(MainActivity.class.getName(), "Error: " + error.getMessage());
                    if (pDialog.isShowing()) pDialog.hide();
                    mySwipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();

                }
            });

// Adding request to request queue
            AppController.getInstance().addToRequestQueue(req);

        }
    }
    private void showNetworkErrorSnackBar() {

        final Snackbar snackBar = Snackbar.make(mRecyclerView, R.string.snackbar_network_error_text, Snackbar.LENGTH_LONG);
        snackBar.setAction(R.string.snackbar_network_error_action_text, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isInternetConnectionAvailable(getBaseContext())) {
                    snackBar.dismiss();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (!isInternetConnectionAvailable(getBaseContext())) {
                                snackBar.show();
                            }else{
                                getData(true);
                            }
                        }
                    }, 1000);
                }
            }
        })  // action text on the right side
                .setActionTextColor(ContextCompat.getColor(getBaseContext(), R.color.colorPrimary))
                .setDuration(Snackbar.LENGTH_LONG).show();
    }

}
