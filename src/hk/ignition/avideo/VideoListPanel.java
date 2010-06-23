package hk.ignition.avideo;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;

public class VideoListPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6789168022749388469L;
	JTextField txtServer = new JTextField();
	JTextField txtPath = new JTextField();
	JTable tblResult = new JTable();
	JLabel lblStatus = new JLabel();
	JTextField txtPort = new JTextField();
	JPasswordField txtPassword = new JPasswordField();
	JToggleButton btnConnect = new JToggleButton();

	/**
	 * Default constructor
	 */
	public VideoListPanel() {
		initializePanel();
	}

	/**
	 * Main method for panel
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setSize(600, 400);
		frame.setLocation(100, 100);
		frame.getContentPane().add(new VideoListPanel());
		frame.setVisible(true);

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				System.exit(0);
			}
		});
	}

	/**
	 * Adds fill components to empty cells in the first row and first column of
	 * the grid. This ensures that the grid spacing will be the same as shown in
	 * the designer.
	 * 
	 * @param cols
	 *            an array of column indices in the first row where fill
	 *            components should be added.
	 * @param rows
	 *            an array of row indices in the first column where fill
	 *            components should be added.
	 */
	void addFillComponents(Container panel, int[] cols, int[] rows) {
		Dimension filler = new Dimension(10, 10);

		boolean filled_cell_11 = false;
		CellConstraints cc = new CellConstraints();
		if (cols.length > 0 && rows.length > 0) {
			if (cols[0] == 1 && rows[0] == 1) {
				/** add a rigid area */
				panel.add(Box.createRigidArea(filler), cc.xy(1, 1));
				filled_cell_11 = true;
			}
		}

		for (int index = 0; index < cols.length; index++) {
			if (cols[index] == 1 && filled_cell_11) {
				continue;
			}
			panel.add(Box.createRigidArea(filler), cc.xy(cols[index], 1));
		}

		for (int index = 0; index < rows.length; index++) {
			if (rows[index] == 1 && filled_cell_11) {
				continue;
			}
			panel.add(Box.createRigidArea(filler), cc.xy(1, rows[index]));
		}

	}

	/**
	 * Helper method to load an image file from the CLASSPATH
	 * 
	 * @param imageName
	 *            the package and name of the file to load relative to the
	 *            CLASSPATH
	 * @return an ImageIcon instance with the specified image file
	 * @throws IllegalArgumentException
	 *             if the image resource cannot be loaded.
	 */
	public ImageIcon loadImage(String imageName) {
		try {
			ClassLoader classloader = getClass().getClassLoader();
			java.net.URL url = classloader.getResource(imageName);
			if (url != null) {
				ImageIcon icon = new ImageIcon(url);
				return icon;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		throw new IllegalArgumentException("Unable to load image: " + imageName);
	}

	public JPanel createPanel() {
		JPanel jpanel1 = new JPanel();
		EmptyBorder emptyborder1 = new EmptyBorder(8, 8, 8, 8);
		jpanel1.setBorder(emptyborder1);
		FormLayout formlayout1 = new FormLayout(
				"FILL:76PX:NONE,FILL:4DLU:NONE,FILL:62PX:NONE,FILL:4DLU:NONE,FILL:DEFAULT:NONE,FILL:4DLU:NONE,FILL:108PX:NONE,FILL:DEFAULT:GROW(1.0),FILL:4DLU:NONE,FILL:DEFAULT:NONE,FILL:4DLU:NONE,FILL:DEFAULT:NONE",
				"CENTER:DEFAULT:NONE,CENTER:2DLU:NONE,CENTER:DEFAULT:NONE,CENTER:DEFAULT:NONE,CENTER:DEFAULT:NONE,CENTER:12DLU:NONE,FILL:DEFAULT:GROW(1.0),CENTER:2DLU:NONE,FILL:PREF:NONE");
		CellConstraints cc = new CellConstraints();
		jpanel1.setLayout(formlayout1);

		JLabel jlabel1 = new JLabel();
		jlabel1.setText("Server");
		jpanel1.add(jlabel1, cc.xy(1, 1));

		txtServer.setName("txtServer");
		jpanel1.add(txtServer, cc.xywh(3, 1, 8, 1));

		JLabel jlabel2 = new JLabel();
		jlabel2.setText("Path");
		jpanel1.add(jlabel2, cc.xy(1, 5));

		txtPath.setName("txtPath");
		txtPath.setSelectionEnd(1);
		txtPath.setSelectionStart(1);
		txtPath.setText("/");
		jpanel1.add(txtPath, cc.xywh(3, 5, 8, 1));

		tblResult.setName("tblResult");
		JScrollPane jscrollpane1 = new JScrollPane();
		jscrollpane1.setViewportView(tblResult);
		jscrollpane1
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		jscrollpane1
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jpanel1.add(jscrollpane1, cc.xywh(1, 7, 12, 1));

		lblStatus.setName("lblStatus");
		lblStatus.setText("Status");
		jpanel1.add(lblStatus, cc.xywh(1, 9, 12, 1));

		JLabel jlabel3 = new JLabel();
		jlabel3.setText("Port");
		jpanel1.add(jlabel3, cc.xy(1, 3));

		txtPort.setName("txtPort");
		txtPort.setSelectionEnd(5);
		txtPort.setSelectionStart(5);
		txtPort.setText("45678");
		jpanel1.add(txtPort, cc.xy(3, 3));

		JLabel jlabel4 = new JLabel();
		jlabel4.setText("Password");
		jpanel1.add(jlabel4, cc.xy(5, 3));

		txtPassword.setName("txtButton");
		jpanel1.add(txtPassword, cc.xy(7, 3));

		btnConnect.setActionCommand("Connect");
		btnConnect.setName("btnConnect");
		btnConnect.setText("Connect");
		jpanel1.add(btnConnect, cc.xy(10, 3));

		addFillComponents(jpanel1,
				new int[] { 2, 4, 5, 6, 7, 8, 9, 10, 11, 12 }, new int[] { 2,
						4, 6, 8 });
		return jpanel1;
	}

	/**
	 * Initializer
	 */
	protected void initializePanel() {
		setLayout(new BorderLayout());
		add(createPanel(), BorderLayout.CENTER);
	}

}
