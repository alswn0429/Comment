package com.edu.bbs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.edu.comment.CommentDTO;

/**
 * BBSOracleDao.java
 * DB를 사용해 데이터를 조회하거나 조작하는 기능을 구현한 클래스
 * 일반, 공지게시판 등 재사용성을 늘리기 위해 클래스로 분리한다.
 */
public class BBSOracleDAO implements LoginStatus {
  private static BBSOracleDAO bbsOracleDao;
  private OracleDBConnector orclDbc = OracleDBConnector.getInstacne();
   
  Connection conn;
  PreparedStatement pstmt;
  ResultSet rs;
  StringBuffer query;
   
  BbsDTO article;
  ArrayList<BbsDTO> articleList;
  int totalCount;
  
  CommentDTO comment;
  ArrayList<CommentDTO> comments;
  
 
  private BBSOracleDAO() {}
   
  public static BBSOracleDAO getInstance() {
    if(bbsOracleDao == null) {
      bbsOracleDao = new BBSOracleDAO();
    }
    return bbsOracleDao;
  }
   
  // synchronized, 한 명의 글쓰기를 처리한 후 다른 사람의 글쓰기를 처리해야 한다.
  public synchronized int insertArticle(BbsDTO article) throws ClassNotFoundException, SQLException {
    conn = orclDbc.getConnection();
    query = new StringBuffer();
    query.append("INSERT INTO nbbs ");
    query.append("VALUES(nbbs_seq.nextval, ?, ?, ?, nbbs_seq.currval, 0, 0, 0, sysdate, ?)");
    pstmt = conn.prepareStatement(query.toString());
    // parameterIndex는 쿼리문의 ? 순서대로 적어주며, 1부터 시작한다.
    pstmt.setString(1, article.getId());
    pstmt.setString(2, article.getTitle());
    pstmt.setString(3, article.getContent());
    pstmt.setString(4, article.getFileName());
     
    int result = pstmt.executeUpdate();
     
    disconnect();
     
    return result;
  }
   
  public BbsDTO selectArticle(String articleNumber) throws ClassNotFoundException, SQLException {
    conn = orclDbc.getConnection();
    query = new StringBuffer();
    query.append("SELECT * FROM nbbs WHERE article_number = ?");
    pstmt = conn.prepareStatement(query.toString());
    pstmt.setString(1, articleNumber);
    rs = pstmt.executeQuery();
     
    if(rs.next()) {
      article = new BbsDTO();
      article.setArticleNumber(rs.getInt("article_number"));
      article.setId(rs.getString("id"));
      article.setTitle(rs.getString("title"));     
      article.setDepth(rs.getInt("depth"));    
      article.setContent(rs.getString("content"));
      article.setHit(rs.getInt("hit"));
      article.setGroupId(rs.getInt("group_id"));
      article.setPos(rs.getInt("pos"));
      article.setWriteDate(rs.getTimestamp("write_date"));
      article.setFileName(rs.getString("file_name"));
    }
    
    //Comment Counting
    query = new StringBuffer();
    query.append("SELECT count(*) FROM comments WHERE article_number = ?");
    pstmt = conn.prepareStatement(query.toString());
    pstmt.setString(1, articleNumber);
    rs = pstmt.executeQuery();
    
    if(rs.next()) {
    	article.setCommentCount(rs.getLong(1));
    }
    
    disconnect();
     
    return article;
  }
   
