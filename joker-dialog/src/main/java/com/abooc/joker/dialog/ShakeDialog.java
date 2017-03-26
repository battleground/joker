package com.abooc.joker.dialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;

/**
 * Created by dayu on 2016/12/22.
 */

public class ShakeDialog extends android.app.Dialog implements View.OnClickListener {


    public static boolean hasShown;

    public ShakeDialog(Context context) {
        this(context, android.R.style.Theme_Holo_Dialog_NoActionBar);
    }

    public ShakeDialog(Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    private void init() {
        setContentView(R.layout.joker_dialog_shake);
        setCanceledOnTouchOutside(false);

        final Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        findViewById(R.id.close).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        dismiss();
    }

    @Override
    public void show() {
        super.show();
        hasShown = true;
    }
}
