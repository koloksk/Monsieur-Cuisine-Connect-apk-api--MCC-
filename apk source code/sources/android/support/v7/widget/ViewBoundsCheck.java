package android.support.v7.widget;

import android.view.View;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public class ViewBoundsCheck {
    public final b a;
    public a b = new a();

    @Retention(RetentionPolicy.SOURCE)
    public @interface ViewBounds {
    }

    public static class a {
        public int a = 0;
        public int b;
        public int c;
        public int d;
        public int e;

        public int a(int i, int i2) {
            if (i > i2) {
                return 1;
            }
            return i == i2 ? 2 : 4;
        }

        public boolean a() {
            int i = this.a;
            if ((i & 7) != 0 && (i & (a(this.d, this.b) << 0)) == 0) {
                return false;
            }
            int i2 = this.a;
            if ((i2 & 112) != 0 && (i2 & (a(this.d, this.c) << 4)) == 0) {
                return false;
            }
            int i3 = this.a;
            if ((i3 & 1792) != 0 && (i3 & (a(this.e, this.b) << 8)) == 0) {
                return false;
            }
            int i4 = this.a;
            return (i4 & 28672) == 0 || (i4 & (a(this.e, this.c) << 12)) != 0;
        }
    }

    public interface b {
        int a();

        int a(View view2);

        View a(int i);

        int b();

        int b(View view2);
    }

    public ViewBoundsCheck(b bVar) {
        this.a = bVar;
    }

    public View a(int i, int i2, int i3, int i4) {
        int iB = this.a.b();
        int iA = this.a.a();
        int i5 = i2 > i ? 1 : -1;
        View view2 = null;
        while (i != i2) {
            View viewA = this.a.a(i);
            int iA2 = this.a.a(viewA);
            int iB2 = this.a.b(viewA);
            a aVar = this.b;
            aVar.b = iB;
            aVar.c = iA;
            aVar.d = iA2;
            aVar.e = iB2;
            if (i3 != 0) {
                aVar.a = 0;
                aVar.a = i3 | 0;
                if (aVar.a()) {
                    return viewA;
                }
            }
            if (i4 != 0) {
                a aVar2 = this.b;
                aVar2.a = 0;
                aVar2.a = i4 | 0;
                if (aVar2.a()) {
                    view2 = viewA;
                }
            }
            i += i5;
        }
        return view2;
    }

    public boolean a(View view2, int i) {
        a aVar = this.b;
        int iB = this.a.b();
        int iA = this.a.a();
        int iA2 = this.a.a(view2);
        int iB2 = this.a.b(view2);
        aVar.b = iB;
        aVar.c = iA;
        aVar.d = iA2;
        aVar.e = iB2;
        if (i == 0) {
            return false;
        }
        a aVar2 = this.b;
        aVar2.a = 0;
        aVar2.a = 0 | i;
        return aVar2.a();
    }
}
