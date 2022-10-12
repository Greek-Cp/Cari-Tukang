package com.example.caritukang.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.caritukang.Fragment.FragmentLogin;
import com.example.caritukang.Fragment.FragmentRegister;
import com.example.caritukang.R;
import com.example.caritukang.Util.Util;

public class BaseActivityApp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_base_app);
        hideActionBar();
        getSupportFragmentManager().beginTransaction().replace(R.id.id_base_frame_layout, new FragmentRegister()).commit();
    }
    public void hideActionBar(){
        if(getSupportActionBar() != null){
            getSupportActionBar().hide();;
        }
    }
}