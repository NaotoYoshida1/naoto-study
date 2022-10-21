package com.example.demo;

import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {
	
	@Autowired
	HttpSession session;
	
	@Autowired
	ScheduleRepository ScheduleRepository;
	
	@Autowired
	AssetRepository AssetRepository;
	
	/**
	 * 初期画面の画面表示
	 */
	@RequestMapping(value = "/asset/main")
	public ModelAndView showMain(
			ModelAndView mv
			) {
		
		// 表示する内容をDBから取得しHTMLへ渡す
		
		/*
		 * SELECT (asset_id, start_time, customer_id)  FROM schedule;
		 * List<ScheduleEntity> ScheduleData = ScheduleRepository.findByLoginId(sche_start_date);
		 * SELECT asset_name FROM asset WHERE asset_id = ?;
		 * SELECT customer_name FROM customer WHERE customer_id = ?;
		 */
		
		//-------------------------------------------
		//データベースへの接続を実施
		//-------------------------------------------
		
		// 10剣以降の処理の際に何件取得しているか確認する
		int count = 0;
		int pageCount = 1;
		mv.addObject("pageCount", pageCount);
		
		
		//DAOクラスをインスタンス化＆データを抽出するよう依頼
		Service dao = new Service();
		List<MainViewEntity> extractedDtoList = dao.scheduleMain();
		//-------------------------------------------
		//抽出したユーザー情報をコマンドライン上に表示
		//-------------------------------------------
		if(extractedDtoList != null){
			// 10件目以降を表示する際は i + 1 にカウントを代入する
			for(int i = 0 ; i < extractedDtoList.size() ; i++){
				//1レコード分のデータを取得＆加工（各カラムをコンマ綴りで結合）
//				StringBuffer rsbuf = new StringBuffer();
				// appendメソッドを使用して文字列を結合
				// 資産名取得
				String asset = extractedDtoList.get(i).getAsset_name();
				// 開始時間取得
				Time start = extractedDtoList.get(i).getSche_start_datetime();
				// 登録者取得
				String name = extractedDtoList.get(i).getName();
				
				count = i;
				//加工作成した1レコード分のデータを表示
//				System.out.println("資産名" + asset);
//				System.out.println("開始時間" + start);
//				System.out.println("名前" + name);
				mv.addObject("asset" + extractedDtoList.size() + i, extractedDtoList.get(i).getAsset_name());
				mv.addObject("start" + extractedDtoList.size() + i, extractedDtoList.get(i).getSche_start_datetime());
				System.out.println(extractedDtoList.size() + "件");
				System.out.println(asset + start + name);
				mv.addObject("name" + extractedDtoList.size() + i, extractedDtoList.get(i).getName());
				
				System.out.println(i + 1 + "件取得しました" ) ;
				System.out.println(" ");
			}
		} else {
			System.out.println("[INFO]該当のユーザー情報を取得できませんでした" ) ;
		}
		mv.setViewName("Main");
		mv.addObject("count", count);
		return mv;
		
	}
	
	/**
	 * 予定登録画面表示
	 */
	@RequestMapping(value = "/schedule/add")
	public ModelAndView showAdd(
			ModelAndView mv
			) {
		// 資産名をDBから取り出す
		//DAOクラスをインスタンス化＆データを抽出するよう依頼
				Service dao = new Service();
				List<AssetEntity> extractedDtoList = dao.assetName_show();
				//-------------------------------------------
				//抽出したユーザー情報をコマンドライン上に表示
				//-------------------------------------------
				if(extractedDtoList != null){
					// 10件目以降を表示する際は i + 1 にカウントを代入する
					for(int i = 0 ; i < extractedDtoList.size() ; i++){
						//1レコード分のデータを取得＆加工（各カラムをコンマ綴りで結合）
						// appendメソッドを使用して文字列を結合
						// 資産名取得
						String asset = extractedDtoList.get(i).getAsset_name();
						
						//加工作成した1レコード分のデータを表示
						System.out.println("資産名" + asset);
						mv.addObject("asset" + i, extractedDtoList.get(i).getAsset_name());
						mv.addObject("count", i);
					}
				} else {
					System.out.println("[INFO]該当のユーザー情報を取得できませんでした" ) ;
				}
		
		// 利用者をDBから取り出す
				Service dao1 = new Service();
				List<UserEntity> extractedDtoList1 = dao1.customerName_show();
				//-------------------------------------------
				//抽出したユーザー情報をコマンドライン上に表示
				//-------------------------------------------
				if(extractedDtoList1 != null){
					// 10件目以降を表示する際は i + 1 にカウントを代入する
					for(int i = 0 ; i < extractedDtoList1.size() ; i++){
						//1レコード分のデータを取得＆加工（各カラムをコンマ綴りで結合）
						// appendメソッドを使用して文字列を結合
						// 名前取得
						String name = extractedDtoList1.get(i).getName();
						
						//加工作成した1レコード分のデータを表示
						System.out.println("名前" + name + i + "回目");
						mv.addObject("name" + i, extractedDtoList1.get(i).getName());
						mv.addObject("count2", i);
						System.out.println(i + "件取得しました" ) ;
					}
				} else {
					System.out.println("[INFO]該当のユーザー情報を取得できませんでした" ) ;
				}
		mv.addObject("asset", extractedDtoList1);
		
		mv.setViewName("AddSchedule");
		return mv;
	}
	
	
	/**
	 * 予定登録処理
	 * @throws SQLException 
	 */
	@RequestMapping(value = "/schedule/adds", method = RequestMethod.POST)
	public ModelAndView ShowScheduleAdd(
			// htmlに引き渡す値
			// 予定開始日
			@RequestParam("sta_year") Integer sta_year,
			@RequestParam("sta_month") Integer sta_month,
			@RequestParam("sta_day") Integer sta_day,
			
			// 予定開始時間
			@RequestParam("sta_hour") Integer sta_hour,
			@RequestParam("sta_min") Integer sta_min,
			
			// 予定終了日
			@RequestParam("end_year") Integer end_year,
			@RequestParam("end_month") Integer end_month,
			@RequestParam("sta_day") Integer end_day,
			
			// 予定終了時間
			@RequestParam("end_hour") Integer end_hour,
			@RequestParam("end_min") Integer end_min,
			
			// 資産名
			@RequestParam("asset_name") String asset_name,
			@RequestParam("name") String name,
			// 利用人数
			@RequestParam("selectNumber") Integer selectNumber,
			@RequestParam("purpose") String purpose,

			ModelAndView mv
			) throws SQLException {
		/*
		 * 記入確認処理
		 */
		if (sta_year == null
				|| sta_month == null
				|| sta_day == null
				|| sta_hour == null
						|| sta_min == null
				|| end_year == null
				|| end_month == null
				|| end_day == null
				|| end_hour == null
						|| end_min == null
				|| asset_name == null  || asset_name.length() == 0
				|| name == null  || name.length() == 0
				|| selectNumber == null
				) {
			// 空白があった場合の処理
			mv.addObject("message", "必須項目が入力されていません");
			showAdd(mv);
			mv.setViewName("AddSchedule");
			return mv;
		}
		// 時間の桁数確認
		// 時刻のキャスト
		/*
		 * 開始日時の引き渡し部分を変更、時間と日付を１つにして渡す
		 */
		int len;
		// 開始時間の桁数チェック
		len = String.valueOf( sta_hour ).length();
		String start_hour;
		// 桁数が1桁の場合に0を頭につける
		if (len < 2) {
			// 1桁の処理
			start_hour = "0" + String.valueOf(sta_hour);
		} else {
			start_hour = String.valueOf(sta_hour);
		}
		
		// 開始分の桁数チェック
		len = String.valueOf( sta_min ).length();
		String start_min;
		// 桁数が1桁の場合に0を頭につける
		if (len < 2) {
			// 1桁の処理
			start_min = "0" + String.valueOf(sta_min);
		} else {
			start_min = String.valueOf(sta_min);
		}
        
        
        /*
		 * 終了日時の引き渡し部分を変更、時間と日付を１つにして渡す
		 */
        // 終了時間の桁数チェック
        len = String.valueOf( end_hour ).length();
		String ends_hour;
		// 桁数が1桁の場合に0を頭につける
		if (len < 2) {
			// 1桁の処理
			ends_hour = "0" + String.valueOf(end_hour);
		} else {
			ends_hour = String.valueOf(end_hour);
		}
		
		// 終了分の桁数チェック
		len = String.valueOf( end_hour ).length();
		String ends_min;
		// 桁数が1桁の場合に0を頭につける
		if (len < 2) {
			// 1桁の処理
			ends_min = "0" + String.valueOf(end_min);
		} else {
			ends_min = String.valueOf(end_min);
		}
        
		
        // 開始日付の変換
        String year = String.valueOf(sta_year);
        String month = String.valueOf(sta_month);
        String day = String.valueOf(sta_day);
        
        String sta = year + "-" + month + "-" + day + " " + start_hour + ":" + start_min + ":" + "00";
        /*
         * 開始日時
         */
        Timestamp startDate = Timestamp.valueOf(sta);
        
        
        
        // 終了日付の変換
        year = String.valueOf(end_year);
        month = String.valueOf(end_month);
        day = String.valueOf(end_day);
        
        String end = year + "-" + month + "-" + day + " " + ends_hour + ":" + ends_min  + ":" + "00";
        /*
         * 終了日時
         */
        Timestamp endDate = Timestamp.valueOf(end);
		
		
		/*
		 * 資産IDの取得
		 */
		Integer asset_id = 0;
		
		//DAOクラスをインスタンス化＆データを抽出するよう依頼
		Service dao = new Service();
		List<AssetEntity> extractedDtoList = dao.serchAssetId(asset_name);
		//-------------------------------------------
		//抽出したユーザー情報をコマンドライン上に表示
		//-------------------------------------------
		if(extractedDtoList != null){
			System.out.println("リストには入ってる");
			for(int i = 0 ; i < extractedDtoList.size() ; i++){
				// 資産名取得
				asset_id = extractedDtoList.get(i).getAsset_id();
				
				//加工作成した1レコード分のデータを表示
				System.out.println("資産名" + asset_id);
//				mv.addObject("asset" + i, extractedDtoList.get(i).getAsset_name());
				System.out.println(i + "件取得しました" ) ;
			}
		} else {
			System.out.println("[INFO]該当のユーザー情報を取得できませんでした" ) ;
		}
		// 資産IDの確認
		System.out.println(asset_id);
		
		/*
		 * ユーザーIDの取得
		 */
		Integer user_id = 0;
		
		List<UserEntity> DtoList = dao.serchCustomerId(name);
		//-------------------------------------------
		//抽出したユーザー情報をコマンドライン上に表示
		//-------------------------------------------
		if(DtoList != null){
			System.out.println("リストには入ってる");
			for(int i = 0 ; i < DtoList.size() ; i++){
				// 資産名取得
				user_id = DtoList.get(i).getUserId();
				
				//加工作成した1レコード分のデータを表示
				System.out.println("資産名" + user_id);
//				mv.addObject("asset" + i, DtoList.get(i).getAsset_name());
				System.out.println(i + "件取得しました" ) ;
			}
		} else {
			System.out.println("[INFO]該当のユーザー情報を取得できませんでした" ) ;
		}
		// 資産IDの確認
		System.out.println(user_id);
		
		
		/*
		 *  登録処理
		 */
		//DAOクラスをインスタンス化＆データを抽出するよう依頼
				List<ScheduleEntity> insertInfo = dao.insert(selectNumber,purpose, asset_id, user_id, 
						startDate, endDate
						);
				//-------------------------------------------
				//抽出したユーザー情報をコマンドライン上に表示
				//-------------------------------------------
				if(insertInfo != null){
					System.out.println("リストには入ってる");
					for(int i = 0 ; i < insertInfo.size() ; i++){
						// 資産名取得
						asset_id = insertInfo.get(i).getAsset_id();
						
						//加工作成した1レコード分のデータを表示
						System.out.println("資産名" + asset_id);
//						mv.addObject("asset" + i, extractedDtoList.get(i).getAsset_name());
						System.out.println(i + "件取得しました" ) ;
					}
				} else {
					System.out.println("[INFO]該当のスケジュール情報を登録できませんでした" ) ;
				}
		
		
		// 受け渡し処理
		int pageCount = 1;
		int count;
		mv.addObject("pageCount", pageCount);
		
		
		//daoClassクラスをインスタンス化＆データを抽出するよう依頼
		Service daoClass = new Service();
		List<MainViewEntity> MainList = daoClass.scheduleMain();
		//-------------------------------------------
		//抽出したユーザー情報をコマンドライン上に表示
		//-------------------------------------------
		if(MainList != null){
			// 10件目以降を表示する際は i + 1 にカウントを代入する
			for(int i = 0 ; i < MainList.size() ; i++){
				//1レコード分のデータを取得＆加工（各カラムをコンマ綴りで結合）
//						StringBuffer rsbuf = new StringBuffer();
				// appendメソッドを使用して文字列を結合
				// 資産名取得
				String asset = MainList.get(i).getAsset_name();
				// 開始時間取得
				Time start = MainList.get(i).getSche_start_datetime();
				// 登録者取得
				String names = MainList.get(i).getName();
				
				count = i;
				//加工作成した1レコード分のデータを表示
//				System.out.println("資産名" + asset);
//				System.out.println("開始時間" + start);
//				System.out.println("名前" + names);
				mv.addObject("asset" + MainList.size() + i, MainList.get(i).getAsset_name());
				mv.addObject("start" + MainList.size() + i, MainList.get(i).getSche_start_datetime());
				mv.addObject("name" + MainList.size() + i, MainList.get(i).getName());
				mv.addObject("count", i);
				System.out.println(i + "件取得しました" ) ;
			}
		} else {
			System.out.println("[INFO]該当のユーザー情報を取得できませんでした" ) ;
		}mv.setViewName("Main");
		
		return mv;
	}
	
	
	/**
	 * 予定確認画面表示
	 * 最初のページ
	 */
	@RequestMapping(value = "/asset/schedule/check")
	public ModelAndView Check(
			// htmlに引き渡す値
			ModelAndView mv
			) {
		// 全件表示する際に10件ずつ表示するロジックを組む
		
		// 10剣以降の処理の際に何件取得しているか確認する
		int pageCount = 1;
		
		
		//DAOクラスをインスタンス化＆データを抽出するよう依頼
		Service dao = new Service();
		List<MainViewEntity> extractedDtoList = dao.scheduleMain();
		int check = 0;
		//-------------------------------------------
		//抽出したユーザー情報をコマンドライン上に表示
		//-------------------------------------------
		if(extractedDtoList != null){
			// 10件目以降を表示する際は i + 1 にカウントを代入する
			
			for(int i = 0 ; i < extractedDtoList.size() ; i++){
				//1レコード分のデータを取得＆加工（各カラムをコンマ綴りで結合）
//						StringBuffer rsbuf = new StringBuffer();
				// appendメソッドを使用して文字列を結合
				// 資産名取得
				String asset = extractedDtoList.get(i).getAsset_name();
				// 開始時間取得
				Time start = extractedDtoList.get(i).getSche_start_datetime();
				// 登録者取得
				String name = extractedDtoList.get(i).getName();
				
				check = 1;
				//加工作成した1レコード分のデータを表示
//				System.out.println("資産名" + asset);
//				System.out.println("開始時間" + start);
//				System.out.println("名前" + name);
				System.out.println(extractedDtoList.size());
				mv.addObject("asset" + extractedDtoList.size() + i, extractedDtoList.get(i).getAsset_name());
				System.out.println("asset" + extractedDtoList.size() + i);
				mv.addObject("start" + extractedDtoList.size() + i, extractedDtoList.get(i).getSche_start_datetime());
				System.out.println("start" + extractedDtoList.size() + i);
				mv.addObject("name" + extractedDtoList.size() + i, extractedDtoList.get(i).getName());
				System.out.println("name" + extractedDtoList.size() + i);
				mv.addObject("count", i);
				System.out.println(i + 1 + "件取得しました" ) ;
			}
		} else {
			System.out.println("[INFO]該当のユーザー情報を取得できませんでした" ) ;
			mv.addObject("asset" + 0, "今日の予定はありません");
			mv.addObject("start" + 0, "今日の予定はありません");
			mv.addObject("name" + 0, "今日の予定はありません");
			mv.addObject("count", 0);
		}
		pageCount = pageCount + 1;
		mv.addObject("pageCount", pageCount);
		
		// 全抜け処理
		if (check == 0) {
			mv.addObject("asset" + 0, "今日の予定はありません");
			mv.addObject("start" + 0, "今日の予定はありません");
			mv.addObject("name" + 0, "今日の予定はありません");
			mv.addObject("count", 0);
			mv.addObject("message", "今日の予定はありません");
		}
		
		// 絞り込み処理(デフォルトも作成する)
		// 資産名の中身をDBから
		// SELECT asset_name FROM schedule
//		mv.addObject("", ScheduleRepository.findAll());
//		
//		// 開始日時をDBから
//		// SELECT sche_start_date FROM schedule
//		mv.addObject("", ScheduleRepository.findAll());
//		
//		// 利用者をDBから
//		// SELECT customer_name FROM schedule
//		mv.addObject("", ScheduleRepository.findAll());
		
		// 必要な情報をリストに追加
		
		// 絞り込み処理
		
		// 受け渡し処理
		mv.setViewName("CheckSchedule");
		
		return mv;
	}
	
	
	/*
	 * 予定確認画面表示
	 * 2ページ目以降
	 */
	@RequestMapping(value = "/asset/schedule/check/pages")
	public ModelAndView Check2(
			// htmlに引き渡す値
			@PathVariable(name = "pageCount") int pageCount,
			ModelAndView mv
			) {
		// 全件表示する際に10件ずつ表示するロジックを組む
		
		// 10剣以降の処理の際に何件取得しているか確認する
//		pageCount = 0;
		
		
		//DAOクラスをインスタンス化＆データを抽出するよう依頼
		Service dao = new Service();
		List<MainViewEntity> extractedDtoList = dao.scheduleMain();
		//-------------------------------------------
		//抽出したユーザー情報をコマンドライン上に表示
		//-------------------------------------------
		if(extractedDtoList != null){
			// 10件目以降を表示する際は i + 1 にカウントを代入する
			for(int i = (pageCount - 1) * 10 + 1 ; i < extractedDtoList.size() ; i++){
				//1レコード分のデータを取得＆加工（各カラムをコンマ綴りで結合）
//						StringBuffer rsbuf = new StringBuffer();
				// appendメソッドを使用して文字列を結合
				// 資産名取得
				String asset = extractedDtoList.get(i).getAsset_name();
				// 開始時間取得
				Time start = extractedDtoList.get(i).getSche_start_datetime();
				// 登録者取得
				String name = extractedDtoList.get(i).getName();
				
				pageCount = pageCount + 1;
				//加工作成した1レコード分のデータを表示
//				System.out.println("資産名" + asset);
//				System.out.println("開始時間" + start);
//				System.out.println("名前" + name);
				
				// サイズの1桁目を取得
				Integer number = Integer.valueOf(extractedDtoList.size());
				String str = number.toString();
				for (int s = 0; s < str.length(); s++) {
					str = str.substring(str.length());
				}
				number = Integer.parseInt(str);
				
				System.out.println(number);
				mv.addObject("asset" + number + i, extractedDtoList.get(i).getAsset_name());
				System.out.println("asset" + number + i);
				mv.addObject("start" + number + i, extractedDtoList.get(i).getSche_start_datetime());
				System.out.println("start" + number + i);
				mv.addObject("name" + number + i, extractedDtoList.get(i).getName());
				System.out.println("name" + number + i);
				mv.addObject("count", i);
				mv.addObject("pageCount", pageCount);
				System.out.println(i + 1 + "件取得しました" ) ;
			}
		} else {
			System.out.println("[INFO]該当のユーザー情報を取得できませんでした" ) ;
			mv.addObject("message", "次の10件はありません");
//			List<MainViewEntity> extractedDtoList2 = dao.scheduleMain();
//			
//			for(int i = (pageCount - 2) * 10 + 1 ; i < extractedDtoList2.size() ; i++){
//				//1レコード分のデータを取得＆加工（各カラムをコンマ綴りで結合）
////						StringBuffer rsbuf = new StringBuffer();
//				
//				pageCount = pageCount + 1;
//				//加工作成した1レコード分のデータを表示
//				
//				// サイズの1桁目を取得
//				Integer number = Integer.valueOf(extractedDtoList2.size());
//				String str = number.toString();
//				for (int s = 0; s < str.length(); s++) {
//					str = str.substring(str.length());
//				}
//				number = Integer.parseInt(str);
//				
//				System.out.println(number);
//				mv.addObject("asset" + number + i, extractedDtoList2.get(i).getAsset_name());
//				System.out.println("asset" + number + i);
//				mv.addObject("start" + number + i, extractedDtoList2.get(i).getSche_start_datetime());
//				System.out.println("start" + number + i);
//				mv.addObject("name" + number + i, extractedDtoList2.get(i).getName());
//				System.out.println("name" + number + i);
//				mv.addObject("count", i);
//				mv.addObject("pageCount", pageCount);
//				System.out.println(i + 1 + "件取得しました" ) ;
//			}
//			
//			mv.setViewName("CheckSchedule");
//			
//			return mv;
		}
		
		pageCount = pageCount + 1;
		mv.addObject("pageCount", pageCount);
		
		// 絞り込み処理(デフォルトも作成する)
		// 資産名の中身をDBから
		// SELECT asset_name FROM schedule
//		mv.addObject("", ScheduleRepository.findAll());
//		
//		// 開始日時をDBから
//		// SELECT sche_start_date FROM schedule
//		mv.addObject("", ScheduleRepository.findAll());
//		
//		// 利用者をDBから
//		// SELECT customer_name FROM schedule
//		mv.addObject("", ScheduleRepository.findAll());
		
		// 必要な情報をリストに追加
		
		// 絞り込み処理
		
		// 受け渡し処理
		mv.setViewName("CheckSchedule");
		
		return mv;
	}
	
	
	/*
	 * 絞り込み操作のクラス作成
	 */
	@RequestMapping(value = "/sort")
	public ModelAndView sort(
			ModelAndView mv
			) {
		
		
		return mv;
	}
	
	
	
	/**
	 * 車庫確認画面表示
	 */
//	@RequestMapping(value = "/asset/stock")
//	public ModelAndView Stock(
//			// htmlに引き渡す値
//			ModelAndView mv
//			) {
//		
//		// 絞り込み処理(デフォルトも作成する)
//		
//		// 必要な情報をリストに追加
//		// 資産名の中身をDBから
//		// SELECT asset_name FROM schedule
//		mv.addObject("", ScheduleRepository.findAll());
//		
//		// 開始日時をDBから
//		// SELECT sche_start_date FROM schedule
//		mv.addObject("", ScheduleRepository.findAll());
//		
//		// 利用者をDBから
//		// SELECT customer_name FROM schedule
//		mv.addObject("", ScheduleRepository.findAll());
//		
//		// 受け渡し処理
//		mv.setViewName("");
//		
//		return mv;
//	}

}
