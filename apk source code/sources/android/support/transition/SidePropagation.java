package android.support.transition;

/* loaded from: classes.dex */
public class SidePropagation extends VisibilityPropagation {
    public float b = 3.0f;
    public int c = 80;

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0079, code lost:
    
        if ((android.support.v4.view.ViewCompat.getLayoutDirection(r18) == 1) != false) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0086, code lost:
    
        if ((android.support.v4.view.ViewCompat.getLayoutDirection(r18) == 1) != false) goto L31;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0088, code lost:
    
        r5 = 3;
        r15 = 3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x008b, code lost:
    
        r15 = 5;
     */
    @Override // android.support.transition.TransitionPropagation
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public long getStartDelay(android.view.ViewGroup r18, android.support.transition.Transition r19, android.support.transition.TransitionValues r20, android.support.transition.TransitionValues r21) {
        /*
            Method dump skipped, instructions count: 234
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.transition.SidePropagation.getStartDelay(android.view.ViewGroup, android.support.transition.Transition, android.support.transition.TransitionValues, android.support.transition.TransitionValues):long");
    }

    public void setPropagationSpeed(float f) {
        if (f == 0.0f) {
            throw new IllegalArgumentException("propagationSpeed may not be 0");
        }
        this.b = f;
    }

    public void setSide(int i) {
        this.c = i;
    }
}
