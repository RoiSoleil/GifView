package org.roisoleil.gifview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;

public class GifViewLayout extends FrameLayout implements OnClickListener {

	protected GifView mGifView;

	protected View mPauseView;

	public GifViewLayout(Context context) {
		this(context, null);
	}

	public GifViewLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public GifViewLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mGifView = createGifView(context, attrs);
		mPauseView = createPauseView();
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		addView(mGifView, params);
		addView(mPauseView, params);
	}

	protected GifView createGifView(Context context, AttributeSet attrs) {
		GifView gifView = new GifView(context, attrs,
				R.styleable.CustomTheme_gifViewStyle);
		gifView.setOnClickListener(this);
		return gifView;
	}

	protected View createPauseView() {
		LayoutInflater layoutInflater = LayoutInflater.from(getContext());
		View view = layoutInflater.inflate(R.layout.pause, this, false);
		view.setVisibility(View.GONE);
		view.setOnClickListener(this);
		return view;
	}

	@Override
	public void onClick(View v) {
		if (mGifView.isPaused()) {
			mPauseView.setVisibility(View.GONE);
			mGifView.setPaused(false);
		} else {
			mPauseView.setVisibility(View.VISIBLE);
			mGifView.setPaused(true);
		}
	}
}
