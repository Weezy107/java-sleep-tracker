package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ChronotypeFunction implements Function<List<SleepingSession>, SleepAnalysisResult> {

    private static final LocalTime NIGHT_END = LocalTime.of(6, 0);
    private static final LocalTime LARK_SLEEP_LIMIT = LocalTime.of(22, 0);
    private static final LocalTime LARK_WAKE_LIMIT = LocalTime.of(7, 0);
    private static final LocalTime OWL_SLEEP_START = LocalTime.of(23, 0);
    private static final LocalTime OWL_WAKE_START = LocalTime.of(9, 0);

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessions) {
        Map<Chronotype, Long> counts = sleepingSessions.stream()
                .filter(this::isNightSession)
                .map(this::classifySession)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        long owlCount = counts.getOrDefault(Chronotype.OWL, 0L);
        long larkCount = counts.getOrDefault(Chronotype.LARK, 0L);
        long doveCount = counts.getOrDefault(Chronotype.DOVE, 0L);

        Chronotype resultType;
        if (owlCount > larkCount && owlCount > doveCount) {
            resultType = Chronotype.OWL;
        } else if (larkCount > owlCount && larkCount > doveCount) {
            resultType = Chronotype.LARK;
        } else {
            resultType = Chronotype.DOVE;
        }

        return new SleepAnalysisResult("Хронотип пользователя", resultType);
    }

    private boolean isNightSession(SleepingSession session) {
        LocalDate startDate = session.getSleepStart().toLocalDate();
        LocalDate finishDate = session.getSleepFinish().toLocalDate();
        return !startDate.equals(finishDate) || session.getSleepStart().getHour() < 6;
    }

    private Chronotype classifySession(SleepingSession session) {
        LocalTime startTime = session.getSleepStart().toLocalTime();
        LocalTime finishTime = session.getSleepFinish().toLocalTime();

        // Сова: лёг после 23:00 (или уже после полуночи до 06:00) и проснулся после 09:00
        boolean isOwlSleep = startTime.isAfter(OWL_SLEEP_START) || startTime.isBefore(NIGHT_END);
        if (isOwlSleep && finishTime.isAfter(OWL_WAKE_START)) {
            return Chronotype.OWL;
        }

        // Жаворонок: лёг вечером до 22:00 (но не после полуночи!) и проснулся до 07:00
        boolean isLarkSleep = !startTime.isBefore(NIGHT_END) && startTime.isBefore(LARK_SLEEP_LIMIT);
        if (isLarkSleep && finishTime.isBefore(LARK_WAKE_LIMIT)) {
            return Chronotype.LARK;
        }

        // Во всех остальных случаях — Голубь
        return Chronotype.DOVE;
    }
}