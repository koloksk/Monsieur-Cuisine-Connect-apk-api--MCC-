package defpackage;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatDrawableManager;
import java.lang.ref.WeakReference;

/* loaded from: classes.dex */
public class v8 extends o8 {
    public final WeakReference<Context> b;

    public v8(@NonNull Context context, @NonNull Resources resources) {
        super(resources);
        this.b = new WeakReference<>(context);
    }

    @Override // android.content.res.Resources
    public Drawable getDrawable(int i) throws Resources.NotFoundException {
        Drawable drawable = this.a.getDrawable(i);
        Context context = this.b.get();
        if (drawable != null && context != null) {
            AppCompatDrawableManager.get();
            AppCompatDrawableManager.a(context, i, drawable);
        }
        return drawable;
    }
}
