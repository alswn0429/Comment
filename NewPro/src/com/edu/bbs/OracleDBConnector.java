package com.edu.bbs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * OracleDBConnector.java
 * Oracle Connection�� ������ Ŭ����
 * ���� �����ͺ��̽� ����� ���� Ŀ�ؼ��� Ŭ������ �и��Ѵ�.
 */
public class OracleDBConnector {
//  private static OracleDBConnector orclDbc = new OracleDBConnector();   // 1.�ٷ� �޸� �Ҵ��ϴ� ���
  private static OracleDBConnector orclDbc;
 
  // �ܺο����� �ν��Ͻ�ȭ�� ���´�.
  private OracleDBConnector() {}
 
  // OracleDBConnector �ν��Ͻ��� ��� ����� getInstance()�޼���� ȣ���ϴ� ������̴�.
  public static OracleDBConnector getInstacne() {
    if (orclDbc == null) {
      orclDbc = new OracleDBConnector();    // 2.�޸𸮿� �Ҵ���� �ʾ��� �� �Ҵ��ϴ� ���
    }
    return orclDbc;
  }
 
  public Connection getConnection() throws ClassNotFoundException, SQLException {
    Class.forName("oracle.jdbc.driver.OracleDriver");
    // core.log�� ����ϰԵǸ� ���� ����� �ֿܼ� ���� �� �ִ�.
//    Class.forName("core.log.jdbc.driver.OracleDriver");
    String url = "jdbc:oracle:thin:@localhost:1521:orcl";
    Connection conn = DriverManager.getConnection(url, "scott", "tiger");
    return conn;
  }
}
