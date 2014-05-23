package pl.bestsoft.snake.model.model;

/**
 * Klasa reprezentująca wolny punkt na planszy
 */
class EmptyPoint {
    /**
     * Położenie punktu na planszy
     */
    private final Coordinates coordinates;

    /**
     * Tworzy nowy pusty punkt na planszy.
     *
     * @param coordinates położenie punktu na planszy
     */
    public EmptyPoint(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Zwraca położenie punktu na planszy.
     *
     * @return położenie punktu na planszy
     */
    Coordinates getCoordinates() {
        return coordinates;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((coordinates == null) ? 0 : coordinates.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        EmptyPoint other = (EmptyPoint) obj;
        if (coordinates == null) {
            if (other.coordinates != null)
                return false;
        } else if (!coordinates.equals(other.coordinates))
            return false;
        return true;
    }
}
