package com.hbbsolution.owner.more.viet_pham.View;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
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

import butterknife.BindView;
import butterknife.ButterKnife;

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
                Intent intent = new Intent(SignUp2Activity.this, TermsActivity.class);
                startActivity(intent);
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
    }
}
