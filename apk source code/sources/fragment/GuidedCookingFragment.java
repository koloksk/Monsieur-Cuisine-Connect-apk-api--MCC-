package fragment;

import activity.MainActivity;
import adapter.guidedcooking.GuidedCookingStepsAdapter;
import adapter.guidedcooking.viewholder.CookingViewHolder;
import adapter.guidedcooking.viewholder.StepViewHolder;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import application.App;
import cooking.CookingManager;
import db.DbHelper;
import db.model.Recipe;
import de.silpion.mc2.R;
import defpackage.g9;
import helper.DialogHelper;
import helper.LayoutHelper;
import helper.SharedPreferencesHelper;
import machineAdapter.IMachineCallback;
import machineAdapter.adapter.MachineCallbackAdapter;
import model.RecipeBookmark;
import org.apache.commons.lang3.ArrayUtils;
import sound.SoundLength;
import view.GuidedCookingLayoutManager;
import view.RecipeInfoRelativeLayout;

/* loaded from: classes.dex */
public class GuidedCookingFragment extends BaseFragment {
    public static final String t = GuidedCookingFragment.class.getSimpleName();
    public ImageButton d;
    public ImageButton e;
    public LinearLayout f;
    public RelativeLayout g;
    public Recipe h;
    public RelativeLayout i;
    public GestureDetector j;
    public View.OnTouchListener k;
    public ImageView m;
    public ImageView n;
    public RecipeInfoRelativeLayout o;
    public RecyclerView p;
    public RelativeLayout q;
    public GuidedCookingStepsAdapter r;
    public GuidedCookingLayoutManager s;
    public final IMachineCallback c = new b(null);
    public Handler l = new Handler();

    public class a extends RecyclerView.OnScrollListener {
        public a() {
        }

        @Override // android.support.v7.widget.RecyclerView.OnScrollListener
        public void onScrolled(RecyclerView recyclerView, int i, int i2) {
            super.onScrolled(recyclerView, i, i2);
            GuidedCookingFragment guidedCookingFragment = GuidedCookingFragment.this;
            int iFindFirstVisibleItemPosition = guidedCookingFragment.s.findFirstVisibleItemPosition();
            Log.i(GuidedCookingFragment.t, "syncProgress " + iFindFirstVisibleItemPosition);
            int itemCount = guidedCookingFragment.s.getItemCount() + (-1);
            int iFindLastVisibleItemPosition = guidedCookingFragment.s.findLastVisibleItemPosition();
            ViewGroup.LayoutParams layoutParams = guidedCookingFragment.n.getLayoutParams();
            layoutParams.width = (guidedCookingFragment.m.getWidth() * iFindFirstVisibleItemPosition) / itemCount;
            guidedCookingFragment.n.setLayoutParams(layoutParams);
            if (iFindFirstVisibleItemPosition < 0 || itemCount < 0) {
                return;
            }
            guidedCookingFragment.d.setVisibility(iFindFirstVisibleItemPosition == 0 ? 4 : 0);
            guidedCookingFragment.e.setVisibility(iFindLastVisibleItemPosition != itemCount ? 0 : 4);
            RecyclerView.ViewHolder childViewHolder = guidedCookingFragment.p.getChildViewHolder(guidedCookingFragment.s.findViewByPosition(iFindFirstVisibleItemPosition));
            if (childViewHolder == null || !(childViewHolder instanceof StepViewHolder)) {
                return;
            }
            guidedCookingFragment.r.activateStep((StepViewHolder) childViewHolder);
        }
    }

    public class b extends MachineCallbackAdapter {
        public /* synthetic */ b(a aVar) {
        }

        public /* synthetic */ void a(int i) {
            GuidedCookingFragment.this.a(i, false);
        }

