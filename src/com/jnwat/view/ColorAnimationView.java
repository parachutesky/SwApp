package com.jnwat.view;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
/**
 * Created by TaurusXi on 2014/5/20.
 */
public class ColorAnimationView
		extends View
		implements ValueAnimator.AnimatorUpdateListener, Animator.AnimatorListener {
	private static final int RED = 0xffFF8080;
	private static final int BLUE = 0xff8080FF;
	private static final int WHITE = 0xffffffff;
	private static final int GREEN = 0xff80ff80;
	private static final int DURATION = 3000;
	ValueAnimator colorAnim = null;

	private PageChangeListener mPageChangeListener;

	ViewPager.OnPageChangeListener onPageChangeListener;

	public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
		this.onPageChangeListener = onPageChangeListener;
	}



	/**
	 * 杩欐槸浣犲敮涓�渶瑕佸叧蹇冪殑鏂规硶
	 * @param mViewPager  浣犲繀椤诲湪璁剧疆 Viewpager 鐨�Adapter 杩欏悗锛屾墠鑳借皟鐢ㄨ繖涓柟娉曘�
	 * @param obj ,杩欎釜obj瀹炵幇浜�ColorAnimationView.OnPageChangeListener 锛屽疄鐜板洖璋�	 * @param count   ,viewpager瀛╁瓙鐨勬暟閲�	 * @param colors int... colors 锛屼綘闇�璁剧疆鐨勯鑹插彉鍖栧�~~ 濡備綍浣犱紶浜�绌猴紝閭ｄ箞瑙﹀彂榛樿璁剧疆鐨勯鑹插姩鐢�	 * */
	/**
	 * This is the only method you need care about.
	 * @param mViewPager  ,you need set the adpater before you call this.
	 * @param count   ,this param set the count of the viewpaper's child
	 * @param colors ,this param set the change color use (int... colors),
	 *               so,you could set any length if you want.And by default.
	 *               if you set nothing , don't worry i have already creat
	 *               a default good change color!
	 * */
	public void setmViewPager(ViewPager mViewPager, int count, int... colors) {
//		this.mViewPager = mViewPager;
		if (mViewPager.getAdapter() == null) {
			throw new IllegalStateException(
					"ViewPager does not have adapter instance.");
		}
		mPageChangeListener.setViewPagerChildCount(count);

		mViewPager.setOnPageChangeListener(mPageChangeListener);
		if (colors.length == 0) {
			createDefaultAnimation();
		} else {
			createAnimation(colors);
		}

	}

	public ColorAnimationView(Context context) {
		this(context, null, 0);

	}

	public ColorAnimationView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ColorAnimationView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mPageChangeListener = new PageChangeListener();
	}

	private void seek(long seekTime) {
		if (colorAnim == null) {
			createDefaultAnimation();
		}
		colorAnim.setCurrentPlayTime(seekTime);
	}

	private void createAnimation(int... colors) {
		if (colorAnim == null) {
			colorAnim = ObjectAnimator.ofInt(this,
					"backgroundColor", colors);
			colorAnim.setEvaluator(new ArgbEvaluator());
			colorAnim.setDuration(DURATION);
			colorAnim.addUpdateListener(this);
		}
	}

	private void createDefaultAnimation() {
		colorAnim = ObjectAnimator.ofInt(this,
				"backgroundColor", WHITE, RED, BLUE, GREEN, WHITE);
		colorAnim.setEvaluator(new ArgbEvaluator());
		colorAnim.setDuration(DURATION);
		colorAnim.addUpdateListener(this);
	}

	@Override public void onAnimationStart(Animator animation) {

	}

	@Override public void onAnimationEnd(Animator animation) {
	}

	@Override public void onAnimationCancel(Animator animation) {

	}

	@Override public void onAnimationRepeat(Animator animation) {

	}

	@Override public void onAnimationUpdate(ValueAnimator animation) {
		invalidate();
//		long playtime = colorAnim.getCurrentPlayTime();
	}

	private class PageChangeListener
			implements ViewPager.OnPageChangeListener {

		private int viewPagerChildCount;

		public void setViewPagerChildCount(int viewPagerChildCount) {
			this.viewPagerChildCount = viewPagerChildCount;
		}

		public int getViewPagerChildCount() {
			return viewPagerChildCount;
		}

		@Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			int count = getViewPagerChildCount() - 1;
			if (count != 0) {
				float length = (position + positionOffset) / count;
				int progress = (int) (length * DURATION);
				ColorAnimationView.this.seek(progress);
			}
			// call the method by default
            if (onPageChangeListener!=null){
                onPageChangeListener.onPageScrolled(position,positionOffset,positionOffsetPixels);
            }

		}

		@Override public void onPageSelected(int position) {
            if (onPageChangeListener!=null) {
                onPageChangeListener.onPageSelected(position);
            }
		}

		@Override public void onPageScrollStateChanged(int state) {
            if (onPageChangeListener!=null) {
                onPageChangeListener.onPageScrollStateChanged(state);
            }
		}
	}
}


