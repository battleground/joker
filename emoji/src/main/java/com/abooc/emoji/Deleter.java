package com.abooc.emoji;

import android.text.TextUtils;
import android.widget.EditText;

/**
 * Created by dayu on 2017/3/8.
 */

public class Deleter {

    /**
     * 删除表情
     */
    public static void delete(EditText editText) {
        int selection = editText.getSelectionStart();// 获取光标的位置
        String body = editText.getText().toString();
        if (selection > 0 && !TextUtils.isEmpty(body)) {
            int start;
            CharSequence charSequence = body.subSequence(selection - 1, selection);
            if ("]".equals(charSequence)) {
                String tempStr = body.substring(0, selection);
                start = tempStr.lastIndexOf("[");// 获取最后一个表情的位置
                start = (start == -1 ? selection - 1 : start); // start=-1，表示不存在'['配对符号，则执行单个字符删除，即（selection - 1）。
            } else {
                start = selection - 1;
            }

            editText.getText().delete(start, selection);
        }

    }


}
