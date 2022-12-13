package io.polygon.kotlin.sdk.sample;

import io.polygon.kotlin.sdk.rest.PolygonRestClient;
import io.polygon.kotlin.sdk.rest.reference.SupportedTickersParameters;
import io.polygon.kotlin.sdk.rest.reference.SupportedTickersParametersBuilder;

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

}
