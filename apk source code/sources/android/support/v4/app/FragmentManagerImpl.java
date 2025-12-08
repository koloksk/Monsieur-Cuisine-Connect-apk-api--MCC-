package android.support.v4.app;

import android.R;
import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.arch.lifecycle.ViewModelStore;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.ArraySet;
import android.support.v4.util.DebugUtils;
import android.support.v4.util.LogWriter;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import defpackage.g9;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes.dex */
public final class FragmentManagerImpl extends FragmentManager implements LayoutInflater.Factory2 {
    public static final int ANIM_DUR = 220;
    public static final int ANIM_STYLE_CLOSE_ENTER = 3;
    public static final int ANIM_STYLE_CLOSE_EXIT = 4;
    public static final int ANIM_STYLE_FADE_ENTER = 5;
    public static final int ANIM_STYLE_FADE_EXIT = 6;
    public static final int ANIM_STYLE_OPEN_ENTER = 1;
    public static final int ANIM_STYLE_OPEN_EXIT = 2;
    public static boolean DEBUG = false;
    public static final String TAG = "FragmentManager";
    public static final String TARGET_REQUEST_CODE_STATE_TAG = "android:target_req_state";
    public static final String TARGET_STATE_TAG = "android:target_state";
    public static final String USER_VISIBLE_HINT_TAG = "android:user_visible_hint";
    public static final String VIEW_STATE_TAG = "android:view_state";
    public static Field sAnimationListenerField;
    public SparseArray<Fragment> mActive;
    public ArrayList<Integer> mAvailBackStackIndices;
    public ArrayList<BackStackRecord> mBackStack;
    public ArrayList<FragmentManager.OnBackStackChangedListener> mBackStackChangeListeners;
    public ArrayList<BackStackRecord> mBackStackIndices;
    public FragmentContainer mContainer;
    public ArrayList<Fragment> mCreatedMenus;
    public boolean mDestroyed;
    public boolean mExecutingActions;
    public boolean mHavePendingDeferredStart;
    public FragmentHostCallback mHost;
    public boolean mNeedMenuInvalidate;
    public String mNoTransactionsBecause;
    public Fragment mParent;
    public ArrayList<OpGenerator> mPendingActions;
    public ArrayList<StartEnterTransitionListener> mPostponedTransactions;
    public Fragment mPrimaryNav;
    public FragmentManagerNonConfig mSavedNonConfig;
    public boolean mStateSaved;
    public ArrayList<Fragment> mTmpAddedFragments;
    public ArrayList<Boolean> mTmpIsPop;
    public ArrayList<BackStackRecord> mTmpRecords;
    public static final Interpolator DECELERATE_QUINT = new DecelerateInterpolator(2.5f);
    public static final Interpolator DECELERATE_CUBIC = new DecelerateInterpolator(1.5f);
    public static final Interpolator ACCELERATE_QUINT = new AccelerateInterpolator(2.5f);
    public static final Interpolator ACCELERATE_CUBIC = new AccelerateInterpolator(1.5f);
    public int mNextFragmentIndex = 0;
    public final ArrayList<Fragment> mAdded = new ArrayList<>();
    public final CopyOnWriteArrayList<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> mLifecycleCallbacks = new CopyOnWriteArrayList<>();
    public int mCurState = 0;
    public Bundle mStateBundle = null;
    public SparseArray<Parcelable> mStateArray = null;
    public Runnable mExecCommit = new Runnable() { // from class: android.support.v4.app.FragmentManagerImpl.1
        @Override // java.lang.Runnable
        public void run() {
            FragmentManagerImpl.this.execPendingActions();
        }
    };

    public static class AnimateOnHWLayerIfNeededListener extends AnimationListenerWrapper {
        public View mView;

        public AnimateOnHWLayerIfNeededListener(View view2, Animation.AnimationListener animationListener) {
            super(animationListener);
            this.mView = view2;
        }

        @Override // android.support.v4.app.FragmentManagerImpl.AnimationListenerWrapper, android.view.animation.Animation.AnimationListener
        @CallSuper
        public void onAnimationEnd(Animation animation) {
            if (ViewCompat.isAttachedToWindow(this.mView) || Build.VERSION.SDK_INT >= 24) {
                this.mView.post(new Runnable() { // from class: android.support.v4.app.FragmentManagerImpl.AnimateOnHWLayerIfNeededListener.1
                    @Override // java.lang.Runnable
                    public void run() {
                        AnimateOnHWLayerIfNeededListener.this.mView.setLayerType(0, null);
                    }
                });
            } else {
                this.mView.setLayerType(0, null);
            }
            super.onAnimationEnd(animation);
        }
    }

    public static class AnimationListenerWrapper implements Animation.AnimationListener {
        public final Animation.AnimationListener mWrapped;

        @Override // android.view.animation.Animation.AnimationListener
        @CallSuper
        public void onAnimationEnd(Animation animation) {
            Animation.AnimationListener animationListener = this.mWrapped;
            if (animationListener != null) {
                animationListener.onAnimationEnd(animation);
            }
        }

        @Override // android.view.animation.Animation.AnimationListener
        @CallSuper
        public void onAnimationRepeat(Animation animation) {
            Animation.AnimationListener animationListener = this.mWrapped;
            if (animationListener != null) {
                animationListener.onAnimationRepeat(animation);
            }
        }

        @Override // android.view.animation.Animation.AnimationListener
        @CallSuper
        public void onAnimationStart(Animation animation) {
            Animation.AnimationListener animationListener = this.mWrapped;
            if (animationListener != null) {
                animationListener.onAnimationStart(animation);
            }
        }

        public AnimationListenerWrapper(Animation.AnimationListener animationListener) {
            this.mWrapped = animationListener;
        }
    }

    public static class AnimatorOnHWLayerIfNeededListener extends AnimatorListenerAdapter {
        public View mView;

        public AnimatorOnHWLayerIfNeededListener(View view2) {
            this.mView = view2;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            this.mView.setLayerType(0, null);
            animator.removeListener(this);
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationStart(Animator animator) {
            this.mView.setLayerType(2, null);
        }
    }

    public static class FragmentTag {
        public static final int[] Fragment = {R.attr.name, R.attr.id, R.attr.tag};
        public static final int Fragment_id = 1;
        public static final int Fragment_name = 0;
        public static final int Fragment_tag = 2;
    }

    public interface OpGenerator {
        boolean generateOps(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2);
    }

    public class PopBackStackState implements OpGenerator {
        public final int mFlags;
        public final int mId;
        public final String mName;

        public PopBackStackState(String str, int i, int i2) {
            this.mName = str;
            this.mId = i;
            this.mFlags = i2;
        }

        @Override // android.support.v4.app.FragmentManagerImpl.OpGenerator
        public boolean generateOps(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2) {
            FragmentManager fragmentManagerPeekChildFragmentManager;
            Fragment fragment2 = FragmentManagerImpl.this.mPrimaryNav;
            if (fragment2 == null || this.mId >= 0 || this.mName != null || (fragmentManagerPeekChildFragmentManager = fragment2.peekChildFragmentManager()) == null || !fragmentManagerPeekChildFragmentManager.popBackStackImmediate()) {
                return FragmentManagerImpl.this.popBackStackState(arrayList, arrayList2, this.mName, this.mId, this.mFlags);
            }
            return false;
        }
    }

    public static class StartEnterTransitionListener implements Fragment.OnStartEnterTransitionListener {
        public final boolean mIsBack;
        public int mNumPostponed;
        public final BackStackRecord mRecord;

        public StartEnterTransitionListener(BackStackRecord backStackRecord, boolean z) {
            this.mIsBack = z;
            this.mRecord = backStackRecord;
        }

        public void cancelTransaction() {
            BackStackRecord backStackRecord = this.mRecord;
            backStackRecord.mManager.completeExecute(backStackRecord, this.mIsBack, false, false);
        }

        public void completeTransaction() {
            boolean z = this.mNumPostponed > 0;
            FragmentManagerImpl fragmentManagerImpl = this.mRecord.mManager;
            int size = fragmentManagerImpl.mAdded.size();
            for (int i = 0; i < size; i++) {
                Fragment fragment2 = fragmentManagerImpl.mAdded.get(i);
                fragment2.setOnStartEnterTransitionListener(null);
                if (z && fragment2.isPostponed()) {
                    fragment2.startPostponedEnterTransition();
                }
            }
            BackStackRecord backStackRecord = this.mRecord;
            backStackRecord.mManager.completeExecute(backStackRecord, this.mIsBack, !z, true);
        }

        public boolean isReady() {
            return this.mNumPostponed == 0;
        }

        @Override // android.support.v4.app.Fragment.OnStartEnterTransitionListener
        public void onStartEnterTransition() {
            int i = this.mNumPostponed - 1;
            this.mNumPostponed = i;
            if (i != 0) {
                return;
            }
            this.mRecord.mManager.scheduleCommit();
        }

        @Override // android.support.v4.app.Fragment.OnStartEnterTransitionListener
        public void startListening() {
            this.mNumPostponed++;
        }
    }

    private void addAddedFragments(ArraySet<Fragment> arraySet) throws NoSuchFieldException, Resources.NotFoundException {
        int i = this.mCurState;
        if (i < 1) {
            return;
        }
        int iMin = Math.min(i, 4);
        int size = this.mAdded.size();
        for (int i2 = 0; i2 < size; i2++) {
            Fragment fragment2 = this.mAdded.get(i2);
            if (fragment2.mState < iMin) {
                moveToState(fragment2, iMin, fragment2.getNextAnim(), fragment2.getNextTransition(), false);
                if (fragment2.mView != null && !fragment2.mHidden && fragment2.mIsNewlyAdded) {
                    arraySet.add(fragment2);
                }
            }
        }
    }

    private void animateRemoveFragment(@NonNull final Fragment fragment2, @NonNull AnimationOrAnimator animationOrAnimator, int i) throws NoSuchFieldException {
        final View view2 = fragment2.mView;
        final ViewGroup viewGroup = fragment2.mContainer;
        viewGroup.startViewTransition(view2);
        fragment2.setStateAfterAnimating(i);
        if (animationOrAnimator.animation != null) {
            EndViewTransitionAnimator endViewTransitionAnimator = new EndViewTransitionAnimator(animationOrAnimator.animation, viewGroup, view2);
            fragment2.setAnimatingAway(fragment2.mView);
            endViewTransitionAnimator.setAnimationListener(new AnimationListenerWrapper(getAnimationListener(endViewTransitionAnimator)) { // from class: android.support.v4.app.FragmentManagerImpl.2
                @Override // android.support.v4.app.FragmentManagerImpl.AnimationListenerWrapper, android.view.animation.Animation.AnimationListener
                public void onAnimationEnd(Animation animation) {
                    super.onAnimationEnd(animation);
                    viewGroup.post(new Runnable() { // from class: android.support.v4.app.FragmentManagerImpl.2.1
                        @Override // java.lang.Runnable
                        public void run() throws NoSuchFieldException, Resources.NotFoundException {
                            if (fragment2.getAnimatingAway() != null) {
                                fragment2.setAnimatingAway(null);
                                AnonymousClass2 anonymousClass2 = AnonymousClass2.this;
                                FragmentManagerImpl fragmentManagerImpl = FragmentManagerImpl.this;
                                Fragment fragment3 = fragment2;
                                fragmentManagerImpl.moveToState(fragment3, fragment3.getStateAfterAnimating(), 0, 0, false);
                            }
                        }
                    });
                }
            });
            setHWLayerAnimListenerIfAlpha(view2, animationOrAnimator);
            fragment2.mView.startAnimation(endViewTransitionAnimator);
            return;
        }
        Animator animator = animationOrAnimator.animator;
        fragment2.setAnimator(animator);
        animator.addListener(new AnimatorListenerAdapter() { // from class: android.support.v4.app.FragmentManagerImpl.3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator2) throws NoSuchFieldException, Resources.NotFoundException {
                viewGroup.endViewTransition(view2);
                Animator animator3 = fragment2.getAnimator();
                fragment2.setAnimator(null);
                if (animator3 == null || viewGroup.indexOfChild(view2) >= 0) {
                    return;
                }
                FragmentManagerImpl fragmentManagerImpl = FragmentManagerImpl.this;
                Fragment fragment3 = fragment2;
                fragmentManagerImpl.moveToState(fragment3, fragment3.getStateAfterAnimating(), 0, 0, false);
            }
        });
        animator.setTarget(fragment2.mView);
        setHWLayerAnimListenerIfAlpha(fragment2.mView, animationOrAnimator);
        animator.start();
    }

    private void burpActive() {
        SparseArray<Fragment> sparseArray = this.mActive;
        if (sparseArray != null) {
            for (int size = sparseArray.size() - 1; size >= 0; size--) {
                if (this.mActive.valueAt(size) == null) {
                    SparseArray<Fragment> sparseArray2 = this.mActive;
                    sparseArray2.delete(sparseArray2.keyAt(size));
                }
            }
        }
    }

