package com.cryptopurse.cryptotrader.market.batch;

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
public class KrakenImportJobConfiguration {

    @Bean(name = "import-kraken-job")
    public Job importKrakenJob(JobBuilderFactory jobs,
                               @Qualifier("import-kraken-step") Step krakenImporterStep) {
        return jobs.get("import-kraken-job")
                .incrementer(new RunIdIncrementer())
                .start(krakenImporterStep)
                .build();
    }

    @Bean(name = "import-kraken-step")
    public Step krakenImporterStep(StepBuilderFactory stepBuilderFactory,
                                   @Qualifier("kraken-trades-reader") ItemReader<Trade> krakenTradesReader,
                                   @Qualifier("kraken-import-writer") ItemWriter<Trade> krakenImportWriter) {
        return stepBuilderFactory
                .get("import-kraken-step")
                .<Trade, Trade>chunk(50)
                .reader(krakenTradesReader)
                .processor(new PassThroughItemProcessor<>())
                .writer(krakenImportWriter)
                .build();
    }

}
