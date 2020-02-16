package com.apurba.infinitemultiviewtypesrecyclerview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class BaseActivity extends AppCompatActivity {


    protected void setNotificationBarBlackNWhite(){
        if (isBuildVersionOk()) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    protected boolean isBuildVersionOk(){
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

}
