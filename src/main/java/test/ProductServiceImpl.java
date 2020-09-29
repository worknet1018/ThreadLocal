package test;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class  ProductServiceImpl implements ProductService{

    private static final String UPDATE_PRODUCT_SQL ="update product set price = ? where id= ?";
    private static final String INSERT_LOG_SQL ="insert into log (created,description ) values (?,?)";


    public void updateProductPrice(long productId, int price) {

        try {
            //获取数据库连接
            Connection conn =DBUtil.getConnection();
            //关闭数据库自动提交事务
            conn.setAutoCommit(false);

            //执行数据库操作
            //更新产品信息
            updateProduct(conn,UPDATE_PRODUCT_SQL,productId,price);
            //插入日志
            insertLog(conn,INSERT_LOG_SQL,"Create product.");
            //提交事务
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //关闭连接
            DBUtil.closeConnection();
        }
    }

    private void insertLog(Connection conn, String insertLogSql, String s) throws Exception {
        PreparedStatement pstmt= conn.prepareStatement(insertLogSql);
        pstmt.setString(1,new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").format(new Date()));
        pstmt.setString(2,s);
        int rows =pstmt.executeUpdate();
        if (rows !=0){
            System.out.println("Insert log success!");
        }
    }

    private void updateProduct(Connection conn, String updateProductSql, long productId, int price) throws Exception {
        PreparedStatement pstmt= conn.prepareStatement(updateProductSql);
        pstmt.setInt(1,price);
        pstmt.setLong(2,productId);
        int rows =pstmt.executeUpdate();
        if (rows !=0){
            System.out.println("update log success!");
        }
    }

//    public static void main(String[] args) {
//        ProductService productService =new ProductServiceImpl();
//        productService.updateProductPrice(1,3000);
//
//    }
public static void main(String[] args) {
    for (int i = 0; i < 10; i++) {
        ProductService productService = new ProductServiceImpl();
        ClientThread thread =new ClientThread(productService);
        thread.start();

    }
    try {
        DatabaseMetaData metaData =DBUtil.getConnection().getMetaData();
        int defaultIsolation =metaData.getDefaultTransactionIsolation();
        System.out.println(defaultIsolation);
    } catch (SQLException throwables) {
        throwables.printStackTrace();
    }
}
}
