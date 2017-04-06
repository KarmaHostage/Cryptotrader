package com.karmahostage.cryptotrader.admin;

import com.karmahostage.cryptotrader.market.service.TradehistoryService;
import com.karmahostage.cryptotrader.usermanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping("/admin/dashboard")
public class DashboardController {

    @Autowired
    private UserService userService;
    @Autowired
    private TradehistoryService tradehistoryService;

    @RequestMapping(method = GET)
    public String index(final ModelMap map) {
        return "admin/dashboard";
    }

    @RequestMapping("/user-count")
    @ResponseBody
    public int getUserCount() {
        return userService.findAll().size();
    }

    @RequestMapping("/trade-count")
    @ResponseBody
    public long getTradeCount() {
        return tradehistoryService.count();
    }

}
