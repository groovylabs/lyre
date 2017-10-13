/*
 * MIT License
 *
 * Copyright (c) 2017 Groovylabs
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

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
