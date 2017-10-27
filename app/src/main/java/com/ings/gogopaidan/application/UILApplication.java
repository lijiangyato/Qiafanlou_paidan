/*******************************************************************************
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.ings.gogopaidan.application;

import java.io.File;
import java.net.CookiePolicy;
import java.net.CookieStore;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;

public class UILApplication extends Application {
	private static UILApplication instance;
	private String aspnetauth;
	private String ASP_NET_SessionId;

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressWarnings("unused")
	@Override
	public void onCreate() {
		instance = this;
		if (Constants.Config.DEVELOPER_MODE
				&& Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
					.detectAll().penaltyDialog().build());
			StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
					.detectAll().penaltyDeath().build());
		}

		super.onCreate();
		File cacheDir = StorageUtils.getCacheDirectory(this); // 缓存文件夹路径
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				this).threadPriority(Thread.NORM_PRIORITY - 2)
				.threadPoolSize(3).denyCacheImageMultipleSizesInMemory()
				.diskCache(new UnlimitedDiskCache(cacheDir)) // default 自定义缓存路径
				.build();
		ImageLoader.getInstance().init(config);

		initImageLoader(getApplicationContext());
	}

	public static UILApplication getInstance() {
		return instance;
	}

	public String getAspnetauth() {
		return aspnetauth;
	}

	public void setAspnetauth(String aspnetauth) {
		this.aspnetauth = aspnetauth;
	}

	public String getASP_NET_SessionId() {
		return ASP_NET_SessionId;
	}

	public void setASP_NET_SessionId(String aSP_NET_SessionId) {
		ASP_NET_SessionId = aSP_NET_SessionId;
	}

	public static void initImageLoader(Context context) {

		ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(
				context);
		config.threadPriority(Thread.NORM_PRIORITY - 2);
		config.denyCacheImageMultipleSizesInMemory();
		config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
		config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
		config.tasksProcessingOrder(QueueProcessingType.LIFO);
		config.writeDebugLogs(); // Remove for release app
		ImageLoader.getInstance().init(config.build());
		DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
		builder.cacheInMemory(true);// 设置下载的图片是否缓存在内存中
		builder.cacheOnDisk(true);// 设置下载的图片是否缓存在SD卡中

	}
}