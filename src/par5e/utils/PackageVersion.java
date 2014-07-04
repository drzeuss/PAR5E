/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package par5e.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zeph
 */
public class PackageVersion {
    
    public String impl_version;
    public String build_date;
    
    private Manifest manifest;
    private Attributes attributes;
    
    public void PackageVersion() {
        try {        
            InputStream stream = getClass().getResourceAsStream("/META-INF/MANIFEST.MF");

            if (stream == null)
            {
                System.out.println("Couldn't find manifest.");
                System.exit(0);
            }

            manifest = new Manifest(stream);
            attributes = manifest.getMainAttributes();    
            
            impl_version = attributes.getValue("Implementation-Version");
            build_date = attributes.getValue("Bundle-Date");
            
        } catch (IOException ex) {
            Logger.getLogger(PackageVersion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getVersion() {
        return impl_version;
    }
    
    public String getBuildDate() {
        return build_date;
    }
    
}
