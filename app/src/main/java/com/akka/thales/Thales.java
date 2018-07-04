package com.akka.thales;

        import android.bluetooth.BluetoothAdapter;
        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.Toast;


public class Thales extends AppCompatActivity {

    Button b_bluetooth;
    BluetoothAdapter bluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thales);

        b_bluetooth = findViewById(R.id.b_gotomainwindow);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();


        //check if bluetooth is supported
        if (bluetoothAdapter == null) {
            Toast.makeText(this,"Bluetooth not supported!", Toast.LENGTH_SHORT).show();
//            finish();
        }

        b_bluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bluetoothintent = new Intent(getApplicationContext(), Main.class);
                startActivity(bluetoothintent);
            }
        });


    }
}