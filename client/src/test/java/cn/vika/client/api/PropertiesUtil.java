/*
 * Copyright (C) 2021 vikadata
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package cn.vika.client.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * used for loading properties from test resources folder
 * @author Shawn Deng
 * @date 2021-02-18 14:28:28
 */
public class PropertiesUtil {

    private static final Properties testProperties = new Properties();

    static {

        boolean isPropertiesLoaded = false;

        // Get the maven basedir, we use it to locate the default properties for the unit tests
        String basedir = (String) System.getProperties().get("basedir");

        System.out.format("baseDir: %s\n", basedir);

        // Loading Properties from "src/test/resources" named test.properties
        File propertiesFile = new File(basedir, "src/test/resources/test.properties");
        if (propertiesFile.exists()) {
            try (InputStreamReader input = new InputStreamReader(new FileInputStream(propertiesFile))) {
                testProperties.load(input);
                System.out.format("Loaded test properties from: %n%s%n", propertiesFile.getAbsolutePath());
                isPropertiesLoaded = true;
            }
            catch (IOException ioe) {
                System.err.println("Error loading base test properties, error=" + ioe.getMessage());
            }
        }

        // Loading Properties from the user's home directory
        propertiesFile = new File((String) System.getProperties().get("user.home"), "test.properties");
        if (propertiesFile.exists()) {
            try (InputStreamReader input = new InputStreamReader(new FileInputStream(propertiesFile))) {
                testProperties.load(input);
                System.out.format("Loaded overriding test properties from: %n%s%n", propertiesFile.getAbsolutePath());
                isPropertiesLoaded = true;
            }
            catch (IOException ioe) {
                System.err.println("Error loading overriding test properties, error=" + ioe.getMessage());
            }
        }

        if (!isPropertiesLoaded) {
            System.err.println("No test properties have been loaded! please check whether test.properties exist");
        }
    }

    public static String getProperty(String key) {
        return testProperties.getProperty(key);
    }
}
