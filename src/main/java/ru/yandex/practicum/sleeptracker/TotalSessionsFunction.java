package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class TotalSessionsFunction implements Function<List<SleepingSession>,SleepAnalysisResult> {

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessions) {
        return new SleepAnalysisResult("Количество сессий сна",sleepingSessions.size());
    }
}
