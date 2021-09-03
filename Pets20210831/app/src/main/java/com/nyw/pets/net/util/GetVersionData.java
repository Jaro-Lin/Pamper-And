package com.nyw.pets.net.util;

/**
 * Created by Administrator on 2016/12/9.
 */
public class GetVersionData {
    //版本ID
    private String versionID;
    //版本名称
    private String versionName;
    //app版本号
    private String versionCode;
    //APP更新的信息
    private String versionMessage;
    //APP下载地址
    private String versionPath;
    //是否要强制更新（1是强制更新，0是则反）
    private String forceUpdate;
    //更新时间
    private String addtime;

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionMessage() {
        return versionMessage;
    }

    public void setVersionMessage(String versionMessage) {
        this.versionMessage = versionMessage;
    }

    public String getVersionPath() {
        return versionPath;
    }

    public void setVersionPath(String versionPath) {
        this.versionPath = versionPath;
    }

    public String getForceUpdate() {
        return forceUpdate;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getVersionID() {
        return versionID;
    }

    public void setVersionID(String versionID) {
        this.versionID = versionID;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getVersionCode() {
        return versionCode;
    }


    public void setForceUpdate(String forceUpdate) {
        this.forceUpdate = forceUpdate;
    }
}
