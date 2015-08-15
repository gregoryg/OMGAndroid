package com.example.ggrubbs.omgandroid;

import android.content.Context;
import android.
    util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by ggrubbs on 8/13/2015.
 */
public class JSONAdapter extends BaseAdapter {
    private static final String IMAGE_URL_BASE = "http://covers.openlibrary.org/b/id/";
    Context mContext;
    LayoutInflater mInflater;
    JSONArray mJsonArray;

    public JSONAdapter(Context context, LayoutInflater inflater) {
	mContext = context;
	mInflater = inflater;
	mJsonArray = new JSONArray();
    }

    public void updateData(JSONArray jsonArray) {
	// update the adapter's dataset
	mJsonArray = jsonArray;
	notifyDataSetChanged();
    }
    
    @Override
    public int getCount() {
	return mJsonArray.length();
    }

    @Override
    public Object getItem(int position) {
	return mJsonArray.optJSONObject(position);
    }

    @Override
    public long getItemId(int position) {
	// the books dataset uses string IDs but something must be returned
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
	ViewHolder holder;

	// check whether the view already exists
	// if so, no need to inflate and findViewById again
	if (convertView == null) {
	    // inflate the custom row layout from your XML
	    convertView = mInflater.inflate(R.layout.row_book, null);
	    //create a new "Holder" with subviews
	    holder = new ViewHolder();
	    holder.thumbnailImageView = (ImageView) convertView.findViewById(R.id.img_thumbnail);
	    holder.titleTextView = (TextView) convertView.findViewById(R.id.text_title);
	    holder.authorTextView = (TextView) convertView.findViewById(R.id.text_author);

	    // hang on to this holder for future recycling
	    convertView.setTag(holder);
	} else {
	    // skip all the expensive inflation/findViewById
	    // and just get the holder made earlier
	    holder = (ViewHolder) convertView.getTag();
	}

	// Get the current book's JSON data
	JSONObject jsonObject = (JSONObject) getItem(position);
	// see if there is a cover ID in the Object
	if (jsonObject.has("cover_i")) {
	    // grab the cover id from the object
	    String imageID = jsonObject.optString("cover_i");
	    // construct the image URL (specifi to the API)
	    String imageURL = IMAGE_URL_BASE + imageID + "-L.jpg";
	    // use Picasso to load the image
	    // temporarily have a placeholder for slow loaders
	    Picasso.with(mContext).load(imageURL).placeholder(R.mipmap.ic_books).into(holder.thumbnailImageView);
	} else {
	    // if no cover ID, use a placeholder
	    holder.thumbnailImageView.setImageResource(R.mipmap.ic_books);
	}
	// grab the title and author from the JSON
	String bookTitle = "";
	String authorName = "";
	if (jsonObject.has("title")) {
	    bookTitle = jsonObject.optString("title");
		Log.d("omg android", "Found title: " + bookTitle );
	}
	if (jsonObject.has("author_name")) {
	    authorName = jsonObject.optJSONArray("author_name").optString(0);
		Log.d("omg android", "Found author: " + authorName);
	}
	// Send these strings to the TextViews for display
	holder.titleTextView.setText(bookTitle);
	holder.authorTextView.setText(authorName);
	    
	return convertView;
    }


    private static class ViewHolder {
	public ImageView thumbnailImageView;
	public TextView titleTextView;
	public TextView authorTextView;
    }
}
