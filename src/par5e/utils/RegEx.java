/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package par5e.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author zeph
 */
public class RegEx {
    
    Pattern pattern;
    Matcher matcher;
    
    //Constructor
    public RegEx(String aPattern, String aString) {
        pattern = Pattern.compile(aPattern);
        matcher = pattern.matcher(aString);
        
    }
    
    //Methods
    public boolean find() {
        return matcher.find();
    }
    public boolean find(Integer start) {
        return matcher.find(start);
    }
    public String group() {
        return matcher.group();
    }
    public String group(Integer group) {
        return matcher.group(group);
    }
    public String group(String name) {
        return matcher.group(name);
    }
    public Integer groupCount() {
        return matcher.groupCount();
    }
    public Integer start() {
        return matcher.start();
    }
    public Integer start(Integer group) {
        return matcher.start(group);
    }
    public Integer end() {
        return matcher.end();
    }
    public Integer end(Integer group) {
        return matcher.end(group);
    }
    public boolean hitEnd() {
        return matcher.hitEnd();
    }
    public boolean matches() {
        return matcher.matches();
    }
    public Pattern pattern() {
        return matcher.pattern();
    }
    public Matcher region(Integer start, Integer end) {
        return matcher.region(start, end);
    }
    public Integer regionStart() {
        return matcher.regionStart();
    }
    public Integer regionEnd() {
        return matcher.regionEnd();
    }
    public String replaceAll(String replacement) {
        return matcher.replaceAll(replacement);
    }
    public String replaceFirst(String replacement) {
        return matcher.replaceFirst(replacement);
    }
    public Matcher reset() {
        return matcher.reset();
    }
    public Matcher reset(CharSequence input) {
        return matcher.reset(input);
    }

}
