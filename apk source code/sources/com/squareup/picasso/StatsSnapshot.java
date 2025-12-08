package com.squareup.picasso;

import android.util.Log;
import defpackage.g9;
import java.io.PrintWriter;
import java.io.StringWriter;

/* loaded from: classes.dex */
public class StatsSnapshot {
    public final long averageDownloadSize;
    public final long averageOriginalBitmapSize;
    public final long averageTransformedBitmapSize;
    public final long cacheHits;
    public final long cacheMisses;
    public final int downloadCount;
    public final int maxSize;
    public final int originalBitmapCount;
    public final int size;
    public final long timeStamp;
    public final long totalDownloadSize;
    public final long totalOriginalBitmapSize;
    public final long totalTransformedBitmapSize;
    public final int transformedBitmapCount;

    public StatsSnapshot(int i, int i2, long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8, int i3, int i4, int i5, long j9) {
        this.maxSize = i;
        this.size = i2;
        this.cacheHits = j;
        this.cacheMisses = j2;
        this.totalDownloadSize = j3;
        this.totalOriginalBitmapSize = j4;
        this.totalTransformedBitmapSize = j5;
        this.averageDownloadSize = j6;
        this.averageOriginalBitmapSize = j7;
        this.averageTransformedBitmapSize = j8;
        this.downloadCount = i3;
        this.originalBitmapCount = i4;
        this.transformedBitmapCount = i5;
        this.timeStamp = j9;
    }

    public void dump() {
        StringWriter stringWriter = new StringWriter();
        dump(new PrintWriter(stringWriter));
        Log.i("Picasso", stringWriter.toString());
    }

    public String toString() {
        StringBuilder sbA = g9.a("StatsSnapshot{maxSize=");
        sbA.append(this.maxSize);
        sbA.append(", size=");
        sbA.append(this.size);
        sbA.append(", cacheHits=");
        sbA.append(this.cacheHits);
        sbA.append(", cacheMisses=");
        sbA.append(this.cacheMisses);
        sbA.append(", downloadCount=");
        sbA.append(this.downloadCount);
        sbA.append(", totalDownloadSize=");
        sbA.append(this.totalDownloadSize);
        sbA.append(", averageDownloadSize=");
        sbA.append(this.averageDownloadSize);
        sbA.append(", totalOriginalBitmapSize=");
        sbA.append(this.totalOriginalBitmapSize);
        sbA.append(", totalTransformedBitmapSize=");
        sbA.append(this.totalTransformedBitmapSize);
        sbA.append(", averageOriginalBitmapSize=");
        sbA.append(this.averageOriginalBitmapSize);
        sbA.append(", averageTransformedBitmapSize=");
        sbA.append(this.averageTransformedBitmapSize);
        sbA.append(", originalBitmapCount=");
        sbA.append(this.originalBitmapCount);
        sbA.append(", transformedBitmapCount=");
        sbA.append(this.transformedBitmapCount);
        sbA.append(", timeStamp=");
        sbA.append(this.timeStamp);
        sbA.append('}');
        return sbA.toString();
    }

    public void dump(PrintWriter printWriter) {
        printWriter.println("===============BEGIN PICASSO STATS ===============");
        printWriter.println("Memory Cache Stats");
        printWriter.print("  Max Cache Size: ");
        printWriter.println(this.maxSize);
        printWriter.print("  Cache Size: ");
        printWriter.println(this.size);
        printWriter.print("  Cache % Full: ");
        printWriter.println((int) Math.ceil((this.size / this.maxSize) * 100.0f));
        printWriter.print("  Cache Hits: ");
        printWriter.println(this.cacheHits);
        printWriter.print("  Cache Misses: ");
        printWriter.println(this.cacheMisses);
        printWriter.println("Network Stats");
        printWriter.print("  Download Count: ");
        printWriter.println(this.downloadCount);
        printWriter.print("  Total Download Size: ");
        printWriter.println(this.totalDownloadSize);
        printWriter.print("  Average Download Size: ");
        printWriter.println(this.averageDownloadSize);
        printWriter.println("Bitmap Stats");
        printWriter.print("  Total Bitmaps Decoded: ");
        printWriter.println(this.originalBitmapCount);
        printWriter.print("  Total Bitmap Size: ");
        printWriter.println(this.totalOriginalBitmapSize);
        printWriter.print("  Total Transformed Bitmaps: ");
        printWriter.println(this.transformedBitmapCount);
        printWriter.print("  Total Transformed Bitmap Size: ");
        printWriter.println(this.totalTransformedBitmapSize);
        printWriter.print("  Average Bitmap Size: ");
        printWriter.println(this.averageOriginalBitmapSize);
        printWriter.print("  Average Transformed Bitmap Size: ");
        printWriter.println(this.averageTransformedBitmapSize);
        printWriter.println("===============END PICASSO STATS ===============");
        printWriter.flush();
    }
}
