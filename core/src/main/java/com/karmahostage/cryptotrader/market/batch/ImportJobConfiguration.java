package com.karmahostage.cryptotrader.market.batch;

import org.knowm.xchange.dto.marketdata.Trade;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.PassThroughItemProcessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ImportJobConfiguration {

    @Bean(name = "import-job")
    public Job importKrakenJob(JobBuilderFactory jobs,
                               @Qualifier("import-step") Step krakenImporterStep) {
        return jobs.get("import-job")
                .incrementer(new RunIdIncrementer())
                .start(krakenImporterStep)
                .build();
    }

    @Bean(name = "import-step")
    public Step krakenImporterStep(StepBuilderFactory stepBuilderFactory,
                                   @Qualifier("trades-reader") ItemReader<Trade> krakenTradesReader,
                                   @Qualifier("import-writer") ItemWriter<Trade> krakenImportWriter) {
        return stepBuilderFactory
                .get("import-step")
                .<Trade, Trade>chunk(50)
                .reader(krakenTradesReader)
                .processor(new PassThroughItemProcessor<>())
                .writer(krakenImportWriter)
                .build();
    }

}
