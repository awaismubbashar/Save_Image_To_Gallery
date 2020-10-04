package com.example.saveimage;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Date;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private static final int GALLERY_REQUEST_CODE = 100;
    private static final int CAMERA_REQUEST_CODE = 200;

    Bitmap bm;
    String extStorageDirectory;
    ImageView imageView;
    Button imageFromGallery, imageFromCamera,saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.imageViewID);
        imageFromGallery = (Button) findViewById(R.id.takeFromGallery);
        imageFromCamera = (Button) findViewById(R.id.takeFromCamera);
        saveBtn = (Button) findViewById(R.id.saveButton);

        imageFromGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               openGallery(v);
            }
        });
        imageFromCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera(v);
            }
        });

//        extStorageDirectory = Environment.getExternalStorageDirectory().toString();
//
//        saveBtn.setText("Save to " + extStorageDirectory + "/qr.PNG");
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    save();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void openGallery(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_REQUEST_CODE);
    }

    public void openCamera(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == GALLERY_REQUEST_CODE) {
            try {
                Uri selectedImage = data.getData();
                InputStream imageStream = getContentResolver().openInputStream(selectedImage);
                imageView.setImageBitmap(BitmapFactory.decodeStream(imageStream));
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);

        }
    }

    public void save() throws IOException {


      //  File file = new File(extStorageDirectory, "er.PNG");
//        Toast.makeText(MainActivity.this, "111", Toast.LENGTH_SHORT).show();
        try {
//            File dir = new File(Environment.getExternalStorageDirectory() + "/awais");
//            if (!dir.exists()){
//                File mydir = new File("/DCIM/awais/");
//                mydir.mkdir();
//                if (mydir.exists()) {
//                    Toast.makeText(this, "dir created", Toast.LENGTH_SHORT).show();
//                }
//            }else{
//                Log.d(TAG, "dir already exist: ");
//            }
            File dir = new File( Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DCIM),"Pola" );

            FileOutputStream outStream = new FileOutputStream(dir);
            Toast.makeText(this, "file saved at " +dir, Toast.LENGTH_SHORT).show();
            bm.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            Toast.makeText(this, "file saved at " +dir, Toast.LENGTH_SHORT).show();
            outStream.flush();
            outStream.close();

            Toast.makeText(MainActivity.this, "Saved", Toast.LENGTH_LONG).show();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
//        File file = new File(Environment.getExternalStorageDirectory()+"/my new");
//        boolean success = true;
//        if(!file.exists()) {
//            Toast.makeText(getApplicationContext(),"Directory does not exist, create it",
//                    Toast.LENGTH_LONG).show();
//        }
//        if(success) {
//            Toast.makeText(getApplication(),"Directory created",
//                    Toast.LENGTH_LONG).show();
//        }
//        else {
//            Toast.makeText(this,"Failed to create Directory",
//                    Toast.LENGTH_LONG).show();
//        }



//        Toast.makeText(MainActivity.this,"1",Toast.LENGTH_SHORT).show();
//        Bitmap image = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
//        BitmapDrawable bitmapDrawable= (BitmapDrawable) imageView.getDrawable();
//        Bitmap bitmap=bitmapDrawable.getBitmap();
//
//        File filePath = Environment.getExternalStorageDirectory();
//        File dir = new File(filePath.getPath()+"/DCIM/Pic");
//
//        if(!dir.exists()){
//            dir.mkdir();
//        }
//        File file = new File(dir,System.currentTimeMillis()+".jpg");
//        try {
//            FileOutputStream outputStream = new FileOutputStream((file));
//            bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
//
//            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//            intent.setData(Uri.fromFile(file));
//            sendBroadcast(intent);
//
//            Log.d("Save Imag ", "Saved");
//
//            outputStream.flush();
//            outputStream.close();
//        }catch (Exception e){
//            Log.e("Error Reported",e.getCause()+"");
//        }
    }



}