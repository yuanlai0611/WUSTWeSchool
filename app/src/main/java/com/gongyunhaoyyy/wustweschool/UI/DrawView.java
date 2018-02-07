package com.gongyunhaoyyy.wustweschool.UI;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

/**
 * Created by acer on 2017/12/10.
 */

public class DrawView extends View {
    double Y1=0,Y2=0;

    public DrawView(Context context, double Y1, double Y2) {
        super( context );
        this.Y1=Y1;
        this.Y2=Y2;
    }
    public DrawView(Context context) {
        super( context );
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 创建画笔
        Paint p = new Paint();

        p.setColor( Color.rgb( 19,177,249 ) );
        p.setStyle(Paint.Style.FILL);//设置填满
        // 绘制任意多边形
        Path path1 = new Path();
        path1.moveTo(0, 0);// 此点为多边形的起点
        path1.lineTo(1500, 0);
        path1.lineTo(1500, 580);
        path1.lineTo(0, 780);
        path1.close(); // 使这些点构成封闭的多边形
        canvas.drawPath(path1, p);

        p.setColor( Color.rgb( 255,191,64 ) );
        Path path2 = new Path();
        path2.moveTo(0, 780);// 此点为多边形的起点
        path2.lineTo(1500, 580);
        path2.lineTo(1500, 1550);
        path2.lineTo(0, 1850);
        path2.close(); // 使这些点构成封闭的多边形
        canvas.drawPath(path2, p);

        p.setColor( Color.rgb( 255,129,64 ) );
        Path path3 = new Path();
        path3.moveTo(0, 1850);// 此点为多边形的起点
        path3.lineTo(1500, 1550);
        path3.lineTo(1500, 2100);
        path3.lineTo(0, 2500);
        path3.close(); // 使这些点构成封闭的多边形
        canvas.drawPath(path3, p);

    }

}
