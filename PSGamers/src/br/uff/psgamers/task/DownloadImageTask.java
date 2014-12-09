package br.uff.psgamers.task;

import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap>{

	private ImageView image;
	
	public DownloadImageTask(ImageView bmImage) {
		this.image = bmImage;
	}
	
	@Override
	protected Bitmap doInBackground(String... urls) {
        return loadImageFromNetwork(urls[0]);
	}
	
	protected void onPostExecute(Bitmap result) {
		
	    image.setImageBitmap(result);
	}
	
	private Bitmap loadImageFromNetwork(String url) {
		
		Bitmap bitmap = null;
		
		try {
            InputStream in = new java.net.URL(url).openStream();
            bitmap = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
		
		return bitmap;
	}
}