package com.google.zxing.oned.rss.expanded;

import android.support.v7.widget.helper.ItemTouchHelper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import com.google.zxing.common.detector.MathUtils;
import com.google.zxing.oned.OneDReader;
import com.google.zxing.oned.rss.AbstractRSSReader;
import com.google.zxing.oned.rss.DataCharacter;
import com.google.zxing.oned.rss.FinderPattern;
import com.google.zxing.oned.rss.RSSUtils;
import com.google.zxing.oned.rss.expanded.decoders.AbstractExpandedDecoder;
import com.journeyapps.barcodescanner.ViewfinderView;
import cooking.Limits;
import de.silpion.mc2.BuildConfig;
import defpackage.eb;
import defpackage.fb;
import defpackage.g9;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import model.Presets;

/* loaded from: classes.dex */
public final class RSSExpandedReader extends AbstractRSSReader {
    public static final int[] k = {7, 5, 4, 3, 1};
    public static final int[] l = {4, 20, 52, 104, 204};
    public static final int[] m = {0, 348, 1388, 2948, 3988};
    public static final int[][] n = {new int[]{1, 8, 4, 1}, new int[]{3, 6, 4, 1}, new int[]{3, 4, 6, 1}, new int[]{3, 2, 8, 1}, new int[]{2, 6, 5, 1}, new int[]{2, 2, 9, 1}};
    public static final int[][] o = {new int[]{1, 3, 9, 27, 81, 32, 96, 77}, new int[]{20, 60, 180, 118, 143, 7, 21, 63}, new int[]{189, 145, 13, 39, 117, 140, 209, 205}, new int[]{193, 157, 49, 147, 19, 57, 171, 91}, new int[]{62, 186, 136, 197, 169, 85, 44, 132}, new int[]{185, 133, 188, 142, 4, 12, 36, 108}, new int[]{113, 128, 173, 97, 80, 29, 87, 50}, new int[]{150, 28, 84, 41, 123, 158, 52, 156}, new int[]{46, 138, 203, 187, 139, 206, 196, 166}, new int[]{76, 17, 51, 153, 37, 111, 122, 155}, new int[]{43, 129, 176, 106, 107, 110, 119, 146}, new int[]{16, 48, 144, 10, 30, 90, 59, 177}, new int[]{109, 116, 137, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, 178, 112, 125, 164}, new int[]{70, 210, 208, 202, 184, Limits.MAX_TEMPERATURE, BuildConfig.DefaultRecipeVersion, 115}, new int[]{134, 191, 151, 31, 93, 68, 204, 190}, new int[]{148, 22, 66, 198, 172, 94, 71, 2}, new int[]{6, 18, 54, 162, 64, 192, 154, 40}, new int[]{Presets.KNEADING_MAX_TIME, 149, 25, 75, 14, 42, 126, 167}, new int[]{79, 26, 78, 23, 69, 207, 199, 175}, new int[]{103, 98, 83, 38, 114, 131, 182, 124}, new int[]{161, 61, 183, 127, 170, 88, 53, 159}, new int[]{55, 165, 73, 8, 24, 72, 5, 15}, new int[]{45, 135, 194, ViewfinderView.CURRENT_POINT_OPACITY, 58, 174, 100, 89}};
    public static final int[][] p = {new int[]{0, 0}, new int[]{0, 1, 1}, new int[]{0, 2, 1, 3}, new int[]{0, 4, 1, 3, 2}, new int[]{0, 4, 1, 3, 3, 5}, new int[]{0, 4, 1, 3, 4, 5, 5}, new int[]{0, 0, 1, 1, 2, 2, 3, 3}, new int[]{0, 0, 1, 1, 2, 2, 3, 4, 4}, new int[]{0, 0, 1, 1, 2, 2, 3, 4, 5, 5}, new int[]{0, 0, 1, 1, 2, 3, 3, 4, 4, 5, 5}};
    public final List<eb> g = new ArrayList(11);
    public final List<fb> h = new ArrayList();
    public final int[] i = new int[2];
    public boolean j;

