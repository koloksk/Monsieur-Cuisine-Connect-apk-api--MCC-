package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.LongSparseArray;
import android.support.v4.util.LruCache;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.appcompat.R;
import android.support.v7.content.res.AppCompatResources;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.util.Xml;
import defpackage.t8;
import defpackage.u8;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public final class AppCompatDrawableManager {
    public static AppCompatDrawableManager i;
    public WeakHashMap<Context, SparseArrayCompat<ColorStateList>> a;
    public ArrayMap<String, c> b;
    public SparseArrayCompat<String> c;
    public final Object d = new Object();
    public final WeakHashMap<Context, LongSparseArray<WeakReference<Drawable.ConstantState>>> e = new WeakHashMap<>(0);
    public TypedValue f;
    public boolean g;
    public static final PorterDuff.Mode h = PorterDuff.Mode.SRC_IN;
    public static final b j = new b(6);
    public static final int[] k = {R.drawable.abc_textfield_search_default_mtrl_alpha, R.drawable.abc_textfield_default_mtrl_alpha, R.drawable.abc_ab_share_pack_mtrl_alpha};
    public static final int[] l = {R.drawable.abc_ic_commit_search_api_mtrl_alpha, R.drawable.abc_seekbar_tick_mark_material, R.drawable.abc_ic_menu_share_mtrl_alpha, R.drawable.abc_ic_menu_copy_mtrl_am_alpha, R.drawable.abc_ic_menu_cut_mtrl_alpha, R.drawable.abc_ic_menu_selectall_mtrl_alpha, R.drawable.abc_ic_menu_paste_mtrl_am_alpha};
    public static final int[] m = {R.drawable.abc_textfield_activated_mtrl_alpha, R.drawable.abc_textfield_search_activated_mtrl_alpha, R.drawable.abc_cab_background_top_mtrl_alpha, R.drawable.abc_text_cursor_material, R.drawable.abc_text_select_handle_left_mtrl_dark, R.drawable.abc_text_select_handle_middle_mtrl_dark, R.drawable.abc_text_select_handle_right_mtrl_dark, R.drawable.abc_text_select_handle_left_mtrl_light, R.drawable.abc_text_select_handle_middle_mtrl_light, R.drawable.abc_text_select_handle_right_mtrl_light};
    public static final int[] n = {R.drawable.abc_popup_background_mtrl_mult, R.drawable.abc_cab_background_internal_bg, R.drawable.abc_menu_hardkey_panel_mtrl_mult};
    public static final int[] o = {R.drawable.abc_tab_indicator_material, R.drawable.abc_textfield_search_material};
    public static final int[] p = {R.drawable.abc_btn_check_material, R.drawable.abc_btn_radio_material};

    @RequiresApi(11)
    public static class a implements c {
        @Override // android.support.v7.widget.AppCompatDrawableManager.c
        public Drawable a(@NonNull Context context, @NonNull XmlPullParser xmlPullParser, @NonNull AttributeSet attributeSet, @Nullable Resources.Theme theme) {
            try {
                return AnimatedVectorDrawableCompat.createFromXmlInner(context, context.getResources(), xmlPullParser, attributeSet, theme);
            } catch (Exception e) {
                Log.e("AvdcInflateDelegate", "Exception while inflating <animated-vector>", e);
                return null;
            }
        }
    }

    public static class b extends LruCache<Integer, PorterDuffColorFilter> {
        public b(int i) {
            super(i);
        }
    }

    public interface c {
        Drawable a(@NonNull Context context, @NonNull XmlPullParser xmlPullParser, @NonNull AttributeSet attributeSet, @Nullable Resources.Theme theme);
    }

    public static class d implements c {
        @Override // android.support.v7.widget.AppCompatDrawableManager.c
        public Drawable a(@NonNull Context context, @NonNull XmlPullParser xmlPullParser, @NonNull AttributeSet attributeSet, @Nullable Resources.Theme theme) {
            try {
                return VectorDrawableCompat.createFromXmlInner(context.getResources(), xmlPullParser, attributeSet, theme);
            } catch (Exception e) {
                Log.e("VdcInflateDelegate", "Exception while inflating <vector>", e);
                return null;
            }
        }
    }

    public static AppCompatDrawableManager get() {
        if (i == null) {
            AppCompatDrawableManager appCompatDrawableManager = new AppCompatDrawableManager();
            i = appCompatDrawableManager;
            if (Build.VERSION.SDK_INT < 24) {
                d dVar = new d();
                if (appCompatDrawableManager.b == null) {
                    appCompatDrawableManager.b = new ArrayMap<>();
                }
                appCompatDrawableManager.b.put("vector", dVar);
                a aVar = new a();
                if (appCompatDrawableManager.b == null) {
                    appCompatDrawableManager.b = new ArrayMap<>();
                }
                appCompatDrawableManager.b.put("animated-vector", aVar);
            }
        }
        return i;
    }

    public static PorterDuffColorFilter getPorterDuffColorFilter(int i2, PorterDuff.Mode mode) {
        b bVar = j;
        if (bVar == null) {
            throw null;
        }
        int i3 = (i2 + 31) * 31;
        PorterDuffColorFilter porterDuffColorFilter = bVar.get(Integer.valueOf(mode.hashCode() + i3));
        if (porterDuffColorFilter == null) {
            porterDuffColorFilter = new PorterDuffColorFilter(i2, mode);
            b bVar2 = j;
            if (bVar2 == null) {
                throw null;
            }
            bVar2.put(Integer.valueOf(mode.hashCode() + i3), porterDuffColorFilter);
        }
        return porterDuffColorFilter;
    }

    public final Drawable a(@NonNull Context context, @DrawableRes int i2, boolean z, @NonNull Drawable drawable) {
        ColorStateList colorStateListB = b(context, i2);
        if (colorStateListB != null) {
            if (DrawableUtils.canSafelyMutateDrawable(drawable)) {
                drawable = drawable.mutate();
            }
            Drawable drawableWrap = DrawableCompat.wrap(drawable);
            DrawableCompat.setTintList(drawableWrap, colorStateListB);
            PorterDuff.Mode mode = i2 == R.drawable.abc_switch_thumb_material ? PorterDuff.Mode.MULTIPLY : null;
            if (mode == null) {
                return drawableWrap;
            }
            DrawableCompat.setTintMode(drawableWrap, mode);
            return drawableWrap;
        }
        if (i2 == R.drawable.abc_seekbar_track_material) {
            LayerDrawable layerDrawable = (LayerDrawable) drawable;
            a(layerDrawable.findDrawableByLayerId(android.R.id.background), t8.b(context, R.attr.colorControlNormal), h);
            a(layerDrawable.findDrawableByLayerId(android.R.id.secondaryProgress), t8.b(context, R.attr.colorControlNormal), h);
            a(layerDrawable.findDrawableByLayerId(android.R.id.progress), t8.b(context, R.attr.colorControlActivated), h);
            return drawable;
        }
        if (i2 != R.drawable.abc_ratingbar_material && i2 != R.drawable.abc_ratingbar_indicator_material && i2 != R.drawable.abc_ratingbar_small_material) {
            if (a(context, i2, drawable) || !z) {
                return drawable;
            }
            return null;
        }
        LayerDrawable layerDrawable2 = (LayerDrawable) drawable;
        a(layerDrawable2.findDrawableByLayerId(android.R.id.background), t8.a(context, R.attr.colorControlNormal), h);
        a(layerDrawable2.findDrawableByLayerId(android.R.id.secondaryProgress), t8.b(context, R.attr.colorControlActivated), h);
        a(layerDrawable2.findDrawableByLayerId(android.R.id.progress), t8.b(context, R.attr.colorControlActivated), h);
        return drawable;
    }

    public ColorStateList b(@NonNull Context context, @DrawableRes int i2) {
        SparseArrayCompat<ColorStateList> sparseArrayCompat;
        WeakHashMap<Context, SparseArrayCompat<ColorStateList>> weakHashMap = this.a;
        ColorStateList colorStateList = null;
        if (weakHashMap != null && (sparseArrayCompat = weakHashMap.get(context)) != null) {
            colorStateList = sparseArrayCompat.get(i2);
        }
        if (colorStateList == null) {
            if (i2 == R.drawable.abc_edit_text_material) {
                colorStateList = AppCompatResources.getColorStateList(context, R.color.abc_tint_edittext);
            } else if (i2 == R.drawable.abc_switch_track_mtrl_alpha) {
                colorStateList = AppCompatResources.getColorStateList(context, R.color.abc_tint_switch_track);
            } else if (i2 == R.drawable.abc_switch_thumb_material) {
                int[][] iArr = new int[3][];
                int[] iArr2 = new int[3];
                ColorStateList colorStateListC = t8.c(context, R.attr.colorSwitchThumbNormal);
                if (colorStateListC == null || !colorStateListC.isStateful()) {
                    iArr[0] = t8.b;
                    iArr2[0] = t8.a(context, R.attr.colorSwitchThumbNormal);
                    iArr[1] = t8.e;
                    iArr2[1] = t8.b(context, R.attr.colorControlActivated);
                    iArr[2] = t8.f;
                    iArr2[2] = t8.b(context, R.attr.colorSwitchThumbNormal);
                } else {
                    iArr[0] = t8.b;
                    iArr2[0] = colorStateListC.getColorForState(iArr[0], 0);
                    iArr[1] = t8.e;
                    iArr2[1] = t8.b(context, R.attr.colorControlActivated);
                    iArr[2] = t8.f;
                    iArr2[2] = colorStateListC.getDefaultColor();
                }
                colorStateList = new ColorStateList(iArr, iArr2);
            } else if (i2 == R.drawable.abc_btn_default_mtrl_shape) {
                colorStateList = a(context, t8.b(context, R.attr.colorButtonNormal));
            } else if (i2 == R.drawable.abc_btn_borderless_material) {
                colorStateList = a(context, 0);
            } else if (i2 == R.drawable.abc_btn_colored_material) {
                colorStateList = a(context, t8.b(context, R.attr.colorAccent));
            } else if (i2 == R.drawable.abc_spinner_mtrl_am_alpha || i2 == R.drawable.abc_spinner_textfield_background_material) {
                colorStateList = AppCompatResources.getColorStateList(context, R.color.abc_tint_spinner);
            } else if (a(l, i2)) {
                colorStateList = t8.c(context, R.attr.colorControlNormal);
            } else if (a(o, i2)) {
                colorStateList = AppCompatResources.getColorStateList(context, R.color.abc_tint_default);
            } else if (a(p, i2)) {
                colorStateList = AppCompatResources.getColorStateList(context, R.color.abc_tint_btn_checkable);
            } else if (i2 == R.drawable.abc_seekbar_thumb_material) {
                colorStateList = AppCompatResources.getColorStateList(context, R.color.abc_tint_seek_thumb);
            }
            if (colorStateList != null) {
                if (this.a == null) {
                    this.a = new WeakHashMap<>();
                }
                SparseArrayCompat<ColorStateList> sparseArrayCompat2 = this.a.get(context);
                if (sparseArrayCompat2 == null) {
                    sparseArrayCompat2 = new SparseArrayCompat<>();
                    this.a.put(context, sparseArrayCompat2);
                }
                sparseArrayCompat2.append(i2, colorStateList);
            }
        }
        return colorStateList;
    }

    public final Drawable c(@NonNull Context context, @DrawableRes int i2) throws XmlPullParserException, Resources.NotFoundException, IOException {
        int next;
        ArrayMap<String, c> arrayMap = this.b;
        if (arrayMap == null || arrayMap.isEmpty()) {
            return null;
        }
        SparseArrayCompat<String> sparseArrayCompat = this.c;
        if (sparseArrayCompat != null) {
            String str = sparseArrayCompat.get(i2);
            if ("appcompat_skip_skip".equals(str) || (str != null && this.b.get(str) == null)) {
                return null;
            }
        } else {
            this.c = new SparseArrayCompat<>();
        }
        if (this.f == null) {
            this.f = new TypedValue();
        }
        TypedValue typedValue = this.f;
        Resources resources = context.getResources();
        resources.getValue(i2, typedValue, true);
        long j2 = (typedValue.assetCookie << 32) | typedValue.data;
        Drawable drawableA = a(context, j2);
        if (drawableA != null) {
            return drawableA;
        }
        CharSequence charSequence = typedValue.string;
        if (charSequence != null && charSequence.toString().endsWith(".xml")) {
            try {
                XmlResourceParser xml = resources.getXml(i2);
                AttributeSet attributeSetAsAttributeSet = Xml.asAttributeSet(xml);
                do {
                    next = xml.next();
                    if (next == 2) {
                        break;
                    }
                } while (next != 1);
                if (next != 2) {
                    throw new XmlPullParserException("No start tag found");
                }
                String name = xml.getName();
                this.c.append(i2, name);
                c cVar = this.b.get(name);
                if (cVar != null) {
                    drawableA = cVar.a(context, xml, attributeSetAsAttributeSet, context.getTheme());
                }
                if (drawableA != null) {
                    drawableA.setChangingConfigurations(typedValue.changingConfigurations);
                    a(context, j2, drawableA);
                }
            } catch (Exception e) {
                Log.e("AppCompatDrawableManag", "Exception while inflating drawable", e);
            }
        }
        if (drawableA == null) {
            this.c.append(i2, "appcompat_skip_skip");
        }
        return drawableA;
    }

    public Drawable getDrawable(@NonNull Context context, @DrawableRes int i2) {
        return a(context, i2, false);
    }

    public void onConfigurationChanged(@NonNull Context context) {
        synchronized (this.d) {
            LongSparseArray<WeakReference<Drawable.ConstantState>> longSparseArray = this.e.get(context);
            if (longSparseArray != null) {
                longSparseArray.clear();
            }
        }
    }

    public final Drawable a(@NonNull Context context, long j2) {
        synchronized (this.d) {
            LongSparseArray<WeakReference<Drawable.ConstantState>> longSparseArray = this.e.get(context);
            if (longSparseArray == null) {
                return null;
            }
            WeakReference<Drawable.ConstantState> weakReference = longSparseArray.get(j2);
            if (weakReference != null) {
                Drawable.ConstantState constantState = weakReference.get();
                if (constantState != null) {
                    return constantState.newDrawable(context.getResources());
                }
                longSparseArray.delete(j2);
            }
            return null;
        }
    }

    public final boolean a(@NonNull Context context, long j2, @NonNull Drawable drawable) {
        Drawable.ConstantState constantState = drawable.getConstantState();
        if (constantState == null) {
            return false;
        }
        synchronized (this.d) {
            LongSparseArray<WeakReference<Drawable.ConstantState>> longSparseArray = this.e.get(context);
            if (longSparseArray == null) {
                longSparseArray = new LongSparseArray<>();
                this.e.put(context, longSparseArray);
            }
            longSparseArray.put(j2, new WeakReference<>(constantState));
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0044  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x005f A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean a(@android.support.annotation.NonNull android.content.Context r6, @android.support.annotation.DrawableRes int r7, @android.support.annotation.NonNull android.graphics.drawable.Drawable r8) {
        /*
            android.graphics.PorterDuff$Mode r0 = android.support.v7.widget.AppCompatDrawableManager.h
            int[] r1 = android.support.v7.widget.AppCompatDrawableManager.k
            boolean r1 = a(r1, r7)
            r2 = 16842801(0x1010031, float:2.3693695E-38)
            r3 = -1
            r4 = 0
            r5 = 1
            if (r1 == 0) goto L15
            int r2 = android.support.v7.appcompat.R.attr.colorControlNormal
        L12:
            r7 = r3
        L13:
            r1 = r5
            goto L42
        L15:
            int[] r1 = android.support.v7.widget.AppCompatDrawableManager.m
            boolean r1 = a(r1, r7)
            if (r1 == 0) goto L20
            int r2 = android.support.v7.appcompat.R.attr.colorControlActivated
            goto L12
        L20:
            int[] r1 = android.support.v7.widget.AppCompatDrawableManager.n
            boolean r1 = a(r1, r7)
            if (r1 == 0) goto L2b
            android.graphics.PorterDuff$Mode r0 = android.graphics.PorterDuff.Mode.MULTIPLY
            goto L12
        L2b:
            int r1 = android.support.v7.appcompat.R.drawable.abc_list_divider_mtrl_alpha
            if (r7 != r1) goto L3a
            r2 = 16842800(0x1010030, float:2.3693693E-38)
            r7 = 1109603123(0x42233333, float:40.8)
            int r7 = java.lang.Math.round(r7)
            goto L13
        L3a:
            int r1 = android.support.v7.appcompat.R.drawable.abc_dialog_material_background
            if (r7 != r1) goto L3f
            goto L12
        L3f:
            r7 = r3
            r1 = r4
            r2 = r1
        L42:
            if (r1 == 0) goto L5f
            boolean r1 = android.support.v7.widget.DrawableUtils.canSafelyMutateDrawable(r8)
            if (r1 == 0) goto L4e
            android.graphics.drawable.Drawable r8 = r8.mutate()
        L4e:
            int r6 = defpackage.t8.b(r6, r2)
            android.graphics.PorterDuffColorFilter r6 = getPorterDuffColorFilter(r6, r0)
            r8.setColorFilter(r6)
            if (r7 == r3) goto L5e
            r8.setAlpha(r7)
        L5e:
            return r5
        L5f:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.AppCompatDrawableManager.a(android.content.Context, int, android.graphics.drawable.Drawable):boolean");
    }

    public static boolean a(int[] iArr, int i2) {
        for (int i3 : iArr) {
            if (i3 == i2) {
                return true;
            }
        }
        return false;
    }

    public final ColorStateList a(@NonNull Context context, @ColorInt int i2) {
        int iB = t8.b(context, R.attr.colorControlHighlight);
        return new ColorStateList(new int[][]{t8.b, t8.d, t8.c, t8.f}, new int[]{t8.a(context, R.attr.colorButtonNormal), ColorUtils.compositeColors(iB, i2), ColorUtils.compositeColors(iB, i2), i2});
    }

    public static void a(Drawable drawable, u8 u8Var, int[] iArr) {
        if (DrawableUtils.canSafelyMutateDrawable(drawable) && drawable.mutate() != drawable) {
            Log.d("AppCompatDrawableManag", "Mutated drawable is not the same instance as the input.");
            return;
        }
        if (!u8Var.d && !u8Var.c) {
            drawable.clearColorFilter();
        } else {
            PorterDuffColorFilter porterDuffColorFilter = null;
            ColorStateList colorStateList = u8Var.d ? u8Var.a : null;
            PorterDuff.Mode mode = u8Var.c ? u8Var.b : h;
            if (colorStateList != null && mode != null) {
                porterDuffColorFilter = getPorterDuffColorFilter(colorStateList.getColorForState(iArr, 0), mode);
            }
            drawable.setColorFilter(porterDuffColorFilter);
        }
        if (Build.VERSION.SDK_INT <= 23) {
            drawable.invalidateSelf();
        }
    }

    public static void a(Drawable drawable, int i2, PorterDuff.Mode mode) {
        if (DrawableUtils.canSafelyMutateDrawable(drawable)) {
            drawable = drawable.mutate();
        }
        if (mode == null) {
            mode = h;
        }
        drawable.setColorFilter(getPorterDuffColorFilter(i2, mode));
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0029, code lost:
    
        if (((r0 instanceof android.support.graphics.drawable.VectorDrawableCompat) || "android.graphics.drawable.VectorDrawable".equals(r0.getClass().getName())) != false) goto L15;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.graphics.drawable.Drawable a(@android.support.annotation.NonNull android.content.Context r9, @android.support.annotation.DrawableRes int r10, boolean r11) throws org.xmlpull.v1.XmlPullParserException, android.content.res.Resources.NotFoundException, java.io.IOException {
        /*
            r8 = this;
            boolean r0 = r8.g
            r1 = 0
            r2 = 1
            if (r0 == 0) goto L7
            goto L2b
        L7:
            r8.g = r2
            int r0 = android.support.v7.appcompat.R.drawable.abc_vector_test
            android.graphics.drawable.Drawable r0 = r8.getDrawable(r9, r0)
            if (r0 == 0) goto L90
            boolean r3 = r0 instanceof android.support.graphics.drawable.VectorDrawableCompat
            if (r3 != 0) goto L28
            java.lang.String r3 = "android.graphics.drawable.VectorDrawable"
            java.lang.Class r0 = r0.getClass()
            java.lang.String r0 = r0.getName()
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L26
            goto L28
        L26:
            r0 = r1
            goto L29
        L28:
            r0 = r2
        L29:
            if (r0 == 0) goto L90
        L2b:
            android.graphics.drawable.Drawable r0 = r8.c(r9, r10)
            if (r0 != 0) goto L7e
            android.util.TypedValue r0 = r8.f
            if (r0 != 0) goto L3c
            android.util.TypedValue r0 = new android.util.TypedValue
            r0.<init>()
            r8.f = r0
        L3c:
            android.util.TypedValue r0 = r8.f
            android.content.res.Resources r3 = r9.getResources()
            r3.getValue(r10, r0, r2)
            int r3 = r0.assetCookie
            long r3 = (long) r3
            r5 = 32
            long r3 = r3 << r5
            int r5 = r0.data
            long r5 = (long) r5
            long r3 = r3 | r5
            android.graphics.drawable.Drawable r5 = r8.a(r9, r3)
            if (r5 == 0) goto L57
        L55:
            r0 = r5
            goto L7e
        L57:
            int r6 = android.support.v7.appcompat.R.drawable.abc_cab_background_top_material
            if (r10 != r6) goto L73
            android.graphics.drawable.LayerDrawable r5 = new android.graphics.drawable.LayerDrawable
            r6 = 2
            android.graphics.drawable.Drawable[] r6 = new android.graphics.drawable.Drawable[r6]
            int r7 = android.support.v7.appcompat.R.drawable.abc_cab_background_internal_bg
            android.graphics.drawable.Drawable r7 = r8.getDrawable(r9, r7)
            r6[r1] = r7
            int r1 = android.support.v7.appcompat.R.drawable.abc_cab_background_top_mtrl_alpha
            android.graphics.drawable.Drawable r1 = r8.getDrawable(r9, r1)
            r6[r2] = r1
            r5.<init>(r6)
        L73:
            if (r5 == 0) goto L55
            int r0 = r0.changingConfigurations
            r5.setChangingConfigurations(r0)
            r8.a(r9, r3, r5)
            goto L55
        L7e:
            if (r0 != 0) goto L84
            android.graphics.drawable.Drawable r0 = android.support.v4.content.ContextCompat.getDrawable(r9, r10)
        L84:
            if (r0 == 0) goto L8a
            android.graphics.drawable.Drawable r0 = r8.a(r9, r10, r11, r0)
        L8a:
            if (r0 == 0) goto L8f
            android.support.v7.widget.DrawableUtils.a(r0)
        L8f:
            return r0
        L90:
            r8.g = r1
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r10 = "This app has been built with an incorrect configuration. Please configure your build for VectorDrawableCompat."
            r9.<init>(r10)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.AppCompatDrawableManager.a(android.content.Context, int, boolean):android.graphics.drawable.Drawable");
    }
}
