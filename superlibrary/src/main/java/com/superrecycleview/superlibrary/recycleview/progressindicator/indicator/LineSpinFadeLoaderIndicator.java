package com.superrecycleview.superlibrary.recycleview.progressindicator.indicator;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Created by super南仔 on 07/28/16.
 * blog: http://supercwn.github.io/
 * GitHub: https://github.com/supercwn
 */
public class LineSpinFadeLoaderIndicator extends BallSpinFadeLoaderIndicator {


    @Override
    public void draw(Canvas canvas, Paint paint) {
        float radius=getWidth()/10;
        for (int i = 0; i < 8; i++) {
            canvas.save();
            Point point=circleAt(getWidth(),getHeight(),getWidth()/2.5f-radius,i*(Math.PI/4));
            canvas.translate(point.x, point.y);
            canvas.scale(scaleFloats[i], scaleFloats[i]);
            canvas.rotate(i*45);
            paint.setAlpha(alphas[i]);
            RectF rectF=new RectF(-radius,-radius/1.5f,1.5f*radius,radius/1.5f);
            canvas.drawRoundRect(rectF,5,5,paint);
            canvas.restore();
        }
    }

}
