package org.apache.commons.lang3.exception;

import defpackage.g9;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

/* loaded from: classes.dex */
public class ExceptionUtils {
    public static final String[] a = {"getCause", "getNextException", "getTargetException", "getException", "getSourceException", "getRootCause", "getCausedByException", "getNested", "getLinkedException", "getNestedException", "getLinkedCause", "getThrowable"};

    public static int a(Throwable th, Class<?> cls, int i, boolean z) {
        if (th != null && cls != null) {
            if (i < 0) {
                i = 0;
            }
            Throwable[] throwables = getThrowables(th);
            if (i >= throwables.length) {
                return -1;
            }
            if (z) {
                while (i < throwables.length) {
                    if (cls.isAssignableFrom(throwables[i].getClass())) {
                        return i;
                    }
                    i++;
                }
            } else {
                while (i < throwables.length) {
                    if (cls.equals(throwables[i].getClass())) {
                        return i;
                    }
                    i++;
                }
            }
        }
        return -1;
    }

    @Deprecated
    public static Throwable getCause(Throwable th) {
        return getCause(th, null);
    }

    @Deprecated
    public static String[] getDefaultCauseMethodNames() {
        return (String[]) ArrayUtils.clone(a);
    }

    public static String getMessage(Throwable th) {
        if (th == null) {
            return "";
        }
        String shortClassName = ClassUtils.getShortClassName(th, null);
        String message = th.getMessage();
        StringBuilder sbA = g9.a(shortClassName, ": ");
        sbA.append(StringUtils.defaultString(message));
        return sbA.toString();
    }

    public static Throwable getRootCause(Throwable th) {
        List<Throwable> throwableList = getThrowableList(th);
        if (throwableList.isEmpty()) {
            return null;
        }
        return throwableList.get(throwableList.size() - 1);
    }

    public static String getRootCauseMessage(Throwable th) {
        Throwable rootCause = getRootCause(th);
        if (rootCause != null) {
            th = rootCause;
        }
        return getMessage(th);
    }

    public static String[] getRootCauseStackTrace(Throwable th) {
        List<String> listA;
        if (th == null) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        Throwable[] throwables = getThrowables(th);
        int length = throwables.length;
        ArrayList arrayList = new ArrayList();
        int i = length - 1;
        List<String> listA2 = a(throwables[i]);
        while (true) {
            length--;
            if (length < 0) {
                return (String[]) arrayList.toArray(new String[arrayList.size()]);
            }
            if (length != 0) {
                listA = a(throwables[length - 1]);
                removeCommonFrames(listA2, listA);
            } else {
                listA = listA2;
            }
            if (length == i) {
                arrayList.add(throwables[length].toString());
            } else {
                StringBuilder sbA = g9.a(" [wrapped] ");
                sbA.append(throwables[length].toString());
                arrayList.add(sbA.toString());
            }
            arrayList.addAll(listA2);
            listA2 = listA;
        }
    }

