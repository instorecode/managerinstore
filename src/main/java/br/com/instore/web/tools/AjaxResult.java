package br.com.instore.web.tools;

public class AjaxResult implements java.io.Serializable {
    private boolean success;
    private String response;

    public AjaxResult() {
    }

    public AjaxResult(boolean success, String response) {
        this.success = success;
        this.response = response;
    }
    
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
