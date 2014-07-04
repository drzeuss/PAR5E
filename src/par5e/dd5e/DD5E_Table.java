/*
 * 
 * PAR5E Fantasy Grounds Ruleset Module Parser Tool
 * Developed by Zeus. Copyright 2014
 * 
 */

package par5e.dd5e;

import java.util.Arrays;

/**
 *
 * @author zeph
 */
public class DD5E_Table extends DD5E_BaseClass {
    
    public String category;
    public String description;
    public String[] columnlabels;
    public DD5E_TableRow[] tabledata;
    
    public DD5E_Table(String aNew) {
        name = aNew;
        text = "";
        description = "";
        category = "";
        
        columnlabels = new String[0];
        tabledata = new DD5E_TableRow[0];
    }
    
    public void setCategory(String aNew) {
        category = aNew;
    }
    public void setDescription(String aNew) {
        description = aNew;
    }
    public void addColumn(String colheader) {
        columnlabels = Arrays.copyOf(columnlabels, columnlabels.length+1);
        columnlabels[columnlabels.length-1] = colheader;
    }
    public void addDataRow(DD5E_TableRow row) {
        
        tabledata = Arrays.copyOf(tabledata, tabledata.length+1);
        tabledata[tabledata.length-1] = row;
    }
    
    public String getCategory() {
        return description;
    }
    public String getDescription() {
        return category;
    }
    public String[] getColumnHeaders() {
        return columnlabels;
    }
    public Integer getResultColumns() {
        return columnlabels.length;
    }
    public DD5E_TableRow[] getTableRows() {
        return tabledata;
    }
    
    @Override
    public boolean isComplete() {
        boolean complete = true;
        if (category.equals("") || description.equals("") || columnlabels.length == 0 || tabledata.length == 0 ) {
            complete = true;
        }
        return complete;
    }
}