    private void checkStateLoss() {
        if (this.mStateSaved) {
            throw new IllegalStateException("Can not perform this action after onSaveInstanceState");
        }
        if (this.mNoTransactionsBecause == null) {
            return;
        }
        StringBuilder sbA = g9.a("Can not perform this action inside of ");
        sbA.append(this.mNoTransactionsBecause);
        throw new IllegalStateException(sbA.toString());
    }

    private void cleanupExec() {
        this.mExecutingActions = false;
        this.mTmpIsPop.clear();
        this.mTmpRecords.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void completeExecute(BackStackRecord backStackRecord, boolean z, boolean z2, boolean z3) {
        if (z) {
            backStackRecord.executePopOps(z3);
        } else {
            backStackRecord.executeOps();
        }
        ArrayList arrayList = new ArrayList(1);
        ArrayList arrayList2 = new ArrayList(1);
        arrayList.add(backStackRecord);
        arrayList2.add(Boolean.valueOf(z));
        if (z2) {
            FragmentTransition.startTransitions(this, arrayList, arrayList2, 0, 1, true);
        }
        if (z3) {
            moveToState(this.mCurState, true);
        }
        SparseArray<Fragment> sparseArray = this.mActive;
        if (sparseArray != null) {
            int size = sparseArray.size();
            for (int i = 0; i < size; i++) {
                Fragment fragmentValueAt = this.mActive.valueAt(i);
                if (fragmentValueAt != null && fragmentValueAt.mView != null && fragmentValueAt.mIsNewlyAdded && backStackRecord.interactsWith(fragmentValueAt.mContainerId)) {
                    float f = fragmentValueAt.mPostponedAlpha;
                    if (f > 0.0f) {
                        fragmentValueAt.mView.setAlpha(f);
                    }
                    if (z3) {
                        fragmentValueAt.mPostponedAlpha = 0.0f;
                    } else {
                        fragmentValueAt.mPostponedAlpha = -1.0f;
                        fragmentValueAt.mIsNewlyAdded = false;
                    }
                }
            }
        }
    }

    private void dispatchStateChange(int i) {
        try {
            this.mExecutingActions = true;
            moveToState(i, false);
            this.mExecutingActions = false;
            execPendingActions();
        } catch (Throwable th) {
            this.mExecutingActions = false;
            throw th;
        }
    }

    private void endAnimatingAwayFragments() throws NoSuchFieldException, Resources.NotFoundException {
        SparseArray<Fragment> sparseArray = this.mActive;
        int size = sparseArray == null ? 0 : sparseArray.size();
        for (int i = 0; i < size; i++) {
            Fragment fragmentValueAt = this.mActive.valueAt(i);
            if (fragmentValueAt != null) {
                if (fragmentValueAt.getAnimatingAway() != null) {
                    int stateAfterAnimating = fragmentValueAt.getStateAfterAnimating();
                    View animatingAway = fragmentValueAt.getAnimatingAway();
                    Animation animation = animatingAway.getAnimation();
                    if (animation != null) {
                        animation.cancel();
                        animatingAway.clearAnimation();
                    }
                    fragmentValueAt.setAnimatingAway(null);
                    moveToState(fragmentValueAt, stateAfterAnimating, 0, 0, false);
                } else if (fragmentValueAt.getAnimator() != null) {
                    fragmentValueAt.getAnimator().end();
                }
            }
        }
    }

    private void ensureExecReady(boolean z) {
        if (this.mExecutingActions) {
            throw new IllegalStateException("FragmentManager is already executing transactions");
        }
        if (this.mHost == null) {
            throw new IllegalStateException("Fragment host has been destroyed");
        }
        if (Looper.myLooper() != this.mHost.getHandler().getLooper()) {
            throw new IllegalStateException("Must be called from main thread of fragment host");
        }
        if (!z) {
            checkStateLoss();
        }
        if (this.mTmpRecords == null) {
            this.mTmpRecords = new ArrayList<>();
            this.mTmpIsPop = new ArrayList<>();
        }
        this.mExecutingActions = true;
        try {
            executePostponedTransaction(null, null);
        } finally {
            this.mExecutingActions = false;
        }
    }

    public static void executeOps(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2, int i, int i2) {
        while (i < i2) {
            BackStackRecord backStackRecord = arrayList.get(i);
            if (arrayList2.get(i).booleanValue()) {
                backStackRecord.bumpBackStackNesting(-1);
                backStackRecord.executePopOps(i == i2 + (-1));
            } else {
                backStackRecord.bumpBackStackNesting(1);
                backStackRecord.executeOps();
            }
            i++;
        }
    }

    private void executeOpsTogether(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2, int i, int i2) throws NoSuchFieldException, Resources.NotFoundException {
        int i3;
        int i4;
        int i5 = i;
        boolean z = arrayList.get(i5).mReorderingAllowed;
        ArrayList<Fragment> arrayList3 = this.mTmpAddedFragments;
        if (arrayList3 == null) {
            this.mTmpAddedFragments = new ArrayList<>();
        } else {
            arrayList3.clear();
        }
        this.mTmpAddedFragments.addAll(this.mAdded);
        Fragment primaryNavigationFragment = getPrimaryNavigationFragment();
        boolean z2 = false;
        for (int i6 = i5; i6 < i2; i6++) {
            BackStackRecord backStackRecord = arrayList.get(i6);
            primaryNavigationFragment = !arrayList2.get(i6).booleanValue() ? backStackRecord.expandOps(this.mTmpAddedFragments, primaryNavigationFragment) : backStackRecord.trackAddedFragmentsInPop(this.mTmpAddedFragments, primaryNavigationFragment);
            z2 = z2 || backStackRecord.mAddToBackStack;
        }
        this.mTmpAddedFragments.clear();
        if (!z) {
            FragmentTransition.startTransitions(this, arrayList, arrayList2, i, i2, false);
        }
        executeOps(arrayList, arrayList2, i, i2);
        if (z) {
            ArraySet<Fragment> arraySet = new ArraySet<>();
            addAddedFragments(arraySet);
            int iPostponePostponableTransactions = postponePostponableTransactions(arrayList, arrayList2, i, i2, arraySet);
            makeRemovedFragmentsInvisible(arraySet);
            i3 = iPostponePostponableTransactions;
        } else {
            i3 = i2;
        }
        if (i3 != i5 && z) {
            FragmentTransition.startTransitions(this, arrayList, arrayList2, i, i3, true);
            moveToState(this.mCurState, true);
        }
        while (i5 < i2) {
            BackStackRecord backStackRecord2 = arrayList.get(i5);
            if (arrayList2.get(i5).booleanValue() && (i4 = backStackRecord2.mIndex) >= 0) {
                freeBackStackIndex(i4);
                backStackRecord2.mIndex = -1;
            }
            backStackRecord2.runOnCommitRunnables();
            i5++;
        }
        if (z2) {
            reportBackStackChanged();
        }
    }

    private void executePostponedTransaction(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2) {
        int iIndexOf;
        int iIndexOf2;
        ArrayList<StartEnterTransitionListener> arrayList3 = this.mPostponedTransactions;
        int size = arrayList3 == null ? 0 : arrayList3.size();
        int i = 0;
        while (i < size) {
            StartEnterTransitionListener startEnterTransitionListener = this.mPostponedTransactions.get(i);
            if (arrayList != null && !startEnterTransitionListener.mIsBack && (iIndexOf2 = arrayList.indexOf(startEnterTransitionListener.mRecord)) != -1 && arrayList2.get(iIndexOf2).booleanValue()) {
                startEnterTransitionListener.cancelTransaction();
            } else if (startEnterTransitionListener.isReady() || (arrayList != null && startEnterTransitionListener.mRecord.interactsWith(arrayList, 0, arrayList.size()))) {
                this.mPostponedTransactions.remove(i);
                i--;
                size--;
                if (arrayList == null || startEnterTransitionListener.mIsBack || (iIndexOf = arrayList.indexOf(startEnterTransitionListener.mRecord)) == -1 || !arrayList2.get(iIndexOf).booleanValue()) {
                    startEnterTransitionListener.completeTransaction();
                } else {
                    startEnterTransitionListener.cancelTransaction();
                }
            }
            i++;
        }
    }

    private Fragment findFragmentUnder(Fragment fragment2) {
        ViewGroup viewGroup = fragment2.mContainer;
        View view2 = fragment2.mView;
        if (viewGroup != null && view2 != null) {
            for (int iIndexOf = this.mAdded.indexOf(fragment2) - 1; iIndexOf >= 0; iIndexOf--) {
                Fragment fragment3 = this.mAdded.get(iIndexOf);
                if (fragment3.mContainer == viewGroup && fragment3.mView != null) {
                    return fragment3;
                }
            }
        }
        return null;
    }

    private void forcePostponedTransactions() {
        if (this.mPostponedTransactions != null) {
            while (!this.mPostponedTransactions.isEmpty()) {
                this.mPostponedTransactions.remove(0).completeTransaction();
            }
        }
    }

    private boolean generateOpsForPendingActions(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2) {
        synchronized (this) {
            if (this.mPendingActions != null && this.mPendingActions.size() != 0) {
                int size = this.mPendingActions.size();
                boolean zGenerateOps = false;
                for (int i = 0; i < size; i++) {
                    zGenerateOps |= this.mPendingActions.get(i).generateOps(arrayList, arrayList2);
                }
                this.mPendingActions.clear();
                this.mHost.getHandler().removeCallbacks(this.mExecCommit);
                return zGenerateOps;
            }
            return false;
        }
    }

    public static Animation.AnimationListener getAnimationListener(Animation animation) throws NoSuchFieldException {
        try {
            if (sAnimationListenerField == null) {
                Field declaredField = Animation.class.getDeclaredField("mListener");
                sAnimationListenerField = declaredField;
                declaredField.setAccessible(true);
            }
            return (Animation.AnimationListener) sAnimationListenerField.get(animation);
        } catch (IllegalAccessException e) {
            Log.e("FragmentManager", "Cannot access Animation's mListener field", e);
            return null;
        } catch (NoSuchFieldException e2) {
            Log.e("FragmentManager", "No field with the name mListener is found in Animation class", e2);
            return null;
        }
    }

    public static AnimationOrAnimator makeFadeAnimation(Context context, float f, float f2) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(f, f2);
        alphaAnimation.setInterpolator(DECELERATE_CUBIC);
        alphaAnimation.setDuration(220L);
        return new AnimationOrAnimator(alphaAnimation);
    }

    public static AnimationOrAnimator makeOpenCloseAnimation(Context context, float f, float f2, float f3, float f4) {
        AnimationSet animationSet = new AnimationSet(false);
        ScaleAnimation scaleAnimation = new ScaleAnimation(f, f2, f, f2, 1, 0.5f, 1, 0.5f);
        scaleAnimation.setInterpolator(DECELERATE_QUINT);
        scaleAnimation.setDuration(220L);
        animationSet.addAnimation(scaleAnimation);
        AlphaAnimation alphaAnimation = new AlphaAnimation(f3, f4);
        alphaAnimation.setInterpolator(DECELERATE_CUBIC);
        alphaAnimation.setDuration(220L);
        animationSet.addAnimation(alphaAnimation);
        return new AnimationOrAnimator(animationSet);
    }

    private void makeRemovedFragmentsInvisible(ArraySet<Fragment> arraySet) {
        int size = arraySet.size();
        for (int i = 0; i < size; i++) {
            Fragment fragmentValueAt = arraySet.valueAt(i);
            if (!fragmentValueAt.mAdded) {
                View view2 = fragmentValueAt.getView();
                fragmentValueAt.mPostponedAlpha = view2.getAlpha();
                view2.setAlpha(0.0f);
            }
        }
    }

    public static boolean modifiesAlpha(AnimationOrAnimator animationOrAnimator) {
        Animation animation = animationOrAnimator.animation;
        if (animation instanceof AlphaAnimation) {
            return true;
        }
        if (!(animation instanceof AnimationSet)) {
            return modifiesAlpha(animationOrAnimator.animator);
        }
        List<Animation> animations = ((AnimationSet) animation).getAnimations();
        for (int i = 0; i < animations.size(); i++) {
            if (animations.get(i) instanceof AlphaAnimation) {
                return true;
            }
        }
        return false;
    }

