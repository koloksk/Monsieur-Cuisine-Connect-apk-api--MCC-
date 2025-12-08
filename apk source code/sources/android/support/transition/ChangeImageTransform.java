package android.support.transition;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import defpackage.b3;
import defpackage.e3;
import defpackage.o3;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/* loaded from: classes.dex */
public class ChangeImageTransform extends Transition {
    public static final String[] I = {"android:changeImageTransform:matrix", "android:changeImageTransform:bounds"};
    public static final TypeEvaluator<Matrix> J = new a();
    public static final Property<ImageView, Matrix> K = new b(Matrix.class, "animatedTransform");

    public static class a implements TypeEvaluator<Matrix> {
        @Override // android.animation.TypeEvaluator
        public Matrix evaluate(float f, Matrix matrix, Matrix matrix2) {
            return null;
        }
    }

    public static class b extends Property<ImageView, Matrix> {
        public b(Class cls, String str) {
            super(cls, str);
        }

        @Override // android.util.Property
        public Matrix get(ImageView imageView) {
            return null;
        }

        @Override // android.util.Property
        public void set(ImageView imageView, Matrix matrix) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
            b3.a(imageView, matrix);
        }
    }

    public static /* synthetic */ class c {
        public static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[ImageView.ScaleType.values().length];
            a = iArr;
            try {
                iArr[ImageView.ScaleType.FIT_XY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[ImageView.ScaleType.CENTER_CROP.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public ChangeImageTransform() {
    }

    public final void b(TransitionValues transitionValues) {
        Matrix matrix;
        View view2 = transitionValues.f0view;
        if ((view2 instanceof ImageView) && view2.getVisibility() == 0) {
            ImageView imageView = (ImageView) view2;
            if (imageView.getDrawable() == null) {
                return;
            }
            Map<String, Object> map = transitionValues.values;
            map.put("android:changeImageTransform:bounds", new Rect(view2.getLeft(), view2.getTop(), view2.getRight(), view2.getBottom()));
            int i = c.a[imageView.getScaleType().ordinal()];
            if (i == 1) {
                Drawable drawable = imageView.getDrawable();
                Matrix matrix2 = new Matrix();
                matrix2.postScale(imageView.getWidth() / drawable.getIntrinsicWidth(), imageView.getHeight() / drawable.getIntrinsicHeight());
                matrix = matrix2;
            } else if (i != 2) {
                matrix = new Matrix(imageView.getImageMatrix());
            } else {
                Drawable drawable2 = imageView.getDrawable();
                int intrinsicWidth = drawable2.getIntrinsicWidth();
                float width = imageView.getWidth();
                float f = intrinsicWidth;
                int intrinsicHeight = drawable2.getIntrinsicHeight();
                float height = imageView.getHeight();
                float f2 = intrinsicHeight;
                float fMax = Math.max(width / f, height / f2);
                int iRound = Math.round((width - (f * fMax)) / 2.0f);
                int iRound2 = Math.round((height - (f2 * fMax)) / 2.0f);
                Matrix matrix3 = new Matrix();
                matrix3.postScale(fMax, fMax);
                matrix3.postTranslate(iRound, iRound2);
                matrix = matrix3;
            }
            map.put("android:changeImageTransform:matrix", matrix);
        }
    }

    @Override // android.support.transition.Transition
    public void captureEndValues(@NonNull TransitionValues transitionValues) {
        b(transitionValues);
    }

    @Override // android.support.transition.Transition
    public void captureStartValues(@NonNull TransitionValues transitionValues) {
        b(transitionValues);
    }

    @Override // android.support.transition.Transition
    public Animator createAnimator(@NonNull ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
        if (transitionValues == null || transitionValues2 == null) {
            return null;
        }
        Rect rect = (Rect) transitionValues.values.get("android:changeImageTransform:bounds");
        Rect rect2 = (Rect) transitionValues2.values.get("android:changeImageTransform:bounds");
        if (rect == null || rect2 == null) {
            return null;
        }
        Matrix matrix = (Matrix) transitionValues.values.get("android:changeImageTransform:matrix");
        Matrix matrix2 = (Matrix) transitionValues2.values.get("android:changeImageTransform:matrix");
        boolean z = (matrix == null && matrix2 == null) || (matrix != null && matrix.equals(matrix2));
        if (rect.equals(rect2) && z) {
            return null;
        }
        ImageView imageView = (ImageView) transitionValues2.f0view;
        Drawable drawable = imageView.getDrawable();
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        b3.a(imageView);
        if (intrinsicWidth == 0 || intrinsicHeight == 0) {
            return ObjectAnimator.ofObject(imageView, (Property<ImageView, V>) K, (TypeEvaluator) J, (Object[]) new Matrix[]{null, null});
        }
        if (matrix == null) {
            matrix = e3.a;
        }
        if (matrix2 == null) {
            matrix2 = e3.a;
        }
        K.set(imageView, matrix);
        return ObjectAnimator.ofObject(imageView, (Property<ImageView, V>) K, (TypeEvaluator) new o3(), (Object[]) new Matrix[]{matrix, matrix2});
    }

    @Override // android.support.transition.Transition
    public String[] getTransitionProperties() {
        return I;
    }

    public ChangeImageTransform(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
