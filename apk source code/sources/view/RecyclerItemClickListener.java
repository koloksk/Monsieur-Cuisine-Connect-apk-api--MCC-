package view;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import application.App;

/* loaded from: classes.dex */
public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
    public OnItemClickListener a;
    public GestureDetector b = new GestureDetector(App.getInstance(), new b(null));

    @Nullable
    public View c;
    public int d;

    public interface OnItemClickListener {
        void onItemClick(View view2, int i);

        void onItemLongPress(View view2, int i);
    }

    public static abstract class SimpleOnItemClickListener implements OnItemClickListener {
        @Override // view.RecyclerItemClickListener.OnItemClickListener
        public void onItemClick(View view2, int i) {
        }

        @Override // view.RecyclerItemClickListener.OnItemClickListener
        public void onItemLongPress(View view2, int i) {
        }
    }

    public class b extends GestureDetector.SimpleOnGestureListener {
        public /* synthetic */ b(a aVar) {
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onDown(MotionEvent motionEvent) {
            return true;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public void onLongPress(MotionEvent motionEvent) {
            RecyclerItemClickListener recyclerItemClickListener = RecyclerItemClickListener.this;
            View view2 = recyclerItemClickListener.c;
            if (view2 != null) {
                recyclerItemClickListener.a.onItemLongPress(view2, recyclerItemClickListener.d);
            }
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            RecyclerItemClickListener recyclerItemClickListener = RecyclerItemClickListener.this;
            View view2 = recyclerItemClickListener.c;
            if (view2 == null) {
                return true;
            }
            recyclerItemClickListener.a.onItemClick(view2, recyclerItemClickListener.d);
            return true;
        }
    }

    public RecyclerItemClickListener(OnItemClickListener onItemClickListener) {
        this.a = onItemClickListener;
    }

    @Override // android.support.v7.widget.RecyclerView.OnItemTouchListener
    public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        View viewFindChildViewUnder = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
        this.c = viewFindChildViewUnder;
        this.d = recyclerView.getChildLayoutPosition(viewFindChildViewUnder);
        return this.c != null && this.b.onTouchEvent(motionEvent);
    }

    @Override // android.support.v7.widget.RecyclerView.OnItemTouchListener
    public void onRequestDisallowInterceptTouchEvent(boolean z) {
    }

    @Override // android.support.v7.widget.RecyclerView.OnItemTouchListener
    public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
    }
}
