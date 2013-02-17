package de.dtk.anconpar;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

public class ParserActivity extends Activity {

	private static final String CLASS = ParserActivity.class.getSimpleName();

	private static final String QUERY_PREFIX = "q=";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Uri data = getIntent().getData();
		
		if (data != null) {
			String query = data.getQuery();
			if (query.startsWith(QUERY_PREFIX)) {
				String address = query.substring(QUERY_PREFIX.length()).trim();
				Log.d(CLASS, String.format("Address: %s", address));
			} else {
				Log.e(CLASS, String.format("Error: Found invalid query: %s", query));
			}
		}
}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.layout_parser, menu);
		return true;
	}

}
