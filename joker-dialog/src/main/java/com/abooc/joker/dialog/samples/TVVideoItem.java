//package com.abooc.joker.dialog;
//
//import org.fourthline.cling.support.model.DIDLObject.Property.DC;
//import org.fourthline.cling.support.model.Res;
//import org.fourthline.cling.support.model.item.VideoItem;
//
//public class TVVideoItem extends VideoItem {
//    public TVVideoItem(String id, String parentID, String title, String creator, Res... resource) {
//        super(id, parentID, title, creator, resource);
//        setClazz(CLASS);
//    }
//
//    public TVVideoItem setChannelID(String channelID) {
//        addProperty(new CHANNEL_ID(channelID));
//        return this;
//    }
//    public TVVideoItem setChannelName(String name) {
//        addProperty(new CHANNEL_NAME(name));
//        return this;
//    }
//    public TVVideoItem setChannelUid(String uid) {
//        addProperty(new CHANNEL_UID(uid));
//        return this;
//    }
//    public TVVideoItem setChannelNickname(String nickname) {
//        addProperty(new CHANNEL_NICKNAME(nickname));
//        return this;
//    }
//    public TVVideoItem setChannelAvatar(String avatar) {
//        addProperty(new CHANNEL_AVATAR(avatar));
//        return this;
//    }
//
//    static public class CHANNEL_ID extends Property<String> implements DC.NAMESPACE {
//        public CHANNEL_ID(String value) {
//            super(value, null);
//        }
//    }
//    static public class CHANNEL_NAME extends Property<String> implements DC.NAMESPACE {
//        public CHANNEL_NAME(String value) {
//            super(value, null);
//        }
//    }
//    static public class CHANNEL_UID extends Property<String> implements DC.NAMESPACE {
//        public CHANNEL_UID(String value) {
//            super(value, null);
//        }
//    }
//    static public class CHANNEL_NICKNAME extends Property<String> implements DC.NAMESPACE {
//        public CHANNEL_NICKNAME(String value) {
//            super(value, null);
//        }
//    }
//    static public class CHANNEL_AVATAR extends Property<String> implements DC.NAMESPACE {
//        public CHANNEL_AVATAR(String value) {
//            super(value, null);
//        }
//    }
//}