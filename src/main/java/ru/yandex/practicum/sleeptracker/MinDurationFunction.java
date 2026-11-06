package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class MinDurationFunction implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessions) {
        long minDuration = sleepingSessions.stream()
                .mapToLong(session -> Duration.between(session.getSleepStart(), session.getSleepFinish()).toMinutes())
                .min()
                .orElse(0);

        return new SleepAnalysisResult("Самая короткая сессия сна",minDuration);
    }
}
