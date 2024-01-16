package com.example.implicit_intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    EditText edt_call, edt_url, edt_sms;
    Button btn_url, btn_call, btn_sms, btn_share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edt_call = findViewById(R.id.edt_number);
        edt_url = findViewById(R.id.edt_http);
        edt_sms = findViewById(R.id.edt_message_text);

        btn_url = findViewById(R.id.btn_http);
        btn_call = findViewById(R.id.btn_call);
        btn_sms = findViewById(R.id.btn_sms);
        btn_share = findViewById(R.id.btn_share);

        btn_url.setOnClickListener(view -> {

            try {
                /*

                Here we are creating an Intent object
                Our Action is to VIEW that page so we have passed parameter as ACTION_VIEW
                ,Uri(Uniform Resource Identifier).parse to convert given text into URL
                edt_url.getText().toString() will get the text and uri.parse will convert it into url type.

                startActivity(Intent.CreateChooser(intent, "Choose browser));
                Intent.CreateChooser will open the selection box and passed the message and set title.

                */
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(edt_url.getText().toString().trim()));

                startActivity(Intent.createChooser(intent, "Choose Browser"));
            }catch (Exception e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        btn_call.setOnClickListener(view -> {
            try {
                Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("Tel:"+edt_call.getText().toString().trim()));
                startActivity(callIntent);
            }catch (Exception e)
            {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("tag_here",e.getMessage());
            }
        });

        btn_sms.setOnClickListener(view -> {

            try {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setData(Uri.parse("smsto:"+edt_call.getText().toString())); //This ensures only SMS apps respond
                intent.putExtra("sms_body",edt_sms.getText().toString());
                if(intent.resolveActivity(getPackageManager()) != null){    //It will find the application which will be ready to accept the intent or has functionality to perform the task.
                    startActivity(intent);
                }else {
                    Log.d("LOG_TYPE","INTENT CATCHER NOT FOUND");
                    Toast.makeText(this, "INTENT CATCHER NOT FOUND",Toast.LENGTH_LONG).show();
                }

            }catch(Exception e){
                Log.d("LOG_TYPE",e.getMessage());
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        btn_share.setOnClickListener(view -> {

            try
            {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");   //data type as text/plain
                intent.putExtra(android.content.Intent.EXTRA_TEXT, edt_sms.getText().toString().trim());

                /*
                For creating chooser we have to pass the intent and "Message";
                In Intent we have to give data about what is the type and what is the message that has to be passed in the intent.
                 */

                Intent chooser = Intent.createChooser(intent, "Share Via");
                if(intent.resolveActivity(getPackageManager()) != null)
                {
                    startActivity(chooser);
                }else {
                    Log.d("LOG_TYPE","INTENT CATCHER NOT FOUND");
                }

            }
            catch (Exception e)
            {
                Log.d("LOG_TYPE", Objects.requireNonNull(e.getMessage()));
            }

        });
    }
}