package view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Layout;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.silpion.mc2.R;
import defpackage.hr;
import defpackage.ir;
import helper.ActionListener;

/* loaded from: classes.dex */
public class QuestionDialogView extends RelativeLayout {
    public static final String g = QuestionDialogView.class.getSimpleName();
    public ActionListener<String> a;
    public ActionListener<QuestionDialogView> b;

    @BindView(R.id.dialog_body)
    public TextView body;

    @BindView(R.id.button_one)
    public ViewGroup buttonOne;

    @BindView(R.id.button_one_image)
    public ImageView buttonOneImage;

    @BindView(R.id.button_one_text)
    public TextView buttonOneText;

    @BindView(R.id.button_three)
    public ViewGroup buttonThree;

    @BindView(R.id.button_three_image)
    public ImageView buttonThreeImage;

    @BindView(R.id.button_three_text)
    public TextView buttonThreeText;

    @BindView(R.id.button_two)
    public ViewGroup buttonTwo;

    @BindView(R.id.button_two_image)
    public ImageView buttonTwoImage;

    @BindView(R.id.button_two_text)
    public TextView buttonTwoText;
    public ActionListener<QuestionDialogView> c;

    @BindView(R.id.dialog_checkbox)
    public Switch checkbox;

    @BindView(R.id.dialog_checkbox_text)
    public TextView checkboxText;
    public ActionListener<QuestionDialogView> d;
    public ActionListener<QuestionDialogView> e;
    public ActionListener<QuestionDialogView> f;

    @BindView(R.id.dialog_title)
    public TextView title;

    public static abstract class LinkHandler extends LinkMovementMethod {
        public abstract void onLinkClicked(String str);

        @Override // android.text.method.LinkMovementMethod, android.text.method.ScrollingMovementMethod, android.text.method.BaseMovementMethod, android.text.method.MovementMethod
        public boolean onTouchEvent(TextView textView, Spannable spannable, MotionEvent motionEvent) {
            if (motionEvent.getAction() == 1) {
                int x = (int) motionEvent.getX();
                int y = (int) motionEvent.getY();
                int totalPaddingLeft = x - textView.getTotalPaddingLeft();
                int totalPaddingTop = y - textView.getTotalPaddingTop();
                int scrollX = textView.getScrollX() + totalPaddingLeft;
                int scrollY = textView.getScrollY() + totalPaddingTop;
                Layout layout = textView.getLayout();
                int offsetForHorizontal = layout.getOffsetForHorizontal(layout.getLineForVertical(scrollY), scrollX);
                URLSpan[] uRLSpanArr = (URLSpan[]) spannable.getSpans(offsetForHorizontal, offsetForHorizontal, URLSpan.class);
                if (uRLSpanArr.length != 0) {
                    onLinkClicked(uRLSpanArr[0].getURL());
                    return true;
                }
            }
            return super.onTouchEvent(textView, spannable, motionEvent);
        }
    }

    public QuestionDialogView(Context context) {
        this(context, null);
    }

    public /* synthetic */ void a(View view2) {
        Log.i(g, "button one");
        ActionListener<QuestionDialogView> actionListener = this.b;
        if (actionListener != null) {
            actionListener.onAction(this);
        }
    }

    public /* synthetic */ void b(View view2) {
        Log.i(g, "button two");
        ActionListener<QuestionDialogView> actionListener = this.d;
        if (actionListener != null) {
            actionListener.onAction(this);
        }
    }

    public /* synthetic */ void c(View view2) {
        Log.i(g, "button three");
        if (this.d != null) {
            this.c.onAction(this);
        }
    }

    public boolean isButtonOneVisible() {
        return this.buttonOne.getVisibility() == 0;
    }

    public boolean isCheckboxChecked() {
        return this.checkbox.isChecked();
    }

    public void setBodyGravity(int i) {
        this.body.setGravity(i);
    }

    public void setBodyText(String str) {
        if (TextUtils.isEmpty(str)) {
            this.body.setVisibility(8);
        } else {
            this.body.setVisibility(0);
            this.body.setText(Html.fromHtml(str));
        }
    }

    public void setBodyTextSize(float f) {
        this.body.setTextSize(f);
    }

    public void setButtonOneClickListener(ActionListener<QuestionDialogView> actionListener) {
        this.b = actionListener;
    }

    public void setButtonOneEnabled(boolean z) {
        if (this.buttonOne.isEnabled() == z) {
            return;
        }
        a(this.buttonOne, this.buttonOneImage, z);
    }

    public void setButtonOneImage(Drawable drawable) {
        this.buttonOneImage.setImageDrawable(drawable);
    }

    public void setButtonOneText(String str) {
        this.buttonOneText.setText(str);
    }

    public void setButtonOneVisibility(int i) {
        this.buttonOne.setVisibility(i);
    }

    public void setButtonThreeClickListener(ActionListener<QuestionDialogView> actionListener) {
        this.c = actionListener;
    }

    public void setButtonThreeImage(Drawable drawable) {
        this.buttonThreeImage.setImageDrawable(drawable);
    }

    public void setButtonThreeText(String str) {
        this.buttonThreeText.setText(str);
    }

