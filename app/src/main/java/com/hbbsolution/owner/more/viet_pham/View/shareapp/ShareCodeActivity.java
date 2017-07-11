package com.hbbsolution.owner.more.viet_pham.View.shareapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hbbsolution.owner.R;
import com.hbbsolution.owner.utils.SessionManagerForLanguage;
import com.hbbsolution.owner.utils.SessionManagerUser;

import org.w3c.dom.Text;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by tantr on 7/3/2017.
 */

public class ShareCodeActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txtContentVoucher)
    TextView txtContentVoucher;
    @BindView(R.id.btnShareCode)
    Button btnShareCode;
    String textContent;
    private SessionManagerForLanguage sessionManagerForLanguage;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_code2);
        ButterKnife.bind(this);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnShareCode.setOnClickListener(this);

        sessionManagerForLanguage = new SessionManagerForLanguage(this);
        String lang = sessionManagerForLanguage.getLanguage();
        switch (lang) {
            case "Tiếng Việt":
                textContent = "Giao diện hiện đại, tính năng độc đáo, hoạt động cực kỳ hiệu quả là những gì bạn vừa trải nghiệm. Với phương châm “Chất lượng tạo niềm tin”, chúng tôi mong muốn mang đến cho khách hàng của mình những điều tốt nhất. Và giờ đây, hãy chia sẻ ứng dụng NGV247 với chúng tôi để cùng nhau phát triển những điều lợi ích nhất.";
                break;
            case "English":
                Log.d("langDevice","Tiếng Anh" );
                textContent = "Modern designs, unique features, extremely efficient performance are what you just experienced. Following the motto " +'"'+ "Quality builds Trust" +'"'+ ", we desire to bring our customers the best of the best. And now, let's share the NGV247 application to develop the greatest.";
                break;
            default:
               String langDevice =  Locale.getDefault().getDisplayLanguage();
                if(langDevice.equals("English")){
                    textContent = "Modern designs, unique features, extremely efficient performance are what you just experienced. Following the motto " +'"'+ "Quality builds Trust" +'"'+ ", we desire to bring our customers the best of the best. And now, let's share the NGV247 application to develop the greatest.";
                }else if(langDevice.equals("Tiếng Việt")){
                    textContent = "Giao diện hiện đại, tính năng độc đáo, hoạt động cực kỳ hiệu quả là những gì bạn vừa trải nghiệm. Với phương châm “Chất lượng tạo niềm tin”, chúng tôi mong muốn mang đến cho khách hàng của mình những điều tốt nhất. Và giờ đây, hãy chia sẻ ứng dụng NGV247 với chúng tôi để cùng nhau phát triển những điều lợi ích nhất.";
                }
                break;
        }
        txtContentVoucher.setText(textContent);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
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
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnShareCode:
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, textContent);
                startActivity(Intent.createChooser(share, getResources().getString(R.string.share_with_friends)));
                finish();
                break;
        }
    }
}
