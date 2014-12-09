package br.uff.psgamers.pojo;

public class ActionListItem {
	
	private int thumbnailId;
	private String header;
	private String subheader;
	
	public ActionListItem(int thumbnailId, String header, String subheader) {
		
		this.thumbnailId = thumbnailId;
		this.header = header;
		this.subheader = subheader;
	}
	
	public int getThumbnailId() {
		return thumbnailId;
	}
	public void setThumbnailId(int thumbnailId) {
		this.thumbnailId = thumbnailId;
	}
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public String getSubheader() {
		return subheader;
	}
	public void setSubheader(String subheader) {
		this.subheader = subheader;
	}
}