package com.high.fullhdwallpaper;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.IOException;

public class SecondActivityActivity extends AppCompatActivity {
  PhotoView photoView;
    String image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_activity);
        photoView=findViewById(R.id.photoview);
        Intent intent=getIntent();
         image=intent.getStringExtra("waseem");
        Glide.with(SecondActivityActivity.this).load(image).into(photoView);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void downloadimage(View view) {
        DownloadManager downloadmanager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(image);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        downloadmanager.enqueue(request);

    }

    public void setwallpaper(View view) {
        WallpaperManager wallpaperManager= WallpaperManager.getInstance(this);
        Bitmap bitmap=((BitmapDrawable)photoView.getDrawable()).getBitmap();
        try {
            wallpaperManager.setBitmap(bitmap);
            Toast.makeText(SecondActivityActivity.this,"Wallpaper has been set In Screen",Toast.LENGTH_LONG).show();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}