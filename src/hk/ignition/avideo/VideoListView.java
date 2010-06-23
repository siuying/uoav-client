package hk.ignition.avideo;

import javax.swing.table.TableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class VideoListView extends VideoListPanel {
	private static final long serialVersionUID = 4364286918008090043L;
	
	public VideoListView() {
		txtPort.setDocument(new NumericDocument());
		txtPort.setText("45678");
		setStatusText(" ");
	}
	
	public void setStatusText(String text) {
		this.lblStatus.setText(text);
	}
	
	public void setConnected(boolean connected) {
		if (connected) {
			this.txtPassword.setEditable(false);
			this.txtPort.setEditable(false);
			this.txtServer.setEditable(false);
			
			this.txtPath.setEnabled(true);
			this.txtPath.setEditable(true);
			this.btnConnect.setText("Disconnect");
			this.btnConnect.setSelected(true);
		} else {
			this.txtPassword.setEditable(true);
			this.txtPort.setEditable(true);
			this.txtServer.setEditable(true);
			
			this.txtPath.setEnabled(false);
			this.txtPath.setEditable(false);
			this.btnConnect.setSelected(false);
			this.btnConnect.setText("Connect");
		}
	}
	
	public void setPath(String path) {
		this.txtPath.setText(path);
	}
	
	public String getPath() {
		return this.txtPath.getText();
	}
	
	public String getServer() {
		return this.txtServer.getText();
	}
	
	public int getPort() {
		return Integer.parseInt(this.txtPort.getText());
	}
	
	public String getPassword() {
		return new String(this.txtPassword.getPassword());
	}
	
	public void setTableModel(TableModel model) {
		this.tblResult.setModel(model);
	}
	
	class NumericDocument extends PlainDocument {
		private static final long serialVersionUID = -7301619665760293380L;

		@Override
		public void insertString(int offs, String str, AttributeSet a)
				throws BadLocationException {
			try {
				Long.parseLong(str);
				super.insertString(offs, str, a);
			} catch (RuntimeException re) {
			}				
		}
		
	}
}
