package com.karmahostage.cryptotrader.infrastructure;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping(method = GET)
public class TermsOfServiceController {

    @RequestMapping(method = GET, value = "/terms-of-service")
    public String index() {
        return "tos";
    }

    @RequestMapping(method = GET, value = "/tos")
    public String tos() {
        return "redirect:/terms-of-service";
    }

}
