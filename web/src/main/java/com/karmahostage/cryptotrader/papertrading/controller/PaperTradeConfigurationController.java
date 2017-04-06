package com.karmahostage.cryptotrader.papertrading.controller;

import com.karmahostage.cryptotrader.advice.domain.StrategyPeriod;
import com.karmahostage.cryptotrader.advice.domain.StrategyType;
import com.karmahostage.cryptotrader.exchange.service.supported.SupportedExchanges;
import com.karmahostage.cryptotrader.market.domain.CurrencyPair;
import com.karmahostage.cryptotrader.papertrading.controller.dto.PaperTradeConfigurationDto;
import com.karmahostage.cryptotrader.papertrading.domain.PaperTradeConfiguration;
import com.karmahostage.cryptotrader.papertrading.service.PaperTradeConfigurationService;
import com.karmahostage.cryptotrader.usermanagement.domain.CryptotraderUser;
import com.karmahostage.cryptotrader.usermanagement.utils.CurrentUser;
import com.karmahostage.cryptotrader.usermanagement.utils.SecurityUtility;
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
@RequestMapping("/user/papertrade-configurations")
public class PaperTradeConfigurationController {

    @Autowired
    private PaperTradeConfigurationService paperTradeConfigurationService;

    @RequestMapping(method = GET)
    public String index(final ModelMap modelMap) {
        final Optional<CryptotraderUser> user = SecurityUtility.currentUser();
        if (user.isPresent()) {
            modelMap.put("configurations", paperTradeConfigurationService.findByUser(user.get()));
            return "user/papertrade-configurations";
        } else {
            return "redirect:/";
        }
    }

    @RequestMapping(value = "/new", method = GET)
    public String createNew(@ModelAttribute(name = "paperTradeConfiguration") PaperTradeConfigurationDto paperTradeConfigurationDto,
                            final ModelMap modelMap) {
        modelMap.put("currencypairs", Arrays.asList(CurrencyPair.values()));
        modelMap.put("exchanges", Arrays.asList(SupportedExchanges.values()));
        return "user/papertrade/edit";
    }

    @RequestMapping(value = "/{id}/edit", method = GET)
    public String edit(final ModelMap modelMap,
                       @ModelAttribute(name = "paperTradeConfiguration") PaperTradeConfigurationDto paperTradeConfiguration,
                       @PathVariable("id") final Long id,
                       @CurrentUser CryptotraderUser cryptotraderUser) {
        final Optional<PaperTradeConfiguration> config = paperTradeConfigurationService.findOne(id);
        if (!config.isPresent()) {
            return "404";
        } else {
            if (config.get().getUser().isSame(cryptotraderUser)) {
                modelMap.put("currencypairs", Arrays.asList(CurrencyPair.values()));
                modelMap.put("exchanges", Arrays.asList(SupportedExchanges.values()));
                modelMap.put("strategyperiods", Arrays.asList(StrategyPeriod.values()));
                modelMap.put("strategytypes", Arrays.asList(StrategyType.values()));
                return "user/papertrade/edit";
            } else {
                throw new IllegalArgumentException("Not allowed to edit");
            }
        }
    }

    @RequestMapping(value = "/save", method = POST)
    public String save(@ModelAttribute(name = "paperTradeConfiguration") final PaperTradeConfigurationDto paperTradeConfiguration,
                       final BindingResult bindingResult,
                       final RedirectAttributes redirectAttributes,
                       final ModelMap modelMap,
                       @CurrentUser final CryptotraderUser user) {
        if (bindingResult.hasErrors()) {
            return "user/papertrade/edit";
        } else {
            try {
                paperTradeConfigurationService.save(paperTradeConfiguration.toPapertradeConfiguration(), user);
                redirectAttributes.addAttribute("message", "configuration successfully saved");
                return "redirect:/user/papertrade-configurations";
            } catch (Exception ex) {
                modelMap.put("message", ex.getMessage());
                return "user/papertrade/edit";
            }
        }
    }


}
