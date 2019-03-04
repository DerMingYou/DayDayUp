package com.yjbest.daydayup.http.eventbus;

/**
 * Description:
 * Data：2019/3/4-15:13
 * Author: DerMing_You
 */
public class ChangeLanguageEventBus {
    private String languageType;

    public String getLanguageType() {
        return languageType;
    }

    public ChangeLanguageEventBus(String languageType) {
        this.languageType = languageType;
    }
}
