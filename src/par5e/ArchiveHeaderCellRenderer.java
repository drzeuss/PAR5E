/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package par5e;

import java.awt.Component;
import javax.swing.JPanel;
import javax.swing.JTable;
import static javax.swing.SwingConstants.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author zeph
 */
public class ArchiveHeaderCellRenderer extends JPanel implements TableCellRenderer {
    private final DefaultTableCellRenderer renderer;

    public ArchiveHeaderCellRenderer(JTable table) {
        renderer = (DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer();      
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
        renderer.setHorizontalAlignment(LEFT);
        return renderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
    }
}
