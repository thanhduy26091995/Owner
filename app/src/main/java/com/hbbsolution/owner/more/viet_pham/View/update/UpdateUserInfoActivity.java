package com.hbbsolution.owner.more.viet_pham.View.update;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.base.ImageLoader;
import com.hbbsolution.owner.more.viet_pham.Model.signin_signup.BodyResponse;
import com.hbbsolution.owner.more.viet_pham.Model.signin_signup.Data;
import com.hbbsolution.owner.more.viet_pham.Model.signin_signup.DataUpdateResponse;
import com.hbbsolution.owner.more.viet_pham.Presenter.ImageFilePathPresenter;
import com.hbbsolution.owner.more.viet_pham.Presenter.UpdateUserPresenter;
import com.hbbsolution.owner.more.viet_pham.View.MoreView;
import com.hbbsolution.owner.more.viet_pham.View.profile.ProfileActivity;
import com.hbbsolution.owner.utils.SessionManagerUser;
import com.hbbsolution.owner.utils.ShowAlertDialog;
import com.hbbsolution.owner.work_management.model.geocodemap.GeoCodeMapResponse;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 6/5/2017.
 */

public class UpdateUserInfoActivity extends AppCompatActivity implements MoreView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edit_email)
    EditText edtEmail;
    @BindView(R.id.edit_full_name)
    EditText edtFullName;
    @BindView(R.id.edit_gender)
    EditText edtGender;
    @BindView(R.id.edit_number)
    EditText edtNumber;
    @BindView(R.id.edit_location)
    EditText edtLocation;
    @BindView(R.id.button_update)
    Button btnUpdate;
    @BindView(R.id.image_avatar)
    CircleImageView ivAvatar;
    private Intent iChooseImage;
    private int permissionCheck;
    private SessionManagerUser mSessionManagerUser;
    private static final int REQUEST_READ_EXTERNAL_PERMISSION = 1;
    private Uri mUriChooseImage;
    private String mFilePath = "";
    private String mFileContentResolver = "";
    private int PICK_IMAGE_FROM_GALLERY_REQUEST = 1;
    private HashMap<String, String> mDataHashUser = new HashMap<>();
    private String mFullName, mPhoneName, mLocation, mGender;
    private int iGender;
    private ProgressDialog mProgressDialog;
    private UpdateUserPresenter mUpdateUserPresenter;
    private double mLat;
    private double mLng;
    private static final int CAMERA_REQUEST = 100;
    private Uri fileUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_info);
        ButterKnife.bind(this);
        setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mSessionManagerUser = new SessionManagerUser(this);
        mDataHashUser = mSessionManagerUser.getUserDetails();

        // from Bitmap
        ImageLoader.getInstance().loadImageAvatar(UpdateUserInfoActivity.this, mDataHashUser.get(SessionManagerUser.KEY_IMAGE),
                ivAvatar);

        edtEmail.setText(mDataHashUser.get(SessionManagerUser.KEY_EMAIL));
        edtFullName.setText(mDataHashUser.get(SessionManagerUser.KEY_NAME));
        if (mDataHashUser.get(SessionManagerUser.KEY_GENDER).equals("0")) {
            edtGender.setText("Nam");
        } else {
            edtGender.setText("Nữ");
        }
        edtNumber.setText(mDataHashUser.get(SessionManagerUser.KEY_PHONE));
        edtLocation.setText(mDataHashUser.get(SessionManagerUser.KEY_ADDRESS));

        addEvents();

        mProgressDialog = new ProgressDialog(this);
        mUpdateUserPresenter = new UpdateUserPresenter(this);


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void addEvents() {

        edtGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View v = getLayoutInflater().inflate(R.layout.gender_bottom_sheet, null);

                final Dialog mBottomSheetDialog = new Dialog(UpdateUserInfoActivity.this, R.style.MaterialDialogSheet);
                mBottomSheetDialog.setContentView(v);
                mBottomSheetDialog.setCancelable(true);
                mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
                mBottomSheetDialog.show();

                final TextView mTextViewMen = (TextView) v.findViewById(R.id.text_men);
                final TextView mTextViewWomen = (TextView) v.findViewById(R.id.text_women);

                mTextViewMen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String men = mTextViewMen.getText().toString();
                        edtGender.setText(men);
                        mBottomSheetDialog.dismiss();
                    }
                });

                mTextViewWomen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String wommen = mTextViewWomen.getText().toString();
                        edtGender.setText(wommen);
                        mBottomSheetDialog.dismiss();

                    }
                });
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFullName = edtFullName.getText().toString();
                mGender = edtGender.getText().toString();
                mPhoneName = edtNumber.getText().toString();
                mLocation = edtLocation.getText().toString();
                if (mGender.equals("Nam")) {
                    iGender = 0;
                } else {
                    iGender = 1;
                }

                if (mFullName.trim().length() == 0 || mGender.length() == 0 || mPhoneName.trim().length() == 0 ||
                        mLocation.trim().length() == 0) {
                    ShowAlertDialog.showAlert("Vui lòng nhập đầy đủ thông tin", UpdateUserInfoActivity.this);
                } else {
                    mProgressDialog.show();
                    mProgressDialog.setCanceledOnTouchOutside(false);
                    mUpdateUserPresenter.getLocaltionAddress(mLocation);
                }


            }
        });

        // Choose image for Cricle Image View
        ivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_READ_EXTERNAL_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivityForResult(Intent.createChooser(iChooseImage, "Select image"), PICK_IMAGE_FROM_GALLERY_REQUEST);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_FROM_GALLERY_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mUriChooseImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mUriChooseImage);
                ivAvatar.setImageBitmap(bitmap);
                mFilePath = ImageFilePathPresenter.getPath(getApplicationContext(), mUriChooseImage);
                mFileContentResolver = getContentResolver().getType(mUriChooseImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            ivAvatar.setImageURI(fileUri);
            mFilePath = ImageFilePathPresenter.getPath(getApplicationContext(),fileUri);
        }
    }

    @Override
    public void displaySignUpAndSignIn(BodyResponse bodyResponse) {

    }

    @Override
    public void displayUpdate(DataUpdateResponse dataUpdateResponse) {

        Data data = new Data();
        data.setUser(dataUpdateResponse.getUser());
        data.setToken(mDataHashUser.get(SessionManagerUser.KEY_TOKEN));
        mSessionManagerUser.removeValue();
        mSessionManagerUser.createLoginSession(data);
        mProgressDialog.dismiss();
        if (dataUpdateResponse.isStatus() == true) {
            if (ProfileActivity.profileActivity !=null)
            {
                ProfileActivity.profileActivity.finish();
            }
            Intent iProfile = new Intent(UpdateUserInfoActivity.this, ProfileActivity.class);
            startActivity(iProfile);
            finish();
            // ShowAlertDialog.showAlert(dataUpdateResponse.getMessage(),UpdateUserInfoActivity.this);
        } else {
            ShowAlertDialog.showAlert(getResources().getString(R.string.cap_nhat_thong_tin), UpdateUserInfoActivity.this);
        }

    }

    @Override
    public void displayError() {

    }

    @Override
    public void displayNotFoundLocaltion() {
        mProgressDialog.dismiss();
        ShowAlertDialog.showAlert(getResources().getString(R.string.dia_chi_khong_tim_thay), UpdateUserInfoActivity.this);
    }

    @Override
    public void getLocaltionAddress(GeoCodeMapResponse geoCodeMapResponse) {
        mLat = geoCodeMapResponse.getResults().get(0).getGeometry().getLocation().getLat();
        mLng = geoCodeMapResponse.getResults().get(0).getGeometry().getLocation().getLng();
//        token = mDataHashUser.get(SessionManagerUser.KEY_TOKEN);
        if (mLat != 0 && mLng != 0) {

            mUpdateUserPresenter.updateUserInfo(mPhoneName, mFullName, mFilePath, mLocation, mLat, mLng, iGender);

        }
    }

    @Override
    public void displaySignInGooAndFace(BodyResponse bodyResponse) {

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    public void selectImage() {
        final CharSequence[] options = {getResources().getString(R.string.sign_up_camera),getResources().getString(R.string.sign_up_libary_image),getResources().getString(R.string.sign_up_cancel)};
        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateUserInfoActivity.this);
        builder.setTitle(getResources().getString(R.string.sign_up_choice));
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Máy ảnh") || options[item].equals("Camera")) {
                    if (verifyCamerapermission()) {
                        takePhoto();
                    } else {
                        return;
                    }
                } else if (options[item].equals("Thư viện ảnh") || options[item].equals("Photo libary")) {
                    if (verifyCamerapermission()) {
                        openGallery();
                    } else {
                        return;
                    }
                }
            }
        });
        builder.show();
    }

    public void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = Uri.fromFile(getOutputMediaFile());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, CAMERA_REQUEST);
    }

    public void openGallery() {
        iChooseImage = new Intent();
        iChooseImage.setType("image/*");
        iChooseImage.setAction(Intent.ACTION_GET_CONTENT);
        permissionCheck = ContextCompat.checkSelfPermission(UpdateUserInfoActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(UpdateUserInfoActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_EXTERNAL_PERMISSION);
        } else {
            startActivityForResult(Intent.createChooser(iChooseImage, getResources().getString(R.string.select_image)), PICK_IMAGE_FROM_GALLERY_REQUEST);
        }
    }

    public boolean verifyCamerapermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_REQUEST);
            return false;
        }
        return true;
    }


    private static File getOutputMediaFile() {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "CameraDemo");

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_" + timeStamp + ".jpg");
    }
}