        @Override // machineAdapter.adapter.MachineCallbackAdapter, machineAdapter.IMachineCallback
        public void onBadState(final int[] iArr) {
            GuidedCookingFragment guidedCookingFragment;
            GuidedCookingStepsAdapter guidedCookingStepsAdapter;
            super.onBadState(iArr);
            if (LayoutHelper.getInstance().isViewSelected(4)) {
                boolean zContains = ArrayUtils.contains(iArr, 7);
                int[] iArrRemoveElement = ArrayUtils.removeElement(iArr, 7);
                if (zContains && ((guidedCookingStepsAdapter = (guidedCookingFragment = GuidedCookingFragment.this).r) == null || guidedCookingStepsAdapter.getItemViewType(guidedCookingFragment.getCurrentPosition()) != 7)) {
                    Log.w(GuidedCookingFragment.t, "onBadState - ignore scale overload");
                    iArr = iArrRemoveElement;
                }
                if (iArr.length == 0) {
                    return;
                }
                App.getInstance().playSound(R.raw.error, SoundLength.MIDDLE);
                if (ArrayUtils.contains(iArr, 13)) {
                    GuidedCookingFragment.this.getActivity().runOnUiThread(new Runnable() { // from class: ne
                        @Override // java.lang.Runnable
                        public final void run() {
                            CookingManager.getInstance().stopCooking();
                        }
                    });
                }
                if (ArrayUtils.contains(iArr, 5)) {
                    GuidedCookingFragment.this.getActivity().runOnUiThread(new Runnable() { // from class: le
                        @Override // java.lang.Runnable
                        public final void run() {
                            CookingManager.getInstance().pauseCooking();
                        }
                    });
                }
                final RelativeLayout relativeLayout = (RelativeLayout) GuidedCookingFragment.this.getActivity().findViewById(R.id.dialog_warning);
                GuidedCookingFragment.this.q.post(new Runnable() { // from class: me
                    @Override // java.lang.Runnable
                    public final void run() {
                        DialogHelper.getInstance().showWarningDialog(relativeLayout, iArr);
                    }
                });
            }
        }

        @Override // machineAdapter.adapter.MachineCallbackAdapter, machineAdapter.IMachineCallback
        public void onJogDialPagingRequested(int i) {
            final int currentPosition;
            if (LayoutHelper.getInstance().isViewSelected(4)) {
                GuidedCookingFragment guidedCookingFragment = GuidedCookingFragment.this;
                if (guidedCookingFragment.h == null || guidedCookingFragment.s == null || guidedCookingFragment.p == null || (currentPosition = guidedCookingFragment.getCurrentPosition()) < 0) {
                    return;
                }
                RecyclerView.ViewHolder viewHolderFindViewHolderForAdapterPosition = GuidedCookingFragment.this.p.findViewHolderForAdapterPosition(currentPosition);
                if ((viewHolderFindViewHolderForAdapterPosition instanceof CookingViewHolder) && ((CookingViewHolder) viewHolderFindViewHolderForAdapterPosition).hasActiveKnob()) {
                    return;
                }
                if (1 == i) {
                    if (currentPosition < GuidedCookingFragment.this.r.getItemCount() - 1) {
                        currentPosition++;
                    }
                } else if (currentPosition > 0) {
                    currentPosition--;
                }
                String str = GuidedCookingFragment.t;
                StringBuilder sbA = g9.a("paging request to position idx ", currentPosition, " of ");
                sbA.append(GuidedCookingFragment.this.r.getItemCount());
                Log.d(str, sbA.toString());
                GuidedCookingFragment.this.q.post(new Runnable() { // from class: oe
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.a.a(currentPosition);
                    }
                });
            }
        }

