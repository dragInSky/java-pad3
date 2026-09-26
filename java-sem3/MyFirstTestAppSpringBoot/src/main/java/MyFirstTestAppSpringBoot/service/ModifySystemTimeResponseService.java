package MyFirstTestAppSpringBoot.service;

import MyFirstTestAppSpringBoot.model.Response;
import MyFirstTestAppSpringBoot.util.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
@Qualifier("ModifySystemTimeResponseService")
public class ModifySystemTimeResponseService
        implements ModifyResponseService {

    @Override
    public Response modify(Response response) {

        log.info("systemTime before modify: {}", response);

        response.setSystemTime(DateTimeUtil.getCustomFormat()
                .format(new Date()));

        log.info("systemTime after modify: {}", response);

        return response;
    }
}
