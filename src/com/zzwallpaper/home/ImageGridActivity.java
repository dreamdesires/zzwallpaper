/*******************************************************************************
 * Copyright 2011-2013 Sergey Tarasevich
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *******************************************************************************/
package com.zzwallpaper.home;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.zzwallpaper.Util.HttpClientUtil;
import com.zzwallpaper.home.Constants.Extra;
import com.zzwallpaper.logic.ApiConfig;

/**
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 */
public class ImageGridActivity extends AbsListViewBaseActivity {

	DisplayImageOptions options;
	private JSONObject jsonObject;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_image_grid);

		options = new DisplayImageOptions.Builder().showStubImage(R.drawable.ic_stub).showImageForEmptyUri(R.drawable.ic_empty).showImageOnFail(R.drawable.ic_error).cacheInMemory(true).cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565).build();

		listView = (GridView) findViewById(R.id.gridview);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				startImagePagerActivity(position);
			}
		});

		// pn=0&rn=2&tag1=%E6%98%8E%E6%98%9F&tag2=%E5%85%A8%E9%83%A8
		RequestParams params = new RequestParams();
		params.put("pn", "0");
		params.put("rn", "30");
		params.put("tag1", "明星");
		params.put("tag2", "全部");
		HttpClientUtil.getHttpClient().HTTP_GET(ApiConfig.mStar, params, new JsonHttpResponseHandler() {

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
			}

			@Override
			public void onSuccess(int statusCode, JSONObject response) {
				jsonObject = response;
				((GridView) listView).setAdapter(new ImageAdapter());

				super.onSuccess(statusCode, response);
			}

			@Override
			public void onFailure(Throwable e, JSONArray errorResponse) {
				// TODO Auto-generated method stub
				super.onFailure(e, errorResponse);
			}
		});

	}

	private void startImagePagerActivity(int position) {
		Intent intent = new Intent(this, ImagePagerActivity.class);
		intent.putExtra(Extra.IMAGES, jsonArray.toString());
		intent.putExtra(Extra.IMAGE_POSITION, position);
		startActivity(intent);
	}
	JSONArray jsonArray = null;
	public class ImageAdapter extends BaseAdapter {

		public ImageAdapter() {
			try {
				jsonArray = jsonObject.getJSONArray("data");
			} catch (JSONException e) {
			}
		}

		@Override
		public int getCount() {
			return Constants.IMAGES.length;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final ImageView imageView;
			if (convertView == null) {
				imageView = (ImageView) getLayoutInflater().inflate(R.layout.item_grid_image, parent, false);
			} else {
				imageView = (ImageView) convertView;
			}

			try {
				imageLoader.displayImage(jsonArray.getJSONObject(position).getString("thumbnail_url"), imageView, options);
			} catch (JSONException e) {
			}
			// imageLoader.displayImage( Constants.IMAGES[position], imageView,
			// options);

			return imageView;
		}
	}

}