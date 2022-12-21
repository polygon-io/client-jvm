package io.polygon.kotlin.sdk.sample;

import io.polygon.kotlin.sdk.rest.*;
import io.polygon.kotlin.sdk.rest.reference.SupportedTickersParameters;
import io.polygon.kotlin.sdk.rest.reference.SupportedTickersParametersBuilder;
import io.polygon.kotlin.sdk.websocket.PolygonWebSocketMessage;

public class JavaIteratorSample {

    public static void IteratorSample(PolygonRestClient client) {
        System.out.println("Iterating 10 stocks tickers");
        SupportedTickersParameters params = new SupportedTickersParametersBuilder()
                .sortBy("ticker")
                .sortDescending(false)
                .market("stocks")
                .limit(3)
                .build();

        // Limit to only the first 10 so this sample ends quickly.
        // Note that since the limit = 3 in the params,
        // this iterator will make 4 requests under the hood
        client.getReferenceClient().listSupportedTickers(params)
                .asStream() // Convert to a Java stream for ease of use.
                .limit(10)
                .forEach((tickerDTO -> System.out.println(tickerDTO.getTicker())));
    }

    public static void TradesIteratorSample(PolygonRestClient client) {
        System.out.println("Running trade iterator");
        TradesParameters params = new TradesParametersBuilder()
                .limit(1)
                .build();

        client.listTrades("F", params)
                .asStream() // Convert to a Java stream for ease of use.
                .limit(2)
                .forEach((trade -> System.out.println(trade.getPrice())));
    }

    public static void QuotesIteratorSample(PolygonRestClient client) {
        System.out.println("Running Quote iterator");
        QuotesParameters params = new QuotesParametersBuilder()
                .limit(1)
                .build();

        client.listQuotes("F", params)
                .asStream() // Convert to a Java stream for ease of use.
                .limit(2)
                .forEach((Quote -> System.out.println(Quote.getAskPrice())));
    }
}
