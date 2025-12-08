package android.support.transition;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.transition.Transition;
import android.support.v4.content.res.TypedArrayUtils;
import android.util.AndroidRuntimeException;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import defpackage.g9;
import defpackage.m3;
import defpackage.p3;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes.dex */
public class TransitionSet extends Transition {
    public static final int ORDERING_SEQUENTIAL = 1;
    public static final int ORDERING_TOGETHER = 0;
    public ArrayList<Transition> I;
    public boolean J;
    public int K;
    public boolean L;

    public class a extends TransitionListenerAdapter {
        public final /* synthetic */ Transition a;

        public a(TransitionSet transitionSet, Transition transition) {
            this.a = transition;
        }

        @Override // android.support.transition.TransitionListenerAdapter, android.support.transition.Transition.TransitionListener
        public void onTransitionEnd(@NonNull Transition transition) {
            this.a.runAnimators();
            transition.removeListener(this);
        }
    }

    public static class b extends TransitionListenerAdapter {
        public TransitionSet a;

        public b(TransitionSet transitionSet) {
            this.a = transitionSet;
        }

        @Override // android.support.transition.TransitionListenerAdapter, android.support.transition.Transition.TransitionListener
        public void onTransitionEnd(@NonNull Transition transition) {
            TransitionSet transitionSet = this.a;
            int i = transitionSet.K - 1;
            transitionSet.K = i;
            if (i == 0) {
                transitionSet.L = false;
                transitionSet.end();
            }
            transition.removeListener(this);
        }

        @Override // android.support.transition.TransitionListenerAdapter, android.support.transition.Transition.TransitionListener
        public void onTransitionStart(@NonNull Transition transition) {
            TransitionSet transitionSet = this.a;
            if (transitionSet.L) {
                return;
            }
            transitionSet.start();
            this.a.L = true;
        }
    }

    public TransitionSet() {
        this.I = new ArrayList<>();
        this.J = true;
        this.L = false;
    }

    @Override // android.support.transition.Transition
    public void a(TransitionValues transitionValues) {
        super.a(transitionValues);
        int size = this.I.size();
        for (int i = 0; i < size; i++) {
            this.I.get(i).a(transitionValues);
        }
    }

    @NonNull
    public TransitionSet addTransition(@NonNull Transition transition) {
        this.I.add(transition);
        transition.r = this;
        long j = this.c;
        if (j >= 0) {
            transition.setDuration(j);
        }
        return this;
    }

    @Override // android.support.transition.Transition
    public Transition b(ViewGroup viewGroup) {
        int size = this.I.size();
        for (int i = 0; i < size; i++) {
            this.I.get(i).b(viewGroup);
        }
        return this;
    }

    @Override // android.support.transition.Transition
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void cancel() {
        super.cancel();
        int size = this.I.size();
        for (int i = 0; i < size; i++) {
            this.I.get(i).cancel();
        }
    }

    @Override // android.support.transition.Transition
    public void captureEndValues(@NonNull TransitionValues transitionValues) {
        if (a(transitionValues.f0view)) {
            Iterator<Transition> it = this.I.iterator();
            while (it.hasNext()) {
                Transition next = it.next();
                if (next.a(transitionValues.f0view)) {
                    next.captureEndValues(transitionValues);
                    transitionValues.a.add(next);
                }
            }
        }
    }

    @Override // android.support.transition.Transition
    public void captureStartValues(@NonNull TransitionValues transitionValues) {
        if (a(transitionValues.f0view)) {
            Iterator<Transition> it = this.I.iterator();
            while (it.hasNext()) {
                Transition next = it.next();
                if (next.a(transitionValues.f0view)) {
                    next.captureStartValues(transitionValues);
                    transitionValues.a.add(next);
                }
            }
        }
    }

    @Override // android.support.transition.Transition
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void createAnimators(ViewGroup viewGroup, p3 p3Var, p3 p3Var2, ArrayList<TransitionValues> arrayList, ArrayList<TransitionValues> arrayList2) {
        long startDelay = getStartDelay();
        int size = this.I.size();
        for (int i = 0; i < size; i++) {
            Transition transition = this.I.get(i);
            if (startDelay > 0 && (this.J || i == 0)) {
                long startDelay2 = transition.getStartDelay();
                if (startDelay2 > 0) {
                    transition.setStartDelay(startDelay2 + startDelay);
                } else {
                    transition.setStartDelay(startDelay);
                }
            }
            transition.createAnimators(viewGroup, p3Var, p3Var2, arrayList, arrayList2);
        }
    }

