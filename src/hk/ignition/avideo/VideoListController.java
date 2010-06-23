package hk.ignition.avideo;

import hk.ignition.avideo.VideoListModel.VideoServerTableModel;
import hk.ignition.avideo.model.AirVideoEntry;
import hk.ignition.avideo.model.Directory;
import hk.ignition.avideo.model.Video;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.logging.Logger;

import javax.swing.JFrame;

public class VideoListController {
	private static Logger logger = Logger.getLogger(VideoListController.class.getName());
	private VideoListView view;

	private VideoListModel model;
	private VideoServerTableModel tableModel;
	
	public VideoListController() {
		view = new VideoListView();		
		model = new VideoListModel();
		
		tableModel = model.getTableModel();
		
		view.btnConnect.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!model.isConnected()) {
					onConnectClicked();
				} else {
					onDisconnectClicked();
				}
			}
		});
		
		view.txtServer.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				onConnectClicked();
			}
		});
		
		view.txtPath.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				changeDirectory(view.getPath());
			}
		});

		view.setConnected(false);
		view.setTableModel(tableModel);
		view.tblResult.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					AirVideoEntry entry = (AirVideoEntry) model.getTableModel().getData()[view.tblResult.getSelectedRow()];
					if (entry instanceof Directory) {
						Directory d = (Directory) entry;
						changeDirectory(d.getLocation());

					} else if (entry instanceof Video) {
						Video v = (Video) entry;
						openLiveVideo(v);

					}
				}
			}			
		});
	}
	
	public void onConnectClicked() {
		logger.info("on connect clicked");
		view.setStatusText("Connecting ...");
		
		try {
			model.connect(view.getServer(), view.getPort(), view.getPassword());

			// update ui
			refreshDirectory();
			view.setConnected(true);

		} catch (RuntimeException re) {
			logger.warning("error connecting to server: " + re.getMessage());
			view.setConnected(false);
			view.setStatusText(String.format("Cannot connect to server. (%s)", re.getMessage()));

		}
	}
	
	public void onDisconnectClicked() {
		model.disconnect();
		tableModel.fireTableDataChanged();
		view.setPath("");
		view.setStatusText("Disconnected");
		view.setConnected(false);
	}
	
	public void changeDirectory(String path) {
		String currentPath = model.getCurrentPath();
		if (!path.equals(currentPath)) {
			view.setStatusText("Changing directory");
			try {
				model.cd(path);
				refreshDirectory();
				view.setStatusText("");
			} catch (RuntimeException re) {
				logger.warning("error: " + re.getMessage());
				view.setStatusText(re.getMessage());

			}
		}
	}
	
	private void refreshDirectory() {
		tableModel.fireTableDataChanged();
		view.setPath(model.getCurrentPath());
		view.setStatusText("");
	}

	/**
	 * Main method for panel
	 */
	public static void main(String[] args) {
		VideoListController controller = new VideoListController();
		controller.start();
	}

	public void start() {
		JFrame frame = new JFrame("UAV Client");
		frame.setSize(1024, 760);
		frame.getContentPane().add(view);
		frame.setVisible(true);

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				System.exit(0);
			}
		});
	}
	
	private void openLiveVideo(Video video) {
		openVideoMac(video.getLiveUrl());
	}
	
	private void openVideoMac(String path) {
		logger.info("open -a \"QuickTime Player\" " + path);
		try {
			Runtime.getRuntime().exec(new String[]{
				"open",
				"-a",
				"QuickTime Player",
				path
			});
		} catch (IOException e1) {
			logger.warning("error running command");
		}
	}
}
