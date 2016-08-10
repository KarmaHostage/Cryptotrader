package com.cryptopurse.cryptotrader.market.batch;

import com.cryptopurse.cryptotrader.market.domain.KrakenImportConfiguration;
import com.cryptopurse.cryptotrader.market.service.KrakenImportConfigurationService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.List;

@Configuration
public class KrakenImportJobLauncher {

    private Log LOG = LogFactory.getLog(this.getClass());

    @Autowired
    private KrakenImportConfigurationService krakenImportConfigurationServiceImpl;
    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier("import-kraken-job")
    private Job importWowuction;

    @Scheduled(fixedDelay = 30000)
    public void startImports() {
        List<KrakenImportConfiguration> importConfigurations = krakenImportConfigurationServiceImpl.getImportConfigurations();
        importConfigurations
                .forEach(config -> {
                    try {
                        jobLauncher.run(importWowuction, new JobParametersBuilder().addDate("importTime", new Date())
                                .addLong("configurationId", config.getId()).toJobParameters());
                    } catch (Exception ex) {
                        LOG.error("unable to start kraken importer " + config.getId().toString());
                    }
                });
    }

}
