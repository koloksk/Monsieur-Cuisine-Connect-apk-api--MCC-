package android.support.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v4.graphics.PathParser;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import defpackage.s2;
import defpackage.t2;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class VectorDrawableCompat extends t2 {
    public static final PorterDuff.Mode j = PorterDuff.Mode.SRC_IN;
    public f b;
    public PorterDuffColorFilter c;
    public ColorFilter d;
    public boolean e;
    public boolean f;
    public final float[] g;
    public final Matrix h;
    public final Rect i;

    public static class a extends d {
        public a() {
        }

        @Override // android.support.graphics.drawable.VectorDrawableCompat.d
        public boolean a() {
            return true;
        }

        public a(a aVar) {
            super(aVar);
        }
    }

    public static class f extends Drawable.ConstantState {
        public int a;
        public e b;
        public ColorStateList c;
        public PorterDuff.Mode d;
        public boolean e;
        public Bitmap f;
        public ColorStateList g;
        public PorterDuff.Mode h;
        public int i;
        public boolean j;
        public boolean k;
        public Paint l;

        public f(f fVar) {
            this.c = null;
            this.d = VectorDrawableCompat.j;
            if (fVar != null) {
                this.a = fVar.a;
                e eVar = new e(fVar.b);
                this.b = eVar;
                if (fVar.b.e != null) {
                    eVar.e = new Paint(fVar.b.e);
                }
                if (fVar.b.d != null) {
                    this.b.d = new Paint(fVar.b.d);
                }
                this.c = fVar.c;
                this.d = fVar.d;
                this.e = fVar.e;
            }
        }

        public void a(int i, int i2) {
            this.f.eraseColor(0);
            Canvas canvas = new Canvas(this.f);
            e eVar = this.b;
            eVar.a(eVar.h, e.p, canvas, i, i2, null);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return this.a;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable() {
            return new VectorDrawableCompat(this);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources resources) {
            return new VectorDrawableCompat(this);
        }

        public f() {
            this.c = null;
            this.d = VectorDrawableCompat.j;
            this.b = new e();
        }
    }

    public VectorDrawableCompat() {
        this.f = true;
        this.g = new float[9];
        this.h = new Matrix();
        this.i = new Rect();
        this.b = new f();
    }

    @Nullable
    public static VectorDrawableCompat create(@NonNull Resources resources, @DrawableRes int i, @Nullable Resources.Theme theme) {
        int next;
        if (Build.VERSION.SDK_INT >= 24) {
            VectorDrawableCompat vectorDrawableCompat = new VectorDrawableCompat();
            vectorDrawableCompat.a = ResourcesCompat.getDrawable(resources, i, theme);
            new g(vectorDrawableCompat.a.getConstantState());
            return vectorDrawableCompat;
        }
        try {
            XmlResourceParser xml = resources.getXml(i);
            AttributeSet attributeSetAsAttributeSet = Xml.asAttributeSet(xml);
            do {
                next = xml.next();
                if (next == 2) {
                    break;
                }
            } while (next != 1);
            if (next == 2) {
                return createFromXmlInner(resources, (XmlPullParser) xml, attributeSetAsAttributeSet, theme);
            }
            throw new XmlPullParserException("No start tag found");
        } catch (IOException e2) {
            Log.e("VectorDrawableCompat", "parser error", e2);
            return null;
        } catch (XmlPullParserException e3) {
            Log.e("VectorDrawableCompat", "parser error", e3);
            return null;
        }
    }

    public static VectorDrawableCompat createFromXmlInner(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        VectorDrawableCompat vectorDrawableCompat = new VectorDrawableCompat();
        vectorDrawableCompat.inflate(resources, xmlPullParser, attributeSet, theme);
        return vectorDrawableCompat;
    }

    public PorterDuffColorFilter a(ColorStateList colorStateList, PorterDuff.Mode mode) {
        if (colorStateList == null || mode == null) {
            return null;
        }
        return new PorterDuffColorFilter(colorStateList.getColorForState(getState(), 0), mode);
    }

    @Override // defpackage.t2, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ void applyTheme(Resources.Theme theme) {
        super.applyTheme(theme);
    }

    @Override // android.graphics.drawable.Drawable
    public boolean canApplyTheme() {
        Drawable drawable = this.a;
        if (drawable == null) {
            return false;
        }
        DrawableCompat.canApplyTheme(drawable);
        return false;
    }

    @Override // defpackage.t2, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ void clearColorFilter() {
        super.clearColorFilter();
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x00d1  */
    @Override // android.graphics.drawable.Drawable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void draw(android.graphics.Canvas r11) {
        /*
            Method dump skipped, instructions count: 360
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.graphics.drawable.VectorDrawableCompat.draw(android.graphics.Canvas):void");
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        Drawable drawable = this.a;
        return drawable != null ? DrawableCompat.getAlpha(drawable) : this.b.b.getRootAlpha();
    }

    @Override // android.graphics.drawable.Drawable
    public int getChangingConfigurations() {
        Drawable drawable = this.a;
        return drawable != null ? drawable.getChangingConfigurations() : super.getChangingConfigurations() | this.b.getChangingConfigurations();
    }

    @Override // defpackage.t2, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ ColorFilter getColorFilter() {
        return super.getColorFilter();
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable.ConstantState getConstantState() {
        if (this.a != null && Build.VERSION.SDK_INT >= 24) {
            return new g(this.a.getConstantState());
        }
        this.b.a = getChangingConfigurations();
        return this.b;
    }

    @Override // defpackage.t2, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ Drawable getCurrent() {
        return super.getCurrent();
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        Drawable drawable = this.a;
        return drawable != null ? drawable.getIntrinsicHeight() : (int) this.b.b.j;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        Drawable drawable = this.a;
        return drawable != null ? drawable.getIntrinsicWidth() : (int) this.b.b.i;
    }

    @Override // defpackage.t2, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ int getMinimumHeight() {
        return super.getMinimumHeight();
    }

    @Override // defpackage.t2, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ int getMinimumWidth() {
        return super.getMinimumWidth();
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        Drawable drawable = this.a;
        if (drawable != null) {
            return drawable.getOpacity();
        }
        return -3;
    }

    @Override // defpackage.t2, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ boolean getPadding(Rect rect) {
        return super.getPadding(rect);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public float getPixelSize() {
        e eVar;
        f fVar = this.b;
        if (fVar == null || (eVar = fVar.b) == null) {
            return 1.0f;
        }
        float f2 = eVar.i;
        if (f2 == 0.0f) {
            return 1.0f;
        }
        float f3 = eVar.j;
        if (f3 == 0.0f) {
            return 1.0f;
        }
        float f4 = eVar.l;
        if (f4 == 0.0f) {
            return 1.0f;
        }
        float f5 = eVar.k;
        if (f5 == 0.0f) {
            return 1.0f;
        }
        return Math.min(f5 / f2, f4 / f3);
    }

    @Override // defpackage.t2, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ int[] getState() {
        return super.getState();
    }

    @Override // defpackage.t2, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ Region getTransparentRegion() {
        return super.getTransparentRegion();
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet) throws XmlPullParserException, IOException {
        Drawable drawable = this.a;
        if (drawable != null) {
            drawable.inflate(resources, xmlPullParser, attributeSet);
        } else {
            inflate(resources, xmlPullParser, attributeSet, null);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void invalidateSelf() {
        Drawable drawable = this.a;
        if (drawable != null) {
            drawable.invalidateSelf();
        } else {
            super.invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isAutoMirrored() {
        Drawable drawable = this.a;
        return drawable != null ? DrawableCompat.isAutoMirrored(drawable) : this.b.e;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        f fVar;
        ColorStateList colorStateList;
        Drawable drawable = this.a;
        return drawable != null ? drawable.isStateful() : super.isStateful() || !((fVar = this.b) == null || (colorStateList = fVar.c) == null || !colorStateList.isStateful());
    }

    @Override // defpackage.t2, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ void jumpToCurrentState() {
        super.jumpToCurrentState();
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable mutate() {
        Drawable drawable = this.a;
        if (drawable != null) {
            drawable.mutate();
            return this;
        }
        if (!this.e && super.mutate() == this) {
            this.b = new f(this.b);
            this.e = true;
        }
        return this;
    }

    @Override // defpackage.t2, android.graphics.drawable.Drawable
    public void onBoundsChange(Rect rect) {
        Drawable drawable = this.a;
        if (drawable != null) {
            drawable.setBounds(rect);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean onStateChange(int[] iArr) {
        PorterDuff.Mode mode;
        Drawable drawable = this.a;
        if (drawable != null) {
            return drawable.setState(iArr);
        }
        f fVar = this.b;
        ColorStateList colorStateList = fVar.c;
        if (colorStateList == null || (mode = fVar.d) == null) {
            return false;
        }
        this.c = a(colorStateList, mode);
        invalidateSelf();
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public void scheduleSelf(Runnable runnable, long j2) {
        Drawable drawable = this.a;
        if (drawable != null) {
            drawable.scheduleSelf(runnable, j2);
        } else {
            super.scheduleSelf(runnable, j2);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        Drawable drawable = this.a;
        if (drawable != null) {
            drawable.setAlpha(i);
        } else if (this.b.b.getRootAlpha() != i) {
            this.b.b.setRootAlpha(i);
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setAutoMirrored(boolean z) {
        Drawable drawable = this.a;
        if (drawable != null) {
            DrawableCompat.setAutoMirrored(drawable, z);
        } else {
            this.b.e = z;
        }
    }

    @Override // defpackage.t2, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ void setChangingConfigurations(int i) {
        super.setChangingConfigurations(i);
    }

    @Override // defpackage.t2, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ void setColorFilter(int i, PorterDuff.Mode mode) {
        super.setColorFilter(i, mode);
    }

    @Override // defpackage.t2, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ void setFilterBitmap(boolean z) {
        super.setFilterBitmap(z);
    }

    @Override // defpackage.t2, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ void setHotspot(float f2, float f3) {
        super.setHotspot(f2, f3);
    }

    @Override // defpackage.t2, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ void setHotspotBounds(int i, int i2, int i3, int i4) {
        super.setHotspotBounds(i, i2, i3, i4);
    }

    @Override // defpackage.t2, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ boolean setState(int[] iArr) {
        return super.setState(iArr);
    }

    @Override // android.graphics.drawable.Drawable, android.support.v4.graphics.drawable.TintAwareDrawable
    public void setTint(int i) {
        Drawable drawable = this.a;
        if (drawable != null) {
            DrawableCompat.setTint(drawable, i);
        } else {
            setTintList(ColorStateList.valueOf(i));
        }
    }

    @Override // android.graphics.drawable.Drawable, android.support.v4.graphics.drawable.TintAwareDrawable
    public void setTintList(ColorStateList colorStateList) {
        Drawable drawable = this.a;
        if (drawable != null) {
            DrawableCompat.setTintList(drawable, colorStateList);
            return;
        }
        f fVar = this.b;
        if (fVar.c != colorStateList) {
            fVar.c = colorStateList;
            this.c = a(colorStateList, fVar.d);
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable, android.support.v4.graphics.drawable.TintAwareDrawable
    public void setTintMode(PorterDuff.Mode mode) {
        Drawable drawable = this.a;
        if (drawable != null) {
            DrawableCompat.setTintMode(drawable, mode);
            return;
        }
        f fVar = this.b;
        if (fVar.d != mode) {
            fVar.d = mode;
            this.c = a(fVar.c, mode);
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean setVisible(boolean z, boolean z2) {
        Drawable drawable = this.a;
        return drawable != null ? drawable.setVisible(z, z2) : super.setVisible(z, z2);
    }

    @Override // android.graphics.drawable.Drawable
    public void unscheduleSelf(Runnable runnable) {
        Drawable drawable = this.a;
        if (drawable != null) {
            drawable.unscheduleSelf(runnable);
        } else {
            super.unscheduleSelf(runnable);
        }
    }

    public static class d {
        public PathParser.PathDataNode[] a;
        public String b;
        public int c;

        public d() {
            this.a = null;
        }

        public boolean a() {
            return false;
        }

        public PathParser.PathDataNode[] getPathData() {
            return this.a;
        }

        public String getPathName() {
            return this.b;
        }

        public void setPathData(PathParser.PathDataNode[] pathDataNodeArr) {
            if (PathParser.canMorph(this.a, pathDataNodeArr)) {
                PathParser.updateNodes(this.a, pathDataNodeArr);
            } else {
                this.a = PathParser.deepCopyNodes(pathDataNodeArr);
            }
        }

        public d(d dVar) {
            this.a = null;
            this.b = dVar.b;
            this.c = dVar.c;
            this.a = PathParser.deepCopyNodes(dVar.a);
        }
    }

    @RequiresApi(24)
    public static class g extends Drawable.ConstantState {
        public final Drawable.ConstantState a;

        public g(Drawable.ConstantState constantState) {
            this.a = constantState;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public boolean canApplyTheme() {
            return this.a.canApplyTheme();
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return this.a.getChangingConfigurations();
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable() {
            VectorDrawableCompat vectorDrawableCompat = new VectorDrawableCompat();
            vectorDrawableCompat.a = (VectorDrawable) this.a.newDrawable();
            return vectorDrawableCompat;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources resources) {
            VectorDrawableCompat vectorDrawableCompat = new VectorDrawableCompat();
            vectorDrawableCompat.a = (VectorDrawable) this.a.newDrawable(resources);
            return vectorDrawableCompat;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources resources, Resources.Theme theme) {
            VectorDrawableCompat vectorDrawableCompat = new VectorDrawableCompat();
            vectorDrawableCompat.a = (VectorDrawable) this.a.newDrawable(resources, theme);
            return vectorDrawableCompat;
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        Drawable drawable = this.a;
        if (drawable != null) {
            drawable.setColorFilter(colorFilter);
        } else {
            this.d = colorFilter;
            invalidateSelf();
        }
    }

    public static int a(int i, float f2) {
        return (i & ViewCompat.MEASURED_SIZE_MASK) | (((int) (Color.alpha(i) * f2)) << 24);
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        int i;
        int i2;
        Drawable drawable = this.a;
        if (drawable != null) {
            DrawableCompat.inflate(drawable, resources, xmlPullParser, attributeSet, theme);
            return;
        }
        f fVar = this.b;
        fVar.b = new e();
        TypedArray typedArrayObtainAttributes = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, s2.a);
        f fVar2 = this.b;
        e eVar = fVar2.b;
        int namedInt = TypedArrayUtils.getNamedInt(typedArrayObtainAttributes, xmlPullParser, "tintMode", 6, -1);
        PorterDuff.Mode mode = PorterDuff.Mode.SRC_IN;
        int i3 = 3;
        if (namedInt == 3) {
            mode = PorterDuff.Mode.SRC_OVER;
        } else if (namedInt != 5) {
            if (namedInt != 9) {
                switch (namedInt) {
                    case 14:
                        mode = PorterDuff.Mode.MULTIPLY;
                        break;
                    case 15:
                        mode = PorterDuff.Mode.SCREEN;
                        break;
                    case 16:
                        mode = PorterDuff.Mode.ADD;
                        break;
                }
            } else {
                mode = PorterDuff.Mode.SRC_ATOP;
            }
        }
        fVar2.d = mode;
        int i4 = 1;
        ColorStateList colorStateList = typedArrayObtainAttributes.getColorStateList(1);
        if (colorStateList != null) {
            fVar2.c = colorStateList;
        }
        fVar2.e = TypedArrayUtils.getNamedBoolean(typedArrayObtainAttributes, xmlPullParser, "autoMirrored", 5, fVar2.e);
        eVar.k = TypedArrayUtils.getNamedFloat(typedArrayObtainAttributes, xmlPullParser, "viewportWidth", 7, eVar.k);
        float namedFloat = TypedArrayUtils.getNamedFloat(typedArrayObtainAttributes, xmlPullParser, "viewportHeight", 8, eVar.l);
        eVar.l = namedFloat;
        if (eVar.k <= 0.0f) {
            throw new XmlPullParserException(typedArrayObtainAttributes.getPositionDescription() + "<vector> tag requires viewportWidth > 0");
        }
        if (namedFloat > 0.0f) {
            eVar.i = typedArrayObtainAttributes.getDimension(3, eVar.i);
            int i5 = 2;
            float dimension = typedArrayObtainAttributes.getDimension(2, eVar.j);
            eVar.j = dimension;
            if (eVar.i <= 0.0f) {
                throw new XmlPullParserException(typedArrayObtainAttributes.getPositionDescription() + "<vector> tag requires width > 0");
            }
            if (dimension > 0.0f) {
                eVar.setAlpha(TypedArrayUtils.getNamedFloat(typedArrayObtainAttributes, xmlPullParser, "alpha", 4, eVar.getAlpha()));
                String string = typedArrayObtainAttributes.getString(0);
                if (string != null) {
                    eVar.n = string;
                    eVar.o.put(string, eVar);
                }
                typedArrayObtainAttributes.recycle();
                fVar.a = getChangingConfigurations();
                fVar.k = true;
                f fVar3 = this.b;
                e eVar2 = fVar3.b;
                ArrayDeque arrayDeque = new ArrayDeque();
                arrayDeque.push(eVar2.h);
                int eventType = xmlPullParser.getEventType();
                int depth = xmlPullParser.getDepth() + 1;
                boolean z = true;
                while (eventType != i4 && (xmlPullParser.getDepth() >= depth || eventType != i3)) {
                    if (eventType == i5) {
                        String name = xmlPullParser.getName();
                        c cVar = (c) arrayDeque.peek();
                        if ("path".equals(name)) {
                            b bVar = new b();
                            TypedArray typedArrayObtainAttributes2 = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, s2.c);
                            bVar.d = null;
                            if (TypedArrayUtils.hasAttribute(xmlPullParser, "pathData")) {
                                String string2 = typedArrayObtainAttributes2.getString(0);
                                if (string2 != null) {
                                    bVar.b = string2;
                                }
                                String string3 = typedArrayObtainAttributes2.getString(2);
                                if (string3 != null) {
                                    bVar.a = PathParser.createNodesFromPathData(string3);
                                }
                                bVar.g = TypedArrayUtils.getNamedColor(typedArrayObtainAttributes2, xmlPullParser, "fillColor", 1, bVar.g);
                                bVar.j = TypedArrayUtils.getNamedFloat(typedArrayObtainAttributes2, xmlPullParser, "fillAlpha", 12, bVar.j);
                                int namedInt2 = TypedArrayUtils.getNamedInt(typedArrayObtainAttributes2, xmlPullParser, "strokeLineCap", 8, -1);
                                Paint.Cap cap = bVar.n;
                                if (namedInt2 == 0) {
                                    cap = Paint.Cap.BUTT;
                                } else if (namedInt2 == 1) {
                                    cap = Paint.Cap.ROUND;
                                } else if (namedInt2 == 2) {
                                    cap = Paint.Cap.SQUARE;
                                }
                                bVar.n = cap;
                                int namedInt3 = TypedArrayUtils.getNamedInt(typedArrayObtainAttributes2, xmlPullParser, "strokeLineJoin", 9, -1);
                                Paint.Join join = bVar.o;
                                if (namedInt3 == 0) {
                                    join = Paint.Join.MITER;
                                } else if (namedInt3 == 1) {
                                    join = Paint.Join.ROUND;
                                } else if (namedInt3 == 2) {
                                    join = Paint.Join.BEVEL;
                                }
                                bVar.o = join;
                                bVar.p = TypedArrayUtils.getNamedFloat(typedArrayObtainAttributes2, xmlPullParser, "strokeMiterLimit", 10, bVar.p);
                                bVar.e = TypedArrayUtils.getNamedColor(typedArrayObtainAttributes2, xmlPullParser, "strokeColor", 3, bVar.e);
                                bVar.h = TypedArrayUtils.getNamedFloat(typedArrayObtainAttributes2, xmlPullParser, "strokeAlpha", 11, bVar.h);
                                bVar.f = TypedArrayUtils.getNamedFloat(typedArrayObtainAttributes2, xmlPullParser, "strokeWidth", 4, bVar.f);
                                bVar.l = TypedArrayUtils.getNamedFloat(typedArrayObtainAttributes2, xmlPullParser, "trimPathEnd", 6, bVar.l);
                                bVar.m = TypedArrayUtils.getNamedFloat(typedArrayObtainAttributes2, xmlPullParser, "trimPathOffset", 7, bVar.m);
                                bVar.k = TypedArrayUtils.getNamedFloat(typedArrayObtainAttributes2, xmlPullParser, "trimPathStart", 5, bVar.k);
                                bVar.i = TypedArrayUtils.getNamedInt(typedArrayObtainAttributes2, xmlPullParser, "fillType", 13, bVar.i);
                            }
                            typedArrayObtainAttributes2.recycle();
                            cVar.b.add(bVar);
                            if (bVar.getPathName() != null) {
                                eVar2.o.put(bVar.getPathName(), bVar);
                            }
                            fVar3.a |= bVar.c;
                            i2 = 3;
                            i = 1;
                            z = false;
                        } else {
                            if ("clip-path".equals(name)) {
                                a aVar = new a();
                                if (TypedArrayUtils.hasAttribute(xmlPullParser, "pathData")) {
                                    TypedArray typedArrayObtainAttributes3 = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, s2.d);
                                    String string4 = typedArrayObtainAttributes3.getString(0);
                                    if (string4 != null) {
                                        aVar.b = string4;
                                    }
                                    String string5 = typedArrayObtainAttributes3.getString(1);
                                    if (string5 != null) {
                                        aVar.a = PathParser.createNodesFromPathData(string5);
                                    }
                                    typedArrayObtainAttributes3.recycle();
                                }
                                cVar.b.add(aVar);
                                if (aVar.getPathName() != null) {
                                    eVar2.o.put(aVar.getPathName(), aVar);
                                }
                                fVar3.a = aVar.c | fVar3.a;
                                i2 = 3;
                            } else {
                                if ("group".equals(name)) {
                                    c cVar2 = new c();
                                    TypedArray typedArrayObtainAttributes4 = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, s2.b);
                                    cVar2.l = null;
                                    cVar2.c = TypedArrayUtils.getNamedFloat(typedArrayObtainAttributes4, xmlPullParser, "rotation", 5, cVar2.c);
                                    cVar2.d = typedArrayObtainAttributes4.getFloat(1, cVar2.d);
                                    cVar2.e = typedArrayObtainAttributes4.getFloat(2, cVar2.e);
                                    cVar2.f = TypedArrayUtils.getNamedFloat(typedArrayObtainAttributes4, xmlPullParser, "scaleX", 3, cVar2.f);
                                    cVar2.g = TypedArrayUtils.getNamedFloat(typedArrayObtainAttributes4, xmlPullParser, "scaleY", 4, cVar2.g);
                                    cVar2.h = TypedArrayUtils.getNamedFloat(typedArrayObtainAttributes4, xmlPullParser, "translateX", 6, cVar2.h);
                                    cVar2.i = TypedArrayUtils.getNamedFloat(typedArrayObtainAttributes4, xmlPullParser, "translateY", 7, cVar2.i);
                                    String string6 = typedArrayObtainAttributes4.getString(0);
                                    if (string6 != null) {
                                        cVar2.m = string6;
                                    }
                                    cVar2.a();
                                    typedArrayObtainAttributes4.recycle();
                                    cVar.b.add(cVar2);
                                    arrayDeque.push(cVar2);
                                    if (cVar2.getGroupName() != null) {
                                        eVar2.o.put(cVar2.getGroupName(), cVar2);
                                    }
                                    fVar3.a = cVar2.k | fVar3.a;
                                }
                                i2 = 3;
                            }
                            i = 1;
                        }
                    } else {
                        int i6 = i3;
                        i = i4;
                        i2 = i6;
                        if (eventType == i2 && "group".equals(xmlPullParser.getName())) {
                            arrayDeque.pop();
                        }
                    }
                    eventType = xmlPullParser.next();
                    i5 = 2;
                    int i7 = i;
                    i3 = i2;
                    i4 = i7;
                }
                if (!z) {
                    this.c = a(fVar.c, fVar.d);
                    return;
                }
                throw new XmlPullParserException("no path defined");
            }
            throw new XmlPullParserException(typedArrayObtainAttributes.getPositionDescription() + "<vector> tag requires height > 0");
        }
        throw new XmlPullParserException(typedArrayObtainAttributes.getPositionDescription() + "<vector> tag requires viewportHeight > 0");
    }

    public VectorDrawableCompat(@NonNull f fVar) {
        this.f = true;
        this.g = new float[9];
        this.h = new Matrix();
        this.i = new Rect();
        this.b = fVar;
        this.c = a(fVar.c, fVar.d);
    }

    public static class e {
        public static final Matrix p = new Matrix();
        public final Path a;
        public final Path b;
        public final Matrix c;
        public Paint d;
        public Paint e;
        public PathMeasure f;
        public int g;
        public final c h;
        public float i;
        public float j;
        public float k;
        public float l;
        public int m;
        public String n;
        public final ArrayMap<String, Object> o;

        public e() {
            this.c = new Matrix();
            this.i = 0.0f;
            this.j = 0.0f;
            this.k = 0.0f;
            this.l = 0.0f;
            this.m = 255;
            this.n = null;
            this.o = new ArrayMap<>();
            this.h = new c();
            this.a = new Path();
            this.b = new Path();
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r11v0 */
        /* JADX WARN: Type inference failed for: r11v1, types: [boolean] */
        /* JADX WARN: Type inference failed for: r11v4 */
        public final void a(c cVar, Matrix matrix, Canvas canvas, int i, int i2, ColorFilter colorFilter) {
            e eVar;
            e eVar2 = this;
            cVar.a.set(matrix);
            cVar.a.preConcat(cVar.j);
            canvas.save();
            ?? r11 = 0;
            int i3 = 0;
            while (i3 < cVar.b.size()) {
                Object obj = cVar.b.get(i3);
                if (obj instanceof c) {
                    a((c) obj, cVar.a, canvas, i, i2, colorFilter);
                } else {
                    if (obj instanceof d) {
                        d dVar = (d) obj;
                        float f = i / eVar2.k;
                        float f2 = i2 / eVar2.l;
                        float fMin = Math.min(f, f2);
                        Matrix matrix2 = cVar.a;
                        eVar2.c.set(matrix2);
                        eVar2.c.postScale(f, f2);
                        float[] fArr = {0.0f, 1.0f, 1.0f, 0.0f};
                        matrix2.mapVectors(fArr);
                        float fHypot = (float) Math.hypot(fArr[r11], fArr[1]);
                        float fHypot2 = (float) Math.hypot(fArr[2], fArr[3]);
                        float f3 = (fArr[r11] * fArr[3]) - (fArr[1] * fArr[2]);
                        float fMax = Math.max(fHypot, fHypot2);
                        float fAbs = fMax > 0.0f ? Math.abs(f3) / fMax : 0.0f;
                        if (fAbs == 0.0f) {
                            eVar = this;
                        } else {
                            eVar = this;
                            Path path = eVar.a;
                            if (dVar == null) {
                                throw null;
                            }
                            path.reset();
                            PathParser.PathDataNode[] pathDataNodeArr = dVar.a;
                            if (pathDataNodeArr != null) {
                                PathParser.PathDataNode.nodesToPath(pathDataNodeArr, path);
                            }
                            Path path2 = eVar.a;
                            eVar.b.reset();
                            if (dVar.a()) {
                                eVar.b.addPath(path2, eVar.c);
                                canvas.clipPath(eVar.b);
                            } else {
                                b bVar = (b) dVar;
                                if (bVar.k != 0.0f || bVar.l != 1.0f) {
                                    float f4 = bVar.k;
                                    float f5 = bVar.m;
                                    float f6 = (f4 + f5) % 1.0f;
                                    float f7 = (bVar.l + f5) % 1.0f;
                                    if (eVar.f == null) {
                                        eVar.f = new PathMeasure();
                                    }
                                    eVar.f.setPath(eVar.a, r11);
                                    float length = eVar.f.getLength();
                                    float f8 = f6 * length;
                                    float f9 = f7 * length;
                                    path2.reset();
                                    if (f8 > f9) {
                                        eVar.f.getSegment(f8, length, path2, true);
                                        eVar.f.getSegment(0.0f, f9, path2, true);
                                    } else {
                                        eVar.f.getSegment(f8, f9, path2, true);
                                    }
                                    path2.rLineTo(0.0f, 0.0f);
                                }
                                eVar.b.addPath(path2, eVar.c);
                                if (bVar.g != 0) {
                                    if (eVar.e == null) {
                                        Paint paint = new Paint();
                                        eVar.e = paint;
                                        paint.setStyle(Paint.Style.FILL);
                                        eVar.e.setAntiAlias(true);
                                    }
                                    Paint paint2 = eVar.e;
                                    paint2.setColor(VectorDrawableCompat.a(bVar.g, bVar.j));
                                    paint2.setColorFilter(colorFilter);
                                    eVar.b.setFillType(bVar.i == 0 ? Path.FillType.WINDING : Path.FillType.EVEN_ODD);
                                    canvas.drawPath(eVar.b, paint2);
                                }
                                if (bVar.e != 0) {
                                    if (eVar.d == null) {
                                        Paint paint3 = new Paint();
                                        eVar.d = paint3;
                                        paint3.setStyle(Paint.Style.STROKE);
                                        eVar.d.setAntiAlias(true);
                                    }
                                    Paint paint4 = eVar.d;
                                    Paint.Join join = bVar.o;
                                    if (join != null) {
                                        paint4.setStrokeJoin(join);
                                    }
                                    Paint.Cap cap = bVar.n;
                                    if (cap != null) {
                                        paint4.setStrokeCap(cap);
                                    }
                                    paint4.setStrokeMiter(bVar.p);
                                    paint4.setColor(VectorDrawableCompat.a(bVar.e, bVar.h));
                                    paint4.setColorFilter(colorFilter);
                                    paint4.setStrokeWidth(bVar.f * fAbs * fMin);
                                    canvas.drawPath(eVar.b, paint4);
                                }
                            }
                        }
                    }
                    i3++;
                    eVar2 = eVar;
                    r11 = 0;
                }
                eVar = eVar2;
                i3++;
                eVar2 = eVar;
                r11 = 0;
            }
            canvas.restore();
        }

        public float getAlpha() {
            return getRootAlpha() / 255.0f;
        }

        public int getRootAlpha() {
            return this.m;
        }

        public void setAlpha(float f) {
            setRootAlpha((int) (f * 255.0f));
        }

        public void setRootAlpha(int i) {
            this.m = i;
        }

        public e(e eVar) {
            this.c = new Matrix();
            this.i = 0.0f;
            this.j = 0.0f;
            this.k = 0.0f;
            this.l = 0.0f;
            this.m = 255;
            this.n = null;
            ArrayMap<String, Object> arrayMap = new ArrayMap<>();
            this.o = arrayMap;
            this.h = new c(eVar.h, arrayMap);
            this.a = new Path(eVar.a);
            this.b = new Path(eVar.b);
            this.i = eVar.i;
            this.j = eVar.j;
            this.k = eVar.k;
            this.l = eVar.l;
            this.g = eVar.g;
            this.m = eVar.m;
            this.n = eVar.n;
            String str = eVar.n;
            if (str != null) {
                this.o.put(str, this);
            }
        }
    }

    public static class b extends d {
        public int[] d;
        public int e;
        public float f;
        public int g;
        public float h;
        public int i;
        public float j;
        public float k;
        public float l;
        public float m;
        public Paint.Cap n;
        public Paint.Join o;
        public float p;

        public b() {
            this.e = 0;
            this.f = 0.0f;
            this.g = 0;
            this.h = 1.0f;
            this.i = 0;
            this.j = 1.0f;
            this.k = 0.0f;
            this.l = 1.0f;
            this.m = 0.0f;
            this.n = Paint.Cap.BUTT;
            this.o = Paint.Join.MITER;
            this.p = 4.0f;
        }

        public float getFillAlpha() {
            return this.j;
        }

        public int getFillColor() {
            return this.g;
        }

        public float getStrokeAlpha() {
            return this.h;
        }

        public int getStrokeColor() {
            return this.e;
        }

        public float getStrokeWidth() {
            return this.f;
        }

        public float getTrimPathEnd() {
            return this.l;
        }

        public float getTrimPathOffset() {
            return this.m;
        }

        public float getTrimPathStart() {
            return this.k;
        }

        public void setFillAlpha(float f) {
            this.j = f;
        }

        public void setFillColor(int i) {
            this.g = i;
        }

        public void setStrokeAlpha(float f) {
            this.h = f;
        }

        public void setStrokeColor(int i) {
            this.e = i;
        }

        public void setStrokeWidth(float f) {
            this.f = f;
        }

        public void setTrimPathEnd(float f) {
            this.l = f;
        }

        public void setTrimPathOffset(float f) {
            this.m = f;
        }

        public void setTrimPathStart(float f) {
            this.k = f;
        }

        public b(b bVar) {
            super(bVar);
            this.e = 0;
            this.f = 0.0f;
            this.g = 0;
            this.h = 1.0f;
            this.i = 0;
            this.j = 1.0f;
            this.k = 0.0f;
            this.l = 1.0f;
            this.m = 0.0f;
            this.n = Paint.Cap.BUTT;
            this.o = Paint.Join.MITER;
            this.p = 4.0f;
            this.d = bVar.d;
            this.e = bVar.e;
            this.f = bVar.f;
            this.h = bVar.h;
            this.g = bVar.g;
            this.i = bVar.i;
            this.j = bVar.j;
            this.k = bVar.k;
            this.l = bVar.l;
            this.m = bVar.m;
            this.n = bVar.n;
            this.o = bVar.o;
            this.p = bVar.p;
        }
    }

    public static class c {
        public final Matrix a;
        public final ArrayList<Object> b;
        public float c;
        public float d;
        public float e;
        public float f;
        public float g;
        public float h;
        public float i;
        public final Matrix j;
        public int k;
        public int[] l;
        public String m;

        public c(c cVar, ArrayMap<String, Object> arrayMap) {
            d aVar;
            this.a = new Matrix();
            this.b = new ArrayList<>();
            this.c = 0.0f;
            this.d = 0.0f;
            this.e = 0.0f;
            this.f = 1.0f;
            this.g = 1.0f;
            this.h = 0.0f;
            this.i = 0.0f;
            this.j = new Matrix();
            this.m = null;
            this.c = cVar.c;
            this.d = cVar.d;
            this.e = cVar.e;
            this.f = cVar.f;
            this.g = cVar.g;
            this.h = cVar.h;
            this.i = cVar.i;
            this.l = cVar.l;
            String str = cVar.m;
            this.m = str;
            this.k = cVar.k;
            if (str != null) {
                arrayMap.put(str, this);
            }
            this.j.set(cVar.j);
            ArrayList<Object> arrayList = cVar.b;
            for (int i = 0; i < arrayList.size(); i++) {
                Object obj = arrayList.get(i);
                if (obj instanceof c) {
                    this.b.add(new c((c) obj, arrayMap));
                } else {
                    if (obj instanceof b) {
                        aVar = new b((b) obj);
                    } else {
                        if (!(obj instanceof a)) {
                            throw new IllegalStateException("Unknown object in the tree!");
                        }
                        aVar = new a((a) obj);
                    }
                    this.b.add(aVar);
                    String str2 = aVar.b;
                    if (str2 != null) {
                        arrayMap.put(str2, aVar);
                    }
                }
            }
        }

        public final void a() {
            this.j.reset();
            this.j.postTranslate(-this.d, -this.e);
            this.j.postScale(this.f, this.g);
            this.j.postRotate(this.c, 0.0f, 0.0f);
            this.j.postTranslate(this.h + this.d, this.i + this.e);
        }

        public String getGroupName() {
            return this.m;
        }

        public Matrix getLocalMatrix() {
            return this.j;
        }

        public float getPivotX() {
            return this.d;
        }

        public float getPivotY() {
            return this.e;
        }

        public float getRotation() {
            return this.c;
        }

        public float getScaleX() {
            return this.f;
        }

        public float getScaleY() {
            return this.g;
        }

        public float getTranslateX() {
            return this.h;
        }

        public float getTranslateY() {
            return this.i;
        }

        public void setPivotX(float f) {
            if (f != this.d) {
                this.d = f;
                a();
            }
        }

        public void setPivotY(float f) {
            if (f != this.e) {
                this.e = f;
                a();
            }
        }

        public void setRotation(float f) {
            if (f != this.c) {
                this.c = f;
                a();
            }
        }

        public void setScaleX(float f) {
            if (f != this.f) {
                this.f = f;
                a();
            }
        }

        public void setScaleY(float f) {
            if (f != this.g) {
                this.g = f;
                a();
            }
        }

        public void setTranslateX(float f) {
            if (f != this.h) {
                this.h = f;
                a();
            }
        }

        public void setTranslateY(float f) {
            if (f != this.i) {
                this.i = f;
                a();
            }
        }

        public c() {
            this.a = new Matrix();
            this.b = new ArrayList<>();
            this.c = 0.0f;
            this.d = 0.0f;
            this.e = 0.0f;
            this.f = 1.0f;
            this.g = 1.0f;
            this.h = 0.0f;
            this.i = 0.0f;
            this.j = new Matrix();
            this.m = null;
        }
    }
}
