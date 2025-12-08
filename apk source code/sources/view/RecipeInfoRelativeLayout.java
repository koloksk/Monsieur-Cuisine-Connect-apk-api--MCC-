package view;

import adapter.IngredientsAdapter;
import adapter.InstructionsAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import application.App;
import com.squareup.picasso.Picasso;
import db.model.Nutrient;
import db.model.Recipe;
import de.silpion.mc2.R;
import defpackage.jr;
import fragment.RecipeOverviewFragment;
import helper.LayoutHelper;
import helper.RecipeAssetsHelper;
import java.util.List;
import mcapi.McApi;
import sound.SoundLength;
import view.RecipeInfoRelativeLayout;

/* loaded from: classes.dex */
public class RecipeInfoRelativeLayout extends RelativeLayout {
    public static final String v = RecipeOverviewFragment.class.getSimpleName();
    public final String a;
    public Animation b;
    public Animation c;
    public Animation d;
    public boolean e;
    public Recipe f;
    public ImageButton g;
    public RecyclerView h;
    public boolean i;
    public ImageButton j;
    public RecyclerView k;
    public TextView l;
    public ImageButton m;
    public ViewGroup n;
    public ImageView o;
    public jr p;
    public RelativeLayout q;
    public ObjectAnimator r;
    public TextView s;
    public ViewGroup t;
    public boolean u;

    public class a extends GestureDetector.SimpleOnGestureListener {
        public final /* synthetic */ boolean a;

        public a(boolean z) {
            this.a = z;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onDown(MotionEvent motionEvent) {
            return true;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            Log.i(RecipeInfoRelativeLayout.v, "onFling " + motionEvent + " -> " + motionEvent2);
            if (motionEvent2.getY() - motionEvent.getY() <= 50.0f) {
                return true;
            }
            RecipeInfoRelativeLayout recipeInfoRelativeLayout = RecipeInfoRelativeLayout.this;
            if (!recipeInfoRelativeLayout.u) {
                return true;
            }
            recipeInfoRelativeLayout.a();
            RecipeInfoRelativeLayout.this.f();
            if (this.a) {
                return true;
            }
            Handler handler = RecipeInfoRelativeLayout.this.getHandler();
            final RecipeInfoRelativeLayout recipeInfoRelativeLayout2 = RecipeInfoRelativeLayout.this;
            handler.postDelayed(new Runnable() { // from class: hq
                @Override // java.lang.Runnable
                public final void run() {
                    recipeInfoRelativeLayout2.setVisibility(8);
                }
            }, 220L);
            return true;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
        public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
            RecipeInfoRelativeLayout recipeInfoRelativeLayout = RecipeInfoRelativeLayout.this;
            if (!recipeInfoRelativeLayout.u) {
                return super.onSingleTapConfirmed(motionEvent);
            }
            recipeInfoRelativeLayout.a();
            RecipeInfoRelativeLayout.this.f();
            if (this.a) {
                return true;
            }
            Handler handler = RecipeInfoRelativeLayout.this.getHandler();
            final RecipeInfoRelativeLayout recipeInfoRelativeLayout2 = RecipeInfoRelativeLayout.this;
            handler.postDelayed(new Runnable() { // from class: gq
                @Override // java.lang.Runnable
                public final void run() {
                    recipeInfoRelativeLayout2.setVisibility(8);
                }
            }, 220L);
            return true;
        }
    }

    public RecipeInfoRelativeLayout(Context context) throws Resources.NotFoundException {
        super(context);
        this.a = RecipeAssetsHelper.getImageFolderPath(App.getInstance());
        a(context);
    }

    public /* synthetic */ void a(int i) {
        this.h.scrollBy(0, i);
    }

    public /* synthetic */ void b(int i) {
        this.k.scrollBy(0, i);
    }

    public /* synthetic */ void c(boolean z, View view2) {
        if (!z) {
            if (this.j.isSelected()) {
                return;
            }
            App.getInstance().playSound(R.raw.click, SoundLength.SHORT);
            d();
            return;
        }
        App.getInstance().playSound(R.raw.click, SoundLength.SHORT);
        if (this.j.isSelected()) {
            a();
            this.j.setSelected(false);
        } else {
            if (!this.u) {
                b();
            }
            d();
        }
    }

