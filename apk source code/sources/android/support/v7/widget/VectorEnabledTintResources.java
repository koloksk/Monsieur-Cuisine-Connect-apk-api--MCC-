package android.support.v7.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.v7.app.AppCompatDelegate;
import java.io.IOException;
import java.lang.ref.WeakReference;
import org.xmlpull.v1.XmlPullParserException;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class VectorEnabledTintResources extends Resources {
    public static final int MAX_SDK_WHERE_REQUIRED = 20;
    public final WeakReference<Context> a;

    public VectorEnabledTintResources(@NonNull Context context, @NonNull Resources resources) {
        super(resources.getAssets(), resources.getDisplayMetrics(), resources.getConfiguration());
        this.a = new WeakReference<>(context);
    }

    public static boolean shouldBeUsed() {
        AppCompatDelegate.isCompatVectorFromResourcesEnabled();
        return false;
    }

    public final Drawable a(int i) {
        return super.getDrawable(i);
    }

    @Override // android.content.res.Resources
    public Drawable getDrawable(int i) throws XmlPullParserException, Resources.NotFoundException, IOException {
        Context context = this.a.get();
        if (context == null) {
            return super.getDrawable(i);
        }
        AppCompatDrawableManager appCompatDrawableManager = AppCompatDrawableManager.get();
        Drawable drawableC = appCompatDrawableManager.c(context, i);
        if (drawableC == null) {
            drawableC = a(i);
        }
        if (drawableC != null) {
            return appCompatDrawableManager.a(context, i, false, drawableC);
        }
        return null;
    }
}
