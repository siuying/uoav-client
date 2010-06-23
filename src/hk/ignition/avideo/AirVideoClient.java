package hk.ignition.avideo;



public interface AirVideoClient {
	public void connect(String server, int port, String password);
	
	public String pwd();
	
	public Object[] ls(String path);

	public void cd(String path);
	
	public int getMaxWidth();

	public void setMaxWidth(int max);
	
	public int getMaxHeight();
	
	public void setMaxHeight(int max);

}
