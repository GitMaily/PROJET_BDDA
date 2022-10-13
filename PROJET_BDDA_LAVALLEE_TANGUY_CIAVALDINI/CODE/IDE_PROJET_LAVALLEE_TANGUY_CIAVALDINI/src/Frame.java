import java.nio.ByteBuffer;

public class Frame {

	/*
	 * Une frame = un buffer
	 * Un ensemble de frames = un "buffer pool"
	 */
	
	private ByteBuffer buffer;
	
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
		this.timestamp = 0;
		buffer = ByteBuffer.allocate(DBParams.pageSize);

	}
	
	public Frame(PageId pageId) {
		this.pin_count = 0;
		this.isDirty=false;
		this.pageId = pageId;
		//DiskManager.readPage(pageId, buffer);
		//buffer = ByteBuffer.allocate(DBParams.pageSize);
		this.timestamp = 0;
	}
	
	
	
	
	
	public Frame(ByteBuffer frame, PageId pageId) {
		this.buffer = frame;
		this.isDirty=false;

		this.pageId = pageId;
		this.timestamp = 0;

	}

	public PageId getPageId() {
		return pageId;
	}

	public void setPageId(PageId pageId) {
		this.pageId = pageId;
	}

	public ByteBuffer getBuffer() {
        //DiskManager.readPage(pageId, buffer);
		return buffer;
	}

	public void setBuffer(ByteBuffer buffer) {
		this.buffer = buffer;
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
