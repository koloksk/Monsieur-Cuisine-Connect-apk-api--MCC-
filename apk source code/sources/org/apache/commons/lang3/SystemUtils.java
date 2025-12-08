package org.apache.commons.lang3;

import java.io.File;

/* loaded from: classes.dex */
public class SystemUtils {
    public static final boolean IS_JAVA_10;
    public static final boolean IS_JAVA_11;
    public static final boolean IS_JAVA_1_1;
    public static final boolean IS_JAVA_1_2;
    public static final boolean IS_JAVA_1_3;
    public static final boolean IS_JAVA_1_4;
    public static final boolean IS_JAVA_1_5;
    public static final boolean IS_JAVA_1_6;
    public static final boolean IS_JAVA_1_7;
    public static final boolean IS_JAVA_1_8;

    @Deprecated
    public static final boolean IS_JAVA_1_9;
    public static final boolean IS_JAVA_9;
    public static final boolean IS_OS_400;
    public static final boolean IS_OS_AIX;
    public static final boolean IS_OS_FREE_BSD;
    public static final boolean IS_OS_HP_UX;
    public static final boolean IS_OS_IRIX;
    public static final boolean IS_OS_LINUX;
    public static final boolean IS_OS_MAC;
    public static final boolean IS_OS_MAC_OSX;
    public static final boolean IS_OS_MAC_OSX_CHEETAH;
    public static final boolean IS_OS_MAC_OSX_EL_CAPITAN;
    public static final boolean IS_OS_MAC_OSX_JAGUAR;
    public static final boolean IS_OS_MAC_OSX_LEOPARD;
    public static final boolean IS_OS_MAC_OSX_LION;
    public static final boolean IS_OS_MAC_OSX_MAVERICKS;
    public static final boolean IS_OS_MAC_OSX_MOUNTAIN_LION;
    public static final boolean IS_OS_MAC_OSX_PANTHER;
    public static final boolean IS_OS_MAC_OSX_PUMA;
    public static final boolean IS_OS_MAC_OSX_SNOW_LEOPARD;
    public static final boolean IS_OS_MAC_OSX_TIGER;
    public static final boolean IS_OS_MAC_OSX_YOSEMITE;
    public static final boolean IS_OS_NET_BSD;
    public static final boolean IS_OS_OPEN_BSD;
    public static final boolean IS_OS_OS2;
    public static final boolean IS_OS_SOLARIS;
    public static final boolean IS_OS_SUN_OS;
    public static final boolean IS_OS_UNIX;
    public static final boolean IS_OS_WINDOWS;
    public static final boolean IS_OS_WINDOWS_10;
    public static final boolean IS_OS_WINDOWS_2000;
    public static final boolean IS_OS_WINDOWS_2003;
    public static final boolean IS_OS_WINDOWS_2008;
    public static final boolean IS_OS_WINDOWS_2012;
    public static final boolean IS_OS_WINDOWS_7;
    public static final boolean IS_OS_WINDOWS_8;
    public static final boolean IS_OS_WINDOWS_95;
    public static final boolean IS_OS_WINDOWS_98;
    public static final boolean IS_OS_WINDOWS_ME;
    public static final boolean IS_OS_WINDOWS_NT;
    public static final boolean IS_OS_WINDOWS_VISTA;
    public static final boolean IS_OS_WINDOWS_XP;
    public static final boolean IS_OS_ZOS;
    public static final String JAVA_SPECIFICATION_VERSION;
    public static final String JAVA_UTIL_PREFS_PREFERENCES_FACTORY;
    public static final String JAVA_VENDOR;
    public static final String JAVA_VENDOR_URL;
    public static final String JAVA_VERSION;
    public static final String JAVA_VM_INFO;
    public static final String JAVA_VM_NAME;
    public static final String JAVA_VM_SPECIFICATION_NAME;
    public static final String JAVA_VM_SPECIFICATION_VENDOR;
    public static final String JAVA_VM_SPECIFICATION_VERSION;
    public static final String JAVA_VM_VENDOR;
    public static final String JAVA_VM_VERSION;

    @Deprecated
    public static final String LINE_SEPARATOR;
    public static final String OS_ARCH;
    public static final String OS_NAME;
    public static final String OS_VERSION;

    @Deprecated
    public static final String PATH_SEPARATOR;
    public static final String USER_COUNTRY;
    public static final String USER_DIR;
    public static final String USER_HOME;
    public static final String USER_LANGUAGE;
    public static final String USER_NAME;
    public static final String USER_TIMEZONE;
    public static final JavaVersion a;
    public static final String AWT_TOOLKIT = c("awt.toolkit");
    public static final String FILE_ENCODING = c("file.encoding");

