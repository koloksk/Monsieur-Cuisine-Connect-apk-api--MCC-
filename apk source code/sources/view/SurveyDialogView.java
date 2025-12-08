package view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.silpion.mc2.R;
import defpackage.kr;
import helper.ActionListener;

/* loaded from: classes.dex */
public class SurveyDialogView extends RelativeLayout {
    public static final String e = SurveyDialogView.class.getSimpleName();
    public ActionListener<String> a;
    public ActionListener<SurveyDialogView> b;

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
    public ActionListener<SurveyDialogView> c;

    @BindView(R.id.campaign_text)
    public QuicksandTextView campaignText;
    public ActionListener<SurveyDialogView> d;

    @BindView(R.id.dialog_title)
    public QuicksandTextView dialogTitle;

    @BindView(R.id.qr_image_view)
    public ImageView imageView;

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
                    String url = uRLSpanArr[0].getURL();
                    ActionListener<String> actionListener = ((kr) this).a.a;
                    if (actionListener != null) {
                        actionListener.onAction(url);
                    }
                    return true;
                }
            }
            return super.onTouchEvent(textView, spannable, motionEvent);
        }
    }

    public SurveyDialogView(Context context) {
        this(context, null);
    }

    public /* synthetic */ void a(View view2) {
        Log.i(e, "button one");
        ActionListener<SurveyDialogView> actionListener = this.b;
        if (actionListener != null) {
            actionListener.onAction(this);
        }
    }

    public /* synthetic */ void b(View view2) {
        Log.i(e, "button two");
        ActionListener<SurveyDialogView> actionListener = this.d;
        if (actionListener != null) {
            actionListener.onAction(this);
        }
    }

    public /* synthetic */ void c(View view2) {
        Log.i(e, "button three");
        if (this.d != null) {
            this.c.onAction(this);
        }
    }

    public boolean isButtonOneVisible() {
        return this.buttonOne.getVisibility() == 0;
    }

    public void setBodyTextSize(float f) {
        this.campaignText.setTextSize(f);
    }

    public void setButtonOneClickListener(ActionListener<SurveyDialogView> actionListener) {
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

    public void setButtonThreeClickListener(ActionListener<SurveyDialogView> actionListener) {
        this.c = actionListener;
    }

    public void setButtonThreeImage(Drawable drawable) {
        this.buttonThreeImage.setImageDrawable(drawable);
    }

    public void setButtonThreeText(String str) {
        this.buttonThreeText.setText(str);
    }

    public void setButtonTwoClickListener(ActionListener<SurveyDialogView> actionListener) {
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

    public void setCampaignText(String str) {
        if (TextUtils.isEmpty(str)) {
            this.campaignText.setVisibility(8);
        } else {
            this.campaignText.setVisibility(0);
            this.campaignText.setText(Html.fromHtml(str));
        }
    }

    public void setImageBitmap(Bitmap bitmap) {
        this.imageView.setImageBitmap(bitmap);
    }

    public void setImageDrawable(Drawable drawable) {
        this.imageView.setImageDrawable(drawable);
    }

    public void setOnBodyURLClick(ActionListener<String> actionListener) {
        this.a = actionListener;
    }

    public void setTitle(String str) {
        this.dialogTitle.setText(str);
    }

    public SurveyDialogView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0);
    }

    public SurveyDialogView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SurveyDialogView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        RelativeLayout.inflate(context, R.layout.dialog_survey, this);
        ButterKnife.bind(this);
        TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.SurveyDialogView, i, i2);
        TypedArray typedArrayObtainStyledAttributes2 = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.QuestionDialogView, i, i2);
        try {
            setCampaignText(typedArrayObtainStyledAttributes.getString(0));
            Drawable drawable = typedArrayObtainStyledAttributes2.getDrawable(2);
            if (drawable != null) {
                this.buttonOneImage.setImageDrawable(drawable);
            }
            this.buttonOneText.setText(typedArrayObtainStyledAttributes2.getString(3));
            a(this.buttonOne, this.buttonOneImage, typedArrayObtainStyledAttributes2.getBoolean(1, true));
            Drawable drawable2 = typedArrayObtainStyledAttributes2.getDrawable(9);
            if (drawable2 != null) {
                this.buttonTwoImage.setImageDrawable(drawable2);
            }
            this.buttonTwoText.setText(typedArrayObtainStyledAttributes2.getString(10));
            this.buttonTwo.setVisibility(typedArrayObtainStyledAttributes2.getBoolean(11, true) ? 0 : 8);
            a(this.buttonTwo, this.buttonTwoImage, typedArrayObtainStyledAttributes2.getBoolean(8, true));
            Drawable drawable3 = typedArrayObtainStyledAttributes2.getDrawable(5);
            if (drawable3 != null) {
                this.buttonThreeImage.setImageDrawable(drawable3);
            }
            this.buttonThreeText.setText(typedArrayObtainStyledAttributes2.getString(6));
            this.buttonThree.setVisibility(typedArrayObtainStyledAttributes2.getBoolean(7, false) ? 0 : 8);
            a(this.buttonThree, this.buttonThreeImage, typedArrayObtainStyledAttributes2.getBoolean(4, true));
            typedArrayObtainStyledAttributes.recycle();
            this.buttonOne.setOnClickListener(new View.OnClickListener() { // from class: rq
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.a.a(view2);
                }
            });
            this.buttonTwo.setOnClickListener(new View.OnClickListener() { // from class: sq
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.a.b(view2);
                }
            });
            this.buttonThree.setOnClickListener(new View.OnClickListener() { // from class: qq
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.a.c(view2);
                }
            });
            this.campaignText.setMovementMethod(new kr(this));
            this.campaignText.setFocusable(false);
            this.campaignText.setLongClickable(false);
        } catch (Throwable th) {
            typedArrayObtainStyledAttributes.recycle();
            throw th;
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
