package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.function.Function;

public class SleeplessNightsFunction implements Function<List<SleepingSession>, SleepAnalysisResult> {

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessions) {
        if (sleepingSessions.isEmpty()) {
            return new SleepAnalysisResult("Количество бессонных ночей", 0L);
        }

        //Считаем количество ночных сессий сна
        long nightSessionsCount = sleepingSessions.stream()
                .filter(session -> {
                    LocalDate startDate = session.getSleepStart().toLocalDate();
                    LocalDate finishDate = session.getSleepFinish().toLocalDate();
                    return !startDate.equals(finishDate) || session.getSleepStart().getHour() < 6;
                })
                .count();

        LocalDateTime firstStart = sleepingSessions.getFirst().getSleepStart();
        LocalDate startDate = firstStart.toLocalDate();

        if (firstStart.getHour() < 12) {
            startDate = startDate.minusDays(1);
        }

        LocalDate endDate = sleepingSessions.getLast().getSleepFinish().toLocalDate();

        long totalNights = ChronoUnit.DAYS.between(startDate, endDate);

        long sleeplessNights = Math.max(0L, totalNights - nightSessionsCount);

        return new SleepAnalysisResult("Количество бессонных ночей", sleeplessNights);
    }
}