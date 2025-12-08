package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import defpackage.a4;
import defpackage.c2;
import defpackage.g9;
import defpackage.i4;
import defpackage.m3;
import defpackage.n3;
import defpackage.p3;
import defpackage.u2;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

/* loaded from: classes.dex */
public abstract class Transition implements Cloneable {
    public static final int[] F = {2, 1, 3, 4};
    public static final PathMotion G = new a();
    public static ThreadLocal<ArrayMap<Animator, c>> H = new ThreadLocal<>();
    public static final int MATCH_ID = 3;
    public static final int MATCH_INSTANCE = 1;
    public static final int MATCH_ITEM_ID = 4;
    public static final int MATCH_NAME = 2;
    public TransitionPropagation C;
    public EpicenterCallback D;
    public ArrayList<TransitionValues> t;
    public ArrayList<TransitionValues> u;
    public String a = getClass().getName();
    public long b = -1;
    public long c = -1;
    public TimeInterpolator d = null;
    public ArrayList<Integer> e = new ArrayList<>();
    public ArrayList<View> f = new ArrayList<>();
    public ArrayList<String> g = null;
    public ArrayList<Class> h = null;
    public ArrayList<Integer> i = null;
    public ArrayList<View> j = null;
    public ArrayList<Class> k = null;
    public ArrayList<String> l = null;
    public ArrayList<Integer> m = null;
    public ArrayList<View> n = null;
    public ArrayList<Class> o = null;
    public p3 p = new p3();
    public p3 q = new p3();
    public TransitionSet r = null;
    public int[] s = F;
    public boolean v = false;
    public ArrayList<Animator> w = new ArrayList<>();
    public int x = 0;
    public boolean y = false;
    public boolean z = false;
    public ArrayList<TransitionListener> A = null;
    public ArrayList<Animator> B = new ArrayList<>();
    public PathMotion E = G;

    public static abstract class EpicenterCallback {
        public abstract Rect onGetEpicenter(@NonNull Transition transition);
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface MatchOrder {
    }

    public interface TransitionListener {
        void onTransitionCancel(@NonNull Transition transition);

        void onTransitionEnd(@NonNull Transition transition);

        void onTransitionPause(@NonNull Transition transition);

        void onTransitionResume(@NonNull Transition transition);

        void onTransitionStart(@NonNull Transition transition);
    }

    public static class a extends PathMotion {
        @Override // android.support.transition.PathMotion
        public Path getPath(float f, float f2, float f3, float f4) {
            Path path = new Path();
            path.moveTo(f, f2);
            path.lineTo(f3, f4);
            return path;
        }
    }

    public class b extends AnimatorListenerAdapter {
        public b() {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            Transition.this.end();
            animator.removeListener(this);
        }
    }

    public static class c {
        public View a;
        public String b;
        public TransitionValues c;
        public i4 d;
        public Transition e;

        public c(View view2, String str, Transition transition, i4 i4Var, TransitionValues transitionValues) {
            this.a = view2;
            this.b = str;
            this.c = transitionValues;
            this.d = i4Var;
            this.e = transition;
        }
    }

    public Transition() {
    }

    public boolean a(View view2) {
        ArrayList<Class> arrayList;
        ArrayList<String> arrayList2;
        int id = view2.getId();
        ArrayList<Integer> arrayList3 = this.i;
        if (arrayList3 != null && arrayList3.contains(Integer.valueOf(id))) {
            return false;
        }
        ArrayList<View> arrayList4 = this.j;
        if (arrayList4 != null && arrayList4.contains(view2)) {
            return false;
        }
        ArrayList<Class> arrayList5 = this.k;
        if (arrayList5 != null) {
            int size = arrayList5.size();
            for (int i = 0; i < size; i++) {
                if (this.k.get(i).isInstance(view2)) {
                    return false;
                }
            }
        }
        if (this.l != null && ViewCompat.getTransitionName(view2) != null && this.l.contains(ViewCompat.getTransitionName(view2))) {
            return false;
        }
        if ((this.e.size() == 0 && this.f.size() == 0 && (((arrayList = this.h) == null || arrayList.isEmpty()) && ((arrayList2 = this.g) == null || arrayList2.isEmpty()))) || this.e.contains(Integer.valueOf(id)) || this.f.contains(view2)) {
            return true;
        }
        ArrayList<String> arrayList6 = this.g;
        if (arrayList6 != null && arrayList6.contains(ViewCompat.getTransitionName(view2))) {
            return true;
        }
        if (this.h != null) {
            for (int i2 = 0; i2 < this.h.size(); i2++) {
                if (this.h.get(i2).isInstance(view2)) {
                    return true;
                }
            }
        }
        return false;
    }

