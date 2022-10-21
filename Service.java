package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Service {
	//-------------------------------------------
	//データベースへの接続情報
	//-------------------------------------------
	
	//JDBCドライバの相対パス
	String DRIVER_NAME = "org.postgresql.Driver";
	
	//接続先のデータベース
	String JDBC_URL    = "jdbc:postgresql://localhost/postgres";
	
	//接続するユーザー名
	String USER_ID     = "postgres";
	
	//接続するユーザーのパスワード
	String USER_PASS   = "himitu";
	
	//----------------------------------------------------------------
	//メソッド
	//----------------------------------------------------------------
	
	/**------------scheduleMain---------------------------------------------------------*
	 * ■selectMemberInfoAllメソッド
	 * 概要　：「schedule」テーブルの開始日付が今日のレコードをすべて抽出する
	 * 引数　：なし
	 * 戻り値：抽出データ（List<Sample4_02_2_Common_DTO>型）
	 *----------------------------------------------------------------------**/
	
	public List<MainViewEntity> scheduleMain(){
		//-------------------------------------------
		//JDBCドライバのロード
		//-------------------------------------------
		try {
			Class.forName(DRIVER_NAME);       //JDBCドライバをロード＆接続先として指定
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		//-------------------------------------------
		//今日の日付を取得
		//-------------------------------------------
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String str = sdf.format(timestamp) + " 00:00:00";
        String strEnd = sdf.format(timestamp) + " 23:59:59";
        
		
		//-------------------------------------------
		//SQL発行
		//-------------------------------------------
		
		//JDBCの接続に使用するオブジェクトを宣言
		Connection        con = null ;   // DB接続情報格納用変数
		PreparedStatement ps  = null ;   // SQL発行用オブジェクト
		ResultSet         rs  = null ;   // SQL抽出結果格納用変数
		
		//抽出データ（List型）格納用変数
		List<MainViewEntity> dtoList = new ArrayList<MainViewEntity>();
		
		try {
			//-------------------------------------------
			//接続の確立（Connectionオブジェクトの取得）
			//-------------------------------------------
			con = DriverManager.getConnection(JDBC_URL, USER_ID, USER_PASS);
			//-------------------------------------------
			//SQL文の送信 ＆ 結果の取得
			//-------------------------------------------
			
			String sql = "SELECT a.asset_name, s.sche_start_datetime, c.customer_name "
					+ "FROM schedule s LEFT JOIN asset a ON s.asset_id = a.asset_id "
					+ "LEFT JOIN customer c ON s.customer_id = c.customer_id WHERE s.sche_start_datetime BETWEEN '" + str + "'AND" + "'"
					+ strEnd +"' ;";

			//PreparedStatement（SQL発行用オブジェクト）を生成＆発行するSQLをセット
	        ps = con.prepareStatement(sql);
			//SQL文の送信＆戻り値としてResultSet（SQL抽出結果）を取得
			rs = ps.executeQuery();
			//ResultSetオブジェクトから1レコード分のデータをDTOに格納
			while(rs.next()){
				MainViewEntity dto = new MainViewEntity();
//				ScheduleEntity dto = new ScheduleEntity();
//				AssetEntity asset = new AssetEntity();
//				UserEntity user = new UserEntity();
				// 資産名
				dto.setAsset_name(     rs.getString(    "asset_name"     ) );
				// 開始時間
				dto.setSche_start_datetime(   rs.getTime( "sche_start_datetime"   ) );
				// 登録者
				dto.setName(   rs.getString( "customer_name"   ) );
				
				dtoList.add(dto);
//				assetList.add(asset);
//				userList.add(user);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//-------------------------------------------
			//接続の解除
			//-------------------------------------------
			
			//ResultSetオブジェクトの接続解除
			
			if (rs != null) {    //接続が確認できている場合のみ実施
				try {
					rs.close();  //接続の解除
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		
		//PreparedStatementオブジェクトの接続解除
		if (ps != null) {    //接続が確認できている場合のみ実施
			try {
				ps.close();  //接続の解除
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		//Connectionオブジェクトの接続解除
		if (con != null) {    //接続が確認できている場合のみ実施
			try {
				con.close();  //接続の解除
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		
	}
	//抽出データを戻す
		return dtoList;
	}
	
	/**----------------------------------------------------------------------*
	 * ■assetName_showメソッド
	 * 概要　：「asset」テーブルの資産名を抽出する
	 * 引数　：なし
	 * 戻り値：抽出データ
	 *----------------------------------------------------------------------**/
	public List<AssetEntity> assetName_show(){
		//-------------------------------------------
		//JDBCドライバのロード
		//-------------------------------------------
		try {
			Class.forName(DRIVER_NAME);       //JDBCドライバをロード＆接続先として指定
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		//-------------------------------------------
		//SQL発行
		//-------------------------------------------
		
		//JDBCの接続に使用するオブジェクトを宣言
		Connection        con = null ;   // DB接続情報格納用変数
		PreparedStatement ps  = null ;   // SQL発行用オブジェクト
		ResultSet         rs  = null ;   // SQL抽出結果格納用変数
		
		//抽出データ（List型）格納用変数
		List<AssetEntity> dtoList = new ArrayList<AssetEntity>();
		
		try {
			//-------------------------------------------
			//接続の確立（Connectionオブジェクトの取得）
			//-------------------------------------------
			con = DriverManager.getConnection(JDBC_URL, USER_ID, USER_PASS);
			//-------------------------------------------
			//SQL文の送信 ＆ 結果の取得
			//-------------------------------------------
			
			//発行するSQL文の生成（SELECT）
			// SELECT (asset_id, start_time, customer_id)  FROM schedule WHERE sche_start_date = today;
			// SELECT asset_name FROM asset WHERE asset_id = ?;
			// SELECT customer_name FROM customer WHERE customer_id = ?;
			String sql = "SELECT asset_name FROM asset;";

			//PreparedStatement（SQL発行用オブジェクト）を生成＆発行するSQLをセット
	        ps = con.prepareStatement(sql);
			//SQL文の送信＆戻り値としてResultSet（SQL抽出結果）を取得
			rs = ps.executeQuery();
			//ResultSetオブジェクトから1レコード分のデータをDTOに格納
			while(rs.next()){
				AssetEntity dto = new AssetEntity();
				// 資産名
				dto.setAsset_name(     rs.getString(    "asset_name"     ) );
				
				dtoList.add(dto);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//-------------------------------------------
			//接続の解除
			//-------------------------------------------
			
			//ResultSetオブジェクトの接続解除
			
			if (rs != null) {    //接続が確認できている場合のみ実施
				try {
					rs.close();  //接続の解除
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		
		//PreparedStatementオブジェクトの接続解除
		if (ps != null) {    //接続が確認できている場合のみ実施
			try {
				ps.close();  //接続の解除
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		//Connectionオブジェクトの接続解除
		if (con != null) {    //接続が確認できている場合のみ実施
			try {
				con.close();  //接続の解除
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		
	}
	//抽出データを戻す
		return dtoList;
	}
	
	
	/**----------------------------------------------------------------------*
	 * ■customerName_showメソッド
	 * 概要　：「customer」テーブルから利用者名抽出する
	 * 引数　：なし
	 * 戻り値：抽出データ
	 *----------------------------------------------------------------------**/
	public List<UserEntity> customerName_show(){
		//-------------------------------------------
		//JDBCドライバのロード
		//-------------------------------------------
		try {
			Class.forName(DRIVER_NAME);       //JDBCドライバをロード＆接続先として指定
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		//-------------------------------------------
		//SQL発行
		//-------------------------------------------
		
		//JDBCの接続に使用するオブジェクトを宣言
		Connection        con = null ;   // DB接続情報格納用変数
		PreparedStatement ps  = null ;   // SQL発行用オブジェクト
		ResultSet         rs  = null ;   // SQL抽出結果格納用変数
		
		//抽出データ（List型）格納用変数
		List<UserEntity> dtoList = new ArrayList<UserEntity>();
		
		try {
			//-------------------------------------------
			//接続の確立（Connectionオブジェクトの取得）
			//-------------------------------------------
			con = DriverManager.getConnection(JDBC_URL, USER_ID, USER_PASS);
			//-------------------------------------------
			//SQL文の送信 ＆ 結果の取得
			//-------------------------------------------
			
			//発行するSQL文の生成（SELECT）
			// SELECT (asset_id, start_time, customer_id)  FROM schedule WHERE sche_start_date = today;
			// SELECT asset_name FROM asset WHERE asset_id = ?;
			// SELECT customer_name FROM customer WHERE customer_id = ?;
			String sql = "SELECT customer_name FROM customer;";
			
			//PreparedStatement（SQL発行用オブジェクト）を生成＆発行するSQLをセット
	        ps = con.prepareStatement(sql);
			//SQL文の送信＆戻り値としてResultSet（SQL抽出結果）を取得
			rs = ps.executeQuery();
			//ResultSetオブジェクトから1レコード分のデータをDTOに格納
			while(rs.next()){
				UserEntity dto = new UserEntity();
				// 資産名
				dto.setName(     rs.getString(    "customer_name"     ) );
				
				dtoList.add(dto);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//-------------------------------------------
			//接続の解除
			//-------------------------------------------
			
			//ResultSetオブジェクトの接続解除
			
			if (rs != null) {    //接続が確認できている場合のみ実施
				try {
					rs.close();  //接続の解除
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		
		//PreparedStatementオブジェクトの接続解除
		if (ps != null) {    //接続が確認できている場合のみ実施
			try {
				ps.close();  //接続の解除
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		//Connectionオブジェクトの接続解除
		if (con != null) {    //接続が確認できている場合のみ実施
			try {
				con.close();  //接続の解除
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		
	}
	//抽出データを戻す
		return dtoList;
	}
	
	/**----------------------------------------------------------------------*
	 * ■serchIdメソッド
	 * 概要　：「asset」テーブルから資産名が一致しているレコードを抽出する
	 * 引数　：なし
	 * 戻り値：抽出データ
	 *----------------------------------------------------------------------**/
	public List<AssetEntity> serchAssetId(String asset_name){
		//-------------------------------------------
		//JDBCドライバのロード
		//-------------------------------------------
		try {
			Class.forName(DRIVER_NAME);       //JDBCドライバをロード＆接続先として指定
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		//-------------------------------------------
		//SQL発行
		//-------------------------------------------
		
		//JDBCの接続に使用するオブジェクトを宣言
		Connection        con = null ;   // DB接続情報格納用変数
		PreparedStatement ps  = null ;   // SQL発行用オブジェクト
		ResultSet         rs  = null ;   // SQL抽出結果格納用変数
		
		//抽出データ（List型）格納用変数
		List<AssetEntity> dtoList = new ArrayList<AssetEntity>();
		
		try {
			//-------------------------------------------
			//接続の確立（Connectionオブジェクトの取得）
			//-------------------------------------------
			con = DriverManager.getConnection(JDBC_URL, USER_ID, USER_PASS);
			//-------------------------------------------
			//SQL文の送信 ＆ 結果の取得
			//-------------------------------------------
			
			//発行するSQL文の生成（SELECT）
			// SELECT (asset_id, start_time, customer_id)  FROM schedule WHERE sche_start_date = today;
			// SELECT asset_name FROM asset WHERE asset_id = ?;
			// SELECT customer_name FROM customer WHERE customer_id = ?;
			
			StringBuffer buf = new StringBuffer();
			buf.append("select asset_id from asset where asset_name = ");
			buf.append(" '");
			buf.append(asset_name);
			buf.append("' ");
			buf.append(";");
			ps = con.prepareStatement(buf.toString());
			rs = ps.executeQuery();
			
			while(rs.next()){
				AssetEntity dto = new AssetEntity();
				// 資産ID
				dto.setAsset_id(     rs.getInt(    "asset_id"     ) );
				dtoList.add(dto);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//-------------------------------------------
			//接続の解除
			//-------------------------------------------
			
			//ResultSetオブジェクトの接続解除
			
			if (rs != null) {    //接続が確認できている場合のみ実施
				try {
					rs.close();  //接続の解除
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		
		//PreparedStatementオブジェクトの接続解除
		if (ps != null) {    //接続が確認できている場合のみ実施
			try {
				ps.close();  //接続の解除
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		//Connectionオブジェクトの接続解除
		if (con != null) {    //接続が確認できている場合のみ実施
			try {
				con.close();  //接続の解除
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		
	}
	//抽出データを戻す
		return dtoList;
	}
	
	
	/**----------------------------------------------------------------------*
	 * ■serchCustomerIdメソッド
	 * 概要　：「customer」テーブルから利用者名が一致している抽出する
	 * 引数　：なし
	 * 戻り値：抽出データ
	 *----------------------------------------------------------------------**/
	public List<UserEntity> serchCustomerId(String name){
		//-------------------------------------------
		//JDBCドライバのロード
		//-------------------------------------------
		try {
			Class.forName(DRIVER_NAME);       //JDBCドライバをロード＆接続先として指定
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		//-------------------------------------------
		//SQL発行
		//-------------------------------------------
		
		//JDBCの接続に使用するオブジェクトを宣言
		Connection        con = null ;   // DB接続情報格納用変数
		PreparedStatement ps  = null ;   // SQL発行用オブジェクト
		ResultSet         rs  = null ;   // SQL抽出結果格納用変数
		
		//抽出データ（List型）格納用変数
		List<UserEntity> dtoList = new ArrayList<UserEntity>();
		
		try {
			//-------------------------------------------
			//接続の確立（Connectionオブジェクトの取得）
			//-------------------------------------------
			con = DriverManager.getConnection(JDBC_URL, USER_ID, USER_PASS);
			//-------------------------------------------
			//SQL文の送信 ＆ 結果の取得
			//-------------------------------------------
			
			//発行するSQL文の生成（SELECT）
			// SELECT (asset_id, start_time, customer_id)  FROM schedule WHERE sche_start_date = today;
			// SELECT asset_name FROM asset WHERE asset_id = ?;
			// SELECT customer_name FROM customer WHERE customer_id = ?;
			
			StringBuffer buf = new StringBuffer();
			buf.append("select customer_id from customer where customer_name = ");
			buf.append(" '");
			buf.append(name);
			buf.append("' ");
			buf.append(";");
			ps = con.prepareStatement(buf.toString());
			rs = ps.executeQuery();
			
			while(rs.next()){
				UserEntity dto = new UserEntity();
				// 資産ID
				dto.setUserId(     rs.getInt(    "customer_id"     ) );
				dtoList.add(dto);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//-------------------------------------------
			//接続の解除
			//-------------------------------------------
			
			//ResultSetオブジェクトの接続解除
			
			if (rs != null) {    //接続が確認できている場合のみ実施
				try {
					rs.close();  //接続の解除
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		
		//PreparedStatementオブジェクトの接続解除
		if (ps != null) {    //接続が確認できている場合のみ実施
			try {
				ps.close();  //接続の解除
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		//Connectionオブジェクトの接続解除
		if (con != null) {    //接続が確認できている場合のみ実施
			try {
				con.close();  //接続の解除
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		
	}
	//抽出データを戻す
		return dtoList;
	}
	
	/**----------------------------------------------------------------------*
	 * ■insertメソッド
	 * 概要　：「Schedule」テーブルにHTMLのデータを登録
	 * 引数　：なし
	 * 戻り値：抽出データ
	 *----------------------------------------------------------------------**/
	public List<ScheduleEntity> insert(Integer selectNumber,String purpose, Integer asset_id, Integer user_id,
			Timestamp startDate, Timestamp endDate){
		//-------------------------------------------
		//JDBCドライバのロード
		//-------------------------------------------
		try {
			Class.forName(DRIVER_NAME);       //JDBCドライバをロード＆接続先として指定
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		//-------------------------------------------
		//SQL発行
		//-------------------------------------------
		
		//JDBCの接続に使用するオブジェクトを宣言
		Connection        con = null ;   // DB接続情報格納用変数
		PreparedStatement ps  = null ;   // SQL発行用オブジェクト
		ResultSet         rs  = null ;   // SQL抽出結果格納用変数
		
		List<ScheduleEntity> dtoList = new ArrayList<ScheduleEntity>();
		//抽出データ（List型）格納用変数
//		List<ScheduleEntity> dtoList = new ArrayList<ScheduleEntity>();
        try{
            con = DriverManager.getConnection ( JDBC_URL, USER_ID, USER_PASS );
            System.out.println("データベースへ正常に接続しました。");

            //実行するSQL文とパラメータを指定する
            StringBuffer buf = new StringBuffer();
            
            // 登録日時の設定
            Timestamp register_date = new Timestamp(System.currentTimeMillis());
            
            buf.append("INSERT INTO schedule (sche_start_datetime, sche_end_datetime, asset_id, customer_id,");
            buf.append(" number_of_people, purpose, register_date, stock )");
            buf.append(" VALUES (");
            buf.append("'");
            buf.append(startDate);	//sche_start_datetime
            buf.append("'");
            buf.append(" , ");
            buf.append("'");
            buf.append(endDate);	//sche_end_datetime
            buf.append("'");
            buf.append(" , ");
            buf.append("'");
            buf.append(asset_id);	//asset_id
            buf.append("'");
            buf.append(" , ");
            buf.append("'");
            buf.append(user_id);	//customer_id
            buf.append("'");
            buf.append(" , ");
            buf.append(selectNumber);	//number_of_people
            buf.append(" , ");
            buf.append("'");
            buf.append(purpose);	//purpose
            buf.append("'");
            buf.append(" , ");
            buf.append("'");
            buf.append(register_date);	//register_date
            buf.append("'");
            buf.append(" , ");
            buf.append(1);				// stock
            buf.append(" )");
            buf.append(";");
            System.out.println(buf.toString());
            ps = con.prepareStatement(buf.toString());
            rs = ps.executeQuery();
            
            //INSERT文を実行する
            int i = ps.executeUpdate();
            
            //コミット
            con.commit();

            //クローズ処理
            con.close();

        } catch (Exception ex) {
            //例外発生時の処理
//            con.rollback();       //ロールバックする
            ex.printStackTrace();  //エラー内容をコンソールに出力する

        } finally {
        	
        }

        return dtoList;
	}
	
	
	/**----------------------------------------------------------------------*
	 * ■schedule10petternメソッド
	 * 概要　：「asset」テーブルの資産名を抽出する
	 * 引数　：なし
	 * 戻り値：抽出データ
	 *----------------------------------------------------------------------**/
	public List<MainViewEntity> schedule10pettern(){
		//-------------------------------------------
		//JDBCドライバのロード
		//-------------------------------------------
		try {
			Class.forName(DRIVER_NAME);       //JDBCドライバをロード＆接続先として指定
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		//-------------------------------------------
		//今日の日付を取得
		//-------------------------------------------
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String str = sdf.format(timestamp) + " 00:00:00";
        String strEnd = sdf.format(timestamp) + " 23:59:59";
        
		
		//-------------------------------------------
		//SQL発行
		//-------------------------------------------
		
		//JDBCの接続に使用するオブジェクトを宣言
		Connection        con = null ;   // DB接続情報格納用変数
		PreparedStatement ps  = null ;   // SQL発行用オブジェクト
		ResultSet         rs  = null ;   // SQL抽出結果格納用変数
		
		//抽出データ（List型）格納用変数
		List<MainViewEntity> dtoList = new ArrayList<MainViewEntity>();
		
		try {
			//-------------------------------------------
			//接続の確立（Connectionオブジェクトの取得）
			//-------------------------------------------
			con = DriverManager.getConnection(JDBC_URL, USER_ID, USER_PASS);
			//-------------------------------------------
			//SQL文の送信 ＆ 結果の取得
			//-------------------------------------------
			
			String sql = "SELECT a.asset_name, s.sche_start_datetime, c.customer_name "
					+ "FROM schedule s LEFT JOIN asset a ON s.asset_id = a.asset_id "
					+ "LEFT JOIN customer c ON s.customer_id = c.customer_id WHERE s.sche_start_datetime  >= '" + str + ";";

			//PreparedStatement（SQL発行用オブジェクト）を生成＆発行するSQLをセット
	        ps = con.prepareStatement(sql);
			//SQL文の送信＆戻り値としてResultSet（SQL抽出結果）を取得
			rs = ps.executeQuery();
			//ResultSetオブジェクトから1レコード分のデータをDTOに格納
			while(rs.next()){
				MainViewEntity dto = new MainViewEntity();
				// 資産名
				dto.setAsset_name(     rs.getString(    "asset_name"     ) );
				// 開始時間
				dto.setSche_start_datetime(   rs.getTime( "sche_start_datetime"   ) );
				// 登録者
				dto.setName(   rs.getString( "customer_name"   ) );
				
				dtoList.add(dto);
//						assetList.add(asset);
//						userList.add(user);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//-------------------------------------------
			//接続の解除
			//-------------------------------------------
			
			//ResultSetオブジェクトの接続解除
			
			if (rs != null) {    //接続が確認できている場合のみ実施
				try {
					rs.close();  //接続の解除
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		
		//PreparedStatementオブジェクトの接続解除
		if (ps != null) {    //接続が確認できている場合のみ実施
			try {
				ps.close();  //接続の解除
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		//Connectionオブジェクトの接続解除
		if (con != null) {    //接続が確認できている場合のみ実施
			try {
				con.close();  //接続の解除
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		
	}
	//抽出データを戻す
		return dtoList;
	}

}