    public static Result a(List<eb> list) throws NotFoundException, FormatException {
        int size = (list.size() << 1) - 1;
        if (list.get(list.size() - 1).b == null) {
            size--;
        }
        BitArray bitArray = new BitArray(size * 12);
        int value = list.get(0).b.getValue();
        int i = 0;
        for (int i2 = 11; i2 >= 0; i2--) {
            if (((1 << i2) & value) != 0) {
                bitArray.set(i);
            }
            i++;
        }
        for (int i3 = 1; i3 < list.size(); i3++) {
            eb ebVar = list.get(i3);
            int value2 = ebVar.a.getValue();
            for (int i4 = 11; i4 >= 0; i4--) {
                if (((1 << i4) & value2) != 0) {
                    bitArray.set(i);
                }
                i++;
            }
            DataCharacter dataCharacter = ebVar.b;
            if (dataCharacter != null) {
                int value3 = dataCharacter.getValue();
                for (int i5 = 11; i5 >= 0; i5--) {
                    if (((1 << i5) & value3) != 0) {
                        bitArray.set(i);
                    }
                    i++;
                }
            }
        }
        String information = AbstractExpandedDecoder.createDecoder(bitArray).parseInformation();
        ResultPoint[] resultPoints = list.get(0).c.getResultPoints();
        ResultPoint[] resultPoints2 = list.get(list.size() - 1).c.getResultPoints();
        return new Result(information, null, new ResultPoint[]{resultPoints[0], resultPoints[1], resultPoints2[0], resultPoints2[1]}, BarcodeFormat.RSS_EXPANDED);
    }

    @Override // com.google.zxing.oned.OneDReader
    public Result decodeRow(int i, BitArray bitArray, Map<DecodeHintType, ?> map) throws NotFoundException, FormatException {
        this.g.clear();
        this.j = false;
        try {
            return a(a(i, bitArray));
        } catch (NotFoundException unused) {
            this.g.clear();
            this.j = true;
            return a(a(i, bitArray));
        }
    }

    @Override // com.google.zxing.oned.OneDReader, com.google.zxing.Reader
    public void reset() {
        this.g.clear();
        this.h.clear();
    }

