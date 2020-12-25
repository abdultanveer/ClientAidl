package com.next.clientaidl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.next.server.AddAidl;

public class MainActivity extends AppCompatActivity {
public static String TAG = MainActivity.class.getSimpleName();
protected AddAidl addService;
EditText fno,sno;
TextView resTextView;
int n1,n2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fno = findViewById(R.id.editTextFno);
        sno = findViewById(R.id.editTextSno);
        resTextView = findViewById(R.id.textViewRes);

    }

    public void clickHandler(View view) {
        String no1 = fno.getText().toString();
        n1 = Integer.parseInt(no1);
        n2 = Integer.parseInt(sno.getText().toString());
        Intent addServiceIntent = new Intent();
       // addServiceIntent.setAction("service.calc");
        addServiceIntent.setPackage("com.next.server");
        addServiceIntent.setClassName("com.next.server", "com.next.server.AddService");



        bindService(addServiceIntent,serviceConnection,BIND_AUTO_CREATE);

    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(TAG, "Service Connected");
            addService = AddAidl.Stub.asInterface((IBinder) iBinder);
            try {
                int res = addService.add(n1,n2);
                resTextView.setText("Result is ="+res);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(TAG, "Service Disconnected");
            addService = null;
        }
    };
}