    private int postponePostponableTransactions(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2, int i, int i2, ArraySet<Fragment> arraySet) throws NoSuchFieldException, Resources.NotFoundException {
        int i3 = i2;
        for (int i4 = i2 - 1; i4 >= i; i4--) {
            BackStackRecord backStackRecord = arrayList.get(i4);
            boolean zBooleanValue = arrayList2.get(i4).booleanValue();
            if (backStackRecord.isPostponed() && !backStackRecord.interactsWith(arrayList, i4 + 1, i2)) {
                if (this.mPostponedTransactions == null) {
                    this.mPostponedTransactions = new ArrayList<>();
                }
                StartEnterTransitionListener startEnterTransitionListener = new StartEnterTransitionListener(backStackRecord, zBooleanValue);
                this.mPostponedTransactions.add(startEnterTransitionListener);
                backStackRecord.setOnStartPostponedListener(startEnterTransitionListener);
                if (zBooleanValue) {
                    backStackRecord.executeOps();
                } else {
                    backStackRecord.executePopOps(false);
                }
                i3--;
                if (i4 != i3) {
                    arrayList.remove(i4);
                    arrayList.add(i3, backStackRecord);
                }
                addAddedFragments(arraySet);
            }
        }
        return i3;
    }

    private void removeRedundantOperationsAndExecute(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2) throws NoSuchFieldException, Resources.NotFoundException {
        if (arrayList == null || arrayList.isEmpty()) {
            return;
        }
        if (arrayList2 == null || arrayList.size() != arrayList2.size()) {
            throw new IllegalStateException("Internal error with the back stack records");
        }
        executePostponedTransaction(arrayList, arrayList2);
        int size = arrayList.size();
        int i = 0;
        int i2 = 0;
        while (i < size) {
            if (!arrayList.get(i).mReorderingAllowed) {
                if (i2 != i) {
                    executeOpsTogether(arrayList, arrayList2, i2, i);
                }
                i2 = i + 1;
                if (arrayList2.get(i).booleanValue()) {
                    while (i2 < size && arrayList2.get(i2).booleanValue() && !arrayList.get(i2).mReorderingAllowed) {
                        i2++;
                    }
                }
                executeOpsTogether(arrayList, arrayList2, i, i2);
                i = i2 - 1;
            }
            i++;
        }
        if (i2 != size) {
            executeOpsTogether(arrayList, arrayList2, i2, size);
        }
    }

    public static int reverseTransit(int i) {
        if (i == 4097) {
            return 8194;
        }
        if (i == 4099) {
            return FragmentTransaction.TRANSIT_FRAGMENT_FADE;
        }
        if (i != 8194) {
            return 0;
        }
        return FragmentTransaction.TRANSIT_FRAGMENT_OPEN;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void scheduleCommit() {
        synchronized (this) {
            boolean z = false;
            boolean z2 = (this.mPostponedTransactions == null || this.mPostponedTransactions.isEmpty()) ? false : true;
            if (this.mPendingActions != null && this.mPendingActions.size() == 1) {
                z = true;
            }
            if (z2 || z) {
                this.mHost.getHandler().removeCallbacks(this.mExecCommit);
                this.mHost.getHandler().post(this.mExecCommit);
            }
        }
    }

    public static void setHWLayerAnimListenerIfAlpha(View view2, AnimationOrAnimator animationOrAnimator) throws NoSuchFieldException {
        if (view2 == null || animationOrAnimator == null || !shouldRunOnHWLayer(view2, animationOrAnimator)) {
            return;
        }
        Animator animator = animationOrAnimator.animator;
        if (animator != null) {
            animator.addListener(new AnimatorOnHWLayerIfNeededListener(view2));
            return;
        }
        Animation.AnimationListener animationListener = getAnimationListener(animationOrAnimator.animation);
        view2.setLayerType(2, null);
        animationOrAnimator.animation.setAnimationListener(new AnimateOnHWLayerIfNeededListener(view2, animationListener));
    }

    public static void setRetaining(FragmentManagerNonConfig fragmentManagerNonConfig) {
        if (fragmentManagerNonConfig == null) {
            return;
        }
        List<Fragment> fragments = fragmentManagerNonConfig.getFragments();
        if (fragments != null) {
            Iterator<Fragment> it = fragments.iterator();
            while (it.hasNext()) {
                it.next().mRetaining = true;
            }
        }
        List<FragmentManagerNonConfig> childNonConfigs = fragmentManagerNonConfig.getChildNonConfigs();
        if (childNonConfigs != null) {
            Iterator<FragmentManagerNonConfig> it2 = childNonConfigs.iterator();
            while (it2.hasNext()) {
                setRetaining(it2.next());
            }
        }
    }

    public static boolean shouldRunOnHWLayer(View view2, AnimationOrAnimator animationOrAnimator) {
        return view2 != null && animationOrAnimator != null && view2.getLayerType() == 0 && ViewCompat.hasOverlappingRendering(view2) && modifiesAlpha(animationOrAnimator);
    }

    private void throwException(RuntimeException runtimeException) {
        Log.e("FragmentManager", runtimeException.getMessage());
        Log.e("FragmentManager", "Activity state:");
        PrintWriter printWriter = new PrintWriter(new LogWriter("FragmentManager"));
        FragmentHostCallback fragmentHostCallback = this.mHost;
        if (fragmentHostCallback != null) {
            try {
                fragmentHostCallback.onDump("  ", null, printWriter, new String[0]);
                throw runtimeException;
            } catch (Exception e) {
                Log.e("FragmentManager", "Failed dumping state", e);
                throw runtimeException;
            }
        }
        try {
            dump("  ", null, printWriter, new String[0]);
            throw runtimeException;
        } catch (Exception e2) {
            Log.e("FragmentManager", "Failed dumping state", e2);
            throw runtimeException;
        }
    }

    public static int transitToStyleIndex(int i, boolean z) {
        if (i == 4097) {
            return z ? 1 : 2;
        }
        if (i == 4099) {
            return z ? 5 : 6;
        }
        if (i != 8194) {
            return -1;
        }
        return z ? 3 : 4;
    }

    public void addBackStackState(BackStackRecord backStackRecord) {
        if (this.mBackStack == null) {
            this.mBackStack = new ArrayList<>();
        }
        this.mBackStack.add(backStackRecord);
    }

    public void addFragment(Fragment fragment2, boolean z) {
        if (DEBUG) {
            g9.a("add: ", fragment2, "FragmentManager");
        }
        makeActive(fragment2);
        if (fragment2.mDetached) {
            return;
        }
        if (this.mAdded.contains(fragment2)) {
            throw new IllegalStateException("Fragment already added: " + fragment2);
        }
        synchronized (this.mAdded) {
            this.mAdded.add(fragment2);
        }
        fragment2.mAdded = true;
        fragment2.mRemoving = false;
        if (fragment2.mView == null) {
            fragment2.mHiddenChanged = false;
        }
        if (fragment2.mHasMenu && fragment2.mMenuVisible) {
            this.mNeedMenuInvalidate = true;
        }
        if (z) {
            moveToState(fragment2);
        }
    }

    @Override // android.support.v4.app.FragmentManager
    public void addOnBackStackChangedListener(FragmentManager.OnBackStackChangedListener onBackStackChangedListener) {
        if (this.mBackStackChangeListeners == null) {
            this.mBackStackChangeListeners = new ArrayList<>();
        }
        this.mBackStackChangeListeners.add(onBackStackChangedListener);
    }

    public int allocBackStackIndex(BackStackRecord backStackRecord) {
        synchronized (this) {
            if (this.mAvailBackStackIndices != null && this.mAvailBackStackIndices.size() > 0) {
                int iIntValue = this.mAvailBackStackIndices.remove(this.mAvailBackStackIndices.size() - 1).intValue();
                if (DEBUG) {
                    Log.v("FragmentManager", "Adding back stack index " + iIntValue + " with " + backStackRecord);
                }
                this.mBackStackIndices.set(iIntValue, backStackRecord);
                return iIntValue;
            }
            if (this.mBackStackIndices == null) {
                this.mBackStackIndices = new ArrayList<>();
            }
            int size = this.mBackStackIndices.size();
            if (DEBUG) {
                Log.v("FragmentManager", "Setting back stack index " + size + " to " + backStackRecord);
            }
            this.mBackStackIndices.add(backStackRecord);
            return size;
        }
    }

    public void attachController(FragmentHostCallback fragmentHostCallback, FragmentContainer fragmentContainer, Fragment fragment2) {
        if (this.mHost != null) {
            throw new IllegalStateException("Already attached");
        }
        this.mHost = fragmentHostCallback;
        this.mContainer = fragmentContainer;
        this.mParent = fragment2;
    }

    public void attachFragment(Fragment fragment2) {
        if (DEBUG) {
            g9.a("attach: ", fragment2, "FragmentManager");
        }
        if (fragment2.mDetached) {
            fragment2.mDetached = false;
            if (fragment2.mAdded) {
                return;
            }
            if (this.mAdded.contains(fragment2)) {
                throw new IllegalStateException("Fragment already added: " + fragment2);
            }
            if (DEBUG) {
                g9.a("add from attach: ", fragment2, "FragmentManager");
            }
            synchronized (this.mAdded) {
                this.mAdded.add(fragment2);
            }
            fragment2.mAdded = true;
            if (fragment2.mHasMenu && fragment2.mMenuVisible) {
                this.mNeedMenuInvalidate = true;
            }
        }
    }

    @Override // android.support.v4.app.FragmentManager
    public FragmentTransaction beginTransaction() {
        return new BackStackRecord(this);
    }

    public void completeShowHideFragment(final Fragment fragment2) throws NoSuchFieldException, Resources.NotFoundException {
        Animator animator;
        if (fragment2.mView != null) {
            AnimationOrAnimator animationOrAnimatorLoadAnimation = loadAnimation(fragment2, fragment2.getNextTransition(), !fragment2.mHidden, fragment2.getNextTransitionStyle());
            if (animationOrAnimatorLoadAnimation == null || (animator = animationOrAnimatorLoadAnimation.animator) == null) {
                if (animationOrAnimatorLoadAnimation != null) {
                    setHWLayerAnimListenerIfAlpha(fragment2.mView, animationOrAnimatorLoadAnimation);
                    fragment2.mView.startAnimation(animationOrAnimatorLoadAnimation.animation);
                    animationOrAnimatorLoadAnimation.animation.start();
                }
                fragment2.mView.setVisibility((!fragment2.mHidden || fragment2.isHideReplaced()) ? 0 : 8);
                if (fragment2.isHideReplaced()) {
                    fragment2.setHideReplaced(false);
                }
            } else {
                animator.setTarget(fragment2.mView);
                if (!fragment2.mHidden) {
                    fragment2.mView.setVisibility(0);
                } else if (fragment2.isHideReplaced()) {
                    fragment2.setHideReplaced(false);
                } else {
                    final ViewGroup viewGroup = fragment2.mContainer;
                    final View view2 = fragment2.mView;
                    viewGroup.startViewTransition(view2);
                    animationOrAnimatorLoadAnimation.animator.addListener(new AnimatorListenerAdapter() { // from class: android.support.v4.app.FragmentManagerImpl.4
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animator2) {
                            viewGroup.endViewTransition(view2);
                            animator2.removeListener(this);
                            View view3 = fragment2.mView;
                            if (view3 != null) {
                                view3.setVisibility(8);
                            }
                        }
                    });
                }
                setHWLayerAnimListenerIfAlpha(fragment2.mView, animationOrAnimatorLoadAnimation);
                animationOrAnimatorLoadAnimation.animator.start();
            }
        }
        if (fragment2.mAdded && fragment2.mHasMenu && fragment2.mMenuVisible) {
            this.mNeedMenuInvalidate = true;
        }
        fragment2.mHiddenChanged = false;
        fragment2.onHiddenChanged(fragment2.mHidden);
    }

    public void detachFragment(Fragment fragment2) {
        if (DEBUG) {
            g9.a("detach: ", fragment2, "FragmentManager");
        }
        if (fragment2.mDetached) {
            return;
        }
        fragment2.mDetached = true;
        if (fragment2.mAdded) {
            if (DEBUG) {
                g9.a("remove from detach: ", fragment2, "FragmentManager");
            }
            synchronized (this.mAdded) {
                this.mAdded.remove(fragment2);
            }
            if (fragment2.mHasMenu && fragment2.mMenuVisible) {
                this.mNeedMenuInvalidate = true;
            }
            fragment2.mAdded = false;
        }
    }

    public void dispatchActivityCreated() {
        this.mStateSaved = false;
        dispatchStateChange(2);
    }

    public void dispatchConfigurationChanged(Configuration configuration) {
        for (int i = 0; i < this.mAdded.size(); i++) {
            Fragment fragment2 = this.mAdded.get(i);
            if (fragment2 != null) {
                fragment2.performConfigurationChanged(configuration);
            }
        }
    }

    public boolean dispatchContextItemSelected(MenuItem menuItem) {
        if (this.mCurState < 1) {
            return false;
        }
        for (int i = 0; i < this.mAdded.size(); i++) {
            Fragment fragment2 = this.mAdded.get(i);
            if (fragment2 != null && fragment2.performContextItemSelected(menuItem)) {
                return true;
            }
        }
        return false;
    }

