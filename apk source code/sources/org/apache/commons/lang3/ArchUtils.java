package org.apache.commons.lang3;

import defpackage.g9;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.arch.Processor;

/* loaded from: classes.dex */
public class ArchUtils {
    public static final Map<String, Processor> a = new HashMap();

    static {
        a(new Processor(Processor.Arch.BIT_32, Processor.Type.X86), "x86", "i386", "i486", "i586", "i686", "pentium");
        a(new Processor(Processor.Arch.BIT_64, Processor.Type.X86), "x86_64", "amd64", "em64t", "universal");
        a(new Processor(Processor.Arch.BIT_32, Processor.Type.IA_64), "ia64_32", "ia64n");
        a(new Processor(Processor.Arch.BIT_64, Processor.Type.IA_64), "ia64", "ia64w");
        a(new Processor(Processor.Arch.BIT_32, Processor.Type.PPC), "ppc", "power", "powerpc", "power_pc", "power_rs");
        a(new Processor(Processor.Arch.BIT_64, Processor.Type.PPC), "ppc64", "power64", "powerpc64", "power_pc64", "power_rs64");
    }

    public static void a(Processor processor, String... strArr) {
        for (String str : strArr) {
            if (a.containsKey(str)) {
                throw new IllegalStateException(g9.a("Key ", str, " already exists in processor map"));
            }
            a.put(str, processor);
        }
    }

    public static Processor getProcessor() {
        return getProcessor(SystemUtils.OS_ARCH);
    }

    public static Processor getProcessor(String str) {
        return a.get(str);
    }
}
