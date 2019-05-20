package com.client.galleryapp.filtercolor;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.client.galleryapp.MainActivity;
import com.client.galleryapp.R;
import com.client.galleryapp.adapters.RecyclerViewAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PhotoEdit extends Activity {
    ImageView imageView;
    Button saveImageButton;
    Button cancelButton;
    private static final String TAG = "MainActivity";
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<Integer> mImageUrls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_editor);
        imageView = (ImageView) findViewById(R.id.photoEditorView);


        saveImageButton = findViewById(R.id.saveImageButton);
        saveImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final File file = (File)getIntent().getExtras().get("img");

                Date currentTime = Calendar.getInstance().getTime();
                DateFormat df = new SimpleDateFormat("HH:mm:ss");
                String date = df.format(currentTime.getTime());


//                String extend = file.getName().substring(file.getName().lastIndexOf("."));


                String filename = file.getName().substring(0,file.getName().lastIndexOf(".")) + "_" + date;

                Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                saveBitmap(bitmap,filename);
            }
        });

        cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        final File file = (File)getIntent().getExtras().get("img");
        getImages();
        try{
            Glide.with(getApplicationContext()).load(Uri.fromFile(file))
                    .fitCenter()
                    .placeholder(R.drawable.waitting_for_load)
                    .into(imageView);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    private void getImages(){
        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");

        mImageUrls.add(R.drawable.origin);
        mNames.add("Original");

        mImageUrls.add(R.drawable.snow);
        mNames.add("Snow");

        mImageUrls.add(R.drawable.grayscale);
        mNames.add("Gray Scale");

        mImageUrls.add(R.drawable.brightness);
        mNames.add("Brightness");

        mImageUrls.add(R.drawable.tint);
        mNames.add("Tint");
        initRecyclerView();

    }

    private void saveBitmap(Bitmap bmp,String fileName){
        try {
            File sd = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator +"GalaryApp");
            if (!sd.exists()) {
                sd.mkdirs();
            }
            File f = new File(sd , fileName+".png");
            FileOutputStream fos = new FileOutputStream(f);
            bmp.compress(Bitmap.CompressFormat.PNG,90,fos);
            Toast.makeText(PhotoEdit.this,"Đã lưu",Toast.LENGTH_SHORT).show();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);

        final File file = (File)getIntent().getExtras().get("img");
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mNames, mImageUrls, file);
        recyclerView.setAdapter(adapter);
    }


}