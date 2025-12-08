package android.support.v4.widget;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.RestrictTo;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/* loaded from: classes.dex */
public class SimpleCursorAdapter extends ResourceCursorAdapter {
    public int d;
    public CursorToStringConverter e;
    public ViewBinder f;
    public String[] g;

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public int[] mFrom;

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public int[] mTo;

    public interface CursorToStringConverter {
        CharSequence convertToString(Cursor cursor);
    }

    public interface ViewBinder {
        boolean setViewValue(View view2, Cursor cursor, int i);
    }

    @Deprecated
    public SimpleCursorAdapter(Context context, int i, Cursor cursor, String[] strArr, int[] iArr) {
        super(context, i, cursor);
        this.d = -1;
        this.mTo = iArr;
        this.g = strArr;
        a(cursor, strArr);
    }

    public final void a(Cursor cursor, String[] strArr) {
        if (cursor == null) {
            this.mFrom = null;
            return;
        }
        int length = strArr.length;
        int[] iArr = this.mFrom;
        if (iArr == null || iArr.length != length) {
            this.mFrom = new int[length];
        }
        for (int i = 0; i < length; i++) {
            this.mFrom[i] = cursor.getColumnIndexOrThrow(strArr[i]);
        }
    }

    @Override // android.support.v4.widget.CursorAdapter
    public void bindView(View view2, Context context, Cursor cursor) {
        ViewBinder viewBinder = this.f;
        int[] iArr = this.mTo;
        int length = iArr.length;
        int[] iArr2 = this.mFrom;
        for (int i = 0; i < length; i++) {
            View viewFindViewById = view2.findViewById(iArr[i]);
            if (viewFindViewById != null) {
                if (viewBinder != null ? viewBinder.setViewValue(viewFindViewById, cursor, iArr2[i]) : false) {
                    continue;
                } else {
                    String string = cursor.getString(iArr2[i]);
                    if (string == null) {
                        string = "";
                    }
                    if (viewFindViewById instanceof TextView) {
                        setViewText((TextView) viewFindViewById, string);
                    } else {
                        if (!(viewFindViewById instanceof ImageView)) {
                            throw new IllegalStateException(viewFindViewById.getClass().getName() + " is not a  view that can be bounds by this SimpleCursorAdapter");
                        }
                        setViewImage((ImageView) viewFindViewById, string);
                    }
                }
            }
        }
    }

    public void changeCursorAndColumns(Cursor cursor, String[] strArr, int[] iArr) {
        this.g = strArr;
        this.mTo = iArr;
        a(cursor, strArr);
        super.changeCursor(cursor);
    }

    @Override // android.support.v4.widget.CursorAdapter, g6.a
    public CharSequence convertToString(Cursor cursor) {
        CursorToStringConverter cursorToStringConverter = this.e;
        if (cursorToStringConverter != null) {
            return cursorToStringConverter.convertToString(cursor);
        }
        int i = this.d;
        return i > -1 ? cursor.getString(i) : super.convertToString(cursor);
    }

    public CursorToStringConverter getCursorToStringConverter() {
        return this.e;
    }

    public int getStringConversionColumn() {
        return this.d;
    }

    public ViewBinder getViewBinder() {
        return this.f;
    }

    public void setCursorToStringConverter(CursorToStringConverter cursorToStringConverter) {
        this.e = cursorToStringConverter;
    }

    public void setStringConversionColumn(int i) {
        this.d = i;
    }

    public void setViewBinder(ViewBinder viewBinder) {
        this.f = viewBinder;
    }

    public void setViewImage(ImageView imageView, String str) {
        try {
            imageView.setImageResource(Integer.parseInt(str));
        } catch (NumberFormatException unused) {
            imageView.setImageURI(Uri.parse(str));
        }
    }

    public void setViewText(TextView textView, String str) {
        textView.setText(str);
    }

    @Override // android.support.v4.widget.CursorAdapter
    public Cursor swapCursor(Cursor cursor) {
        a(cursor, this.g);
        return super.swapCursor(cursor);
    }

    public SimpleCursorAdapter(Context context, int i, Cursor cursor, String[] strArr, int[] iArr, int i2) {
        super(context, i, cursor, i2);
        this.d = -1;
        this.mTo = iArr;
        this.g = strArr;
        a(cursor, strArr);
    }
}
