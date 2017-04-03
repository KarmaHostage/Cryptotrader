package com.cryptopurse.cryptotrader.admin;

import com.cryptopurse.cryptotrader.admin.dto.ImportConfigurationDto;
import com.cryptopurse.cryptotrader.exchange.service.supported.SupportedExchanges;
import com.cryptopurse.cryptotrader.market.domain.CurrencyPair;
import com.cryptopurse.cryptotrader.market.domain.ImportConfiguration;
import com.cryptopurse.cryptotrader.market.service.ImportConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

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

    @RequestMapping(method = GET, value = "/{id}/edit")
    public String edit(@ModelAttribute("importConfigurationDto") ImportConfigurationDto importConfigurationDto,
                       @PathVariable("id") final Long id,
                       final ModelMap modelMap) {
        final Optional<ImportConfiguration> savedConfiguration = importConfigurationService.findOne(id);
        if (!savedConfiguration.isPresent()) {
            return "404";
        } else {
            importConfigurationDto.fill(savedConfiguration.get());
            modelMap.put("exchanges", Arrays.asList(SupportedExchanges.values()));
            modelMap.put("currencypairs", Arrays.asList(CurrencyPair.values()));
            return "admin/import-configurations/edit";
        }
    }

    @RequestMapping(method = GET, value = "/new")
    public String createNew(@ModelAttribute("importConfigurationDto") final ImportConfigurationDto importConfigurationDto,
                            final ModelMap modelMap) {
        modelMap.put("exchanges", Arrays.asList(SupportedExchanges.values()));
        modelMap.put("currencypairs", Arrays.asList(CurrencyPair.values()));
        return "admin/import-configurations/edit";
    }

    @RequestMapping(method = POST, value = "/save")
    public String save(@ModelAttribute("importConfigurationDto") final ImportConfigurationDto importConfigurationDto,
                       final BindingResult bindingResult,
                       final ModelMap modelMap,
                       final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "admin/import-configurations/edit";
        } else {
            importConfigurationService.save(importConfigurationDto.toImportConfiguration());
            return "redirect:/admin/import-configurations";
        }
    }

}
