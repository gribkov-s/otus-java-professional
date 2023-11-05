package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.*;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);
    private final Lock lock = new ReentrantLock();
    private final Condition read  = lock.newCondition();
    private final Condition write  = lock.newCondition();

    private int sharedCounter = 0;
    private boolean wasUpdated = false;

    public static void main(String[] args) {
        new App().run();
    }

    private void run() {
        new Thread(this::writer, "writer").start();
        new Thread(this::reader, "reader").start();
    }

    private void writer() {
        var step = 1;
        while (!Thread.currentThread().isInterrupted()) {
            lock.lock();
            try {
                while (wasUpdated) write.await();
                switch (sharedCounter) {
                    case 10 -> step = -1;
                    case 1 -> step = 1;
                }
                sharedCounter = sharedCounter + step;
                logger.info(String.valueOf(this.sharedCounter));
                wasUpdated = true;
                read.signal();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();
            }
            sleep(2);
        }
    }

    private void reader() {
        while (!Thread.currentThread().isInterrupted()) {
            lock.lock();
            try {
                while (!wasUpdated) read.await();
                logger.info(String.valueOf(this.sharedCounter));
                wasUpdated = false;
                write.signal();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();
            }
        }
    }

    private static void sleep(int seconds) {
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(seconds));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
