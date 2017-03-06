package com.abooc.emoji;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
import android.widget.EditText;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dayu on 2017/1/17.
 */

public class EmojiBuilder {

    public static String EMOJI_PATTERN = "\\[[^\\[\\[]+\\]";


    /**
     * @param message 源文本，即目标字符串，如：瞧[惊讶]，这是一条带表情符的消息[微笑]！
     * @return 返回将表情符转为图片显示的内容。
     */
    public static CharSequence toEmojiCharAll(Context ctx, String message, Map<String, Bitmap> emojiMap) {
        SpannableStringBuilder spannableString = new SpannableStringBuilder(message);

        Pattern pattern = Pattern.compile(EMOJI_PATTERN);
        Matcher matcher = pattern.matcher(message);

        while (matcher.find()) {
            String emojiChars = matcher.group(0);
            int start = matcher.start();
            int end = matcher.end();

            Bitmap bitmap = emojiMap.get(emojiChars);
            if (bitmap != null) {
                ImageSpan imageSpan = toImageSpan(ctx, bitmap);
                spannableString.setSpan(imageSpan, start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            }
        }
        return spannableString;
    }


    public static CharSequence toEmojiChar(Context ctx, String emojiCode, Map<String, Bitmap> emojiMap) {
        SpannableStringBuilder spannableString = new SpannableStringBuilder(emojiCode);
        Bitmap bitmap = emojiMap.get(emojiCode);
        if (bitmap != null) {
            ImageSpan imageSpan = toImageSpan(ctx, bitmap);
            spannableString.setSpan(imageSpan, 0, emojiCode.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }

    public static ImageSpan toImageSpan(Context ctx, Bitmap bitmap){
        return new ImageSpan(ctx, bitmap);
//        return new ImageSpan(ctx, bitmap, DynamicDrawableSpan.ALIGN_BASELINE);
    }


    public static String writeEmoji(String emojiCode, EditText inputEditText) {
        int selectionStart = inputEditText.getSelectionStart();
        int selectionEnd = inputEditText.getSelectionEnd();

        if (selectionEnd > selectionStart) {
            inputEditText.getText().replace(selectionStart, selectionEnd, emojiCode);
        } else {
            inputEditText.getText().insert(selectionStart, emojiCode);
        }
        return inputEditText.getText().toString();
    }

    public static CharSequence writeEmoji(String emojiCode, EditText inputEditText, Map<String, Bitmap> emojiMap) {
        int selectionStart = inputEditText.getSelectionStart();
        int selectionEnd = inputEditText.getSelectionEnd();

        Context context = inputEditText.getContext();
        CharSequence emojiChar = EmojiBuilder.toEmojiChar(context, emojiCode, emojiMap);

        if (selectionEnd > selectionStart) {
            inputEditText.getText().replace(selectionStart, selectionEnd, emojiChar);
        } else {
            inputEditText.getText().insert(selectionStart, emojiChar);
        }
        return inputEditText.getText();
    }
}
