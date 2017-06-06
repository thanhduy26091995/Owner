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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.maid_profile.view.MaidProfileActivity;
import com.hbbsolution.owner.model.CheckInResponse;
import com.hbbsolution.owner.utils.Constants;
import com.hbbsolution.owner.utils.EncodeImage;
import com.hbbsolution.owner.utils.SessionManagerUser;
import com.hbbsolution.owner.utils.ShowAlertDialog;
import com.hbbsolution.owner.work_management.model.workmanagerpending.DatumPending;
import com.hbbsolution.owner.work_management.presenter.DetailJobPostPresenter;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by tantr on 5/14/2017.
 */

public class DetailJobPendingActivity extends AppCompatActivity implements DetailJobPostView, View.OnClickListener {
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


        txtNameMaid.setText(mDatum.getStakeholders().getMadi().getInfo().getName());
        txtAddressMaid.setText(mDatum.getStakeholders().getMadi().getInfo().getAddress().getName());

        Picasso.with(this).load(mDatum.getStakeholders().getMadi().getInfo().getImage())
                .error(R.drawable.avatar)
                .placeholder(R.drawable.avatar)
                .into(img_avatarMaid);

        txtTitleJobPending.setText(mDatum.getInfo().getTitle());
        txtTypeJobPending.setText(mDatum.getInfo().getWork().getName());
        txtContentJobPending.setText(mDatum.getInfo().getDescription());
        txtPriceJobPending.setText(String.valueOf(mDatum.getInfo().getPrice()));
        txtAddressJobPending.setText(mDatum.getInfo().getAddress().getName());
        txtDateJobPending.setText(getDateStartWork(mDatum.getHistory().getUpdateAt()));
        txtTimeDoWrokJobPending.setText(getTimerDoingWork(mDatum.getInfo().getTime().getStartAt(), mDatum.getInfo().getTime().getEndAt()));
        Picasso.with(this).load(mDatum.getInfo().getWork().getImage())
                .error(R.drawable.no_image)
                .placeholder(R.drawable.no_image)
                .into(img_TypeJob);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            EventBus.getDefault().postSticky(false);
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
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lo_clear_job_pending:
                String id = mDatum.getId();
                String idOwner = mDatum.getStakeholders().getOwner();
                Log.d("idrequset", id + " - " + idOwner);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setCancelable(false);
                alertDialog.setTitle("Thông báo");
                alertDialog.setMessage("Bạn có chắc muốn xóa bài đăng này !");
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        progressBar.setVisibility(View.VISIBLE);
                        mDetailJobPostPresenter.deleteJob(mDatum.getId(), mDatum.getStakeholders().getOwner());
                    }
                });
                alertDialog.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alertDialog.show();
                break;
            case R.id.lo_infoMaid:
                Intent itInfoUser = new Intent(DetailJobPendingActivity.this, MaidProfileActivity.class);
                itInfoUser.putExtra("maid", mDatum.getStakeholders().getMadi());
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
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(MediaStore.Images.Media.TITLE, "Title");
//        mUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
//        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
//        startActivityForResult(cameraIntent, Constants.CAMERA_INTENT);

        // create Intent to take a picture and return control to the calling application
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, getPhotoFileUri("photo.jpg")); // set the image file name
//
//        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
//        // So as long as the result is not null, it's safe to use the intent.
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            // Start the image capture intent to take photo
//            startActivityForResult(intent, Constants.CAMERA_INTENT);
//        }


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
//            Uri takenPhotoUri = getPhotoFileUri("photo.jpg");
//            try {
//                Log.d("PATH", getFilePath(DetailJobPendingActivity.this, takenPhotoUri));
//            } catch (URISyntaxException e) {
//                e.printStackTrace();
//            }

            // Bundle extras = data.getExtras();
            //Bitmap imageBitmap = (Bitmap) extras.get("data");
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

            // String photoPath = GetRealPath.getPath(DetailJobPendingActivity.this, imageBitmap);
            //  String photoPath = GetRealPath.getPath(DetailJobPendingActivity.this, data.getData());
            // Log.d("PATH", photoPath);

//            Bitmap bitmap = EncodeImage.encodeImage(getRealPathFromURI(takenPhotoUri));
//            String photoPath = "";
//            photoPath = getRealPathFromURI(getImageUri(DetailJobPendingActivity.this, bitmap));
            //   Log.d("PATH", photoPath);
            //show progress
            showProgress();
            mDetailJobPostPresenter.checkIn(photoPath, "5911460ae740560cb422ac35", "59114b6cbd3b3f2de964950c");
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

    private void showProgress() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Đang xác thực...Việc này có thể mất khá nhiều thời gian");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void hideProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private String getTimerDoingWork(String startAt, String endAt) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
        Date dateStartAt = new DateTime(startAt).toDate();
        Date dateEndtAt = new DateTime(endAt).toDate();
        String mDateStartAt = simpleDateFormat.format(dateStartAt);
        String mDateEndAt = simpleDateFormat.format(dateEndtAt);
        String mTimeDoing = mDateStartAt + " - " + mDateEndAt;

        return mTimeDoing;
    }

    private String getDateStartWork(String dateStartWork) {
        Date date0 = new DateTime(dateStartWork).toDate();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String mDateStartWork = df.format(date0);
        return mDateStartWork;
    }

    @Override
    public void displayNotifyJobPost(boolean isJobPost) {
        progressBar.setVisibility(View.GONE);
        if (isJobPost) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setCancelable(false);
            alertDialog.setTitle("Thông báo");
            alertDialog.setMessage("Bài đăng đã được xóa !");
            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    EventBus.getDefault().postSticky(true);
                    EventBus.getDefault().postSticky(1);
                    finish();
                }
            });

            alertDialog.show();
        } else {
            ShowAlertDialog.showAlert("Thất bại", DetailJobPendingActivity.this);
        }
    }

    @Override
    public void displayError(String error) {

    }

    @Override
    public void checkIn(CheckInResponse checkInResponse) {
        hideProgress();
        timeEnd = new Date().getTime();
        Log.d("TIME", "" + (timeEnd - timeStart) / 1000);
        boolean status = checkInResponse.isStatus();
        if (status) {
            boolean isIdentical = checkInResponse.getData().isIdentical();
            if (isIdentical) {
                try {
                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DetailJobPendingActivity.this);
                    alertDialogBuilder.setMessage("Xác thực thành công");
                    alertDialogBuilder.setCancelable(false);
                    alertDialogBuilder.setPositiveButton(getResources().getText(R.string.okAlert),
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    finish();
                                    Constants.isLoadTabDoing = true;
                                    alertDialogBuilder.create().dismiss();
                                }

                            });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                } catch (Exception e) {

                }
            } else {
                ShowAlertDialog.showAlert("Xác thực không thành công, vui lòng thử lại", DetailJobPendingActivity.this);
            }
        }
    }

    @Override
    public void checkInFail(String error) {

    }
}
