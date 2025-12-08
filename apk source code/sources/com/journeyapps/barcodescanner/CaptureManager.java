package com.journeyapps.barcodescanner;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.BeepManager;
import com.google.zxing.client.android.InactivityTimer;
import com.google.zxing.client.android.Intents;
import com.google.zxing.client.android.R;
import com.journeyapps.barcodescanner.CameraPreview;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class CaptureManager {
    public static int m = 250;
    public Activity a;
    public DecoratedBarcodeView b;
    public InactivityTimer f;
    public BeepManager g;
    public Handler h;
    public int c = -1;
    public boolean d = false;
    public boolean e = false;
    public boolean i = false;
    public BarcodeCallback j = new a();
    public final CameraPreview.StateListener k = new b();
    public boolean l = false;

    public class a implements BarcodeCallback {

        /* renamed from: com.journeyapps.barcodescanner.CaptureManager$a$a, reason: collision with other inner class name */
        public class RunnableC0010a implements Runnable {
            public final /* synthetic */ BarcodeResult a;

            public RunnableC0010a(BarcodeResult barcodeResult) {
                this.a = barcodeResult;
            }

            @Override // java.lang.Runnable
            public void run() throws IOException {
                CaptureManager.this.returnResult(this.a);
            }
        }

        public a() {
        }

        @Override // com.journeyapps.barcodescanner.BarcodeCallback
        public void barcodeResult(BarcodeResult barcodeResult) {
            CaptureManager.this.b.pause();
            CaptureManager.this.g.playBeepSoundAndVibrate();
            CaptureManager.this.h.post(new RunnableC0010a(barcodeResult));
        }

        @Override // com.journeyapps.barcodescanner.BarcodeCallback
        public void possibleResultPoints(List<ResultPoint> list) {
        }
    }

    public class b implements CameraPreview.StateListener {
        public b() {
        }

        @Override // com.journeyapps.barcodescanner.CameraPreview.StateListener
        public void cameraClosed() {
            if (CaptureManager.this.i) {
                Log.d("CaptureManager", "Camera closed; finishing activity");
                CaptureManager.this.a.finish();
            }
        }

        @Override // com.journeyapps.barcodescanner.CameraPreview.StateListener
        public void cameraError(Exception exc) {
            CaptureManager.this.displayFrameworkBugMessageAndExit();
        }

        @Override // com.journeyapps.barcodescanner.CameraPreview.StateListener
        public void previewSized() {
        }

        @Override // com.journeyapps.barcodescanner.CameraPreview.StateListener
        public void previewStarted() {
        }

        @Override // com.journeyapps.barcodescanner.CameraPreview.StateListener
        public void previewStopped() {
        }
    }

    public class c implements Runnable {
        public c() {
        }

        @Override // java.lang.Runnable
        public void run() {
            Log.d("CaptureManager", "Finishing due to inactivity");
            CaptureManager.this.a.finish();
        }
    }

    public class d implements Runnable {
        public d() {
        }

        @Override // java.lang.Runnable
        public void run() {
            CaptureManager.this.returnResultTimeout();
        }
    }

    public class e implements DialogInterface.OnClickListener {
        public e() {
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            CaptureManager.this.a.finish();
        }
    }

    public class f implements DialogInterface.OnCancelListener {
        public f() {
        }

        @Override // android.content.DialogInterface.OnCancelListener
        public void onCancel(DialogInterface dialogInterface) {
            CaptureManager.this.a.finish();
        }
    }

    public CaptureManager(Activity activity2, DecoratedBarcodeView decoratedBarcodeView) {
        this.a = activity2;
        this.b = decoratedBarcodeView;
        decoratedBarcodeView.getBarcodeView().addStateListener(this.k);
        this.h = new Handler();
        this.f = new InactivityTimer(activity2, new c());
        this.g = new BeepManager(activity2);
    }

    public static int getCameraPermissionReqCode() {
        return m;
    }

    public static Intent resultIntent(BarcodeResult barcodeResult, String str) {
        Intent intent = new Intent(Intents.Scan.ACTION);
        intent.addFlags(524288);
        intent.putExtra(Intents.Scan.RESULT, barcodeResult.toString());
        intent.putExtra(Intents.Scan.RESULT_FORMAT, barcodeResult.getBarcodeFormat().toString());
        byte[] rawBytes = barcodeResult.getRawBytes();
        if (rawBytes != null && rawBytes.length > 0) {
            intent.putExtra(Intents.Scan.RESULT_BYTES, rawBytes);
        }
        Map<ResultMetadataType, Object> resultMetadata = barcodeResult.getResultMetadata();
        if (resultMetadata != null) {
            if (resultMetadata.containsKey(ResultMetadataType.UPC_EAN_EXTENSION)) {
                intent.putExtra(Intents.Scan.RESULT_UPC_EAN_EXTENSION, resultMetadata.get(ResultMetadataType.UPC_EAN_EXTENSION).toString());
            }
            Number number = (Number) resultMetadata.get(ResultMetadataType.ORIENTATION);
            if (number != null) {
                intent.putExtra(Intents.Scan.RESULT_ORIENTATION, number.intValue());
            }
            String str2 = (String) resultMetadata.get(ResultMetadataType.ERROR_CORRECTION_LEVEL);
            if (str2 != null) {
                intent.putExtra(Intents.Scan.RESULT_ERROR_CORRECTION_LEVEL, str2);
            }
            Iterable iterable = (Iterable) resultMetadata.get(ResultMetadataType.BYTE_SEGMENTS);
            if (iterable != null) {
                int i = 0;
                Iterator it = iterable.iterator();
                while (it.hasNext()) {
                    intent.putExtra(Intents.Scan.RESULT_BYTE_SEGMENTS_PREFIX + i, (byte[]) it.next());
                    i++;
                }
            }
        }
        if (str != null) {
            intent.putExtra(Intents.Scan.RESULT_BARCODE_IMAGE_PATH, str);
        }
        return intent;
    }

    public static void setCameraPermissionReqCode(int i) {
        m = i;
    }

    public void closeAndFinish() {
        if (this.b.getBarcodeView().isCameraClosed()) {
            this.a.finish();
        } else {
            this.i = true;
        }
        this.b.pause();
        this.f.cancel();
    }

    public void decode() {
        this.b.decodeSingle(this.j);
    }

    public void displayFrameworkBugMessageAndExit() {
        if (this.a.isFinishing() || this.e || this.i) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this.a);
        builder.setTitle(this.a.getString(R.string.zxing_app_name));
        builder.setMessage(this.a.getString(R.string.zxing_msg_camera_framework_bug));
        builder.setPositiveButton(R.string.zxing_button_ok, new e());
        builder.setOnCancelListener(new f());
        builder.show();
    }

    public void initializeFromIntent(Intent intent, Bundle bundle) {
        this.a.getWindow().addFlags(128);
        if (bundle != null) {
            this.c = bundle.getInt("SAVED_ORIENTATION_LOCK", -1);
        }
        if (intent != null) {
            if (intent.getBooleanExtra(Intents.Scan.ORIENTATION_LOCKED, true)) {
                lockOrientation();
            }
            if (Intents.Scan.ACTION.equals(intent.getAction())) {
                this.b.initializeFromIntent(intent);
            }
            if (!intent.getBooleanExtra(Intents.Scan.BEEP_ENABLED, true)) {
                this.g.setBeepEnabled(false);
            }
            if (intent.hasExtra(Intents.Scan.TIMEOUT)) {
                this.h.postDelayed(new d(), intent.getLongExtra(Intents.Scan.TIMEOUT, 0L));
            }
            if (intent.getBooleanExtra(Intents.Scan.BARCODE_IMAGE_ENABLED, false)) {
                this.d = true;
            }
        }
    }

    public void lockOrientation() {
        if (this.c == -1) {
            int rotation = this.a.getWindowManager().getDefaultDisplay().getRotation();
            int i = this.a.getResources().getConfiguration().orientation;
            int i2 = 0;
            if (i == 2) {
                if (rotation != 0 && rotation != 1) {
                    i2 = 8;
                }
            } else if (i == 1) {
                i2 = (rotation == 0 || rotation == 3) ? 1 : 9;
            }
            this.c = i2;
        }
        this.a.setRequestedOrientation(this.c);
    }

    public void onDestroy() {
        this.e = true;
        this.f.cancel();
        this.h.removeCallbacksAndMessages(null);
    }

    public void onPause() throws InterruptedException {
        this.f.cancel();
        this.b.pauseAndWait();
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        if (i == m) {
            if (iArr.length <= 0 || iArr[0] != 0) {
                displayFrameworkBugMessageAndExit();
            } else {
                this.b.resume();
            }
        }
    }

    public void onResume() {
        if (ContextCompat.checkSelfPermission(this.a, "android.permission.CAMERA") == 0) {
            this.b.resume();
        } else if (!this.l) {
            ActivityCompat.requestPermissions(this.a, new String[]{"android.permission.CAMERA"}, m);
            this.l = true;
        }
        this.f.start();
    }

    public void onSaveInstanceState(Bundle bundle) {
        bundle.putInt("SAVED_ORIENTATION_LOCK", this.c);
    }

    public void returnResult(BarcodeResult barcodeResult) throws IOException {
        String absolutePath;
        if (this.d) {
            Bitmap bitmap = barcodeResult.getBitmap();
            try {
                File fileCreateTempFile = File.createTempFile("barcodeimage", ".jpg", this.a.getCacheDir());
                FileOutputStream fileOutputStream = new FileOutputStream(fileCreateTempFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                fileOutputStream.close();
                absolutePath = fileCreateTempFile.getAbsolutePath();
            } catch (IOException e2) {
                Log.w("CaptureManager", "Unable to create temporary file and store bitmap! " + e2);
            }
        } else {
            absolutePath = null;
        }
        this.a.setResult(-1, resultIntent(barcodeResult, absolutePath));
        closeAndFinish();
    }

    public void returnResultTimeout() {
        Intent intent = new Intent(Intents.Scan.ACTION);
        intent.putExtra(Intents.Scan.TIMEOUT, true);
        this.a.setResult(0, intent);
        closeAndFinish();
    }
}
