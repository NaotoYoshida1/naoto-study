package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class testController {
	
	@Autowired
	HttpSession session;
	
	
	/**
	 * テスト
	 */
	@RequestMapping("/")
	public String test1 () {
		session.invalidate();
		return "1";
	}
	
	/**
	 * insert表示
	 */
	@RequestMapping("/show")
	public String showInsert () {
		session.invalidate();
		return "insert";
	}
	
	/**
	 * insert表示
	 */
	@RequestMapping("/showupd")
	public ModelAndView showUpd (
			ModelAndView mv
			) {
		// 接続文字列の設定
        String url = "jdbc:postgresql://localhost/sampledb" ;
        String user = "postgres" ;
        
        String password = "himitu" ;

        // SELECT文の作成・実行
        String sql = "select * from testlist" ;

        ArrayList<String> col1 = new ArrayList<>();
//        String col1 = null;
        ArrayList<String> col2 = new ArrayList<>();
//        String col2 = null;
        
        int count = 0;
        
        // PostgreSQLに接続
        try (Connection con = DriverManager.getConnection ( url, user, password );
        		// String型でSQL指定
                Statement stmt = con.createStatement();
                ResultSet result = stmt.executeQuery ( sql ); ) {
        	if (result == null) {
        		System.out.println("nullです");
        	}

            // 実行結果の取得
            while ( result.next() ) {
            	
                col1.add(result.getString ( "id" )) ;
                col2.add(result.getString ( "name" )) ;
            	
//            	col1 = result.getString ( 1 ) ;
//              col2 = result.getString ( 2 ) ;
                                
                mv.addObject("test", col1);
                mv.addObject("test2", col2);
                System.out.println ( col1 + " " + col2 + "です" ) ;
            }
        } catch ( SQLException e ) {
          e.printStackTrace() ;
      }
		
        
		mv.setViewName("upd");
		return mv;
	}
	
	/**
	 * 初期画面の画面表示
	 */
	@RequestMapping(value = "/main")
	public ModelAndView showMain(
			ModelAndView mv
			) {
		
		/*
		 * 全件表示
		 */
		
		/*
		 * SQL実行準備
		 */
		// 接続文字列の設定
        String url = "jdbc:postgresql://localhost/sampledb" ;
        String user = "postgres" ;
        
        String password = "himitu" ;

        // SELECT文の作成・実行
        String sql = "select * from testlist" ;

        String col1 = null;
        String col2 = null;
        
        // PostgreSQLに接続
        try (Connection con = DriverManager.getConnection ( url, user, password );
                Statement stmt = con.createStatement();
                ResultSet result = stmt.executeQuery ( sql ); ) {
        	if (result == null) {
        		System.out.println("nullです");
        	}

            // 実行結果の取得
            while ( result.next() ) {
                col1 = result.getString ( 1 ) ;
                col2 = result.getString ( 2 ) ;
                
                System.out.println ( col1 + " " + col2 + "です" ) ;
            }
        } catch ( SQLException e ) {
          e.printStackTrace() ;
      }
		
        mv.addObject("test", col1);
        mv.addObject("test2", col2);
        
        
        mv.setViewName("test1");
		return mv;
	}
	
	/**
	 * 追加作業
	 * @throws SQLException 
	 */
	@RequestMapping(value = "/insert")
	public ModelAndView insertTest(
			@RequestParam(name = "id") int id,
			@RequestParam(name = "name") String name,
			ModelAndView mv
			) throws SQLException {
		
		/*
		 * SQL実行準備
		 */
		// 接続文字列の設定
        String url = "jdbc:postgresql://localhost/sampledb" ;
        String user = "postgres" ;
        
        String password = "himitu" ;
        
        String sql = "INSERT INTO testlist VALUES(?, ?)" ;

//        /*
//		 * 更新
//		 */
//        // SELECT文の作成・実行
//        String sql = "UPDATE testlist SET id = ?, name = ? WHERE no = ?" ;

        String col1 = null;
        String col2 = null;
        PreparedStatement ps = null;
        Connection con = null;
        
        // PostgreSQLに接続
        try{
            con = DriverManager.getConnection ( url, user, password );
            System.out.println("データベースへ正常に接続しました。");
            
          //実行するSQL文とパラメータを指定する
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, name);
          //INSERT文を実行する
            int i = ps.executeUpdate();
            
          //コミット
            con.commit();
        /*sql="insert into product values(101,'Viz','Compact car',100);";
        	statement.executeUpdate(sql);
         
        //アップデート文
        sql="update product set price=price*1.1 where price>=200;";
        int result=statement.executeUpdate(sql);
        System.out.println(result+" products are changed.");
         
        //消去処理
        sql="delete product where price<=100;";
        int result2=statement.executeUpdate(sql);
        System.out.println(result2+" products are deleted.");
        */
        		
        //クローズ処理
        con.close();
        
        } catch (Exception ex) {
            //例外発生時の処理
            con.rollback();       //ロールバックする
            ex.printStackTrace();  //エラー内容をコンソールに出力する

        } finally {
            //クローズ処理
            if (ps != null) ps.close();
            if (con != null) con.close();
        }
        
        mv.setViewName("upd");
		return mv;
	}
	
	
	/**
	 * 更新
	 * @throws SQLException 
	 */
	@RequestMapping(value = "/upd")
	public ModelAndView updTest(
			@RequestParam(name = "id") int id,
			@RequestParam(name = "name") String name,
			ModelAndView mv
			) throws SQLException {
		
		/*
		 * SQL実行準備
		 */
		// 接続文字列の設定
        String url = "jdbc:postgresql://localhost/sampledb" ;
        String user = "postgres" ;
        
        String password = "himitu" ;

        // SELECT文の作成・実行
        String sql = "UPDATE testlist SET id = ?, name = ? WHERE no = ?" ;

        String col1 = null;
        String col2 = null;
        PreparedStatement ps = null;
        Connection con = null;
        
        // PostgreSQLに接続
        try{
            con = DriverManager.getConnection ( url, user, password );
            System.out.println("データベースへ正常に接続しました。");
            
            //アップデート文
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, name);
            int result=ps.executeUpdate(sql);
            
            
//          //実行するSQL文とパラメータを指定する
//            ps = con.prepareStatement(sql);
//            ps.setInt(1, id);
//            ps.setString(2, name);
//          //INSERT文を実行する
//            int i = ps.executeUpdate();
            
          //コミット
            con.commit();
        /*sql="insert into product values(101,'Viz','Compact car',100);";
        	statement.executeUpdate(sql);
         
        //アップデート文
        sql="update product set price=price*1.1 where price>=200;";
        int result=statement.executeUpdate(sql);
        System.out.println(result+" products are changed.");
         
        //消去処理
        sql="delete product where price<=100;";
        int result2=statement.executeUpdate(sql);
        System.out.println(result2+" products are deleted.");
        */
        		
        //クローズ処理
        con.close();
        
        } catch (Exception ex) {
            //例外発生時の処理
            con.rollback();       //ロールバックする
            ex.printStackTrace();  //エラー内容をコンソールに出力する

        } finally {
            //クローズ処理
            if (ps != null) ps.close();
            if (con != null) con.close();
        }
        
        mv.setViewName("upd");
		return mv;
	}
	

}
