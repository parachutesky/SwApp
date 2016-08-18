package com.jnwat.swmobilegy;

import com.jnwat.swmobilegy.R;
import com.jnwat.tools.ModifySysTitle;

public class SoftDestn extends BaseActivity {

	@Override
	protected void initView() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setListener() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_soft_destn);

		
		ModifySysTitle.ModifySysTitleStyle(R.color.black_dark,
				SoftDestn.this);

		setBackListener("关于", false);
	}

}
