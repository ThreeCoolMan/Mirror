package com.lanou.mirror.bean;

/**
 * Created by Yi on 16/4/5.
 */
public class LoginBeans {

    /**
     * result : 1
     * msg :
     * data : {"token":"08f46330b2634ed9ddb0dbf9be876379","uid":"57"}
     */

    private String result;
    private String msg;
    /**
     * token : 08f46330b2634ed9ddb0dbf9be876379
     * uid : 57
     */

    private DataEntity data;

    public void setResult(String result) {
        this.result = result;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public String getResult() {
        return result;
    }

    public String getMsg() {
        return msg;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        private String token;
        private String uid;

        public void setToken(String token) {
            this.token = token;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getToken() {
            return token;
        }

        public String getUid() {
            return uid;
        }
    }
}
