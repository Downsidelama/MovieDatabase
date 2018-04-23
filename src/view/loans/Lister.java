package view.loans;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JOptionPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import engine.Database;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Lister {

	private JDialog frmLoans;
	private JTable table;
	private view.loans.TableModel tm;
	private view.listing.TableModel otherTableModel;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private Database db;
	private JTextField searchTextField;
	private JButton btnSearch;

	/**
	 * Launch the application.
	 */
	/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Lister window = new Lister();
					window.frmLoans.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the application.
	 */
	public Lister() {
		try {
			db = Database.getInstance();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initialize();
	}

	public Lister(view.listing.TableModel tm) {
		try {
			db = Database.getInstance();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		otherTableModel = tm;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException e) {
		} catch (ClassNotFoundException e) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		}
		tm = new view.loans.TableModel();
		frmLoans = new JDialog();
		frmLoans.setTitle("Loans");
		frmLoans.setBounds(100, 100, 900, 700);
		frmLoans.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		frmLoans.setMinimumSize(new Dimension(900, 700));

		JScrollPane scrollPane = new JScrollPane();

		btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmLoans.dispose();
			}
		});

		btnNewButton_1 = new JButton("Take back");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() != -1) {
					if (tm.getRow(table.getSelectedRow()).getDateOver() == null) {
						db.takeBack(tm.getRow(table.getSelectedRow()));
						tm.fireTableDataChanged();
						otherTableModel.fireTableDataChanged();
					} else {
						JOptionPane.showMessageDialog(null, "That one is already over.");
					}
				}
			}
		});
		
		JCheckBox chckbxOnLoan = new JCheckBox("Show all");
		chckbxOnLoan.setSelected(true);
		
		chckbxOnLoan.addActionListener((t) -> tm.setShowAll(chckbxOnLoan.isSelected()));
		
		searchTextField = new JTextField();
		searchTextField.setColumns(10);
		
		btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tm.setSearch(searchTextField.getText());
			}
		});
		GroupLayout groupLayout = new GroupLayout(frmLoans.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 660, Short.MAX_VALUE)
					.addGap(29)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(btnSearch, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(searchTextField)
						.addComponent(chckbxOnLoan, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnNewButton_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
						.addComponent(btnNewButton, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(11)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 639, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnNewButton_1)
							.addGap(41)
							.addComponent(chckbxOnLoan)
							.addGap(41)
							.addComponent(searchTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(22)
							.addComponent(btnSearch)
							.addPreferredGap(ComponentPlacement.RELATED, 423, Short.MAX_VALUE)
							.addComponent(btnNewButton)))
					.addGap(11))
		);

		table = new JTable();
		scrollPane.setViewportView(table);
		table.setFillsViewportHeight(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(tm);
		frmLoans.getContentPane().setLayout(groupLayout);
	}

	public void showWindow() {
		frmLoans.setModal(true);
		frmLoans.setVisible(true);
	}
}
