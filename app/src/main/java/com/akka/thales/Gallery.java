package com.akka.thales;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.io.File;

public class Gallery extends AppCompatActivity{
// TEST PATHS:


    String DEVICE_ID = "123";
    String SCAN_PATH = Environment.getExternalStorageDirectory().getPath() + "/DCIM";     //path with all devices folders
//    String SCAN_PATH = "/storage/sdcard1/MIUI/Gallery/cloud/.thumbnailFile/";      //CATS
//    String SCAN_PATH = "/storage/sdcard1/DCIM/Camera/";          //ALL CAMERA
//    String SCAN_PATH = "/media/external/images/media/";      //SCREENSHOTS

    public static final int FOLDER_REQUEST = 10;

    File[] allFiles;

    Button b_gallery;
    GridView gridView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        File folder = new File(SCAN_PATH);
        Log.d("PATH", "PATH -> " + folder.getAbsolutePath());
        allFiles = folder.listFiles();

        gridView = findViewById(R.id.gv_folder);
        b_gallery = findViewById(R.id.b_gallery);


        b_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialog();
//            performFileSearch();
//            new SingleMediaScanner(Gallery.this, allFiles[0]);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode == RESULT_OK) {
            if (requestCode == FOLDER_REQUEST) {
                Uri uri = data.getData();
                Uri folderUri = DocumentsContract.buildChildDocumentsUriUsingTree(uri, DocumentsContract.getDocumentId(uri));
                Log.d("FOLDER SEARCH", "SEARCHED FOR FOLDER-> " + folderUri.getPath());
            }
        }
    }


    public void performFileSearch() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        intent.putExtra("android.content.extra.SHOW_ADVANCED", true);
        intent.putExtra("android.content.extra.FANCY", true);
        intent.putExtra("android.content.extra.SHOW_FILESIZE", true);
        startActivityForResult(intent, FOLDER_REQUEST);
        Toast.makeText(getApplicationContext(), "PERFORM FILE SEARCH PRESSED", Toast.LENGTH_SHORT).show();
    }

    public class SingleMediaScanner implements MediaScannerConnectionClient {

        private MediaScannerConnection mMs;
        private File mFile;

        public SingleMediaScanner(Context context, File f) {
            mFile = f;
            mMs = new MediaScannerConnection(context, this);
            mMs.connect();
        }

        public void onMediaScannerConnected() {
            mMs.scanFile(mFile.getAbsolutePath(), null);
        }

        public void onScanCompleted(String path, Uri uri) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(uri);
            startActivity(intent);
            mMs.disconnect();

        }


    }

    public void createDialog (){
        AlertDialog.Builder deviceDialog = new AlertDialog.Builder(Gallery.this);
        deviceDialog.setTitle("Select a Device:");
        deviceDialog.setItems(R.array.devices, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DEVICE_ID = getResources().getStringArray(R.array.devices)[i];
            }
        });
        deviceDialog.show();
    }

}
