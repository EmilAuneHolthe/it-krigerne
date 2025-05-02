package inf112.skeleton.model.map;

import static inf112.skeleton.model.GamePanel.UNIT_SCALE;

/**
 * Represents a rectangular border area in the game world.
 * This class defines a rectangular area with coordinates and provides methods
 * to check if a point is inside the area and retrieve its dimensions.
 * All coordinates are automatically scaled by UNIT_SCALE.
 */
public class Borders { /** The x-coordinate of the left border */
    public final float x1;
    public final float x2;
    public final float y1;
    public final float y2;
    public final float width;
    public final float height;
    final String name;

    /**
     * Constructs a new Borders object with the specified coordinates and name.
     * All coordinates are automatically scaled by UNIT_SCALE.
     *
     * @param x1 The left x-coordinate
     * @param y1 The bottom y-coordinate
     * @param x2 The right x-coordinate
     * @param y2 The top y-coordinate
     * @param name The name of the border area
     */
    public Borders(float x1, float y1, float x2, float y2, String name) {
        this.x1 = x1*UNIT_SCALE;
        this.y1 = y1*UNIT_SCALE;
        this.x2 = x2*UNIT_SCALE;
        this.y2 = y2*UNIT_SCALE;
        this.width = x2;
        this.height = y2;
        this.name = name;
    }

    /**
     * Checks if a point is inside the border area.
     * The coordinates are automatically scaled by UNIT_SCALE.
     *
     * @param x The x-coordinate of the point to check
     * @param y The y-coordinate of the point to check
     * @return The name of the border area if the point is inside, null otherwise
     */
    public String isInside(float x, float y) {
        float xy = x/UNIT_SCALE;
        float yx = y/UNIT_SCALE;
        if (xy >= x1 && xy <= x2 && yx >= y1 && yx <= y2) {
            return name;
        }
        return null;
    }

    /**
     * Gets the left x-coordinate of the border.
     *
     * @return The left x-coordinate
     */
    public float getX1() {
        return x1;
    }

    /**
     * Gets the right x-coordinate of the border.
     *
     * @return The right x-coordinate
     */
    public float getX2() {
        return x2;
    }

    /**
     * Gets the bottom y-coordinate of the border.
     *
     * @return The bottom y-coordinate
     */
    public float getY1() {
        return y1;
    }

    /**
     * Gets the top y-coordinate of the border.
     *
     * @return The top y-coordinate
     */
    public float getY2() {
        return y2;
    }

    /**
     * Gets the name of the border area.
     *
     * @return The name of the border area
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the width of the border area in world units.
     * The width is calculated by dividing the right x-coordinate by UNIT_SCALE.
     *
     * @return The width of the border area
     */
    public float getWidth() {
        return x2/UNIT_SCALE;
    }

    /**
     * Gets the height of the border area in world units.
     * The height is calculated by dividing the top y-coordinate by UNIT_SCALE.
     *
     * @return The height of the border area
     */
    public float getHeight() {
        return y2/UNIT_SCALE;
    }
}
