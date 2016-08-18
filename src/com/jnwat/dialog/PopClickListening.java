package com.jnwat.dialog;

import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * Created by Parachute on 14/12/15.
 */

/**
 * 实现POP 确定和取消的两个接口
 * View.OnClickListener
 */
public interface PopClickListening {
    //  public void PopPositiveLister(android.view.View.OnClickListener l);

    //    public void PopNegativeLister(android.view.View.OnClickListener l);
    void PopPositiveLister();

    void PopNegativeLister();
    void EditPopPositiveLister(EditText et);

    void EditPopNegativeLister(EditText et);
    
    abstract void setListViewForName(ListView lv,LinearLayout lin);

}