package org.example.osadnici;

/**
 * Enum representing ANSI color codes for text formatting.
 * Each color has a corresponding ANSI code used to set the text color in a terminal.
 */
public enum Color {
    RESET("\033[0m"),      // Resets color

    BLACK("\033[0;30m"),   // Black color
    RED("\033[0;31m"),     // Red color
    GREEN("\033[0;32m"),   // Green color
    YELLOW("\033[0;33m"),  // Yellow color
    BLUE("\033[0;34m"),    // Blue color
    PURPLE("\033[0;35m"),  // Purple color
    WHITE("\033[0;37m");   // White color

    /** The ANSI code for the color. */
    private final String ansiCode;

    /**
     * Gets the ANSI code for the color.
     *
     * @return the ANSI code as a string
     */
    public String get() {
        return ansiCode;
    }

    /**
     * Constructs a Color enum with the specified ANSI code.
     *
     * @param ansiCode the ANSI code for the color
     */
    Color(String ansiCode) {
        this.ansiCode = ansiCode;
    }
}
