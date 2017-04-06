package com.karmahostage.cryptotrader.account.controller;

import com.karmahostage.cryptotrader.account.controller.dto.ForgotPasswordDto;
import com.karmahostage.cryptotrader.account.controller.dto.PasswordResetDto;
import com.karmahostage.cryptotrader.usermanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class PasswordResetController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/forgot-password", method = GET)
    public String forgotPassword() {
        return "account/forgot-password";
    }

    @RequestMapping(value = "/forgot-password", method = RequestMethod.POST)
    public String forgotPassword(@Valid @ModelAttribute ForgotPasswordDto forgotPasswordDto,
                                 BindingResult bindingResult,
                                 ModelMap modelMap) {
        if (bindingResult.hasErrors()) {
            return forgotPassword();
        } else {
            userService.forgotPassword(forgotPasswordDto.getEmail());
            modelMap.put("emailSent", true);
            return "account/forgot-password";
        }
    }

    @RequestMapping(value = "/reset-password/{passwordResetCode}", method = GET)
    public String resetPassword() {
        return "account/reset-password";
    }

    @RequestMapping(value = "/reset-password/{passwordResetCode}", method = POST)
    public String resetPassword(@Valid @ModelAttribute PasswordResetDto passwordResetDto,
                                BindingResult bindingResult,
                                ModelMap modelMap,
                                @PathVariable("passwordResetCode") String passwordResetCode) {
        if (bindingResult.hasErrors() || !isValid(passwordResetDto)) {
            return resetPassword();
        } else {
            userService.resetPassword(
                    passwordResetCode,
                    passwordResetDto.getPassword());
            modelMap.put("emailSent", true);
            return "account/reset-password";
        }

    }

    private boolean isValid(PasswordResetDto passwordResetDto) {
        return passwordResetDto.getPassword().equals(passwordResetDto.getPasswordConfirm());
    }
}
