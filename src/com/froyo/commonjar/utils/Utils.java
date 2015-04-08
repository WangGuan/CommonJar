package com.froyo.commonjar.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.view.inputmethod.InputMethodManager;

import com.froyo.commonjar.activity.BaseActivity;

public class Utils {

	@SuppressLint("SimpleDateFormat")
	public static String formatTime(long time, String format) {
		return new SimpleDateFormat(format).format(new Date(time));
	}

	@SuppressLint("SimpleDateFormat")
	public static String formatTime(Date time, String format) {
		// yyyy-MM-dd HH:mm:ss
		return new SimpleDateFormat(format).format(time);
	}

	/**
	 * 格式化 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param time
	 * @return
	 */
	public static String formatTime(String time) {
		if (isEmpty(time)) {
			return "传入时间为空";
		}
		return formatTime(Long.parseLong(time), "yyyy-MM-dd HH:mm");
	}

	public static String formatHour(String time) {
		if (isEmpty(time)) {
			return "传入时间为空";
		}
		return formatTime(Long.parseLong(time), "HH:mm");
	}

	public static boolean isEmpty(List<?> list) {
		return (list == null || list.size() == 0);
	}

	public static boolean isEmpty(File file) {
		return file == null;
	}

	public static <T> boolean isEmpty(T[] array) {
		return ((array == null) || (array.length) == 0);
	}

	public static boolean isEmpty(String val) {
		if (val == null || val.matches("\\s") || val.length() == 0
				|| "null".equalsIgnoreCase(val)) {
			return true;
		}
		return false;
	}

	public static <T> List<T> MapToList(Map<String, T> map) {
		List<T> list = new ArrayList<T>();
		Set<String> set = map.keySet();
		Iterator<String> it = set.iterator();
		while (it.hasNext()) {
			String key = it.next();
			list.add(map.get(key));
		}
		return list;
	}
	public static boolean isMobileNum(String mobile) {
		if (isEmpty(mobile)) {
			return false;
		}
		Pattern p = Pattern.compile("^[1][3-8]+\\d{9}$");
		Matcher m = p.matcher(mobile);
		return m.matches();
	}

	public static void hideKeyboard(BaseActivity activity) {
		try {
			((InputMethodManager) activity
					.getSystemService(activity.INPUT_METHOD_SERVICE))
					.hideSoftInputFromWindow(activity.getCurrentFocus()
							.getWindowToken(),
							InputMethodManager.HIDE_NOT_ALWAYS);
		} catch (Exception e) {
		}
	}

	public static boolean isServiceRunning(Context mContext, String className) {
		boolean isRunning = false;
		ActivityManager activityManager = (ActivityManager) mContext
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> serviceList = activityManager
				.getRunningServices(30);
		if (!(serviceList.size() > 0)) {
			return false;
		}
		for (int i = 0; i < serviceList.size(); i++) {
			if (serviceList.get(i).service.getClassName().equalsIgnoreCase(className) == true) {
				isRunning = true;
				break;
			}
		}
		return isRunning;
	}
	
	public static File saveBitmapFile(Context context, Bitmap bitmap) {
		UUID id = UUID.randomUUID();
		File file = new File(InitUtil.getImageCachePath(context) + id + ".png");// 将要保存图片的路径
		try {
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(file));
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
			bos.flush();
			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}
	
	/**
	 * 
	 * @Des: 获取视频缩略图
	 * @param @param url
	 * @param @param width
	 * @param @param height
	 * @param @return   
	 * @return Bitmap
	 */
	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	public static Bitmap createVideoThumbnail(String url, int width, int height) {
	    Bitmap bitmap = null;
	    MediaMetadataRetriever retriever = new MediaMetadataRetriever();
	    int kind = MediaStore.Video.Thumbnails.MINI_KIND;
	    try {
	      if (Build.VERSION.SDK_INT >= 14) {
	        retriever.setDataSource(url, new HashMap<String, String>());
	      } else {
	        retriever.setDataSource(url);
	      }
	      bitmap = retriever.getFrameAtTime();
	    } catch (IllegalArgumentException ex) {
	      // Assume this is a corrupt video file
	    } catch (RuntimeException ex) {
	      // Assume this is a corrupt video file.
	    } finally {
	      try {
	        retriever.release();
	      } catch (RuntimeException ex) {
	        // Ignore failures while cleaning up.
	      }
	    }
	    if (kind == Images.Thumbnails.MICRO_KIND && bitmap != null) {
	      bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
	          ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
	    }
	    return bitmap;
	  }
}
