package com.example.zad1.simulation;


import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.TextField;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;


public class AnimationThread {

    private AtomicBoolean isAnimationRunning = new AtomicBoolean(false);
    private AtomicBoolean isAnimationPaused = new AtomicBoolean(false);

    private Thread thread;

    public AtomicBoolean getIsAnimationRunning() {
        return isAnimationRunning;
    }

    public AtomicBoolean getIsAnimationPaused() {
        return isAnimationPaused;
    }

    public void startAnimation(Environment environment) {
        isAnimationRunning.set(true);
        run(environment);
    }

    public void pauseAnimation() {
        isAnimationPaused.set(true);
    }

    public void repauseAnimation() {
        isAnimationPaused.set(false);
    }

    public void stopAnimation() {
        isAnimationRunning.set(false);
    }

    private void run(Environment environment){
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() {
                while (isAnimationRunning.get()) {
                    long callTimePeriod = 0;
                    if (!isAnimationPaused.get()) {
                        callTimePeriod += action(() -> environment.step());
                        callTimePeriod += action(() -> {
                            Platform.runLater(() -> {
                                System.out.println("Timestamp: " + environment.getTimestamp());
                                System.out.println("Real Distance: " + environment.getItemDistance());
                                System.out.println("Calculated Distance: " + environment.getDistanceSensor().getDistance());
                            });
                        });
                    }

                    long sleepTime = ((long) (environment.getTimeStep() * 1000)) - callTimePeriod;
                    try {
                        TimeUnit.MILLISECONDS.sleep(sleepTime > 0 ? sleepTime : 0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }
        };

        thread = new Thread(task);
        thread.start();
    }

    private long action(Runnable runnable) {
        long startTime = System.currentTimeMillis();
        runnable.run();
        return (System.currentTimeMillis() - startTime);
    }
}