    public void dispatchCreate() {
        this.mStateSaved = false;
        dispatchStateChange(1);
    }

    public boolean dispatchCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        if (this.mCurState < 1) {
            return false;
        }
        ArrayList<Fragment> arrayList = null;
        boolean z = false;
        for (int i = 0; i < this.mAdded.size(); i++) {
            Fragment fragment2 = this.mAdded.get(i);
            if (fragment2 != null && fragment2.performCreateOptionsMenu(menu, menuInflater)) {
                if (arrayList == null) {
                    arrayList = new ArrayList<>();
                }
                arrayList.add(fragment2);
                z = true;
            }
        }
        if (this.mCreatedMenus != null) {
            for (int i2 = 0; i2 < this.mCreatedMenus.size(); i2++) {
                Fragment fragment3 = this.mCreatedMenus.get(i2);
                if (arrayList == null || !arrayList.contains(fragment3)) {
                    fragment3.onDestroyOptionsMenu();
                }
            }
        }
        this.mCreatedMenus = arrayList;
        return z;
    }

    public void dispatchDestroy() {
        this.mDestroyed = true;
        execPendingActions();
        dispatchStateChange(0);
        this.mHost = null;
        this.mContainer = null;
        this.mParent = null;
    }

    public void dispatchDestroyView() {
        dispatchStateChange(1);
    }

    public void dispatchLowMemory() {
        for (int i = 0; i < this.mAdded.size(); i++) {
            Fragment fragment2 = this.mAdded.get(i);
            if (fragment2 != null) {
                fragment2.performLowMemory();
            }
        }
    }

    public void dispatchMultiWindowModeChanged(boolean z) {
        for (int size = this.mAdded.size() - 1; size >= 0; size--) {
            Fragment fragment2 = this.mAdded.get(size);
            if (fragment2 != null) {
                fragment2.performMultiWindowModeChanged(z);
            }
        }
    }

    public void dispatchOnFragmentActivityCreated(Fragment fragment2, Bundle bundle, boolean z) {
        Fragment fragment3 = this.mParent;
        if (fragment3 != null) {
            FragmentManager fragmentManager = fragment3.getFragmentManager();
            if (fragmentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) fragmentManager).dispatchOnFragmentActivityCreated(fragment2, bundle, true);
            }
        }
        Iterator<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean> next = it.next();
            if (!z || next.second.booleanValue()) {
                next.first.onFragmentActivityCreated(this, fragment2, bundle);
            }
        }
    }

    public void dispatchOnFragmentAttached(Fragment fragment2, Context context, boolean z) {
        Fragment fragment3 = this.mParent;
        if (fragment3 != null) {
            FragmentManager fragmentManager = fragment3.getFragmentManager();
            if (fragmentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) fragmentManager).dispatchOnFragmentAttached(fragment2, context, true);
            }
        }
        Iterator<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean> next = it.next();
            if (!z || next.second.booleanValue()) {
                next.first.onFragmentAttached(this, fragment2, context);
            }
        }
    }

    public void dispatchOnFragmentCreated(Fragment fragment2, Bundle bundle, boolean z) {
        Fragment fragment3 = this.mParent;
        if (fragment3 != null) {
            FragmentManager fragmentManager = fragment3.getFragmentManager();
            if (fragmentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) fragmentManager).dispatchOnFragmentCreated(fragment2, bundle, true);
            }
        }
        Iterator<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean> next = it.next();
            if (!z || next.second.booleanValue()) {
                next.first.onFragmentCreated(this, fragment2, bundle);
            }
        }
    }

    public void dispatchOnFragmentDestroyed(Fragment fragment2, boolean z) {
        Fragment fragment3 = this.mParent;
        if (fragment3 != null) {
            FragmentManager fragmentManager = fragment3.getFragmentManager();
            if (fragmentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) fragmentManager).dispatchOnFragmentDestroyed(fragment2, true);
            }
        }
        Iterator<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean> next = it.next();
            if (!z || next.second.booleanValue()) {
                next.first.onFragmentDestroyed(this, fragment2);
            }
        }
    }

    public void dispatchOnFragmentDetached(Fragment fragment2, boolean z) {
        Fragment fragment3 = this.mParent;
        if (fragment3 != null) {
            FragmentManager fragmentManager = fragment3.getFragmentManager();
            if (fragmentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) fragmentManager).dispatchOnFragmentDetached(fragment2, true);
            }
        }
        Iterator<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean> next = it.next();
            if (!z || next.second.booleanValue()) {
                next.first.onFragmentDetached(this, fragment2);
            }
        }
    }

    public void dispatchOnFragmentPaused(Fragment fragment2, boolean z) {
        Fragment fragment3 = this.mParent;
        if (fragment3 != null) {
            FragmentManager fragmentManager = fragment3.getFragmentManager();
            if (fragmentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) fragmentManager).dispatchOnFragmentPaused(fragment2, true);
            }
        }
        Iterator<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean> next = it.next();
            if (!z || next.second.booleanValue()) {
                next.first.onFragmentPaused(this, fragment2);
            }
        }
    }

    public void dispatchOnFragmentPreAttached(Fragment fragment2, Context context, boolean z) {
        Fragment fragment3 = this.mParent;
        if (fragment3 != null) {
            FragmentManager fragmentManager = fragment3.getFragmentManager();
            if (fragmentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) fragmentManager).dispatchOnFragmentPreAttached(fragment2, context, true);
            }
        }
        Iterator<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean> next = it.next();
            if (!z || next.second.booleanValue()) {
                next.first.onFragmentPreAttached(this, fragment2, context);
            }
        }
    }

    public void dispatchOnFragmentPreCreated(Fragment fragment2, Bundle bundle, boolean z) {
        Fragment fragment3 = this.mParent;
        if (fragment3 != null) {
            FragmentManager fragmentManager = fragment3.getFragmentManager();
            if (fragmentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) fragmentManager).dispatchOnFragmentPreCreated(fragment2, bundle, true);
            }
        }
        Iterator<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean> next = it.next();
            if (!z || next.second.booleanValue()) {
                next.first.onFragmentPreCreated(this, fragment2, bundle);
            }
        }
    }

    public void dispatchOnFragmentResumed(Fragment fragment2, boolean z) {
        Fragment fragment3 = this.mParent;
        if (fragment3 != null) {
            FragmentManager fragmentManager = fragment3.getFragmentManager();
            if (fragmentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) fragmentManager).dispatchOnFragmentResumed(fragment2, true);
            }
        }
        Iterator<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean> next = it.next();
            if (!z || next.second.booleanValue()) {
                next.first.onFragmentResumed(this, fragment2);
            }
        }
    }

    public void dispatchOnFragmentSaveInstanceState(Fragment fragment2, Bundle bundle, boolean z) {
        Fragment fragment3 = this.mParent;
        if (fragment3 != null) {
            FragmentManager fragmentManager = fragment3.getFragmentManager();
            if (fragmentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) fragmentManager).dispatchOnFragmentSaveInstanceState(fragment2, bundle, true);
            }
        }
        Iterator<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean> next = it.next();
            if (!z || next.second.booleanValue()) {
                next.first.onFragmentSaveInstanceState(this, fragment2, bundle);
            }
        }
    }

    public void dispatchOnFragmentStarted(Fragment fragment2, boolean z) {
        Fragment fragment3 = this.mParent;
        if (fragment3 != null) {
            FragmentManager fragmentManager = fragment3.getFragmentManager();
            if (fragmentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) fragmentManager).dispatchOnFragmentStarted(fragment2, true);
            }
        }
        Iterator<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean> next = it.next();
            if (!z || next.second.booleanValue()) {
                next.first.onFragmentStarted(this, fragment2);
            }
        }
    }

    public void dispatchOnFragmentStopped(Fragment fragment2, boolean z) {
        Fragment fragment3 = this.mParent;
        if (fragment3 != null) {
            FragmentManager fragmentManager = fragment3.getFragmentManager();
            if (fragmentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) fragmentManager).dispatchOnFragmentStopped(fragment2, true);
            }
        }
        Iterator<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean> next = it.next();
            if (!z || next.second.booleanValue()) {
                next.first.onFragmentStopped(this, fragment2);
            }
        }
    }

    public void dispatchOnFragmentViewCreated(Fragment fragment2, View view2, Bundle bundle, boolean z) {
        Fragment fragment3 = this.mParent;
        if (fragment3 != null) {
            FragmentManager fragmentManager = fragment3.getFragmentManager();
            if (fragmentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) fragmentManager).dispatchOnFragmentViewCreated(fragment2, view2, bundle, true);
            }
        }
        Iterator<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean> next = it.next();
            if (!z || next.second.booleanValue()) {
                next.first.onFragmentViewCreated(this, fragment2, view2, bundle);
            }
        }
    }

    public void dispatchOnFragmentViewDestroyed(Fragment fragment2, boolean z) {
        Fragment fragment3 = this.mParent;
        if (fragment3 != null) {
            FragmentManager fragmentManager = fragment3.getFragmentManager();
            if (fragmentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) fragmentManager).dispatchOnFragmentViewDestroyed(fragment2, true);
            }
        }
        Iterator<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> it = this.mLifecycleCallbacks.iterator();
        while (it.hasNext()) {
            Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean> next = it.next();
            if (!z || next.second.booleanValue()) {
                next.first.onFragmentViewDestroyed(this, fragment2);
            }
        }
    }

    public boolean dispatchOptionsItemSelected(MenuItem menuItem) {
        if (this.mCurState < 1) {
            return false;
        }
        for (int i = 0; i < this.mAdded.size(); i++) {
            Fragment fragment2 = this.mAdded.get(i);
            if (fragment2 != null && fragment2.performOptionsItemSelected(menuItem)) {
                return true;
            }
        }
        return false;
    }

    public void dispatchOptionsMenuClosed(Menu menu) {
        if (this.mCurState < 1) {
            return;
        }
        for (int i = 0; i < this.mAdded.size(); i++) {
            Fragment fragment2 = this.mAdded.get(i);
            if (fragment2 != null) {
                fragment2.performOptionsMenuClosed(menu);
            }
        }
    }

    public void dispatchPause() {
        dispatchStateChange(4);
    }

    public void dispatchPictureInPictureModeChanged(boolean z) {
        for (int size = this.mAdded.size() - 1; size >= 0; size--) {
            Fragment fragment2 = this.mAdded.get(size);
            if (fragment2 != null) {
                fragment2.performPictureInPictureModeChanged(z);
            }
        }
    }

    public boolean dispatchPrepareOptionsMenu(Menu menu) {
        if (this.mCurState < 1) {
            return false;
        }
        boolean z = false;
        for (int i = 0; i < this.mAdded.size(); i++) {
            Fragment fragment2 = this.mAdded.get(i);
            if (fragment2 != null && fragment2.performPrepareOptionsMenu(menu)) {
                z = true;
            }
        }
        return z;
    }

    public void dispatchReallyStop() {
        dispatchStateChange(2);
    }

    public void dispatchResume() {
        this.mStateSaved = false;
        dispatchStateChange(5);
    }

    public void dispatchStart() {
        this.mStateSaved = false;
        dispatchStateChange(4);
    }

    public void dispatchStop() {
        this.mStateSaved = true;
        dispatchStateChange(3);
    }

    public void doPendingDeferredStart() {
        LoaderManagerImpl loaderManagerImpl;
        if (this.mHavePendingDeferredStart) {
            boolean zHasRunningLoaders = false;
            for (int i = 0; i < this.mActive.size(); i++) {
                Fragment fragmentValueAt = this.mActive.valueAt(i);
                if (fragmentValueAt != null && (loaderManagerImpl = fragmentValueAt.mLoaderManager) != null) {
                    zHasRunningLoaders |= loaderManagerImpl.hasRunningLoaders();
                }
            }
            if (zHasRunningLoaders) {
                return;
            }
            this.mHavePendingDeferredStart = false;
            startPendingDeferredFragments();
        }
    }

    @Override // android.support.v4.app.FragmentManager
    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        int size;
        int size2;
        int size3;
        int size4;
        int size5;
        String strB = g9.b(str, "    ");
        SparseArray<Fragment> sparseArray = this.mActive;
        if (sparseArray != null && (size5 = sparseArray.size()) > 0) {
            printWriter.print(str);
            printWriter.print("Active Fragments in ");
            printWriter.print(Integer.toHexString(System.identityHashCode(this)));
            printWriter.println(":");
            for (int i = 0; i < size5; i++) {
                Fragment fragmentValueAt = this.mActive.valueAt(i);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i);
                printWriter.print(": ");
                printWriter.println(fragmentValueAt);
                if (fragmentValueAt != null) {
                    fragmentValueAt.dump(strB, fileDescriptor, printWriter, strArr);
                }
            }
        }
        int size6 = this.mAdded.size();
        if (size6 > 0) {
            printWriter.print(str);
            printWriter.println("Added Fragments:");
            for (int i2 = 0; i2 < size6; i2++) {
                Fragment fragment2 = this.mAdded.get(i2);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i2);
                printWriter.print(": ");
                printWriter.println(fragment2.toString());
            }
        }
        ArrayList<Fragment> arrayList = this.mCreatedMenus;
        if (arrayList != null && (size4 = arrayList.size()) > 0) {
            printWriter.print(str);
            printWriter.println("Fragments Created Menus:");
            for (int i3 = 0; i3 < size4; i3++) {
                Fragment fragment3 = this.mCreatedMenus.get(i3);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i3);
                printWriter.print(": ");
                printWriter.println(fragment3.toString());
            }
        }
        ArrayList<BackStackRecord> arrayList2 = this.mBackStack;
        if (arrayList2 != null && (size3 = arrayList2.size()) > 0) {
            printWriter.print(str);
            printWriter.println("Back Stack:");
            for (int i4 = 0; i4 < size3; i4++) {
                BackStackRecord backStackRecord = this.mBackStack.get(i4);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i4);
                printWriter.print(": ");
                printWriter.println(backStackRecord.toString());
                backStackRecord.dump(strB, fileDescriptor, printWriter, strArr);
            }
        }
        synchronized (this) {
            if (this.mBackStackIndices != null && (size2 = this.mBackStackIndices.size()) > 0) {
                printWriter.print(str);
                printWriter.println("Back Stack Indices:");
                for (int i5 = 0; i5 < size2; i5++) {
                    Object obj = (BackStackRecord) this.mBackStackIndices.get(i5);
                    printWriter.print(str);
                    printWriter.print("  #");
                    printWriter.print(i5);
                    printWriter.print(": ");
                    printWriter.println(obj);
                }
            }
            if (this.mAvailBackStackIndices != null && this.mAvailBackStackIndices.size() > 0) {
                printWriter.print(str);
                printWriter.print("mAvailBackStackIndices: ");
                printWriter.println(Arrays.toString(this.mAvailBackStackIndices.toArray()));
            }
        }
        ArrayList<OpGenerator> arrayList3 = this.mPendingActions;
        if (arrayList3 != null && (size = arrayList3.size()) > 0) {
            printWriter.print(str);
            printWriter.println("Pending Actions:");
            for (int i6 = 0; i6 < size; i6++) {
                Object obj2 = (OpGenerator) this.mPendingActions.get(i6);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i6);
                printWriter.print(": ");
                printWriter.println(obj2);
            }
        }
        printWriter.print(str);
        printWriter.println("FragmentManager misc state:");
        printWriter.print(str);
        printWriter.print("  mHost=");
        printWriter.println(this.mHost);
        printWriter.print(str);
        printWriter.print("  mContainer=");
        printWriter.println(this.mContainer);
        if (this.mParent != null) {
            printWriter.print(str);
            printWriter.print("  mParent=");
            printWriter.println(this.mParent);
        }
        printWriter.print(str);
        printWriter.print("  mCurState=");
        printWriter.print(this.mCurState);
        printWriter.print(" mStateSaved=");
        printWriter.print(this.mStateSaved);
        printWriter.print(" mDestroyed=");
        printWriter.println(this.mDestroyed);
        if (this.mNeedMenuInvalidate) {
            printWriter.print(str);
            printWriter.print("  mNeedMenuInvalidate=");
            printWriter.println(this.mNeedMenuInvalidate);
        }
        if (this.mNoTransactionsBecause != null) {
            printWriter.print(str);
            printWriter.print("  mNoTransactionsBecause=");
            printWriter.println(this.mNoTransactionsBecause);
        }
    }

    public void enqueueAction(OpGenerator opGenerator, boolean z) {
        if (!z) {
            checkStateLoss();
        }
        synchronized (this) {
            if (!this.mDestroyed && this.mHost != null) {
                if (this.mPendingActions == null) {
                    this.mPendingActions = new ArrayList<>();
                }
                this.mPendingActions.add(opGenerator);
                scheduleCommit();
                return;
            }
            if (!z) {
                throw new IllegalStateException("Activity has been destroyed");
            }
        }
    }

    public void ensureInflatedFragmentView(Fragment fragment2) {
        if (!fragment2.mFromLayout || fragment2.mPerformedCreateView) {
            return;
        }
        View viewPerformCreateView = fragment2.performCreateView(fragment2.performGetLayoutInflater(fragment2.mSavedFragmentState), null, fragment2.mSavedFragmentState);
        fragment2.mView = viewPerformCreateView;
        if (viewPerformCreateView == null) {
            fragment2.mInnerView = null;
            return;
        }
        fragment2.mInnerView = viewPerformCreateView;
        viewPerformCreateView.setSaveFromParentEnabled(false);
        if (fragment2.mHidden) {
            fragment2.mView.setVisibility(8);
        }
        fragment2.onViewCreated(fragment2.mView, fragment2.mSavedFragmentState);
        dispatchOnFragmentViewCreated(fragment2, fragment2.mView, fragment2.mSavedFragmentState, false);
    }

    public boolean execPendingActions() {
        ensureExecReady(true);
        boolean z = false;
        while (generateOpsForPendingActions(this.mTmpRecords, this.mTmpIsPop)) {
            this.mExecutingActions = true;
            try {
                removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop);
                cleanupExec();
                z = true;
            } catch (Throwable th) {
                cleanupExec();
                throw th;
            }
        }
        doPendingDeferredStart();
        burpActive();
        return z;
    }

    public void execSingleAction(OpGenerator opGenerator, boolean z) {
        if (z && (this.mHost == null || this.mDestroyed)) {
            return;
        }
        ensureExecReady(z);
        if (opGenerator.generateOps(this.mTmpRecords, this.mTmpIsPop)) {
            this.mExecutingActions = true;
            try {
                removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop);
            } finally {
                cleanupExec();
            }
        }
        doPendingDeferredStart();
        burpActive();
    }

    @Override // android.support.v4.app.FragmentManager
    public boolean executePendingTransactions() {
        boolean zExecPendingActions = execPendingActions();
        forcePostponedTransactions();
        return zExecPendingActions;
    }

    @Override // android.support.v4.app.FragmentManager
    public Fragment findFragmentById(int i) {
        for (int size = this.mAdded.size() - 1; size >= 0; size--) {
            Fragment fragment2 = this.mAdded.get(size);
            if (fragment2 != null && fragment2.mFragmentId == i) {
                return fragment2;
            }
        }
        SparseArray<Fragment> sparseArray = this.mActive;
        if (sparseArray == null) {
            return null;
        }
        for (int size2 = sparseArray.size() - 1; size2 >= 0; size2--) {
            Fragment fragmentValueAt = this.mActive.valueAt(size2);
            if (fragmentValueAt != null && fragmentValueAt.mFragmentId == i) {
                return fragmentValueAt;
            }
        }
        return null;
    }

    @Override // android.support.v4.app.FragmentManager
    public Fragment findFragmentByTag(String str) {
        if (str != null) {
            for (int size = this.mAdded.size() - 1; size >= 0; size--) {
                Fragment fragment2 = this.mAdded.get(size);
                if (fragment2 != null && str.equals(fragment2.mTag)) {
                    return fragment2;
                }
            }
        }
        SparseArray<Fragment> sparseArray = this.mActive;
        if (sparseArray == null || str == null) {
            return null;
        }
        for (int size2 = sparseArray.size() - 1; size2 >= 0; size2--) {
            Fragment fragmentValueAt = this.mActive.valueAt(size2);
            if (fragmentValueAt != null && str.equals(fragmentValueAt.mTag)) {
                return fragmentValueAt;
            }
        }
        return null;
    }

    public Fragment findFragmentByWho(String str) {
        Fragment fragmentFindFragmentByWho;
        SparseArray<Fragment> sparseArray = this.mActive;
        if (sparseArray == null || str == null) {
            return null;
        }
        for (int size = sparseArray.size() - 1; size >= 0; size--) {
            Fragment fragmentValueAt = this.mActive.valueAt(size);
            if (fragmentValueAt != null && (fragmentFindFragmentByWho = fragmentValueAt.findFragmentByWho(str)) != null) {
                return fragmentFindFragmentByWho;
            }
        }
        return null;
    }

    public void freeBackStackIndex(int i) {
        synchronized (this) {
            this.mBackStackIndices.set(i, null);
            if (this.mAvailBackStackIndices == null) {
                this.mAvailBackStackIndices = new ArrayList<>();
            }
            if (DEBUG) {
                Log.v("FragmentManager", "Freeing back stack index " + i);
            }
            this.mAvailBackStackIndices.add(Integer.valueOf(i));
        }
    }

    public int getActiveFragmentCount() {
        SparseArray<Fragment> sparseArray = this.mActive;
        if (sparseArray == null) {
            return 0;
        }
        return sparseArray.size();
    }

    public List<Fragment> getActiveFragments() {
        SparseArray<Fragment> sparseArray = this.mActive;
        if (sparseArray == null) {
            return null;
        }
        int size = sparseArray.size();
        ArrayList arrayList = new ArrayList(size);
        for (int i = 0; i < size; i++) {
            arrayList.add(this.mActive.valueAt(i));
        }
        return arrayList;
    }

    @Override // android.support.v4.app.FragmentManager
    public FragmentManager.BackStackEntry getBackStackEntryAt(int i) {
        return this.mBackStack.get(i);
    }

    @Override // android.support.v4.app.FragmentManager
    public int getBackStackEntryCount() {
        ArrayList<BackStackRecord> arrayList = this.mBackStack;
        if (arrayList != null) {
            return arrayList.size();
        }
        return 0;
    }

    @Override // android.support.v4.app.FragmentManager
    public Fragment getFragment(Bundle bundle, String str) {
        int i = bundle.getInt(str, -1);
        if (i == -1) {
            return null;
        }
        Fragment fragment2 = this.mActive.get(i);
        if (fragment2 == null) {
            throwException(new IllegalStateException("Fragment no longer exists for key " + str + ": index " + i));
        }
        return fragment2;
    }

    @Override // android.support.v4.app.FragmentManager
    public List<Fragment> getFragments() {
        List<Fragment> list;
        if (this.mAdded.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        synchronized (this.mAdded) {
            list = (List) this.mAdded.clone();
        }
        return list;
    }

    public LayoutInflater.Factory2 getLayoutInflaterFactory() {
        return this;
    }

    @Override // android.support.v4.app.FragmentManager
    public Fragment getPrimaryNavigationFragment() {
        return this.mPrimaryNav;
    }

    public void hideFragment(Fragment fragment2) {
        if (DEBUG) {
            g9.a("hide: ", fragment2, "FragmentManager");
        }
        if (fragment2.mHidden) {
            return;
        }
        fragment2.mHidden = true;
        fragment2.mHiddenChanged = true ^ fragment2.mHiddenChanged;
    }

    @Override // android.support.v4.app.FragmentManager
    public boolean isDestroyed() {
        return this.mDestroyed;
    }

    public boolean isStateAtLeast(int i) {
        return this.mCurState >= i;
    }

    @Override // android.support.v4.app.FragmentManager
    public boolean isStateSaved() {
        return this.mStateSaved;
    }

    public AnimationOrAnimator loadAnimation(Fragment fragment2, int i, boolean z, int i2) throws Resources.NotFoundException {
        int iTransitToStyleIndex;
        int nextAnim = fragment2.getNextAnim();
        Animation animationOnCreateAnimation = fragment2.onCreateAnimation(i, z, nextAnim);
        if (animationOnCreateAnimation != null) {
            return new AnimationOrAnimator(animationOnCreateAnimation);
        }
        Animator animatorOnCreateAnimator = fragment2.onCreateAnimator(i, z, nextAnim);
        if (animatorOnCreateAnimator != null) {
            return new AnimationOrAnimator(animatorOnCreateAnimator);
        }
        if (nextAnim != 0) {
            boolean zEquals = "anim".equals(this.mHost.getContext().getResources().getResourceTypeName(nextAnim));
            boolean z2 = false;
            if (zEquals) {
                try {
                    Animation animationLoadAnimation = AnimationUtils.loadAnimation(this.mHost.getContext(), nextAnim);
                    if (animationLoadAnimation != null) {
                        return new AnimationOrAnimator(animationLoadAnimation);
                    }
                    z2 = true;
                } catch (Resources.NotFoundException e) {
                    throw e;
                } catch (RuntimeException unused) {
                }
            }
            if (!z2) {
                try {
                    Animator animatorLoadAnimator = AnimatorInflater.loadAnimator(this.mHost.getContext(), nextAnim);
                    if (animatorLoadAnimator != null) {
                        return new AnimationOrAnimator(animatorLoadAnimator);
                    }
                } catch (RuntimeException e2) {
                    if (zEquals) {
                        throw e2;
                    }
                    Animation animationLoadAnimation2 = AnimationUtils.loadAnimation(this.mHost.getContext(), nextAnim);
                    if (animationLoadAnimation2 != null) {
                        return new AnimationOrAnimator(animationLoadAnimation2);
                    }
                }
            }
        }
        if (i == 0 || (iTransitToStyleIndex = transitToStyleIndex(i, z)) < 0) {
            return null;
        }
        switch (iTransitToStyleIndex) {
            case 1:
                return makeOpenCloseAnimation(this.mHost.getContext(), 1.125f, 1.0f, 0.0f, 1.0f);
            case 2:
                return makeOpenCloseAnimation(this.mHost.getContext(), 1.0f, 0.975f, 1.0f, 0.0f);
            case 3:
                return makeOpenCloseAnimation(this.mHost.getContext(), 0.975f, 1.0f, 0.0f, 1.0f);
            case 4:
                return makeOpenCloseAnimation(this.mHost.getContext(), 1.0f, 1.075f, 1.0f, 0.0f);
            case 5:
                return makeFadeAnimation(this.mHost.getContext(), 0.0f, 1.0f);
            case 6:
                return makeFadeAnimation(this.mHost.getContext(), 1.0f, 0.0f);
            default:
                if (i2 == 0 && this.mHost.onHasWindowAnimations()) {
                    i2 = this.mHost.onGetWindowAnimations();
                }
                if (i2 == 0) {
                }
                return null;
        }
    }

    public void makeActive(Fragment fragment2) {
        if (fragment2.mIndex >= 0) {
            return;
        }
        int i = this.mNextFragmentIndex;
        this.mNextFragmentIndex = i + 1;
        fragment2.setIndex(i, this.mParent);
        if (this.mActive == null) {
            this.mActive = new SparseArray<>();
        }
        this.mActive.put(fragment2.mIndex, fragment2);
        if (DEBUG) {
            g9.a("Allocated fragment index ", fragment2, "FragmentManager");
        }
    }

    public void makeInactive(Fragment fragment2) {
        if (fragment2.mIndex < 0) {
            return;
        }
        if (DEBUG) {
            g9.a("Freeing fragment index ", fragment2, "FragmentManager");
        }
        this.mActive.put(fragment2.mIndex, null);
        fragment2.initState();
    }

    public void moveFragmentToExpectedState(Fragment fragment2) {
        if (fragment2 == null) {
            return;
        }
        int iMin = this.mCurState;
        if (fragment2.mRemoving) {
            iMin = fragment2.isInBackStack() ? Math.min(iMin, 1) : Math.min(iMin, 0);
        }
        moveToState(fragment2, iMin, fragment2.getNextTransition(), fragment2.getNextTransitionStyle(), false);
        if (fragment2.mView != null) {
            Fragment fragmentFindFragmentUnder = findFragmentUnder(fragment2);
            if (fragmentFindFragmentUnder != null) {
                View view2 = fragmentFindFragmentUnder.mView;
                ViewGroup viewGroup = fragment2.mContainer;
                int iIndexOfChild = viewGroup.indexOfChild(view2);
                int iIndexOfChild2 = viewGroup.indexOfChild(fragment2.mView);
                if (iIndexOfChild2 < iIndexOfChild) {
                    viewGroup.removeViewAt(iIndexOfChild2);
                    viewGroup.addView(fragment2.mView, iIndexOfChild);
                }
            }
            if (fragment2.mIsNewlyAdded && fragment2.mContainer != null) {
                float f = fragment2.mPostponedAlpha;
                if (f > 0.0f) {
                    fragment2.mView.setAlpha(f);
                }
                fragment2.mPostponedAlpha = 0.0f;
                fragment2.mIsNewlyAdded = false;
                AnimationOrAnimator animationOrAnimatorLoadAnimation = loadAnimation(fragment2, fragment2.getNextTransition(), true, fragment2.getNextTransitionStyle());
                if (animationOrAnimatorLoadAnimation != null) {
                    setHWLayerAnimListenerIfAlpha(fragment2.mView, animationOrAnimatorLoadAnimation);
                    Animation animation = animationOrAnimatorLoadAnimation.animation;
                    if (animation != null) {
                        fragment2.mView.startAnimation(animation);
                    } else {
                        animationOrAnimatorLoadAnimation.animator.setTarget(fragment2.mView);
                        animationOrAnimatorLoadAnimation.animator.start();
                    }
                }
            }
        }
        if (fragment2.mHiddenChanged) {
            completeShowHideFragment(fragment2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:128:0x0249  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x024d  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x025f  */
    /* JADX WARN: Removed duplicated region for block: B:196:0x0322  */
    /* JADX WARN: Removed duplicated region for block: B:226:0x038a  */
    /* JADX WARN: Removed duplicated region for block: B:230:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void moveToState(android.support.v4.app.Fragment r17, int r18, int r19, int r20, boolean r21) throws java.lang.NoSuchFieldException, android.content.res.Resources.NotFoundException {
        /*
            Method dump skipped, instructions count: 952
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.app.FragmentManagerImpl.moveToState(android.support.v4.app.Fragment, int, int, int, boolean):void");
    }

    public void noteStateNotSaved() {
        this.mSavedNonConfig = null;
        this.mStateSaved = false;
        int size = this.mAdded.size();
        for (int i = 0; i < size; i++) {
            Fragment fragment2 = this.mAdded.get(i);
            if (fragment2 != null) {
                fragment2.noteStateNotSaved();
            }
        }
    }

    @Override // android.view.LayoutInflater.Factory2
    public View onCreateView(View view2, String str, Context context, AttributeSet attributeSet) throws NoSuchFieldException, Resources.NotFoundException {
        if (!"fragment".equals(str)) {
            return null;
        }
        String attributeValue = attributeSet.getAttributeValue(null, "class");
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, FragmentTag.Fragment);
        if (attributeValue == null) {
            attributeValue = typedArrayObtainStyledAttributes.getString(0);
        }
        String str2 = attributeValue;
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(1, -1);
        String string = typedArrayObtainStyledAttributes.getString(2);
        typedArrayObtainStyledAttributes.recycle();
        if (!Fragment.isSupportFragmentClass(this.mHost.getContext(), str2)) {
            return null;
        }
        int id = view2 != null ? view2.getId() : 0;
        if (id == -1 && resourceId == -1 && string == null) {
            throw new IllegalArgumentException(attributeSet.getPositionDescription() + ": Must specify unique android:id, android:tag, or have a parent with an id for " + str2);
        }
        Fragment fragmentFindFragmentById = resourceId != -1 ? findFragmentById(resourceId) : null;
        if (fragmentFindFragmentById == null && string != null) {
            fragmentFindFragmentById = findFragmentByTag(string);
        }
        if (fragmentFindFragmentById == null && id != -1) {
            fragmentFindFragmentById = findFragmentById(id);
        }
        if (DEBUG) {
            StringBuilder sbA = g9.a("onCreateView: id=0x");
            sbA.append(Integer.toHexString(resourceId));
            sbA.append(" fname=");
            sbA.append(str2);
            sbA.append(" existing=");
            sbA.append(fragmentFindFragmentById);
            Log.v("FragmentManager", sbA.toString());
        }
        if (fragmentFindFragmentById == null) {
            fragmentFindFragmentById = this.mContainer.instantiate(context, str2, null);
            fragmentFindFragmentById.mFromLayout = true;
            fragmentFindFragmentById.mFragmentId = resourceId != 0 ? resourceId : id;
            fragmentFindFragmentById.mContainerId = id;
            fragmentFindFragmentById.mTag = string;
            fragmentFindFragmentById.mInLayout = true;
            fragmentFindFragmentById.mFragmentManager = this;
            FragmentHostCallback fragmentHostCallback = this.mHost;
            fragmentFindFragmentById.mHost = fragmentHostCallback;
            fragmentFindFragmentById.onInflate(fragmentHostCallback.getContext(), attributeSet, fragmentFindFragmentById.mSavedFragmentState);
            addFragment(fragmentFindFragmentById, true);
        } else {
            if (fragmentFindFragmentById.mInLayout) {
                throw new IllegalArgumentException(attributeSet.getPositionDescription() + ": Duplicate id 0x" + Integer.toHexString(resourceId) + ", tag " + string + ", or parent id 0x" + Integer.toHexString(id) + " with another fragment for " + str2);
            }
            fragmentFindFragmentById.mInLayout = true;
            FragmentHostCallback fragmentHostCallback2 = this.mHost;
            fragmentFindFragmentById.mHost = fragmentHostCallback2;
            if (!fragmentFindFragmentById.mRetaining) {
                fragmentFindFragmentById.onInflate(fragmentHostCallback2.getContext(), attributeSet, fragmentFindFragmentById.mSavedFragmentState);
            }
        }
        Fragment fragment2 = fragmentFindFragmentById;
        if (this.mCurState >= 1 || !fragment2.mFromLayout) {
            moveToState(fragment2);
        } else {
            moveToState(fragment2, 1, 0, 0, false);
        }
        View view3 = fragment2.mView;
        if (view3 == null) {
            throw new IllegalStateException(g9.a("Fragment ", str2, " did not create a view."));
        }
        if (resourceId != 0) {
            view3.setId(resourceId);
        }
        if (fragment2.mView.getTag() == null) {
            fragment2.mView.setTag(string);
        }
        return fragment2.mView;
    }

    public void performPendingDeferredStart(Fragment fragment2) {
        if (fragment2.mDeferStart) {
            if (this.mExecutingActions) {
                this.mHavePendingDeferredStart = true;
            } else {
                fragment2.mDeferStart = false;
                moveToState(fragment2, this.mCurState, 0, 0, false);
            }
        }
    }

    @Override // android.support.v4.app.FragmentManager
    public void popBackStack() {
        enqueueAction(new PopBackStackState(null, -1, 0), false);
    }

    @Override // android.support.v4.app.FragmentManager
    public boolean popBackStackImmediate() {
        checkStateLoss();
        return popBackStackImmediate(null, -1, 0);
    }

    public boolean popBackStackState(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2, String str, int i, int i2) {
        int i3;
        ArrayList<BackStackRecord> arrayList3 = this.mBackStack;
        if (arrayList3 == null) {
            return false;
        }
        if (str == null && i < 0 && (i2 & 1) == 0) {
            int size = arrayList3.size() - 1;
            if (size < 0) {
                return false;
            }
            arrayList.add(this.mBackStack.remove(size));
            arrayList2.add(true);
        } else {
            if (str != null || i >= 0) {
                int size2 = this.mBackStack.size() - 1;
                while (size2 >= 0) {
                    BackStackRecord backStackRecord = this.mBackStack.get(size2);
                    if ((str != null && str.equals(backStackRecord.getName())) || (i >= 0 && i == backStackRecord.mIndex)) {
                        break;
                    }
                    size2--;
                }
                if (size2 < 0) {
                    return false;
                }
                if ((i2 & 1) != 0) {
                    while (true) {
                        size2--;
                        if (size2 < 0) {
                            break;
                        }
                        BackStackRecord backStackRecord2 = this.mBackStack.get(size2);
                        if (str == null || !str.equals(backStackRecord2.getName())) {
                            if (i < 0 || i != backStackRecord2.mIndex) {
                                break;
                            }
                        }
                    }
                }
                i3 = size2;
            } else {
                i3 = -1;
            }
            if (i3 == this.mBackStack.size() - 1) {
                return false;
            }
            for (int size3 = this.mBackStack.size() - 1; size3 > i3; size3--) {
                arrayList.add(this.mBackStack.remove(size3));
                arrayList2.add(true);
            }
        }
        return true;
    }

    @Override // android.support.v4.app.FragmentManager
    public void putFragment(Bundle bundle, String str, Fragment fragment2) {
        if (fragment2.mIndex < 0) {
            throwException(new IllegalStateException(g9.b("Fragment ", fragment2, " is not currently in the FragmentManager")));
        }
        bundle.putInt(str, fragment2.mIndex);
    }

    @Override // android.support.v4.app.FragmentManager
    public void registerFragmentLifecycleCallbacks(FragmentManager.FragmentLifecycleCallbacks fragmentLifecycleCallbacks, boolean z) {
        this.mLifecycleCallbacks.add(new Pair<>(fragmentLifecycleCallbacks, Boolean.valueOf(z)));
    }

    public void removeFragment(Fragment fragment2) {
        if (DEBUG) {
            Log.v("FragmentManager", "remove: " + fragment2 + " nesting=" + fragment2.mBackStackNesting);
        }
        boolean z = !fragment2.isInBackStack();
        if (!fragment2.mDetached || z) {
            synchronized (this.mAdded) {
                this.mAdded.remove(fragment2);
            }
            if (fragment2.mHasMenu && fragment2.mMenuVisible) {
                this.mNeedMenuInvalidate = true;
            }
            fragment2.mAdded = false;
            fragment2.mRemoving = true;
        }
    }

    @Override // android.support.v4.app.FragmentManager
    public void removeOnBackStackChangedListener(FragmentManager.OnBackStackChangedListener onBackStackChangedListener) {
        ArrayList<FragmentManager.OnBackStackChangedListener> arrayList = this.mBackStackChangeListeners;
        if (arrayList != null) {
            arrayList.remove(onBackStackChangedListener);
        }
    }

    public void reportBackStackChanged() {
        if (this.mBackStackChangeListeners != null) {
            for (int i = 0; i < this.mBackStackChangeListeners.size(); i++) {
                this.mBackStackChangeListeners.get(i).onBackStackChanged();
            }
        }
    }

    public void restoreAllState(Parcelable parcelable, FragmentManagerNonConfig fragmentManagerNonConfig) {
        List<FragmentManagerNonConfig> childNonConfigs;
        List<ViewModelStore> viewModelStores;
        if (parcelable == null) {
            return;
        }
        FragmentManagerState fragmentManagerState = (FragmentManagerState) parcelable;
        if (fragmentManagerState.mActive == null) {
            return;
        }
        if (fragmentManagerNonConfig != null) {
            List<Fragment> fragments = fragmentManagerNonConfig.getFragments();
            childNonConfigs = fragmentManagerNonConfig.getChildNonConfigs();
            viewModelStores = fragmentManagerNonConfig.getViewModelStores();
            int size = fragments != null ? fragments.size() : 0;
            for (int i = 0; i < size; i++) {
                Fragment fragment2 = fragments.get(i);
                if (DEBUG) {
                    g9.a("restoreAllState: re-attaching retained ", fragment2, "FragmentManager");
                }
                int i2 = 0;
                while (true) {
                    FragmentState[] fragmentStateArr = fragmentManagerState.mActive;
                    if (i2 >= fragmentStateArr.length || fragmentStateArr[i2].mIndex == fragment2.mIndex) {
                        break;
                    } else {
                        i2++;
                    }
                }
                if (i2 == fragmentManagerState.mActive.length) {
                    StringBuilder sbA = g9.a("Could not find active fragment with index ");
                    sbA.append(fragment2.mIndex);
                    throwException(new IllegalStateException(sbA.toString()));
                }
                FragmentState fragmentState = fragmentManagerState.mActive[i2];
                fragmentState.mInstance = fragment2;
                fragment2.mSavedViewState = null;
                fragment2.mBackStackNesting = 0;
                fragment2.mInLayout = false;
                fragment2.mAdded = false;
                fragment2.mTarget = null;
                Bundle bundle = fragmentState.mSavedFragmentState;
                if (bundle != null) {
                    bundle.setClassLoader(this.mHost.getContext().getClassLoader());
                    fragment2.mSavedViewState = fragmentState.mSavedFragmentState.getSparseParcelableArray(VIEW_STATE_TAG);
                    fragment2.mSavedFragmentState = fragmentState.mSavedFragmentState;
                }
            }
        } else {
            childNonConfigs = null;
            viewModelStores = null;
        }
        this.mActive = new SparseArray<>(fragmentManagerState.mActive.length);
        int i3 = 0;
        while (true) {
            FragmentState[] fragmentStateArr2 = fragmentManagerState.mActive;
            if (i3 >= fragmentStateArr2.length) {
                break;
            }
            FragmentState fragmentState2 = fragmentStateArr2[i3];
            if (fragmentState2 != null) {
                Fragment fragmentInstantiate = fragmentState2.instantiate(this.mHost, this.mContainer, this.mParent, (childNonConfigs == null || i3 >= childNonConfigs.size()) ? null : childNonConfigs.get(i3), (viewModelStores == null || i3 >= viewModelStores.size()) ? null : viewModelStores.get(i3));
                if (DEBUG) {
                    Log.v("FragmentManager", "restoreAllState: active #" + i3 + ": " + fragmentInstantiate);
                }
                this.mActive.put(fragmentInstantiate.mIndex, fragmentInstantiate);
                fragmentState2.mInstance = null;
            }
            i3++;
        }
        if (fragmentManagerNonConfig != null) {
            List<Fragment> fragments2 = fragmentManagerNonConfig.getFragments();
            int size2 = fragments2 != null ? fragments2.size() : 0;
            for (int i4 = 0; i4 < size2; i4++) {
                Fragment fragment3 = fragments2.get(i4);
                int i5 = fragment3.mTargetIndex;
                if (i5 >= 0) {
                    Fragment fragment4 = this.mActive.get(i5);
                    fragment3.mTarget = fragment4;
                    if (fragment4 == null) {
                        Log.w("FragmentManager", "Re-attaching retained fragment " + fragment3 + " target no longer exists: " + fragment3.mTargetIndex);
                    }
                }
            }
        }
        this.mAdded.clear();
        if (fragmentManagerState.mAdded != null) {
            int i6 = 0;
            while (true) {
                int[] iArr = fragmentManagerState.mAdded;
                if (i6 >= iArr.length) {
                    break;
                }
                Fragment fragment5 = this.mActive.get(iArr[i6]);
                if (fragment5 == null) {
                    StringBuilder sbA2 = g9.a("No instantiated fragment for index #");
                    sbA2.append(fragmentManagerState.mAdded[i6]);
                    throwException(new IllegalStateException(sbA2.toString()));
                }
                fragment5.mAdded = true;
                if (DEBUG) {
                    Log.v("FragmentManager", "restoreAllState: added #" + i6 + ": " + fragment5);
                }
                if (this.mAdded.contains(fragment5)) {
                    throw new IllegalStateException("Already added!");
                }
                synchronized (this.mAdded) {
                    this.mAdded.add(fragment5);
                }
                i6++;
            }
        }
        if (fragmentManagerState.mBackStack != null) {
            this.mBackStack = new ArrayList<>(fragmentManagerState.mBackStack.length);
            int i7 = 0;
            while (true) {
                BackStackState[] backStackStateArr = fragmentManagerState.mBackStack;
                if (i7 >= backStackStateArr.length) {
                    break;
                }
                BackStackRecord backStackRecordInstantiate = backStackStateArr[i7].instantiate(this);
                if (DEBUG) {
                    StringBuilder sbA3 = g9.a("restoreAllState: back stack #", i7, " (index ");
                    sbA3.append(backStackRecordInstantiate.mIndex);
                    sbA3.append("): ");
                    sbA3.append(backStackRecordInstantiate);
                    Log.v("FragmentManager", sbA3.toString());
                    PrintWriter printWriter = new PrintWriter(new LogWriter("FragmentManager"));
                    backStackRecordInstantiate.dump("  ", printWriter, false);
                    printWriter.close();
                }
                this.mBackStack.add(backStackRecordInstantiate);
                int i8 = backStackRecordInstantiate.mIndex;
                if (i8 >= 0) {
                    setBackStackIndex(i8, backStackRecordInstantiate);
                }
                i7++;
            }
        } else {
            this.mBackStack = null;
        }
        int i9 = fragmentManagerState.mPrimaryNavActiveIndex;
        if (i9 >= 0) {
            this.mPrimaryNav = this.mActive.get(i9);
        }
        this.mNextFragmentIndex = fragmentManagerState.mNextFragmentIndex;
    }

    public FragmentManagerNonConfig retainNonConfig() {
        setRetaining(this.mSavedNonConfig);
        return this.mSavedNonConfig;
    }

    public Parcelable saveAllState() throws NoSuchFieldException, Resources.NotFoundException {
        int[] iArr;
        int size;
        forcePostponedTransactions();
        endAnimatingAwayFragments();
        execPendingActions();
        this.mStateSaved = true;
        BackStackState[] backStackStateArr = null;
        this.mSavedNonConfig = null;
        SparseArray<Fragment> sparseArray = this.mActive;
        if (sparseArray == null || sparseArray.size() <= 0) {
            return null;
        }
        int size2 = this.mActive.size();
        FragmentState[] fragmentStateArr = new FragmentState[size2];
        boolean z = false;
        for (int i = 0; i < size2; i++) {
            Fragment fragmentValueAt = this.mActive.valueAt(i);
            if (fragmentValueAt != null) {
                if (fragmentValueAt.mIndex < 0) {
                    throwException(new IllegalStateException("Failure saving state: active " + fragmentValueAt + " has cleared index: " + fragmentValueAt.mIndex));
                }
                FragmentState fragmentState = new FragmentState(fragmentValueAt);
                fragmentStateArr[i] = fragmentState;
                if (fragmentValueAt.mState <= 0 || fragmentState.mSavedFragmentState != null) {
                    fragmentState.mSavedFragmentState = fragmentValueAt.mSavedFragmentState;
                } else {
                    fragmentState.mSavedFragmentState = saveFragmentBasicState(fragmentValueAt);
                    Fragment fragment2 = fragmentValueAt.mTarget;
                    if (fragment2 != null) {
                        if (fragment2.mIndex < 0) {
                            throwException(new IllegalStateException("Failure saving state: " + fragmentValueAt + " has target not in fragment manager: " + fragmentValueAt.mTarget));
                        }
                        if (fragmentState.mSavedFragmentState == null) {
                            fragmentState.mSavedFragmentState = new Bundle();
                        }
                        putFragment(fragmentState.mSavedFragmentState, TARGET_STATE_TAG, fragmentValueAt.mTarget);
                        int i2 = fragmentValueAt.mTargetRequestCode;
                        if (i2 != 0) {
                            fragmentState.mSavedFragmentState.putInt(TARGET_REQUEST_CODE_STATE_TAG, i2);
                        }
                    }
                }
                if (DEBUG) {
                    Log.v("FragmentManager", "Saved state of " + fragmentValueAt + ": " + fragmentState.mSavedFragmentState);
                }
                z = true;
            }
        }
        if (!z) {
            if (DEBUG) {
                Log.v("FragmentManager", "saveAllState: no fragments!");
            }
            return null;
        }
        int size3 = this.mAdded.size();
        if (size3 > 0) {
            iArr = new int[size3];
            for (int i3 = 0; i3 < size3; i3++) {
                iArr[i3] = this.mAdded.get(i3).mIndex;
                if (iArr[i3] < 0) {
                    StringBuilder sbA = g9.a("Failure saving state: active ");
                    sbA.append(this.mAdded.get(i3));
                    sbA.append(" has cleared index: ");
                    sbA.append(iArr[i3]);
                    throwException(new IllegalStateException(sbA.toString()));
                }
                if (DEBUG) {
                    StringBuilder sbA2 = g9.a("saveAllState: adding fragment #", i3, ": ");
                    sbA2.append(this.mAdded.get(i3));
                    Log.v("FragmentManager", sbA2.toString());
                }
            }
        } else {
            iArr = null;
        }
        ArrayList<BackStackRecord> arrayList = this.mBackStack;
        if (arrayList != null && (size = arrayList.size()) > 0) {
            backStackStateArr = new BackStackState[size];
            for (int i4 = 0; i4 < size; i4++) {
                backStackStateArr[i4] = new BackStackState(this.mBackStack.get(i4));
                if (DEBUG) {
                    StringBuilder sbA3 = g9.a("saveAllState: adding back stack #", i4, ": ");
                    sbA3.append(this.mBackStack.get(i4));
                    Log.v("FragmentManager", sbA3.toString());
                }
            }
        }
        FragmentManagerState fragmentManagerState = new FragmentManagerState();
        fragmentManagerState.mActive = fragmentStateArr;
        fragmentManagerState.mAdded = iArr;
        fragmentManagerState.mBackStack = backStackStateArr;
        Fragment fragment3 = this.mPrimaryNav;
        if (fragment3 != null) {
            fragmentManagerState.mPrimaryNavActiveIndex = fragment3.mIndex;
        }
        fragmentManagerState.mNextFragmentIndex = this.mNextFragmentIndex;
        saveNonConfig();
        return fragmentManagerState;
    }

    public Bundle saveFragmentBasicState(Fragment fragment2) {
        if (this.mStateBundle == null) {
            this.mStateBundle = new Bundle();
        }
        fragment2.performSaveInstanceState(this.mStateBundle);
        dispatchOnFragmentSaveInstanceState(fragment2, this.mStateBundle, false);
        Bundle bundle = null;
        if (!this.mStateBundle.isEmpty()) {
            Bundle bundle2 = this.mStateBundle;
            this.mStateBundle = null;
            bundle = bundle2;
        }
        if (fragment2.mView != null) {
            saveFragmentViewState(fragment2);
        }
        if (fragment2.mSavedViewState != null) {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putSparseParcelableArray(VIEW_STATE_TAG, fragment2.mSavedViewState);
        }
        if (!fragment2.mUserVisibleHint) {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putBoolean(USER_VISIBLE_HINT_TAG, fragment2.mUserVisibleHint);
        }
        return bundle;
    }

    @Override // android.support.v4.app.FragmentManager
    public Fragment.SavedState saveFragmentInstanceState(Fragment fragment2) {
        Bundle bundleSaveFragmentBasicState;
        if (fragment2.mIndex < 0) {
            throwException(new IllegalStateException(g9.b("Fragment ", fragment2, " is not currently in the FragmentManager")));
        }
        if (fragment2.mState <= 0 || (bundleSaveFragmentBasicState = saveFragmentBasicState(fragment2)) == null) {
            return null;
        }
        return new Fragment.SavedState(bundleSaveFragmentBasicState);
    }

    public void saveFragmentViewState(Fragment fragment2) {
        if (fragment2.mInnerView == null) {
            return;
        }
        SparseArray<Parcelable> sparseArray = this.mStateArray;
        if (sparseArray == null) {
            this.mStateArray = new SparseArray<>();
        } else {
            sparseArray.clear();
        }
        fragment2.mInnerView.saveHierarchyState(this.mStateArray);
        if (this.mStateArray.size() > 0) {
            fragment2.mSavedViewState = this.mStateArray;
            this.mStateArray = null;
        }
    }

    public void saveNonConfig() {
        ArrayList arrayList;
        ArrayList arrayList2;
        ArrayList arrayList3;
        FragmentManagerNonConfig fragmentManagerNonConfig;
        if (this.mActive != null) {
            arrayList = null;
            arrayList2 = null;
            arrayList3 = null;
            for (int i = 0; i < this.mActive.size(); i++) {
                Fragment fragmentValueAt = this.mActive.valueAt(i);
                if (fragmentValueAt != null) {
                    if (fragmentValueAt.mRetainInstance) {
                        if (arrayList == null) {
                            arrayList = new ArrayList();
                        }
                        arrayList.add(fragmentValueAt);
                        Fragment fragment2 = fragmentValueAt.mTarget;
                        fragmentValueAt.mTargetIndex = fragment2 != null ? fragment2.mIndex : -1;
                        if (DEBUG) {
                            g9.a("retainNonConfig: keeping retained ", fragmentValueAt, "FragmentManager");
                        }
                    }
                    FragmentManagerImpl fragmentManagerImpl = fragmentValueAt.mChildFragmentManager;
                    if (fragmentManagerImpl != null) {
                        fragmentManagerImpl.saveNonConfig();
                        fragmentManagerNonConfig = fragmentValueAt.mChildFragmentManager.mSavedNonConfig;
                    } else {
                        fragmentManagerNonConfig = fragmentValueAt.mChildNonConfig;
                    }
                    if (arrayList2 == null && fragmentManagerNonConfig != null) {
                        arrayList2 = new ArrayList(this.mActive.size());
                        for (int i2 = 0; i2 < i; i2++) {
                            arrayList2.add(null);
                        }
                    }
                    if (arrayList2 != null) {
                        arrayList2.add(fragmentManagerNonConfig);
                    }
                    if (arrayList3 == null && fragmentValueAt.mViewModelStore != null) {
                        arrayList3 = new ArrayList(this.mActive.size());
                        for (int i3 = 0; i3 < i; i3++) {
                            arrayList3.add(null);
                        }
                    }
                    if (arrayList3 != null) {
                        arrayList3.add(fragmentValueAt.mViewModelStore);
                    }
                }
            }
        } else {
            arrayList = null;
            arrayList2 = null;
            arrayList3 = null;
        }
        if (arrayList == null && arrayList2 == null && arrayList3 == null) {
            this.mSavedNonConfig = null;
        } else {
            this.mSavedNonConfig = new FragmentManagerNonConfig(arrayList, arrayList2, arrayList3);
        }
    }

    public void setBackStackIndex(int i, BackStackRecord backStackRecord) {
        synchronized (this) {
            if (this.mBackStackIndices == null) {
                this.mBackStackIndices = new ArrayList<>();
            }
            int size = this.mBackStackIndices.size();
            if (i < size) {
                if (DEBUG) {
                    Log.v("FragmentManager", "Setting back stack index " + i + " to " + backStackRecord);
                }
                this.mBackStackIndices.set(i, backStackRecord);
            } else {
                while (size < i) {
                    this.mBackStackIndices.add(null);
                    if (this.mAvailBackStackIndices == null) {
                        this.mAvailBackStackIndices = new ArrayList<>();
                    }
                    if (DEBUG) {
                        Log.v("FragmentManager", "Adding available back stack index " + size);
                    }
                    this.mAvailBackStackIndices.add(Integer.valueOf(size));
                    size++;
                }
                if (DEBUG) {
                    Log.v("FragmentManager", "Adding back stack index " + i + " with " + backStackRecord);
                }
                this.mBackStackIndices.add(backStackRecord);
            }
        }
    }

    public void setPrimaryNavigationFragment(Fragment fragment2) {
        if (fragment2 == null || (this.mActive.get(fragment2.mIndex) == fragment2 && (fragment2.mHost == null || fragment2.getFragmentManager() == this))) {
            this.mPrimaryNav = fragment2;
            return;
        }
        throw new IllegalArgumentException("Fragment " + fragment2 + " is not an active fragment of FragmentManager " + this);
    }

    public void showFragment(Fragment fragment2) {
        if (DEBUG) {
            g9.a("show: ", fragment2, "FragmentManager");
        }
        if (fragment2.mHidden) {
            fragment2.mHidden = false;
            fragment2.mHiddenChanged = !fragment2.mHiddenChanged;
        }
    }

    public void startPendingDeferredFragments() {
        if (this.mActive == null) {
            return;
        }
        for (int i = 0; i < this.mActive.size(); i++) {
            Fragment fragmentValueAt = this.mActive.valueAt(i);
            if (fragmentValueAt != null) {
                performPendingDeferredStart(fragmentValueAt);
            }
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("FragmentManager{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(" in ");
        Fragment fragment2 = this.mParent;
        if (fragment2 != null) {
            DebugUtils.buildShortClassTag(fragment2, sb);
        } else {
            DebugUtils.buildShortClassTag(this.mHost, sb);
        }
        sb.append("}}");
        return sb.toString();
    }

    @Override // android.support.v4.app.FragmentManager
    public void unregisterFragmentLifecycleCallbacks(FragmentManager.FragmentLifecycleCallbacks fragmentLifecycleCallbacks) {
        synchronized (this.mLifecycleCallbacks) {
            int i = 0;
            int size = this.mLifecycleCallbacks.size();
            while (true) {
                if (i >= size) {
                    break;
                }
                if (this.mLifecycleCallbacks.get(i).first == fragmentLifecycleCallbacks) {
                    this.mLifecycleCallbacks.remove(i);
                    break;
                }
                i++;
            }
        }
    }

    public static class AnimationOrAnimator {
        public final Animation animation;
        public final Animator animator;

        public AnimationOrAnimator(Animation animation) {
            this.animation = animation;
            this.animator = null;
            if (animation == null) {
                throw new IllegalStateException("Animation cannot be null");
            }
        }

        public AnimationOrAnimator(Animator animator) {
            this.animation = null;
            this.animator = animator;
            if (animator == null) {
                throw new IllegalStateException("Animator cannot be null");
            }
        }
    }

    @Override // android.support.v4.app.FragmentManager
    public void popBackStack(String str, int i) {
        enqueueAction(new PopBackStackState(str, -1, i), false);
    }

    @Override // android.support.v4.app.FragmentManager
    public void popBackStack(int i, int i2) {
        if (i >= 0) {
            enqueueAction(new PopBackStackState(null, i, i2), false);
            return;
        }
        throw new IllegalArgumentException(g9.b("Bad id: ", i));
    }

    @Override // android.support.v4.app.FragmentManager
    public boolean popBackStackImmediate(String str, int i) {
        checkStateLoss();
        return popBackStackImmediate(str, -1, i);
    }

    public static class EndViewTransitionAnimator extends AnimationSet implements Runnable {
        public final View mChild;
        public boolean mEnded;
        public final ViewGroup mParent;
        public boolean mTransitionEnded;

        public EndViewTransitionAnimator(@NonNull Animation animation, @NonNull ViewGroup viewGroup, @NonNull View view2) {
            super(false);
            this.mParent = viewGroup;
            this.mChild = view2;
            addAnimation(animation);
        }

        @Override // android.view.animation.AnimationSet, android.view.animation.Animation
        public boolean getTransformation(long j, Transformation transformation) {
            if (this.mEnded) {
                return !this.mTransitionEnded;
            }
            if (!super.getTransformation(j, transformation)) {
                this.mEnded = true;
                this.mParent.post(this);
            }
            return true;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.mParent.endViewTransition(this.mChild);
            this.mTransitionEnded = true;
        }

        @Override // android.view.animation.Animation
        public boolean getTransformation(long j, Transformation transformation, float f) {
            if (this.mEnded) {
                return !this.mTransitionEnded;
            }
            if (!super.getTransformation(j, transformation, f)) {
                this.mEnded = true;
                this.mParent.post(this);
            }
            return true;
        }
    }

    @Override // android.support.v4.app.FragmentManager
    public boolean popBackStackImmediate(int i, int i2) {
        checkStateLoss();
        execPendingActions();
        if (i >= 0) {
            return popBackStackImmediate(null, i, i2);
        }
        throw new IllegalArgumentException(g9.b("Bad id: ", i));
    }

    public static boolean modifiesAlpha(Animator animator) {
        if (animator == null) {
            return false;
        }
        if (animator instanceof ValueAnimator) {
            for (PropertyValuesHolder propertyValuesHolder : ((ValueAnimator) animator).getValues()) {
                if ("alpha".equals(propertyValuesHolder.getPropertyName())) {
                    return true;
                }
            }
        } else if (animator instanceof AnimatorSet) {
            ArrayList<Animator> childAnimations = ((AnimatorSet) animator).getChildAnimations();
            for (int i = 0; i < childAnimations.size(); i++) {
                if (modifiesAlpha(childAnimations.get(i))) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean popBackStackImmediate(String str, int i, int i2) {
        FragmentManager fragmentManagerPeekChildFragmentManager;
        execPendingActions();
        ensureExecReady(true);
        Fragment fragment2 = this.mPrimaryNav;
        if (fragment2 != null && i < 0 && str == null && (fragmentManagerPeekChildFragmentManager = fragment2.peekChildFragmentManager()) != null && fragmentManagerPeekChildFragmentManager.popBackStackImmediate()) {
            return true;
        }
        boolean zPopBackStackState = popBackStackState(this.mTmpRecords, this.mTmpIsPop, str, i, i2);
        if (zPopBackStackState) {
            this.mExecutingActions = true;
            try {
                removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop);
            } finally {
                cleanupExec();
            }
        }
        doPendingDeferredStart();
        burpActive();
        return zPopBackStackState;
    }

    @Override // android.view.LayoutInflater.Factory
    public View onCreateView(String str, Context context, AttributeSet attributeSet) {
        return onCreateView(null, str, context, attributeSet);
    }

    public void moveToState(Fragment fragment2) throws NoSuchFieldException, Resources.NotFoundException {
        moveToState(fragment2, this.mCurState, 0, 0, false);
    }

    public void moveToState(int i, boolean z) {
        FragmentHostCallback fragmentHostCallback;
        if (this.mHost == null && i != 0) {
            throw new IllegalStateException("No activity");
        }
        if (z || i != this.mCurState) {
            this.mCurState = i;
            if (this.mActive != null) {
                int size = this.mAdded.size();
                boolean zHasRunningLoaders = false;
                for (int i2 = 0; i2 < size; i2++) {
                    Fragment fragment2 = this.mAdded.get(i2);
                    moveFragmentToExpectedState(fragment2);
                    LoaderManagerImpl loaderManagerImpl = fragment2.mLoaderManager;
                    if (loaderManagerImpl != null) {
                        zHasRunningLoaders |= loaderManagerImpl.hasRunningLoaders();
                    }
                }
                int size2 = this.mActive.size();
                for (int i3 = 0; i3 < size2; i3++) {
                    Fragment fragmentValueAt = this.mActive.valueAt(i3);
                    if (fragmentValueAt != null && ((fragmentValueAt.mRemoving || fragmentValueAt.mDetached) && !fragmentValueAt.mIsNewlyAdded)) {
                        moveFragmentToExpectedState(fragmentValueAt);
                        LoaderManagerImpl loaderManagerImpl2 = fragmentValueAt.mLoaderManager;
                        if (loaderManagerImpl2 != null) {
                            zHasRunningLoaders |= loaderManagerImpl2.hasRunningLoaders();
                        }
                    }
                }
                if (!zHasRunningLoaders) {
                    startPendingDeferredFragments();
                }
                if (this.mNeedMenuInvalidate && (fragmentHostCallback = this.mHost) != null && this.mCurState == 5) {
                    fragmentHostCallback.onSupportInvalidateOptionsMenu();
                    this.mNeedMenuInvalidate = false;
                }
            }
        }
    }
}
