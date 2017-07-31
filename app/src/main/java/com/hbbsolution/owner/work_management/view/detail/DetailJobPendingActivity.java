package com.hbbsolution.owner.work_management.view.detail;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hbbsolution.owner.R;
import com.hbbsolution.owner.base.AuthenticationBaseActivity;
import com.hbbsolution.owner.maid_profile.view.MaidProfileActivity;
import com.hbbsolution.owner.model.CheckInResponse;
import com.hbbsolution.owner.utils.Constants;
import com.hbbsolution.owner.utils.EncodeImage;
import com.hbbsolution.owner.utils.SessionManagerUser;
import com.hbbsolution.owner.utils.ShowAlertDialog;
import com.hbbsolution.owner.utils.WorkTimeValidate;
import com.hbbsolution.owner.work_management.model.workmanagerpending.DatumPending;
import com.hbbsolution.owner.work_management.presenter.DetailJobPostPresenter;

import java.io.File;
import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by tantr on 5/14/2017.
 */

public class DetailJobPendingActivity extends AuthenticationBaseActivity implements DetailJobPostView, View.OnClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.lo_clear_job_pending)
    LinearLayout lo_clear_job_pending;
    @BindView(R.id.lo_infoMaid)
    RelativeLayout lo_infoMaid;
    @BindView(R.id.txtTitleJobPending)
    TextView txtTitleJobPending;
    @BindView(R.id.txtTypeJobPending)
    TextView txtTypeJobPending;
    @BindView(R.id.txtContentJobPending)
    TextView txtContentJobPending;
    @BindView(R.id.txtPriceJobPending)
    TextView txtPriceJobPending;
    @BindView(R.id.txtDateJobPending)
    TextView txtDateJobPending;
    @BindView(R.id.txtTimeDoWrokJobPending)
    TextView txtTimeDoWrokJobPending;
    @BindView(R.id.txtAddressJobPending)
    TextView txtAddressJobPending;
    @BindView(R.id.txtNameMaid)
    TextView txtNameMaid;
    @BindView(R.id.txtAddressMaid)
    TextView txtAddressMaid;
    @BindView(R.id.img_avatarMaid)
    ImageView img_avatarMaid;
    @BindView(R.id.img_TypeJob)
    ImageView img_TypeJob;
    @BindView(R.id.progressDetailJobPending)
    ProgressBar progressBar;
    @BindView(R.id.rela_confirm_maid)
    RelativeLayout relaConfirmMaid;

    private DatumPending mDatum;
    private DetailJobPostPresenter mDetailJobPostPresenter;
    private static final int REQUEST_CODE_CAMERA = 1002;
    private static final String[] PERMISSIONS_CAMERA = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private Uri mUri;
    private SessionManagerUser sessionManagerUser;
    private HashMap<String, String> hashDataUser = new HashMap<>();
    private long timeStart, timeEnd;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_job_pending);
        ButterKnife.bind(this);

        checkConnectionInterner();
        sessionManagerUser = new SessionManagerUser(this);
        hashDataUser = sessionManagerUser.getUserDetails();
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDetailJobPostPresenter = new DetailJobPostPresenter(this);

        //event click
        lo_clear_job_pending.setOnClickListener(this);
        lo_infoMaid.setOnClickListener(this);
        relaConfirmMaid.setOnClickListener(this);

        final Intent intent = getIntent();
        mDatum = (DatumPending) intent.getSerializableExtra("mDatum");

        try {
            if (mDatum != null) {
                if (!WorkTimeValidate.compareDays(mDatum.getInfo().getTime().getEndAt())) {
                    relaConfirmMaid.setVisibility(View.GONE);
                } else {
                    relaConfirmMaid.setVisibility(View.VISIBLE);
                }

                txtNameMaid.setText(mDatum.getStakeholders().getMadi().getInfo().getName());
                txtAddressMaid.setText(mDatum.getStakeholders().getMadi().getInfo().getAddress().getName());

                //load image
                if (mDatum.getStakeholders() != null && mDatum.getStakeholders().getMadi() != null &&
                        mDatum.getStakeholders().getMadi().getInfo() != null && mDatum.getStakeholders().getMadi().getInfo().getImage() != null) {
                    Glide.with(this).load(mDatum.getStakeholders().getMadi().getInfo().getImage())
                            .error(R.drawable.avatar)
                            .thumbnail(0.5f)
                            .dontAnimate()
                            .placeholder(R.drawable.avatar)
                            .into(img_avatarMaid);
                }

                txtTitleJobPending.setText(mDatum.getInfo().getTitle());
                txtTypeJobPending.setText(mDatum.getInfo().getWork().getName());
                txtContentJobPending.setText(mDatum.getInfo().getDescription());
                txtPriceJobPending.setText(formatPrice(mDatum.getInfo().getPrice()));
                txtAddressJobPending.setText(mDatum.getInfo().getAddress().getName());
                txtDateJobPending.setText(WorkTimeValidate.getDatePostHistory(mDatum.getInfo().getTime().getEndAt()));
                String mStartTime = WorkTimeValidate.getTimeWorkLanguage(this, mDatum.getInfo().getTime().getStartAt());
                String mEndTime = WorkTimeValidate.getTimeWorkLanguage(this, mDatum.getInfo().getTime().getEndAt());
                txtTimeDoWrokJobPending.setText(mStartTime + " - " + mEndTime);
//        txtTimeDoWrokJobPending.setText(getTimerDoingWork(mDatum.getInfo().getTime().getStartAt(), mDatum.getInfo().getTime().getEndAt()));
                if (mDatum.getInfo() != null && mDatum.getInfo().getWork() != null &&
                        mDatum.getInfo().getWork().getImage() != null) {
                    Glide.with(this).load(mDatum.getInfo().getWork().getImage())
                            .error(R.drawable.no_image)
                            .placeholder(R.drawable.no_image)
                            .dontAnimate()
                            .into(img_TypeJob);
                }
            }
        } catch (Exception e) {

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            EventBus.getDefault().postSticky(false);
            EventBus.getDefault().postSticky("1");
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(this).unbind();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        EventBus.getDefault().postSticky(false);
        EventBus.getDefault().postSticky("1");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lo_clear_job_pending:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setCancelable(false);
                alertDialog.setTitle(getResources().getString(R.string.notification));
                alertDialog.setMessage(getResources().getString(R.string.notification_del_job_post));
                alertDialog.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        progressBar.setVisibility(View.VISIBLE);
                        showProgress();
                        mDetailJobPostPresenter.deleteJob(mDatum.getId(), mDatum.getStakeholders().getOwner());
                    }
                });
                alertDialog.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alertDialog.show();
                break;
            case R.id.lo_infoMaid:
                Intent itInfoUser = new Intent(DetailJobPendingActivity.this, MaidProfileActivity.class);
                itInfoUser.putExtra("maid", mDatum.getStakeholders().getMadi());
                itInfoUser.putExtra("tabJonPending", 1);
                startActivity(itInfoUser);
                break;
            case R.id.rela_confirm_maid: {
                if (verifyOpenCamera()) {
                    openCamera();
                }
                break;
            }
        }
    }

    private void openCamera() {
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePhotoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePhotoIntent, Constants.CAMERA_INTENT);
        }
    }

    // Returns the Uri for a photo stored on disk given the fileName
    public Uri getPhotoFileUri(String fileName) {
        // Only continue if the SD Card is mounted
        if (isExternalStorageAvailable()) {
            // Get safe storage directory for photos
            // Use `getExternalFilesDir` on Context to access package-specific directories.
            // This way, we don't need to request external read/write runtime permissions.
            File mediaStorageDir = new File(
                    getExternalFilesDir(Environment.DIRECTORY_PICTURES), "Owner");

            // Create the storage directory if it does not exist
            if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
                // Log.d(APP_TAG, "failed to create directory");
            }

            // Return the file target for the photo based on filename
            File file = new File(mediaStorageDir.getPath() + File.separator + fileName);

            // wrap File object into a content provider
            // required for API >= 24
            // See https://guides.codepath.com/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
            return FileProvider.getUriForFile(DetailJobPendingActivity.this, "com.hbbsolution.owner", file);
        }
        return null;
    }

    // Returns true if external storage for photos is available
    private boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    private boolean verifyOpenCamera() {
        int camera = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (camera != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS_CAMERA, REQUEST_CODE_CAMERA
            );

            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults) {
        switch (permsRequestCode) {
            case REQUEST_CODE_CAMERA:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                }
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.CAMERA_INTENT && resultCode == RESULT_OK) {
            timeStart = new Date().getTime();
            String photoPath = "";
            if (getRealPathFromURI(data.getData()) != "") {
                Bitmap imageBitmap = EncodeImage.encodeImage(getRealPathFromURI(data.getData()));
                photoPath = getRealPathFromURI(getImageUri(DetailJobPendingActivity.this, imageBitmap));
            } else {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                // Bitmap des = EncodeImage.rotateBitmap(photo, orientation);
                Uri tempUri = getImageUri(getApplicationContext(), photo);
                photoPath = getRealPathFromURI(tempUri);
            }
            //show progress
            showProgress();
            mDetailJobPostPresenter.checkIn(photoPath, "5911460ae740560cb422ac35", mDatum.getId());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private Uri getImageUri(Context inContext, Bitmap inImage) {
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
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

    @Override
    public void displayNotifyJobPost(boolean isJobPost) {
//        progressBar.setVisibility(View.GONE);
        hideProgress();
        if (isJobPost) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setCancelable(false);
            alertDialog.setTitle(getResources().getString(R.string.notification));
            alertDialog.setMessage(getResources().getString(R.string.notification__pass_del_job_post));
            alertDialog.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    EventBus.getDefault().postSticky(true);
                    EventBus.getDefault().postSticky("1");
                    finish();
                }
            });

            alertDialog.show();
        } else {
            ShowAlertDialog.showAlert(getResources().getString(R.string.thatbai), DetailJobPendingActivity.this);
        }
    }

    @Override
    public void displayError(String error) {
        hideProgress();
    }

    @Override
    public void checkIn(CheckInResponse checkInResponse) {
        hideProgress();
        boolean status = checkInResponse.isStatus();
        if (status) {
            //boolean isIdentical = checkInResponse.getData().isIdentical();
            // if (isIdentical) {
            try {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DetailJobPendingActivity.this);
                alertDialogBuilder.setMessage(getResources().getString(R.string.confirm_success));
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setPositiveButton(getResources().getText(R.string.okAlert),
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
//                                finish();
//                                Constants.isLoadTabDoing = true;
                                EventBus.getDefault().postSticky(true);
                                EventBus.getDefault().postSticky("1");
                                finish();
                                alertDialogBuilder.create().dismiss();
                            }

                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            } catch (Exception e) {

            }
        } else {
            String message = checkInResponse.getMessage();
            if (message.equals("DATA_NOT_EXIST")) {
                ShowAlertDialog.showAlert(getResources().getString(R.string.data_not_exist), DetailJobPendingActivity.this);
            } else if (message.equals("CHECK_IN_EXIST")) {
                ShowAlertDialog.showAlert(getResources().getString(R.string.checkin_exist), DetailJobPendingActivity.this);
            } else if (message.equals("FACE_IDENTICAL_FAILED")) {
                ShowAlertDialog.showAlert(getResources().getString(R.string.checkin_face_identical_failed), DetailJobPendingActivity.this);
            } else {
                ShowAlertDialog.showAlert(getResources().getString(R.string.loi_thu_lai), DetailJobPendingActivity.this);

            }

        }
    }

    @Override
    public void checkInFail(String error) {
        Log.d("ERROR", error);
        hideProgress();
        ShowAlertDialog.showAlert(getResources().getString(R.string.confirm_failed), DetailJobPendingActivity.this);
    }

    private String formatPrice(Integer _Price) {
        String mOutputPrice = null;
        if (_Price != null && _Price != 0) {
            mOutputPrice = String.format("%s VND", NumberFormat.getNumberInstance(Locale.GERMANY).format(_Price));
        } else if (_Price == 0) {
            mOutputPrice = getResources().getString(R.string.hourly_pay);
        }
        return mOutputPrice;
    }

    @Override
    public void connectServerFail() {
        hideProgress();
        ShowAlertDialog.showAlert(getResources().getString(R.string.connection_error), this);
    }
}
