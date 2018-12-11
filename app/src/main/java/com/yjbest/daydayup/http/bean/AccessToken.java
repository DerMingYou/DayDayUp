package com.yjbest.daydayup.http.bean;

/**
 * Description:
 * Data：2018/10/27-15:09
 * Author: DerMing_You
 */
public class AccessToken {

    /**
     * code : 200
     * message : success
     */

    private DataEntity data;
    private int code;
    private String message;

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataEntity {
        /**
         * access_token : cc8b0d89-1672-43ca-9603-53656b1de32f
         * token_type : bearer
         * expires_in : 32893
         * scope : read write
         */

        private String access_token;
        private String token_type;
        private String expires_in;
        private String scope;

        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }

        public String getToken_type() {
            return token_type;
        }

        public void setToken_type(String token_type) {
            this.token_type = token_type;
        }

        public String getExpires_in() {
            return expires_in;
        }

        public void setExpires_in(String expires_in) {
            this.expires_in = expires_in;
        }

        public String getScope() {
            return scope;
        }

        public void setScope(String scope) {
            this.scope = scope;
        }
    }
}
