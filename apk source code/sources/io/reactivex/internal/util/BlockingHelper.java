package io.reactivex.internal.util;

import defpackage.g9;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.NonBlockingThread;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.CountDownLatch;

/* loaded from: classes.dex */
public final class BlockingHelper {
    public BlockingHelper() {
        throw new IllegalStateException("No instances!");
    }

    public static void awaitForComplete(CountDownLatch countDownLatch, Disposable disposable) throws InterruptedException {
        if (countDownLatch.getCount() == 0) {
            return;
        }
        try {
            verifyNonBlocking();
            countDownLatch.await();
        } catch (InterruptedException e) {
            disposable.dispose();
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Interrupted while waiting for subscription to complete.", e);
        }
    }

    public static void verifyNonBlocking() {
        if (RxJavaPlugins.isFailOnNonBlockingScheduler()) {
            if ((Thread.currentThread() instanceof NonBlockingThread) || RxJavaPlugins.onBeforeBlocking()) {
                StringBuilder sbA = g9.a("Attempt to block on a Scheduler ");
                sbA.append(Thread.currentThread().getName());
                sbA.append(" that doesn't support blocking operators as they may lead to deadlock");
                throw new IllegalStateException(sbA.toString());
            }
        }
    }
}