    public void setButtonTwoClickListener(ActionListener<QuestionDialogView> actionListener) {
        this.d = actionListener;
    }

    public void setButtonTwoEnabled(boolean z) {
        if (this.buttonTwo.isEnabled() == z) {
            return;
        }
        a(this.buttonTwo, this.buttonTwoImage, z);
    }

    public void setButtonTwoImage(Drawable drawable) {
        this.buttonTwoImage.setImageDrawable(drawable);
    }

    public void setButtonTwoText(String str) {
        this.buttonTwoText.setText(str);
    }

    public void setButtonTwoVisibility(int i) {
        this.buttonTwo.setVisibility(i);
    }

    public void setCheckboxChecked(boolean z) {
        this.checkbox.setChecked(z);
    }

    public void setCheckboxCheckedListener(ActionListener<QuestionDialogView> actionListener) {
        this.e = actionListener;
    }

    public void setCheckboxLinkClickListener(ActionListener<QuestionDialogView> actionListener) {
        this.f = actionListener;
    }

    public void setOnBodyURLClick(ActionListener<String> actionListener) {
        this.a = actionListener;
    }

    public void setTitleText(String str) {
        if (TextUtils.isEmpty(str)) {
            this.title.setVisibility(8);
        } else {
            this.title.setVisibility(0);
            this.title.setText(Html.fromHtml(str));
        }
    }

    public QuestionDialogView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0);
    }

    public QuestionDialogView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public QuestionDialogView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        RelativeLayout.inflate(context, R.layout.dialog_question, this);
        ButterKnife.bind(this);
        TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.QuestionDialogView, i, i2);
        try {
            setBodyText(typedArrayObtainStyledAttributes.getString(0));
            setTitleText(typedArrayObtainStyledAttributes.getString(15));
            Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(2);
            if (drawable != null) {
                this.buttonOneImage.setImageDrawable(drawable);
            }
            this.buttonOneText.setText(typedArrayObtainStyledAttributes.getString(3));
            a(this.buttonOne, this.buttonOneImage, typedArrayObtainStyledAttributes.getBoolean(1, true));
            Drawable drawable2 = typedArrayObtainStyledAttributes.getDrawable(9);
            if (drawable2 != null) {
                this.buttonTwoImage.setImageDrawable(drawable2);
            }
            this.buttonTwoText.setText(typedArrayObtainStyledAttributes.getString(10));
            this.buttonTwo.setVisibility(typedArrayObtainStyledAttributes.getBoolean(11, true) ? 0 : 8);
            a(this.buttonTwo, this.buttonTwoImage, typedArrayObtainStyledAttributes.getBoolean(8, true));
            Drawable drawable3 = typedArrayObtainStyledAttributes.getDrawable(5);
            if (drawable3 != null) {
                this.buttonThreeImage.setImageDrawable(drawable3);
            }
            this.buttonThreeText.setText(typedArrayObtainStyledAttributes.getString(6));
            this.buttonThree.setVisibility(typedArrayObtainStyledAttributes.getBoolean(7, false) ? 0 : 8);
            a(this.buttonThree, this.buttonThreeImage, typedArrayObtainStyledAttributes.getBoolean(4, true));
            if (typedArrayObtainStyledAttributes.getBoolean(14, false)) {
                this.checkbox.setChecked(typedArrayObtainStyledAttributes.getBoolean(12, false));
                this.checkbox.setVisibility(0);
                this.checkboxText.setText(Html.fromHtml(typedArrayObtainStyledAttributes.getString(13)));
                this.checkboxText.setVisibility(0);
            } else {
                this.checkbox.setVisibility(8);
                this.checkboxText.setVisibility(8);
            }
            typedArrayObtainStyledAttributes.recycle();
            this.buttonOne.setOnClickListener(new View.OnClickListener() { // from class: eq
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.a.a(view2);
                }
            });
            this.buttonTwo.setOnClickListener(new View.OnClickListener() { // from class: dq
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.a.b(view2);
                }
            });
            this.buttonThree.setOnClickListener(new View.OnClickListener() { // from class: fq
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.a.c(view2);
                }
            });
            this.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: cq
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    this.a.a(compoundButton, z);
                }
            });
            this.checkboxText.setMovementMethod(new hr(this));
            this.checkboxText.setFocusable(false);
            this.checkboxText.setLongClickable(false);
            this.body.setMovementMethod(new ir(this));
            this.body.setFocusable(false);
            this.body.setLongClickable(false);
        } catch (Throwable th) {
            typedArrayObtainStyledAttributes.recycle();
            throw th;
        }
    }

    public /* synthetic */ void a(CompoundButton compoundButton, boolean z) {
        Log.i(g, "check changed");
        ActionListener<QuestionDialogView> actionListener = this.e;
        if (actionListener != null) {
            actionListener.onAction(this);
        }
    }

    public final void a(ViewGroup viewGroup, ImageView imageView, boolean z) {
        viewGroup.setEnabled(z);
        viewGroup.setClickable(z);
        viewGroup.setFocusable(z);
        imageView.setEnabled(z);
        if (z) {
            imageView.clearColorFilter();
        } else {
            imageView.setColorFilter(Color.argb(100, 150, 150, 150), PorterDuff.Mode.MULTIPLY);
        }
    }
}
