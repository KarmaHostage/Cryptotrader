package com.karmahostage.cryptotrader.infrastructure;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping(method = GET, value = "/")
public class IndexController {

    @RequestMapping(method = GET)
    public String index() {
        return "index";
    }

}
