package com.example.tablelayout;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.Nullable;

/**
 * @author zwp
 * @description:
 * @date: 2020/11/18 15:26
 */
public class TableView extends LinearLayout {
    private int width, height;
    private int rows, columns;
    private Context mContext;
    private TableLayout mTableHead, mTableContent;
    private OnTableClick mOnTableClick;
    private final int MP = ViewGroup.LayoutParams.MATCH_PARENT;
    private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;

    public TableView(Context context) {
        super(context);
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        width = metrics.widthPixels;//获取了屏幕的宽度
        height = metrics.heightPixels;//获取屏幕的高度
        this.mContext = context;
    }

    public TableView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        width = metrics.widthPixels;//获取了屏幕的宽度
        height = metrics.heightPixels;//获取屏幕的高度
        this.mContext = context;
    }

    /**
     * 设置表格的行数和列数
     *
     * @param rows
     * @param columns
     */
    public void setTable(int rows, int columns, OnTableClick onTableClick) {
        this.rows = rows;
        this.columns = columns;
        this.mOnTableClick = onTableClick;
    }

    /**
     * 设置表头
     */
    public void setTableHead(String[] headTitles) {
        initTableHead(headTitles);
    }

    /**
     * 初始化表头
     */
    private void initTableHead(String[] titles) {
        LayoutInflater.from(mContext).inflate(R.layout.tableview, this);
        mTableHead = this.findViewById(R.id.table_head);
        mTableHead.setStretchAllColumns(true);// 设置所有单元格都可以伸缩
        TableRow rowHead = new TableRow(mContext);//动态构建四个单元格
        rowHead.setBackgroundColor(Color.rgb(255, 255, 255));
        for (int col = 0; col < columns; col++) {
            TextView tvHeadUnit = new TextView(mContext);
            tvHeadUnit.setWidth(width / columns);
            tvHeadUnit.setHeight(100);
            tvHeadUnit.setTextSize(18);
            tvHeadUnit.setGravity(Gravity.CENTER);
            tvHeadUnit.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));// 文字加粗
            tvHeadUnit.setTextColor(Color.rgb(255, 255, 255));//文本颜色为白色
            tvHeadUnit.setBackgroundResource(R.drawable.shape_head);
            if (col < titles.length)
                tvHeadUnit.setText(titles[col]);
            rowHead.addView(tvHeadUnit, col);
        }
        mTableHead.addView(rowHead, MP, WC);
    }

    /**
     * 设置表格内容
     */
    public void setTableContent() {
        initTableContent();
    }

    /**
     * 初始化表格内容
     */
    private void initTableContent() {
        LayoutInflater.from(mContext).inflate(R.layout.tableview, this);
        mTableContent = this.findViewById(R.id.table_content);
        mTableContent.setStretchAllColumns(true);
        for (int row = 0; row < rows; row++) {
            TableRow rowContent = new TableRow(mContext);
            rowContent.setBackgroundColor(Color.rgb(255, 255, 255));
            for (int col = 0; col < columns; col++) {
                TextView tvContentUnit = new TextView(mContext);
                tvContentUnit.setWidth(width / columns);
                tvContentUnit.setHeight(100);
                tvContentUnit.setGravity(Gravity.CENTER);
                tvContentUnit.setText("" + row + col);
                //点击表格内部数据的监听事件
                tvContentUnit.setOnClickListener(new MyTableListener(row, col, mOnTableClick));
                if (col < columns - 1) {
                    tvContentUnit.setBackgroundResource(R.drawable.shape_left);
                } else {
                    tvContentUnit.setBackgroundResource(R.drawable.shape_right);
                }
                rowContent.addView(tvContentUnit, col);
            }
            mTableContent.addView(rowContent, MP, WC);
        }
    }
}

class MyTableListener implements ViewGroup.OnClickListener {
    int row;
    int col;
    OnTableClick onTableClick;

    public MyTableListener(int row, int col, OnTableClick onTableClick) {
        this.row = row;
        this.col = col;
        this.onTableClick = onTableClick;
    }

    @Override
    public void onClick(View v) {
        onTableClick.onTableClickListener(row, col);
    }
}

//回调接口
interface OnTableClick {
    public void onTableClickListener(int row, int col);
}