  public ArrayList<BbsDTO> selectArticles(int startRow, int endRow) throws ClassNotFoundException, SQLException {
	  conn = orclDbc.getConnection();
	  query = new StringBuffer();
	  // BETWEEN ? AND ? 일 때는 Inline View 두 번 해야한다.
	  // 만약 BETWEEN 1 AND ? 이라면 인라인뷰 한 번만으로 가능하다.
	  query.append("SELECT nbbs.* ");
	  query.append("  FROM (SELECT rownum AS row_num, nbbs.* ");
	  query.append("        FROM (SELECT article_number, id, title, depth, hit, write_date ");
	  query.append("                FROM nbbs ");
	  query.append("               ORDER BY group_id DESC, pos ");
	  query.append("            ) nbbs ");
	  query.append("      ) nbbs ");
	  query.append(" WHERE row_num BETWEEN ? AND ?");
	  pstmt = conn.prepareStatement(query.toString());
	  pstmt.setInt(1, startRow);
	  pstmt.setInt(2, endRow);
	  rs = pstmt.executeQuery();
	   
	  articleList = new ArrayList<>();
	   
	  while(rs.next()) {
	    article = new BbsDTO();
	    article.setArticleNumber(rs.getInt("article_number"));
	    article.setId(rs.getString("id"));
	    article.setTitle(rs.getString("title"));
	    article.setDepth(rs.getInt("depth"));
	    article.setHit(rs.getInt("hit"));
	    article.setWriteDate(rs.getTimestamp("write_date"));
	    articleList.add(article);
	  }
	   
	  disconnect();
	   
	  return articleList;
	}
	 
	public int getArticleTotalCount() throws ClassNotFoundException, SQLException {
	  conn = orclDbc.getConnection();
	  pstmt = conn.prepareStatement("SELECT count(*) AS total_count FROM nbbs");
	  rs = pstmt.executeQuery();
	   
	  int totalCount = 0;
	   
	  if(rs.next()) {
	    totalCount = rs.getInt(1);    // index로 받아오는 것이 속도면에선 좋다.
//	    totalCount = rs.getInt("total_count");    // 하지만 컬럼명이 보기엔 명확한 듯 하다.
	  }
	   
	  disconnect();
	   
	  return totalCount;
	}

	public synchronized int upHit(String articleNumber) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		conn = orclDbc.getConnection();
		pstmt = conn.prepareStatement("UPDATE nbbs SET hit = hit + 1 WHERE article_number = ?");
		pstmt.setString(1, articleNumber);
		int result = pstmt.executeUpdate(); 
		
		disconnect(); 
		
