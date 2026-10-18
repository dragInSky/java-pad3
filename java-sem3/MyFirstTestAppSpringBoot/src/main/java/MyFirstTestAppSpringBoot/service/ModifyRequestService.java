package MyFirstTestAppSpringBoot.service;

import MyFirstTestAppSpringBoot.model.Request;
import org.springframework.stereotype.Service;

@Service
public interface ModifyRequestService {

    void modify(Request request);

}
