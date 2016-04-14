package com.lanou.mirror.bean;

/**
 * Created by Yi on 16/4/13.
 */
public class AilPayBeans {

    /**
     * msg :
     * data : {"str":"service=\"mobile.securitypay.pay\"&partner=\"2088021758262531\"&_input_charset=\"utf-8\"¬ify_url=\"http%3A%2F%2Fapi.mirroreye.cn%2Findex.php%2Fali_notify\"&out_trade_no=\"1460518641e0l\"&subject=\"KAREN WALKER\"&payment_type=\"1\"&seller_id=\"2088021758262531\"&total_fee=\"1538.00\"&body=\"KAREN WALKER\"&it_b_pay =\"30m\"&sign=\"PquJroNOMX8wLqX0h36eQUkYjqhB4yYmwqILXIvMD4TBL4F%2FRSiSgr9wxdZujU8hgFs0qCtC2OorN5Tsbl3LLgtzXRHzcWmLVLQQREQvDy6vJOhrwYXrKhlHS%2FXPR2r21U9thEtW3IvXgjvCtnQdDkU0LWhRKgr%2FptH7P0OXqkg%3D\"&sign_type=\"RSA\""}
     */

    private String msg;
    /**
     * str : service="mobile.securitypay.pay"&partner="2088021758262531"&_input_charset="utf-8"¬ify_url="http%3A%2F%2Fapi.mirroreye.cn%2Findex.php%2Fali_notify"&out_trade_no="1460518641e0l"&subject="KAREN WALKER"&payment_type="1"&seller_id="2088021758262531"&total_fee="1538.00"&body="KAREN WALKER"&it_b_pay ="30m"&sign="PquJroNOMX8wLqX0h36eQUkYjqhB4yYmwqILXIvMD4TBL4F%2FRSiSgr9wxdZujU8hgFs0qCtC2OorN5Tsbl3LLgtzXRHzcWmLVLQQREQvDy6vJOhrwYXrKhlHS%2FXPR2r21U9thEtW3IvXgjvCtnQdDkU0LWhRKgr%2FptH7P0OXqkg%3D"&sign_type="RSA"
     */

    private DataEntity data;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        private String str;

        public void setStr(String str) {
            this.str = str;
        }

        public String getStr() {
            return str;
        }
    }
}
