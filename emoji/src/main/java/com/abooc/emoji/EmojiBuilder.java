package com.abooc.emoji;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.widget.EditText;

import com.abooc.util.Debug;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dayu on 2017/1/17.
 */

public class EmojiBuilder {

    public static String EMOJI_PATTERN = "\\[[^\\[\\[]+\\]";


    /**
     * 匹配图片
     *
     * @param emoji
     * @param bitmapMap
     * @return
     */
    public static Bitmap findBitmap(String emoji, Map<String, Bitmap> bitmapMap) {
        Bitmap bitmap = bitmapMap.get(emoji);
        return bitmap;
    }

    /**
     * @param message 源文本，即目标字符串，如：瞧[惊讶]，这是一条带表情符的消息[微笑]！
     * @return 返回将表情符转为图片显示的内容。
     */
    public static CharSequence toEmojiString(Context ctx, String message, Map<String, Bitmap> emojiMap) {
        Debug.anchor(message);
        SpannableStringBuilder spannableString = new SpannableStringBuilder(message);

        Pattern pattern = Pattern.compile(EMOJI_PATTERN);
        Matcher matcher = pattern.matcher(message);

        HashMap<int[], String> map = new HashMap<>();
        while (matcher.find()) {
            String emojiChars = matcher.group(0);
            int start = matcher.start();
            int end = matcher.end();

            map.put(new int[]{start, end}, emojiChars);
            System.out.println("From map: " + emojiChars + ", [" + start + " - " + end + "]");

            Bitmap bitmap = emojiMap.get(emojiChars);
            ImageSpan imageSpan = new ImageSpan(ctx, bitmap);
            spannableString.setSpan(imageSpan, start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        System.out.println("\n");
        return spannableString;
    }


    public static CharSequence toEmoji(Context ctx, String emojiChars, Map<String, Bitmap> emojiMap) {
        SpannableStringBuilder spannableString = new SpannableStringBuilder(emojiChars);
        Bitmap bitmap = emojiMap.get(emojiChars);
        ImageSpan imageSpan = new ImageSpan(ctx, bitmap);
        spannableString.setSpan(imageSpan, 0, emojiChars.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }


    public static String writeEmoji(String emojiChars, EditText inputEditText) {
        int selectionStart = inputEditText.getSelectionStart();
        int selectionEnd = inputEditText.getSelectionEnd();
        Debug.anchor("selectionStart:" + selectionStart + ", selectionEnd:" + selectionEnd);

        if (selectionEnd > selectionStart) {
            inputEditText.getText().replace(selectionStart, selectionEnd, emojiChars);
        } else {
            inputEditText.getText().insert(selectionStart, emojiChars);
        }
        return inputEditText.getText().toString();
    }

    public static CharSequence writeEmoji(String emojiChars, EditText inputEditText, Map<String, Bitmap> emojiMap) {
        int selectionStart = inputEditText.getSelectionStart();
        int selectionEnd = inputEditText.getSelectionEnd();
        Debug.anchor("selectionStart:" + selectionStart + ", selectionEnd:" + selectionEnd);

        Context context = inputEditText.getContext();
        CharSequence emojiChar = EmojiBuilder.toEmoji(context, emojiChars, emojiMap);

        if (selectionEnd > selectionStart) {
            inputEditText.getText().replace(selectionStart, selectionEnd, emojiChar);
        } else {
            inputEditText.getText().insert(selectionStart, emojiChar);
        }
        return inputEditText.getText();
    }
}
