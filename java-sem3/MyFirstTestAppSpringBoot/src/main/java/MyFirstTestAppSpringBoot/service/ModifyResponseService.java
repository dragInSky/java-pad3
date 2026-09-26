package MyFirstTestAppSpringBoot.service;

import MyFirstTestAppSpringBoot.model.Response;
import org.springframework.stereotype.Service;

@Service
public interface ModifyResponseService {

    Response modify(Response response);

}
