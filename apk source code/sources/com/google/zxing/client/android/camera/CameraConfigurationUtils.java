package com.google.zxing.client.android.camera;

import android.annotation.TargetApi;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Build;
import android.util.Log;
import com.journeyapps.barcodescanner.camera.CameraSettings;
import defpackage.g9;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public final class CameraConfigurationUtils {
    public static final Pattern a = Pattern.compile(";");

    public static String a(String str, Collection<String> collection, String... strArr) {
        Log.i("CameraConfiguration", "Requesting " + str + " value from among: " + Arrays.toString(strArr));
        Log.i("CameraConfiguration", "Supported " + str + " values: " + collection);
        if (collection != null) {
            for (String str2 : strArr) {
                if (collection.contains(str2)) {
                    Log.i("CameraConfiguration", "Can set " + str + " to: " + str2);
                    return str2;
                }
            }
        }
        Log.i("CameraConfiguration", "No supported values match");
        return null;
    }

    public static String collectStats(Camera.Parameters parameters) {
        return collectStats(parameters.flatten());
    }

    public static void setBarcodeSceneMode(Camera.Parameters parameters) {
        if ("barcode".equals(parameters.getSceneMode())) {
            Log.i("CameraConfiguration", "Barcode scene mode already set");
            return;
        }
        String strA = a("scene mode", parameters.getSupportedSceneModes(), "barcode");
        if (strA != null) {
            parameters.setSceneMode(strA);
        }
    }

    public static void setBestExposure(Camera.Parameters parameters, boolean z) {
        int minExposureCompensation = parameters.getMinExposureCompensation();
        int maxExposureCompensation = parameters.getMaxExposureCompensation();
        float exposureCompensationStep = parameters.getExposureCompensationStep();
        if (minExposureCompensation != 0 || maxExposureCompensation != 0) {
            if (exposureCompensationStep > 0.0f) {
                int iRound = Math.round((z ? 0.0f : 1.5f) / exposureCompensationStep);
                float f = exposureCompensationStep * iRound;
                int iMax = Math.max(Math.min(iRound, maxExposureCompensation), minExposureCompensation);
                if (parameters.getExposureCompensation() == iMax) {
                    Log.i("CameraConfiguration", "Exposure compensation already set to " + iMax + " / " + f);
                    return;
                }
                Log.i("CameraConfiguration", "Setting exposure compensation to " + iMax + " / " + f);
                parameters.setExposureCompensation(iMax);
                return;
            }
        }
        Log.i("CameraConfiguration", "Camera does not support exposure compensation");
    }

    public static void setBestPreviewFPS(Camera.Parameters parameters) {
        setBestPreviewFPS(parameters, 10, 20);
    }

    public static void setFocus(Camera.Parameters parameters, CameraSettings.FocusMode focusMode, boolean z) {
        List<String> supportedFocusModes = parameters.getSupportedFocusModes();
        String strA = (z || focusMode == CameraSettings.FocusMode.AUTO) ? a("focus mode", supportedFocusModes, "auto") : focusMode == CameraSettings.FocusMode.CONTINUOUS ? a("focus mode", supportedFocusModes, "continuous-picture", "continuous-video", "auto") : focusMode == CameraSettings.FocusMode.INFINITY ? a("focus mode", supportedFocusModes, "infinity") : focusMode == CameraSettings.FocusMode.MACRO ? a("focus mode", supportedFocusModes, "macro") : null;
        if (!z && strA == null) {
            strA = a("focus mode", supportedFocusModes, "macro", "edof");
        }
        if (strA != null) {
            if (!strA.equals(parameters.getFocusMode())) {
                parameters.setFocusMode(strA);
                return;
            }
            Log.i("CameraConfiguration", "Focus mode already set to " + strA);
        }
    }

    @TargetApi(15)
    public static void setFocusArea(Camera.Parameters parameters) {
        if (parameters.getMaxNumFocusAreas() <= 0) {
            Log.i("CameraConfiguration", "Device does not support focus areas");
            return;
        }
        StringBuilder sbA = g9.a("Old focus areas: ");
        sbA.append(a(parameters.getFocusAreas()));
        Log.i("CameraConfiguration", sbA.toString());
        List<Camera.Area> listSingletonList = Collections.singletonList(new Camera.Area(new Rect(-400, -400, 400, 400), 1));
        StringBuilder sbA2 = g9.a("Setting focus area to : ");
        sbA2.append(a(listSingletonList));
        Log.i("CameraConfiguration", sbA2.toString());
        parameters.setFocusAreas(listSingletonList);
    }

    public static void setInvertColor(Camera.Parameters parameters) {
        if ("negative".equals(parameters.getColorEffect())) {
            Log.i("CameraConfiguration", "Negative effect already set");
            return;
        }
        String strA = a("color effect", parameters.getSupportedColorEffects(), "negative");
        if (strA != null) {
            parameters.setColorEffect(strA);
        }
    }

    @TargetApi(15)
    public static void setMetering(Camera.Parameters parameters) {
        if (parameters.getMaxNumMeteringAreas() <= 0) {
            Log.i("CameraConfiguration", "Device does not support metering areas");
            return;
        }
        StringBuilder sbA = g9.a("Old metering areas: ");
        sbA.append(parameters.getMeteringAreas());
        Log.i("CameraConfiguration", sbA.toString());
        List<Camera.Area> listSingletonList = Collections.singletonList(new Camera.Area(new Rect(-400, -400, 400, 400), 1));
        StringBuilder sbA2 = g9.a("Setting metering area to : ");
        sbA2.append(a(listSingletonList));
        Log.i("CameraConfiguration", sbA2.toString());
        parameters.setMeteringAreas(listSingletonList);
    }

    public static void setTorch(Camera.Parameters parameters, boolean z) {
        List<String> supportedFlashModes = parameters.getSupportedFlashModes();
        String strA = z ? a("flash mode", supportedFlashModes, "torch", "on") : a("flash mode", supportedFlashModes, "off");
        if (strA != null) {
            if (strA.equals(parameters.getFlashMode())) {
                Log.i("CameraConfiguration", "Flash mode already set to " + strA);
                return;
            }
            Log.i("CameraConfiguration", "Setting flash mode to " + strA);
            parameters.setFlashMode(strA);
        }
    }

    @TargetApi(15)
    public static void setVideoStabilization(Camera.Parameters parameters) {
        if (!parameters.isVideoStabilizationSupported()) {
            Log.i("CameraConfiguration", "This device does not support video stabilization");
        } else if (parameters.getVideoStabilization()) {
            Log.i("CameraConfiguration", "Video stabilization already enabled");
        } else {
            Log.i("CameraConfiguration", "Enabling video stabilization...");
            parameters.setVideoStabilization(true);
        }
    }

    public static void setZoom(Camera.Parameters parameters, double d) {
        Integer numValueOf;
        if (!parameters.isZoomSupported()) {
            Log.i("CameraConfiguration", "Zoom is not supported");
            return;
        }
        List<Integer> zoomRatios = parameters.getZoomRatios();
        Log.i("CameraConfiguration", "Zoom ratios: " + zoomRatios);
        int maxZoom = parameters.getMaxZoom();
        if (zoomRatios == null || zoomRatios.isEmpty() || zoomRatios.size() != maxZoom + 1) {
            Log.w("CameraConfiguration", "Invalid zoom ratios!");
            numValueOf = null;
        } else {
            double d2 = d * 100.0d;
            double d3 = Double.POSITIVE_INFINITY;
            int i = 0;
            for (int i2 = 0; i2 < zoomRatios.size(); i2++) {
                double dAbs = Math.abs(zoomRatios.get(i2).intValue() - d2);
                if (dAbs < d3) {
                    i = i2;
                    d3 = dAbs;
                }
            }
            StringBuilder sbA = g9.a("Chose zoom ratio of ");
            sbA.append(zoomRatios.get(i).intValue() / 100.0d);
            Log.i("CameraConfiguration", sbA.toString());
            numValueOf = Integer.valueOf(i);
        }
        if (numValueOf == null) {
            return;
        }
        if (parameters.getZoom() == numValueOf.intValue()) {
            Log.i("CameraConfiguration", "Zoom is already set to " + numValueOf);
            return;
        }
        Log.i("CameraConfiguration", "Setting zoom to " + numValueOf);
        parameters.setZoom(numValueOf.intValue());
    }

    public static String collectStats(CharSequence charSequence) {
        StringBuilder sb = new StringBuilder(1000);
        sb.append("BOARD=");
        g9.a(sb, Build.BOARD, '\n', "BRAND=");
        g9.a(sb, Build.BRAND, '\n', "CPU_ABI=");
        g9.a(sb, Build.CPU_ABI, '\n', "DEVICE=");
        g9.a(sb, Build.DEVICE, '\n', "DISPLAY=");
        g9.a(sb, Build.DISPLAY, '\n', "FINGERPRINT=");
        g9.a(sb, Build.FINGERPRINT, '\n', "HOST=");
        g9.a(sb, Build.HOST, '\n', "ID=");
        g9.a(sb, Build.ID, '\n', "MANUFACTURER=");
        g9.a(sb, Build.MANUFACTURER, '\n', "MODEL=");
        g9.a(sb, Build.MODEL, '\n', "PRODUCT=");
        g9.a(sb, Build.PRODUCT, '\n', "TAGS=");
        g9.a(sb, Build.TAGS, '\n', "TIME=");
        sb.append(Build.TIME);
        sb.append('\n');
        sb.append("TYPE=");
        g9.a(sb, Build.TYPE, '\n', "USER=");
        g9.a(sb, Build.USER, '\n', "VERSION.CODENAME=");
        g9.a(sb, Build.VERSION.CODENAME, '\n', "VERSION.INCREMENTAL=");
        g9.a(sb, Build.VERSION.INCREMENTAL, '\n', "VERSION.RELEASE=");
        g9.a(sb, Build.VERSION.RELEASE, '\n', "VERSION.SDK_INT=");
        sb.append(Build.VERSION.SDK_INT);
        sb.append('\n');
        if (charSequence != null) {
            String[] strArrSplit = a.split(charSequence);
            Arrays.sort(strArrSplit);
            for (String str : strArrSplit) {
                sb.append(str);
                sb.append('\n');
            }
        }
        return sb.toString();
    }

    public static void setBestPreviewFPS(Camera.Parameters parameters, int i, int i2) {
        String string;
        List<int[]> supportedPreviewFpsRange = parameters.getSupportedPreviewFpsRange();
        StringBuilder sbA = g9.a("Supported FPS ranges: ");
        if (supportedPreviewFpsRange == null || supportedPreviewFpsRange.isEmpty()) {
            string = "[]";
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append('[');
            Iterator<int[]> it = supportedPreviewFpsRange.iterator();
            while (it.hasNext()) {
                sb.append(Arrays.toString(it.next()));
                if (it.hasNext()) {
                    sb.append(", ");
                }
            }
            sb.append(']');
            string = sb.toString();
        }
        sbA.append(string);
        Log.i("CameraConfiguration", sbA.toString());
        if (supportedPreviewFpsRange == null || supportedPreviewFpsRange.isEmpty()) {
            return;
        }
        int[] iArr = null;
        Iterator<int[]> it2 = supportedPreviewFpsRange.iterator();
        while (true) {
            if (!it2.hasNext()) {
                break;
            }
            int[] next = it2.next();
            int i3 = next[0];
            int i4 = next[1];
            if (i3 >= i * 1000 && i4 <= i2 * 1000) {
                iArr = next;
                break;
            }
        }
        if (iArr == null) {
            Log.i("CameraConfiguration", "No suitable FPS range?");
            return;
        }
        int[] iArr2 = new int[2];
        parameters.getPreviewFpsRange(iArr2);
        if (Arrays.equals(iArr2, iArr)) {
            StringBuilder sbA2 = g9.a("FPS range already set to ");
            sbA2.append(Arrays.toString(iArr));
            Log.i("CameraConfiguration", sbA2.toString());
        } else {
            StringBuilder sbA3 = g9.a("Setting FPS range to ");
            sbA3.append(Arrays.toString(iArr));
            Log.i("CameraConfiguration", sbA3.toString());
            parameters.setPreviewFpsRange(iArr[0], iArr[1]);
        }
    }

    @TargetApi(15)
    public static String a(Iterable<Camera.Area> iterable) {
        if (iterable == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (Camera.Area area : iterable) {
            sb.append(area.rect);
            sb.append(':');
            sb.append(area.weight);
            sb.append(' ');
        }
        return sb.toString();
    }
}
