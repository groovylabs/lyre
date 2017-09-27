package com.github.groovylabs.lyre.test.initializations;

import com.github.groovylabs.lyre.engine.scanner.Scanner;
import com.github.groovylabs.lyre.test.tools.Resources;

public interface InitializeResource {

    Resources getResources();

    Scanner getScanner();

}
