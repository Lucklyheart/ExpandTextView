package com.luckdu.library;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;


/**
 * 自定义控件，文本展开收起TextView
 */
@SuppressLint("AppCompatCustomView")
public class ExpandTextView extends TextView {
    /**
     * 原始内容文本
     */
    private String originText;
    /**
     * TextView可展示宽度
     */
    private int initWidth = Integer.MAX_VALUE;
    /**
     * TextView显示的最大行数
     */
    private int mMaxLines = 3;
    /**
     * 收起的拼接文案
     */
    private SpannableString SPAN_CLOSE = null;
    /**
     * 展开的拼接文案
     */
    private SpannableString SPAN_EXPAND = null;
    /**
     * 默认展开文案
     */
    private String TEXT_EXPAND = "  展开>";
    /**
     * 默认收起文案
     */
    private String TEXT_CLOSE = "  <收起";
    /**
     * 图片资源ID
     */
    private int mResId = 0;
    /**
     * 是否拼接文字（两种方式：true末尾拼文字或false者拼图片）
     */
    private boolean mAppendText = true;
    /**
     * 文本所占行数
     */
    private int mLines;
    /**
     * 文本格式（true全角   false半角）
     */
    private boolean ToDBC = true;

    public ExpandTextView(Context context) {
        super(context);

    }

    public ExpandTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public ExpandTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    /**
     * 初始化TextView的可展示宽度
     *
     * @param width
     */
    public void initWidth(int width) {
        initWidth = width;
    }

    /**
     * 设置TextView可显示的最大行数
     *
     * @param maxLines 最大行数
     */
    @Override
    public void setMaxLines(int maxLines) {
        this.mMaxLines = maxLines;
        super.setMaxLines(maxLines);
    }

    /**
     * 是否拼接文本
     *
     * @param appendText
     */
    public void setAppendText(boolean appendText) {
        this.mAppendText = appendText;
    }

    /**
     * 设置资源ID
     *
     * @param resId
     */
    public void setRes(int resId) {
        mResId = resId;
    }

    /**
     * 获取资源ID
     *
     * @return
     */
    public int getResId() {
        return mResId;
    }

    /**
     * 获取文本行数
     *
     * @return
     */
    public int getLines() {
        return mLines;
    }

    public void setToDBC(boolean toDBC) {
        ToDBC = toDBC;
    }

