package com.pequla.playerfinder;

import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.pequla.playerfinder.model.QrcodeModel;
import com.pequla.playerfinder.service.RestService;

import java.io.IOException;

public class ScannerActivity extends AppCompatActivity {

    private CodeScanner mCodeScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        // Enabling the back/up button
        getSupportActionBar().setTitle(R.string.scanner_activity_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Init scanner
        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        scannerView.setOnClickListener((view) -> {
            mCodeScanner.startPreview();
        });

        mCodeScanner.setDecodeCallback(result -> {
            try {
                String json = result.getText();
                QrcodeModel model = RestService.getInstance().getMapper().readValue(json, QrcodeModel.class);
                runOnUiThread(() -> {
                    Intent intent = new Intent(this, DetailsActivity.class);
                    intent.putExtra(DetailsActivity.PLAYER_DATA_ID_KEY, String.valueOf(model.getId()));
                    startActivity(intent);
                });
            } catch (IOException e) {
                Toast.makeText(this, "Invalid QR Code, scan again", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }
}