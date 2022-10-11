package com.example.caritukang.Util;

import android.os.Build;
import android.text.Html;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.caritukang.R;

import java.text.NumberFormat;
import java.util.Locale;

public class Util {
    public static void switchFragment(Fragment fragmentTarget , FragmentActivity activityFragment){
        activityFragment.getSupportFragmentManager().beginTransaction().replace(R.id.id_frame_layout,fragmentTarget).commit();
    }

    public static void setCustomColorText(TextView mTextViewTarget , String oldText , String coloredText , String coloredHex){
        mTextViewTarget.setText(Html.fromHtml(oldText + "<font color=\"#" +  coloredHex + "\">" + coloredText));
    }
    public static void setNavigationBarColor(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            // getActivity().getWindow().setNavigationBarColor(getResources().getColor(R.color.green_background));
        }
    }
    public static String convertToRupiah(int totalRupiah){
        Locale myIndonesianLocale = new Locale("in", "ID");
        NumberFormat formater = NumberFormat.getCurrencyInstance(myIndonesianLocale);
        return formater.format(totalRupiah).replace(",00" ,"");
    }
}
