package view;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/* loaded from: classes.dex */
public class QuicksandTextView extends AppCompatTextView {
    public static Typeface c;
    public static Typeface d;

    public QuicksandTextView(@NonNull Context context) {
        super(context);
        a(context);
    }

    public final void a(@NonNull Context context) {
        if (isInEditMode()) {
            return;
        }
        synchronized (this) {
            if (c == null) {
                c = Typeface.createFromAsset(context.getAssets(), "fonts/quicksand_regular.ttf");
            }
            if (d == null) {
                d = Typeface.createFromAsset(context.getAssets(), "fonts/quicksand_bold.ttf");
            }
            if (getTypeface() != null) {
                super.setTypeface(getTypeface().isBold() ? d : c);
            } else {
                super.setTypeface(c);
            }
        }
    }

    @Override // android.widget.TextView
    public void setTypeface(@Nullable Typeface typeface, int i) {
        boolean z = true;
        if ((i & 1) != 1 && (typeface == null || !typeface.isBold())) {
            z = false;
        }
        super.setTypeface(z ? d : c);
    }

    public QuicksandTextView(@NonNull Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context);
    }

    public QuicksandTextView(@NonNull Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a(context);
    }
}
