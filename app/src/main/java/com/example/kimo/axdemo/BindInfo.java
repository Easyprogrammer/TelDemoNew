package com.example.kimo.axdemo;

/**
 * ClassName:${TYPE_NAME} <br/>
 * Function: ${TODO} ADD FUNCTION. <br/>
 * Reason:   ${TODO} ADD REASON. <br/>
 * Date:     2017/4/16 16:00 <br/>
 *
 * @author 76dgs02
 * @see
 * @since JDK 1.6
 */


public class BindInfo {
    /*appId	String	必选	平台配置
    caller	String	必选	主叫号码(必须为11位手机号，号码前加0086，如008613631686024)
    dstVirtualNum	String	可选	X 号码；与VirtualType属性配套使用
    virtualType	String	可选	Type=0{客户同步}Type=1{平台分配}
    name	String	必选	姓名
    cardtype	String	必选	证件类型{默认填写0；表示证件类型为身份证}
    cardno	String	必选	证件号码
    maxAge	String	可选	主叫+虚拟保护号码允许合作方最大cache存储时间(单位秒)
    cityId	String	必选	城市区号，比如北京0086755
    requestId	String	可选	字符串最大长度不超过128字节，该requestId在后面话单和录音URL推送中原样带回
    record	String	可选	是否录音，0表示不录音，1表示录音。默认为不录音
    statusUrl	String	可选	状态回调通知地址，正式环境可以配置默认推送地址
    hangupUrl	String	可选	话单推送地址，不填推到默认协商地址
    recordUrl	String	可选	录单URL回调通知地址，不填推到默认协商地址*/
    private String appId;
    private String callee;
    private String dstVirtualNum;

    public String getBindId() {
        return bindId;
    }

    public void setBindId(String bindId) {
        this.bindId = bindId;
    }

    public String getCallDisplay() {
        return calldisplay;
    }

    public void setCallDisplay(String calldisplay) {
        this.calldisplay = calldisplay;
    }

    private String virtualType;
    private String name;
    private String cardtype;
    private String cardno;
    private String maxAge;
    private String cityId;
    private String requestId;
    private String record;
    private String bindId;
    private String calldisplay;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getCallee() {
        return callee;
    }

    public void setCallee(String caller) {
        this.callee = caller;
    }

    public String getDstVirtualNum() {
        return dstVirtualNum;
    }

    public void setDstVirtualNum(String dstVirtualNum) {
        this.dstVirtualNum = dstVirtualNum;
    }

    public String getVirtualType() {
        return virtualType;
    }

    public void setVirtualType(String virtualType) {
        this.virtualType = virtualType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardtype() {
        return cardtype;
    }

    public void setCardtype(String cardtype) {
        this.cardtype = cardtype;
    }

    public String getCardno() {
        return cardno;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno;
    }

    public String getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(String maxAge) {
        this.maxAge = maxAge;
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

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }
}
