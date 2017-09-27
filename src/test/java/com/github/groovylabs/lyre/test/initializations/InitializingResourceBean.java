package com.github.groovylabs.lyre.test.initializations;

import org.junit.Before;

public abstract class InitializingResourceBean implements InitializeResource {

    @Before
    public void initializeResource() {
        //changing default test path to temp directory
        getScanner().getLyreProperties().setScanPath(getResources().getDirectory(0).getAbsolutePath());
    }

}