    @Deprecated
    public static final String FILE_SEPARATOR = c("file.separator");
    public static final String JAVA_AWT_FONTS = c("java.awt.fonts");
    public static final String JAVA_AWT_GRAPHICSENV = c("java.awt.graphicsenv");
    public static final String JAVA_AWT_HEADLESS = c("java.awt.headless");
    public static final String JAVA_AWT_PRINTERJOB = c("java.awt.printerjob");
    public static final String JAVA_CLASS_PATH = c("java.class.path");
    public static final String JAVA_CLASS_VERSION = c("java.class.version");
    public static final String JAVA_COMPILER = c("java.compiler");
    public static final String JAVA_ENDORSED_DIRS = c("java.endorsed.dirs");
    public static final String JAVA_EXT_DIRS = c("java.ext.dirs");
    public static final String JAVA_HOME = c("java.home");
    public static final String JAVA_IO_TMPDIR = c("java.io.tmpdir");
    public static final String JAVA_LIBRARY_PATH = c("java.library.path");
    public static final String JAVA_RUNTIME_NAME = c("java.runtime.name");
    public static final String JAVA_RUNTIME_VERSION = c("java.runtime.version");
    public static final String JAVA_SPECIFICATION_NAME = c("java.specification.name");
    public static final String JAVA_SPECIFICATION_VENDOR = c("java.specification.vendor");

    static {
        String strC = c("java.specification.version");
        JAVA_SPECIFICATION_VERSION = strC;
        a = JavaVersion.a(strC);
        JAVA_UTIL_PREFS_PREFERENCES_FACTORY = c("java.util.prefs.PreferencesFactory");
        JAVA_VENDOR = c("java.vendor");
        JAVA_VENDOR_URL = c("java.vendor.url");
        JAVA_VERSION = c("java.version");
        JAVA_VM_INFO = c("java.vm.info");
        JAVA_VM_NAME = c("java.vm.name");
        JAVA_VM_SPECIFICATION_NAME = c("java.vm.specification.name");
        JAVA_VM_SPECIFICATION_VENDOR = c("java.vm.specification.vendor");
        JAVA_VM_SPECIFICATION_VERSION = c("java.vm.specification.version");
        JAVA_VM_VENDOR = c("java.vm.vendor");
        JAVA_VM_VERSION = c("java.vm.version");
        LINE_SEPARATOR = c("line.separator");
        OS_ARCH = c("os.arch");
        OS_NAME = c("os.name");
        OS_VERSION = c("os.version");
        PATH_SEPARATOR = c("path.separator");
        USER_COUNTRY = c(c("user.country") == null ? "user.region" : "user.country");
        USER_DIR = c("user.dir");
        USER_HOME = c("user.home");
        USER_LANGUAGE = c("user.language");
        USER_NAME = c("user.name");
        USER_TIMEZONE = c("user.timezone");
        IS_JAVA_1_1 = a("1.1");
        IS_JAVA_1_2 = a("1.2");
        IS_JAVA_1_3 = a("1.3");
        IS_JAVA_1_4 = a("1.4");
        IS_JAVA_1_5 = a("1.5");
        IS_JAVA_1_6 = a("1.6");
        IS_JAVA_1_7 = a("1.7");
        IS_JAVA_1_8 = a("1.8");
        IS_JAVA_1_9 = a("9");
        IS_JAVA_9 = a("9");
        IS_JAVA_10 = a("10");
        IS_JAVA_11 = a("11");
        IS_OS_AIX = b("AIX");
        IS_OS_HP_UX = b("HP-UX");
        IS_OS_400 = b("OS/400");
        IS_OS_IRIX = b("Irix");
        IS_OS_LINUX = b("Linux") || b("LINUX");
        IS_OS_MAC = b("Mac");
        IS_OS_MAC_OSX = b("Mac OS X");
        IS_OS_MAC_OSX_CHEETAH = a("Mac OS X", "10.0");
        IS_OS_MAC_OSX_PUMA = a("Mac OS X", "10.1");
        IS_OS_MAC_OSX_JAGUAR = a("Mac OS X", "10.2");
        IS_OS_MAC_OSX_PANTHER = a("Mac OS X", "10.3");
        IS_OS_MAC_OSX_TIGER = a("Mac OS X", "10.4");
        IS_OS_MAC_OSX_LEOPARD = a("Mac OS X", "10.5");
        IS_OS_MAC_OSX_SNOW_LEOPARD = a("Mac OS X", "10.6");
        IS_OS_MAC_OSX_LION = a("Mac OS X", "10.7");
        IS_OS_MAC_OSX_MOUNTAIN_LION = a("Mac OS X", "10.8");
        IS_OS_MAC_OSX_MAVERICKS = a("Mac OS X", "10.9");
        IS_OS_MAC_OSX_YOSEMITE = a("Mac OS X", "10.10");
        IS_OS_MAC_OSX_EL_CAPITAN = a("Mac OS X", "10.11");
        IS_OS_FREE_BSD = b("FreeBSD");
        IS_OS_OPEN_BSD = b("OpenBSD");
        IS_OS_NET_BSD = b("NetBSD");
        IS_OS_OS2 = b("OS/2");
        IS_OS_SOLARIS = b("Solaris");
        boolean zB = b("SunOS");
        IS_OS_SUN_OS = zB;
        IS_OS_UNIX = IS_OS_AIX || IS_OS_HP_UX || IS_OS_IRIX || IS_OS_LINUX || IS_OS_MAC_OSX || IS_OS_SOLARIS || zB || IS_OS_FREE_BSD || IS_OS_OPEN_BSD || IS_OS_NET_BSD;
        IS_OS_WINDOWS = b("Windows");
        IS_OS_WINDOWS_2000 = b("Windows 2000");
        IS_OS_WINDOWS_2003 = b("Windows 2003");
        IS_OS_WINDOWS_2008 = b("Windows Server 2008");
        IS_OS_WINDOWS_2012 = b("Windows Server 2012");
        IS_OS_WINDOWS_95 = b("Windows 95");
        IS_OS_WINDOWS_98 = b("Windows 98");
        IS_OS_WINDOWS_ME = b("Windows Me");
        IS_OS_WINDOWS_NT = b("Windows NT");
        IS_OS_WINDOWS_XP = b("Windows XP");
        IS_OS_WINDOWS_VISTA = b("Windows Vista");
        IS_OS_WINDOWS_7 = b("Windows 7");
        IS_OS_WINDOWS_8 = b("Windows 8");
        IS_OS_WINDOWS_10 = b("Windows 10");
        IS_OS_ZOS = b("z/OS");
    }

