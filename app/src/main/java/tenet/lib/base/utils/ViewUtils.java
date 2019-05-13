package tenet.lib.base.utils;

import android.view.View;
import android.view.ViewGroup;

public class ViewUtils {


    public static boolean removeFromParent(View v){
        if(v.getParent() instanceof ViewGroup){
            ((ViewGroup)v.getParent()).removeView(v);
            return true;
        }
        return false;
    }
}
