package io.harness.fme.citymap;

public class ANSI {
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String BLUE = "\u001B[34m";

    public static String colorDot(String treatment) {
        switch (treatment) {
            case "red": return RED + "●" + RESET;
            case "green": return GREEN + "●" + RESET;
            case "blue": return BLUE + "●" + RESET;
            default: return "●";
        }
    }
}

