package io.harness.fme.citymap;

public class MapUtils {

    public static int lonToX(double lon, int width) {
        return (int) ((lon + 125) / (60.0) * (width - 1)); // -125..-65 -> 0..width-1
    }

    public static int latToY(double lat, int height) {
        return (int) ((50 - lat) / 25.0 * (height - 1)); // 50..25 -> 0..height-1
    }
}