    public static boolean a(String str) {
        String str2 = JAVA_SPECIFICATION_VERSION;
        if (str2 == null) {
            return false;
        }
        return str2.startsWith(str);
    }

    public static boolean b(String str) {
        String str2 = OS_NAME;
        if (str2 == null) {
            return false;
        }
        return str2.startsWith(str);
    }

    public static String c(String str) {
        try {
            return System.getProperty(str);
        } catch (SecurityException unused) {
            return null;
        }
    }

    public static String getEnvironmentVariable(String str, String str2) {
        try {
            String str3 = System.getenv(str);
            return str3 == null ? str2 : str3;
        } catch (SecurityException unused) {
            return str2;
        }
    }

    public static String getHostName() {
        return System.getenv(IS_OS_WINDOWS ? "COMPUTERNAME" : "HOSTNAME");
    }

    public static File getJavaHome() {
        return new File(System.getProperty("java.home"));
    }

    public static File getJavaIoTmpDir() {
        return new File(System.getProperty("java.io.tmpdir"));
    }

    public static File getUserDir() {
        return new File(System.getProperty("user.dir"));
    }

    public static File getUserHome() {
        return new File(System.getProperty("user.home"));
    }

    public static boolean isJavaAwtHeadless() {
        return Boolean.TRUE.toString().equals(JAVA_AWT_HEADLESS);
    }

    public static boolean isJavaVersionAtLeast(JavaVersion javaVersion) {
        return a.atLeast(javaVersion);
    }

    public static boolean a(String str, String str2) {
        boolean z;
        String str3 = OS_NAME;
        String str4 = OS_VERSION;
        if (str3 == null || str4 == null || !str3.startsWith(str)) {
            return false;
        }
        if (StringUtils.isEmpty(str4)) {
            z = false;
            break;
        }
        String[] strArrSplit = str2.split("\\.");
        String[] strArrSplit2 = str4.split("\\.");
        for (int i = 0; i < Math.min(strArrSplit.length, strArrSplit2.length); i++) {
            if (!strArrSplit[i].equals(strArrSplit2[i])) {
                z = false;
                break;
            }
        }
        z = true;
        return z;
    }
}