    public List<eb> a(int i, BitArray bitArray) throws NotFoundException {
        boolean zEquals;
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        while (true) {
            try {
                this.g.add(a(bitArray, this.g, i));
            } catch (NotFoundException e) {
                if (!this.g.isEmpty()) {
                    if (a()) {
                        return this.g;
                    }
                    boolean z6 = !this.h.isEmpty();
                    int i2 = 0;
                    boolean zEquals2 = false;
                    while (true) {
                        if (i2 >= this.h.size()) {
                            zEquals = false;
                            break;
                        }
                        fb fbVar = this.h.get(i2);
                        if (fbVar.b > i) {
                            zEquals = fbVar.a.equals(this.g);
                            break;
                        }
                        zEquals2 = fbVar.a.equals(this.g);
                        i2++;
                    }
                    if (!zEquals && !zEquals2) {
                        List<eb> list = this.g;
                        Iterator<T> it = this.h.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                z = false;
                                break;
                            }
                            fb fbVar2 = (fb) it.next();
                            Iterator<T> it2 = list.iterator();
                            while (true) {
                                if (!it2.hasNext()) {
                                    z4 = true;
                                    break;
                                }
                                eb ebVar = (eb) it2.next();
                                Iterator<eb> it3 = fbVar2.a.iterator();
                                while (true) {
                                    if (!it3.hasNext()) {
                                        z5 = false;
                                        break;
                                    }
                                    if (ebVar.equals(it3.next())) {
                                        z5 = true;
                                        break;
                                    }
                                }
                                if (!z5) {
                                    z4 = false;
                                    break;
                                }
                            }
                            if (z4) {
                                z = true;
                                break;
                            }
                        }
                        if (!z) {
                            this.h.add(i2, new fb(this.g, i, false));
                            List<eb> list2 = this.g;
                            Iterator<fb> it4 = this.h.iterator();
                            while (it4.hasNext()) {
                                fb next = it4.next();
                                if (next.a.size() != list2.size()) {
                                    Iterator<eb> it5 = next.a.iterator();
                                    while (true) {
                                        if (!it5.hasNext()) {
                                            z2 = true;
                                            break;
                                        }
                                        eb next2 = it5.next();
                                        Iterator<eb> it6 = list2.iterator();
                                        while (true) {
                                            if (!it6.hasNext()) {
                                                z3 = false;
                                                break;
                                            }
                                            if (next2.equals(it6.next())) {
                                                z3 = true;
                                                break;
                                            }
                                        }
                                        if (!z3) {
                                            z2 = false;
                                            break;
                                        }
                                    }
                                    if (z2) {
                                        it4.remove();
                                    }
                                }
                            }
                        }
                    }
                    if (z6) {
                        List<eb> listA = a(false);
                        if (listA != null) {
                            return listA;
                        }
                        List<eb> listA2 = a(true);
                        if (listA2 != null) {
                            return listA2;
                        }
                    }
                    throw NotFoundException.getNotFoundInstance();
                }
                throw e;
            }
        }
    }

    public final List<eb> a(boolean z) {
        List<eb> listA = null;
        if (this.h.size() > 25) {
            this.h.clear();
            return null;
        }
        this.g.clear();
        if (z) {
            Collections.reverse(this.h);
        }
        try {
            listA = a(new ArrayList(), 0);
        } catch (NotFoundException unused) {
        }
        if (z) {
            Collections.reverse(this.h);
        }
        return listA;
    }

    public final List<eb> a(List<fb> list, int i) throws NotFoundException {
        boolean z;
        while (i < this.h.size()) {
            fb fbVar = this.h.get(i);
            this.g.clear();
            Iterator<fb> it = list.iterator();
            while (it.hasNext()) {
                this.g.addAll(it.next().a);
            }
            this.g.addAll(fbVar.a);
            List<eb> list2 = this.g;
            int[][] iArr = p;
            int length = iArr.length;
            boolean z2 = false;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    break;
                }
                int[] iArr2 = iArr[i2];
                if (list2.size() <= iArr2.length) {
                    int i3 = 0;
                    while (true) {
                        if (i3 >= list2.size()) {
                            z = true;
                            break;
                        }
                        if (list2.get(i3).c.getValue() != iArr2[i3]) {
                            z = false;
                            break;
                        }
                        i3++;
                    }
                    if (z) {
                        z2 = true;
                        break;
                    }
                }
                i2++;
            }
            if (z2) {
                if (a()) {
                    return this.g;
                }
                ArrayList arrayList = new ArrayList();
                arrayList.addAll(list);
                arrayList.add(fbVar);
                try {
                    return a(arrayList, i + 1);
                } catch (NotFoundException unused) {
                    continue;
                }
            }
            i++;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    public final boolean a() {
        eb ebVar = this.g.get(0);
        DataCharacter dataCharacter = ebVar.a;
        DataCharacter dataCharacter2 = ebVar.b;
        if (dataCharacter2 == null) {
            return false;
        }
        int checksumPortion = dataCharacter2.getChecksumPortion();
        int i = 2;
        for (int i2 = 1; i2 < this.g.size(); i2++) {
            eb ebVar2 = this.g.get(i2);
            int checksumPortion2 = ebVar2.a.getChecksumPortion() + checksumPortion;
            i++;
            DataCharacter dataCharacter3 = ebVar2.b;
            if (dataCharacter3 != null) {
                checksumPortion = dataCharacter3.getChecksumPortion() + checksumPortion2;
                i++;
            } else {
                checksumPortion = checksumPortion2;
            }
        }
        return g9.a(i, -4, 211, checksumPortion % 211) == dataCharacter.getValue();
    }

    public eb a(BitArray bitArray, List<eb> list, int i) throws NotFoundException {
        int i2;
        int i3;
        int i4;
        int i5;
        FinderPattern finderPattern;
        int i6 = 2;
        boolean z = list.size() % 2 == 0;
        if (this.j) {
            z = !z;
        }
        int nextUnset = -1;
        boolean z2 = true;
        while (true) {
            int[] decodeFinderCounters = getDecodeFinderCounters();
            decodeFinderCounters[0] = 0;
            decodeFinderCounters[1] = 0;
            decodeFinderCounters[i6] = 0;
            decodeFinderCounters[3] = 0;
            int size = bitArray.getSize();
            if (nextUnset >= 0) {
                i2 = nextUnset;
            } else {
                i2 = list.isEmpty() ? 0 : list.get(list.size() - 1).c.getStartEnd()[1];
            }
            boolean z3 = list.size() % i6 != 0;
            if (this.j) {
                z3 = !z3;
            }
            boolean z4 = false;
            while (i2 < size) {
                z4 = !bitArray.get(i2);
                if (!z4) {
                    break;
                }
                i2++;
            }
            int i7 = 0;
            boolean z5 = z4;
            int i8 = i2;
            while (i2 < size) {
                if (bitArray.get(i2) ^ z5) {
                    decodeFinderCounters[i7] = decodeFinderCounters[i7] + 1;
                } else {
                    if (i7 == 3) {
                        if (z3) {
                            a(decodeFinderCounters);
                        }
                        if (AbstractRSSReader.isFinderPattern(decodeFinderCounters)) {
                            int[] iArr = this.i;
                            iArr[0] = i8;
                            iArr[1] = i2;
                            if (z) {
                                int i9 = iArr[0] - 1;
                                while (i9 >= 0 && !bitArray.get(i9)) {
                                    i9--;
                                }
                                int i10 = i9 + 1;
                                int[] iArr2 = this.i;
                                i3 = iArr2[0] - i10;
                                i5 = i10;
                                i4 = iArr2[1];
                            } else {
                                int i11 = iArr[0];
                                int nextUnset2 = bitArray.getNextUnset(iArr[1] + 1);
                                i3 = nextUnset2 - this.i[1];
                                i4 = nextUnset2;
                                i5 = i11;
                            }
                            int[] decodeFinderCounters2 = getDecodeFinderCounters();
                            System.arraycopy(decodeFinderCounters2, 0, decodeFinderCounters2, 1, decodeFinderCounters2.length - 1);
                            decodeFinderCounters2[0] = i3;
                            DataCharacter dataCharacterA = null;
                            try {
                                finderPattern = new FinderPattern(AbstractRSSReader.parseFinderValue(decodeFinderCounters2, n), new int[]{i5, i4}, i5, i4, i);
                            } catch (NotFoundException unused) {
                                finderPattern = null;
                            }
                            if (finderPattern == null) {
                                int i12 = this.i[0];
                                if (bitArray.get(i12)) {
                                    nextUnset = bitArray.getNextSet(bitArray.getNextUnset(i12));
                                } else {
                                    nextUnset = bitArray.getNextUnset(bitArray.getNextSet(i12));
                                }
                            } else {
                                z2 = false;
                            }
                            if (!z2) {
                                DataCharacter dataCharacterA2 = a(bitArray, finderPattern, z, true);
                                if (!list.isEmpty()) {
                                    if (list.get(list.size() - 1).b == null) {
                                        throw NotFoundException.getNotFoundInstance();
                                    }
                                }
                                try {
                                    dataCharacterA = a(bitArray, finderPattern, z, false);
                                } catch (NotFoundException unused2) {
                                }
                                return new eb(dataCharacterA2, dataCharacterA, finderPattern, true);
                            }
                            i6 = 2;
                        } else {
                            if (z3) {
                                a(decodeFinderCounters);
                            }
                            i8 = decodeFinderCounters[0] + decodeFinderCounters[1] + i8;
                            decodeFinderCounters[0] = decodeFinderCounters[2];
                            decodeFinderCounters[1] = decodeFinderCounters[3];
                            decodeFinderCounters[2] = 0;
                            decodeFinderCounters[3] = 0;
                            i7--;
                        }
                    } else {
                        i7++;
                    }
                    decodeFinderCounters[i7] = 1;
                    z5 = !z5;
                }
                i2++;
            }
            throw NotFoundException.getNotFoundInstance();
        }
    }

    public static void a(int[] iArr) {
        int length = iArr.length;
        for (int i = 0; i < length / 2; i++) {
            int i2 = iArr[i];
            int i3 = (length - i) - 1;
            iArr[i] = iArr[i3];
            iArr[i3] = i2;
        }
    }

    public DataCharacter a(BitArray bitArray, FinderPattern finderPattern, boolean z, boolean z2) throws NotFoundException {
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6;
        boolean z7;
        boolean z8;
        boolean z9;
        boolean z10;
        int[] dataCharacterCounters = getDataCharacterCounters();
        dataCharacterCounters[0] = 0;
        dataCharacterCounters[1] = 0;
        dataCharacterCounters[2] = 0;
        dataCharacterCounters[3] = 0;
        dataCharacterCounters[4] = 0;
        dataCharacterCounters[5] = 0;
        dataCharacterCounters[6] = 0;
        dataCharacterCounters[7] = 0;
        if (z2) {
            OneDReader.recordPatternInReverse(bitArray, finderPattern.getStartEnd()[0], dataCharacterCounters);
        } else {
            OneDReader.recordPattern(bitArray, finderPattern.getStartEnd()[1], dataCharacterCounters);
            int i = 0;
            for (int length = dataCharacterCounters.length - 1; i < length; length--) {
                int i2 = dataCharacterCounters[i];
                dataCharacterCounters[i] = dataCharacterCounters[length];
                dataCharacterCounters[length] = i2;
                i++;
            }
        }
        float fSum = MathUtils.sum(dataCharacterCounters) / 17.0f;
        float f = (finderPattern.getStartEnd()[1] - finderPattern.getStartEnd()[0]) / 15.0f;
        if (Math.abs(fSum - f) / f <= 0.3f) {
            int[] oddCounts = getOddCounts();
            int[] evenCounts = getEvenCounts();
            float[] oddRoundingErrors = getOddRoundingErrors();
            float[] evenRoundingErrors = getEvenRoundingErrors();
            for (int i3 = 0; i3 < dataCharacterCounters.length; i3++) {
                float f2 = (dataCharacterCounters[i3] * 1.0f) / fSum;
                int i4 = (int) (0.5f + f2);
                if (i4 <= 0) {
                    if (f2 < 0.3f) {
                        throw NotFoundException.getNotFoundInstance();
                    }
                    i4 = 1;
                } else if (i4 > 8) {
                    if (f2 > 8.7f) {
                        throw NotFoundException.getNotFoundInstance();
                    }
                    i4 = 8;
                }
                int i5 = i3 / 2;
                if ((i3 & 1) == 0) {
                    oddCounts[i5] = i4;
                    oddRoundingErrors[i5] = f2 - i4;
                } else {
                    evenCounts[i5] = i4;
                    evenRoundingErrors[i5] = f2 - i4;
                }
            }
            int iSum = MathUtils.sum(getOddCounts());
            int iSum2 = MathUtils.sum(getEvenCounts());
            if (iSum > 13) {
                z3 = false;
                z4 = true;
            } else if (iSum < 4) {
                z4 = false;
                z3 = true;
            } else {
                z3 = false;
                z4 = false;
            }
            if (iSum2 > 13) {
                z5 = false;
                z6 = true;
            } else if (iSum2 < 4) {
                z6 = false;
                z5 = true;
            } else {
                z5 = false;
                z6 = false;
            }
            int i6 = (iSum + iSum2) - 17;
            boolean z11 = (iSum & 1) == 1;
            boolean z12 = (iSum2 & 1) == 0;
            if (i6 != 1) {
                z9 = z3;
                z7 = z4;
                z8 = z5;
                z10 = z6;
                if (i6 != -1) {
                    if (i6 != 0) {
                        throw NotFoundException.getNotFoundInstance();
                    }
                    if (z11) {
                        if (!z12) {
                            throw NotFoundException.getNotFoundInstance();
                        }
                        if (iSum < iSum2) {
                            z9 = true;
                            z10 = true;
                            z7 = z4;
                            z8 = z5;
                        } else {
                            z7 = true;
                            z8 = true;
                            z9 = z3;
                            z10 = z6;
                        }
                    } else if (z12) {
                        throw NotFoundException.getNotFoundInstance();
                    }
                } else if (z11) {
                    if (z12) {
                        throw NotFoundException.getNotFoundInstance();
                    }
                    z9 = true;
                    z7 = z4;
                    z8 = z5;
                    z10 = z6;
                } else {
                    if (!z12) {
                        throw NotFoundException.getNotFoundInstance();
                    }
                    z8 = true;
                    z9 = z3;
                    z7 = z4;
                    z10 = z6;
                }
            } else if (z11) {
                if (z12) {
                    throw NotFoundException.getNotFoundInstance();
                }
                z7 = true;
                z9 = z3;
                z8 = z5;
                z10 = z6;
            } else {
                if (!z12) {
                    throw NotFoundException.getNotFoundInstance();
                }
                z10 = true;
                z9 = z3;
                z7 = z4;
                z8 = z5;
            }
            if (z9) {
                if (!z7) {
                    AbstractRSSReader.increment(getOddCounts(), getOddRoundingErrors());
                } else {
                    throw NotFoundException.getNotFoundInstance();
                }
            }
            if (z7) {
                AbstractRSSReader.decrement(getOddCounts(), getOddRoundingErrors());
            }
            if (z8) {
                if (!z10) {
                    AbstractRSSReader.increment(getEvenCounts(), getOddRoundingErrors());
                } else {
                    throw NotFoundException.getNotFoundInstance();
                }
            }
            if (z10) {
                AbstractRSSReader.decrement(getEvenCounts(), getEvenRoundingErrors());
            }
            int value = (((finderPattern.getValue() * 4) + (z ? 0 : 2)) + (!z2 ? 1 : 0)) - 1;
            int i7 = 0;
            int i8 = 0;
            for (int length2 = oddCounts.length - 1; length2 >= 0; length2--) {
                if ((finderPattern.getValue() == 0 && z && z2) ? false : true) {
                    i7 += oddCounts[length2] * o[value][length2 * 2];
                }
                i8 += oddCounts[length2];
            }
            int i9 = 0;
            for (int length3 = evenCounts.length - 1; length3 >= 0; length3--) {
                if ((finderPattern.getValue() == 0 && z && z2) ? false : true) {
                    i9 += evenCounts[length3] * o[value][(length3 * 2) + 1];
                }
            }
            int i10 = i7 + i9;
            if ((i8 & 1) == 0 && i8 <= 13 && i8 >= 4) {
                int i11 = (13 - i8) / 2;
                int i12 = k[i11];
                return new DataCharacter((RSSUtils.getRSSvalue(oddCounts, i12, true) * l[i11]) + RSSUtils.getRSSvalue(evenCounts, 9 - i12, false) + m[i11], i10);
            }
            throw NotFoundException.getNotFoundInstance();
        }
        throw NotFoundException.getNotFoundInstance();
    }
}
