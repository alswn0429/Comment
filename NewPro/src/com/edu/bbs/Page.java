package com.edu.bbs;

public class Page {
	private static Page page = new Page();
	private int startRow, endRow;
	private StringBuffer sb;
	
	private Page() {}
	
	public static Page getInstance() {
		if(page == null) {
			page = new Page();
		}
		return page;
	}
	
	// pageSize:����Ʈ row ����, pageBlock: �ϴ� ������ ����
	  public synchronized void paging(int pageNum, int totalCount, int pageSize, int pageBlock) {
	    // totalPage : �� ������ ��
	    int totalPage = (int)Math.ceil((double)totalCount/pageSize);    // Math.ceil() : �ø�
	    // startRow, endRow : �� �������� start, end row ���
	    startRow = (pageNum - 1) * pageSize + 1;
	    endRow = pageNum * pageSize;
	     
	    // ������ �켱����: ���׿�����(casting ��) > ���������(* ��)
	    // ���� �������� ���� �ϴܺο� ����� start, end ������ ����Ѵ�.
	    // ���� ���, 3�������� 1~10������ �ӿ� ���Եȴ�.
	    int startPage = (int)((pageNum - 1)/pageBlock) * pageBlock + 1;
	    int endPage = startPage + pageBlock - 1;
	     
	    // ���� ���� ������ �������� ���� ������ ���������� ���ٸ�,
	    // ���� ���, �� 77���������� ���������� ���� �������� 80�����̹Ƿ� 77�� �������ش�.
	    if(endPage > totalPage) {
	      endPage = totalPage;
	    }
	     
	    // html�ڵ� ������ StringBuffer�ν��Ͻ� ����
	    sb = new StringBuffer();
	     
	    if(startPage < pageBlock) {
	        sb.append("<li class='disabled'><a href='#' aria-label='Previous'><span aria-hidden='true'>&laquo;</span></a></li>");
	      } else {
	        sb.append("<li class=''><a href='list.bbs?pageNum=");
	        sb.append(startPage - pageBlock);
	        sb.append("' aria-label='Previous'><span aria-hidden='true'>&laquo;</span></a></li>");
	      }
	       
	      for(int i = startPage; i <= endPage; i++) {
	        if(i == pageNum) {
	          sb.append("<li class='active'><a href='#'>");
	          sb.append(i);
	          sb.append("<span class='sr-only'>");
	          sb.append(i);
	          sb.append("</span></a></li>");
	        } else {
	          sb.append("<li class=''><a href='list.bbs?pageNum=");
	          sb.append(i);
	          sb.append("'>");
	          sb.append(i);
	          sb.append("<span class='sr-only'>");
	          sb.append(i);
	          sb.append("</span></a></li>");
	        }
	      }
	       
	      if(endPage < totalPage) {
	        sb.append("<li class=''><a href='list.bbs?pageNum=");
	        sb.append(startPage + pageBlock);
	        sb.append("' aria-label='Next'><span aria-hidden='true'>&raquo;</span></a></li>");
	      } else {
	        sb.append("<li class='disabled'><a href='#' aria-label='Next'><span aria-hidden='true'>&raquo;</span></a></li>");
	      }
	    }
	     
	    public StringBuffer getSb() {
	      return sb;
	    }
	   
	    public int getStartRow() {
	      return startRow;
	    }
	   
	    public int getEndRow() {
	      return endRow;
	    }
}
