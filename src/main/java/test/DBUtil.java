package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    //数据库配置
    private static final String driver ="com.mysql.jdbc.Driver";
    private static final String url ="jdbc:mysql://192.168.19.131:3306/demo?useUnicode=true&characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=GMT%2B8";
    private static final String username ="root";
    private static final String password ="root";

    //定义一个用于放置数据库连接的局部线程变量(使每个线程都拥有自己的连接）
    private static ThreadLocal<Connection> connContainer =new ThreadLocal<Connection>();

//    //定义一个数据库连接
//    private static Connection conn =null;


    //获取数据库连接
    public static Connection getConnection(){
        Connection conn =connContainer.get();
        try {
            Class.forName(driver);
            conn= DriverManager.getConnection(url,username,password);
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            connContainer.set(conn);
        }
return conn;
    }

    //关闭数据库连接
    public static  void closeConnection(){
        Connection conn=connContainer.get();
        try {
            if (conn !=null){
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            connContainer.remove();
        }
    }
}
