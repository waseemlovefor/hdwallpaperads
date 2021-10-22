package com.corefoxes.fullhdwallpaper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
   RecyclerView recyclerView;
   List<PhotoList> photoLists=new ArrayList<>();
   ImageAdapter adapter;
   Toolbar toolbar;
   Boolean isscrooling=false;
   int pagenb=1;
   int currentiten,totalitem,scrollitem;
    private AdView mAdView;
    String url="https://api.pexels.com/v1/curated?page="+pagenb+"&per_page=80";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("Hd WallPaper");
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        if (toolbar!=null){
            setSupportActionBar(toolbar);
        }
        recyclerView=findViewById(R.id.recycleview);
        adapter=new ImageAdapter(this,photoLists);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
        fatchimage();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState== AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isscrooling=true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
               currentiten=gridLayoutManager.getChildCount();
               totalitem=gridLayoutManager.getItemCount();
               scrollitem=gridLayoutManager.findFirstCompletelyVisibleItemPosition();
               if (isscrooling&&(currentiten+scrollitem==totalitem)){
                   isscrooling=false;
                   fatchimage();
               }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem item=menu.findItem(R.id.search);
        SearchView searchView= (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                url="https://api.pexels.com/v1/search?page="+pagenb+"&per_page=80&query="+query;
                adapter.getFilter().filter(url);
                fatchimage();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    public void fatchimage(){
        RequestQueue queue= Volley.newRequestQueue(this);
        StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object=new JSONObject(response);
                    JSONArray array=object.getJSONArray("photos");
                    int arlength=array.length();
                    for (int i=0;i<arlength;i++){
                      JSONObject idobject=array.getJSONObject(i);
                      int id=idobject.getInt("id");
                      JSONObject imageobject=idobject.getJSONObject("src");
                      String large=imageobject.getString("large");
                      String medium=imageobject.getString("medium");
                      PhotoList photoList=new PhotoList(id,large,medium);
                      photoLists.add(photoList);
                    }
                    adapter.notifyDataSetChanged();
                    pagenb++;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,""+error,Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> data=new HashMap<>();
                data.put("Authorization","563492ad6f917000010000017d0e42bedf31477ba7013ceabfa59059");
                return data;
            }
        };
        queue.add(request);

    }

}