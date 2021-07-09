package com.open.assignment.stub;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.serverError;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.ClasspathFileSource;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

public class JsonStub {

    private static final String CONTENT_TYPE_HEADER = "Content-Type";
    private static final String CONTENT_TYPE_JSON = "application/json";

    private static WireMockConfiguration configure() {
        return WireMockConfiguration.wireMockConfig()
                .fileSource(new ClasspathFileSource("testdata"))
                .extensions(new CustomResponseTemplateTransformer());

    }

    private static void setUp() {

        // Get agreements for an user
        stubFor(get(urlPathMatching("/agreements/([a-zA-Z0-9]*)"))
                .willReturn(
                        aResponse()
                                .withBodyFile("agreements/{{request.path.[1]}}.json")
                                .withFixedDelay(0)
                                .withHeader(CONTENT_TYPE_HEADER, CONTENT_TYPE_JSON)));

        // Get accounts details
        stubFor(get(urlMatching("/accounts/\\d+"))
                .willReturn(
                        aResponse()
                                .withBodyFile("accounts/{{request.path.[1]}}.json")
                                .withFixedDelay(500)
                                .withHeader(CONTENT_TYPE_HEADER, CONTENT_TYPE_JSON)));

        // Get accounts details error for 1234567893
        stubFor(get(urlMatching("/accounts/1234567893"))
                .willReturn(
                        serverError().withBodyFile("accounts/{{request.path.[1]}}.json")));

        // Get debit card details
        stubFor(get(urlMatching("/debit-card/\\d+"))
                .willReturn(
                        aResponse()
                                .withBodyFile("debit-card/{{request.path.[1]}}.json")
                                .withFixedDelay(2000)
                                .withHeader(CONTENT_TYPE_HEADER, CONTENT_TYPE_JSON)));

        // Get debit card error for 7777
        stubFor(get(urlMatching("/debit-card/7777"))
                .willReturn(
                        serverError().withBodyFile("debit-card/{{request.path.[1]}}.json")));
    }

    public static void main(final String[] args) {
        new WireMockServer(configure()).start();
        setUp();
    }

}
