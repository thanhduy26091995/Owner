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

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.hbbsolution.owner.R;
import com.hbbsolution.owner.base.BaseActivity;
import com.hbbsolution.owner.run_face.model.CompareImageModel;
import com.hbbsolution.owner.run_face.patch.SafeFaceDetector;
import com.hbbsolution.owner.utils.Constants;
import com.hbbsolution.owner.utils.EncodeImage;

/**
 * Demonstrates basic usage of the GMS vision face detector by running face landmark detection on a
 * photo and displaying the photo with associated landmarks in the UI.
 */
public class PhotoViewerActivity extends BaseActivity implements View.OnClickListener, PhotoViewerView {
    private static final String TAG = "PhotoViewerActivity";
    TextView textViewMatchRate;
    private FaceView overlayImageServer;
    private FaceView overlayImageCompare;
    ProgressBar progressBar;
    int mProcess;
    int mRateMatch = 70;
    int mDuration = 0;
    SparseArray<Face> facesTemp;

    private CompareImageModel mCompareImageModel;
    private Bitmap bitmapGallery, bitmapServer;
    private ProgressDialog mProgressDialog;
    private TextView mTextViewResult, mTextViewHeader;
    private Toolbar toolbar;
    private int PERCENT_RATE = (int) (Constants.CONFIDENCE_CORRECT_DEFAULT * 100);
    final int originalPos[] = new int[2];
    private FaceView view1, view2;
    private boolean fisrt = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_viewer);

        overlayImageServer = (FaceView) findViewById(R.id.faceView_server);
        overlayImageCompare = (FaceView) findViewById(R.id.faceView_compare_uri);
        textViewMatchRate = (TextView) findViewById(R.id.textview_matchrate);
        progressBar = (ProgressBar) findViewById(R.id.circularProgressbar);

        mTextViewResult = (TextView) findViewById(R.id.textView_result);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");
        mTextViewHeader = (TextView) toolbar.findViewById(R.id.title_toolbar);
        mTextViewHeader.setText(getResources().getString(R.string.xacnhannguoigiupviec));

        //get intent
        mCompareImageModel = (CompareImageModel) getIntent().getSerializableExtra("CompareImage");


        Double mConfidence = mCompareImageModel.getConfidence() * 100;
        mRateMatch = mConfidence.intValue();
        showProgress();
        try {
            bitmapGallery = EncodeImage.encodeImage(getRealPathFromURI(Uri.parse(mCompareImageModel.getImageGallery())));
            //bitmapGallery = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(mCompareImageModel.getImageGallery()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Glide.with(PhotoViewerActivity.this)
                .load(mCompareImageModel.getImageServer())
                .asBitmap()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        hideProgress();
                        bitmapServer = resource;
                        isLoadImageSuccess(true);
                    }
                });

    }


    private String getRealPathFromURI(Uri contentURI) {
        String result = "";
        try {
            Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
            if (cursor == null) { // Source is Dropbox or other similar local file path
                result = contentURI.getPath();
            } else {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                result = cursor.getString(idx);
                cursor.close();
            }
        } catch (Exception e) {

        }
        return result;
    }

    private void reversePosition() {
        overlayImageCompare.setTranslationX(0);
        overlayImageCompare.setTranslationY(0);

        overlayImageServer.setTranslationX(0);
        overlayImageServer.setTranslationY(0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        reversePosition();
    }

    private Bitmap cutImage(Bitmap bitmap, FaceView faceView) {
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, faceView.mRect.left, faceView.mRect.top, faceView.mRect.width(), faceView.mRect.height());
        return resizedBitmap;
    }


    public void transPositionView(FaceView faceView) {
        moveViewToScreenCenter(faceView);
    }

    public void transPositionViewCompare(FaceView faceView) {
        moveViewToScreenCenter(faceView);
    }

    private void moveViewToScreenCenter(final View view) {
        DisplayMetrics dm = new DisplayMetrics();
        View rootView = findViewById(R.id.root_view);
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int statusBarOffset = dm.heightPixels - rootView.getMeasuredHeight();

        view.getLocationOnScreen(originalPos);


        int xDest = dm.widthPixels / 2;
        xDest -= (view.getMeasuredWidth() / 2);
        int yDest = dm.heightPixels / 2 - (view.getMeasuredHeight() / 2) - statusBarOffset;

        view.animate()
                .alpha(0.5f)
                .translationX(xDest - originalPos[0])
                .translationY(yDest - originalPos[1])
                .setInterpolator(new AccelerateInterpolator())
                .setDuration(mDuration);

    }


    @Override
    public void onClick(View v) {


    }

    @Override
    public void isLoadImageSuccess(boolean result) {
        hideProgress();
        if (result) {
            // A new face detector is created for detecting the face and its landmarks.
            //
            // Setting "tracking enabled" to false is recommended for detection with unrelated
            // individual images (as opposed to video or a series of consecutively captured still
            // images).  For detection on unrelated individual images, this will give a more accurate
            // result.  For detection on consecutive images (e.g., live video), tracking gives a more
            // accurate (and faster) result.
            //
            // By default, landmark detection is not enabled since it increases detection time.  We
            // enable it here in order to visualize detected landmarks.
            FaceDetector detector = new FaceDetector.Builder(getApplicationContext())
                    .setTrackingEnabled(false)
                    .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                    .build();

            // This is a temporary workaround for a bug in the face detector with respect to operating
            // on very small images.  This will be fixed in a future release.  But in the near term, use
            // of the SafeFaceDetector class will patch the issue.
            final Detector<Face> safeDetector = new SafeFaceDetector(detector);

            // Create a frame from the bitmap and run face detection on the frame.
            Frame frameServer = new Frame.Builder().setBitmap(bitmapServer).build();
            final SparseArray<Face> facesServer = safeDetector.detect(frameServer);

            Frame frameGallery = new Frame.Builder().setBitmap(bitmapGallery).build();
            final SparseArray<Face> facesGallery = safeDetector.detect(frameGallery);

            if (!safeDetector.isOperational()) {
                // Note: The first time that an app using face API is installed on a device, GMS will
                // download a native library to the device in order to do detection.  Usually this
                // completes before the app is run for the first time.  But if that download has not yet
                // completed, then the above call will not detect any faces.
                //
                // isOperational() can be used to check if the required native library is currently
                // available.  The detector will automatically become operational once the library
                // download completes on device.
                Log.w(TAG, "Face detector dependencies are not yet available.");

                // Check for low storage.  If there is low storage, the native library will not be
                // downloaded, so detection will not become operational.
                IntentFilter lowstorageFilter = new IntentFilter(Intent.ACTION_DEVICE_STORAGE_LOW);
                boolean hasLowStorage = registerReceiver(null, lowstorageFilter) != null;

                if (hasLowStorage) {
                    // Toast.makeText(this, R.string.low_storage_error, Toast.LENGTH_LONG).show();
                    Log.w(TAG, "Face detector dependencies cannot be downloaded due to low device storage");
                }
            }

            progressBar.setMax(100);

            overlayImageServer.setContent(bitmapServer, facesServer);
            overlayImageCompare.setContent(bitmapGallery, facesGallery);

            safeDetector.release();

            //run
            mDuration = 100 * (mRateMatch / 2);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    initProgressbar();
                    transPositionView(overlayImageServer);
                    transPositionViewCompare(overlayImageCompare);
                }
            }, 1000);

        }
    }

    private void initProgressbar() {
        textViewMatchRate.setText("0 %");
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mProcess < mRateMatch) {
                    try {
                        Thread.sleep(100);
                        mProcess += 2;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textViewMatchRate.setText(String.format("%d", mProcess) + " %");
                                if (mProcess < PERCENT_RATE) {
                                    textViewMatchRate.setTextColor(getResources().getColor(R.color.job_number_person));
                                } else {
                                    textViewMatchRate.setTextColor(getResources().getColor(R.color.colorAccent));
                                }
                                //show result
                                if (mProcess >= mRateMatch) {
                                    if (mRateMatch < PERCENT_RATE) {
                                        mTextViewResult.setText(getResources().getString(R.string.check_in_incorrect));
                                        mTextViewResult.setTextColor(getResources().getColor(R.color.job_number_person));
                                    } else {
                                        mTextViewResult.setText(getResources().getString(R.string.check_in_correct));
                                        mTextViewResult.setTextColor(getResources().getColor(R.color.colorAccent));
                                    }
                                }

                            }
                        });
                        progressBar.setProgress(mProcess);

                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}


