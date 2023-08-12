package com.eqipped.helper;

public class CCAvenuePaymentRequest {

    private String enc_request;
    private String access_code;
    private String command;
    private String request_type;
    private String response_type;
    private String version;

    public String getEnc_request() {
        return enc_request;
    }

    public void setEnc_request(String enc_request) {
        this.enc_request = enc_request;
    }

    public String getAccess_code() {
        return access_code;
    }

    public void setAccess_code(String access_code) {
        this.access_code = access_code;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getRequest_type() {
        return request_type;
    }

    public void setRequest_type(String request_type) {
        this.request_type = request_type;
    }

    public String getResponse_type() {
        return response_type;
    }

    public void setResponse_type(String response_type) {
        this.response_type = response_type;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
