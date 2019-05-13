package com.founq.sdk.materialtest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class UIUtils {
    /**
     * 通过设置全屏，设置状态栏透明
     *
     * @param activity
     */
    public static void fullScreen(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
                Window window = activity.getWindow();
                View decorView = window.getDecorView();
                //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
                //导航栏颜色也可以正常设置
                //window.setNavigationBarColor(Color.TRANSPARENT);
            } else {
                Window window = activity.getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                attributes.flags |= flagTranslucentStatus;
                //透明导航栏
                //int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
                //attributes.flags |= flagTranslucentNavigation;
                window.setAttributes(attributes);
            }
        }
    }

    /**
     * 设置状态栏颜色
     *
     * @param activity
     * @param colorResId
     */
    public static void setWindowStatusBarColor(Activity activity, int colorResId) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(activity.getResources().getColor(colorResId));

                //底部导航栏
                //window.setNavigationBarColor(activity.getResources().getColor(colorResId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置状态栏字体颜色
     *
     * @param activity
     * @param bDark
     */
    public static void setDarkStatusIcon(Activity activity, boolean bDark) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//只支持android6.0以上
            View decorView = activity.getWindow().getDecorView();
            if (decorView != null) {
                int vis = decorView.getSystemUiVisibility();
                if (bDark) {
                    vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                } else {
                    vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                }
                decorView.setSystemUiVisibility(vis);
            }
        }
    }

    /**
     * 显示确认对话框
     *
     * @param context
     * @param title
     * @param text
     * @param positiveText
     * @param positiveListener
     * @param negativeText
     * @param negativeListener
     */
    public static AlertDialog showConfirmDialog(Context context, String title, String text,
                                                CharSequence positiveText, DialogInterface.OnClickListener positiveListener,
                                                CharSequence negativeText, DialogInterface.OnClickListener negativeListener) {

        return new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(text)
                .setPositiveButton(positiveText, positiveListener)
                .setNegativeButton(negativeText, negativeListener)
                .show();
    }

    /**
     * 显示列表框
     *
     * @param context
     * @param list
     * @param listener
     * @return
     */
    public static AlertDialog showListDialog(Context context, String title, String list[], DialogInterface.OnClickListener listener) {

        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setItems(list, listener)
                .show();

        return alertDialog;
    }

    /**
     * 显示等待对话框
     *
     * @param context
     * @param title
     * @param text
     * @return
     */
    public static ProgressDialog showProcessDialog(Context context, String title, String text) {

        ProgressDialog processDia = new ProgressDialog(context);
        processDia.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        processDia.setTitle(title);
        processDia.setMessage(text);
        processDia.setIndeterminate(true);
        processDia.setCancelable(false);
        processDia.show();

        return processDia;
    }
}
