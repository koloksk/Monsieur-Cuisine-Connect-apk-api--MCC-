package android.support.transition;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes.dex */
public class TransitionManager {
    public static Transition c = new AutoTransition();
    public static ThreadLocal<WeakReference<ArrayMap<ViewGroup, ArrayList<Transition>>>> d = new ThreadLocal<>();
    public static ArrayList<ViewGroup> e = new ArrayList<>();
    public ArrayMap<Scene, Transition> a = new ArrayMap<>();
    public ArrayMap<Scene, ArrayMap<Scene, Transition>> b = new ArrayMap<>();

    public static class a implements ViewTreeObserver.OnPreDrawListener, View.OnAttachStateChangeListener {
        public Transition a;
        public ViewGroup b;

        /* renamed from: android.support.transition.TransitionManager$a$a, reason: collision with other inner class name */
        public class C0000a extends TransitionListenerAdapter {
            public final /* synthetic */ ArrayMap a;

            public C0000a(ArrayMap arrayMap) {
                this.a = arrayMap;
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.support.transition.TransitionListenerAdapter, android.support.transition.Transition.TransitionListener
            public void onTransitionEnd(@NonNull Transition transition) {
                ((ArrayList) this.a.get(a.this.b)).remove(transition);
            }
        }

        public a(Transition transition, ViewGroup viewGroup) {
            this.a = transition;
            this.b = viewGroup;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:136:0x02a8  */
        /* JADX WARN: Removed duplicated region for block: B:14:0x005b  */
        /* JADX WARN: Removed duplicated region for block: B:20:0x0077  */
        @Override // android.view.ViewTreeObserver.OnPreDrawListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public boolean onPreDraw() {
            /*
                Method dump skipped, instructions count: 682
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.transition.TransitionManager.a.onPreDraw():boolean");
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewAttachedToWindow(View view2) {
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewDetachedFromWindow(View view2) {
            this.b.getViewTreeObserver().removeOnPreDrawListener(this);
            this.b.removeOnAttachStateChangeListener(this);
            TransitionManager.e.remove(this.b);
            ArrayList<Transition> arrayList = TransitionManager.a().get(this.b);
            if (arrayList != null && arrayList.size() > 0) {
                Iterator<Transition> it = arrayList.iterator();
                while (it.hasNext()) {
                    it.next().resume(this.b);
                }
            }
            this.a.a(true);
        }
    }

    public static void a(Scene scene, Transition transition) {
        ViewGroup sceneRoot = scene.getSceneRoot();
        if (e.contains(sceneRoot)) {
            return;
        }
        if (transition == null) {
            scene.enter();
            return;
        }
        e.add(sceneRoot);
        Transition transitionMo2clone = transition.mo2clone();
        transitionMo2clone.b(sceneRoot);
        Scene sceneA = Scene.a(sceneRoot);
        if (sceneA != null) {
            if (sceneA.b > 0) {
                transitionMo2clone.b(true);
            }
        }
        a(sceneRoot, transitionMo2clone);
        scene.enter();
        if (sceneRoot != null) {
            a aVar = new a(transitionMo2clone, sceneRoot);
            sceneRoot.addOnAttachStateChangeListener(aVar);
            sceneRoot.getViewTreeObserver().addOnPreDrawListener(aVar);
        }
    }

    public static void beginDelayedTransition(@NonNull ViewGroup viewGroup) {
        beginDelayedTransition(viewGroup, null);
    }

    public static void endTransitions(ViewGroup viewGroup) {
        e.remove(viewGroup);
        ArrayList<Transition> arrayList = a().get(viewGroup);
        if (arrayList == null || arrayList.isEmpty()) {
            return;
        }
        ArrayList arrayList2 = new ArrayList(arrayList);
        for (int size = arrayList2.size() - 1; size >= 0; size--) {
            ((Transition) arrayList2.get(size)).a(viewGroup);
        }
    }

    public static void go(@NonNull Scene scene) {
        a(scene, c);
    }

    public void setTransition(@NonNull Scene scene, @Nullable Transition transition) {
        this.a.put(scene, transition);
    }

    public void transitionTo(@NonNull Scene scene) {
        Transition transition;
        Scene scene2;
        ArrayMap<Scene, Transition> arrayMap;
        ViewGroup sceneRoot = scene.getSceneRoot();
        if ((sceneRoot == null || (scene2 = (Scene) sceneRoot.getTag(R.id.transition_current_scene)) == null || (arrayMap = this.b.get(scene)) == null || (transition = arrayMap.get(scene2)) == null) && (transition = this.a.get(scene)) == null) {
            transition = c;
        }
        a(scene, transition);
    }

    public static void beginDelayedTransition(@NonNull ViewGroup viewGroup, @Nullable Transition transition) {
        if (e.contains(viewGroup) || !ViewCompat.isLaidOut(viewGroup)) {
            return;
        }
        e.add(viewGroup);
        if (transition == null) {
            transition = c;
        }
        Transition transitionMo2clone = transition.mo2clone();
        a(viewGroup, transitionMo2clone);
        viewGroup.setTag(R.id.transition_current_scene, null);
        if (transitionMo2clone != null) {
            a aVar = new a(transitionMo2clone, viewGroup);
            viewGroup.addOnAttachStateChangeListener(aVar);
            viewGroup.getViewTreeObserver().addOnPreDrawListener(aVar);
        }
    }

    public static void go(@NonNull Scene scene, @Nullable Transition transition) {
        a(scene, transition);
    }

    public void setTransition(@NonNull Scene scene, @NonNull Scene scene2, @Nullable Transition transition) {
        ArrayMap<Scene, Transition> arrayMap = this.b.get(scene2);
        if (arrayMap == null) {
            arrayMap = new ArrayMap<>();
            this.b.put(scene2, arrayMap);
        }
        arrayMap.put(scene, transition);
    }

    public static ArrayMap<ViewGroup, ArrayList<Transition>> a() {
        WeakReference<ArrayMap<ViewGroup, ArrayList<Transition>>> weakReference = d.get();
        if (weakReference == null || weakReference.get() == null) {
            WeakReference<ArrayMap<ViewGroup, ArrayList<Transition>>> weakReference2 = new WeakReference<>(new ArrayMap());
            d.set(weakReference2);
            weakReference = weakReference2;
        }
        return weakReference.get();
    }

    public static void a(ViewGroup viewGroup, Transition transition) {
        ArrayList<Transition> arrayList = a().get(viewGroup);
        if (arrayList != null && arrayList.size() > 0) {
            Iterator<Transition> it = arrayList.iterator();
            while (it.hasNext()) {
                it.next().pause(viewGroup);
            }
        }
        if (transition != null) {
            transition.a(viewGroup, true);
        }
        Scene scene = (Scene) viewGroup.getTag(R.id.transition_current_scene);
        if (scene != null) {
            scene.exit();
        }
    }
}
