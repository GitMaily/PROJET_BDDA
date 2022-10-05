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
	
	
	
	public Frame() {
		
	}
	
	public Frame(PageId pageId) {
		this.setPin_count(1);
		this.setDirty(false);
		this.pageId = pageId;
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
