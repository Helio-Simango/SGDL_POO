package view.extras;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Grácio Macuácua
 */
public class Table extends JTable {

    public Table() {
        setShowHorizontalLines(true);
        setRowHeight(30);
        setGridColor(Color.WHITE);
        getTableHeader().setReorderingAllowed(false);
        getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer(){
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                TableHeader header = new TableHeader(value + "");
                if(column > 7)
                    header.setHorizontalAlignment(JLabel.CENTER);
                return header;
            }
            
        });
        
        
        setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                if(column <= 7){
                    Component com = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    com.setBackground(Color.WHITE);
                    setBorder(noFocusBorder);
                    if(isSelected)
                        com.setForeground(new Color(13, 113, 182));
                    else
                        com.setForeground(new Color(102, 102, 102));
                   
                    return com;
                }
                    return new JLabel("Testando");
            }
            
        });
        
    }
    
}
