package com.github.chillibox.exp.controller;

import com.github.chillibox.exp.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * <p>Created on 2017/7/10.</p>
 *
 * @author Gonster
 */
@Controller
public class BaseController {

    private Constants constants;

    @Autowired
    public void setConstants(Constants constants) {
        this.constants = constants;
    }

    public Constants getConstants() {
        return constants;
    }

    @ModelAttribute("appConst")
    public Constants mapConstants() {
        return constants;
    }

    @ModelAttribute("isAdmin")
    public Boolean role(HttpServletRequest request) {
        return request.isUserInRole(Constants.ROLE_ADMIN);
    }

    @ModelAttribute("username")
    public String username(Principal principal) {
        if (principal == null) return "";
        return principal.getName();
    }
}
