package defpackage;

import android.R;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AlertController;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.CursorAdapter;

/* loaded from: classes.dex */
public class l6 extends CursorAdapter {
    public final int a;
    public final int b;
    public final /* synthetic */ AlertController.RecycleListView c;
    public final /* synthetic */ AlertController d;
    public final /* synthetic */ AlertController.AlertParams e;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public l6(AlertController.AlertParams alertParams, Context context, Cursor cursor, boolean z, AlertController.RecycleListView recycleListView, AlertController alertController) {
        super(context, cursor, z);
        this.e = alertParams;
        this.c = recycleListView;
        this.d = alertController;
        Cursor cursor2 = getCursor();
        this.a = cursor2.getColumnIndexOrThrow(this.e.mLabelColumn);
        this.b = cursor2.getColumnIndexOrThrow(this.e.mIsCheckedColumn);
    }

    @Override // android.widget.CursorAdapter
    public void bindView(View view2, Context context, Cursor cursor) {
        ((CheckedTextView) view2.findViewById(R.id.text1)).setText(cursor.getString(this.a));
        this.c.setItemChecked(cursor.getPosition(), cursor.getInt(this.b) == 1);
    }

    @Override // android.widget.CursorAdapter
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return this.e.mInflater.inflate(this.d.M, viewGroup, false);
    }
}
