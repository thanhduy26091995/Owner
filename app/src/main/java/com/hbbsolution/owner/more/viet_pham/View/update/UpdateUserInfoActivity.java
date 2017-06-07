package com.hbbsolution.owner.more.viet_pham.View.update;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.more.viet_pham.Model.BodyResponse;
import com.hbbsolution.owner.more.viet_pham.Model.UpdateResponse;
import com.hbbsolution.owner.more.viet_pham.Presenter.ImageFilePathPresenter;
import com.hbbsolution.owner.more.viet_pham.Presenter.UpdateUserPresenter;
import com.hbbsolution.owner.more.viet_pham.View.MoreView;
import com.hbbsolution.owner.utils.SessionManagerUser;
import com.hbbsolution.owner.utils.ShowAlertDialog;
import com.hbbsolution.owner.work_management.model.geocodemap.GeoCodeMapResponse;

import java.io.IOException;
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
    private String token;

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

//        ImageLoader.getInstance().loadImageAvatar(UpdateUserInfoActivity.this
//                , mDataHashUser.get(SessionManagerUser.KEY_IMAGE)
//                ,ivAvatar);

        edtEmail.setText(mDataHashUser.get(SessionManagerUser.KEY_EMAIL));
        edtFullName.setText(mDataHashUser.get(SessionManagerUser.KEY_NAME));
        if (mDataHashUser.get(SessionManagerUser.KEY_GENDER).equals(0)){
            edtGender.setText("Nam");
        }else {
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
                        mUpdateUserPresenter.getLocaltionAddress(mLocation);
                }


            }
        });

        // Choose image for Cricle Image View
        ivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iChooseImage = new Intent();
                iChooseImage.setType("image/*");
                iChooseImage.setAction(Intent.ACTION_GET_CONTENT);
                permissionCheck = ContextCompat.checkSelfPermission(UpdateUserInfoActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
                if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(UpdateUserInfoActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_EXTERNAL_PERMISSION);
                } else {
                    startActivityForResult(Intent.createChooser(iChooseImage, "Select image"), PICK_IMAGE_FROM_GALLERY_REQUEST);
                }

            }
        });
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
        }
    }

    @Override
    public void displaySignUpAndSignIn(BodyResponse bodyResponse) {

    }

    @Override
    public void displayUpdate(UpdateResponse updateResponse) {
        ShowAlertDialog.showAlert(updateResponse.getMessage(),UpdateUserInfoActivity.this);
    }

    @Override
    public void displayError() {

    }

    @Override
    public void displayNotFoundLocaltion() {

    }

    @Override
    public void getLocaltionAddress(GeoCodeMapResponse geoCodeMapResponse) {
        mLat = geoCodeMapResponse.getResults().get(0).getGeometry().getLocation().getLat();
        mLng = geoCodeMapResponse.getResults().get(0).getGeometry().getLocation().getLng();
//        token = mDataHashUser.get(SessionManagerUser.KEY_TOKEN);
        if (mLat != 0 && mLng != 0) {
            mProgressDialog.dismiss();
            mUpdateUserPresenter.updateUserInfo(mPhoneName,mFullName,mFilePath,mLocation,mLat,mLng,iGender,mFileContentResolver);

        }
    }
}
