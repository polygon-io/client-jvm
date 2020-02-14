package io.polygon.kotlin.sdk.sample;

import com.tylerthrailkill.helpers.prettyprint.PrettyPrintKt;

import java.util.concurrent.CountDownLatch;

import io.polygon.kotlin.sdk.rest.PolygonRestApiCallback;
import io.polygon.kotlin.sdk.rest.PolygonRestClient;
import io.polygon.kotlin.sdk.rest.reference.MarketsDTO;
import io.polygon.kotlin.sdk.rest.reference.SupportedTickersParameters;
import io.polygon.kotlin.sdk.rest.reference.SupportedTickersParametersBuilder;

public class JavaUsageSample {

    public static void main(String[] args) throws InterruptedException {
        String polygonKey = System.getenv("POLYGON_API_KEY");
        if (polygonKey == null || polygonKey.isEmpty()) {
            System.err.println("Make sure you set your polygon API key in the POLYGON_API_KEY environment variable!");
            System.exit(1);
        }

        PolygonRestClient client = new PolygonRestClient(polygonKey);

        System.out.println("Blocking for markets...");
        final MarketsDTO markets = client.getReferenceClient().getSupportedMarketsBlocking();
        System.out.println("Got markets synchronously: " + markets.toString());

        final CountDownLatch latch = new CountDownLatch(1);
        System.out.println("Getting markets asynchronously...");

        client.getReferenceClient().getSupportedMarkets(new PolygonRestApiCallback<MarketsDTO>() {

            @Override
            public void onSuccess(MarketsDTO result) {
                System.out.println("Got markets asynchronously: " + result.toString());
                latch.countDown();
            }

            @Override
            public void onError(Throwable error) {
                System.out.println("Error getting markets asynchronously");
                error.printStackTrace();
                latch.countDown();
            }
        });

        latch.await();
        System.out.println("Done waiting for async market data\n\n");

    }

    public static void supportedTickersSample(PolygonRestClient polygonRestClient) {
        System.out.println("3 Supported Tickers: ");
        SupportedTickersParameters params = new SupportedTickersParametersBuilder()
                .tickersPerPage(3)
                .market("FX")
                .build();

        System.out.println(polygonRestClient
                .getReferenceClient()
                .getSupportedTickersBlocking(params)
        );
    }

    public static void tickerDetailsSample(PolygonRestClient polygonRestClient) {
        System.out.println("Redfin Ticker Details: ");
        System.out.println(polygonRestClient.getReferenceClient().getTickerDetailsBlocking("RDFN"));
    }

}
