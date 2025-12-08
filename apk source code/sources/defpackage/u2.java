package defpackage;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.annotation.NonNull;

/* loaded from: classes.dex */
public class u2 {
    public static final w2 a = new v2();

    public static void a(@NonNull Animator animator, @NonNull AnimatorListenerAdapter animatorListenerAdapter) {
        animator.addPauseListener(animatorListenerAdapter);
    }

    public static void b(@NonNull Animator animator) {
        animator.resume();
    }

    public static void a(@NonNull Animator animator) {
        animator.pause();
    }
}
