package de.dtk.anconpar;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class ParserActivity extends Activity {

	private static final String QUERY_PREFIX = "q=";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_parser);
		
		Uri data = getIntent().getData();
		
		if (data != null) {
			TextView mainText = (TextView)findViewById(R.id.main_text);
			String query = data.getQuery();
			if (query.startsWith(QUERY_PREFIX)) {
				String address = query.substring(QUERY_PREFIX.length()).trim();
				mainText.setText(address);
			} else {
				mainText.setText(String.format("Error: Found invalid query: %s", query));
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
