package MyFirstTestAppSpringBoot.service;

import MyFirstTestAppSpringBoot.model.Positions;
import org.springframework.stereotype.Service;

@Service
public interface AnnualBonusService {

    double calculate(Positions positions, double salary, double bonus, int workDays);

    double calculateQuarterly(Positions positions, double salary, double bonus, int workDays);

}
