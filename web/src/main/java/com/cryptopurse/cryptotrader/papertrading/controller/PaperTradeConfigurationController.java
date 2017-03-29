package com.cryptopurse.cryptotrader.papertrading.controller;

import com.cryptopurse.cryptotrader.papertrading.service.PaperTradeConfigurationService;
import com.cryptopurse.cryptotrader.usermanagement.domain.CryptotraderUser;
import com.cryptopurse.cryptotrader.usermanagement.utils.SecurityUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping("/user/papertrade-configurations")
public class PaperTradeConfigurationController {

    @Autowired
    private PaperTradeConfigurationService paperTradeConfigurationService;

    @RequestMapping(method = GET)
    public String index(final ModelMap modelMap) {
        final Optional<CryptotraderUser> user = SecurityUtility.currentUser();
        if (user.isPresent()) {
            modelMap.put("configurations", paperTradeConfigurationService.findByUser(user.get()));
            return "/user/papertrade-configurations";
        } else {
            return "redirect:/";
        }
    }

}
