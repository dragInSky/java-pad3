package MyFirstTestAppSpringBoot.service;

import MyFirstTestAppSpringBoot.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@Qualifier("ModifyOperationUidResponseService")
public class ModifyOperationUidResponseService
        implements ModifyResponseService {

    @Override
    public Response modify(Response response) {

        log.info("operationUid before modify: {}", response);

        UUID uuid = UUID.randomUUID();

        response.setOperationUid(uuid.toString());

        log.info("operationUid after modify: {}", response);

        return response;
    }
}
