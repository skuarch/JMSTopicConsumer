package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Utilities for Properties.
 * @author skuarch
 */
public class PropertieUtilities {

    //==========================================================================
    /**
     * this class is an utilitie doesn't need a constructor
     */
    private PropertieUtilities() {
    } // end PropertieUtilities

    //==========================================================================
    /**
     * you need to specifie a path in order to load a file with FileinputStream.
     *
     * @param path String
     * @return Properties
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static Properties getProperties(String path) throws FileNotFoundException, IOException {

        if (path == null || path.length() < 1) {
            throw new NullPointerException("path is null");
        }

        FileInputStream fis = null;
        Properties properties = null;

        try {

            fis = new FileInputStream(path);
            properties = new Properties();
            properties.load(fis);

        } catch (FileNotFoundException fnfe) {
            throw fnfe;
        } catch (IOException ioe) {
            throw ioe;
        } finally {
            IOUtilities.closeInputStream(fis);            
            fis = null;
        }

        return properties;

    } // end getProperties
    
} // end class
