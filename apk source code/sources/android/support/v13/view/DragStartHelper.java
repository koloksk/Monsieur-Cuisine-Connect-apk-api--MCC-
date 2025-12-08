package android.support.v13.view;

import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;

/* loaded from: classes.dex */
public class DragStartHelper {
    public final View a;
    public final OnDragStartListener b;
    public int c;
    public int d;
    public boolean e;
    public final View.OnLongClickListener f = new a();
    public final View.OnTouchListener g = new b();

    public interface OnDragStartListener {
        boolean onDragStart(View view2, DragStartHelper dragStartHelper);
    }

    public class a implements View.OnLongClickListener {
        public a() {
        }

        @Override // android.view.View.OnLongClickListener
        public boolean onLongClick(View view2) {
            return DragStartHelper.this.onLongClick(view2);
        }
    }

    public class b implements View.OnTouchListener {
        public b() {
        }

        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View view2, MotionEvent motionEvent) {
            return DragStartHelper.this.onTouch(view2, motionEvent);
        }
    }

    public DragStartHelper(View view2, OnDragStartListener onDragStartListener) {
        this.a = view2;
        this.b = onDragStartListener;
    }

    public void attach() {
        this.a.setOnLongClickListener(this.f);
        this.a.setOnTouchListener(this.g);
    }

    public void detach() {
        this.a.setOnLongClickListener(null);
        this.a.setOnTouchListener(null);
    }

    public void getTouchPosition(Point point) {
        point.set(this.c, this.d);
    }

    public boolean onLongClick(View view2) {
        return this.b.onDragStart(view2, this);
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0046  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouch(android.view.View r7, android.view.MotionEvent r8) {
        /*
            r6 = this;
            float r0 = r8.getX()
            int r0 = (int) r0
            float r1 = r8.getY()
            int r1 = (int) r1
            int r2 = r8.getAction()
            r3 = 0
            if (r2 == 0) goto L49
            r4 = 1
            if (r2 == r4) goto L46
            r5 = 2
            if (r2 == r5) goto L1b
            r7 = 3
            if (r2 == r7) goto L46
            goto L4d
        L1b:
            r2 = 8194(0x2002, float:1.1482E-41)
            boolean r2 = android.support.v4.view.MotionEventCompat.isFromSource(r8, r2)
            if (r2 == 0) goto L4d
            int r8 = r8.getButtonState()
            r8 = r8 & r4
            if (r8 != 0) goto L2b
            goto L4d
        L2b:
            boolean r8 = r6.e
            if (r8 == 0) goto L30
            goto L4d
        L30:
            int r8 = r6.c
            if (r8 != r0) goto L39
            int r8 = r6.d
            if (r8 != r1) goto L39
            goto L4d
        L39:
            r6.c = r0
            r6.d = r1
            android.support.v13.view.DragStartHelper$OnDragStartListener r8 = r6.b
            boolean r7 = r8.onDragStart(r7, r6)
            r6.e = r7
            return r7
        L46:
            r6.e = r3
            goto L4d
        L49:
            r6.c = r0
            r6.d = r1
        L4d:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v13.view.DragStartHelper.onTouch(android.view.View, android.view.MotionEvent):boolean");
    }
}
