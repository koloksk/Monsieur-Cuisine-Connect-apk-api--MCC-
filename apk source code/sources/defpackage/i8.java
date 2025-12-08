package defpackage;

/* loaded from: classes.dex */
public class i8 {
    public int b;
    public int c;
    public int d;
    public int e;
    public boolean h;
    public boolean i;
    public boolean a = true;
    public int f = 0;
    public int g = 0;

    public String toString() {
        StringBuilder sbA = g9.a("LayoutState{mAvailable=");
        sbA.append(this.b);
        sbA.append(", mCurrentPosition=");
        sbA.append(this.c);
        sbA.append(", mItemDirection=");
        sbA.append(this.d);
        sbA.append(", mLayoutDirection=");
        sbA.append(this.e);
        sbA.append(", mStartLine=");
        sbA.append(this.f);
        sbA.append(", mEndLine=");
        sbA.append(this.g);
        sbA.append('}');
        return sbA.toString();
    }
}