        @Override // machineAdapter.adapter.MachineCallbackAdapter, machineAdapter.IMachineCallback
        public void onJogDialTurned(int i, long j) {
            super.onJogDialTurned(i, j);
            RecipeInfoRelativeLayout recipeInfoRelativeLayout = GuidedCookingFragment.this.o;
            if (recipeInfoRelativeLayout == null || recipeInfoRelativeLayout.getVisibility() != 0) {
                return;
            }
            int i2 = (int) (50 * j * j);
            if (i == 0) {
                i2 *= -1;
            }
            GuidedCookingFragment.this.o.handleJogDialScroll(i2);
        }
    }

    public class c extends GestureDetector.SimpleOnGestureListener {
        public c() {
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            Log.w(GuidedCookingFragment.t, "onFling");
            try {
                if (motionEvent.getY() - motionEvent2.getY() > 50.0f) {
                    Math.abs(f);
                }
                if (motionEvent2.getY() - motionEvent.getY() > 50.0f) {
                    Math.abs(f);
                }
                if (motionEvent.getX() - motionEvent2.getX() > 50.0f && Math.abs(f) > 100.0f) {
                    GuidedCookingFragment guidedCookingFragment = GuidedCookingFragment.this;
                    if (guidedCookingFragment.s.getOrientation() != 0) {
                        return false;
                    }
                    guidedCookingFragment.a(guidedCookingFragment.getCurrentPosition() + 1, true);
                    return false;
                }
                if (motionEvent2.getX() - motionEvent.getX() <= 50.0f || Math.abs(f) <= 100.0f) {
                    return false;
                }
                GuidedCookingFragment guidedCookingFragment2 = GuidedCookingFragment.this;
                if (guidedCookingFragment2.s.getOrientation() != 0) {
                    return false;
                }
                guidedCookingFragment2.a(guidedCookingFragment2.getCurrentPosition() - 1, true);
                return false;
            } catch (Exception unused) {
                return false;
            }
        }
    }

    public /* synthetic */ void b(View view2) throws Resources.NotFoundException {
        App.getInstance().playSound(R.raw.click, SoundLength.SHORT);
        this.s.setOrientation(0);
        this.p.setLayoutManager(this.s);
        a(false);
        this.r.setOrientation(0);
        if (getCurrentPosition() == 0) {
            this.d.setVisibility(4);
        } else if (b()) {
            this.e.setVisibility(4);
        }
        CookingManager.getInstance().stopCooking();
        this.p.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.horizontal_shake));
    }

    public /* synthetic */ void c(View view2) {
        App.getInstance().playSound(R.raw.click, SoundLength.SHORT);
        view2.setSelected(!view2.isSelected());
        this.o.showRecipeDetail(this.h, false);
    }

    public void checkAndScrollToBookmark() {
        RecipeBookmark recipeBookmark = SharedPreferencesHelper.getInstance().getRecipeBookmark();
        if (recipeBookmark == null || this.h == null || recipeBookmark.getRecipeId() != this.h.getId().longValue()) {
            return;
        }
        RecipeBookmark recipeBookmark2 = SharedPreferencesHelper.getInstance().getRecipeBookmark();
        setRecipe(DbHelper.getInstance().getRecipeById(recipeBookmark2.getRecipeId()));
        a(recipeBookmark2.getPage(), false);
        SharedPreferencesHelper.getInstance().removeBookmark();
        ((MainActivity) getActivity()).resetBookmarkImage();
    }

    public /* synthetic */ void d(View view2) {
        App.getInstance().playSound(R.raw.click, SoundLength.SHORT);
        view2.setSelected(!view2.isSelected());
        this.o.showRecipeDetail(this.h, false);
        CookingManager.getInstance().stopCooking();
    }

    public /* synthetic */ void e(View view2) {
        a(getCurrentPosition() - 1, true);
    }

    public /* synthetic */ void f(View view2) {
        a(getCurrentPosition() + 1, true);
    }

    public int getCurrentPosition() {
        return this.s.findFirstVisibleItemPosition();
    }

    public String getCurrentRecipeName() {
        Recipe recipe = this.h;
        return recipe != null ? recipe.getName() : "";
    }

    public boolean hasRecipe() {
        return this.h != null;
    }

    public boolean isFirstOrLastStepPosition() {
        return b() || getCurrentPosition() == 0;
    }

    @Override // android.support.v4.app.Fragment
    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        RelativeLayout relativeLayout = (RelativeLayout) layoutInflater.inflate(R.layout.fragment_guided_cooking, viewGroup, false);
        this.q = relativeLayout;
        return relativeLayout;
    }

    @Override // fragment.BaseFragment, android.support.v4.app.Fragment
    public void onViewCreated(@NonNull View view2, Bundle bundle) throws IllegalStateException, Resources.NotFoundException {
        super.onViewCreated(view2, bundle);
        App.getInstance().getMachineAdapter().registerMachineCallback(this.c);
        this.i = (RelativeLayout) this.q.findViewById(R.id.dialog_changed_recipe_rl);
        this.p = (RecyclerView) this.q.findViewById(R.id.fragment_guided_cooking_rv);
        GuidedCookingLayoutManager guidedCookingLayoutManager = new GuidedCookingLayoutManager(getActivity());
        this.s = guidedCookingLayoutManager;
        guidedCookingLayoutManager.setOrientation(0);
        this.p.setLayoutManager(this.s);
        new PagerSnapHelper().attachToRecyclerView(this.p);
        this.p.setItemViewCacheSize(0);
        this.d = (ImageButton) this.q.findViewById(R.id.fragment_guided_cooking_left_arrow_ib);
        this.e = (ImageButton) this.q.findViewById(R.id.fragment_guided_cooking_right_arrow_ib);
        this.n = (ImageView) this.q.findViewById(R.id.fragment_guided_cooking_progress_iv);
        this.m = (ImageView) this.q.findViewById(R.id.fragment_guided_cooking_progress_bg_iv);
        this.f = (LinearLayout) this.q.findViewById(R.id.fragment_guided_cooking_bottom_container_ll);
        this.g = (RelativeLayout) this.q.findViewById(R.id.fragment_guided_cooking_bottom_vertical_container_ll);
        ((ImageButton) this.q.findViewById(R.id.fragment_guided_cooking_switch_horizontal_ib)).setOnClickListener(new View.OnClickListener() { // from class: se
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) throws Resources.NotFoundException {
                this.a.a(view3);
            }
        });
        ((ImageButton) this.q.findViewById(R.id.fragment_guided_cooking_switch_vertical_ib)).setOnClickListener(new View.OnClickListener() { // from class: ke
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) throws Resources.NotFoundException {
                this.a.b(view3);
            }
        });
        this.q.findViewById(R.id.fragment_guided_cooking_info_ib).setOnClickListener(new View.OnClickListener() { // from class: ue
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                this.a.c(view3);
            }
        });
        this.q.findViewById(R.id.fragment_guided_cooking_info_vertical_ib).setOnClickListener(new View.OnClickListener() { // from class: ie
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                this.a.d(view3);
            }
        });
        this.d.setOnClickListener(new View.OnClickListener() { // from class: je
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                this.a.e(view3);
            }
        });
        this.e.setOnClickListener(new View.OnClickListener() { // from class: qe
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                this.a.f(view3);
            }
        });
        this.o = (RecipeInfoRelativeLayout) this.q.findViewById(R.id.recipe_info_layout);
        this.p.addOnScrollListener(new a());
        if (this.h != null) {
            a();
        }
        this.j = new GestureDetector(getActivity(), new c());
        View.OnTouchListener onTouchListener = new View.OnTouchListener() { // from class: re
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view3, MotionEvent motionEvent) {
                return this.a.a(view3, motionEvent);
            }
        };
        this.k = onTouchListener;
        this.p.setOnTouchListener(onTouchListener);
        checkAndScrollToBookmark();
    }

    public void saveBookmark() {
        SharedPreferencesHelper.getInstance().saveRecipeBookmark(new RecipeBookmark(this.h.getId().longValue(), getCurrentPosition()));
    }

    public void setRecipe(Recipe recipe) {
        this.h = recipe;
        if (this.o != null) {
            a();
        }
    }

    public void showRecipeChangedDialog() {
        String str = t;
        StringBuilder sbA = g9.a("showRecipeChangedDialog: ");
        sbA.append(this.i);
        Log.i(str, sbA.toString());
        RelativeLayout relativeLayout = this.i;
        if (relativeLayout == null || relativeLayout.getVisibility() == 0) {
            return;
        }
        this.i.setVisibility(0);
        DialogHelper.getInstance().showRecipeChangedDialog(this.i, new DialogHelper.SelectionListener() { // from class: pe
            @Override // helper.DialogHelper.SelectionListener
            public final void onSelect(int i) {
                this.a.a(i);
            }
        });
    }

    public /* synthetic */ void a(View view2) throws Resources.NotFoundException {
        App.getInstance().playSound(R.raw.click, SoundLength.SHORT);
        this.s.setOrientation(1);
        this.p.setLayoutManager(this.s);
        a(true);
        this.r.setOrientation(1);
        CookingManager.getInstance().stopCooking();
        this.p.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.vertical_shake));
    }

    public final void c() {
        int iFindFirstVisibleItemPosition = this.s.findFirstVisibleItemPosition();
        Log.i(t, "syncProgress " + iFindFirstVisibleItemPosition);
        int itemCount = this.s.getItemCount() + (-1);
        int iFindLastVisibleItemPosition = this.s.findLastVisibleItemPosition();
        ViewGroup.LayoutParams layoutParams = this.n.getLayoutParams();
        layoutParams.width = (this.m.getWidth() * iFindFirstVisibleItemPosition) / itemCount;
        this.n.setLayoutParams(layoutParams);
        if (iFindFirstVisibleItemPosition < 0 || itemCount < 0) {
            return;
        }
        this.d.setVisibility(iFindFirstVisibleItemPosition == 0 ? 4 : 0);
        this.e.setVisibility(iFindLastVisibleItemPosition != itemCount ? 0 : 4);
        RecyclerView.ViewHolder childViewHolder = this.p.getChildViewHolder(this.s.findViewByPosition(iFindFirstVisibleItemPosition));
        if (childViewHolder == null || !(childViewHolder instanceof StepViewHolder)) {
            return;
        }
        this.r.activateStep((StepViewHolder) childViewHolder);
    }

    public /* synthetic */ boolean a(View view2, MotionEvent motionEvent) {
        Log.w(t, "onTouchGesture");
        return this.j.onTouchEvent(motionEvent);
    }

    public /* synthetic */ void a(int i) {
        if (i == 1) {
            RecyclerView.ViewHolder viewHolderFindViewHolderForAdapterPosition = this.p.findViewHolderForAdapterPosition(this.s.findFirstVisibleItemPosition());
            if (viewHolderFindViewHolderForAdapterPosition instanceof CookingViewHolder) {
                Log.i(t, "cancel: ");
                ((CookingViewHolder) viewHolderFindViewHolderForAdapterPosition).refreshData();
            }
        }
        this.i.setVisibility(8);
    }

    public /* synthetic */ void b(Animation animation) {
        this.e.startAnimation(animation);
    }

    public final boolean b() {
        int currentPosition = getCurrentPosition();
        return this.s.getChildCount() + currentPosition >= this.s.getItemCount() && currentPosition >= 0;
    }

    public final void a() throws Resources.NotFoundException {
        String str = t;
        StringBuilder sbA = g9.a("applyCurrentRecipe -- recipe ");
        sbA.append(this.h);
        Log.i(str, sbA.toString());
        this.o.reset();
        this.r = new GuidedCookingStepsAdapter(getContext(), this.h);
        final Animation animationLoadAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.bounce_anim);
        this.r.setStepDoneCallback(new Runnable() { // from class: te
            @Override // java.lang.Runnable
            public final void run() {
                this.a.a(animationLoadAnimation);
            }
        });
        this.p.setAdapter(this.r);
        ImageView imageView = this.n;
        if (imageView != null) {
            ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
            layoutParams.width = 0;
            this.n.setLayoutParams(layoutParams);
        }
        this.d.setVisibility(4);
        this.e.setVisibility(this.r.getItemCount() > 1 ? 0 : 4);
        this.s.setOrientation(0);
        this.p.setLayoutManager(this.s);
        a(false);
        this.p.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.horizontal_shake));
        LayoutHelper.applyFullscreen(this.a);
    }

    public /* synthetic */ void a(final Animation animation) {
        App.getInstance().getMainActivity().runOnUiThread(new Runnable() { // from class: ve
            @Override // java.lang.Runnable
            public final void run() {
                this.a.b(animation);
            }
        });
    }

    public final void a(int i, boolean z) {
        Log.d(t, "scrollToStep " + i);
        if (i >= 0 && i < this.r.getItemCount()) {
            if (z) {
                App.getInstance().playSound(R.raw.click, SoundLength.SHORT);
            }
            this.s.scrollToPosition(i);
            this.l.postDelayed(new Runnable() { // from class: rj
                @Override // java.lang.Runnable
                public final void run() {
                    this.a.c();
                }
            }, 100L);
            this.e.clearAnimation();
            CookingManager.getInstance().stopCooking();
            return;
        }
        Log.i(t, "scrollToStep " + i + " .. ignored.");
    }

    public final void a(boolean z) {
        int i = z ? 0 : 8;
        int i2 = z ? 8 : 0;
        this.d.setVisibility(i2);
        this.e.setVisibility(i2);
        this.n.setVisibility(i2);
        this.m.setVisibility(i2);
        this.g.setVisibility(i);
        this.f.setVisibility(i2);
    }
}
