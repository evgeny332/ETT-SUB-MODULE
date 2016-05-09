package com.rh.push.notifications.server.spring.root;

/*
@author Ankit
*/

public class PushNotificationConfig {

    private String appleCertificate ;
    private String appleCertificatePassword;
    private String androidAPiKey;
    boolean dev;
    boolean isPushNotRegUpdateSend=false;
    private String pushQueueName;

    public String getAppleCertificatePassword() {
        return appleCertificatePassword;
    }

    public void setAppleCertificatePassword(String appleCertificatePassword) {
        this.appleCertificatePassword = appleCertificatePassword;
    }

    public String getAndroidAPiKey() {
        return androidAPiKey;
    }

    public void setAndroidAPiKey(String androidAPiKey) {
        this.androidAPiKey = androidAPiKey;
    }

    public String getAppleCertificate() {
        return appleCertificate;
    }

    public void setAppleCertificate(String appleCertificate) {
        this.appleCertificate = appleCertificate;
    }

    public boolean isDev() {
        return dev;
    }

    public void setDev(boolean dev) {
        this.dev = dev;
    }

	public boolean isPushNotRegUpdateSend() {
		return isPushNotRegUpdateSend;
	}

	public void setPushNotRegUpdateSend(boolean isPushNotRegUpdateSend) {
		this.isPushNotRegUpdateSend = isPushNotRegUpdateSend;
	}

	public String getPushQueueName() {
		return pushQueueName;
	}

	public void setPushQueueName(String pushQueueName) {
		this.pushQueueName = pushQueueName;
	}
 
	
}
