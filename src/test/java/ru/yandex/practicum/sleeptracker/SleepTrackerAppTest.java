package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

public class SleepTrackerAppTest {

    @Test
    public void shouldReturnZeroForEmptyList() {
        TotalSessionsFunction function = new TotalSessionsFunction();
        List<SleepingSession> sessions = List.of();
        SleepAnalysisResult result = function.apply(sessions);
        Assertions.assertEquals(0, result.getValue());
    }

    @Test
    public void shouldReturnAnyForNonEmptyList() {
        TotalSessionsFunction function = new TotalSessionsFunction();
        List<SleepingSession> sessions = List.of(new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 22, 15),
                        LocalDateTime.of(2025, 10, 2, 6, 0), SleepQuality.GOOD),
                new SleepingSession(LocalDateTime.of(2025, 10, 2, 23, 30),
                        LocalDateTime.of(2025, 10, 3, 7, 0), SleepQuality.GOOD));
        SleepAnalysisResult result = function.apply(sessions);
        Assertions.assertEquals(2, result.getValue());
    }

    @Test
    public void shouldReturnZeroMinDurationForEmptyList() {
        MinDurationFunction function = new MinDurationFunction();
        List<SleepingSession> sessions = List.of();
        SleepAnalysisResult result = function.apply(sessions);
        Assertions.assertEquals(0L, result.getValue());
    }

    @Test
    public void shouldReturnCorrectMinDuration() {
        MinDurationFunction function = new MinDurationFunction();
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 22, 0),
                        LocalDateTime.of(2025, 10, 2, 6, 0),
                        SleepQuality.GOOD
                ), // 480 минут
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 14, 0),
                        LocalDateTime.of(2025, 10, 2, 14, 50),
                        SleepQuality.NORMAL
                )  // 50 минут
        );
        SleepAnalysisResult result = function.apply(sessions);
        Assertions.assertEquals(50L, result.getValue());
    }

    @Test
    public void shouldReturnZeroMaxDurationForEmptyList() {
        MaxDurationFunction function = new MaxDurationFunction();
        List<SleepingSession> sessions = List.of();
        SleepAnalysisResult result = function.apply(sessions);
        Assertions.assertEquals(0L, result.getValue());
    }

    @Test
    public void shouldReturnCorrectMaxDuration() {
        MaxDurationFunction function = new MaxDurationFunction();
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 22, 0),
                        LocalDateTime.of(2025, 10, 2, 6, 0),
                        SleepQuality.GOOD
                ), // 480 минут (самая длинная)
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 14, 0),
                        LocalDateTime.of(2025, 10, 2, 14, 50),
                        SleepQuality.NORMAL
                )  // 50 минут
        );
        SleepAnalysisResult result = function.apply(sessions);
        Assertions.assertEquals(480L, result.getValue());
    }

    @Test
    public void shouldReturnZeroAverageDurationForEmptyList() {
        AverageDurationFunction function = new AverageDurationFunction();
        List<SleepingSession> sessions = List.of();
        SleepAnalysisResult result = function.apply(sessions);
        Assertions.assertEquals(0.0, result.getValue());
    }

    @Test
    public void shouldReturnCorrectAverageDuration() {
        AverageDurationFunction function = new AverageDurationFunction();
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 22, 0),
                        LocalDateTime.of(2025, 10, 2, 6, 0),
                        SleepQuality.GOOD
                ), // 480 минут
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 14, 0),
                        LocalDateTime.of(2025, 10, 2, 15, 0),
                        SleepQuality.NORMAL
                )  // 60 минут
        );
        SleepAnalysisResult result = function.apply(sessions);
        Assertions.assertEquals(270.0, result.getValue());
    }

    @Test
    public void shouldReturnZeroBadSessionsWhenAllAreGood() {
        BadQualitySessionsFunction function = new BadQualitySessionsFunction();
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 22, 0),
                        LocalDateTime.of(2025, 10, 2, 6, 0),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 23, 0),
                        LocalDateTime.of(2025, 10, 3, 7, 0),
                        SleepQuality.NORMAL
                )
        );
        SleepAnalysisResult result = function.apply(sessions);
        Assertions.assertEquals(0L, result.getValue());
    }

    @Test
    public void shouldCountBadQualitySessionsCorrectly() {
        BadQualitySessionsFunction function = new BadQualitySessionsFunction();
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 22, 0),
                        LocalDateTime.of(2025, 10, 2, 6, 0),
                        SleepQuality.BAD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 23, 0),
                        LocalDateTime.of(2025, 10, 3, 7, 0),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 3, 23, 30),
                        LocalDateTime.of(2025, 10, 4, 6, 20),
                        SleepQuality.BAD
                )
        );
        SleepAnalysisResult result = function.apply(sessions);
        Assertions.assertEquals(2L, result.getValue());
    }

    @Test
    public void shouldReturnZeroSleeplessNightsForEmptyList() {
        SleeplessNightsFunction function = new SleeplessNightsFunction();
        List<SleepingSession> sessions = List.of();
        SleepAnalysisResult result = function.apply(sessions);
        Assertions.assertEquals(0L, result.getValue());
    }

    @Test
    public void shouldReturnZeroSleeplessNightsWhenSleptEveryNightAndDaytime() {
        SleeplessNightsFunction function = new SleeplessNightsFunction();
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 22, 15),
                        LocalDateTime.of(2025, 10, 2, 8, 0),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 14, 30),
                        LocalDateTime.of(2025, 10, 2, 15, 20),
                        SleepQuality.NORMAL
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 23, 0),
                        LocalDateTime.of(2025, 10, 3, 8, 0),
                        SleepQuality.NORMAL
                )
        );
        SleepAnalysisResult result = function.apply(sessions);
        Assertions.assertEquals(0L, result.getValue());
    }

    @Test
    public void shouldCountNightAsNotSleeplessWhenSleptBetweenMidnightAndSixAm() {
        SleeplessNightsFunction function = new SleeplessNightsFunction();
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 2, 0),
                        LocalDateTime.of(2025, 10, 2, 5, 0),
                        SleepQuality.BAD
                )
        );
        SleepAnalysisResult result = function.apply(sessions);
        Assertions.assertEquals(0L, result.getValue());
    }

    @Test
    public void shouldDetectSleeplessNightWhenFirstSessionStartsBetweenSixAmAndNoon() {
        SleeplessNightsFunction function = new SleeplessNightsFunction();
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 7, 0),
                        LocalDateTime.of(2025, 10, 2, 11, 0),
                        SleepQuality.BAD
                )
        );
        SleepAnalysisResult result = function.apply(sessions);
        Assertions.assertEquals(1L, result.getValue());
    }

    @Test
    public void shouldCorrectlyCountSleeplessNightsAcrossMonthBoundary() {
        SleeplessNightsFunction function = new SleeplessNightsFunction();
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 9, 30, 23, 0),
                        LocalDateTime.of(2025, 10, 1, 7, 0),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 13, 0),
                        LocalDateTime.of(2025, 10, 2, 15, 0),
                        SleepQuality.NORMAL
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 22, 30),
                        LocalDateTime.of(2025, 10, 3, 7, 30),
                        SleepQuality.GOOD
                )
        );
        SleepAnalysisResult result = function.apply(sessions);
        Assertions.assertEquals(1L, result.getValue());
    }

    @Test
    public void shouldClassifyUserAsOwl() {
        ChronotypeFunction function = new ChronotypeFunction();
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 23, 30),
                        LocalDateTime.of(2025, 10, 2, 9, 30),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 3, 1, 0),
                        LocalDateTime.of(2025, 10, 3, 10, 0),
                        SleepQuality.NORMAL
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 3, 22, 30),
                        LocalDateTime.of(2025, 10, 4, 8, 0),
                        SleepQuality.GOOD
                )
        );
        SleepAnalysisResult result = function.apply(sessions);
        Assertions.assertEquals(Chronotype.OWL, result.getValue());
    }

    @Test
    public void shouldClassifyUserAsLarkAndIgnoreDaytimeSleep() {
        ChronotypeFunction function = new ChronotypeFunction();
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 21, 30),
                        LocalDateTime.of(2025, 10, 2, 6, 30),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 13, 0),
                        LocalDateTime.of(2025, 10, 2, 14, 0),
                        SleepQuality.NORMAL
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 3, 15, 0),
                        LocalDateTime.of(2025, 10, 3, 16, 0),
                        SleepQuality.NORMAL
                )
        );
        SleepAnalysisResult result = function.apply(sessions);
        Assertions.assertEquals(Chronotype.LARK, result.getValue());
    }

    @Test
    public void shouldClassifyUserAsDoveWhenTieBetweenOwlAndLark() {
        ChronotypeFunction function = new ChronotypeFunction();
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 23, 30),
                        LocalDateTime.of(2025, 10, 2, 9, 30),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 21, 0),
                        LocalDateTime.of(2025, 10, 3, 6, 0),
                        SleepQuality.GOOD
                )
        );
        SleepAnalysisResult result = function.apply(sessions);
        Assertions.assertEquals(Chronotype.DOVE, result.getValue());
    }
}