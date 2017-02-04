package org.hades.wechat_align_bottom.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.hades.wechat_align_bottom.R;
import org.hades.wechat_align_bottom.view.MyListView;

import java.util.ArrayList;

/**
 * Created by Hades on 17/2/4.
 */
public class MyAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> datas;//数据集
    private MyListView listView;
    private EditText commentEt;//评论输入框
    private View inputLayout;//输入布局
    private boolean isShow = false;//是否在显示
    //位置参数
    private int clickBottom = 0, clickPosition = 0, clickHeight = 0, newH = 0, offset = 0;


    public MyAdapter(Context context, final MyListView listView, ArrayList<String> datas) {
        this.context = context;
        this.listView = listView;
        this.datas = datas;

        initInputLayout();

        //设置监听器
        listView.setSizeChangeListener(new MyListView.OnReSizeListener() {
            @Override
            public void onResize(int w, int h, int oldw, final int oldh) {
                if (oldh > h) {
                    //一旦旧的高度大于新的高度就说明键盘此时弹出
                    offset = (oldh - h + inputLayout.getHeight()) - (oldh - clickBottom);
                    newH = h;
                    //滑动列表
                    listView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            listView.scrollListBy(offset);
                        }
                    }, 20);
                }
            }
        });
    }

    @Override
    public int getCount() {
        return (datas.size());
    }

    @Override
    public Object getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return (long) i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        final int position = i;
        Log.d("t", "position = " + i + "the view = " + getItemId(i) + "Count = " + getCount());
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false);
            holder = new ViewHolder();
            holder.icon = (ImageView) view.findViewById(R.id.icon);
            holder.content = (TextView) view.findViewById(R.id.content);
            holder.commentIv = (ImageView) view.findViewById(R.id.comment_pressed);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.content.setText("Content:" + datas.get(i));

        holder.commentIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputLayout();
                clickBottom = listView.getChildAt(position - listView.getFirstVisiblePosition()).getBottom();
                clickHeight = listView.getChildAt(position - listView.getFirstVisiblePosition()).getHeight();
                clickPosition = position;
            }
        });

        /**
         * 这里也是关键，因为添加一条空的数据，但是必须每一条都检测
         * 不是空的数据要将他显示，否则会是容器出现多个不能显示但是
         * 占据布局的VIew
         */
        if (datas.get(i).equals("")) {
            view.setVisibility(View.INVISIBLE);
        } else {
            view.setVisibility(View.VISIBLE);
        }

        return view;
    }

    private static class ViewHolder {
        ImageView icon;
        TextView content;
        ImageView commentIv;
    }

    private void showInputLayout() {
        inputLayout.setVisibility(View.VISIBLE);
        openSoftKeyboard(commentEt);
        setShow(true);
    }


    private void initInputLayout() {
        inputLayout = ((Activity) context).findViewById(R.id.input_layer);
        commentEt = (EditText) ((Activity) context).findViewById(R.id.comment_et);
        inputLayout.setVisibility(View.GONE);

    }

    private void openSoftKeyboard(EditText et) {
        et.requestFocus();
        InputMethodManager inputManager = (InputMethodManager) et.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(et, 0);
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }
}
