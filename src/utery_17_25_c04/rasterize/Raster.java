package utery_17_25_c04.rasterize;

import java.util.Optional;

public interface Raster<T> {

    default boolean checkBounds(int x, int y) {
        return x >= 0 && y >= 0 && x < getWidth() && y < getHeight();
    }

    /**
     * Clear canvas
     */
    void clear();

    /**
     * Set clear value
     *
     * @param clearValue clear value
     */
    void setClearValue(T clearValue);

    /**
     * Get horizontal size
     *
     * @return width
     */
    int getWidth();

    /**
     * Get vertical size
     *
     * @return height
     */
    int getHeight();

    /**
     * Get element value at [x,y] position
     *
     * @param x horizontal coordinate
     * @param y vertical coordinate
     * @return element value
     */
    Optional<T> getElement(int x, int y);

    /**
     * Set element value at [x,y] position
     *
     * @param x       horizontal coordinate
     * @param y       vertical coordinate
     * @param element element value
     */
    void setElement(int x, int y, T element);

}
