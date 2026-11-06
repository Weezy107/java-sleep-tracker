package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class BadQualitySessionsFunction implements Function<List<SleepingSession>, SleepAnalysisResult> {

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessions) {
        long badSession = sleepingSessions.stream()
                .filter(session -> session.getSleepQuality().equals(SleepQuality.BAD))
                .count();

        return new SleepAnalysisResult("Количество плохих сессий сна",badSession);
    }
}
