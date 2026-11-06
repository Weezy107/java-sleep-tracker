package ru.yandex.practicum.sleeptracker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SleepTrackerApp {
    private static final List<Function<List<SleepingSession>, SleepAnalysisResult>> functions =
            List.of(new TotalSessionsFunction(),
                    new MinDurationFunction(),
                    new MaxDurationFunction(),
                    new AverageDurationFunction(),
                    new BadQualitySessionsFunction(),
                    new SleeplessNightsFunction(),
                    new ChronotypeFunction()
            );

    public static void main(String[] args) {
        Path filePath = Path.of(args[0]);

        //Создаем шаблон, по которому Java поймет формат даты
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

        //Открываем файл и превращаем его строки в список объектов SleepingSession
        try (Stream<String> lines = Files.lines(filePath)) {
            List<SleepingSession> sessions = lines
                    .map(line -> {
                        String[] parts = line.split(";");
                        LocalDateTime start = LocalDateTime.parse(parts[0], formatter);
                        LocalDateTime finish = LocalDateTime.parse(parts[1], formatter);
                        SleepQuality quality = SleepQuality.valueOf(parts[2]);

                        return new SleepingSession(start, finish, quality);
                    })
                    .collect(Collectors.toList());
            functions.forEach(function -> System.out.println(function.apply(sessions)));

        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }
}