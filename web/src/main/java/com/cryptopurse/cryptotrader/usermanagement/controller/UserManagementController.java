package com.cryptopurse.cryptotrader.usermanagement.controller;

import com.cryptopurse.cryptotrader.usermanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping("/admin/usermanagement")
public class UserManagementController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = GET)
    public String index(final ModelMap modelMap) {
        modelMap.put("users", userService.findAll());
        return "admin/usermanagement";
    }

}
