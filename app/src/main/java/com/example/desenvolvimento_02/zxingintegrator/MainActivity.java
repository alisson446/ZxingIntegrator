package com.example.desenvolvimento_02.zxingintegrator;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    private Button decode;
    private TextView decoded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        decode = (Button) findViewById(R.id.btnDecode);
        decoded = (TextView) findViewById(R.id.textDecoded);

        decode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator it = new IntentIntegrator(MainActivity.this);
                it.setPrompt("Faça uma scanner de um código de barra ou QRCode");
                it.setCameraId(0);
                it.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                it.setOrientationLocked(false);
                it.setBeepEnabled(true);
                it.initiateScan();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        decoded.setText(result.getContents());

        Context page = this;
        SharedPreferences sharedp = page.getSharedPreferences("files_preferences", page.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedp.edit();
        editor.putString("qrcode", result.getContents());
        editor.commit();

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("decodificação");
        alert.setMessage(sharedp.getString("qrcode", null));
        alert.setNeutralButton("ok", null);
        alert.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
