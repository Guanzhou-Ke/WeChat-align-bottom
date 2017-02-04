package org.hades.wechat_align_bottom.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by Hades on 17/2/4.
 */
public class MyListView extends ListView {

    private OnReSizeListener listener;

    public interface OnReSizeListener{
        void onResize(int w, int h, int oldw, int oldh);
    }

    public void setSizeChangeListener(OnReSizeListener listener) {
        this.listener = listener;
    }

    public MyListView(Context context) {
        super(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        listener.onResize(w, h, oldw, oldh);
    }
}
