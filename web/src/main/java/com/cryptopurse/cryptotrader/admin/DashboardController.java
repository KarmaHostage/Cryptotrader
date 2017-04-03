package com.cryptopurse.cryptotrader.admin;

import com.cryptopurse.cryptotrader.market.service.TradehistoryService;
import com.cryptopurse.cryptotrader.usermanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @SubscribeMapping("/admin/dashboard/user-count")
    public int getUserCount() {
        return userService.findAll().size();
    }

    @SubscribeMapping("/admin/dashboard/trade-count")
    public long getTradeCount() {
        return tradehistoryService.count();
    }

}
