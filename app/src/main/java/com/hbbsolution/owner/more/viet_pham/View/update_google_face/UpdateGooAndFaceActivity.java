package com.hbbsolution.owner.more.viet_pham.View.update_google_face;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.base.ImageLoader;
import com.hbbsolution.owner.more.viet_pham.Model.signin_signup.BodyResponse;
import com.hbbsolution.owner.more.viet_pham.Model.signin_signup.DataUpdateResponse;
import com.hbbsolution.owner.more.viet_pham.Presenter.UpdateInfoGooAndFacePresenter;
import com.hbbsolution.owner.more.viet_pham.View.MoreView;
import com.hbbsolution.owner.utils.ShowAlertDialog;
import com.hbbsolution.owner.work_management.model.geocodemap.GeoCodeMapResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.hbbsolution.owner.utils.ShowAlertDialog.mProgressDialog;

/**
 * Created by Administrator on 6/21/2017.
 */

public class UpdateGooAndFaceActivity extends AppCompatActivity implements MoreView {
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
    private double mLat;
    private double mLng;
    private UpdateInfoGooAndFacePresenter mUpdateInfoGooAndFacePresenter;
    private String emailGoogle ;
    private String nameGoogle ;
    private String idUser ;
    private String TokenId ;
    private String deviceToken ;
    private String imageGoogle ;
    private String mFullName,mPhoneName, mLocation, mGender;
    private int iGender;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_info);
        ButterKnife.bind(this);
        setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        loadInfoFromGooAndFace();
        mUpdateInfoGooAndFacePresenter = new UpdateInfoGooAndFacePresenter(this);
        mProgressDialog = new ProgressDialog(this);
        addEvents();
    }


    private void addEvents() {

        edtGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View v = getLayoutInflater().inflate(R.layout.gender_bottom_sheet, null);

                final Dialog mBottomSheetDialog = new Dialog(UpdateGooAndFaceActivity.this, R.style.MaterialDialogSheet);
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
                mGender = edtGender.getText().toString();
                mPhoneName = edtNumber.getText().toString();
                mLocation = edtLocation.getText().toString();
                mFullName = "gm"+idUser;
                if (mGender.equals("Nam")) {
                    iGender = 0;
                } else {
                    iGender = 1;
                }

                if (nameGoogle.trim().length() == 0 || mGender.length() == 0 || mPhoneName.trim().length() == 0 ||
                        mLocation.trim().length() == 0) {
                    ShowAlertDialog.showAlert("Vui lòng nhập đầy đủ thông tin", UpdateGooAndFaceActivity.this);
                } else {
                    mProgressDialog.show();
                    mProgressDialog.setCanceledOnTouchOutside(false);
                    mUpdateInfoGooAndFacePresenter.getLocaltionAddress(mLocation);
                }


            }
        });

    }

    public void loadInfoFromGooAndFace()
    {
        Intent iUpdate = getIntent();
        Bundle bUpdate = iUpdate.getBundleExtra("infoGoogle");
        emailGoogle = bUpdate.getString("emailGoogle");
        nameGoogle = bUpdate.getString("nameGoogle");
        idUser = bUpdate.getString("idUser");
        TokenId = bUpdate.getString("TokenId");
        deviceToken = bUpdate.getString("deviceToken");
        imageGoogle = bUpdate.getString("imageGoogle");
        ImageLoader.getInstance().loadImageAvatar(UpdateGooAndFaceActivity.this,imageGoogle,
                ivAvatar);
        edtEmail.setText(emailGoogle);
        edtFullName.setText(nameGoogle);

    }

    @Override
    public void displaySignUpAndSignIn(BodyResponse bodyResponse) {

    }

    @Override
    public void displayUpdate(DataUpdateResponse dataUpdateResponse) {

    }

    @Override
    public void displayError() {

    }

    @Override
    public void displayNotFoundLocaltion() {
        mProgressDialog.dismiss();
        ShowAlertDialog.showAlert("Địa chỉ không tìm thấy", UpdateGooAndFaceActivity.this);
    }

    @Override
    public void getLocaltionAddress(GeoCodeMapResponse geoCodeMapResponse) {
        mLat = geoCodeMapResponse.getResults().get(0).getGeometry().getLocation().getLat();
        mLng = geoCodeMapResponse.getResults().get(0).getGeometry().getLocation().getLng();
//        token = mDataHashUser.get(SessionManagerUser.KEY_TOKEN);
        if (mLat != 0 && mLng != 0) {
            mUpdateInfoGooAndFacePresenter.updateUserInfoGooAndFace(idUser,TokenId,deviceToken,emailGoogle,mFullName,mPhoneName
            ,nameGoogle,mLocation,mLat,mLng,iGender,imageGoogle);
        }

    }

    @Override
    public void displaySignInGooAndFace(DataUpdateResponse dataUpdateResponse) {
        mProgressDialog.dismiss();
        if (dataUpdateResponse.isStatus() == true)
        {
            ShowAlertDialog.showAlert("Cập nhật thông tin thành công",UpdateGooAndFaceActivity.this);
        }else {
            ShowAlertDialog.showAlert("Cập nhật thông tin không thành công",UpdateGooAndFaceActivity.this);
        }
    }
}
