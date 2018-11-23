package com.tag.tagsdemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class TagsLayout extends ViewGroup implements View.OnClickListener {

    private Context context;
    private ArrayList<String> tags;
    private boolean hasExecuted = false;

    private int tagsBackground;
    private int tagsTextColor;
    private float tagsTextSize;
    private int tagsPadding;
    private int tagsMargin;

    public TagsLayout(Context context) {
        this(context, null);
        this.context = context;
    }

    public TagsLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        this.context = context;
    }

    public TagsLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        @SuppressLint({"Recycle", "CustomViewStyleable"})
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TagsLayout);
        tagsBackground = typedArray.getResourceId(R.styleable.TagsLayout_tags_background, 0);
        tagsTextColor = typedArray.getColor(R.styleable.TagsLayout_tags_text_color, Color.LTGRAY);
        tagsTextSize = typedArray.getDimension(R.styleable.TagsLayout_tags_text_size, 16);
        tagsPadding = (int) typedArray.getDimension(R.styleable.TagsLayout_tags_padding, 10);
        tagsMargin = (int) typedArray.getDimension(R.styleable.TagsLayout_tags_margin, 10);
        typedArray.recycle();
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    /*
    set property of every tags such as gravity、padding、color、background.
     */
    private void initTags() {
        hasExecuted = true;
        if (tags.size() > 0) {
            for (int i = 0; i < tags.size(); i++) {
                TextView textView = new TextView(context);
                textView.setText(tags.get(i));
                textView.setBackgroundResource(tagsBackground);
                textView.setTextColor(tagsTextColor);
                textView.setTextSize(tagsTextSize);
                textView.setGravity(Gravity.CENTER);
                textView.setPadding(tagsPadding, tagsPadding, tagsPadding, tagsPadding);
                final int finalI = i;
                textView.setOnClickListener(new OnClickListener() {
                    @SuppressLint("NewApi")
                    @Override
                    public void onClick(View v) {
                        listener.onTagsClick(v, tags.get(finalI));
                    }
                });
                this.addView(textView);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //onMeasure()方法会执行两次，在这里做一个判断
        if (!hasExecuted) {
            initTags();
        }
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childrenWidth = 0; // 每一行子view的总宽度
        int leftPosition = 0; //子view的左侧坐标
        int topPosition = 0; //子view的顶部坐标
        int left, top, right, bottom; // 子view四个方位的坐标
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            int childWidth = view.getMeasuredWidth();
            int childHeight = view.getMeasuredHeight();
            Log.e(TAG, "onLayout: " + childHeight);
            /*
            判断每行标签总宽度是否大于当前要摆放的标签的宽度，如果大于继续摆放否则换到下一行
            "getMeasuredWidth() - childrenWidth":父控件的总宽度减去当前行所有子view的宽度的值
             */
            if (getMeasuredWidth() - childrenWidth > childWidth) {
                left = leftPosition + tagsMargin;
                top = topPosition + tagsMargin;
                right = left + childWidth;
                bottom = top + childHeight;
                childrenWidth = right + tagsMargin;
            }else { //换行
                //换行后顶部的坐标自加一个view的高度，来起到换行的作用
                topPosition += childHeight + tagsMargin;
                childrenWidth = childWidth + tagsMargin;

                leftPosition = 0;
                left = leftPosition + tagsMargin;
                top = topPosition + tagsMargin;
                right = left + childWidth;
                bottom = top + childHeight;
            }
            leftPosition += childWidth + tagsMargin;
            view.layout(left, top, right, bottom);
        }
    }

    @Override
    public void onClick(View v) {

    }

    private OnTagsClickListener listener;

    public void setOnTagsOnClickListener(OnTagsClickListener listener) {
        this.listener = listener;
    }

    public interface OnTagsClickListener {
        void onTagsClick(View view, String tagText);
    }
}
