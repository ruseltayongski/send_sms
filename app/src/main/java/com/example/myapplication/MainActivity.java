package com.example.myapplication;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public String number_sms1 = "";
    public String number_sms2 = "";
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0;
    public boolean sms_flag = false;
    public boolean bcg_flag1 = false;
    public boolean bcg_flag2 = false;
    public boolean bcg_flag3 = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Read from the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("ridts");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //String value = dataSnapshot.getValue(String.class);
                //Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();

                if(sms_flag) {
                    String message = "";
                    for(DataSnapshot snapshot : dataSnapshot.getChildren() ) {
                        Map<String, Object> data = (Map<String, Object>) snapshot.getValue();
                        message = data.get("message").toString();
                    }

                    //Log.d(TAG, "The value is = " + message);
                    //String mesage_temp = "+639455500447,+639254737109,+639564259618,message";
                    //List<String> items = Arrays.asList(message.split("\\s*,\\s*"));

                    List<String> items = Arrays.asList(message.split("\\s*@\\s*"));
                    for (int i = 0; i < items.size()-1; i++) {
                        Log.d(TAG, "The value is = " + items.get(i));
                        number_sms1 = items.get(i);
                        String send_sms = items.get(items.size()-1);
                        SmsManager sms = SmsManager.getDefault();

                        if(!bcg_flag1)
                            sms.sendTextMessage(number_sms1, null, send_sms, null,null);

                        if(number_sms1.equals(number_sms2)) {
                            if(bcg_flag3) {
                                sms.sendTextMessage(number_sms1, null, "(5) Ang bakuna luwas ug epektibo. Ang BAKUNADO ay PROTEKTADO!", null,null);
                            }
                            if(bcg_flag2) {
                                sms.sendTextMessage(number_sms1, null, "(4) Importante nga ang bata mabakunahan sa tukmang schedule arun siya ma depensahan batok sa maong sakit.", null,null);
                            }
                            if(bcg_flag1) {
                                bcg_flag2 = true;
                                sms.sendTextMessage(number_sms1, null, "(3) Ang bakuna nga BCG maga protekta sa mga bata batok sa sakit nga Tuberculosis o TB.", null,null);
                            }
                            if(!bcg_flag1) {
                                bcg_flag1 = true;
                                sms.sendTextMessage(number_sms1, null, "(2) Please come on your schedule and bring the vaccination card when you visit the Health Center.", null,null);
                            }
                        }

                        number_sms2 = items.get(i);
                    }

                    /*Log.d(TAG, "The value is = " + items.get(items.size()-1));*/
                    /*SmsManager sms= SmsManager.getDefault();
                    sms.sendTextMessage("+639564259618", null, message, null,null);*/

                    /*SmsManager sms = SmsManager.getDefault();
                    sms.sendTextMessage(message, null, "(1) Congratulations!\n\n", null,null);*/

                    Toast.makeText(getApplicationContext(), "Send SMS",
                            Toast.LENGTH_LONG).show();
                }
                sms_flag = true;

                /*SmsManager sms= SmsManager.getDefault();
                sms.sendTextMessage("+639564259618", null, message, null,null);
                sms.sendTextMessage("+639254737109", null, message, null,null);

                Toast.makeText(getApplicationContext(), "Send SMS",
                        Toast.LENGTH_LONG).show();*/

                /*
                sms.sendTextMessage("+639455500447", null, message, null,null);*/
               /* sendSMSMessage();*/
            }

            @Override
            public void onCancelled(DatabaseError error) {
                /* Failed to read value */
                Log.w(TAG, "Failed to read value.", error.toException());
                Toast.makeText(getApplicationContext(), "Failed Firebase.",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    /*protected void sendSMSMessage() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNo, null, message, null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }
    }*/


}