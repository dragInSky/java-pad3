package MyFirstTestAppSpringBoot.service;

import MyFirstTestAppSpringBoot.model.Positions;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Year;

@Service
public class AnnualBonusServiceImpl implements AnnualBonusService {

    private final Clock clock;

    public AnnualBonusServiceImpl() {
        this(Clock.systemDefaultZone());
    }

    AnnualBonusServiceImpl(Clock clock) {
        this.clock = clock;
    }

    @Override
    public double calculate(Positions positions, double salary, double bonus, int workDays) {
        int daysInYear = Year.now(clock).isLeap() ? 366 : 365;
        return salary * bonus * daysInYear * positions.getPositionCoefficient() / workDays;
    }

    @Override
    public double calculateQuarterly(Positions positions, double salary, double bonus, int workDays) {
        if (!positions.isManager()) {
            return 0;
        }
        return calculate(positions, salary, bonus, workDays) / 4;
    }
}
