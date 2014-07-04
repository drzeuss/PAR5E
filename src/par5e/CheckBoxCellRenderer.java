/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package par5e;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.util.BitSet;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author zeph
 */
public class CheckBoxCellRenderer extends JPanel implements TableCellRenderer {

        private final JCheckBox checkBox;

        public CheckBoxCellRenderer() {
            super(new FlowLayout(FlowLayout.CENTER, -2, -4));
            setOpaque(true);
            checkBox = new JCheckBox();
            checkBox.setOpaque(false);
            checkBox.setHorizontalAlignment(JCheckBox.CENTER);
            add(checkBox);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            
            if (column > 1 && column < 5) {
                BitSet bs = (BitSet)table.getModel().getValueAt(row, 5);
                int bsindex = column - 2;
                
                if (! bs.get(bsindex)) {
                    checkBox.setVisible(false);
                    checkBox.setSelected(value instanceof Boolean && (Boolean) value);
                } else {
                    checkBox.setVisible(true);
                    checkBox.setSelected(value instanceof Boolean && (Boolean) value);
                }
                
                if (isSelected) {
                    setBackground(table.getSelectionBackground());
                    setForeground(table.getSelectionForeground());
                } else {
                    setBackground(table.getBackground());
                    setForeground(table.getForeground());
                }
            
                
                
            }
            return this;
        }
}

