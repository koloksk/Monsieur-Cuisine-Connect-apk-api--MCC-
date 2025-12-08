package machineAdapter.impl;

import android.content.Context;
import de.silpion.mc2.BuildConfig;
import defpackage.ul;
import defpackage.vl;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import machineAdapter.IMachineAdapter;
import machineAdapter.impl.service.HardwareMachineService;
import machineAdapter.impl.service.MockMachineService;

/* loaded from: classes.dex */
public final class MachineAdapterFactory {

    @Retention(RetentionPolicy.SOURCE)
    public @interface BuildFlavor {
    }

    public static String getBuildFlavor() {
        return BuildConfig.FLAVOR;
    }

    public IMachineAdapter createMachineAdapter(Context context) {
        return getBuildFlavor().equals("mockMachineAdapter") ? new vl(MockMachineService.class, context) : new ul(HardwareMachineService.class, context);
    }
}
