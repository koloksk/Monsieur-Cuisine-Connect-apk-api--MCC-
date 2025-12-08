package com.journeyapps.barcodescanner;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import com.google.zxing.client.android.R;

/* loaded from: classes.dex */
public class CaptureActivity extends Activity {
    public CaptureManager a;
    public DecoratedBarcodeView b;

    public DecoratedBarcodeView initializeContent() {
        setContentView(R.layout.zxing_capture);
        return (DecoratedBarcodeView) findViewById(R.id.zxing_barcode_scanner);
    }

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        DecoratedBarcodeView decoratedBarcodeViewInitializeContent = initializeContent();
        this.b = decoratedBarcodeViewInitializeContent;
        CaptureManager captureManager = new CaptureManager(this, decoratedBarcodeViewInitializeContent);
        this.a = captureManager;
        captureManager.initializeFromIntent(getIntent(), bundle);
        this.a.decode();
    }

    @Override // android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        this.a.onDestroy();
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        return this.b.onKeyDown(i, keyEvent) || super.onKeyDown(i, keyEvent);
    }

    @Override // android.app.Activity
    public void onPause() throws InterruptedException {
        super.onPause();
        this.a.onPause();
    }

    @Override // android.app.Activity
    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        this.a.onRequestPermissionsResult(i, strArr, iArr);
    }

    @Override // android.app.Activity
    public void onResume() {
        super.onResume();
        this.a.onResume();
    }

    @Override // android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        this.a.onSaveInstanceState(bundle);
    }
}
