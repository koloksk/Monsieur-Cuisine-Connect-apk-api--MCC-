package view;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.ToggleButton;

/* loaded from: classes.dex */
public class QuicksandToggleButton extends ToggleButton {
    public static Typeface a;
    public static Typeface b;

    public QuicksandToggleButton(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        a(context);
    }

    public final void a(@NonNull Context context) {
        if (isInEditMode()) {
            return;
        }
        synchronized (this) {
            if (a == null) {
                a = Typeface.createFromAsset(context.getAssets(), "fonts/quicksand_regular.ttf");
            }
            if (b == null) {
                b = Typeface.createFromAsset(context.getAssets(), "fonts/quicksand_bold.ttf");
            }
            setTypeface(getTypeface().isBold() ? b : a);
        }
    }

    public QuicksandToggleButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a(context);
    }

    public QuicksandToggleButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context);
    }

    public QuicksandToggleButton(Context context) {
        super(context);
        a(context);
    }
}
