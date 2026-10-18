package MyFirstTestAppSpringBoot.service;

import MyFirstTestAppSpringBoot.model.Positions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;

class AnnualBonusServiceImplTest {

    private static final Clock YEAR_2026 = Clock.fixed(Instant.parse("2026-06-01T00:00:00Z"), ZoneOffset.UTC);
    private static final Clock LEAP_YEAR_2028 = Clock.fixed(Instant.parse("2028-06-01T00:00:00Z"), ZoneOffset.UTC);

    @Test
    void calculate() {

        // given
        Positions position = Positions.HR;
        double bonus = 2.0;
        int workDays = 243;
        double salary = 100000.00;

        // when
        double result = new AnnualBonusServiceImpl(YEAR_2026).calculate(position, salary, bonus, workDays);

        // then
        double expected = 360493.8271604938;
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void calculateInLeapYear() {

        // given
        Positions position = Positions.HR;
        double bonus = 2.0;
        int workDays = 243;
        double salary = 100000.00;

        // when
        double result = new AnnualBonusServiceImpl(LEAP_YEAR_2028).calculate(position, salary, bonus, workDays);

        // then
        double expected = 361481.48148148146;
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void calculateQuarterlyForManager() {

        // given
        Positions position = Positions.TL;
        double bonus = 2.0;
        int workDays = 243;
        double salary = 100000.00;

        // when
        double result = new AnnualBonusServiceImpl(YEAR_2026).calculateQuarterly(position, salary, bonus, workDays);

        // then
        double expected = 195267.48971193415;
        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @EnumSource(value = Positions.class, names = {"DEV", "HR"})
    void calculateQuarterlyForNotManager(Positions position) {

        // given
        double bonus = 2.0;
        int workDays = 243;
        double salary = 100000.00;

        // when
        double result = new AnnualBonusServiceImpl(YEAR_2026).calculateQuarterly(position, salary, bonus, workDays);

        // then
        assertThat(result).isZero();
    }
}
