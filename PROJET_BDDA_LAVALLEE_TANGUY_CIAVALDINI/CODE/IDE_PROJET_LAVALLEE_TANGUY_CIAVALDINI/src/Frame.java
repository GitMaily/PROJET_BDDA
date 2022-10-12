import java.nio.ByteBuffer;

public class Frame {

	/*
	 * Une frame = un buffer
	 * Un ensemble de frames = un "buffer pool"
	 */
	
	private ByteBuffer frame;
	
	private PageId pageId;
	private int pin_count;
	private boolean isDirty;
	private int timestamp;
	
	
	
	public int getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(int timestamp) {
		this.timestamp = timestamp;
	}

	public Frame() {
		this.pin_count = 0;
		this.isDirty = false;
		this.pageId = null;
		frame = null;
		this.timestamp = 0;
	}
	
	public Frame(PageId pageId) {
		this.pin_count = 0;
		this.isDirty=false;
		this.pageId = pageId;
		frame = ByteBuffer.allocate(DBParams.pageSize);
		this.timestamp = 0;
		//DiskManager.readPage(pageId, frame);
	}
	
	
	
	
	
	public Frame(ByteBuffer frame, PageId pageId) {
		this.frame = frame;
		this.pageId = pageId;
		
	}

	public PageId getPageId() {
		return pageId;
	}

	public void setPageId(PageId pageId) {
		this.pageId = pageId;
	}

	public ByteBuffer getFrame() {
		return frame;
	}

	public void setFrame(ByteBuffer frame) {
		this.frame = frame;
	}

	public int getPin_count() {
		return pin_count;
	}

	public void setPin_count(int pin_count) {
		this.pin_count = pin_count;
	}

	public boolean isDirty() {
		return isDirty;
	}

	public void setDirty(boolean isDirty) {
		this.isDirty = isDirty;
	}
	
	
	
}
