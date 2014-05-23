package pl.bestsoft.snake.controler;

import pl.bestsoft.snake.dao.TextsDao;
import pl.bestsoft.snake.model.events.GameEvent;
import pl.bestsoft.snake.model.events.TimerEvent;

import java.util.concurrent.BlockingQueue;

/**
 * Timer odlicza czas pomiędzy kolejnymi ruchami węża.
 */
class Timer implements Runnable {
    /**
     * Kolejka blokująca w której timer umieszcza eventy.
     */
    private final BlockingQueue<GameEvent> blockingQueue;

    /**
     * Tworzy Timer.
     *
     * @param blockingQueue klejka blokująca zawierająca eventy od gracza
     */
    Timer(final BlockingQueue<GameEvent> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    /**
     * Usypia wątek po czym umieszcza TimerEvent w kolejce blokującej.
     */
    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(100);
                blockingQueue.add(new TimerEvent());
            }
        } catch (Exception e) {
            System.out.println(TextsDao.getText("Timer.0"));
            e.printStackTrace();
        }
    }
}