    public /* synthetic */ void d(boolean z, View view2) {
        if (!z) {
            if (this.m.isSelected()) {
                return;
            }
            App.getInstance().playSound(R.raw.click, SoundLength.SHORT);
            e();
            return;
        }
        App.getInstance().playSound(R.raw.click, SoundLength.SHORT);
        if (this.m.isSelected()) {
            a();
            this.m.setSelected(false);
        } else {
            if (!this.u) {
                b();
            }
            e();
        }
    }

    public final void e() {
        this.m.setSelected(true);
        this.g.setSelected(false);
        this.j.setSelected(false);
        this.h.setVisibility(8);
        this.k.setVisibility(8);
        this.n.setVisibility(0);
    }

    public final void f() {
        ImageButton imageButton = this.g;
        if (imageButton != null) {
            imageButton.setSelected(false);
        }
        ImageButton imageButton2 = this.j;
        if (imageButton2 != null) {
            imageButton2.setSelected(false);
        }
        ImageButton imageButton3 = this.m;
        if (imageButton3 != null) {
            imageButton3.setSelected(false);
        }
    }

    public void handleJogDialScroll(final int i) {
        if (this.u) {
            if (this.g.isSelected()) {
                this.q.post(new Runnable() { // from class: lq
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.a.a(i);
                    }
                });
            } else if (this.j.isSelected()) {
                this.q.post(new Runnable() { // from class: iq
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.a.b(i);
                    }
                });
            }
        }
    }

    public boolean hasChangedIsFavorite() {
        return this.e;
    }

    public void reset() {
        if (this.u) {
            a();
        }
        this.f = null;
        f();
    }

    public void showRecipeDetail(final Recipe recipe, final boolean z) {
        App app = App.getInstance();
        setVisibility(0);
        this.f = recipe;
        this.i = recipe != null && recipe.getIsFavorite();
        this.e = false;
        LinearLayout linearLayout = (LinearLayout) this.t.findViewById(R.id.fat_layout);
        LinearLayout linearLayout2 = (LinearLayout) this.t.findViewById(R.id.carbohydrate_layout);
        LinearLayout linearLayout3 = (LinearLayout) this.t.findViewById(R.id.protein_layout);
        LinearLayout linearLayout4 = (LinearLayout) this.t.findViewById(R.id.energy_values_layout);
        LinearLayout linearLayout5 = (LinearLayout) linearLayout4.findViewById(R.id.kilojoules_layout);
        LinearLayout linearLayout6 = (LinearLayout) linearLayout4.findViewById(R.id.calories_layout);
        List<Nutrient> nutrients = this.f.getNutrients();
        if (nutrients == null) {
            Log.w(v, "nutrients = null!");
        } else {
            ((TextView) this.t.findViewById(R.id.nutrients_header)).setText(getResources().getString(R.string.nutritional_header));
            ((TextView) this.t.findViewById(R.id.nutrients_unit)).setText(getResources().getString(R.string.nutritional_unit, this.f.getUnit()));
            if (nutrients.size() > 4) {
                ((TextView) linearLayout.findViewById(R.id.amount_tv)).setText(this.f.getNutrients().get(4).getAmount());
                ((TextView) linearLayout.findViewById(R.id.unit_tv)).setText(this.f.getNutrients().get(4).getUnit());
            }
            if (nutrients.size() > 3) {
                ((TextView) linearLayout2.findViewById(R.id.amount_tv)).setText(this.f.getNutrients().get(3).getAmount());
                ((TextView) linearLayout2.findViewById(R.id.unit_tv)).setText(this.f.getNutrients().get(3).getUnit());
            }
            if (nutrients.size() > 2) {
                ((TextView) linearLayout3.findViewById(R.id.amount_tv)).setText(this.f.getNutrients().get(2).getAmount());
                ((TextView) linearLayout3.findViewById(R.id.unit_tv)).setText(this.f.getNutrients().get(2).getUnit());
            }
            if (nutrients.size() > 1) {
                ((TextView) linearLayout5.findViewById(R.id.amount_tv)).setText(this.f.getNutrients().get(1).getAmount());
                ((TextView) linearLayout5.findViewById(R.id.unit_tv)).setText(this.f.getNutrients().get(1).getUnit());
            }
            if (nutrients.size() > 0) {
                ((TextView) linearLayout6.findViewById(R.id.amount_tv)).setText(this.f.getNutrients().get(0).getAmount());
                ((TextView) linearLayout6.findViewById(R.id.unit_tv)).setText(this.f.getNutrients().get(0).getUnit());
            }
            ((TextView) linearLayout.findViewById(R.id.type_tv)).setText(getResources().getString(R.string.fat));
            ((TextView) linearLayout2.findViewById(R.id.type_tv)).setText(getResources().getString(R.string.carbohydrate));
            ((TextView) linearLayout3.findViewById(R.id.type_tv)).setText(getResources().getString(R.string.protein));
        }
        String str = this.a;
        Picasso.get().load(RecipeAssetsHelper.getLargeRecipeImageFile(str, recipe.getLargeImagePath(str))).fit().centerCrop().into(this.o);
        TextView textView = (TextView) this.q.findViewById(R.id.recipe_detail_minutes);
        TextView textView2 = (TextView) this.q.findViewById(R.id.recipe_detail_servings);
        TextView textView3 = (TextView) this.q.findViewById(R.id.recipe_detail_level_label);
        this.l.setText(recipe.getName());
        textView.setText(String.valueOf(recipe.getDurationTotal()));
        textView2.setText(String.valueOf(recipe.getYield()));
        textView3.setText(recipe.getComplexity());
        this.s.setText(getContext().getString(z ? R.string.start_lower_case : R.string.continue_guided_cooking));
        this.s.setOnClickListener(new View.OnClickListener() { // from class: oq
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.a.a(z, view2);
            }
        });
        final ImageButton imageButton = (ImageButton) this.q.findViewById(R.id.recipe_detail_favorite_button);
        imageButton.setSelected(recipe.getIsFavorite());
        imageButton.setOnClickListener(new View.OnClickListener() { // from class: mq
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.a.a(imageButton, recipe, view2);
            }
        });
        this.h.setAdapter(new IngredientsAdapter(getContext(), recipe.getIngredientsBases()));
        this.k.setAdapter(new InstructionsAdapter(getContext(), recipe));
        f();
        if (!z) {
            c();
            b();
        } else if (this.u) {
            a();
        }
        this.g.setOnClickListener(new View.OnClickListener() { // from class: nq
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.a.b(z, view2);
            }
        });
        this.j.setOnClickListener(new View.OnClickListener() { // from class: kq
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.a.c(z, view2);
            }
        });
        this.m.setOnClickListener(new View.OnClickListener() { // from class: jq
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.a.d(z, view2);
            }
        });
        final GestureDetector gestureDetector = new GestureDetector(app, new a(z));
        ((RelativeLayout) this.q.findViewById(R.id.recipe_details_top_container)).setOnTouchListener(new View.OnTouchListener() { // from class: pq
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view2, MotionEvent motionEvent) {
                return RecipeInfoRelativeLayout.a(gestureDetector, view2, motionEvent);
            }
        });
    }

    public /* synthetic */ void b(boolean z, View view2) {
        if (!z) {
            if (this.g.isSelected()) {
                return;
            }
            App.getInstance().playSound(R.raw.click, SoundLength.SHORT);
            c();
            return;
        }
        App.getInstance().playSound(R.raw.click, SoundLength.SHORT);
        if (this.g.isSelected()) {
            a();
            this.g.setSelected(false);
        } else {
            if (!this.u) {
                b();
            }
            c();
        }
    }

    public final void a(Context context) throws Resources.NotFoundException {
        RelativeLayout relativeLayout = (RelativeLayout) View.inflate(context, R.layout.recipe_details_layout, this);
        this.q = relativeLayout;
        this.o = (ImageView) relativeLayout.findViewById(R.id.recipe_details_image);
        this.l = (TextView) this.q.findViewById(R.id.recipe_details_name);
        this.s = (TextView) this.q.findViewById(R.id.recipe_details_start_cooking);
        this.t = (ViewGroup) this.q.findViewById(R.id.recipe_details_values);
        this.h = (RecyclerView) this.q.findViewById(R.id.recipe_details_ingredients);
        this.n = (ViewGroup) this.q.findViewById(R.id.recipes_detail_nutrient_root);
        this.k = (RecyclerView) this.q.findViewById(R.id.recipe_details_instructions);
        this.g = (ImageButton) this.q.findViewById(R.id.recipe_detail_ingredients_button);
        this.j = (ImageButton) this.q.findViewById(R.id.recipe_detail_instructions_button);
        this.m = (ImageButton) this.q.findViewById(R.id.recipe_detail_nutrients_button);
        Animation animationLoadAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.move_to_top);
        this.b = animationLoadAnimation;
        animationLoadAnimation.setFillAfter(false);
        Animation animationLoadAnimation2 = AnimationUtils.loadAnimation(getContext(), R.anim.move_to_top_half);
        this.c = animationLoadAnimation2;
        animationLoadAnimation2.setFillAfter(false);
        Animation animationLoadAnimation3 = AnimationUtils.loadAnimation(getContext(), R.anim.move_to_top_third);
        this.d = animationLoadAnimation3;
        animationLoadAnimation3.setFillAfter(false);
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.s, "translationY", 0.0f, -144.0f);
        this.r = objectAnimatorOfFloat;
        objectAnimatorOfFloat.setInterpolator(new LinearInterpolator());
        this.p = new jr();
    }

    public RecipeInfoRelativeLayout(Context context, AttributeSet attributeSet) throws Resources.NotFoundException {
        super(context, attributeSet);
        this.a = RecipeAssetsHelper.getImageFolderPath(App.getInstance());
        a(context);
    }

    public RecipeInfoRelativeLayout(Context context, AttributeSet attributeSet, int i) throws Resources.NotFoundException {
        super(context, attributeSet, i);
        this.a = RecipeAssetsHelper.getImageFolderPath(App.getInstance());
        a(context);
    }

    public final void c() {
        this.g.setSelected(true);
        this.j.setSelected(false);
        this.m.setSelected(false);
        this.h.setVisibility(0);
        this.k.setVisibility(8);
        this.n.setVisibility(8);
        this.h.scrollToPosition(0);
    }

    public final void d() {
        this.j.setSelected(true);
        this.m.setSelected(false);
        this.g.setSelected(false);
        this.h.setVisibility(8);
        this.k.setVisibility(0);
        this.n.setVisibility(8);
        this.k.scrollToPosition(0);
    }

    public final void b() {
        this.t.setVisibility(0);
        this.b.setInterpolator(null);
        this.b.setFillAfter(false);
        this.t.startAnimation(this.b);
        this.c.setInterpolator(null);
        this.c.setFillAfter(true);
        this.c.setStartOffset(200L);
        this.c.setDuration(500L);
        this.o.startAnimation(this.c);
        this.d.setInterpolator(null);
        this.d.setFillAfter(true);
        this.d.setStartOffset(10L);
        this.l.startAnimation(this.d);
        this.r.setDuration(430L);
        this.r.setStartDelay(60L);
        this.r.start();
        this.u = true;
    }

    public /* synthetic */ void a(boolean z, View view2) {
        App.getInstance().playSound(R.raw.click, SoundLength.SHORT);
        a();
        if (z) {
            LayoutHelper.getInstance().openGuidedCooking(this.f);
        } else {
            getHandler().postDelayed(new Runnable() { // from class: dr
                @Override // java.lang.Runnable
                public final void run() {
                    this.a.setVisibility(8);
                }
            }, 220L);
        }
    }

    public /* synthetic */ void a(ImageButton imageButton, Recipe recipe, View view2) {
        App.getInstance().playSound(R.raw.click, SoundLength.SHORT);
        imageButton.setSelected(!imageButton.isSelected());
        recipe.setIsFavorite(imageButton.isSelected());
        recipe.update();
        McApi.getInstance().setIsFavorite(recipe.getId(), imageButton.isSelected());
        this.e = this.i != recipe.getIsFavorite();
    }

    public static /* synthetic */ boolean a(GestureDetector gestureDetector, View view2, MotionEvent motionEvent) {
        Log.i(v, "flingView on touch @top_container");
        return gestureDetector.onTouchEvent(motionEvent);
    }

    public final void a() {
        this.r.setDuration(400L);
        this.r.setStartDelay(0L);
        this.r.reverse();
        this.b.setInterpolator(this.p);
        this.b.setFillAfter(true);
        this.t.startAnimation(this.b);
        this.c.setInterpolator(this.p);
        this.c.setFillAfter(true);
        this.c.setStartOffset(0L);
        this.c.setDuration(200L);
        this.o.startAnimation(this.c);
        this.d.setInterpolator(this.p);
        this.d.setFillAfter(true);
        this.d.setStartOffset(100L);
        this.l.startAnimation(this.d);
        this.t.setVisibility(8);
        this.u = false;
    }
}
