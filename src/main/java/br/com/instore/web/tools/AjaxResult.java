package br.com.instore.web.tools;

public class AjaxResult implements java.io.Serializable {
    private boolean success;
    private String response;
    private Object object;

    public AjaxResult() {
    }

    public AjaxResult(boolean success, String response) {
        this.success = success;
        this.response = response;
    }

    public AjaxResult(boolean success, String response, Object object) {
        this.success = success;
        this.response = response;
        this.object = object;
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

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
