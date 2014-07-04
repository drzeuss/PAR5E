/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package par5e;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 *
 * @author zeph
 */
public class SimpleErrorHandler implements ErrorHandler {
    @Override
    public void warning(SAXParseException e) throws SAXException {
      //  System.out.println("Warning:[Line: " + e.getLineNumber() + " Col: " + e.getColumnNumber() + "] " + e.getMessage());
    }

    @Override
    public void error(SAXParseException e) throws SAXException {
      //  System.out.println("Error:[Line: " + e.getLineNumber() + " Col: " + e.getColumnNumber() + "] " + e.getMessage());
    }

    @Override
    public void fatalError(SAXParseException e) throws SAXException {
       // System.out.println("Fatal Error: [Line: " + e.getLineNumber() + " Col: " + e.getColumnNumber() + "] " + e.getMessage());
    }
}