package defpackage;

import android.graphics.SurfaceTexture;
import android.view.TextureView;
import com.journeyapps.barcodescanner.CameraPreview;
import com.journeyapps.barcodescanner.Size;

/* loaded from: classes.dex */
public class tc implements TextureView.SurfaceTextureListener {
    public final /* synthetic */ CameraPreview a;

    public tc(CameraPreview cameraPreview) {
        this.a = cameraPreview;
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
        this.a.p = new Size(i, i2);
        this.a.a();
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        return false;
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
        this.a.p = new Size(i, i2);
        this.a.a();
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
    }
}
