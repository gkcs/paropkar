package paropkar.http;

import com.google.gson.Gson;
import com.ning.http.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

/**
 * Makes asynchronous HTTP requests. The method used can be either GET or POST. In case of getting a response code
 * other than 200,
 * an exception is thrown. Else, the response is returned in a future object.
 */

public class HttpClient {
    private static final String HTTP_GET_PRE_REQUEST_MESSAGE = "Making HTTP get request to {}";
    private static final String HTTP_POST_PRE_REQUEST_MESSAGE = "sending post request with body {}";
    private static final String POST = "POST", GET = "GET";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON = "application/json";
    private static final String ACCEPT = "Accept";
    private static final String REQUEST_USER_AGENT = "aadhaar_service_provider";
    private static final String scheme = "HTTP";
    private static final Logger logger = LoggerFactory.getLogger(HttpClient.class.getCanonicalName());
    private final Gson gson = new Gson();
    private final AsyncHttpClient asyncHttpClient;

    public HttpClient(@Value("${http-client-pool-size}") int poolSize,
                      @Value("${http-client-pool-size-per-host}") int poolSizePerHost,
                      @Value("${http-retry-attempts-on-io-exception}") int retry,
                      @Value("${http-connection-idle-timeout}") int connectionIdleTimeout,
                      @Value("${http-connect-timeout}") int connectTimeOut) {
        this.asyncHttpClient = new AsyncHttpClient(new AsyncHttpClientConfig.Builder()
                .setConnectTimeout(connectTimeOut)
                .setMaxConnectionsPerHost(poolSizePerHost)
                .setMaxConnections(poolSize)
                .setPooledConnectionIdleTimeout(connectionIdleTimeout)
                .setMaxRequestRetry(retry)
                .setUserAgent(REQUEST_USER_AGENT)
                .setReadTimeout(connectTimeOut)
                .setRequestTimeout(connectTimeOut)
                .setAllowPoolingConnections(true)
                .build());
    }

    public CompletableFuture<HttpResponse> sendPost(final String host,
                                                    final String url,
                                                    final int port,
                                                    final String json) {
        logger.info(HTTP_POST_PRE_REQUEST_MESSAGE, json);
        return execute(new RequestBuilder(POST)
                .setUrl(tryToGetURL(host, url, port).toString())
                .setHeaders(getBasicHeaders())
                .setBody(json).build());
    }

    public CompletableFuture<HttpResponse> sendGet(final String host,
                                                   final String url,
                                                   final int port) {

        URL formedUrl = tryToGetURL(host, url, port);
        logger.info(HTTP_GET_PRE_REQUEST_MESSAGE, formedUrl.toString());
        return execute(new RequestBuilder(GET)
                .setUrl(formedUrl.toString())
                .setHeaders(getBasicHeaders())
                .build());
    }

    private URL tryToGetURL(final String host, final String url, final int port) {
        URL formedUrl;
        try {
            formedUrl = new URL(scheme, host, port, url);
        } catch (MalformedURLException e) {
            throw new RuntimeException("URL cannot be formed.");
        }
        return formedUrl;
    }

    private CompletableFuture<HttpResponse> execute(final com.ning.http.client.Request request) {
        CompletableFuture<HttpResponse> completableFuture = new CompletableFuture<>();
        asyncHttpClient.executeRequest(request, new CustomAsyncCompletionHandler(completableFuture));
        return completableFuture;
    }


    private FluentCaseInsensitiveStringsMap getBasicHeaders() {
        FluentCaseInsensitiveStringsMap basicHeaders = new FluentCaseInsensitiveStringsMap();
        basicHeaders.add(CONTENT_TYPE, APPLICATION_JSON);
        basicHeaders.add(ACCEPT, APPLICATION_JSON);
        return basicHeaders;
    }

    public static class HttpResponse {
        private final String responseBody;
        private final int statusCode;

        public HttpResponse(final String responseBody, final int statusCode) {
            this.responseBody = responseBody;
            this.statusCode = statusCode;
        }

        public String getResponseBody() {
            return responseBody;
        }

        public int getStatusCode() {
            return statusCode;
        }
    }

    private class CustomAsyncCompletionHandler extends AsyncCompletionHandler<HttpResponse> {
        private static final String HTTP_SUCCESS_MESSAGE = "Http call successful, Status code: {}";
        private static final String HTTP_FAILURE_MESSAGE = "Http call failed. error code: {} response: {}";
        private static final int HTTP_SUCCESSFUL_RESPONSE_CODE = 200;
        private static final String HTTP_CALL_CANCELLED_MESSAGE = "Http Failure: ";
        private final CompletableFuture<HttpResponse> completableFuture;

        private CustomAsyncCompletionHandler(CompletableFuture<HttpResponse> completableFuture) {
            this.completableFuture = completableFuture;
        }

        @Override
        public void onThrowable(Throwable throwable) {
            logger.info(HTTP_CALL_CANCELLED_MESSAGE + " " + throwable.getMessage(), throwable);
            completeExceptionally(new RuntimeException(HTTP_CALL_CANCELLED_MESSAGE + " " + throwable.getMessage()));
        }

        @Override
        public HttpResponse onCompleted(Response response) {
            HttpResponse httpResponse = null;
            logger.info("Response: {}", gson.toJson(response, Response.class));
            try {
                logger.info(HTTP_SUCCESS_MESSAGE, response.getStatusCode());
                httpResponse =
                        new HttpResponse(response.getResponseBody(),
                                response.getStatusCode());
                if (httpResponse.getStatusCode() == HTTP_SUCCESSFUL_RESPONSE_CODE) {
                    complete(httpResponse);
                } else {
                    logger.info(HTTP_FAILURE_MESSAGE,
                            httpResponse.getStatusCode(),
                            httpResponse.getResponseBody());
                    completeExceptionally(new RuntimeException(String.valueOf(httpResponse.getStatusCode())));
                }
            } catch (Throwable t) {
                logger.info("found exception in reading input stream", t);
                completeExceptionally(t);
            }
            return httpResponse;
        }

        private void completeExceptionally(Throwable t) {
            completableFuture.completeExceptionally(t);
        }

        private void complete(HttpResponse httpResponse) {
            completableFuture.complete(httpResponse);
        }
    }
}