    @NonNull
    public Transition addListener(@NonNull TransitionListener transitionListener) {
        if (this.A == null) {
            this.A = new ArrayList<>();
        }
        this.A.add(transitionListener);
        return this;
    }

    @NonNull
    public Transition addTarget(@NonNull View view2) {
        this.f.add(view2);
        return this;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void animate(Animator animator) {
        if (animator == null) {
            end();
            return;
        }
        if (getDuration() >= 0) {
            animator.setDuration(getDuration());
        }
        if (getStartDelay() >= 0) {
            animator.setStartDelay(getStartDelay());
        }
        if (getInterpolator() != null) {
            animator.setInterpolator(getInterpolator());
        }
        animator.addListener(new b());
        animator.start();
    }

    public Transition b(ViewGroup viewGroup) {
        return this;
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x002e, code lost:
    
        if (r3 < 0) goto L30;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0030, code lost:
    
        if (r8 == false) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0032, code lost:
    
        r7 = r6.u;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0035, code lost:
    
        r7 = r6.t;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x003e, code lost:
    
        return r7.get(r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:?, code lost:
    
        return null;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.support.transition.TransitionValues b(android.view.View r7, boolean r8) {
        /*
            r6 = this;
            android.support.transition.TransitionSet r0 = r6.r
            if (r0 == 0) goto L9
            android.support.transition.TransitionValues r7 = r0.b(r7, r8)
            return r7
        L9:
            if (r8 == 0) goto Le
            java.util.ArrayList<android.support.transition.TransitionValues> r0 = r6.t
            goto L10
        Le:
            java.util.ArrayList<android.support.transition.TransitionValues> r0 = r6.u
        L10:
            r1 = 0
            if (r0 != 0) goto L14
            return r1
        L14:
            int r2 = r0.size()
            r3 = -1
            r4 = 0
        L1a:
            if (r4 >= r2) goto L2e
            java.lang.Object r5 = r0.get(r4)
            android.support.transition.TransitionValues r5 = (android.support.transition.TransitionValues) r5
            if (r5 != 0) goto L25
            return r1
        L25:
            android.view.View r5 = r5.f0view
            if (r5 != r7) goto L2b
            r3 = r4
            goto L2e
        L2b:
            int r4 = r4 + 1
            goto L1a
        L2e:
            if (r3 < 0) goto L3e
            if (r8 == 0) goto L35
            java.util.ArrayList<android.support.transition.TransitionValues> r7 = r6.u
            goto L37
        L35:
            java.util.ArrayList<android.support.transition.TransitionValues> r7 = r6.t
        L37:
            java.lang.Object r7 = r7.get(r3)
            r1 = r7
            android.support.transition.TransitionValues r1 = (android.support.transition.TransitionValues) r1
        L3e:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.transition.Transition.b(android.view.View, boolean):android.support.transition.TransitionValues");
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void cancel() {
        for (int size = this.w.size() - 1; size >= 0; size--) {
            this.w.get(size).cancel();
        }
        ArrayList<TransitionListener> arrayList = this.A;
        if (arrayList == null || arrayList.size() <= 0) {
            return;
        }
        ArrayList arrayList2 = (ArrayList) this.A.clone();
        int size2 = arrayList2.size();
        for (int i = 0; i < size2; i++) {
            ((TransitionListener) arrayList2.get(i)).onTransitionCancel(this);
        }
    }

    public abstract void captureEndValues(@NonNull TransitionValues transitionValues);

    public abstract void captureStartValues(@NonNull TransitionValues transitionValues);

    @Nullable
    public Animator createAnimator(@NonNull ViewGroup viewGroup, @Nullable TransitionValues transitionValues, @Nullable TransitionValues transitionValues2) {
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0043  */
    @android.support.annotation.RestrictTo({android.support.annotation.RestrictTo.Scope.LIBRARY_GROUP})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void createAnimators(android.view.ViewGroup r21, defpackage.p3 r22, defpackage.p3 r23, java.util.ArrayList<android.support.transition.TransitionValues> r24, java.util.ArrayList<android.support.transition.TransitionValues> r25) {
        /*
            Method dump skipped, instructions count: 346
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.transition.Transition.createAnimators(android.view.ViewGroup, p3, p3, java.util.ArrayList, java.util.ArrayList):void");
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void end() {
        int i = this.x - 1;
        this.x = i;
        if (i == 0) {
            ArrayList<TransitionListener> arrayList = this.A;
            if (arrayList != null && arrayList.size() > 0) {
                ArrayList arrayList2 = (ArrayList) this.A.clone();
                int size = arrayList2.size();
                for (int i2 = 0; i2 < size; i2++) {
                    ((TransitionListener) arrayList2.get(i2)).onTransitionEnd(this);
                }
            }
            for (int i3 = 0; i3 < this.p.c.size(); i3++) {
                View viewValueAt = this.p.c.valueAt(i3);
                if (viewValueAt != null) {
                    ViewCompat.setHasTransientState(viewValueAt, false);
                }
            }
            for (int i4 = 0; i4 < this.q.c.size(); i4++) {
                View viewValueAt2 = this.q.c.valueAt(i4);
                if (viewValueAt2 != null) {
                    ViewCompat.setHasTransientState(viewValueAt2, false);
                }
            }
            this.z = true;
        }
    }

    @NonNull
    public Transition excludeChildren(@NonNull View view2, boolean z) {
        ArrayList<View> arrayListA = this.n;
        if (view2 != null) {
            arrayListA = z ? c2.a(arrayListA, view2) : c2.b(arrayListA, view2);
        }
        this.n = arrayListA;
        return this;
    }

    @NonNull
    public Transition excludeTarget(@NonNull View view2, boolean z) {
        ArrayList<View> arrayListA = this.j;
        if (view2 != null) {
            arrayListA = z ? c2.a(arrayListA, view2) : c2.b(arrayListA, view2);
        }
        this.j = arrayListA;
        return this;
    }

    public long getDuration() {
        return this.c;
    }

    @Nullable
    public Rect getEpicenter() {
        EpicenterCallback epicenterCallback = this.D;
        if (epicenterCallback == null) {
            return null;
        }
        return epicenterCallback.onGetEpicenter(this);
    }

    @Nullable
    public EpicenterCallback getEpicenterCallback() {
        return this.D;
    }

    @Nullable
    public TimeInterpolator getInterpolator() {
        return this.d;
    }

    @NonNull
    public String getName() {
        return this.a;
    }

    @NonNull
    public PathMotion getPathMotion() {
        return this.E;
    }

    @Nullable
    public TransitionPropagation getPropagation() {
        return this.C;
    }

    public long getStartDelay() {
        return this.b;
    }

    @NonNull
    public List<Integer> getTargetIds() {
        return this.e;
    }

    @Nullable
    public List<String> getTargetNames() {
        return this.g;
    }

    @Nullable
    public List<Class> getTargetTypes() {
        return this.h;
    }

    @NonNull
    public List<View> getTargets() {
        return this.f;
    }

    @Nullable
    public String[] getTransitionProperties() {
        return null;
    }

    @Nullable
    public TransitionValues getTransitionValues(@NonNull View view2, boolean z) {
        TransitionSet transitionSet = this.r;
        if (transitionSet != null) {
            return transitionSet.getTransitionValues(view2, z);
        }
        return (z ? this.p : this.q).a.get(view2);
    }

    public boolean isTransitionRequired(@Nullable TransitionValues transitionValues, @Nullable TransitionValues transitionValues2) {
        if (transitionValues == null || transitionValues2 == null) {
            return false;
        }
        String[] transitionProperties = getTransitionProperties();
        if (transitionProperties == null) {
            Iterator<String> it = transitionValues.values.keySet().iterator();
            while (it.hasNext()) {
                if (a(transitionValues, transitionValues2, it.next())) {
                }
            }
            return false;
        }
        for (String str : transitionProperties) {
            if (!a(transitionValues, transitionValues2, str)) {
            }
        }
        return false;
        return true;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void pause(View view2) {
        if (this.z) {
            return;
        }
        ArrayMap<Animator, c> arrayMapA = a();
        int size = arrayMapA.size();
        i4 i4VarC = a4.c(view2);
        for (int i = size - 1; i >= 0; i--) {
            c cVarValueAt = arrayMapA.valueAt(i);
            if (cVarValueAt.a != null && i4VarC.equals(cVarValueAt.d)) {
                u2.a(arrayMapA.keyAt(i));
            }
        }
        ArrayList<TransitionListener> arrayList = this.A;
        if (arrayList != null && arrayList.size() > 0) {
            ArrayList arrayList2 = (ArrayList) this.A.clone();
            int size2 = arrayList2.size();
            for (int i2 = 0; i2 < size2; i2++) {
                ((TransitionListener) arrayList2.get(i2)).onTransitionPause(this);
            }
        }
        this.y = true;
    }

    @NonNull
    public Transition removeListener(@NonNull TransitionListener transitionListener) {
        ArrayList<TransitionListener> arrayList = this.A;
        if (arrayList == null) {
            return this;
        }
        arrayList.remove(transitionListener);
        if (this.A.size() == 0) {
            this.A = null;
        }
        return this;
    }

    @NonNull
    public Transition removeTarget(@NonNull View view2) {
        this.f.remove(view2);
        return this;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void resume(View view2) {
        if (this.y) {
            if (!this.z) {
                ArrayMap<Animator, c> arrayMapA = a();
                int size = arrayMapA.size();
                i4 i4VarC = a4.c(view2);
                for (int i = size - 1; i >= 0; i--) {
                    c cVarValueAt = arrayMapA.valueAt(i);
                    if (cVarValueAt.a != null && i4VarC.equals(cVarValueAt.d)) {
                        u2.b(arrayMapA.keyAt(i));
                    }
                }
                ArrayList<TransitionListener> arrayList = this.A;
                if (arrayList != null && arrayList.size() > 0) {
                    ArrayList arrayList2 = (ArrayList) this.A.clone();
                    int size2 = arrayList2.size();
                    for (int i2 = 0; i2 < size2; i2++) {
                        ((TransitionListener) arrayList2.get(i2)).onTransitionResume(this);
                    }
                }
            }
            this.y = false;
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void runAnimators() {
        start();
        ArrayMap<Animator, c> arrayMapA = a();
        Iterator<Animator> it = this.B.iterator();
        while (it.hasNext()) {
            Animator next = it.next();
            if (arrayMapA.containsKey(next)) {
                start();
                if (next != null) {
                    next.addListener(new n3(this, arrayMapA));
                    animate(next);
                }
            }
        }
        this.B.clear();
        end();
    }

    @NonNull
    public Transition setDuration(long j) {
        this.c = j;
        return this;
    }

    public void setEpicenterCallback(@Nullable EpicenterCallback epicenterCallback) {
        this.D = epicenterCallback;
    }

    @NonNull
    public Transition setInterpolator(@Nullable TimeInterpolator timeInterpolator) {
        this.d = timeInterpolator;
        return this;
    }

    public void setMatchOrder(int... iArr) {
        if (iArr == null || iArr.length == 0) {
            this.s = F;
            return;
        }
        for (int i = 0; i < iArr.length; i++) {
            int i2 = iArr[i];
            boolean z = true;
            if (!(i2 >= 1 && i2 <= 4)) {
                throw new IllegalArgumentException("matches contains invalid value");
            }
            int i3 = iArr[i];
            int i4 = 0;
            while (true) {
                if (i4 >= i) {
                    z = false;
                    break;
                } else if (iArr[i4] == i3) {
                    break;
                } else {
                    i4++;
                }
            }
            if (z) {
                throw new IllegalArgumentException("matches contains a duplicate value");
            }
        }
        this.s = (int[]) iArr.clone();
    }

    public void setPathMotion(@Nullable PathMotion pathMotion) {
        if (pathMotion == null) {
            this.E = G;
        } else {
            this.E = pathMotion;
        }
    }

    public void setPropagation(@Nullable TransitionPropagation transitionPropagation) {
        this.C = transitionPropagation;
    }

    @NonNull
    public Transition setStartDelay(long j) {
        this.b = j;
        return this;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void start() {
        if (this.x == 0) {
            ArrayList<TransitionListener> arrayList = this.A;
            if (arrayList != null && arrayList.size() > 0) {
                ArrayList arrayList2 = (ArrayList) this.A.clone();
                int size = arrayList2.size();
                for (int i = 0; i < size; i++) {
                    ((TransitionListener) arrayList2.get(i)).onTransitionStart(this);
                }
            }
            this.z = false;
        }
        this.x++;
    }

    public String toString() {
        return a("");
    }

    @NonNull
    public Transition addTarget(@IdRes int i) {
        if (i != 0) {
            this.e.add(Integer.valueOf(i));
        }
        return this;
    }

    @Override // 
    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public Transition mo2clone() {
        try {
            Transition transition = (Transition) super.clone();
            transition.B = new ArrayList<>();
            transition.p = new p3();
            transition.q = new p3();
            transition.t = null;
            transition.u = null;
            return transition;
        } catch (CloneNotSupportedException unused) {
            return null;
        }
    }

    @NonNull
    public Transition removeTarget(@IdRes int i) {
        if (i != 0) {
            this.e.remove(Integer.valueOf(i));
        }
        return this;
    }

    @NonNull
    public Transition addTarget(@NonNull String str) {
        if (this.g == null) {
            this.g = new ArrayList<>();
        }
        this.g.add(str);
        return this;
    }

    @NonNull
    public Transition removeTarget(@NonNull String str) {
        ArrayList<String> arrayList = this.g;
        if (arrayList != null) {
            arrayList.remove(str);
        }
        return this;
    }

    @NonNull
    public Transition excludeChildren(@IdRes int i, boolean z) {
        this.m = a(this.m, i, z);
        return this;
    }

    @NonNull
    public Transition excludeTarget(@IdRes int i, boolean z) {
        this.i = a(this.i, i, z);
        return this;
    }

    @NonNull
    public Transition removeTarget(@NonNull Class cls) {
        ArrayList<Class> arrayList = this.h;
        if (arrayList != null) {
            arrayList.remove(cls);
        }
        return this;
    }

    @NonNull
    public Transition addTarget(@NonNull Class cls) {
        if (this.h == null) {
            this.h = new ArrayList<>();
        }
        this.h.add(cls);
        return this;
    }

    @NonNull
    public Transition excludeChildren(@NonNull Class cls, boolean z) {
        ArrayList<Class> arrayListB = this.o;
        if (cls != null) {
            if (z) {
                arrayListB = c2.a(arrayListB, cls);
            } else {
                arrayListB = c2.b(arrayListB, cls);
            }
        }
        this.o = arrayListB;
        return this;
    }

    @NonNull
    public Transition excludeTarget(@NonNull String str, boolean z) {
        ArrayList<String> arrayListB = this.l;
        if (str != null) {
            if (z) {
                arrayListB = c2.a(arrayListB, str);
            } else {
                arrayListB = c2.b(arrayListB, str);
            }
        }
        this.l = arrayListB;
        return this;
    }

    public void b(boolean z) {
        this.v = z;
    }

    @NonNull
    public Transition excludeTarget(@NonNull Class cls, boolean z) {
        ArrayList<Class> arrayListB = this.k;
        if (cls != null) {
            if (z) {
                arrayListB = c2.a(arrayListB, cls);
            } else {
                arrayListB = c2.b(arrayListB, cls);
            }
        }
        this.k = arrayListB;
        return this;
    }

    public static ArrayMap<Animator, c> a() {
        ArrayMap<Animator, c> arrayMap = H.get();
        if (arrayMap != null) {
            return arrayMap;
        }
        ArrayMap<Animator, c> arrayMap2 = new ArrayMap<>();
        H.set(arrayMap2);
        return arrayMap2;
    }

    public final ArrayList<Integer> a(ArrayList<Integer> arrayList, int i, boolean z) {
        if (i <= 0) {
            return arrayList;
        }
        if (z) {
            return c2.a(arrayList, Integer.valueOf(i));
        }
        return c2.b(arrayList, Integer.valueOf(i));
    }

    public void a(ViewGroup viewGroup, boolean z) {
        ArrayList<String> arrayList;
        ArrayList<Class> arrayList2;
        a(z);
        if ((this.e.size() <= 0 && this.f.size() <= 0) || (((arrayList = this.g) != null && !arrayList.isEmpty()) || ((arrayList2 = this.h) != null && !arrayList2.isEmpty()))) {
            a((View) viewGroup, z);
            return;
        }
        for (int i = 0; i < this.e.size(); i++) {
            View viewFindViewById = viewGroup.findViewById(this.e.get(i).intValue());
            if (viewFindViewById != null) {
                TransitionValues transitionValues = new TransitionValues();
                transitionValues.f0view = viewFindViewById;
                if (z) {
                    captureStartValues(transitionValues);
                } else {
                    captureEndValues(transitionValues);
                }
                transitionValues.a.add(this);
                a(transitionValues);
                if (z) {
                    a(this.p, viewFindViewById, transitionValues);
                } else {
                    a(this.q, viewFindViewById, transitionValues);
                }
            }
        }
        for (int i2 = 0; i2 < this.f.size(); i2++) {
            View view2 = this.f.get(i2);
            TransitionValues transitionValues2 = new TransitionValues();
            transitionValues2.f0view = view2;
            if (z) {
                captureStartValues(transitionValues2);
            } else {
                captureEndValues(transitionValues2);
            }
            transitionValues2.a.add(this);
            a(transitionValues2);
            if (z) {
                a(this.p, view2, transitionValues2);
            } else {
                a(this.q, view2, transitionValues2);
            }
        }
    }

    public Transition(Context context, AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, m3.c);
        XmlResourceParser xmlResourceParser = (XmlResourceParser) attributeSet;
        long namedInt = TypedArrayUtils.getNamedInt(typedArrayObtainStyledAttributes, xmlResourceParser, "duration", 1, -1);
        if (namedInt >= 0) {
            setDuration(namedInt);
        }
        long namedInt2 = TypedArrayUtils.getNamedInt(typedArrayObtainStyledAttributes, xmlResourceParser, "startDelay", 2, -1);
        if (namedInt2 > 0) {
            setStartDelay(namedInt2);
        }
        int namedResourceId = TypedArrayUtils.getNamedResourceId(typedArrayObtainStyledAttributes, xmlResourceParser, "interpolator", 0, 0);
        if (namedResourceId > 0) {
            setInterpolator(AnimationUtils.loadInterpolator(context, namedResourceId));
        }
        String namedString = TypedArrayUtils.getNamedString(typedArrayObtainStyledAttributes, xmlResourceParser, "matchOrder", 3);
        if (namedString != null) {
            StringTokenizer stringTokenizer = new StringTokenizer(namedString, ",");
            int[] iArr = new int[stringTokenizer.countTokens()];
            int i = 0;
            while (stringTokenizer.hasMoreTokens()) {
                String strTrim = stringTokenizer.nextToken().trim();
                if ("id".equalsIgnoreCase(strTrim)) {
                    iArr[i] = 3;
                } else if ("instance".equalsIgnoreCase(strTrim)) {
                    iArr[i] = 1;
                } else if ("name".equalsIgnoreCase(strTrim)) {
                    iArr[i] = 2;
                } else if ("itemId".equalsIgnoreCase(strTrim)) {
                    iArr[i] = 4;
                } else if (strTrim.isEmpty()) {
                    int[] iArr2 = new int[iArr.length - 1];
                    System.arraycopy(iArr, 0, iArr2, 0, i);
                    i--;
                    iArr = iArr2;
                } else {
                    throw new InflateException(g9.a("Unknown match type in matchOrder: '", strTrim, "'"));
                }
                i++;
            }
            setMatchOrder(iArr);
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    public static void a(p3 p3Var, View view2, TransitionValues transitionValues) {
        p3Var.a.put(view2, transitionValues);
        int id = view2.getId();
        if (id >= 0) {
            if (p3Var.b.indexOfKey(id) >= 0) {
                p3Var.b.put(id, null);
            } else {
                p3Var.b.put(id, view2);
            }
        }
        String transitionName = ViewCompat.getTransitionName(view2);
        if (transitionName != null) {
            if (p3Var.d.containsKey(transitionName)) {
                p3Var.d.put(transitionName, null);
            } else {
                p3Var.d.put(transitionName, view2);
            }
        }
        if (view2.getParent() instanceof ListView) {
            ListView listView = (ListView) view2.getParent();
            if (listView.getAdapter().hasStableIds()) {
                long itemIdAtPosition = listView.getItemIdAtPosition(listView.getPositionForView(view2));
                if (p3Var.c.indexOfKey(itemIdAtPosition) >= 0) {
                    View view3 = p3Var.c.get(itemIdAtPosition);
                    if (view3 != null) {
                        ViewCompat.setHasTransientState(view3, false);
                        p3Var.c.put(itemIdAtPosition, null);
                        return;
                    }
                    return;
                }
                ViewCompat.setHasTransientState(view2, true);
                p3Var.c.put(itemIdAtPosition, view2);
            }
        }
    }

    public void a(boolean z) {
        if (z) {
            this.p.a.clear();
            this.p.b.clear();
            this.p.c.clear();
        } else {
            this.q.a.clear();
            this.q.b.clear();
            this.q.c.clear();
        }
    }

    public final void a(View view2, boolean z) {
        if (view2 == null) {
            return;
        }
        int id = view2.getId();
        ArrayList<Integer> arrayList = this.i;
        if (arrayList == null || !arrayList.contains(Integer.valueOf(id))) {
            ArrayList<View> arrayList2 = this.j;
            if (arrayList2 == null || !arrayList2.contains(view2)) {
                ArrayList<Class> arrayList3 = this.k;
                if (arrayList3 != null) {
                    int size = arrayList3.size();
                    for (int i = 0; i < size; i++) {
                        if (this.k.get(i).isInstance(view2)) {
                            return;
                        }
                    }
                }
                if (view2.getParent() instanceof ViewGroup) {
                    TransitionValues transitionValues = new TransitionValues();
                    transitionValues.f0view = view2;
                    if (z) {
                        captureStartValues(transitionValues);
                    } else {
                        captureEndValues(transitionValues);
                    }
                    transitionValues.a.add(this);
                    a(transitionValues);
                    if (z) {
                        a(this.p, view2, transitionValues);
                    } else {
                        a(this.q, view2, transitionValues);
                    }
                }
                if (view2 instanceof ViewGroup) {
                    ArrayList<Integer> arrayList4 = this.m;
                    if (arrayList4 == null || !arrayList4.contains(Integer.valueOf(id))) {
                        ArrayList<View> arrayList5 = this.n;
                        if (arrayList5 == null || !arrayList5.contains(view2)) {
                            ArrayList<Class> arrayList6 = this.o;
                            if (arrayList6 != null) {
                                int size2 = arrayList6.size();
                                for (int i2 = 0; i2 < size2; i2++) {
                                    if (this.o.get(i2).isInstance(view2)) {
                                        return;
                                    }
                                }
                            }
                            ViewGroup viewGroup = (ViewGroup) view2;
                            for (int i3 = 0; i3 < viewGroup.getChildCount(); i3++) {
                                a(viewGroup.getChildAt(i3), z);
                            }
                        }
                    }
                }
            }
        }
    }

    public static boolean a(TransitionValues transitionValues, TransitionValues transitionValues2, String str) {
        Object obj = transitionValues.values.get(str);
        Object obj2 = transitionValues2.values.get(str);
        if (obj == null && obj2 == null) {
            return false;
        }
        if (obj == null || obj2 == null) {
            return true;
        }
        return true ^ obj.equals(obj2);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void a(ViewGroup viewGroup) {
        ArrayMap<Animator, c> arrayMapA = a();
        int size = arrayMapA.size();
        if (viewGroup != null) {
            i4 i4VarC = a4.c(viewGroup);
            for (int i = size - 1; i >= 0; i--) {
                c cVarValueAt = arrayMapA.valueAt(i);
                if (cVarValueAt.a != null && i4VarC.equals(cVarValueAt.d)) {
                    arrayMapA.keyAt(i).end();
                }
            }
        }
    }

    public void a(TransitionValues transitionValues) {
        String[] propagationProperties;
        if (this.C == null || transitionValues.values.isEmpty() || (propagationProperties = this.C.getPropagationProperties()) == null) {
            return;
        }
        boolean z = false;
        int i = 0;
        while (true) {
            if (i >= propagationProperties.length) {
                z = true;
                break;
            } else if (!transitionValues.values.containsKey(propagationProperties[i])) {
                break;
            } else {
                i++;
            }
        }
        if (z) {
            return;
        }
        this.C.captureValues(transitionValues);
    }

    public String a(String str) {
        StringBuilder sbA = g9.a(str);
        sbA.append(getClass().getSimpleName());
        sbA.append("@");
        sbA.append(Integer.toHexString(hashCode()));
        sbA.append(": ");
        String string = sbA.toString();
        if (this.c != -1) {
            StringBuilder sbA2 = g9.a(string, "dur(");
            sbA2.append(this.c);
            sbA2.append(") ");
            string = sbA2.toString();
        }
        if (this.b != -1) {
            StringBuilder sbA3 = g9.a(string, "dly(");
            sbA3.append(this.b);
            sbA3.append(") ");
            string = sbA3.toString();
        }
        if (this.d != null) {
            StringBuilder sbA4 = g9.a(string, "interp(");
            sbA4.append(this.d);
            sbA4.append(") ");
            string = sbA4.toString();
        }
        if (this.e.size() <= 0 && this.f.size() <= 0) {
            return string;
        }
        String strB = g9.b(string, "tgts(");
        if (this.e.size() > 0) {
            for (int i = 0; i < this.e.size(); i++) {
                if (i > 0) {
                    strB = g9.b(strB, ", ");
                }
                StringBuilder sbA5 = g9.a(strB);
                sbA5.append(this.e.get(i));
                strB = sbA5.toString();
            }
        }
        if (this.f.size() > 0) {
            for (int i2 = 0; i2 < this.f.size(); i2++) {
                if (i2 > 0) {
                    strB = g9.b(strB, ", ");
                }
                StringBuilder sbA6 = g9.a(strB);
                sbA6.append(this.f.get(i2));
                strB = sbA6.toString();
            }
        }
        return g9.b(strB, ")");
    }
}
