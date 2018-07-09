package com.akka.thales;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import java.util.HashSet;
import java.util.Set;


public class Main extends AppCompatActivity {

    //refresh button animation
    Animation animation = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

    Button b_gallery;
    ImageButton b_on, b_refresh;
    ListView list;
    BluetoothAdapter bluetoothAdapter;

    Set<BluetoothDevice> deviceSet = new HashSet<>();

    private static final int REQUEST_BLUETOOTH_ON = 10;
    private static final int REQUEST_PAIR_DEVICE = 20;
    private static final int REQUEST_DISCOVERABLE = 30;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //refresh button animation
        animation.setRepeatCount(-1);
        animation.setDuration(2000);

        //Allows to broadcast when it finds a bluetooth device
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);


        b_on = findViewById(R.id.b_on);
        b_gallery = findViewById(R.id.b_image);
        b_refresh = findViewById(R.id.b_refresh);
        list = findViewById(R.id.list);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();


        b_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!bluetoothAdapter.isEnabled()) {
                    //turn on bluetooth
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intent, REQUEST_BLUETOOTH_ON);
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
                startActivityForResult(settings, REQUEST_PAIR_DEVICE);
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
                addPairedDevicestoList();
                showListofDevices();
                bluetoothAdapter.startDiscovery();



    //          b_refresh.clearAnimation();
            }
        });


        //if bluetooth is already enabled, immediately starts looking for devices to connect
        if (bluetoothAdapter.isEnabled()){
            b_refresh.callOnClick();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode){
                case REQUEST_BLUETOOTH_ON:
                    b_refresh.callOnClick();
                    break;
                case REQUEST_PAIR_DEVICE:
                    Toast.makeText(getApplicationContext(), "DONT SHOW THIS-> ", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getApplicationContext(), "the data received-> " + data.toString(), Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    }

/*         ------------------------- PREVIOUS VERSION ---------------------------------------------
    protected void showListofPairedDevices(){
        //clear the list
        list.setAdapter(null);

        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        ArrayList<String> devices = new ArrayList<>();

        for (BluetoothDevice bt : pairedDevices){
            devices.add(bt.getName());
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, devices);

//        bluetoothAdapter.startDiscovery();

        list.setAdapter(arrayAdapter);

    }
*/
    protected void addPairedDevicestoList(){
        //get paired devices and build a list with them all
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

        if (pairedDevices.size() > 0) {
            //If there are paired devices, it puts the names into the list.
            for (BluetoothDevice bt : pairedDevices) {
                //Adds a device from the pairedDevices to the deviceSet if it is not there already
                if (!deviceSet.contains(bt)) {
                    deviceSet.add(bt);
                }
            }
        }



    }

    //uses the deviceSet
    protected void showListofDevices(){
        //clear the list
        list.setAdapter(null);

        ArrayList<String> devicenames = new ArrayList<>();
        for (BluetoothDevice bt : deviceSet){
            devicenames.add(bt.getName());
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, devicenames);
        list.setAdapter(arrayAdapter);
    }



    // Create a BroadcastReceiver for ACTION_FOUND.
    // Adds new found devices to the deviceSet
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                Toast.makeText(getApplicationContext(), "FOUND A DEVICE-> ", Toast.LENGTH_SHORT).show();
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (!deviceSet.contains(device)) {
                    deviceSet.add(device);
                    bluetoothAdapter.cancelDiscovery();
                }
                showListofDevices();
            }
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Don't forget to unregister the ACTION_FOUND receiver.
        unregisterReceiver(mReceiver);
    }


}
