package hk.ignition.avideo;

import hk.ignition.avideo.model.AirVideoEntry;
import hk.ignition.avideo.model.Directory;
import hk.ignition.avideo.model.Video;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.table.AbstractTableModel;

import org.jruby.RubyObject;
import org.jruby.embed.PathType;
import org.jruby.embed.ScriptingContainer;

public class VideoListModel {
	private static Logger logger = Logger.getLogger("Model");
	private ScriptingContainer container;
	
	private VideoServerTableModel tableModel;
	private AirVideoClient delegate;

	public VideoListModel() {
		container = new ScriptingContainer();
		container.runScriptlet(PathType.CLASSPATH, "airvideo");
		tableModel = new VideoServerTableModel();
	}

	public void cd(String path) {
		delegate.cd(path);
		Object[] data = delegate.ls(".");
		tableModel.setData(data);
	}

	public Object[] ls(String path) {
		Object[] result = delegate.ls(path);
		List<Object> results = new ArrayList<Object>();
		for (Object o : result) {
			RubyObject ro = (RubyObject) o;
			if (ro.getType().getName().equals("AirVideo::Client::VideoObject")) {
				results.add(container.getInstance(ro, Video.class));
			} else if (ro.getType().getName().equals("AirVideo::Client::FolderObject")) {
				results.add(container.getInstance(ro, Directory.class));
			} else {
				logger.warning("unknow type: " + ro.getType().getName());
			}
		}
		
		Object[] data = results.toArray();
		tableModel.setData(data);
		return data;
	}

	public void connect(String server, int port, String password) {
	    try {
    		Object receiver = container.runScriptlet("require 'hk/ignition/avideo/air_video_wrapper'; AirVideoWrapper.new");
    		delegate = container.getInstance(receiver, AirVideoClient.class);
    		delegate.connect(server, port, password);
    		delegate.setMaxHeight(640);
    		delegate.setMaxWidth(480);
    		ls(".");
		} catch (RuntimeException re) {
		    delegate = null;
		    throw re;
		}
	}
	
	public void disconnect() {
		delegate = null;
		Object[] data = new Object[0];
		tableModel.setData(data);
	}

	public String getCurrentPath() {
		return delegate.pwd();
	}
	
	public VideoServerTableModel getTableModel() {
		return tableModel;
	}
	
	public boolean isConnected() {
		return delegate != null;
	}

	public class VideoServerTableModel extends AbstractTableModel {
		private static final long serialVersionUID = -4487636479561428796L;
		private Object[] data = new Object[0];
		
		@Override
		public int getColumnCount() {
			return 2;
		}

		@Override
		public int getRowCount() {
			return data.length;
		}
		
		public void setData(Object[] data) {
			this.data = data;
		}
		
		public Object[] getData() {
			return data;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			Object obj = data[rowIndex];
			switch(columnIndex) {
			case 0:
				if (Directory.class.isAssignableFrom(obj.getClass())) {
					return "<Directory>";
				} else if (Video.class.isAssignableFrom(obj.getClass())) {
					return "<Video>";
				} else {
					logger.warning("unknown class = " + obj.getClass());
					return null;
				}
			case 1:
				if (AirVideoEntry.class.isAssignableFrom(obj.getClass())) {
					return ((AirVideoEntry) obj).getName();
				} else {
					logger.warning("unknown class = " + obj.getClass());
					return null;
				}
			}
			return null;
		}		
	}
}
