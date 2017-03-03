package com.bftv.tv_remoter_samples;

import com.abooc.airremoter.Sender;
import com.baofeng.fengmi.remoter.SenderImpl;

/**
 * Created by dayu on 2017/3/2.
 */

public class OkBuilder implements SenderImpl.SenderBuilder {
    @Override
    public Sender creator(String ip) {
        return OkSender.create(ip, OkSender.PORT_REMOTER);
    }
}
