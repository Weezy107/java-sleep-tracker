package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class AverageDurationFunction implements Function<List<SleepingSession>, SleepAnalysisResult> {

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessions) {
        double avgDuration = sleepingSessions.stream()
                .mapToLong(session -> Duration.between(session.getSleepStart(), session.getSleepFinish()).toMinutes())
                .average()
                .orElse(0.0);

        return new SleepAnalysisResult("Средняя длительность сессии сна",avgDuration);
    }
}