    @Override // android.support.transition.Transition
    @NonNull
    public Transition excludeTarget(@NonNull View view2, boolean z) {
        for (int i = 0; i < this.I.size(); i++) {
            this.I.get(i).excludeTarget(view2, z);
        }
        return super.excludeTarget(view2, z);
    }

    public int getOrdering() {
        return !this.J ? 1 : 0;
    }

    public Transition getTransitionAt(int i) {
        if (i < 0 || i >= this.I.size()) {
            return null;
        }
        return this.I.get(i);
    }

    public int getTransitionCount() {
        return this.I.size();
    }

    @Override // android.support.transition.Transition
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void pause(View view2) {
        super.pause(view2);
        int size = this.I.size();
        for (int i = 0; i < size; i++) {
            this.I.get(i).pause(view2);
        }
    }

    @NonNull
    public TransitionSet removeTransition(@NonNull Transition transition) {
        this.I.remove(transition);
        transition.r = null;
        return this;
    }

    @Override // android.support.transition.Transition
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void resume(View view2) {
        super.resume(view2);
        int size = this.I.size();
        for (int i = 0; i < size; i++) {
            this.I.get(i).resume(view2);
        }
    }

    @Override // android.support.transition.Transition
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void runAnimators() {
        if (this.I.isEmpty()) {
            start();
            end();
            return;
        }
        b bVar = new b(this);
        Iterator<Transition> it = this.I.iterator();
        while (it.hasNext()) {
            it.next().addListener(bVar);
        }
        this.K = this.I.size();
        if (this.J) {
            Iterator<Transition> it2 = this.I.iterator();
            while (it2.hasNext()) {
                it2.next().runAnimators();
            }
            return;
        }
        for (int i = 1; i < this.I.size(); i++) {
            this.I.get(i - 1).addListener(new a(this, this.I.get(i)));
        }
        Transition transition = this.I.get(0);
        if (transition != null) {
            transition.runAnimators();
        }
    }

    @Override // android.support.transition.Transition
    public void setEpicenterCallback(Transition.EpicenterCallback epicenterCallback) {
        super.setEpicenterCallback(epicenterCallback);
        int size = this.I.size();
        for (int i = 0; i < size; i++) {
            this.I.get(i).setEpicenterCallback(epicenterCallback);
        }
    }

    @NonNull
    public TransitionSet setOrdering(int i) {
        if (i == 0) {
            this.J = true;
        } else {
            if (i != 1) {
                throw new AndroidRuntimeException(g9.b("Invalid parameter for TransitionSet ordering: ", i));
            }
            this.J = false;
        }
        return this;
    }

    @Override // android.support.transition.Transition
    public void setPathMotion(PathMotion pathMotion) {
        super.setPathMotion(pathMotion);
        for (int i = 0; i < this.I.size(); i++) {
            this.I.get(i).setPathMotion(pathMotion);
        }
    }

    @Override // android.support.transition.Transition
    public void setPropagation(TransitionPropagation transitionPropagation) {
        super.setPropagation(transitionPropagation);
        int size = this.I.size();
        for (int i = 0; i < size; i++) {
            this.I.get(i).setPropagation(transitionPropagation);
        }
    }

    @Override // android.support.transition.Transition
    @NonNull
    public TransitionSet addListener(@NonNull Transition.TransitionListener transitionListener) {
        return (TransitionSet) super.addListener(transitionListener);
    }

    @Override // android.support.transition.Transition
    /* renamed from: clone */
    public Transition mo2clone() {
        TransitionSet transitionSet = (TransitionSet) super.mo2clone();
        transitionSet.I = new ArrayList<>();
        int size = this.I.size();
        for (int i = 0; i < size; i++) {
            transitionSet.addTransition(this.I.get(i).mo2clone());
        }
        return transitionSet;
    }

    @Override // android.support.transition.Transition
    @NonNull
    public TransitionSet removeListener(@NonNull Transition.TransitionListener transitionListener) {
        return (TransitionSet) super.removeListener(transitionListener);
    }

    @Override // android.support.transition.Transition
    @NonNull
    public TransitionSet setDuration(long j) {
        super.setDuration(j);
        if (this.c >= 0) {
            int size = this.I.size();
            for (int i = 0; i < size; i++) {
                this.I.get(i).setDuration(j);
            }
        }
        return this;
    }

    @Override // android.support.transition.Transition
    @NonNull
    public TransitionSet setInterpolator(@Nullable TimeInterpolator timeInterpolator) {
        return (TransitionSet) super.setInterpolator(timeInterpolator);
    }

