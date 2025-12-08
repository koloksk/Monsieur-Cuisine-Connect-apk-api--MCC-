package defpackage;

import android.content.Context;
import android.databinding.ObservableList;
import android.support.annotation.RestrictTo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
public class p1<T> extends BaseAdapter {
    public List<T> a;
    public ObservableList.OnListChangedCallback b;
    public final Context c;
    public final int d;
    public final int e;
    public final int f;
    public final LayoutInflater g;

    public class a extends ObservableList.OnListChangedCallback {
        public a() {
        }

        @Override // android.databinding.ObservableList.OnListChangedCallback
        public void onChanged(ObservableList observableList) {
            p1.this.notifyDataSetChanged();
        }

        @Override // android.databinding.ObservableList.OnListChangedCallback
        public void onItemRangeChanged(ObservableList observableList, int i, int i2) {
            p1.this.notifyDataSetChanged();
        }

        @Override // android.databinding.ObservableList.OnListChangedCallback
        public void onItemRangeInserted(ObservableList observableList, int i, int i2) {
            p1.this.notifyDataSetChanged();
        }

        @Override // android.databinding.ObservableList.OnListChangedCallback
        public void onItemRangeMoved(ObservableList observableList, int i, int i2, int i3) {
            p1.this.notifyDataSetChanged();
        }

        @Override // android.databinding.ObservableList.OnListChangedCallback
        public void onItemRangeRemoved(ObservableList observableList, int i, int i2) {
            p1.this.notifyDataSetChanged();
        }
    }

    public p1(Context context, List<T> list, int i, int i2, int i3) {
        this.c = context;
        this.e = i;
        this.d = i2;
        this.f = i3;
        this.g = i == 0 ? null : (LayoutInflater) context.getSystemService("layout_inflater");
        a(list);
    }

    public void a(List<T> list) {
        List<T> list2 = this.a;
        if (list2 == list) {
            return;
        }
        if (list2 instanceof ObservableList) {
            ((ObservableList) list2).removeOnListChangedCallback(this.b);
        }
        this.a = list;
        if (list instanceof ObservableList) {
            if (this.b == null) {
                this.b = new a();
            }
            ((ObservableList) this.a).addOnListChangedCallback(this.b);
        }
        notifyDataSetChanged();
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.a.size();
    }

    @Override // android.widget.BaseAdapter, android.widget.SpinnerAdapter
    public View getDropDownView(int i, View view2, ViewGroup viewGroup) {
        return a(this.d, i, view2, viewGroup);
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        return this.a.get(i);
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view2, ViewGroup viewGroup) {
        return a(this.e, i, view2, viewGroup);
    }

    public View a(int i, int i2, View view2, ViewGroup viewGroup) {
        CharSequence charSequenceValueOf;
        if (view2 == null) {
            if (i == 0) {
                view2 = new TextView(this.c);
            } else {
                view2 = this.g.inflate(i, viewGroup, false);
            }
        }
        int i3 = this.f;
        TextView textView = (TextView) (i3 == 0 ? view2 : view2.findViewById(i3));
        T t = this.a.get(i2);
        if (t instanceof CharSequence) {
            charSequenceValueOf = (CharSequence) t;
        } else {
            charSequenceValueOf = String.valueOf(t);
        }
        textView.setText(charSequenceValueOf);
        return view2;
    }
}
