package pl.bestsoft.snake.model.events;

import java.io.Serializable;

/**
 * Wydarzenie związane z koniecznością wykonania ruchu przez węża.
 * Wydarzenia te wrzucane są do kolejki blokującej co pewien kwan czasu trzez wątek timera.
 */
public class TimerEvent extends GameEvent implements Serializable {
    private static final long serialVersionUID = 1L;

}
