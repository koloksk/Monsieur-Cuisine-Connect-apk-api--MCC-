package com.google.zxing.oned.rss;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.BitArray;
import defpackage.db;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public final class RSS14Reader extends AbstractRSSReader {
    public static final int[] i = {1, 10, 34, 70, 126};
    public static final int[] j = {4, 20, 48, 81};
    public static final int[] k = {0, 161, 961, 2015, 2715};
    public static final int[] l = {0, 336, 1036, 1516};
    public static final int[] m = {8, 6, 4, 3, 1};
    public static final int[] n = {2, 4, 6, 8};
    public static final int[][] o = {new int[]{3, 8, 2, 1}, new int[]{3, 5, 5, 1}, new int[]{3, 3, 7, 1}, new int[]{3, 1, 9, 1}, new int[]{2, 7, 4, 1}, new int[]{2, 5, 6, 1}, new int[]{2, 3, 8, 1}, new int[]{1, 5, 7, 1}, new int[]{1, 3, 9, 1}};
    public final List<db> g = new ArrayList();
    public final List<db> h = new ArrayList();

    public static void a(Collection<db> collection, db dbVar) {
        if (dbVar == null) {
            return;
        }
        boolean z = false;
        Iterator<db> it = collection.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            db next = it.next();
            if (next.getValue() == dbVar.getValue()) {
                next.d++;
                z = true;
                break;
            }
        }
        if (z) {
            return;
        }
        collection.add(dbVar);
    }

    @Override // com.google.zxing.oned.OneDReader
    public Result decodeRow(int i2, BitArray bitArray, Map<DecodeHintType, ?> map) throws NotFoundException {
        a(this.g, a(bitArray, false, i2, map));
        bitArray.reverse();
        a(this.h, a(bitArray, true, i2, map));
        bitArray.reverse();
        for (db dbVar : this.g) {
            if (dbVar.d > 1) {
                for (db dbVar2 : this.h) {
                    if (dbVar2.d > 1) {
                        int checksumPortion = ((dbVar2.getChecksumPortion() * 16) + dbVar.getChecksumPortion()) % 79;
                        int value = dbVar2.c.getValue() + (dbVar.c.getValue() * 9);
                        if (value > 72) {
                            value--;
                        }
                        if (value > 8) {
                            value--;
                        }
                        if (checksumPortion == value) {
                            String strValueOf = String.valueOf((dbVar.getValue() * 4537077) + dbVar2.getValue());
                            StringBuilder sb = new StringBuilder(14);
                            for (int length = 13 - strValueOf.length(); length > 0; length--) {
                                sb.append('0');
                            }
                            sb.append(strValueOf);
                            int i3 = 0;
                            for (int i4 = 0; i4 < 13; i4++) {
                                int iCharAt = sb.charAt(i4) - '0';
                                if ((i4 & 1) == 0) {
                                    iCharAt *= 3;
                                }
                                i3 += iCharAt;
                            }
                            int i5 = 10 - (i3 % 10);
                            if (i5 == 10) {
                                i5 = 0;
                            }
                            sb.append(i5);
                            ResultPoint[] resultPoints = dbVar.c.getResultPoints();
                            ResultPoint[] resultPoints2 = dbVar2.c.getResultPoints();
                            return new Result(String.valueOf(sb.toString()), null, new ResultPoint[]{resultPoints[0], resultPoints[1], resultPoints2[0], resultPoints2[1]}, BarcodeFormat.RSS_14);
                        }
                    }
                }
            }
        }
        throw NotFoundException.getNotFoundInstance();
    }

    @Override // com.google.zxing.oned.OneDReader, com.google.zxing.Reader
    public void reset() {
        this.g.clear();
        this.h.clear();
    }

    public final db a(BitArray bitArray, boolean z, int i2, Map<DecodeHintType, ?> map) {
        try {
            FinderPattern finderPatternA = a(bitArray, i2, z, a(bitArray, 0, z));
            ResultPointCallback resultPointCallback = map == null ? null : (ResultPointCallback) map.get(DecodeHintType.NEED_RESULT_POINT_CALLBACK);
            if (resultPointCallback != null) {
                float size = (r2[0] + r2[1]) / 2.0f;
                if (z) {
                    size = (bitArray.getSize() - 1) - size;
                }
                resultPointCallback.foundPossibleResultPoint(new ResultPoint(size, i2));
            }
            DataCharacter dataCharacterA = a(bitArray, finderPatternA, true);
            DataCharacter dataCharacterA2 = a(bitArray, finderPatternA, false);
            return new db((dataCharacterA.getValue() * 1597) + dataCharacterA2.getValue(), (dataCharacterA2.getChecksumPortion() * 4) + dataCharacterA.getChecksumPortion(), finderPatternA);
        } catch (NotFoundException unused) {
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:46:0x00cf A[PHI: r8 r12
  0x00cf: PHI (r8v7 boolean) = (r8v4 boolean), (r8v19 boolean) binds: [B:45:0x00cd, B:34:0x00b8] A[DONT_GENERATE, DONT_INLINE]
  0x00cf: PHI (r12v6 boolean) = (r12v3 boolean), (r12v14 boolean) binds: [B:45:0x00cd, B:34:0x00b8] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00d2 A[PHI: r8 r12
  0x00d2: PHI (r8v15 boolean) = (r8v4 boolean), (r8v19 boolean) binds: [B:45:0x00cd, B:34:0x00b8] A[DONT_GENERATE, DONT_INLINE]
  0x00d2: PHI (r12v12 boolean) = (r12v3 boolean), (r12v14 boolean) binds: [B:45:0x00cd, B:34:0x00b8] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.google.zxing.oned.rss.DataCharacter a(com.google.zxing.common.BitArray r19, com.google.zxing.oned.rss.FinderPattern r20, boolean r21) throws com.google.zxing.NotFoundException {
        /*
            Method dump skipped, instructions count: 511
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.oned.rss.RSS14Reader.a(com.google.zxing.common.BitArray, com.google.zxing.oned.rss.FinderPattern, boolean):com.google.zxing.oned.rss.DataCharacter");
    }

    public final int[] a(BitArray bitArray, int i2, boolean z) throws NotFoundException {
        int[] decodeFinderCounters = getDecodeFinderCounters();
        decodeFinderCounters[0] = 0;
        decodeFinderCounters[1] = 0;
        decodeFinderCounters[2] = 0;
        decodeFinderCounters[3] = 0;
        int size = bitArray.getSize();
        boolean z2 = false;
        while (i2 < size) {
            z2 = !bitArray.get(i2);
            if (z == z2) {
                break;
            }
            i2++;
        }
        int i3 = i2;
        int i4 = 0;
        while (i2 < size) {
            if (bitArray.get(i2) ^ z2) {
                decodeFinderCounters[i4] = decodeFinderCounters[i4] + 1;
            } else {
                if (i4 != 3) {
                    i4++;
                } else {
                    if (AbstractRSSReader.isFinderPattern(decodeFinderCounters)) {
                        return new int[]{i3, i2};
                    }
                    i3 += decodeFinderCounters[0] + decodeFinderCounters[1];
                    decodeFinderCounters[0] = decodeFinderCounters[2];
                    decodeFinderCounters[1] = decodeFinderCounters[3];
                    decodeFinderCounters[2] = 0;
                    decodeFinderCounters[3] = 0;
                    i4--;
                }
                decodeFinderCounters[i4] = 1;
                z2 = !z2;
            }
            i2++;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    public final FinderPattern a(BitArray bitArray, int i2, boolean z, int[] iArr) throws NotFoundException {
        int size;
        int i3;
        boolean z2 = bitArray.get(iArr[0]);
        int i4 = iArr[0] - 1;
        while (i4 >= 0 && (bitArray.get(i4) ^ z2)) {
            i4--;
        }
        int i5 = i4 + 1;
        int i6 = iArr[0] - i5;
        int[] decodeFinderCounters = getDecodeFinderCounters();
        System.arraycopy(decodeFinderCounters, 0, decodeFinderCounters, 1, decodeFinderCounters.length - 1);
        decodeFinderCounters[0] = i6;
        int finderValue = AbstractRSSReader.parseFinderValue(decodeFinderCounters, o);
        int i7 = iArr[1];
        if (z) {
            int size2 = (bitArray.getSize() - 1) - i5;
            size = (bitArray.getSize() - 1) - i7;
            i3 = size2;
        } else {
            size = i7;
            i3 = i5;
        }
        return new FinderPattern(finderValue, new int[]{i5, iArr[1]}, i3, size, i2);
    }
}
