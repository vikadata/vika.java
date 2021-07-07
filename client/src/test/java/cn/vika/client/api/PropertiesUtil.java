/*
 * Copyright (c) 2021 vikadata, https://vika.cn <support@vikadata.com>
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
