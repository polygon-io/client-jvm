package io.polygon.kotlin.sdk.sample;

import io.polygon.kotlin.sdk.rest.*;
import io.polygon.kotlin.sdk.rest.reference.MarketsDTO;
import io.polygon.kotlin.sdk.rest.reference.SupportedTickersParameters;
import io.polygon.kotlin.sdk.rest.reference.SupportedTickersParametersBuilder;
import io.polygon.kotlin.sdk.websocket.*;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

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

        System.out.println("Using options");
        AggregatesDTO groupedDaily = client.getGroupedDailyAggregatesBlocking(
                new GroupedDailyParametersBuilder().locale("us").market("stocks").date("2022-12-08").build(),
                PolygonRestOptions.withTimeout(10_000),
                PolygonRestOptions.withQueryParam("additional-param", "additional-value"),
                PolygonRestOptions.withHeader("X-Custom-Header", "custom-header-value")
        );

        System.out.println("Got " + groupedDaily.getResults().size() + " results from grouped daily");

        System.out.println("Websocket sample:");
        websocketSample(polygonKey);
    }

    public static void websocketSample(String polygonKey) {
        PolygonWebSocketClient client = new PolygonWebSocketClient(
                polygonKey,
                PolygonWebSocketCluster.Crypto,
                new DefaultPolygonWebSocketListener() {
                    @Override
                    public void onReceive(@NotNull PolygonWebSocketClient client, @NotNull PolygonWebSocketMessage message) {
                        if (message instanceof PolygonWebSocketMessage.RawMessage) {
                            System.out.println(new String(((PolygonWebSocketMessage.RawMessage) message).getData()));
                        } else {

                            System.out.println(message.toString());
                        }
                    }

                    @Override
                    public void onError(@NotNull PolygonWebSocketClient client, @NotNull Throwable error) {
                        System.out.println("Error in websocket");
                        error.printStackTrace();
                    }
                });

        client.connectBlocking();

        List<PolygonWebSocketSubscription> subs = Collections.singletonList(
                new PolygonWebSocketSubscription(PolygonWebSocketChannel.Crypto.Level2Books.INSTANCE, "BTC-USD"));
        client.subscribeBlocking(subs);

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        client.unsubscribeBlocking(subs);
        client.disconnectBlocking();
    }

    public static void supportedTickersSample(PolygonRestClient polygonRestClient) {
        System.out.println("3 Supported Tickers: ");
        SupportedTickersParameters params = new SupportedTickersParametersBuilder()
                .limit(3)
                .market("fx")
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
