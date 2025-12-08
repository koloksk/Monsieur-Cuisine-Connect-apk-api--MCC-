package android.support.transition;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.InflateException;
import android.view.ViewGroup;
import defpackage.g9;
import java.io.IOException;
import java.lang.reflect.Constructor;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class TransitionInflater {
    public static final Class<?>[] b = {Context.class, AttributeSet.class};
    public static final ArrayMap<String, Constructor> c = new ArrayMap<>();
    public final Context a;

    public TransitionInflater(@NonNull Context context) {
        this.a = context;
    }

    public static TransitionInflater from(Context context) {
        return new TransitionInflater(context);
    }

    /* JADX WARN: Code restructure failed: missing block: B:115:0x021a, code lost:
    
        return r2;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.support.transition.Transition a(org.xmlpull.v1.XmlPullParser r13, android.util.AttributeSet r14, android.support.transition.Transition r15) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 539
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.transition.TransitionInflater.a(org.xmlpull.v1.XmlPullParser, android.util.AttributeSet, android.support.transition.Transition):android.support.transition.Transition");
    }

    public Transition inflateTransition(int i) throws Resources.NotFoundException {
        XmlResourceParser xml = this.a.getResources().getXml(i);
        try {
            try {
                return a(xml, Xml.asAttributeSet(xml), (Transition) null);
            } catch (IOException e) {
                throw new InflateException(xml.getPositionDescription() + ": " + e.getMessage(), e);
            } catch (XmlPullParserException e2) {
                throw new InflateException(e2.getMessage(), e2);
            }
        } finally {
            xml.close();
        }
    }

    public TransitionManager inflateTransitionManager(int i, ViewGroup viewGroup) throws Resources.NotFoundException {
        XmlResourceParser xml = this.a.getResources().getXml(i);
        try {
            try {
                return a(xml, Xml.asAttributeSet(xml), viewGroup);
            } catch (IOException e) {
                InflateException inflateException = new InflateException(xml.getPositionDescription() + ": " + e.getMessage());
                inflateException.initCause(e);
                throw inflateException;
            } catch (XmlPullParserException e2) {
                InflateException inflateException2 = new InflateException(e2.getMessage());
                inflateException2.initCause(e2);
                throw inflateException2;
            }
        } finally {
            xml.close();
        }
    }

    public final Object a(AttributeSet attributeSet, Class cls, String str) {
        Object objNewInstance;
        Class<? extends U> clsAsSubclass;
        String attributeValue = attributeSet.getAttributeValue(null, "class");
        if (attributeValue != null) {
            try {
                synchronized (c) {
                    Constructor constructor = c.get(attributeValue);
                    if (constructor == null && (clsAsSubclass = this.a.getClassLoader().loadClass(attributeValue).asSubclass(cls)) != 0) {
                        constructor = clsAsSubclass.getConstructor(b);
                        constructor.setAccessible(true);
                        c.put(attributeValue, constructor);
                    }
                    objNewInstance = constructor.newInstance(this.a, attributeSet);
                }
                return objNewInstance;
            } catch (Exception e) {
                throw new InflateException("Could not instantiate " + cls + " class " + attributeValue, e);
            }
        }
        throw new InflateException(g9.b(str, " tag must have a 'class' attribute"));
    }

    /* JADX WARN: Code restructure failed: missing block: B:36:0x0089, code lost:
    
        r12 = defpackage.g9.a("Unknown scene name: ");
        r12.append(r10.getName());
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x009f, code lost:
    
        throw new java.lang.RuntimeException(r12.toString());
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x00a0, code lost:
    
        return r2;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.support.transition.TransitionManager a(org.xmlpull.v1.XmlPullParser r10, android.util.AttributeSet r11, android.view.ViewGroup r12) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            r9 = this;
            int r0 = r10.getDepth()
            r1 = 0
            r2 = r1
        L6:
            int r3 = r10.next()
            r4 = 3
            if (r3 != r4) goto L13
            int r4 = r10.getDepth()
            if (r4 <= r0) goto La0
        L13:
            r4 = 1
            if (r3 == r4) goto La0
            r5 = 2
            if (r3 == r5) goto L1a
            goto L6
        L1a:
            java.lang.String r3 = r10.getName()
            java.lang.String r6 = "transitionManager"
            boolean r6 = r3.equals(r6)
            if (r6 == 0) goto L2c
            android.support.transition.TransitionManager r2 = new android.support.transition.TransitionManager
            r2.<init>()
            goto L6
        L2c:
            java.lang.String r6 = "transition"
            boolean r3 = r3.equals(r6)
            if (r3 == 0) goto L89
            if (r2 == 0) goto L89
            android.content.Context r3 = r9.a
            int[] r7 = defpackage.m3.b
            android.content.res.TypedArray r3 = r3.obtainStyledAttributes(r11, r7)
            r7 = -1
            int r5 = android.support.v4.content.res.TypedArrayUtils.getNamedResourceId(r3, r10, r6, r5, r7)
            r6 = 0
            java.lang.String r8 = "fromScene"
            int r6 = android.support.v4.content.res.TypedArrayUtils.getNamedResourceId(r3, r10, r8, r6, r7)
            if (r6 >= 0) goto L4e
            r6 = r1
            goto L54
        L4e:
            android.content.Context r8 = r9.a
            android.support.transition.Scene r6 = android.support.transition.Scene.getSceneForLayout(r12, r6, r8)
        L54:
            java.lang.String r8 = "toScene"
            int r4 = android.support.v4.content.res.TypedArrayUtils.getNamedResourceId(r3, r10, r8, r4, r7)
            if (r4 >= 0) goto L5e
            r4 = r1
            goto L64
        L5e:
            android.content.Context r7 = r9.a
            android.support.transition.Scene r4 = android.support.transition.Scene.getSceneForLayout(r12, r4, r7)
        L64:
            if (r5 < 0) goto L84
            android.support.transition.Transition r7 = r9.inflateTransition(r5)
            if (r7 == 0) goto L84
            if (r4 == 0) goto L78
            if (r6 != 0) goto L74
            r2.setTransition(r4, r7)
            goto L84
        L74:
            r2.setTransition(r6, r4, r7)
            goto L84
        L78:
            java.lang.RuntimeException r10 = new java.lang.RuntimeException
            java.lang.String r11 = "No toScene for transition ID "
            java.lang.String r11 = defpackage.g9.b(r11, r5)
            r10.<init>(r11)
            throw r10
        L84:
            r3.recycle()
            goto L6
        L89:
            java.lang.RuntimeException r11 = new java.lang.RuntimeException
            java.lang.String r12 = "Unknown scene name: "
            java.lang.StringBuilder r12 = defpackage.g9.a(r12)
            java.lang.String r10 = r10.getName()
            r12.append(r10)
            java.lang.String r10 = r12.toString()
            r11.<init>(r10)
            throw r11
        La0:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.transition.TransitionInflater.a(org.xmlpull.v1.XmlPullParser, android.util.AttributeSet, android.view.ViewGroup):android.support.transition.TransitionManager");
    }
}
