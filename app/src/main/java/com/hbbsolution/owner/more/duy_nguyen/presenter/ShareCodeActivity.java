package com.hbbsolution.owner.more.duy_nguyen.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hbbsolution.owner.R;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by tantr on 6/30/2017.
 */

public class ShareCodeActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private TextView mTxtToolbar_title, mTxt_Content_Share;
    private ImageView mImgBack, mImgHome;
    private TextView txtCodeShare;
    private Button btnShare;
//    private SessionManager sessionManager;
    private String CodeShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_code2);

//        mToolbar = (Toolbar) findViewById(R.id.toolbarbackhome);
//        mTxtToolbar_title = (TextView) findViewById(R.id.toolbar_title);
//        mTxtToolbar_title.setText(getResources().getString(R.string.share_your_app_uper));
//
//        mImgBack = (ImageView) mToolbar.findViewById(R.id.imgback);
//        mImgHome = (ImageView) mToolbar.findViewById(R.id.imgHome);
//
//        txtCodeShare = (TextView) findViewById(R.id.txtCode_Share);
//        mTxt_Content_Share = (TextView) findViewById(R.id.txtContentVoucher);
//        btnShare = (Button) findViewById(R.id.btnShareCode);
//
//        sessionManager = new SessionManager(this);
//        HashMap<String, String> user = sessionManager.getUserDetails();
//        CodeShare = user.get(SessionManager.KEY_SHARECODE);
//        String text = getResources().getString(R.string.fisrt_text_share) + "Android: " + "https://goo.gl/JyNDVY,\n" +
//                "iOS: " + "https://goo.gl/VDSCty.\n" + getResources().getString(R.string.mid_text_share) + " " + CodeShare + "\n"
//                + getResources()
//                .getString(R.string.next_text_share);
//        mTxt_Content_Share.setText(text);
//        txtCodeShare.setText(CodeShare);
//
//
//        btnShare.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                String text_content = getResources().getString(R.string.fisrt_text_share) + "Android: " + ".https://goo.gl/JyNDVY,\n" +
//                        "iOS: " + "https://goo.gl/VDSCty.\n" + getResources().getString(R.string.mid_text_share) + " " + CodeShare + "\n"
//                        + getResources()
//                        .getString(R.string.next_text_share);
//
//                Intent share = new Intent(Intent.ACTION_SEND);
//                share.setType("text/plain");
//                share.putExtra(Intent.EXTRA_TEXT, text_content);
//                startActivity(Intent.createChooser(share, getResources().getString(R.string.share_with_friends)));
//
//
//            }
//        });
//
//        mImgBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//
//            }
//        });
//
//
//        mImgHome.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (HomeActivity.mHomeActivity != null) {
//                    HomeActivity.mHomeActivity.finish();
//                }
//                try {
//
//                    if (ProfileActivity.mProfileActivity != null) {
//                        ProfileActivity.mProfileActivity.finish();
//                    }
//
//                } catch (Exception e) {
//
//                }
//
//                Intent iBackHome = new Intent(ShareCodeActivity.this, HomeActivity.class);
//                startActivity(iBackHome);
//                finish();
//            }
//        });
    }
}