		return result;
	}
	 public int loginCheck(String id, String pw) throws ClassNotFoundException, SQLException {
		    conn = orclDbc.getConnection();
		    pstmt = conn.prepareStatement("SELECT pw FROM users WHERE id = ?");
		    pstmt.setString(1, id);
		    rs = pstmt.executeQuery();
		     
		    int result = 0;
		     
		    if(rs.next()) {
		      if(pw.equals(rs.getString("pw")))
		        // 직관적으로 알 수 있도록 상수로 정의하자.
		        result = LOGIN_SUCCESS;
		      else
		        result = PASS_FAIL;
		    } else
		      result = NOT_MEMBER;
		     
		    disconnect();
		     
		    return result;
		  }
		   
		  public void disconnect() throws SQLException {
		    if(rs != null) {
		      rs.close();
		    }
		    pstmt.close();
		    conn.close();
		  }

		public synchronized int updateArticle(BbsDTO article) throws ClassNotFoundException, SQLException{
			// TODO Auto-generated method stub
			conn = orclDbc.getConnection();
			pstmt = conn.prepareStatement("UPDATE nbbs SET title=?, content=? WHERE article_number=?");
			pstmt.setString(1, article.getTitle());
			pstmt.setString(2,  article.getContent());
			pstmt.setInt(3, article.getArticleNumber());
			int result = pstmt.executeUpdate();
			
			disconnect();
			
			return 0;
		}

		public synchronized int deleteArticle(String articleNumber) throws ClassNotFoundException, SQLException {
			// TODO Auto-generated method stub
			conn=orclDbc.getConnection();
			pstmt = conn.prepareStatement("DELETE FROM nbbs WHERE article_number=?");
			pstmt.setString(1, articleNumber);
			int result = pstmt.executeUpdate();
		
			disconnect();
			
			return result;
		}
		
		public synchronized int upPos(int groupId, int pos) throws ClassNotFoundException, SQLException {
			  conn = orclDbc.getConnection();
			  query = new StringBuffer();
			  query.append("UPDATE nbbs");
			  query.append("     SET pos = pos + 1");
			  query.append(" WHERE group_id = ?");
			  query.append("     AND pos > ?");
			  pstmt = conn.prepareStatement(query.toString());
			  pstmt.setInt(1, groupId);
			  pstmt.setInt(2, pos);
			  int result = pstmt.executeUpdate();
			 
			  disconnect();
			   
			  return result;
			}
			 
		public synchronized int replyArticle(BbsDTO article) throws ClassNotFoundException, SQLException {
			  BBSOracleDAO.getInstance().upPos(article.getGroupId(), article.getPos());
			   
			  conn = orclDbc.getConnection();
			  query = new StringBuffer();
			  query.append("INSERT INTO nbbs ");
			  query.append("VALUES(nbbs_seq.nextval, ?, ?, ?, ?, ?, ?, 0, sysdate, ?)");
			  pstmt = conn.prepareStatement(query.toString());
			  pstmt.setString(1, article.getId());
			  pstmt.setString(2, article.getTitle());
			  pstmt.setString(3, article.getContent());
			  // groupId는 그대로 삽입한다.
			  pstmt.setInt(4, article.getGroupId());
			  // depth는 1 증가시킨다.
			  pstmt.setInt(5, article.getDepth() + 1);
			  // pos은 부모글 아래에 이미 답글이 있으면 모두 1씩 증가시킨 후(upPos메서드가 수행),
			  // 현재 삽입하는 게시글의 pos를 1 증가시킨다.
			  pstmt.setInt(6, article.getPos() + 1);
			  pstmt.setString(7, article.getFileName());
			   
			  int result = pstmt.executeUpdate();
			   
			  disconnect();
			   
			  return result;
		}
			
		public ArrayList<CommentDTO> selectComments(String articleNumber, int commPageSize) throws ClassNotFoundException, SQLException {
		    conn = orclDbc.getConnection();
		    query = new StringBuffer();
		    query.append("SELECT * ");
		    query.append("  FROM (SELECT id, comment_content, comment_date, article_number ");
		    query.append("               FROM comments ");
		    query.append("             WHERE article_number = ? ");
		    query.append("             ORDER BY comment_number DESC");
		    query.append("           ) comments ");
		    query.append(" WHERE rownum BETWEEN 1 AND ?");
		    pstmt = conn.prepareStatement(query.toString());
		    pstmt.setString(1, articleNumber);
		    pstmt.setInt(2, commPageSize);
		    rs = pstmt.executeQuery();
		    
		    comments = new ArrayList<>();
		    
		    while(rs.next()) {
		        comment = new CommentDTO();
		        comment.setId(rs.getString("id"));
		        comment.setCommentContent(rs.getString("comment_content"));
		        comment.setCommentDate(rs.getString("comment_date"));
		        comment.setArticleNumber(rs.getInt("article_number"));
		        comments.add(comment);
		    }
		    
		    disconnect();
		    
		    return comments;
		}
		 
		public synchronized HashMap<String, Object> insertComment(String id, String commentContent, String articleNumber) throws ClassNotFoundException, SQLException {
		    conn = orclDbc.getConnection();
		    pstmt = conn.prepareStatement("INSERT INTO comments VALUES(comment_seq.nextval, ?, ?, sysdate, ?)");
		    pstmt.setString(1, id);
		    pstmt.setString(2, commentContent);
		    pstmt.setString(3, articleNumber);
		    int result = pstmt.executeUpdate();
		    ArrayList<CommentDTO> comments = selectComments(articleNumber, 10);
		    
		    HashMap<String, Object> hm = new HashMap<>();
		    hm.put("result", result);
		    hm.put("comments", comments);
		    
		    disconnect();
		    
		    return hm;
		}


			
			
}



