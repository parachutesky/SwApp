package com.jnwat.swmobilegy;

import com.jnwat.swmobilegy.R;
import com.jnwat.tools.ModifySysTitle;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


/**
 * @author chang-zhiyuan 搜索页面
 * 
 */
public class SerchActivity extends BaseActivity {
	private static ImageView iv_back = null;
	private static TextView title_bar_name = null;
    private EditText serch_edt;
    private ListView serch_show_list;
	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		// SetHeadListener.setBackListener("");
		setBackListener("搜索",true);
		serch_edt = (EditText) this.findViewById(R.id.serch_edt);
		serch_show_list = (ListView) this.findViewById(R.id.serch_show_list);
	}

	@Override
	protected void setListener() {
		// TODO Auto-generated method stub
		serch_edt.addTextChangedListener(new editTextChangeList());
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_serch);
		ModifySysTitle.ModifySysTitleStyle(R.color.black_dark, SerchActivity.this);
		
	}
	// 监听输入框 内容
	class editTextChangeList implements TextWatcher{

		@Override
		public void afterTextChanged(Editable arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTextChanged(CharSequence content, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub
			String str = content.toString().toUpperCase();
			
			
		}
		
	}
   
}
