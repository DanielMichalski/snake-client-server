package pl.bestsoft.snake.model.model;

/**
 * Opisuje rozmiar częci ciała węża.
 */
public enum BodySize {
    NORMAL, BIG;

    /**
     * zmienia rozmiar częci ciała na duży
     */
    BodySize grow() {
        return BIG;
    }
}
