package com.ings.gogopaidan.db;

import com.ings.gogopaidan.utils.LogUtils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MyShopSqlHelper extends SQLiteOpenHelper {

	public MyShopSqlHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL("create table user(_id integer primary key autoincrement,"
				+ "name TEXT," + "pwd TEXT)");

		LogUtils.e("数据库onCreate 被调用", "数据库onCreate 被调用");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		// db.execSQL("drop table if exists student");
		System.out.println("onUpgrade 被调用" + oldVersion + "--" + newVersion);

	}

}
