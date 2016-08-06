package com.cryptopurse.cryptotrader.usermanagement.controller;

import com.cryptopurse.cryptotrader.usermanagement.controller.dto.ChoosePasswordDto;
import com.cryptopurse.cryptotrader.usermanagement.domain.CryptotraderUser;
import com.cryptopurse.cryptotrader.usermanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

import static org.springframework.util.StringUtils.isEmpty;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RequestMapping(value = "/activation/{activationCode}")
@Controller
public class ActivationController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = GET)
    public String activate(@PathVariable("activationCode") String activationCode) {
        if (alreadyHasPassword(userService.findByActivationCode(activationCode))) {
            userService.activate(activationCode);
            return "redirect:/login?activation";
        } else {
            return "redirect:/activation/" + activationCode + "/choose-password";
        }
    }

    @RequestMapping(method = GET, value = "/choose-password")
    public String butRequiresPassword(@PathVariable("activationCode") String activationCode) {
        return "account/choose-password";
    }

    @RequestMapping(method = POST, value = "/choose-password")
    public String userChosePassword(@PathVariable("activationCode") String activationCode,
                                    @ModelAttribute ChoosePasswordDto choosePasswordDto,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "account/choose-password";
        }
        if (validPassword(choosePasswordDto)) {
            try {
                userService.setPasswordForActivationCode(activationCode, choosePasswordDto.getEmail(), choosePasswordDto.getPassword());
                userService.activate(activationCode);
                return "redirect:/login?activation";
            } catch (Exception ex) {
                redirectAttributes.addFlashAttribute("message", ex.getMessage());
                return "redirect:/activation/" + activationCode + "/choose-password";
            }
        } else {
            redirectAttributes.addFlashAttribute("message", "Please make sure your password confirmation is correct");
            return "redirect:/activation/" + activationCode + "/choose-password";
        }

    }

    private boolean validPassword(ChoosePasswordDto choosePasswordDto) {
        return !isEmpty(choosePasswordDto.getPassword()) && !isEmpty(choosePasswordDto.getPasswordConfirm()) && choosePasswordDto.getPassword().equals(choosePasswordDto.getPasswordConfirm());
    }

    private boolean alreadyHasPassword(Optional<CryptotraderUser> byActivationCode) {
        return (byActivationCode.isPresent() && !isEmpty(byActivationCode.get().getPassword()));
    }

}
