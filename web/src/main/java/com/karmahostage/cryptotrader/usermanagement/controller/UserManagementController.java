package com.karmahostage.cryptotrader.usermanagement.controller;

import com.karmahostage.cryptotrader.usermanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping("/admin/users")
public class UserManagementController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = GET)
    public String index(final ModelMap modelMap) {
        modelMap.put("users", userService.findAll());
        return "admin/usermanagement";
    }

    @RequestMapping(method = RequestMethod.POST, params = "disable", value = "/{id}")
    public String disable(@PathVariable("id") Long id) {
        userService.disable(id);
        return "redirect:/admin/users";
    }

    @RequestMapping(method = RequestMethod.POST, params = "enable", value = "/{id}")
    public String enable(@PathVariable("id") Long id) {
        userService.enable(id);
        return "redirect:/admin/users";
    }

}
