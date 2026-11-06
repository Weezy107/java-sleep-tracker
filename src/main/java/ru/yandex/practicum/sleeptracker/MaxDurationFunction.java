package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class MaxDurationFunction implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessions) {
        long maxDuration = sleepingSessions.stream()
                .mapToLong(session -> Duration.between(session.getSleepStart(), session.getSleepFinish()).toMinutes())
                .max()
                .orElse(0);

        return new SleepAnalysisResult("Самая длинная сессия сна",maxDuration);
    }
}
