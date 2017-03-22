package com.cryptopurse.cryptotrader.learning;

import com.cryptopurse.cryptotrader.advice.domain.StrategyPeriod;
import com.cryptopurse.cryptotrader.market.domain.CurrencyPair;
import com.cryptopurse.cryptotrader.market.domain.KrakenTrade;
import com.cryptopurse.cryptotrader.market.repository.KrakenTradeRepository;
import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.collection.CollectionRecordReader;
import org.deeplearning4j.datasets.fetchers.MnistDataFetcher;
import org.deeplearning4j.datasets.iterator.impl.MnistDataSetIterator;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.weights.WeightInit;
import org.joda.time.DateTime;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class KrakenMarketLearner {
/*
    @Autowired
    private KrakenTradeRepository krakenTradeRepository;

    public void learn() {
        MultiLayerConfiguration build = new NeuralNetConfiguration.Builder()
                .iterations(1)
                .weightInit(WeightInit.XAVIER)
                .activation(Activation.RELU)
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .learningRate(0.05)
                .list()
                .layer(0, new DenseLayer.Builder().nIn(3).nOut(3)
                        .build())
                .layer(1, new DenseLayer.Builder().nIn(2).nOut(2)
                        .build())
                .layer(2, new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                        .activation(Activation.SOFTMAX)
                        .nIn(2).nOut(1).build())
                .backprop(true).pretrain(false)
                .build();

        List<KrakenTrade> allTrades = krakenTradeRepository.findRecentTrades(DateTime.now().minusDays(21).toDate());

        final Map<CurrencyPair, List<KrakenTrade>> tradesPerCurrencypair = allTrades
                .stream()
                .collect(Collectors.groupingBy(KrakenTrade::getCurrencyPair));

        tradesPerCurrencypair
                .forEach(
                        ((currencyPair, krakenTrades) -> {
                            train(currencyPair, krakenTrades);
                        })
                );
    }

    private void train(final CurrencyPair currencyPair, final List<KrakenTrade> krakenTrades) {
        DataSetIterator iter =
    }
    */

}
