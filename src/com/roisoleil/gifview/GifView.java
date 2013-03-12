package com.roisoleil.gifview;

import java.io.InputStream;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Movie;
import android.net.Uri;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;

public class GifView extends View {

	private Movie movie;

	private long startTime;

	private int drawableId;

	public GifView(Context context) {
		super(context);
	}

	public GifView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setAttrs(attrs);
		initializeView();
	}

	public GifView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setAttrs(attrs);
		initializeView();
	}

	public void setInputStram(InputStream inputStream) {
		initializeView(inputStream);
	}

	public void setDrawable(int drawableId) {
		this.drawableId = drawableId;
		initializeView();
	}

	public int getDrawable() {
		return drawableId;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawColor(Color.TRANSPARENT);
		final long actualTime = SystemClock.uptimeMillis();
		if (startTime == 0) {
			startTime = actualTime;
		}
		if (movie != null) {
			final int relativeTime = (int) ((actualTime - startTime) % movie
					.duration());
			movie.setTime(relativeTime);
			double scaleFactorX = (double) getWidth() / (double) movie.width();
			canvas.scale((float) scaleFactorX, (float) scaleFactorX);
			movie.draw(canvas, (float) scaleFactorX, (float) scaleFactorX);
		}
		invalidate();
	}

	private void initializeView() {
		if (drawableId != 0) {
			InputStream inputStream = getContext().getResources()
					.openRawResource(drawableId);
			initializeView(inputStream);
		}
	}

	private void initializeView(InputStream inputStream) {
		if (inputStream != null) {
			movie = Movie.decodeStream(inputStream);
			startTime = 0;
			invalidate();
		}
	}

	private void setAttrs(AttributeSet attrs) {
		if (attrs != null) {
			TypedArray a = getContext().obtainStyledAttributes(attrs,
					R.styleable.GifView, 0, 0);
			String gifSource = a.getString(R.styleable.GifView_drawable);
			if (gifSource != null) {
				String sourceName = Uri.parse(gifSource).getLastPathSegment()
						.replace(".gif", "");
				setDrawable(getResources().getIdentifier(sourceName,
						"drawable", getContext().getPackageName()));
			}
			a.recycle();
		}
	}

}