    @Override // android.support.transition.Transition
    @NonNull
    public TransitionSet setStartDelay(long j) {
        return (TransitionSet) super.setStartDelay(j);
    }

    @Override // android.support.transition.Transition
    public void b(boolean z) {
        this.v = z;
        int size = this.I.size();
        for (int i = 0; i < size; i++) {
            this.I.get(i).b(z);
        }
    }

    @Override // android.support.transition.Transition
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void a(ViewGroup viewGroup) {
        super.a(viewGroup);
        int size = this.I.size();
        for (int i = 0; i < size; i++) {
            this.I.get(i).a(viewGroup);
        }
    }

    @Override // android.support.transition.Transition
    @NonNull
    public Transition excludeTarget(@NonNull String str, boolean z) {
        for (int i = 0; i < this.I.size(); i++) {
            this.I.get(i).excludeTarget(str, z);
        }
        return super.excludeTarget(str, z);
    }

    public TransitionSet(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.I = new ArrayList<>();
        this.J = true;
        this.L = false;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, m3.i);
        setOrdering(TypedArrayUtils.getNamedInt(typedArrayObtainStyledAttributes, (XmlResourceParser) attributeSet, "transitionOrdering", 0, 0));
        typedArrayObtainStyledAttributes.recycle();
    }

    @Override // android.support.transition.Transition
    @NonNull
    public TransitionSet addTarget(@NonNull View view2) {
        for (int i = 0; i < this.I.size(); i++) {
            this.I.get(i).addTarget(view2);
        }
        return (TransitionSet) super.addTarget(view2);
    }

    @Override // android.support.transition.Transition
    @NonNull
    public TransitionSet removeTarget(@IdRes int i) {
        for (int i2 = 0; i2 < this.I.size(); i2++) {
            this.I.get(i2).removeTarget(i);
        }
        return (TransitionSet) super.removeTarget(i);
    }

    @Override // android.support.transition.Transition
    public String a(String str) {
        String strA = super.a(str);
        for (int i = 0; i < this.I.size(); i++) {
            StringBuilder sbA = g9.a(strA, StringUtils.LF);
            sbA.append(this.I.get(i).a(str + "  "));
            strA = sbA.toString();
        }
        return strA;
    }

    @Override // android.support.transition.Transition
    @NonNull
    public Transition excludeTarget(int i, boolean z) {
        for (int i2 = 0; i2 < this.I.size(); i2++) {
            this.I.get(i2).excludeTarget(i, z);
        }
        return super.excludeTarget(i, z);
    }

    @Override // android.support.transition.Transition
    @NonNull
    public TransitionSet addTarget(@IdRes int i) {
        for (int i2 = 0; i2 < this.I.size(); i2++) {
            this.I.get(i2).addTarget(i);
        }
        return (TransitionSet) super.addTarget(i);
    }

    @Override // android.support.transition.Transition
    @NonNull
    public TransitionSet removeTarget(@NonNull View view2) {
        for (int i = 0; i < this.I.size(); i++) {
            this.I.get(i).removeTarget(view2);
        }
        return (TransitionSet) super.removeTarget(view2);
    }

    @Override // android.support.transition.Transition
    @NonNull
    public Transition excludeTarget(@NonNull Class cls, boolean z) {
        for (int i = 0; i < this.I.size(); i++) {
            this.I.get(i).excludeTarget(cls, z);
        }
        return super.excludeTarget(cls, z);
    }

    @Override // android.support.transition.Transition
    @NonNull
    public TransitionSet addTarget(@NonNull String str) {
        for (int i = 0; i < this.I.size(); i++) {
            this.I.get(i).addTarget(str);
        }
        return (TransitionSet) super.addTarget(str);
    }

    @Override // android.support.transition.Transition
    @NonNull
    public TransitionSet removeTarget(@NonNull Class cls) {
        for (int i = 0; i < this.I.size(); i++) {
            this.I.get(i).removeTarget(cls);
        }
        return (TransitionSet) super.removeTarget(cls);
    }

    @Override // android.support.transition.Transition
    @NonNull
    public TransitionSet addTarget(@NonNull Class cls) {
        for (int i = 0; i < this.I.size(); i++) {
            this.I.get(i).addTarget(cls);
        }
        return (TransitionSet) super.addTarget(cls);
    }

    @Override // android.support.transition.Transition
    @NonNull
    public TransitionSet removeTarget(@NonNull String str) {
        for (int i = 0; i < this.I.size(); i++) {
            this.I.get(i).removeTarget(str);
        }
        return (TransitionSet) super.removeTarget(str);
    }
}
