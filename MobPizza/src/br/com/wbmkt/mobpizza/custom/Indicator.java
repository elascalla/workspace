package br.com.wbmkt.mobpizza.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class Indicator extends View implements OnTouchListener {
	private static final float MAXIMO_TELA = 200;
	private static final float MAXIMO_ALTURA = 30;
	
	private float x;
	
	public Indicator(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		setOnTouchListener(this);
		setMinimumHeight((int)MAXIMO_ALTURA);
		setMinimumWidth((int)MAXIMO_TELA);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(getSuggestedMinimumWidth(), getSuggestedMinimumHeight());
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		Paint p = new Paint();
		
		if (getValor() > 5.0) {
			p.setColor(Color.BLUE);
		} else {
			p.setColor(Color.RED);
		}
		
		RectF r = new RectF(0, 0, x, MAXIMO_ALTURA);
		
		canvas.drawColor(Color.GRAY);
		canvas.drawRect(r, p);
		
		Paint textPaint = new Paint();
		textPaint.setColor(Color.WHITE);
		textPaint.setTextSize(12);
		textPaint.setTextAlign(Align.CENTER);
		textPaint.setTypeface(Typeface.DEFAULT_BOLD);
		canvas.drawText(String.valueOf(getValor()), (MAXIMO_TELA / 2), (MAXIMO_ALTURA / 2), textPaint);
	}

	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				x = event.getX();
				invalidate();
				break;
			default:
				return false;
		}
		
		return false;
	}

	public float getValor() {
		return x * 10 / MAXIMO_TELA;
	}

	public void setValor(float x) {
		this.x = (float)(MAXIMO_TELA * x / 10);
	}
}
