package view;

import android.R;
import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import java.util.Arrays;
import java.util.HashSet;

/* loaded from: classes.dex */
public class QuicksandTextInputEditText extends TextInputEditText {
    public static Typeface c;
    public static Typeface d;
    public static HashSet<Integer> e = new HashSet<>(Arrays.asList(Integer.valueOf(R.id.copy), Integer.valueOf(R.id.cut), Integer.valueOf(R.id.paste), Integer.valueOf(R.id.selectAll)));

    public class a implements ActionMode.Callback {
        public a(QuicksandTextInputEditText quicksandTextInputEditText) {
        }

        @Override // android.view.ActionMode.Callback
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            return false;
        }

        @Override // android.view.ActionMode.Callback
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            if (menu == null) {
                return true;
            }
            int size = menu.size();
            for (int i = 0; i < size; i++) {
                int itemId = menu.getItem(i).getItemId();
                if (!QuicksandTextInputEditText.e.contains(Integer.valueOf(itemId))) {
                    menu.removeItem(itemId);
                }
            }
            return true;
        }

        @Override // android.view.ActionMode.Callback
        public void onDestroyActionMode(ActionMode actionMode) {
        }

        @Override // android.view.ActionMode.Callback
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }
    }

    public QuicksandTextInputEditText(@NonNull Context context) {
        super(context);
        a(context);
        a();
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
            setTypeface(getTypeface().isBold() ? d : c);
        }
    }

    public QuicksandTextInputEditText(@NonNull Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context);
        a();
    }

    public QuicksandTextInputEditText(@NonNull Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a(context);
        a();
    }

    public final void a() {
        setCustomSelectionActionModeCallback(new a(this));
    }
}
