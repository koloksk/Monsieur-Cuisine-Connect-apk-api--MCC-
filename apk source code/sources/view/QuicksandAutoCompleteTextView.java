package view;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.util.AttributeSet;
import db.model.Recipe;

/* loaded from: classes.dex */
public class QuicksandAutoCompleteTextView extends AppCompatAutoCompleteTextView {
    public static Typeface d;
    public static Typeface e;

    public QuicksandAutoCompleteTextView(Context context) {
        super(context);
    }

    @Override // android.widget.AutoCompleteTextView
    public CharSequence convertSelectionToString(Object obj) {
        return obj instanceof Recipe ? ((Recipe) obj).getName() : super.convertSelectionToString(obj);
    }

    @Override // android.widget.AutoCompleteTextView
    public void performFiltering(CharSequence charSequence, int i) {
        super.performFiltering("", i);
    }

    public QuicksandAutoCompleteTextView(@NonNull Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        synchronized (this) {
            if (d == null) {
                d = Typeface.createFromAsset(context.getAssets(), "fonts/quicksand_regular.ttf");
            }
            if (e == null) {
                e = Typeface.createFromAsset(context.getAssets(), "fonts/quicksand_bold.ttf");
            }
            setTypeface(getTypeface().isBold() ? e : d);
        }
    }

    public QuicksandAutoCompleteTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
