package MyFirstTestAppSpringBoot.service;

import MyFirstTestAppSpringBoot.model.Request;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Qualifier("ModifySourceRequestService")
public class ModifySourceRequestService
        implements ModifyRequestService {

    @Override
    public void modify(Request request) {

        log.info("source before modify: {}", request);

        request.setSource(String.valueOf(request.getSystemName()));

        log.info("source after modify: {}", request);
    }
}
