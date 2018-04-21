package view.listing;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import engine.Database;
import engine.entity.Row;

/**
 * <h1>Custom TableModel</h1>
 * @author Tamas Pleszkan
 * @version 1.0
 * @since 2018-04-14
 */
public class TableModel extends AbstractTableModel {
	private static final long serialVersionUID = 9047572394630079603L;
	private String[] columnNames = {"Title", "Director", "Main Actors", "Release year", "Duration", "Media", "Genuine", "Rented", "Times Rented"};
	Database db = Database.getInstance();
	private ArrayList<Row> data = db.getAllMovies();

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		return data.get(arg0).column(arg1+1);
	}
	
	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return columnNames[column];
	}
	
	@Override
	public void fireTableDataChanged() {
		// TODO Auto-generated method stub
		data = db.getAllMovies();
		super.fireTableDataChanged();
	}
	
	public Row getRow(int rowId) {
		return data.get(rowId);
	}
	
}
