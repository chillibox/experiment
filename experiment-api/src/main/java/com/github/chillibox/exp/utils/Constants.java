package com.github.chillibox.exp.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Created on 2017/7/5.</p>
 *
 * @author Gonster
 */

@Configuration
public class Constants {

    public static final String COUPON_PATH = "/public/c";

    public static final String ROLE_ADMIN = "admin";
    public static final String ROLE_STAFF = "staff";
    public static final String ROLE_PREFIX = "ROLE_";

    @Value("${app.resource.version:}")
    private String appVersion;

    @Value("${app.admin.console.title:}")
    private String consoleTitle;

    @Value("${app.admin.console.brand:}")
    private String consoleBrand;

    public String getConsoleTitle() {
        return consoleTitle;
    }

    public void setConsoleTitle(String consoleTitle) {
        this.consoleTitle = consoleTitle;
    }

    public String getConsoleBrand() {
        return consoleBrand;
    }

    public void setConsoleBrand(String consoleBrand) {
        this.consoleBrand = consoleBrand;
    }

}
