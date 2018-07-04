package com.akka.thales;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Images extends AppCompatActivity {

    public static final int IMAGE_GALLERY_REQUEST = 20;
    public static final int EXPLORER_REQUEST = 10;
    Button b_explorer, b_gallery;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Toast.makeText(getApplicationContext(), "IMAGES INTENT CREATED", Toast.LENGTH_SHORT).show();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);

        b_explorer = findViewById(R.id.b_explorer);
        b_gallery = findViewById(R.id.b_gallery);
        imageView = findViewById(R.id.imageView);



        b_explorer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent explorerintent = new Intent(Intent.ACTION_GET_CONTENT);
                explorerintent.setType("*/*");
                startActivityForResult(explorerintent, EXPLORER_REQUEST);
            }
        });

        b_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //invoke image gallery
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);

                //where to find the data
                File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                String pictureDirectoryPath = pictureDirectory.getPath();


                //get a URI representation
                Uri data = Uri.parse(pictureDirectoryPath);

                //set data and type. Gets all image types
                photoPickerIntent.setDataAndType(data, "image/*");


                startActivityForResult(photoPickerIntent, IMAGE_GALLERY_REQUEST);

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode == RESULT_OK) {
            if ((requestCode == IMAGE_GALLERY_REQUEST) || (requestCode == EXPLORER_REQUEST)) {

                //the address of the image on the SD Card
                Uri imageUri = data.getData();
                Log.w("URI VARIABLE", imageUri.toString());
                //declare a stream to read the image data from the SD Card
                InputStream inputStream;

/*
                File imgfile = new File(data.getData().getPath());

                Bitmap myBitmap = BitmapFactory.decodeFile(imgfile.getAbsolutePath());
                imageView.setImageBitmap(myBitmap);
*/

                //we are getting an input stream based on the URI of the image
                try {
                    inputStream = getContentResolver().openInputStream(imageUri);
                    Toast.makeText(getApplicationContext(), "SO FAR OK", Toast.LENGTH_LONG).show();

                    //get a bitmap from the stream
                    Bitmap image = BitmapFactory.decodeStream(inputStream);

                    //shows image to the user
                    imageView.setImageBitmap(image);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Unable to open image", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}