package MyFirstTestAppSpringBoot.service;

import MyFirstTestAppSpringBoot.model.Request;
import MyFirstTestAppSpringBoot.model.Systems;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Slf4j
@Service
@Qualifier("ModifySystemNameRequestService")
public class ModifySystemNameRequestService
        implements ModifyRequestService {

    private final RestClient restClient = RestClient.create();

    @Override
    public void modify(Request request) {

        log.info("systemName before modify: {}", request);

        request.setSystemName(Systems.SERVICE_1);

        log.info("systemName after modify: {}", request);

        restClient.post()
                .uri("http://localhost:8084/feedback")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .toBodilessEntity();

        log.info("request sent to Service 2: {}", request);
    }
}
