package hu.pleszkan.moviedatabase.view.listing;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileFilter;

import org.apache.commons.io.FilenameUtils;

import hu.pleszkan.moviedatabase.engine.Database;
import hu.pleszkan.moviedatabase.engine.entity.Row;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class AddEdit {

	private JDialog frmAddedit;
	private boolean add = true;
	private JTextField title;
	private JTextField director;
	private JTextField mainActors;
	private JTextField imagePathTextField;
	private static Database db;
	private TableModel tm;
	private Row row;

	/**
	 * Create the application.
	 */
	public AddEdit(boolean add, TableModel tm) {
		this.add = add;
		this.tm = tm;
		try {
			db = Database.getInstance();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		initialize();
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	public AddEdit(boolean add, TableModel tm, Row row) {
		this.add = add;
		this.tm = tm;
		this.row = row;
		try {
			db = Database.getInstance();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		initialize();
	}

	public void showWindow() {
		frmAddedit.setModal(true);
		frmAddedit.setVisible(true);
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
		frmAddedit = new JDialog();
		if (add) {
			frmAddedit.setTitle("Add movie");
		} else {
			frmAddedit.setTitle("Edit a movie");
		}
		frmAddedit.setBounds(100, 100, 450, 700);
		frmAddedit.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmAddedit.setMinimumSize(new Dimension(450, 700));

		title = new JTextField();
		title.setColumns(10);

		director = new JTextField();
		director.setColumns(10);

		JLabel lblDirector = new JLabel("Director");

		JLabel lblNewLabel = new JLabel("Main Actors");

		mainActors = new JTextField();
		mainActors.setColumns(10);

		JLabel lblReleaseDate = new JLabel("Release Date");

		JSpinner releaseDate = new JSpinner();
		releaseDate.setModel(new SpinnerNumberModel(2018, 1800, 2018, 1));

		JLabel lblDuration = new JLabel("Duration");

		JSpinner durationSpinner = new JSpinner();
		durationSpinner.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));

		JLabel lblMedia = new JLabel("Media");

		JComboBox<String> mediaComboBox = new JComboBox<String>();
		mediaComboBox.addItem("DVD");
		mediaComboBox.addItem("VHS");
		mediaComboBox.addItem("Blu-Ray");
		mediaComboBox.addItem("CD");

		JLabel lblCoverImageLocation = new JLabel("Cover image location");

		imagePathTextField = new JTextField();
		imagePathTextField.setEnabled(false);
		imagePathTextField.setColumns(10);
		JButton browseButton = new JButton("Browse");
		browseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.addChoosableFileFilter(new FileFilter() {

					@Override
					public boolean accept(File f) {
						if (f.isDirectory()) {
							return true;
						}

						String extension = FilenameUtils.getExtension(f.getName());
						if (extension != null) {
							if (extension.equalsIgnoreCase("jpeg") || extension.equalsIgnoreCase("png")
									|| extension.equalsIgnoreCase("jpg")) {
								return true;
							}
						} else {
							return false;
						}

						return false;
					}

					@Override
					public String getDescription() {
						return "Images";
					}
				});
				fc.setAcceptAllFileFilterUsed(false);
				int returnVal = fc.showOpenDialog(frmAddedit);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					imagePathTextField.setText(fc.getSelectedFile().getAbsolutePath());
				}
			}
		});

		JComboBox<String> genuineComboBox = new JComboBox<String>();
		genuineComboBox.addItem("Yes");
		genuineComboBox.addItem("No");

		JLabel lblGenuine = new JLabel("Genuine");

		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (testInput(title) & testInput(director) & testInput(mainActors) & testInput(imagePathTextField)) {
					File img = new File(imagePathTextField.getText());
					if (img.exists() && img.canRead()) {
						try {
							long millis = System.currentTimeMillis();
							String filename = null;
							if (row == null || !imagePathTextField.getText().equals("img/" + row.getPicture())) {
								copyFileUsingStream(img, new File("img/" + Long.toString(millis) + "."
										+ FilenameUtils.getExtension(img.getName())));
								filename = millis + "." + FilenameUtils.getExtension(img.getName());
							} else {
								filename = row.getPicture();
							}
							if (add) {
								Row insert = new Row(title.getText(), director.getText(), mainActors.getText(),
										Integer.parseInt(releaseDate.getValue().toString()),
										Integer.parseInt(durationSpinner.getValue().toString()),
										mediaComboBox.getSelectedItem().toString(),
										genuineComboBox.getSelectedItem().toString().equals("Yes") ? true : false,
										false, 0, filename);
								db.insertIntoMovie(insert);
							} else {
								Row insert = new Row(row.getId(), title.getText(), director.getText(),
										mainActors.getText(), Integer.parseInt(releaseDate.getValue().toString()),
										Integer.parseInt(durationSpinner.getValue().toString()),
										mediaComboBox.getSelectedItem().toString(),
										genuineComboBox.getSelectedItem().toString().equals("Yes") ? true : false,
										false, 0, filename);
								db.updateMovieRow(insert);
							}
							tm.fireTableDataChanged();
							frmAddedit.dispose();
						} catch (IOException e) {
							e.printStackTrace();
						} catch (SQLException e) {
							JOptionPane.showMessageDialog(null, e.getMessage(), "SQL Error!", JOptionPane.OK_OPTION);
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						}
					} else {
						JOptionPane.showMessageDialog(null, "Selected file doesn't exists or couldn't be read.");
					}
				}
			}
		});

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmAddedit.dispose();
			}
		});

		JLabel lblTitle = new JLabel("Title");

		// If in edit mode:
		if (!add) {
			title.setText(row.getTitle());
			mainActors.setText(row.getMainactor());
			int mediaID = 0;
			if (row.getMedia().equals("DVD")) {
				mediaID = 0;
			} else if (row.getMedia().equals("VHS")) {
				mediaID = 1;
			} else if (row.getMedia().equals("Blu-Ray")) {
				mediaID = 2;
			} else {
				mediaID = 3;
			}
			mediaComboBox.setSelectedIndex(mediaID);
			durationSpinner.setValue(row.getLength());
			director.setText(row.getProducer());
			releaseDate.setValue(row.getRelease());
			imagePathTextField.setText("img/" + row.getPicture());
			if (row.isGenuinity()) {
				genuineComboBox.setSelectedIndex(0);
			} else {
				genuineComboBox.setSelectedIndex(1);
			}
		}
		GroupLayout groupLayout = new GroupLayout(frmAddedit.getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addContainerGap()
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(releaseDate, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
						.addComponent(title, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
						.addComponent(director, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
						.addComponent(mainActors, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
						.addComponent(durationSpinner, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 414,
								Short.MAX_VALUE)
						.addComponent(mediaComboBox, Alignment.TRAILING, 0, 414, Short.MAX_VALUE)
						.addComponent(genuineComboBox, Alignment.TRAILING, 0, 414, Short.MAX_VALUE)
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
								.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED, 134, Short.MAX_VALUE)
								.addComponent(btnCancel, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE))
						.addGroup(Alignment.TRAILING,
								groupLayout.createSequentialGroup()
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addGroup(groupLayout.createSequentialGroup()
														.addComponent(imagePathTextField, GroupLayout.DEFAULT_SIZE, 289,
																Short.MAX_VALUE)
														.addGap(18))
												.addGroup(groupLayout.createSequentialGroup()
														.addComponent(lblGenuine, GroupLayout.PREFERRED_SIZE, 100,
																GroupLayout.PREFERRED_SIZE)
														.addGap(207)))
										.addComponent(browseButton, GroupLayout.PREFERRED_SIZE, 107,
												GroupLayout.PREFERRED_SIZE))
						.addComponent(lblCoverImageLocation).addComponent(lblMedia).addComponent(lblDuration)
						.addComponent(lblReleaseDate).addComponent(lblNewLabel).addComponent(lblDirector)
						.addComponent(lblTitle, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
				.addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addGap(18).addComponent(lblTitle).addGap(18)
				.addComponent(title, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addGap(18).addComponent(lblDirector).addGap(18)
				.addComponent(director, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
						GroupLayout.PREFERRED_SIZE)
				.addGap(18).addComponent(lblNewLabel).addGap(18)
				.addComponent(mainActors, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
						GroupLayout.PREFERRED_SIZE)
				.addGap(18).addComponent(lblReleaseDate).addGap(18)
				.addComponent(releaseDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
						GroupLayout.PREFERRED_SIZE)
				.addGap(18).addComponent(lblDuration).addGap(18)
				.addComponent(durationSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
						GroupLayout.PREFERRED_SIZE)
				.addGap(18).addComponent(lblMedia).addGap(18)
				.addComponent(mediaComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
						GroupLayout.PREFERRED_SIZE)
				.addGap(18).addComponent(lblCoverImageLocation).addGap(18)
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(imagePathTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(browseButton))
				.addGap(18).addComponent(lblGenuine).addGap(18)
				.addComponent(genuineComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
						GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED, 55, Short.MAX_VALUE).addGroup(groupLayout
						.createParallelGroup(Alignment.BASELINE).addComponent(btnSave).addComponent(btnCancel))
				.addContainerGap()));
		frmAddedit.getContentPane().setLayout(groupLayout);
	}

	private static void copyFileUsingStream(File source, File dest) throws IOException {
		InputStream is = null;
		OutputStream os = null;
		try {
			is = new FileInputStream(source);
			os = new FileOutputStream(dest);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = is.read(buffer)) > 0) {
				os.write(buffer, 0, length);
			}
		} finally {
			is.close();
			os.close();
		}
	}

	private boolean testInput(JTextField t) {
		if (t.getText() != null && t.getText().length() > 0 && t.getText().length() <= 255) {
			t.setBackground(Color.WHITE);
			return true;
		} else {
			t.setBackground(Color.RED);
			return false;
		}
	}
}
