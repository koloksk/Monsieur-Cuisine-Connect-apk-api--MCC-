package android.support.transition;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/* loaded from: classes.dex */
public class Scene {
    public Context a;
    public int b;
    public ViewGroup c;
    public View d;
    public Runnable e;
    public Runnable f;

    public Scene(@NonNull ViewGroup viewGroup) {
        this.b = -1;
        this.c = viewGroup;
    }

    public static Scene a(View view2) {
        return (Scene) view2.getTag(R.id.transition_current_scene);
    }

    @NonNull
    public static Scene getSceneForLayout(@NonNull ViewGroup viewGroup, @LayoutRes int i, @NonNull Context context) {
        SparseArray sparseArray = (SparseArray) viewGroup.getTag(R.id.transition_scene_layoutid_cache);
        if (sparseArray == null) {
            sparseArray = new SparseArray();
            viewGroup.setTag(R.id.transition_scene_layoutid_cache, sparseArray);
        }
        Scene scene = (Scene) sparseArray.get(i);
        if (scene != null) {
            return scene;
        }
        Scene scene2 = new Scene(viewGroup, i, context);
        sparseArray.put(i, scene2);
        return scene2;
    }

    public void enter() {
        if (this.b > 0 || this.d != null) {
            getSceneRoot().removeAllViews();
            if (this.b > 0) {
                LayoutInflater.from(this.a).inflate(this.b, this.c);
            } else {
                this.c.addView(this.d);
            }
        }
        Runnable runnable = this.e;
        if (runnable != null) {
            runnable.run();
        }
        this.c.setTag(R.id.transition_current_scene, this);
    }

    public void exit() {
        Runnable runnable;
        if (a(this.c) != this || (runnable = this.f) == null) {
            return;
        }
        runnable.run();
    }

    @NonNull
    public ViewGroup getSceneRoot() {
        return this.c;
    }

    public void setEnterAction(@Nullable Runnable runnable) {
        this.e = runnable;
    }

    public void setExitAction(@Nullable Runnable runnable) {
        this.f = runnable;
    }

    public Scene(ViewGroup viewGroup, int i, Context context) {
        this.b = -1;
        this.a = context;
        this.c = viewGroup;
        this.b = i;
    }

    public Scene(@NonNull ViewGroup viewGroup, @NonNull View view2) {
        this.b = -1;
        this.c = viewGroup;
        this.d = view2;
    }
}
