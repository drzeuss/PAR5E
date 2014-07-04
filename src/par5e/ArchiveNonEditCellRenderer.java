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
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author zeph
 */
public class ArchiveNonEditCellRenderer extends JPanel implements TableCellRenderer {

        private static final long serialVersionUID = 1L;
        private final JLabel label;
    
        public ArchiveNonEditCellRenderer() {
            super(new FlowLayout(FlowLayout.LEFT, 2, 0));
            setOpaque(true);
            label = new JLabel();
            label.setFont(new Font("Lucida Grande", 0, 11));
            label.setForeground(Color.BLACK);
            label.setBorder(null);
            label.setOpaque(true);
            add(label);
            
            
            setBackground(Color.WHITE);
            setForeground(Color.BLACK);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
            
            String s = table.getModel().getValueAt(row, col).toString();
            label.setText(s);
            
            if (col >= 2) {
                FlowLayout fout = new FlowLayout(FlowLayout.RIGHT, 2, 0);
                setLayout(fout);
            }
            if (isSelected) {
                setBackground(table.getSelectionBackground());
                setForeground(table.getSelectionForeground());

                label.setBackground(table.getSelectionBackground());
                label.setForeground(table.getSelectionForeground());
                label.setFont(new Font("Lucinda Grande", Font.BOLD, 11));
            } else {
                setBackground(table.getBackground());
                setForeground(table.getForeground());
                label.setBackground(table.getBackground());
                label.setForeground(table.getForeground());
                label.setFont(new Font("Lucida Grande", 0, 11));
            }    
            
            return this;
        }
}