package com.hbbsolution.owner.more.viet_pham.View.signup;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
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
import com.hbbsolution.owner.more.duy_nguyen.TermsActivity;
import com.hbbsolution.owner.more.viet_pham.Model.RegisterResponse;
import com.hbbsolution.owner.more.viet_pham.Presenter.ImageFilePathPresenter;
import com.hbbsolution.owner.more.viet_pham.Presenter.RegisterPresenter;
import com.hbbsolution.owner.utils.EmailValidate;
import com.hbbsolution.owner.utils.ShowAlertDialog;
import com.hbbsolution.owner.work_management.model.geocodemap.GeoCodeMapResponse;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 5/10/2017.
 */

public class SignUp2Activity extends AppCompatActivity implements SignUpView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.button_next)
    Button buttonNext;
    @BindView(R.id.edit_gender)
    EditText edtGender;
    @BindView(R.id.edit_email)
    EditText edtEmail;
    @BindView(R.id.edit_full_name)
    EditText edtFullName;
    @BindView(R.id.edit_number)
    EditText edtNumber;
    @BindView(R.id.edit_location)
    EditText edtLocation;
    @BindView(R.id.image_avatar)
    CircleImageView ivAvatar;
    private int PICK_IMAGE_FROM_GALLERY_REQUEST = 1;
    private Uri mUriChooseImage;
    private String mFilePath = "";
    private String mFileContentResolver = "";
    private int iGender;
    private RegisterPresenter mRegisterPresenter;
    private ProgressDialog mProgressDialog;
    private double mLat;
    private double mLng;
    private String mUserName, mPassword, mEmail, mFullName, mPhoneName, mLocation, mGender;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_2);
        ButterKnife.bind(this);
        setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        addEvents();

        mProgressDialog = new ProgressDialog(this);
        mRegisterPresenter = new RegisterPresenter(this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void addEvents() {
        // Event click next page
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start get data from Sign up 1
                Intent iSignUp1 = getIntent();
                Bundle bSignUp1 = iSignUp1.getBundleExtra("bNextPage");
                mUserName = bSignUp1.getString("username");
                mPassword = bSignUp1.getString("password");
                // Start get data from Sign in 1

                // Start get data from Edittext of Sign up 2
                mEmail = edtEmail.getText().toString();
                mFullName = edtFullName.getText().toString();
                mGender = edtGender.getText().toString();
                mPhoneName = edtNumber.getText().toString();
                mLocation = edtLocation.getText().toString();
                // End get data from Edittext of Sign up 2
                if (mGender.equals("Nam")) {
                    iGender = 0;
                } else {
                    iGender = 1;
                }

                // Start transfer data from Sign up 2 to page terms
                if (mEmail.trim().length() == 0 || mFullName.trim().length() == 0 || mGender.length() == 0 || mPhoneName.trim().length() == 0 ||
                        mLocation.trim().length() == 0) {
                    ShowAlertDialog.showAlert("Vui lòng nhập đầy đủ thông tin", SignUp2Activity.this);
                } else {
                    if (EmailValidate.IsOk(mEmail))
                    {
                        mProgressDialog.show();
                        mRegisterPresenter.getLocaltionAddress(mLocation);
                    }else {
                        ShowAlertDialog.showAlert("Vui lòng nhập đúng email",SignUp2Activity.this);
                    }

                }
                // End transfer data from Sign up 2 to page terms
            }
        });

        // Event choosen gender
        edtGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View v = getLayoutInflater().inflate(R.layout.gender_bottom_sheet, null);

                final Dialog mBottomSheetDialog = new Dialog(SignUp2Activity.this, R.style.MaterialDialogSheet);
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

        // Choose image for Cricle Image View
        ivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iChooseImage = new Intent();
                iChooseImage.setType("image/*");
                iChooseImage.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(iChooseImage, "Select image"), PICK_IMAGE_FROM_GALLERY_REQUEST);
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
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(this).unbind();
    }

    @Override
    public void displaySignUp(RegisterResponse registerResponse) {

    }

    @Override
    public void displayError() {

    }

    @Override
    public void displayNotFoundLocaltion() {
        mProgressDialog.dismiss();
        ShowAlertDialog.showAlert("Vui lòng nhập chính xác địa chỉ", SignUp2Activity.this);
    }

    @Override
    public void getLocaltionAddress(GeoCodeMapResponse geoCodeMapResponse) {
        mLat = geoCodeMapResponse.getResults().get(0).getGeometry().getLocation().getLat();
        mLng = geoCodeMapResponse.getResults().get(0).getGeometry().getLocation().getLng();
        if (mLat != 0 && mLng != 0) {
            Intent iSignUp2 = new Intent(SignUp2Activity.this, TermsActivity.class);
            Bundle bSignUp2 = new Bundle();
            bSignUp2.putString("username", mUserName);
            bSignUp2.putString("password", mPassword);
            bSignUp2.putString("email", mEmail);
            bSignUp2.putString("fullname", mFullName);
            bSignUp2.putInt("gender", iGender);
            bSignUp2.putString("phone", mPhoneName);
            bSignUp2.putString("location", mLocation);
            bSignUp2.putDouble("lat", mLat);
            bSignUp2.putDouble("lng", mLng);
            bSignUp2.putString("filepath", mFilePath);
            bSignUp2.putString("filecontent", mFileContentResolver);
            iSignUp2.putExtra("bSignUp2", bSignUp2);
            startActivity(iSignUp2);
            mProgressDialog.dismiss();
        }
    }
}
