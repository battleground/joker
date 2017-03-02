package com.baofeng.fengmi.remoter;

import com.abooc.airremoter.Sender;

/**
 * Created by dayu on 2017/3/2.
 */
public class SenderImpl {

    private static Sender iSender;
    private static SenderBuilder iSenderBuilder;

    private SenderImpl() {
    }

    public static void setSender(Sender sender) {
        iSender = sender;
    }

    public static void setBuilder(SenderBuilder builder) {
        iSenderBuilder = builder;
    }

    public static Sender getSender() {
        return iSender;
    }

    public static Sender buildSender(String ip) {
        return iSenderBuilder.creator(ip);
    }

    public interface SenderBuilder {
        Sender creator(String ip);
    }
}
