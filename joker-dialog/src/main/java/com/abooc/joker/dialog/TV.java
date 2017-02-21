//
//package com.abooc.joker.dialog;
//
//import android.text.TextUtils;
//import android.widget.Checkable;
//
//public class TV implements Checkable {
//    public String videoName;
//    public String ipAddress;
//
//    private boolean checked;
//
//    @Override
//    public void setChecked(boolean checked) {
//        this.checked = checked;
//    }
//
//    @Override
//    public boolean isChecked() {
//        return this.checked;
//    }
//
//    @Override
//    public void toggle() {
//        this.checked = !this.checked;
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj) {
//            return true;
//        }
//        if (!(obj instanceof TV)) {
//            return false;
//        }
//
//        TV other = (TV) obj;
//        if (TextUtils.equals(this.ipAddress, other.ipAddress)) {
//            return true;
//        }
//        return false;
//    }
//}
