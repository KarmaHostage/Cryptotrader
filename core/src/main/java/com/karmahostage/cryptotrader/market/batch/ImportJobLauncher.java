package com.karmahostage.cryptotrader.market.batch;

import com.karmahostage.cryptotrader.market.domain.ImportConfiguration;
import com.karmahostage.cryptotrader.market.service.ImportConfigurationService;
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
public class ImportJobLauncher {

    private Log LOG = LogFactory.getLog(this.getClass());

    @Autowired
    private ImportConfigurationService importConfigurationServiceImpl;
    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier("import-job")
    private Job tradeImportJob;

    @Scheduled(fixedDelay = 30000)
    public void startImports() {
        List<ImportConfiguration> importConfigurations = importConfigurationServiceImpl.getImportConfigurations();
        importConfigurations
                .forEach(config -> {
                    try {
                        jobLauncher.run(tradeImportJob, new JobParametersBuilder()
                                .addDate("importTime", new Date())
                                .addLong("configurationId", config.getId())
                                .toJobParameters()
                        );
                    } catch (Exception ex) {
                        LOG.error("unable to start importer " + config.getId().toString());
                    }
                });
    }

}
