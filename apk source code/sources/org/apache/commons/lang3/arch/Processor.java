package org.apache.commons.lang3.arch;

/* loaded from: classes.dex */
public class Processor {
    public final Arch a;
    public final Type b;

    public enum Arch {
        BIT_32,
        BIT_64,
        UNKNOWN
    }

    public enum Type {
        X86,
        IA_64,
        PPC,
        UNKNOWN
    }

    public Processor(Arch arch, Type type) {
        this.a = arch;
        this.b = type;
    }

    public Arch getArch() {
        return this.a;
    }

    public Type getType() {
        return this.b;
    }

    public boolean is32Bit() {
        return Arch.BIT_32.equals(this.a);
    }

    public boolean is64Bit() {
        return Arch.BIT_64.equals(this.a);
    }

    public boolean isIA64() {
        return Type.IA_64.equals(this.b);
    }

    public boolean isPPC() {
        return Type.PPC.equals(this.b);
    }

    public boolean isX86() {
        return Type.X86.equals(this.b);
    }
}