    /**
     * 收起的文案(颜色处理)初始化
     */
    private void initCloseEnd() {
        String content = TEXT_EXPAND;
        SPAN_CLOSE = new SpannableString(content);
        ButtonSpan span = new ButtonSpan(getContext(), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExpandTextView.super.setMaxLines(Integer.MAX_VALUE);
                setExpandText(originText);
            }
        });
        SPAN_CLOSE.setSpan(span, 0, content.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
    }

    /**
     * 收起的文案末尾图标
     */
    public void initCloseEndImg() {
        String content = TEXT_EXPAND;
        SPAN_CLOSE = new SpannableString(content);
        Drawable drawable = getResources().getDrawable(getResId());
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        ImageSpan span = new ImageSpan(drawable);
        SPAN_CLOSE.setSpan(span, 0, content.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
    }

    /**
     * 展开的文案(颜色处理)初始化
     */
    public void initExpandEnd() {
        String content = TEXT_CLOSE;
        SPAN_EXPAND = new SpannableString(content);
        ButtonSpan span = new ButtonSpan(getContext(), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExpandTextView.super.setMaxLines(mMaxLines);
                setCloseText(originText);
            }
        });
        SPAN_EXPAND.setSpan(span, 0, content.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
    }

    /**
     * 展开的文案末尾图标
     */
    private void initExpandEndImg() {
        String content = TEXT_CLOSE;
        SPAN_EXPAND = new SpannableString(content);
        Drawable drawable = getResources().getDrawable(getResId());
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        ImageSpan span = new ImageSpan(drawable);
        SPAN_EXPAND.setSpan(span, 0, content.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
    }

    /**
     * 设置文本收起
     *
     * @param text
     */
    public void setCloseText(String text) {

        if (SPAN_CLOSE == null) {
            if (mAppendText) {
                initCloseEnd();
            } else {
                initCloseEndImg();
            }
        }
        boolean appendShowAll = false;// false 不需要展开收起功能， true 需要展开收起功能
        originText = ToDBC == true ? ToDBC(text) : toDBC(text);

        // SDK >= 16 可以直接从xml属性获取最大行数
        int maxLines = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            maxLines = getMaxLines();
        } else {
            maxLines = mMaxLines;
        }
        String workingText = new StringBuilder(originText).toString();
        if (maxLines != -1) {
            Layout layout = createWorkingLayout(workingText);
            mLines = layout.getLineCount();
            if (layout.getLineCount() > maxLines) {
                //获取一行显示字符个数，然后截取字符串数
                workingText = originText.substring(0, layout.getLineEnd(maxLines - 1)).trim();// 收起状态原始文本截取展示的部分
                String showText = mAppendText == true ? originText.substring(0, layout.getLineEnd(maxLines - 1)).trim() + "..." + SPAN_CLOSE : originText.substring(0, layout.getLineEnd(maxLines - 1)).trim() + "...";
                Layout layout2 = createWorkingLayout(showText);
                // 对workingText进行-1截取，直到展示行数==最大行数，并且添加 SPAN_CLOSE 后刚好占满最后一行
                while (layout2.getLineCount() > maxLines) {
                    int lastSpace = workingText.length() - 1;
                    if (lastSpace == -1) {
                        break;
                    }
                    workingText = workingText.substring(0, lastSpace);
                    if (mAppendText) {
                        layout2 = createWorkingLayout(workingText + "..." + SPAN_CLOSE);
                    } else {
                        layout2 = createWorkingLayout(workingText + "...");
                    }

                }
                if (mAppendText) {
                    appendShowAll = true;
                }
                workingText = workingText + "...";
            } else {
                if (!mAppendText) {
                    appendShowAll = true;
                    workingText = workingText + "...\t";
                }
            }
        }
        setText(workingText);
        if (appendShowAll) {
            // 必须使用append，不能在上面使用+连接，否则spannable会无效
            append(SPAN_CLOSE);
            setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    /**
     * 设置文本展开
     *
     * @param text
     */
    public void setExpandText(String text) {
        if (SPAN_EXPAND == null) {
            if (mAppendText) {
                initExpandEnd();
            } else {
                initExpandEndImg();
            }

        }
        originText = ToDBC == true ? ToDBC(text) : toDBC(text);
        Layout layout1 = createWorkingLayout(text);
        Layout layout2 = createWorkingLayout(text + TEXT_CLOSE);
        // 展示全部原始内容时 如果 TEXT_CLOSE 需要换行才能显示完整，则直接将TEXT_CLOSE展示在下一行
        mLines = layout2.getLineCount();
        if (mAppendText) {
            if (layout2.getLineCount() > layout1.getLineCount()) {
                setText(originText + "\n");
            } else {
                setText(originText);
            }
        } else {
            setText(originText + "\t");
        }
        append(SPAN_EXPAND);
        setMovementMethod(LinkMovementMethod.getInstance());
    }

    /**
     * 返回textview的显示区域的layout
     */
    private Layout createWorkingLayout(String workingText) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            return new StaticLayout(workingText, getPaint(), initWidth - getPaddingLeft() - getPaddingRight(),
                    Layout.Alignment.ALIGN_NORMAL, getLineSpacingMultiplier(), getLineSpacingExtra(), false);
        } else {
            return new StaticLayout(workingText, getPaint(), initWidth - getPaddingLeft() - getPaddingRight(),
                    Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        }
    }

    /**
     * 屏蔽长按事件，防止崩溃
     *
     * @param longClickable
     */
    @Override
    public void setLongClickable(boolean longClickable) {
        super.setLongClickable(false);
    }

    /**
     * 展开收起的角标 点击事件
     */
    class ButtonSpan extends ClickableSpan {

        private View.OnClickListener onClickListener;
        private Context context;

        public ButtonSpan(Context context, View.OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
            this.context = context;
        }

        @Override
        public void updateDrawState(TextPaint textPaint) {
            textPaint.setColor(context.getResources().getColor(android.R.color.holo_red_light));
            textPaint.setUnderlineText(false);
            textPaint.setLinearText(false);
            textPaint.setFakeBoldText(false);
        }

        @Override
        public void onClick(View view) {
            if (onClickListener != null) {
                onClickListener.onClick(view);
            }
        }
    }

    /**
     * 转全角
     *
     * @param input
     * @return
     */
    private static String toDBC(String input) {
        char c[] = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == '\n') {

            } else if (c[i] == ' ') {
                c[i] = '\u3000';
            } else if (c[i] < '\177') {
                c[i] = (char) (c[i] + 65248);
            }
        }
        return new String(c);
    }

    /**
     * 转半角
     *
     * @param input
     * @return
     */
    public static String ToDBC(String input) {
        char c[] = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == '\u3000') {
                c[i] = ' ';
            } else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
                c[i] = (char) (c[i] - 65248);

            }
        }
        return new String(c);
    }
}