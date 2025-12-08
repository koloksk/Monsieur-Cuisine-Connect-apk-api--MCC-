package android.support.graphics.drawable;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Build;
import android.support.annotation.AnimatorRes;
import android.support.annotation.RestrictTo;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v4.graphics.PathParser;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.util.Xml;
import android.view.InflateException;
import defpackage.s2;
import java.io.IOException;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import sound.SoundLength;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class AnimatorInflaterCompat {

    public static class b implements TypeEvaluator<PathParser.PathDataNode[]> {
        public PathParser.PathDataNode[] a;

        public /* synthetic */ b(a aVar) {
        }

        @Override // android.animation.TypeEvaluator
        public PathParser.PathDataNode[] evaluate(float f, PathParser.PathDataNode[] pathDataNodeArr, PathParser.PathDataNode[] pathDataNodeArr2) {
            PathParser.PathDataNode[] pathDataNodeArr3 = pathDataNodeArr;
            PathParser.PathDataNode[] pathDataNodeArr4 = pathDataNodeArr2;
            if (!PathParser.canMorph(pathDataNodeArr3, pathDataNodeArr4)) {
                throw new IllegalArgumentException("Can't interpolate between two incompatible pathData");
            }
            PathParser.PathDataNode[] pathDataNodeArr5 = this.a;
            if (pathDataNodeArr5 == null || !PathParser.canMorph(pathDataNodeArr5, pathDataNodeArr3)) {
                this.a = PathParser.deepCopyNodes(pathDataNodeArr3);
            }
            for (int i = 0; i < pathDataNodeArr3.length; i++) {
                this.a[i].interpolatePathDataNode(pathDataNodeArr3[i], pathDataNodeArr4[i], f);
            }
            return this.a;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static PropertyValuesHolder a(TypedArray typedArray, int i, int i2, int i3, String str) {
        PropertyValuesHolder propertyValuesHolderOfInt;
        PropertyValuesHolder propertyValuesHolderOfObject;
        TypedValue typedValuePeekValue = typedArray.peekValue(i2);
        byte b2 = typedValuePeekValue != null;
        int i4 = b2 != false ? typedValuePeekValue.type : 0;
        TypedValue typedValuePeekValue2 = typedArray.peekValue(i3);
        byte b3 = typedValuePeekValue2 != null;
        int i5 = b3 != false ? typedValuePeekValue2.type : 0;
        if (i == 4) {
            i = ((b2 == true && a(i4)) || (b3 == true && a(i5))) ? 3 : 0;
        }
        byte b4 = i == 0;
        PropertyValuesHolder propertyValuesHolder = null;
        byte b5 = 0;
        byte b6 = 0;
        if (i != 2) {
            ArgbEvaluator argbEvaluator = i == 3 ? ArgbEvaluator.getInstance() : null;
            if (b4 == true) {
                if (b2 == true) {
                    float dimension = i4 == 5 ? typedArray.getDimension(i2, 0.0f) : typedArray.getFloat(i2, 0.0f);
                    if (b3 == true) {
                        propertyValuesHolderOfInt = PropertyValuesHolder.ofFloat(str, dimension, i5 == 5 ? typedArray.getDimension(i3, 0.0f) : typedArray.getFloat(i3, 0.0f));
                    } else {
                        propertyValuesHolderOfInt = PropertyValuesHolder.ofFloat(str, dimension);
                    }
                } else {
                    propertyValuesHolderOfInt = PropertyValuesHolder.ofFloat(str, i5 == 5 ? typedArray.getDimension(i3, 0.0f) : typedArray.getFloat(i3, 0.0f));
                }
            } else {
                if (b2 != true) {
                    if (b3 != false) {
                        propertyValuesHolderOfInt = PropertyValuesHolder.ofInt(str, i5 == 5 ? (int) typedArray.getDimension(i3, 0.0f) : a(i5) ? typedArray.getColor(i3, 0) : typedArray.getInt(i3, 0));
                    }
                    if (propertyValuesHolder == null && argbEvaluator != null) {
                        propertyValuesHolder.setEvaluator(argbEvaluator);
                        return propertyValuesHolder;
                    }
                }
                int dimension2 = i4 == 5 ? (int) typedArray.getDimension(i2, 0.0f) : a(i4) ? typedArray.getColor(i2, 0) : typedArray.getInt(i2, 0);
                if (b3 == true) {
                    propertyValuesHolderOfInt = PropertyValuesHolder.ofInt(str, dimension2, i5 == 5 ? (int) typedArray.getDimension(i3, 0.0f) : a(i5) ? typedArray.getColor(i3, 0) : typedArray.getInt(i3, 0));
                } else {
                    propertyValuesHolderOfInt = PropertyValuesHolder.ofInt(str, dimension2);
                }
            }
            propertyValuesHolder = propertyValuesHolderOfInt;
            return propertyValuesHolder == null ? propertyValuesHolder : propertyValuesHolder;
        }
        String string = typedArray.getString(i2);
        String string2 = typedArray.getString(i3);
        PathParser.PathDataNode[] pathDataNodeArrCreateNodesFromPathData = PathParser.createNodesFromPathData(string);
        PathParser.PathDataNode[] pathDataNodeArrCreateNodesFromPathData2 = PathParser.createNodesFromPathData(string2);
        if (pathDataNodeArrCreateNodesFromPathData == null && pathDataNodeArrCreateNodesFromPathData2 == null) {
            return null;
        }
        if (pathDataNodeArrCreateNodesFromPathData == null) {
            if (pathDataNodeArrCreateNodesFromPathData2 != null) {
                return PropertyValuesHolder.ofObject(str, new b(b5 == true ? 1 : 0), pathDataNodeArrCreateNodesFromPathData2);
            }
            return null;
        }
        b bVar = new b(b6 == true ? 1 : 0);
        if (pathDataNodeArrCreateNodesFromPathData2 == null) {
            propertyValuesHolderOfObject = PropertyValuesHolder.ofObject(str, bVar, pathDataNodeArrCreateNodesFromPathData);
        } else {
            if (!PathParser.canMorph(pathDataNodeArrCreateNodesFromPathData, pathDataNodeArrCreateNodesFromPathData2)) {
                throw new InflateException(" Can't morph from " + string + " to " + string2);
            }
            propertyValuesHolderOfObject = PropertyValuesHolder.ofObject(str, bVar, pathDataNodeArrCreateNodesFromPathData, pathDataNodeArrCreateNodesFromPathData2);
        }
        return propertyValuesHolderOfObject;
    }

    public static boolean a(int i) {
        return i >= 28 && i <= 31;
    }

    public static Animator loadAnimator(Context context, @AnimatorRes int i) throws Resources.NotFoundException {
        return Build.VERSION.SDK_INT >= 24 ? AnimatorInflater.loadAnimator(context, i) : loadAnimator(context, context.getResources(), context.getTheme(), i);
    }

    public static Animator loadAnimator(Context context, Resources resources, Resources.Theme theme, @AnimatorRes int i) throws Resources.NotFoundException {
        return loadAnimator(context, resources, theme, i, 1.0f);
    }

    public static Animator loadAnimator(Context context, Resources resources, Resources.Theme theme, @AnimatorRes int i, float f) throws Resources.NotFoundException {
        XmlResourceParser animation = null;
        try {
            try {
                animation = resources.getAnimation(i);
                return a(context, resources, theme, animation, Xml.asAttributeSet(animation), null, 0, f);
            } catch (IOException e) {
                Resources.NotFoundException notFoundException = new Resources.NotFoundException("Can't load animation resource ID #0x" + Integer.toHexString(i));
                notFoundException.initCause(e);
                throw notFoundException;
            } catch (XmlPullParserException e2) {
                Resources.NotFoundException notFoundException2 = new Resources.NotFoundException("Can't load animation resource ID #0x" + Integer.toHexString(i));
                notFoundException2.initCause(e2);
                throw notFoundException2;
            }
        } finally {
            if (animation != null) {
                animation.close();
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:157:0x0326, code lost:
    
        if (r27 == null) goto L166;
     */
    /* JADX WARN: Code restructure failed: missing block: B:158:0x0328, code lost:
    
        if (r13 == null) goto L166;
     */
    /* JADX WARN: Code restructure failed: missing block: B:159:0x032a, code lost:
    
        r1 = new android.animation.Animator[r13.size()];
        r2 = r13.iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:161:0x0338, code lost:
    
        if (r2.hasNext() == false) goto L174;
     */
    /* JADX WARN: Code restructure failed: missing block: B:162:0x033a, code lost:
    
        r1[r14] = (android.animation.Animator) r2.next();
        r14 = r14 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:163:0x0346, code lost:
    
        if (r28 != 0) goto L165;
     */
    /* JADX WARN: Code restructure failed: missing block: B:164:0x0348, code lost:
    
        r27.playTogether(r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:165:0x034c, code lost:
    
        r27.playSequentially(r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:166:0x034f, code lost:
    
        return r0;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:152:0x02ff  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.animation.Animator a(android.content.Context r22, android.content.res.Resources r23, android.content.res.Resources.Theme r24, org.xmlpull.v1.XmlPullParser r25, android.util.AttributeSet r26, android.animation.AnimatorSet r27, int r28, float r29) throws org.xmlpull.v1.XmlPullParserException, android.content.res.Resources.NotFoundException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 848
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.graphics.drawable.AnimatorInflaterCompat.a(android.content.Context, android.content.res.Resources, android.content.res.Resources$Theme, org.xmlpull.v1.XmlPullParser, android.util.AttributeSet, android.animation.AnimatorSet, int, float):android.animation.Animator");
    }

    public static Keyframe a(Keyframe keyframe, float f) {
        if (keyframe.getType() == Float.TYPE) {
            return Keyframe.ofFloat(f);
        }
        if (keyframe.getType() == Integer.TYPE) {
            return Keyframe.ofInt(f);
        }
        return Keyframe.ofObject(f);
    }

    public static ValueAnimator a(Context context, Resources resources, Resources.Theme theme, AttributeSet attributeSet, ValueAnimator valueAnimator, float f, XmlPullParser xmlPullParser) throws Resources.NotFoundException {
        ValueAnimator valueAnimator2;
        int i;
        ValueAnimator valueAnimator3;
        TypedArray typedArrayObtainAttributes = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, s2.g);
        TypedArray typedArrayObtainAttributes2 = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, s2.k);
        ValueAnimator valueAnimator4 = valueAnimator == null ? new ValueAnimator() : valueAnimator;
        long namedInt = TypedArrayUtils.getNamedInt(typedArrayObtainAttributes, xmlPullParser, "duration", 1, SoundLength.SMALL_THRESHOLD);
        int i2 = 0;
        long namedInt2 = TypedArrayUtils.getNamedInt(typedArrayObtainAttributes, xmlPullParser, "startOffset", 2, 0);
        int namedInt3 = TypedArrayUtils.getNamedInt(typedArrayObtainAttributes, xmlPullParser, "valueType", 7, 4);
        if (TypedArrayUtils.hasAttribute(xmlPullParser, "valueFrom") && TypedArrayUtils.hasAttribute(xmlPullParser, "valueTo")) {
            if (namedInt3 == 4) {
                TypedValue typedValuePeekValue = typedArrayObtainAttributes.peekValue(5);
                boolean z = typedValuePeekValue != null;
                int i3 = z ? typedValuePeekValue.type : 0;
                TypedValue typedValuePeekValue2 = typedArrayObtainAttributes.peekValue(6);
                boolean z2 = typedValuePeekValue2 != null;
                namedInt3 = ((z && a(i3)) || (z2 && a(z2 ? typedValuePeekValue2.type : 0))) ? 3 : 0;
            }
            PropertyValuesHolder propertyValuesHolderA = a(typedArrayObtainAttributes, namedInt3, 5, 6, "");
            if (propertyValuesHolderA != null) {
                valueAnimator4.setValues(propertyValuesHolderA);
            }
        }
        valueAnimator4.setDuration(namedInt);
        valueAnimator4.setStartDelay(namedInt2);
        valueAnimator4.setRepeatCount(TypedArrayUtils.getNamedInt(typedArrayObtainAttributes, xmlPullParser, "repeatCount", 3, 0));
        valueAnimator4.setRepeatMode(TypedArrayUtils.getNamedInt(typedArrayObtainAttributes, xmlPullParser, "repeatMode", 4, 1));
        if (typedArrayObtainAttributes2 != null) {
            ObjectAnimator objectAnimator = (ObjectAnimator) valueAnimator4;
            String namedString = TypedArrayUtils.getNamedString(typedArrayObtainAttributes2, xmlPullParser, "pathData", 1);
            if (namedString != null) {
                String namedString2 = TypedArrayUtils.getNamedString(typedArrayObtainAttributes2, xmlPullParser, "propertyXName", 2);
                String namedString3 = TypedArrayUtils.getNamedString(typedArrayObtainAttributes2, xmlPullParser, "propertyYName", 3);
                if (namedString2 == null && namedString3 == null) {
                    throw new InflateException(typedArrayObtainAttributes2.getPositionDescription() + " propertyXName or propertyYName is needed for PathData");
                }
                Path pathCreatePathFromPathData = PathParser.createPathFromPathData(namedString);
                float f2 = 0.5f * f;
                PathMeasure pathMeasure = new PathMeasure(pathCreatePathFromPathData, false);
                ArrayList arrayList = new ArrayList();
                arrayList.add(Float.valueOf(0.0f));
                float length = 0.0f;
                do {
                    length += pathMeasure.getLength();
                    arrayList.add(Float.valueOf(length));
                } while (pathMeasure.nextContour());
                PathMeasure pathMeasure2 = new PathMeasure(pathCreatePathFromPathData, false);
                int iMin = Math.min(100, ((int) (length / f2)) + 1);
                float[] fArr = new float[iMin];
                float[] fArr2 = new float[iMin];
                float[] fArr3 = new float[2];
                float f3 = length / (iMin - 1);
                valueAnimator2 = valueAnimator4;
                int i4 = 0;
                float fFloatValue = 0.0f;
                while (true) {
                    if (i2 >= iMin) {
                        break;
                    }
                    pathMeasure2.getPosTan(fFloatValue, fArr3, null);
                    fArr[i2] = fArr3[0];
                    fArr2[i2] = fArr3[1];
                    fFloatValue += f3;
                    int i5 = i4 + 1;
                    float[] fArr4 = fArr3;
                    if (i5 < arrayList.size() && fFloatValue > ((Float) arrayList.get(i5)).floatValue()) {
                        fFloatValue -= ((Float) arrayList.get(i5)).floatValue();
                        pathMeasure2.nextContour();
                        i4 = i5;
                    }
                    i2++;
                    fArr3 = fArr4;
                }
                PropertyValuesHolder propertyValuesHolderOfFloat = namedString2 != null ? PropertyValuesHolder.ofFloat(namedString2, fArr) : null;
                PropertyValuesHolder propertyValuesHolderOfFloat2 = namedString3 != null ? PropertyValuesHolder.ofFloat(namedString3, fArr2) : null;
                if (propertyValuesHolderOfFloat == null) {
                    i = 0;
                    objectAnimator.setValues(propertyValuesHolderOfFloat2);
                } else {
                    i = 0;
                    if (propertyValuesHolderOfFloat2 == null) {
                        objectAnimator.setValues(propertyValuesHolderOfFloat);
                    } else {
                        objectAnimator.setValues(propertyValuesHolderOfFloat, propertyValuesHolderOfFloat2);
                    }
                }
            } else {
                valueAnimator2 = valueAnimator4;
                i = 0;
                objectAnimator.setPropertyName(TypedArrayUtils.getNamedString(typedArrayObtainAttributes2, xmlPullParser, "propertyName", 0));
            }
        } else {
            valueAnimator2 = valueAnimator4;
            i = 0;
        }
        int namedResourceId = TypedArrayUtils.getNamedResourceId(typedArrayObtainAttributes, xmlPullParser, "interpolator", i, i);
        if (namedResourceId > 0) {
            valueAnimator3 = valueAnimator2;
            valueAnimator3.setInterpolator(AnimationUtilsCompat.loadInterpolator(context, namedResourceId));
        } else {
            valueAnimator3 = valueAnimator2;
        }
        typedArrayObtainAttributes.recycle();
        if (typedArrayObtainAttributes2 != null) {
            typedArrayObtainAttributes2.recycle();
        }
        return valueAnimator3;
    }
}
