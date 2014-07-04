/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package par5e.utils;

/**
 *
 * @author Zeus
 */
public class OSValidator {
 
	public String os;
        
	public OSValidator() {
            if (System.getProperty("os.name") != null) {
                os = System.getProperty("os.name").toLowerCase();
            } else {
                os = "";
            }
        }
 	public boolean isWindows() {
            return (os.contains("windows"));
 	}
 	public boolean isMac() {
            return (os.contains("mac"));
 	}
 	public boolean isUnix() {
            return (os.contains("nix") || os.contains("nux") || os.contains("aix") );
 	}
 	public boolean isSolaris() {
            return (os.contains("solaris"));
 	}
        public String getOS() {
            return os;
        }
 
}