    public static String[] getStackFrames(Throwable th) {
        if (th == null) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        StringTokenizer stringTokenizer = new StringTokenizer(getStackTrace(th), System.lineSeparator());
        ArrayList arrayList = new ArrayList();
        while (stringTokenizer.hasMoreTokens()) {
            arrayList.add(stringTokenizer.nextToken());
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    public static String getStackTrace(Throwable th) {
        StringWriter stringWriter = new StringWriter();
        th.printStackTrace(new PrintWriter((Writer) stringWriter, true));
        return stringWriter.getBuffer().toString();
    }

    public static int getThrowableCount(Throwable th) {
        return getThrowableList(th).size();
    }

    public static List<Throwable> getThrowableList(Throwable th) {
        ArrayList arrayList = new ArrayList();
        while (th != null && !arrayList.contains(th)) {
            arrayList.add(th);
            th = th.getCause();
        }
        return arrayList;
    }

    public static Throwable[] getThrowables(Throwable th) {
        List<Throwable> throwableList = getThrowableList(th);
        return (Throwable[]) throwableList.toArray(new Throwable[throwableList.size()]);
    }

    public static boolean hasCause(Throwable th, Class<? extends Throwable> cls) {
        if (th instanceof UndeclaredThrowableException) {
            th = th.getCause();
        }
        return cls.isInstance(th);
    }

    public static int indexOfThrowable(Throwable th, Class<?> cls) {
        return a(th, cls, 0, false);
    }

    public static int indexOfType(Throwable th, Class<?> cls) {
        return a(th, cls, 0, true);
    }

    public static void printRootCauseStackTrace(Throwable th) {
        printRootCauseStackTrace(th, System.err);
    }

    public static void removeCommonFrames(List<String> list, List<String> list2) {
        if (list == null || list2 == null) {
            throw new IllegalArgumentException("The List must not be null");
        }
        int size = list.size() - 1;
        for (int size2 = list2.size() - 1; size >= 0 && size2 >= 0; size2--) {
            if (list.get(size).equals(list2.get(size2))) {
                list.remove(size);
            }
            size--;
        }
    }

    public static <R> R rethrow(Throwable th) {
        throw th;
    }

    public static <R> R wrapAndThrow(Throwable th) {
        if (th instanceof RuntimeException) {
            throw ((RuntimeException) th);
        }
        if (th instanceof Error) {
            throw ((Error) th);
        }
        throw new UndeclaredThrowableException(th);
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x003e A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x003f A[SYNTHETIC] */
    @java.lang.Deprecated
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.Throwable getCause(java.lang.Throwable r7, java.lang.String[] r8) throws java.lang.NoSuchMethodException, java.lang.SecurityException {
        /*
            r0 = 0
            if (r7 != 0) goto L4
            return r0
        L4:
            if (r8 != 0) goto Lf
            java.lang.Throwable r8 = r7.getCause()
            if (r8 == 0) goto Ld
            return r8
        Ld:
            java.lang.String[] r8 = org.apache.commons.lang3.exception.ExceptionUtils.a
        Lf:
            int r1 = r8.length
            r2 = 0
            r3 = r2
        L12:
            if (r3 >= r1) goto L42
            r4 = r8[r3]
            if (r4 == 0) goto L3f
            java.lang.Class r5 = r7.getClass()     // Catch: java.lang.Throwable -> L23
            java.lang.Class[] r6 = new java.lang.Class[r2]     // Catch: java.lang.Throwable -> L23
            java.lang.reflect.Method r4 = r5.getMethod(r4, r6)     // Catch: java.lang.Throwable -> L23
            goto L24
        L23:
            r4 = r0
        L24:
            if (r4 == 0) goto L3b
            java.lang.Class<java.lang.Throwable> r5 = java.lang.Throwable.class
            java.lang.Class r6 = r4.getReturnType()
            boolean r5 = r5.isAssignableFrom(r6)
            if (r5 == 0) goto L3b
            java.lang.Object[] r5 = new java.lang.Object[r2]     // Catch: java.lang.Throwable -> L3b
            java.lang.Object r4 = r4.invoke(r7, r5)     // Catch: java.lang.Throwable -> L3b
            java.lang.Throwable r4 = (java.lang.Throwable) r4     // Catch: java.lang.Throwable -> L3b
            goto L3c
        L3b:
            r4 = r0
        L3c:
            if (r4 == 0) goto L3f
            return r4
        L3f:
            int r3 = r3 + 1
            goto L12
        L42:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.lang3.exception.ExceptionUtils.getCause(java.lang.Throwable, java.lang.String[]):java.lang.Throwable");
    }

    public static int indexOfThrowable(Throwable th, Class<?> cls, int i) {
        return a(th, cls, i, false);
    }

    public static int indexOfType(Throwable th, Class<?> cls, int i) {
        return a(th, cls, i, true);
    }

    public static void printRootCauseStackTrace(Throwable th, PrintStream printStream) {
        if (th == null) {
            return;
        }
        Validate.isTrue(printStream != null, "The PrintStream must not be null", new Object[0]);
        for (String str : getRootCauseStackTrace(th)) {
            printStream.println(str);
        }
        printStream.flush();
    }

    public static List<String> a(Throwable th) {
        StringTokenizer stringTokenizer = new StringTokenizer(getStackTrace(th), System.lineSeparator());
        ArrayList arrayList = new ArrayList();
        boolean z = false;
        while (stringTokenizer.hasMoreTokens()) {
            String strNextToken = stringTokenizer.nextToken();
            int iIndexOf = strNextToken.indexOf("at");
            if (iIndexOf != -1 && strNextToken.substring(0, iIndexOf).trim().isEmpty()) {
                z = true;
                arrayList.add(strNextToken);
            } else if (z) {
                break;
            }
        }
        return arrayList;
    }

    public static void printRootCauseStackTrace(Throwable th, PrintWriter printWriter) {
        if (th == null) {
            return;
        }
        Validate.isTrue(printWriter != null, "The PrintWriter must not be null", new Object[0]);
        for (String str : getRootCauseStackTrace(th)) {
            printWriter.println(str);
        }
        printWriter.flush();
    }
}
