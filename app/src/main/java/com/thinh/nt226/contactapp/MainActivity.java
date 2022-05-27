package com.thinh.nt226.contactapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText txtSDT, txtTinNhan;
    Button btnGoiLuon, btnNhanTin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        addEvents();
    }

    private void addEvents() {
        btnGoiLuon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("tel:"+txtSDT.getText().toString());
                Intent i = new Intent(Intent.ACTION_CALL);
                i.setData(uri);
                startActivity(i);
            }
        });
        btnNhanTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SmsManager sms = SmsManager.getDefault();
                Intent msgSent = new Intent("ACTION_MSG_SENT");
                //xử lý trả kết quả có thành công hay không
                final PendingIntent pendingMsgSent =
                        PendingIntent.getBroadcast(MainActivity.this,0,msgSent,0);
                registerReceiver(new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        int result = getResultCode();
                        String msg = "OK";
                        if(result!= Activity.RESULT_OK)
                            msg = "Not OK";
                        Toast.makeText(context, ""+msg, Toast.LENGTH_SHORT).show();
                    }
                },new IntentFilter("ACTION_MSG_SENT"));

                sms.sendTextMessage(
                        txtSDT.getText().toString(),
                        null,
                        txtTinNhan.getText().toString(),
                        pendingMsgSent,
                        null);
            }
        });
    }

    private void addControls() {
        txtSDT = (EditText) findViewById(R.id.txtSDT);
        txtTinNhan = (EditText) findViewById(R.id.txtTinNhan);
        btnGoiLuon = (Button) findViewById(R.id.btnGoiLuon);
        btnNhanTin = (Button) findViewById(R.id.btnNhanTin);
    }
}