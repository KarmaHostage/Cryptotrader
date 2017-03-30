package com.cryptopurse.cryptotrader.papertrading.controller;

import com.cryptopurse.cryptotrader.papertrading.controller.dto.PaperTradePerConfigurationDTo;
import com.cryptopurse.cryptotrader.papertrading.service.PaperTradeService;
import com.cryptopurse.cryptotrader.usermanagement.utils.SecurityUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Comparator;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user/papertrade")
public class PaperTradeController {

    @Autowired
    private PaperTradeService paperTradeService;

    @RequestMapping(method = RequestMethod.GET)
    public String index(final ModelMap map) {
        if (SecurityUtility.userIsAnonymous()) {
            return "redirect:/";
        } else {
            map.put("papertrades", paperTradeService.findByUser(SecurityUtility.currentUser().get())
                    .entrySet()
                    .stream()
                    .map(entry -> new PaperTradePerConfigurationDTo(entry.getKey(), entry.getValue()))
                    .sorted(Comparator.comparingLong(p -> p.getConfiguration().getId()))
                    .collect(Collectors.toList()));

            return "user/papertrades";
        }

    }

}
