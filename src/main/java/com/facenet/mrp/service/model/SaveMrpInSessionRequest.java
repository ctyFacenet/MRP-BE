package com.facenet.mrp.service.model;

public class SaveMrpInSessionRequest {
    private boolean isHold = false;
    private String sessionId;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public boolean getIsHold() {
        return isHold;
    }

    public void setIsHold(boolean hold) {
        isHold = hold;
    }
}
