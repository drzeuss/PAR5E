/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package par5e;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author zeph
 */
public class NonEditCellRenderer extends JPanel implements TableCellRenderer {

        private static final long serialVersionUID = 1L;
        private final JTextField label;
    
        public NonEditCellRenderer() {
            super(new FlowLayout(FlowLayout.LEFT, 2, 0));
            setOpaque(true);
            label = new JTextField();
            label.setFont(new Font("Lucida Grande", 0, 11));
            label.setBackground(new Color(227,228,230));
            label.setForeground(new Color(127,129,130));
            label.setBorder(null);
            label.setOpaque(true);
            label.setHorizontalAlignment(JLabel.LEFT);
            add(label);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
            
            String s = table.getModel().getValueAt(row, col).toString();
            label.setText(s);
            
            if (col == 0) {
               setBackground(new Color(227,228,230));
            }
            
            if (isSelected) {
                setBackground(table.getSelectionBackground());
                setForeground(table.getSelectionForeground());
                
                if (col == 0) {
                    label.setBackground(table.getSelectionBackground());
                    label.setForeground(table.getSelectionForeground());
                    label.setFont(new Font("Lucinda Grande", Font.BOLD, 11));
                } 
            } else {
                setBackground(table.getBackground());
                setForeground(table.getForeground());
                if (col == 0) {
                    label.setBackground(new Color(227,228,230));
                    label.setForeground(new Color(154,155,156));
                    label.setFont(new Font("Lucida Grande", 0, 11));
            
                    setBackground(new Color(227,228,230));
                }
            }    
            
            return this;
        }
}