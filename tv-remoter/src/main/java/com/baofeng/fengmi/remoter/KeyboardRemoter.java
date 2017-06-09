package com.baofeng.fengmi.remoter;


import com.abooc.airremoter.Sender;

/**
 * 遥控器
 * <p/>
 * Created by author:李瑞宇
 * email:allnet@live.cn
 * on 16/5/13.
 */
public class KeyboardRemoter {


    public static final int ACTION_REMOTER = 601;

    /**
     * 模拟遥控器按键
     */
    public static class KeyCode {
        public static final int POWER = 99;
        public static final int OK = 100;

        public static final int UP = 101;
        public static final int LEFT = 102;
        public static final int DOWN = 103;
        public static final int RIGHT = 104;

        public static final int HOME = 106;
        public static final int BACK = 107;
        public static final int MENU = 108;
        public static final int BIU = 109;
        public static final int VOLUME_UP = 111;
        public static final int VOLUME_DOWN = 112;
    }

    private Sender mSender;

    KeyboardRemoter(Sender sender) {
        this.mSender = sender;
    }

    public void send(int keyCode, int keyEvent) {
        mSender.doSend(buildJson(keyCode, keyEvent));
    }

    public void sendVoice(int keyCode, String content) {
        mSender.doSend(buildJson(keyCode, content, 1));
    }

    public void send(int keyCode) {
        mSender.doSend(json(keyCode));
    }

    @Deprecated
    public static String buildJson2(int keyCode, int keyEvent) {
        return "{\"code\":" + ACTION_REMOTER
                + ", \"info\":{\"keycode\":" + keyCode + ", \"keyevent\":" + keyEvent + "}"
                + "}";
    }

    private String json(int keyCode) {
        return "{\"code\":" + ACTION_REMOTER
                + ", \"info\":{\"keycode\":" + keyCode + "}"
                + "}";
    }

    public static String buildJson(int keyCode, int keyEvent) {
        return "{" +
                "\"code\":" + ACTION_REMOTER + ", " +
                "\"appName\":" + "\"control\"" + ", " +
                "\"timeStamp\":" + "\"" + System.currentTimeMillis() + "\"" + ", " +
                "\"info\":" +
                "       {\"keycode\":" + keyCode + ", " +
                "       \"keyevent\":" + keyEvent +
                "       }"
                + ","
                +
                "\"data\":" +
                "       {\"keycode\":" + keyCode + ", " +
                "       \"keyevent\":" + keyEvent
                + "     }"
                + "}";
    }

    /**
     * 发送语音
     *
     * @param keyCode
     * @param content 语音文本
     * @param version 中间层所需版本号
     * @return
     */
    public static String buildJson(int keyCode, String content,int version) {
        return "{" +
                "\"appName\":" + "\"voice\"" + ", " +
                "\"timeStamp\":" + "\"" + System.currentTimeMillis() + "\"" + ", " +
                "\"version\":"+version+", " +
                "\"data\":" +
                "{\"msg\":\"" + content
                + "\"}"
                + "}";
    }
}
