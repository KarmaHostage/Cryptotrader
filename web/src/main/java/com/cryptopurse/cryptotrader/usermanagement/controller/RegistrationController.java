package com.cryptopurse.cryptotrader.usermanagement.controller;

import com.cryptopurse.cryptotrader.usermanagement.controller.dto.RegistrationDto;
import com.cryptopurse.cryptotrader.usermanagement.service.UserService;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping(value = "/register")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = GET)
    public String index() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null
                && authentication.isAuthenticated()
                && !authentication.getPrincipal().equals("anonymousUser")) {
            return "redirect:/";
        } else {
            return "register";
        }
    }

    @RequestMapping(method = POST)
    public String doRegistration(@Valid @ModelAttribute RegistrationDto registrationDto,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null
                && authentication.isAuthenticated()
                && !authentication.getPrincipal().equals("anonymousUser")) {
            return "redirect:/";
        } else {
            if (!bindingResult.hasErrors() && isValid(registrationDto, redirectAttributes)) {
                userService.register(registrationDto.getEmail(), registrationDto.getPassword());
                return "redirect:/register/email-sent";
            } else {
                return "redirect:/register";
            }
        }
    }

    @RequestMapping(method = GET, value = "/email-sent")
    public String registrationCompleted() {
        return "account/registration-completed";
    }

    private boolean isValid(RegistrationDto registrationDto, RedirectAttributes redirectAttributes) {
        boolean valid = true;
        if (StringUtils.isEmpty(registrationDto.getEmail()) || !EmailValidator.getInstance().isValid(registrationDto.getEmail())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Please fill in a valid email address");
            valid = false;
        }

        if (StringUtils.isEmpty(registrationDto.getPassword()) || StringUtils.isEmpty(registrationDto.getPasswordRepeat())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Please provide a secure password");
            valid = false;
        }

        if (!registrationDto.getPassword().equals(registrationDto.getPasswordRepeat())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Please make sure both passwords are equal");
            valid = false;
        }

        if (!registrationDto.isTos()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Please read and agree with the terms of service");
            valid = false;
        }
        return valid;
    }

}
