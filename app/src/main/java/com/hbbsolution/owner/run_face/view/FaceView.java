/*
 * Copyright (C) The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hbbsolution.owner.run_face.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.Landmark;

/**
 * View which displays a bitmap containing a face along with overlay graphics that identify the
 * locations of detected facial landmarks.
 */
public class FaceView extends View {
    private final Object mLock = new Object();
    private int mPreviewWidth;
    private float mWidthScaleFactor = 1.0f;
    private int mPreviewHeight;
    private float mHeightScaleFactor = 1.0f;
    private int mFacing = CameraSource.CAMERA_FACING_BACK;
    private Bitmap faceBitmap;
    private static final float FACE_POSITION_RADIUS = 10.0f;
    private Bitmap mBitmap;
    private SparseArray<Face> mFaces;
    public Rect mRect;
    private int[] mColors = {
            Color.RED,
            Color.BLUE,
            Color.YELLOW,
            Color.CYAN,
            Color.MAGENTA,
            Color.DKGRAY,
    };

    public float scaleX(float horizontal) {
        return horizontal * mWidthScaleFactor;
    }

    /**
     * Adjusts a vertical value of the supplied value from the preview scale to the view scale.
     */
    public float scaleY(float vertical) {
        return vertical * mHeightScaleFactor;
    }

    public float translateX(float x) {
        if (mFacing == CameraSource.CAMERA_FACING_FRONT) {
            return getWidth() - scaleX(x);
        } else {
            return scaleX(x);
        }
    }

    /**
     * Adjusts the y coordinate from the preview's coordinate system to the view coordinate
     * system.
     */
    public float translateY(float y) {
        return scaleY(y);
    }

    public FaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FaceView(Context context) {
        super(context);
    }

    /**
     * Sets the bitmap background and the associated face detections.
     */
    void setContent(Bitmap bitmap, SparseArray<Face> faces) {
        mBitmap = bitmap;
        mFaces = faces;
        invalidate();
    }

    /**
     * Draws the bitmap background and the associated face landmarks.
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if ((mBitmap != null) && (mFaces != null)) {
            double scale = drawBitmap(canvas);
            Log.d("SCALE", "" + scale);
            drawFaceAnnotations(canvas, scale);
        }
    }

    /**
     * Draws the bitmap background, scaled to the device size.  Returns the scale for future use in
     * positioning the facial landmark graphics.
     */
    private double drawBitmap(Canvas canvas) {
        double viewWidth = canvas.getWidth();
        double viewHeight = canvas.getHeight();
        double imageWidth = mBitmap.getWidth();
        double imageHeight = mBitmap.getHeight();
        Log.d("FIRST_1", "" + imageWidth + "/ " + imageHeight);
        double scale = Math.min(viewWidth / imageWidth, viewHeight / imageHeight);


        //Rect destBounds = new Rect(0, 0, (int) (imageWidth * scale), (int) (imageHeight * scale));
        // canvas.drawBitmap(mBitmap, null, destBounds, null);

//        Bitmap workingBitmap = Bitmap.createBitmap(mBitmap);
//        Bitmap mutableBitmap = workingBitmap.copy(Bitmap.Config.ARGB_8888, true);
//        canvas = new Canvas(mutableBitmap);


        BitmapShader bitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(1);
        paint.setShader(bitmapShader);

        //  Bitmap mutableBitmap = mBitmap.copy(Bitmap.Config.ARGB_8888, true);
        // canvas = new Canvas(mutableBitmap);

        canvas.drawCircle((float) viewWidth / 2, (float) viewHeight / 2, (float) viewWidth / 2, paint);
        // canvas.drawBitmap(mBitmap, null, destBounds, null);
        return scale;
    }

    /**
     * Draws a small circle for each detected landmark, centered at the detected landmark position.
     * <p>
     * <p>
     * Note that eye landmarks are defined to be the midpoint between the detected eye corner
     * positions, which tends to place the eye landmarks at the lower eyelid rather than at the
     * pupil position.
     */
    private void drawFaceAnnotations(final Canvas canvas, final double scale) {
        final Paint paintLine = new Paint();
        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setStrokeWidth(1);
        paintLine.setColor(Color.GREEN);

        Paint mFacePositionPaint = new Paint();
        mFacePositionPaint.setStyle(Paint.Style.STROKE);
        mFacePositionPaint.setStrokeWidth(2);
        mFacePositionPaint.setColor(Color.RED);

        for (int i = 0; i < mFaces.size(); ++i) {
            Face face = mFaces.valueAt(i);
//            // Draws a bounding box around the face.
//            // Draws a circle at the position of the detected face, with the face's track id below.
//            float x = (int) (translateX(face.getPosition().x + face.getWidth() / 2) * scale);
//            float y = (int) (translateY(face.getPosition().y + face.getHeight() / 2) * scale);
////            float xOffset = scaleX(face.getWidth() / 2.0f);
//
////            float yOffset = scaleY(face.getHeight() / 2.0f);
//            float xOffset = (float) (scaleX(face.getWidth() / 2.0f) * scale);
//            float yOffset = (float) (scaleY(face.getHeight() / 2.0f) * scale);
////            float xOffset = 40;
////            float yOffset = 40;
//            float left = x - xOffset;
//            float top = y - yOffset;
//            float right = x + xOffset;
//            float bottom = y + yOffset;
//            if (top < 0) {
//                top = 0;
//            }
//            if (left < 0) {
//                left = 0;
//            }
//            if (bottom > canvas.getHeight()) {
//                bottom = canvas.getHeight();
//            }

            for (int j = 0; j < face.getLandmarks().size(); j++) {
                Landmark landmark = face.getLandmarks().get(j);
                final Paint paint = new Paint();
                paint.setStyle(Paint.Style.FILL_AND_STROKE);
                paint.setStrokeWidth(1);
                paint.setColor(mColors[j % mColors.length]);
                final int cx = (int) (landmark.getPosition().x );
                final int cy = (int) (landmark.getPosition().y );
                //canvas.drawCircle(cx, cy, 5, paint);

                Landmark landmarkPre = face.getLandmarks().get((j + 2) % face.getLandmarks().size());
                final int cxPre = (int) (landmarkPre.getPosition().x);
                final int cyPre = (int) (landmarkPre.getPosition().y);
                canvas.drawLine(cx, cy, cxPre, cyPre, paintLine);

                Landmark landmarkNext = face.getLandmarks().get((j + 1) % face.getLandmarks().size());
                final int cxNext = (int) (landmarkNext.getPosition().x );
                final int cyNext = (int) (landmarkNext.getPosition().y );
                canvas.drawLine(cx, cy, cxNext, cyNext, paintLine);
            }
        }
    }
}
