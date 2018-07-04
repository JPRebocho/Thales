package com.akka.thales;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;


public class Main extends AppCompatActivity {

    //refresh button animation
    Animation animation = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);


    Button b_gallery;
    ImageButton b_on, b_refresh;

    ListView list;
    BluetoothAdapter bluetoothAdapter;


    private static final int REQUEST_ENABLED = 0;
    private static final int REQUEST_DISCOVERABLE = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Toast.makeText(getApplicationContext(), "BLUETOOTH INTENT CREATED", Toast.LENGTH_SHORT).show();
        //refresh button animation
        animation.setRepeatCount(-1);
        animation.setDuration(2000);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b_on = findViewById(R.id.b_on);
        b_gallery = findViewById(R.id.b_gallery);
        b_refresh = findViewById(R.id.b_refresh);
        list = findViewById(R.id.list);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();


        b_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!bluetoothAdapter.isEnabled()) {
                    //turn on bluetooth
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intent, REQUEST_ENABLED);
                    b_refresh.callOnClick();
                }
                else {
                    bluetoothAdapter.disable();
                    //clear the list
                    list.setAdapter(null);
                    b_refresh.clearAnimation();
                }
            }
        });

        //long press opens device default bluetooth screen
        b_on.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent settings = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
                startActivity(settings);
                return true;
            }
        });


        b_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent imagesintent = new Intent(getApplicationContext(), Images.class);
                startActivity(imagesintent);
            }
        });


        b_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b_refresh.startAnimation(animation);
                //list paired devices
                Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

                ArrayList<String> devices = new ArrayList<>();

                for (BluetoothDevice bt : pairedDevices){
                    devices.add(bt.getName());
                }

                ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, devices);

                bluetoothAdapter.startDiscovery();

                list.setAdapter(arrayAdapter);

                Toast.makeText(getApplicationContext(), "BLUETOOTH LIST PRESSED", Toast.LENGTH_SHORT).show();
//                b_refresh.clearAnimation();
            }
        });


        //if bluetooth is already enabled, immediately starts looking for devices to connect
        if (bluetoothAdapter.isEnabled()){
            b_refresh.callOnClick();
        }

    }
}
