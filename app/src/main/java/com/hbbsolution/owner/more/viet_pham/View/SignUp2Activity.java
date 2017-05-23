package com.hbbsolution.owner.more.viet_pham.View;

import android.app.Dialog;
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
import com.hbbsolution.owner.more.viet_pham.ImageFilePath;
import com.hbbsolution.owner.utils.ShowAlertDialog;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 5/10/2017.
 */

public class SignUp2Activity extends AppCompatActivity {
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
    @BindView(R.id.edit_home)
    EditText edtHome;
    @BindView(R.id.image_avatar)
    CircleImageView ivAvatar;
    private int PICK_IMAGE_FROM_GALLERY_REQUEST = 1;
    private Uri mUriChooseImage;
    private String mFilePath = "";
    private String mFileContentResolver = "";
    private int iGender;

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
                String username = bSignUp1.getString("username");
                String password = bSignUp1.getString("password");
                // Start get data from Sign in 1

                // Start get data from Edittext of Sign up 2
                String email = edtEmail.getText().toString();
                String fullname = edtFullName.getText().toString();
                String gender = edtGender.getText().toString();
                String phoneNumber = edtNumber.getText().toString();
                String location = edtHome.getText().toString();
                // End get data from Edittext of Sign up 2
                 if(gender.equals("Nam"))
                 {
                     iGender = 0;
                 }else {
                     iGender = 1;
                 }

                // Start transfer data from Sign up 2 to page terms
                if (email.trim().length() == 0 || fullname.trim().length() == 0 || gender.length() == 0 || phoneNumber.trim().length() == 0 ||
                        location.trim().length() == 0) {
//                    Toast.makeText(SignUp2Activity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    ShowAlertDialog.showAlert("Vui lòng nhập đầy đủ thông tin",SignUp2Activity.this);
                } else {
                    Intent iSignUp2 = new Intent(SignUp2Activity.this, TermsActivity.class);
                    Bundle bSignUp2 = new Bundle();
                    bSignUp2.putString("username", username);
                    bSignUp2.putString("password", password);
                    bSignUp2.putString("email", email);
                    bSignUp2.putString("fullname", fullname);
                    bSignUp2.putInt("gender", iGender);
                    bSignUp2.putString("phone", phoneNumber);
                    bSignUp2.putString("location", location);
                    bSignUp2.putString("filepath", mFilePath);
                    bSignUp2.putString("filecontent",mFileContentResolver);
                    iSignUp2.putExtra("bSignUp2", bSignUp2);
                    startActivity(iSignUp2);
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
                mFilePath = ImageFilePath.getPath(getApplicationContext(), mUriChooseImage);
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
}
