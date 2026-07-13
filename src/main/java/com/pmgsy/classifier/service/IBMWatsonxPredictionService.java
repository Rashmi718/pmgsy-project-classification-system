package com.pmgsy.classifier.service;

import com.pmgsy.classifier.config.properties.IBMWatsonxProperties;
import com.pmgsy.classifier.dto.IBMRequest;
import com.pmgsy.classifier.dto.IBMResponse;
import com.pmgsy.classifier.exception.IBMWatsonxServiceException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Slf4j
@Service
@RequiredArgsConstructor
public class IBMWatsonxPredictionService {

    private final @Qualifier("ibmWatsonxRestClient") RestClient restClient;
    private final IBMWatsonxProperties properties;

    public IBMResponse predict(IBMRequest request) {
        log.info("Calling IBM watsonx.ai deployment {} for prediction", properties.deploymentId());
        String accessToken = fetchAccessToken();
        IBMResponse response = score(request, accessToken);
        log.info("IBM prediction call completed successfully");
        return response;
    }

    private String fetchAccessToken() {
        String formBody = "grant_type=urn:ibm:params:oauth:grant-type:apikey&apikey="
                + URLEncoder.encode(properties.apiKey(), StandardCharsets.UTF_8);

        try {
            TokenResponse tokenResponse = restClient.post()
                    .uri(StringUtils.hasText(properties.tokenUrl()) ? properties.tokenUrl() : "https://iam.cloud.ibm.com/identity/token")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .accept(MediaType.APPLICATION_JSON)
                    .body(formBody)
                    .retrieve()
                    .onStatus(statusCode -> statusCode.is4xxClientError() || statusCode.is5xxServerError(),
                            (request, response) -> {
                                HttpStatus status = HttpStatus.valueOf(response.getStatusCode().value());
                                throw new IBMWatsonxServiceException(status, "IBM IAM token request failed with HTTP " + status.value());
                            })
                    .body(TokenResponse.class);

            if (tokenResponse == null || !StringUtils.hasText(tokenResponse.accessToken())) {
                throw new IBMWatsonxServiceException(HttpStatus.BAD_GATEWAY, "IBM IAM token response was empty");
            }

            return tokenResponse.accessToken();
        } catch (ResourceAccessException ex) {
            log.error("IBM IAM token request timed out or could not connect", ex);
            throw new IBMWatsonxServiceException("IBM IAM token request timed out or could not connect", ex);
        } catch (RestClientException ex) {
            log.error("IBM IAM token request failed", ex);
            throw new IBMWatsonxServiceException(HttpStatus.BAD_GATEWAY, "IBM IAM token request failed", ex);
        }
    }

    private IBMResponse score(IBMRequest request, String accessToken) {
        String scoringUri = buildScoringUri();

        try {
            IBMResponse response = restClient.post()
                    .uri(scoringUri)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .body(request)
                    .retrieve()
                    .onStatus(status -> status.value() == 401 || status.value() == 403 || status.value() == 404 || status.is5xxServerError(),
                            (httpRequest, httpResponse) -> {
                                HttpStatus status = HttpStatus.valueOf(httpResponse.getStatusCode().value());
                                throw new IBMWatsonxServiceException(status, "IBM deployment scoring failed with HTTP " + status.value());
                            })
                    .body(IBMResponse.class);

            if (response == null) {
                throw new IBMWatsonxServiceException(HttpStatus.BAD_GATEWAY, "IBM deployment returned an empty response body");
            }

            return response;
        } catch (ResourceAccessException ex) {
            log.error("IBM deployment scoring request timed out or could not connect", ex);
            throw new IBMWatsonxServiceException("IBM deployment scoring request timed out or could not connect", ex);
        } catch (RestClientException ex) {
            log.error("IBM deployment scoring request failed", ex);
            throw new IBMWatsonxServiceException(HttpStatus.BAD_GATEWAY, "IBM deployment scoring request failed", ex);
        }
    }

    private String buildScoringUri() {
        String endpoint = properties.deploymentEndpoint();
        if (endpoint.endsWith("/")) {
            endpoint = endpoint.substring(0, endpoint.length() - 1);
        }

        String apiVersion = StringUtils.hasText(properties.apiVersion()) ? properties.apiVersion() : "2021-05-01";
        return endpoint + "/ml/v4/deployments/" + properties.deploymentId() + "/predictions?version=" + apiVersion;
    }

    private record TokenResponse(@com.fasterxml.jackson.annotation.JsonProperty("access_token") String accessToken) {
    }
}