package com.cryptopurse.cryptotrader.admin;

import com.cryptopurse.cryptotrader.market.service.ImportConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping("/admin/import-configurations")
public class ImportConfigurationController {

    @Autowired
    private ImportConfigurationService importConfigurationService;

    @RequestMapping(method = GET)
    public String index(final ModelMap modelMap) {
        modelMap.put("configurations", importConfigurationService.getImportConfigurations());
        return "admin/import-configurations";
    }

}
