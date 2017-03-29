package com.cryptopurse.cryptotrader.admin;

import com.cryptopurse.cryptotrader.market.service.UserTradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping("/admin/papertrade")
public class UserTradesController {

    @Autowired
    private UserTradeService userTradeService;

    @RequestMapping(method = GET)
    public String index(final ModelMap modelMap) {
        modelMap.put("papertrades", userTradeService.findAll());
        return "admin/papertrades";
    }

}
