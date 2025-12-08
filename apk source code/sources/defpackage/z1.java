package defpackage;

import android.graphics.Outline;
import android.support.annotation.RequiresApi;

@RequiresApi(21)
/* loaded from: classes.dex */
public class z1 extends y1 {
    @Override // android.graphics.drawable.Drawable
    public void getOutline(Outline outline) {
        copyBounds(this.b);
        outline.setOval(this.b);
    }
}
