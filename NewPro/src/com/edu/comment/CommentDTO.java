package com.edu.comment;

public class CommentDTO {
	private int commentNumber;
	private String id;
	private String commentContent;
	private String commentDate;
	private int articleNumber;
	public int getCommentNumber() {
		return commentNumber;
	}
	public void setCommentNumber(int commentNumber) {
		this.commentNumber = commentNumber;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCommentContent() {
		return commentContent;
	}
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}
	public String getCommentDate() {
		return commentDate;
	}
	public void setCommentDate(String commentDate) {
		this.commentDate = commentDate;
	}
	public int getArticleNumber() {
		return articleNumber;
	}
	public void setArticleNumber(int articleNumber) {
		this.articleNumber = articleNumber;
	}
	@Override
	public String toString() {
		return "CommentDTO [commentNumber=" + commentNumber + ", id=" + id + ", commentContent=" + commentContent
				+ ", commentDate=" + commentDate + ", articleNumber=" + articleNumber + "]";
	}
	
	
}
