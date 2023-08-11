package io.polygon.kotlin.sdk.sample;

import io.polygon.kotlin.sdk.DefaultJvmHttpClientProvider;
import io.polygon.kotlin.sdk.rest.ComparisonQueryFilterParameters;
import io.polygon.kotlin.sdk.rest.ComparisonQueryFilterParametersBuilder;
import io.polygon.kotlin.sdk.rest.*;
import io.polygon.kotlin.sdk.rest.experimental.FinancialsParameters;
import io.polygon.kotlin.sdk.rest.experimental.FinancialsParametersBuilder;
import io.polygon.kotlin.sdk.rest.reference.*;
import io.polygon.kotlin.sdk.websocket.*;
import kotlinx.coroutines.channels.Channel;
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

        JavaIteratorSample.IteratorSample(client);

        financialsSample(client);

        System.out.println("Websocket sample:");
        websocketSample(polygonKey);
    }

    public static void websocketSample(String polygonKey) {
        PolygonWebSocketClient client = new PolygonWebSocketClient(
                polygonKey,
                Feed.RealTime.INSTANCE,
                Market.Crypto.INSTANCE,
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

    public static void websocketLaunchpadSample(String polygonKey) {
        PolygonWebSocketClient client = new PolygonWebSocketClient(
                polygonKey,
                Feed.RealTime.INSTANCE,
                Market.LaunchpadStocks.INSTANCE,
                new DefaultPolygonWebSocketListener() {
                    @Override
                    public void onReceive(@NotNull PolygonWebSocketClient client, @NotNull PolygonWebSocketMessage message) {
                        if (message instanceof PolygonWebSocketMessage.LaunchpadMessage) {
                            System.out.println("Launchpad " + message);
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
                new PolygonWebSocketSubscription(PolygonWebSocketChannel.LaunchpadStocks.AggPerMinute.INSTANCE, "TSLA"));
        client.subscribeBlocking(subs);

        try {
            Thread.sleep(60000);
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

    public static void dividendsSample(PolygonRestClient polygonRestClient) {
        System.out.println("GE dividends:");
        DividendsParameters geParams = new DividendsParametersBuilder()
                .ticker(ComparisonQueryFilterParameters.equal("GE"))
                .limit(1)
                .build();
        System.out.println(polygonRestClient.getReferenceClient().getDividendsBlocking(geParams));

        System.out.println("Dividends with cash amounts between $1 and $10");
        DividendsParameters cashAmountFilterParams = new DividendsParametersBuilder()
                .cashAmount(new ComparisonQueryFilterParametersBuilder<Double>()
                        .greaterThanOrEqual(1.0)
                        .lessThanOrEqual(10.0)
                        .build())
                .limit(1)
                .build();
        System.out.println(polygonRestClient.getReferenceClient().getDividendsBlocking(cashAmountFilterParams));
    }

    public static void financialsSample(PolygonRestClient polygonRestClient) {
        System.out.println("RDFN Financials");
        FinancialsParameters params = new FinancialsParametersBuilder().ticker("RDFN").build();
        System.out.println(polygonRestClient.getExperimentalClient().getFinancialsBlocking(params));
    }

}