package com.intfinit.earthquakes.util;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import static com.google.common.base.Throwables.propagate;

public class ProjectRoot {

    private static final File PROJECT_ROOT;

    static {
        PROJECT_ROOT = findProjectRoot();
    }

    public static File projectRoot() {
        return PROJECT_ROOT;
    }

    public static File projectRootRelative(String path) {
        return PROJECT_ROOT.toPath().resolve(path).toFile();
    }

    private static File findProjectRoot() {

        URL startLocation = ProjectRoot.class.getProtectionDomain().getCodeSource().getLocation();

        try {
            File file = new File(startLocation.toURI());

            while (file != null) {
                File[] files = file.listFiles(pathname ->
                        pathname.isFile() && "settings.gradle".equals(pathname.getName())
                );
                if (files != null && files.length > 0) {
                    return file.getCanonicalFile();
                }

                file = file.getParentFile();
            }


        } catch (URISyntaxException | IOException e) {
            propagate(e);
        }

        return null;

    }

}
