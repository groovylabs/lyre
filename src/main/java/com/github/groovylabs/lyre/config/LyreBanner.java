package com.github.groovylabs.lyre.config;

import org.springframework.boot.Banner;
import org.springframework.core.env.Environment;

import java.io.PrintStream;

public class LyreBanner implements Banner {

    @Override
    public void printBanner(Environment environment, Class<?> aClass, PrintStream printStream) {

        printStream.println("\n");
        printStream.println("   __ __  _                         ____   _____  ____  _____      _     ____  ___    __  __               _       _                 _  __ __");
        printStream.println("  / // / | |    _   _  _ __  ___   |  _ \\ | ____|/ ___||_   _|    / \\   |  _ \\|_ _|  |  \\/  |  ___    ___ | | __  | |_  ___    ___  | | \\ \\\\ \\");
        printStream.println(" / // /  | |   | | | || '__|/ _ \\  | |_) ||  _|  \\___ \\  | |     / _ \\  | |_) || |   | |\\/| | / _ \\  / __|| |/ /  | __|/ _ \\  / _ \\ | |  \\ \\\\ \\");
        printStream.println(" \\ \\\\ \\  | |___| |_| || |  |  __/  |  _ < | |___  ___) | | |    / ___ \\ |  __/ | |   | |  | || (_) || (__ |   <   | |_| (_) || (_) || |  / // /");
        printStream.println("  \\_\\\\_\\ |_____|\\__, ||_|   \\___|  |_| \\_\\|_____||____/  |_|   /_/   \\_\\|_|   |___|  |_|  |_| \\___/  \\___||_|\\_\\   \\__|\\___/  \\___/ |_| /_//_/");
        printStream.println("                |___/\n");
        printStream.println("\n");
    }
}
