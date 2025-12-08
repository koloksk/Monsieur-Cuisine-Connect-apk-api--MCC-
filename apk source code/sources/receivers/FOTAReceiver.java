package receivers;

import android.content.BroadcastReceiver;

/* loaded from: classes.dex */
public class FOTAReceiver extends BroadcastReceiver {
    /* JADX WARN: Removed duplicated region for block: B:18:0x0038  */
    @Override // android.content.BroadcastReceiver
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onReceive(android.content.Context r4, android.content.Intent r5) {
        /*
            r3 = this;
            java.lang.String r4 = r5.getAction()
            int r5 = r4.hashCode()
            r0 = -1357953369(0xffffffffaf0f46a7, float:-1.3030875E-10)
            r1 = 2
            r2 = 1
            if (r5 == r0) goto L2e
            r0 = -772275019(0xffffffffd1f804b5, float:-1.33153858E11)
            if (r5 == r0) goto L24
            r0 = -760525912(0xffffffffd2ab4ba8, float:-3.67854354E11)
            if (r5 == r0) goto L1a
            goto L38
        L1a:
            java.lang.String r5 = "action:fota.msg.install_other_image.response"
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L38
            r4 = r2
            goto L39
        L24:
            java.lang.String r5 = "action:fota.msg.install_other_image_result"
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L38
            r4 = r1
            goto L39
        L2e:
            java.lang.String r5 = "action:fota.msg.install_other_image"
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L38
            r4 = 0
            goto L39
        L38:
            r4 = -1
        L39:
            if (r4 == 0) goto L52
            if (r4 == r2) goto L48
            if (r4 == r1) goto L40
            goto L5b
        L40:
            machineAdapter.impl.service.HardwareLEDService r4 = machineAdapter.impl.service.HardwareLEDService.getInstance()
            r4.turnOff()
            goto L5b
        L48:
            machineAdapter.impl.service.HardwareLEDService r4 = machineAdapter.impl.service.HardwareLEDService.getInstance()
            machineAdapter.impl.service.LEDColor r5 = machineAdapter.impl.service.LEDColor.GREEN
            r4.pulseLED(r5)
            goto L5b
        L52:
            machineAdapter.impl.service.HardwareLEDService r4 = machineAdapter.impl.service.HardwareLEDService.getInstance()
            machineAdapter.impl.service.LEDColor r5 = machineAdapter.impl.service.LEDColor.WHITE
            r4.blinkLED(r5)
        L5b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: receivers.FOTAReceiver.onReceive(android.content.Context, android.content.Intent):void");
    }
}
