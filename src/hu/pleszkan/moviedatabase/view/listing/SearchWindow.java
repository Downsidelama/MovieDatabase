package hu.pleszkan.moviedatabase.view.listing;

import java.awt.Color;

import javax.swing.JDialog;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ActionEvent;

/**
 * The movie searching window
 * @author Tamas Pleszkan
 * @version 1.0
 * @since 2018-04-14
 */
public class SearchWindow {

	private JDialog frmSearch;
	private JTextField title;
	private JTextField director;
	private JTextField mainActor;
	private JTextField release;
	private JTextField duration;
	private TableModel tm;
	private JComboBox<String> loaned;

	/**
	 * Launch the application.
	 */
	/*
	 * public static void main(String[] args) { EventQueue.invokeLater(new
	 * Runnable() { public void run() { try { SearchWindow window = new
	 * SearchWindow(null); window.frmSearch.setVisible(true); } catch (Exception e)
	 * { e.printStackTrace(); } } }); }
	 */

	/**
	 * Create the application.
	 * @param tm Tablemodel
	 */
	public SearchWindow(TableModel tm) {
		this.tm = tm;
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
		frmSearch = new JDialog();
		frmSearch.setModal(true);
		frmSearch.setResizable(false);
		frmSearch.setTitle("Search");
		frmSearch.setBounds(100, 100, 309, 396);
		frmSearch.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		title = new JTextField();
		title.setColumns(10);
		setPlaceHolder(title, "Title");

		director = new JTextField();
		director.setColumns(10);
		setPlaceHolder(director, "Director");

		mainActor = new JTextField();
		mainActor.setColumns(10);
		setPlaceHolder(mainActor, "Main actor");

		release = new JTextField();
		release.setColumns(10);
		setPlaceHolder(release, "Release");

		duration = new JTextField();
		duration.setColumns(10);
		setPlaceHolder(duration, "Duration");

		JComboBox<String> genuine = new JComboBox<String>();
		genuine.addItem("Is genuine?");
		genuine.addItem("Yes");
		genuine.addItem("No");

		JComboBox<String> media = new JComboBox<String>();
		media.addItem("Select media");
		media.addItem("DVD");
		media.addItem("VHS");
		media.addItem("Blu-Ray");
		media.addItem("CD");

		JButton searchButton = new JButton("Search");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (title.getForeground() != Color.GRAY) {
					tm.setTitle(title.getText());
				} else {
					tm.setTitle(null);
				}
				if (director.getForeground() != Color.GRAY) {
					tm.setDirector(director.getText());
				} else {
					tm.setDirector(null);
				}
				if (mainActor.getForeground() != Color.GRAY) {
					tm.setMainActor(mainActor.getText());
				} else {
					tm.setMainActor(null);
				}
				if (release.getForeground() != Color.GRAY) {
					try {
						tm.setRelease(Integer.parseInt(release.getText()));
					} catch (NumberFormatException e2) {
						JOptionPane.showMessageDialog(null, "That is not a number!");
					}
				} else {
					tm.setRelease(-1);
				}
				if (duration.getForeground() != Color.GRAY) {
					try {
						tm.setDuration(Integer.parseInt(duration.getText()));
					} catch (NumberFormatException e1) {
						JOptionPane.showMessageDialog(null, "That is not a number!");
					}
				} else {
					tm.setDuration(-1);
				}
				if (genuine.getSelectedIndex() != 0) {
					tm.setFilterGenuine(true);
					if (genuine.getSelectedIndex() == 1) {
						tm.setGenuine(true);
					}
					if (genuine.getSelectedIndex() == 2) {
						tm.setGenuine(false);
					}
				} else {
					tm.setFilterGenuine(false);
				}
				if (media.getSelectedIndex() != 0) {
					tm.setMedia(media.getSelectedItem().toString());
				} else {
					tm.setMedia(null);
				}
				if (loaned.getSelectedIndex() != 0) {
					tm.setFilterRented(true);
					if (loaned.getSelectedIndex() == 1) {
						tm.setLoaned(true);
					}
					if (loaned.getSelectedIndex() == 2) {
						tm.setLoaned(false);
					}
				} else {
					tm.setFilterRented(false);
				}
				tm.fireTableDataChanged();
				frmSearch.dispose();
			}
		});

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmSearch.dispose();
			}
		});

		loaned = new JComboBox<String>();
		loaned.addItem("Loaned?");
		loaned.addItem("Yes");
		loaned.addItem("No");

		GroupLayout groupLayout = new GroupLayout(frmSearch.getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addContainerGap()
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
						.createSequentialGroup()
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
								.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(duration, Alignment.LEADING).addComponent(release, Alignment.LEADING)
								.addComponent(mainActor, Alignment.LEADING).addComponent(director, Alignment.LEADING)
								.addComponent(title, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE))
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
										.addComponent(media, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(genuine, 0, 281, Short.MAX_VALUE)
										.addComponent(loaned, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
								.addComponent(searchButton, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(cancelButton, GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
								.addGap(12)))));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addContainerGap()
				.addComponent(title, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addGap(18)
				.addComponent(director, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
						GroupLayout.PREFERRED_SIZE)
				.addGap(18)
				.addComponent(mainActor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
						GroupLayout.PREFERRED_SIZE)
				.addGap(18)
				.addComponent(release, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addGap(18)
				.addComponent(duration, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
						GroupLayout.PREFERRED_SIZE)
				.addGap(18)
				.addComponent(genuine, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addGap(18)
				.addComponent(media, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addGap(18)
				.addComponent(loaned, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED, 36, Short.MAX_VALUE).addGroup(groupLayout
						.createParallelGroup(Alignment.BASELINE).addComponent(searchButton).addComponent(cancelButton))
				.addContainerGap()));
		frmSearch.getContentPane().setLayout(groupLayout);
	}

	private void setPlaceHolder(JTextField tf, String ph) {
		tf.setText(ph);
		tf.setForeground(Color.GRAY);
		tf.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (tf.getText().equals(ph)) {
					tf.setText("");
					tf.setForeground(Color.BLACK);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (tf.getText().isEmpty()) {
					tf.setForeground(Color.GRAY);
					tf.setText(ph);
				}
			}
		});
	}

	public void showWindow() {
		frmSearch.setVisible(true);
	}

}
