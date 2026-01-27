package io.harness.fme.citymap;

import java.util.*;

public class MapRenderer {

    private int width;
    private int height;

    public MapRenderer(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void render(List<City> cities) {
        String[][] grid = new String[height][width];

        // fill empty grid
        for (int y = 0; y < height; y++) {
            Arrays.fill(grid[y], " ");
        }

        // plot cities
        for (City city : cities) {
            int x = MapUtils.lonToX(city.getLongitude(), width);
            int y = MapUtils.latToY(city.getLatitude(), height);
            if (x >= 0 && x < width && y >= 0 && y < height) {
                grid[y][x] = ANSI.colorDot(city.getTreatment());
            }
        }

        // clear terminal
        System.out.print("\033[H\033[2J");
        System.out.flush();

        // print grid
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                System.out.print(grid[y][x]);
            }
            System.out.println();
        }
    }
}

