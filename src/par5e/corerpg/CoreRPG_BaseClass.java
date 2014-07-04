/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package par5e.corerpg;

/**
 *
 * @author zeph
 */
public class CoreRPG_BaseClass extends Object {
    //Variables
    public String name;
    public String text;
    public String source;
    
    private static final long serialVersionUID = 0L;
    
    //Constructors
    public CoreRPG_BaseClass() {
        name = "";
        text = "";
        source = "";
    }
    public CoreRPG_BaseClass(String sNew) {
        name = sNew;
        text = "";
        source = "";
    }
    //Methods
    public void setName(String sNew) {
        name = sNew;
    }
    public void setText(String sNew) {
        text = sNew;
    }
    public void setSource(String aNew) {
        source = aNew;
    }
    
    public String getName() {
        return name;
    }
    public String getText() {
        return text;
    }
    public String getSource() {
        return source;
    }
    
    public boolean isComplete() {
        boolean complete = true;
        if (name.equals("") || text.equals("")) {
            complete = false;
        }
        return complete;
    }
}

