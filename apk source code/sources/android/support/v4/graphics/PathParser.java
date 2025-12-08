package android.support.v4.graphics;

import android.graphics.Path;
import android.support.annotation.RestrictTo;
import android.util.Log;
import defpackage.g9;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class PathParser {
    public static float[] a(float[] fArr, int i, int i2) {
        if (i > i2) {
            throw new IllegalArgumentException();
        }
        int length = fArr.length;
        if (i < 0 || i > length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int i3 = i2 - i;
        int iMin = Math.min(i3, length - i);
        float[] fArr2 = new float[i3];
        System.arraycopy(fArr, i, fArr2, 0, iMin);
        return fArr2;
    }

    public static boolean canMorph(PathDataNode[] pathDataNodeArr, PathDataNode[] pathDataNodeArr2) {
        if (pathDataNodeArr == null || pathDataNodeArr2 == null || pathDataNodeArr.length != pathDataNodeArr2.length) {
            return false;
        }
        for (int i = 0; i < pathDataNodeArr.length; i++) {
            if (pathDataNodeArr[i].mType != pathDataNodeArr2[i].mType || pathDataNodeArr[i].mParams.length != pathDataNodeArr2[i].mParams.length) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:41:0x008a, code lost:
    
        if (r13 == 0) goto L42;
     */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0047  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0097 A[Catch: NumberFormatException -> 0x00bc, LOOP:3: B:29:0x006c->B:49:0x0097, LOOP_END, TryCatch #0 {NumberFormatException -> 0x00bc, blocks: (B:26:0x0059, B:29:0x006c, B:31:0x0072, B:36:0x0080, B:49:0x0097, B:51:0x009c, B:54:0x00ac, B:56:0x00b1), top: B:71:0x0059 }] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x009c A[Catch: NumberFormatException -> 0x00bc, TryCatch #0 {NumberFormatException -> 0x00bc, blocks: (B:26:0x0059, B:29:0x006c, B:31:0x0072, B:36:0x0080, B:49:0x0097, B:51:0x009c, B:54:0x00ac, B:56:0x00b1), top: B:71:0x0059 }] */
    /* JADX WARN: Removed duplicated region for block: B:53:0x00ab  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00ac A[Catch: NumberFormatException -> 0x00bc, TryCatch #0 {NumberFormatException -> 0x00bc, blocks: (B:26:0x0059, B:29:0x006c, B:31:0x0072, B:36:0x0080, B:49:0x0097, B:51:0x009c, B:54:0x00ac, B:56:0x00b1), top: B:71:0x0059 }] */
    /* JADX WARN: Removed duplicated region for block: B:75:0x00d9 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0096 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.support.v4.graphics.PathParser.PathDataNode[] createNodesFromPathData(java.lang.String r17) {
        /*
            Method dump skipped, instructions count: 276
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.graphics.PathParser.createNodesFromPathData(java.lang.String):android.support.v4.graphics.PathParser$PathDataNode[]");
    }

    public static Path createPathFromPathData(String str) {
        Path path = new Path();
        PathDataNode[] pathDataNodeArrCreateNodesFromPathData = createNodesFromPathData(str);
        if (pathDataNodeArrCreateNodesFromPathData == null) {
            return null;
        }
        try {
            PathDataNode.nodesToPath(pathDataNodeArrCreateNodesFromPathData, path);
            return path;
        } catch (RuntimeException e) {
            throw new RuntimeException(g9.b("Error in parsing ", str), e);
        }
    }

    public static PathDataNode[] deepCopyNodes(PathDataNode[] pathDataNodeArr) {
        if (pathDataNodeArr == null) {
            return null;
        }
        PathDataNode[] pathDataNodeArr2 = new PathDataNode[pathDataNodeArr.length];
        for (int i = 0; i < pathDataNodeArr.length; i++) {
            pathDataNodeArr2[i] = new PathDataNode(pathDataNodeArr[i]);
        }
        return pathDataNodeArr2;
    }

    public static void updateNodes(PathDataNode[] pathDataNodeArr, PathDataNode[] pathDataNodeArr2) {
        for (int i = 0; i < pathDataNodeArr2.length; i++) {
            pathDataNodeArr[i].mType = pathDataNodeArr2[i].mType;
            for (int i2 = 0; i2 < pathDataNodeArr2[i].mParams.length; i2++) {
                pathDataNodeArr[i].mParams[i2] = pathDataNodeArr2[i].mParams[i2];
            }
        }
    }

    public static class PathDataNode {

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public float[] mParams;

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public char mType;

        public PathDataNode(char c, float[] fArr) {
            this.mType = c;
            this.mParams = fArr;
        }

        public static void a(Path path, float f, float f2, float f3, float f4, float f5, float f6, float f7, boolean z, boolean z2) {
            double d;
            double d2;
            double radians = Math.toRadians(f7);
            double dCos = Math.cos(radians);
            double dSin = Math.sin(radians);
            double d3 = f;
            double d4 = f2;
            double d5 = (d4 * dSin) + (d3 * dCos);
            double d6 = d3;
            double d7 = f5;
            double d8 = d5 / d7;
            double d9 = f6;
            double d10 = ((d4 * dCos) + ((-f) * dSin)) / d9;
            double d11 = d4;
            double d12 = f4;
            double d13 = ((d12 * dSin) + (f3 * dCos)) / d7;
            double d14 = ((d12 * dCos) + ((-f3) * dSin)) / d9;
            double d15 = d8 - d13;
            double d16 = d10 - d14;
            double d17 = (d8 + d13) / 2.0d;
            double d18 = (d10 + d14) / 2.0d;
            double d19 = (d16 * d16) + (d15 * d15);
            if (d19 == 0.0d) {
                Log.w("PathParser", " Points are coincident");
                return;
            }
            double d20 = (1.0d / d19) - 0.25d;
            if (d20 < 0.0d) {
                Log.w("PathParser", "Points are too far apart " + d19);
                float fSqrt = (float) (Math.sqrt(d19) / 1.99999d);
                a(path, f, f2, f3, f4, f5 * fSqrt, f6 * fSqrt, f7, z, z2);
                return;
            }
            double dSqrt = Math.sqrt(d20);
            double d21 = d15 * dSqrt;
            double d22 = dSqrt * d16;
            if (z == z2) {
                d = d17 - d22;
                d2 = d18 + d21;
            } else {
                d = d17 + d22;
                d2 = d18 - d21;
            }
            double dAtan2 = Math.atan2(d10 - d2, d8 - d);
            double dAtan22 = Math.atan2(d14 - d2, d13 - d) - dAtan2;
            if (z2 != (dAtan22 >= 0.0d)) {
                dAtan22 = dAtan22 > 0.0d ? dAtan22 - 6.283185307179586d : dAtan22 + 6.283185307179586d;
            }
            double d23 = d * d7;
            double d24 = d2 * d9;
            double d25 = (d23 * dCos) - (d24 * dSin);
            double d26 = (d24 * dCos) + (d23 * dSin);
            int iCeil = (int) Math.ceil(Math.abs((dAtan22 * 4.0d) / 3.141592653589793d));
            double dCos2 = Math.cos(radians);
            double dSin2 = Math.sin(radians);
            double dCos3 = Math.cos(dAtan2);
            double dSin3 = Math.sin(dAtan2);
            double d27 = -d7;
            double d28 = d27 * dCos2;
            double d29 = d9 * dSin2;
            double d30 = (d28 * dSin3) - (d29 * dCos3);
            double d31 = d27 * dSin2;
            double d32 = d9 * dCos2;
            double d33 = (dCos3 * d32) + (dSin3 * d31);
            double d34 = dAtan22 / iCeil;
            int i = 0;
            while (i < iCeil) {
                double d35 = dAtan2 + d34;
                double dSin4 = Math.sin(d35);
                double dCos4 = Math.cos(d35);
                double d36 = d34;
                double d37 = (((d7 * dCos2) * dCos4) + d25) - (d29 * dSin4);
                double d38 = d25;
                double d39 = (d32 * dSin4) + (d7 * dSin2 * dCos4) + d26;
                double d40 = (d28 * dSin4) - (d29 * dCos4);
                double d41 = (dCos4 * d32) + (dSin4 * d31);
                double d42 = d35 - dAtan2;
                double dTan = Math.tan(d42 / 2.0d);
                double dSqrt2 = ((Math.sqrt(((dTan * 3.0d) * dTan) + 4.0d) - 1.0d) * Math.sin(d42)) / 3.0d;
                path.rLineTo(0.0f, 0.0f);
                path.cubicTo((float) ((d30 * dSqrt2) + d6), (float) ((d33 * dSqrt2) + d11), (float) (d37 - (dSqrt2 * d40)), (float) (d39 - (dSqrt2 * d41)), (float) d37, (float) d39);
                i++;
                dAtan2 = d35;
                dCos2 = dCos2;
                d31 = d31;
                d33 = d41;
                iCeil = iCeil;
                d7 = d7;
                d30 = d40;
                d6 = d37;
                d11 = d39;
                d34 = d36;
                d25 = d38;
            }
        }

        public static void nodesToPath(PathDataNode[] pathDataNodeArr, Path path) {
            int i;
            int i2;
            float[] fArr;
            char c;
            int i3;
            float f;
            float f2;
            float f3;
            float f4;
            float f5;
            float f6;
            float f7;
            float f8;
            float f9;
            float f10;
            float f11;
            float f12;
            float f13;
            float f14;
            float f15;
            float f16;
            float f17;
            float f18;
            float f19;
            PathDataNode[] pathDataNodeArr2 = pathDataNodeArr;
            int i4 = 6;
            float[] fArr2 = new float[6];
            char c2 = 'm';
            int i5 = 0;
            char c3 = 'm';
            int i6 = 0;
            while (i6 < pathDataNodeArr2.length) {
                char c4 = pathDataNodeArr2[i6].mType;
                float[] fArr3 = pathDataNodeArr2[i6].mParams;
                float f20 = fArr2[i5];
                float f21 = fArr2[1];
                float f22 = fArr2[2];
                float f23 = fArr2[3];
                float f24 = fArr2[4];
                float f25 = fArr2[5];
                switch (c4) {
                    case 'A':
                    case 'a':
                        i = 7;
                        break;
                    case 'C':
                    case 'c':
                        i = i4;
                        break;
                    case 'H':
                    case 'V':
                    case 'h':
                    case 'v':
                        i = 1;
                        break;
                    case 'Q':
                    case 'S':
                    case 'q':
                    case 's':
                        i = 4;
                        break;
                    case 'Z':
                    case 'z':
                        path.close();
                        path.moveTo(f24, f25);
                        f20 = f24;
                        f22 = f20;
                        f21 = f25;
                        f23 = f21;
                    default:
                        i = 2;
                        break;
                }
                float f26 = f24;
                float f27 = f25;
                float f28 = f20;
                float f29 = f21;
                int i7 = i5;
                while (i7 < fArr3.length) {
                    if (c4 != 'A') {
                        if (c4 != 'C') {
                            if (c4 == 'H') {
                                i2 = i7;
                                fArr = fArr3;
                                c = c4;
                                i3 = i6;
                                int i8 = i2 + 0;
                                path.lineTo(fArr[i8], f29);
                                f28 = fArr[i8];
                            } else if (c4 == 'Q') {
                                i2 = i7;
                                fArr = fArr3;
                                c = c4;
                                i3 = i6;
                                int i9 = i2 + 0;
                                int i10 = i2 + 1;
                                int i11 = i2 + 2;
                                int i12 = i2 + 3;
                                path.quadTo(fArr[i9], fArr[i10], fArr[i11], fArr[i12]);
                                f = fArr[i9];
                                f2 = fArr[i10];
                                f28 = fArr[i11];
                                f29 = fArr[i12];
                            } else if (c4 == 'V') {
                                i2 = i7;
                                fArr = fArr3;
                                c = c4;
                                i3 = i6;
                                int i13 = i2 + 0;
                                path.lineTo(f28, fArr[i13]);
                                f29 = fArr[i13];
                            } else if (c4 != 'a') {
                                if (c4 != 'c') {
                                    if (c4 == 'h') {
                                        i2 = i7;
                                        int i14 = i2 + 0;
                                        path.rLineTo(fArr3[i14], 0.0f);
                                        f28 += fArr3[i14];
                                    } else if (c4 != 'q') {
                                        if (c4 == 'v') {
                                            i2 = i7;
                                            f10 = f29;
                                            int i15 = i2 + 0;
                                            path.rLineTo(0.0f, fArr3[i15]);
                                            f11 = fArr3[i15];
                                        } else if (c4 == 'L') {
                                            i2 = i7;
                                            int i16 = i2 + 0;
                                            int i17 = i2 + 1;
                                            path.lineTo(fArr3[i16], fArr3[i17]);
                                            f28 = fArr3[i16];
                                            f29 = fArr3[i17];
                                        } else if (c4 == 'M') {
                                            i2 = i7;
                                            int i18 = i2 + 0;
                                            float f30 = fArr3[i18];
                                            int i19 = i2 + 1;
                                            float f31 = fArr3[i19];
                                            if (i2 > 0) {
                                                path.lineTo(fArr3[i18], fArr3[i19]);
                                                f28 = f30;
                                                f29 = f31;
                                            } else {
                                                path.moveTo(fArr3[i18], fArr3[i19]);
                                                f28 = f30;
                                                f29 = f31;
                                                f27 = f29;
                                                f26 = f28;
                                            }
                                        } else if (c4 == 'S') {
                                            i2 = i7;
                                            float f32 = f29;
                                            float f33 = f28;
                                            if (c3 == 'c' || c3 == 's' || c3 == 'C' || c3 == 'S') {
                                                f12 = (f32 * 2.0f) - f23;
                                                f13 = (f33 * 2.0f) - f22;
                                            } else {
                                                f13 = f33;
                                                f12 = f32;
                                            }
                                            int i20 = i2 + 0;
                                            int i21 = i2 + 1;
                                            int i22 = i2 + 2;
                                            int i23 = i2 + 3;
                                            path.cubicTo(f13, f12, fArr3[i20], fArr3[i21], fArr3[i22], fArr3[i23]);
                                            float f34 = fArr3[i20];
                                            float f35 = fArr3[i21];
                                            f9 = fArr3[i22];
                                            f8 = fArr3[i23];
                                            f22 = f34;
                                            f23 = f35;
                                            f28 = f9;
                                            f29 = f8;
                                        } else if (c4 == 'T') {
                                            i2 = i7;
                                            float f36 = f29;
                                            float f37 = f28;
                                            if (c3 == 'q' || c3 == 't' || c3 == 'Q' || c3 == 'T') {
                                                f14 = (f37 * 2.0f) - f22;
                                                f15 = (f36 * 2.0f) - f23;
                                            } else {
                                                f14 = f37;
                                                f15 = f36;
                                            }
                                            int i24 = i2 + 0;
                                            int i25 = i2 + 1;
                                            path.quadTo(f14, f15, fArr3[i24], fArr3[i25]);
                                            f23 = f15;
                                            f22 = f14;
                                            fArr = fArr3;
                                            c = c4;
                                            i3 = i6;
                                            f28 = fArr3[i24];
                                            f29 = fArr3[i25];
                                        } else if (c4 == 'l') {
                                            i2 = i7;
                                            f10 = f29;
                                            int i26 = i2 + 0;
                                            int i27 = i2 + 1;
                                            path.rLineTo(fArr3[i26], fArr3[i27]);
                                            f28 += fArr3[i26];
                                            f11 = fArr3[i27];
                                        } else if (c4 == c2) {
                                            i2 = i7;
                                            int i28 = i2 + 0;
                                            f28 += fArr3[i28];
                                            int i29 = i2 + 1;
                                            f29 += fArr3[i29];
                                            if (i2 > 0) {
                                                path.rLineTo(fArr3[i28], fArr3[i29]);
                                            } else {
                                                path.rMoveTo(fArr3[i28], fArr3[i29]);
                                                f27 = f29;
                                                f26 = f28;
                                            }
                                        } else if (c4 != 's') {
                                            if (c4 == 't') {
                                                if (c3 == 'q' || c3 == 't' || c3 == 'Q' || c3 == 'T') {
                                                    f18 = f28 - f22;
                                                    f19 = f29 - f23;
                                                } else {
                                                    f19 = 0.0f;
                                                    f18 = 0.0f;
                                                }
                                                int i30 = i7 + 0;
                                                int i31 = i7 + 1;
                                                path.rQuadTo(f18, f19, fArr3[i30], fArr3[i31]);
                                                f22 = f18 + f28;
                                                float f38 = f19 + f29;
                                                f28 += fArr3[i30];
                                                f29 += fArr3[i31];
                                                f23 = f38;
                                            }
                                            i2 = i7;
                                        } else {
                                            if (c3 == 'c' || c3 == 's' || c3 == 'C' || c3 == 'S') {
                                                f16 = f29 - f23;
                                                f17 = f28 - f22;
                                            } else {
                                                f17 = 0.0f;
                                                f16 = 0.0f;
                                            }
                                            int i32 = i7 + 0;
                                            int i33 = i7 + 1;
                                            int i34 = i7 + 2;
                                            int i35 = i7 + 3;
                                            i2 = i7;
                                            f3 = f29;
                                            float f39 = f28;
                                            path.rCubicTo(f17, f16, fArr3[i32], fArr3[i33], fArr3[i34], fArr3[i35]);
                                            f4 = fArr3[i32] + f39;
                                            f5 = fArr3[i33] + f3;
                                            f6 = f39 + fArr3[i34];
                                            f7 = fArr3[i35];
                                        }
                                        f29 = f10 + f11;
                                    } else {
                                        i2 = i7;
                                        f3 = f29;
                                        float f40 = f28;
                                        int i36 = i2 + 0;
                                        int i37 = i2 + 1;
                                        int i38 = i2 + 2;
                                        int i39 = i2 + 3;
                                        path.rQuadTo(fArr3[i36], fArr3[i37], fArr3[i38], fArr3[i39]);
                                        f4 = fArr3[i36] + f40;
                                        f5 = fArr3[i37] + f3;
                                        float f41 = f40 + fArr3[i38];
                                        float f42 = fArr3[i39];
                                        f6 = f41;
                                        f7 = f42;
                                    }
                                    fArr = fArr3;
                                    c = c4;
                                    i3 = i6;
                                } else {
                                    i2 = i7;
                                    f3 = f29;
                                    float f43 = f28;
                                    int i40 = i2 + 2;
                                    int i41 = i2 + 3;
                                    int i42 = i2 + 4;
                                    int i43 = i2 + 5;
                                    path.rCubicTo(fArr3[i2 + 0], fArr3[i2 + 1], fArr3[i40], fArr3[i41], fArr3[i42], fArr3[i43]);
                                    f4 = fArr3[i40] + f43;
                                    f5 = fArr3[i41] + f3;
                                    f6 = f43 + fArr3[i42];
                                    f7 = fArr3[i43];
                                }
                                f8 = f3 + f7;
                                f22 = f4;
                                f23 = f5;
                                f9 = f6;
                                f28 = f9;
                                f29 = f8;
                                fArr = fArr3;
                                c = c4;
                                i3 = i6;
                            } else {
                                i2 = i7;
                                float f44 = f29;
                                float f45 = f28;
                                int i44 = i2 + 5;
                                int i45 = i2 + 6;
                                fArr = fArr3;
                                c = c4;
                                i3 = i6;
                                a(path, f45, f44, fArr3[i44] + f45, fArr3[i45] + f44, fArr3[i2 + 0], fArr3[i2 + 1], fArr3[i2 + 2], fArr3[i2 + 3] != 0.0f, fArr3[i2 + 4] != 0.0f);
                                f28 = f45 + fArr[i44];
                                f29 = f44 + fArr[i45];
                            }
                            i7 = i2 + i;
                            c2 = 'm';
                            i5 = 0;
                            c3 = c;
                            c4 = c3;
                            fArr3 = fArr;
                            i6 = i3;
                        } else {
                            i2 = i7;
                            fArr = fArr3;
                            c = c4;
                            i3 = i6;
                            int i46 = i2 + 2;
                            int i47 = i2 + 3;
                            int i48 = i2 + 4;
                            int i49 = i2 + 5;
                            path.cubicTo(fArr[i2 + 0], fArr[i2 + 1], fArr[i46], fArr[i47], fArr[i48], fArr[i49]);
                            f28 = fArr[i48];
                            f29 = fArr[i49];
                            f = fArr[i46];
                            f2 = fArr[i47];
                        }
                        f22 = f;
                        f23 = f2;
                        i7 = i2 + i;
                        c2 = 'm';
                        i5 = 0;
                        c3 = c;
                        c4 = c3;
                        fArr3 = fArr;
                        i6 = i3;
                    } else {
                        i2 = i7;
                        fArr = fArr3;
                        c = c4;
                        i3 = i6;
                        int i50 = i2 + 5;
                        int i51 = i2 + 6;
                        a(path, f28, f29, fArr[i50], fArr[i51], fArr[i2 + 0], fArr[i2 + 1], fArr[i2 + 2], fArr[i2 + 3] != 0.0f, fArr[i2 + 4] != 0.0f);
                        f28 = fArr[i50];
                        f29 = fArr[i51];
                    }
                    f23 = f29;
                    f22 = f28;
                    i7 = i2 + i;
                    c2 = 'm';
                    i5 = 0;
                    c3 = c;
                    c4 = c3;
                    fArr3 = fArr;
                    i6 = i3;
                }
                int i52 = i6;
                int i53 = i5;
                fArr2[i53] = f28;
                fArr2[1] = f29;
                fArr2[2] = f22;
                fArr2[3] = f23;
                fArr2[4] = f26;
                fArr2[5] = f27;
                i6 = i52 + 1;
                i4 = 6;
                c2 = 'm';
                i5 = i53;
                c3 = pathDataNodeArr[i52].mType;
                pathDataNodeArr2 = pathDataNodeArr;
            }
        }

        public void interpolatePathDataNode(PathDataNode pathDataNode, PathDataNode pathDataNode2, float f) {
            int i = 0;
            while (true) {
                float[] fArr = pathDataNode.mParams;
                if (i >= fArr.length) {
                    return;
                }
                this.mParams[i] = (pathDataNode2.mParams[i] * f) + ((1.0f - f) * fArr[i]);
                i++;
            }
        }

        public PathDataNode(PathDataNode pathDataNode) {
            this.mType = pathDataNode.mType;
            float[] fArr = pathDataNode.mParams;
            this.mParams = PathParser.a(fArr, 0, fArr.length);
        }
    }
}
