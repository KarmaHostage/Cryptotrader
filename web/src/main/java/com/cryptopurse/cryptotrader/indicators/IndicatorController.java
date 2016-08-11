package com.cryptopurse.cryptotrader.indicators;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/indicators")
public class IndicatorController {

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "indicators/indicators";
    }
}
