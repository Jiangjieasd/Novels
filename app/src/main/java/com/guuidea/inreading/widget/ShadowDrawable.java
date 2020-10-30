package com.guuidea.inreading.widget;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;

import com.guuidea.inreading.utils.ScreenUtils;

/**
 * @author 江 杰
 * @file ShadowDrawable
 * @description 阴影
 * @createDate 2020/10/29 16:39
 */
public class ShadowDrawable extends Drawable {

    private final Paint mShadowPaint;
    private final Paint mBgPaint;
    private final Paint mPaint;
    private final Paint mBgPartPaint;
    private final int mShadowRadius;
    private final int mShape;
    private final int mShapeRadius;
    private final int[] mBgColor;
    private RectF mRect;
    private final TypeEnum type;
    private static final int SHAPE_ROUND = 1;
    private static final int SHAPE_ROUND_PART = 3;

    public enum TypeEnum {
        /**
         * 各边均绘制阴影
         */
        ALL,
        /**
         * 左侧绘制阴影
         */
        LEFT,
        /**
         * 上侧绘制阴影
         */
        TOP,
        /**
         * 右侧绘制阴影
         */
        RIGHT,
        /**
         * 底部绘制阴影
         */
        BOTTOM
    }


    private ShadowDrawable(int shape,
                           TypeEnum type,
                           int[] bgColor,
                           int shapeRadius,
                           int shadowColor,
                           int shadowRadius) {
        this.mShape = shape;
        this.type = type;
        this.mBgColor = bgColor;
        this.mShapeRadius = shapeRadius;
        this.mShadowRadius = shadowRadius;
        // 阴影paint
        mShadowPaint = new Paint();
        mShadowPaint.setAntiAlias(true);
        mShadowPaint.setShadowLayer(shadowRadius, 0, 0, shadowColor);
        //覆盖部分背景的paint
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        //背景paint
        mBgPaint = new Paint();
        mBgPaint.setAntiAlias(true);
        //覆盖部分圆角的paint
        mBgPartPaint = new Paint();
        mBgPartPaint.setAntiAlias(true);
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);
        switch (type) {
            case ALL:
                left += mShadowRadius;
                top += mShadowRadius;
                right -= mShadowRadius;
                bottom -= mShadowRadius;
                break;
            case TOP:
            case BOTTOM:
                top += mShadowRadius;
                bottom -= mShadowRadius;
                break;
            case LEFT:
            case RIGHT:
                left += mShadowRadius;
                right -= mShadowRadius;
                break;
            default:
                break;
        }


        mRect = new RectF(left, top, right, bottom);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        if (mBgColor != null) {
            if (mBgColor.length == 1) {
                mBgPaint.setColor(mBgColor[0]);
                mShadowPaint.setColor(mBgColor[0]);
                mBgPartPaint.setColor(mBgColor[0]);
            } else {
                mBgPaint.setShader(new LinearGradient(mRect.left, mRect.height() / 2, mRect.right,
                        mRect.height() / 2, mBgColor, null, Shader.TileMode.CLAMP));
            }
        }
        //设置覆盖阴影/圆角 的 RectF
        RectF rect = new RectF();
        RectF rect1 = new RectF();
        calculate(rect, rect1);

