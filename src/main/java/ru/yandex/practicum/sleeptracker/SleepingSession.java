package ru.yandex.practicum.sleeptracker;

import java.time.LocalDateTime;

public class SleepingSession {
    private LocalDateTime sleepStart;
    private LocalDateTime sleepFinish;
    private SleepQuality sleepQuality;

    public SleepingSession(LocalDateTime sleepStart, LocalDateTime sleepFinish, SleepQuality sleepQuality) {
        this.sleepFinish = sleepFinish;
        this.sleepStart = sleepStart;
        this.sleepQuality = sleepQuality;
    }

    public LocalDateTime getSleepStart() {return sleepStart;}

    public LocalDateTime getSleepFinish() {return sleepFinish;}

    public SleepQuality getSleepQuality() {return sleepQuality;}
}
