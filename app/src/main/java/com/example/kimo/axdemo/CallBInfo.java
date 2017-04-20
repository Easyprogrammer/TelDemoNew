package com.example.kimo.axdemo;

/**
 * ClassName:${TYPE_NAME} <br/>
 * Function: ${TODO} ADD FUNCTION. <br/>
 * Reason:   ${TODO} ADD REASON. <br/>
 * Date:     2017/4/18 10:28 <br/>
 *
 * @author 76dgs02
 * @see
 * @since JDK 1.6
 */


public class CallBInfo {
    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getCaller() {
        return caller;
    }

    public void setCaller(String caller) {
        this.caller = caller;
    }

    public String getBindId() {
        return bindId;
    }

    public void setBindId(String bindId) {
        this.bindId = bindId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
    /*param.put("appId",appId);
    param.put("caller","18588461043");
    param.put("bindId", "8065b4e06c3e4d84953229cdc5ec6302");
	param.put("cityId", "0086851");
    param.put("requestId", requestId);*/

    private String appId;
    private String caller;
    private String bindId;
    private String cityId;
    private String requestId;
}