        if (mShape == SHAPE_ROUND) {
            //绘制全圆角阴影
            canvas.drawRoundRect(mRect, mShapeRadius, mShapeRadius, mShadowPaint);
            canvas.drawRect(rect, mPaint);
            canvas.drawRoundRect(mRect, mShapeRadius, mShapeRadius, mBgPaint);
        } else if (mShape == SHAPE_ROUND_PART) {
            //绘制部分圆角阴影
            canvas.drawRoundRect(mRect, mShapeRadius, mShapeRadius, mShadowPaint);
            canvas.drawRect(rect, mPaint);
            canvas.drawRoundRect(mRect, mShapeRadius, mShapeRadius, mBgPaint);
            canvas.drawRect(rect1, mBgPartPaint);
        } else {
            //绘制圆形阴影
            canvas.drawCircle(mRect.centerX(), mRect.centerY(),
                    Math.min(mRect.width(), mRect.height()) / 2, mShadowPaint);
            canvas.drawRect(rect, mPaint);
            canvas.drawCircle(mRect.centerX(), mRect.centerY(),
                    Math.min(mRect.width(), mRect.height()) / 2, mBgPaint);
        }
    }

    private void calculate(RectF rect, RectF rect1) {
        switch (type) {
            case ALL:
                rect.left = 0;
                rect.right = 0;
                rect.top = 0;
                rect.bottom = 0;
                break;
            case TOP:
                rect.left = mRect.left;
                rect.right = mRect.right;
                rect.top = mRect.height() / 2;
                rect.bottom = mRect.bottom + mShadowRadius;
                rect1.left = mRect.left;
                rect1.right = mRect.right;
                rect1.top = mRect.height() / 2;
                rect1.bottom = mRect.bottom;
                break;
            case BOTTOM:
                rect.left = mRect.left;
                rect.right = mRect.right;
                rect.top = mRect.top - mShadowRadius;
                rect.bottom = mRect.height() / 2;

                rect1.left = mRect.left;
                rect1.right = mRect.right;
                rect1.top = mRect.top;
                rect1.bottom = mRect.height() / 2;

                break;
            case LEFT:
                rect.left = mRect.width() / 2;
                rect.right = mRect.right + mShadowRadius;
                rect.top = mRect.top;
                rect.bottom = mRect.bottom;

                rect1.left = mRect.width() / 2;
                rect1.right = mRect.right;
                rect1.top = mRect.top;
                rect1.bottom = mRect.bottom;

                break;
            case RIGHT:
                rect.left = mRect.left - mShadowRadius;
                rect.right = mRect.width() / 2;
                rect.top = mRect.top;
                rect.bottom = mRect.bottom;

                rect1.left = mRect.left;
                rect1.right = mRect.width() / 2;
                rect1.top = mRect.top;
                rect1.bottom = mRect.bottom;
                break;

            default:
                break;
        }
    }

    @Override
    public void setAlpha(int alpha) {
        mShadowPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        mShadowPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    /****************************************公有设置method**************************************/

    public static void setShadowDrawable(View view) {
        ShadowDrawable drawable = new Builder()
                .setType(TypeEnum.BOTTOM)
                .builder();
        view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        ViewCompat.setBackground(view, drawable);
    }

    public static void setShadowDrawable(View view, String bgColor) {
        ShadowDrawable drawable = new Builder()
                .setType(TypeEnum.BOTTOM)
                .setBgColor(bgColor)
                .builder();
        view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        ViewCompat.setBackground(view, drawable);
    }

    public static void setShadowDrawable(View view, TypeEnum type) {
        ShadowDrawable drawable = new Builder()
                .setType(type)
                .builder();
        view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        ViewCompat.setBackground(view, drawable);
    }

    public static void setShadowDrawableCircle(View view) {
        int SHAPE_CIRCLE = 2;
        ShadowDrawable drawable = new Builder()
                .setShape(SHAPE_CIRCLE)
                .setType(TypeEnum.ALL)
                .builder();
        view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        ViewCompat.setBackground(view, drawable);
    }

    public static void setShadowDrawableTop(View view, String shapeColor) {
        ShadowDrawable drawable = new Builder()
                .setShape(SHAPE_ROUND_PART)
                .setType(TypeEnum.TOP)
                .setShadowColor(shapeColor)
                .builder();
        view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        ViewCompat.setBackground(view, drawable);
    }

    public static void setShadowDrawable(View view,
                                         String shapeColor,
                                         int shapeRadius,
                                         String shadowColor,
                                         int shadowRadius) {
        ShadowDrawable drawable = new Builder()
                .setBgColor(shapeColor)
                .setShapeRadius(shapeRadius)
                .setShadowColor(shadowColor)
                .setShadowRadius(shadowRadius)
                .builder();
        view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        ViewCompat.setBackground(view, drawable);
    }

    public static void setShadowDrawable(View view,
                                         int shape,
                                         TypeEnum type,
                                         String shapeColor,
                                         int shapeRadius,
                                         String shadowColor,
                                         int shadowRadius) {
        ShadowDrawable drawable = new Builder()
                .setBgColor(shapeColor)
                .setShapeRadius(shapeRadius)
                .setShadowColor(shadowColor)
                .setShadowRadius(shadowRadius)
                .setShape(shape)
                .setType(type)
                .builder();
        view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        ViewCompat.setBackground(view, drawable);
    }

    public static void setShadowDrawable(View view,
                                         int shape,
                                         TypeEnum type,
                                         String[] shapeColor,
                                         int shapeRadius,
                                         String shadowColor,
                                         int shadowRadius) {
        ShadowDrawable drawable = new Builder()
                .setBgColor(shapeColor)
                .setShapeRadius(shapeRadius)
                .setShadowColor(shadowColor)
                .setShadowRadius(shadowRadius)
                .setShape(shape)
                .setType(type)
                .builder();
        view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        ViewCompat.setBackground(view, drawable);
    }

    /*******************************************建造器******************************************/
    private static class Builder {
        private int mShape;
        private int mShapeRadius;
        private int mShadowColor;
        private int mShadowRadius;
        private int[] mBgColor;
        private TypeEnum type;

        private Builder() {
            mShape = SHAPE_ROUND;
            type = TypeEnum.ALL;
            mShapeRadius = 17;
            mShadowColor = Color.parseColor("#FF0000");
            mShadowRadius = 4;
            mBgColor = new int[1];
            mBgColor[0] = Color.GREEN;
        }

        private Builder setShape(int mShape) {
            this.mShape = mShape;
            return this;
        }

        private Builder setShapeRadius(int shapeRadius) {
            this.mShapeRadius = shapeRadius;
            return this;
        }

        private Builder setShadowColor(String shadowColor) {

            this.mShadowColor = Color.parseColor(shadowColor);
            return this;
        }

        private Builder setShadowRadius(int shadowRadius) {
            this.mShadowRadius = shadowRadius;
            return this;
        }

        private Builder setBgColor(String bgColor) {
            this.mBgColor[0] = Color.parseColor(bgColor);
            return this;
        }

        private Builder setType(TypeEnum type) {
            this.type = type;
            return this;
        }


        private Builder setBgColor(String[] bgColors) {
            if (bgColors != null && bgColors.length > 0) {
                int[] b = new int[bgColors.length];
                for (int i = 0; i < bgColors.length; i++) {
                    b[i] = Color.parseColor(bgColors[i]);
                }
                this.mBgColor = b;
            }

            return this;
        }

        private ShadowDrawable builder() {
            return new ShadowDrawable(mShape, type, mBgColor, ScreenUtils.dpToPx(mShapeRadius),
                    mShadowColor, ScreenUtils.dpToPx(mShadowRadius));
        }
    }
}
