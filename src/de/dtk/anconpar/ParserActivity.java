package de.dtk.anconpar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

public class ParserActivity extends Activity {

	private static final String CLASS = ParserActivity.class.getSimpleName();

	private static final String QUERY_PREFIX = "q=";

	private static final String DELIMITER = ",";
	private static final String STREET_FORMAT = "\\p{L}[\\p{L}- ]+[0-9]+";
	private static final String CITY_FORMAT = "[0-9]{4,5} +\\p{L}[\\p{L}- ]+";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		String scheme = intent.getScheme();
		String type = intent.getType();

		if (scheme != null && scheme.equals("geo")) {
			String query = intent.getData().getQuery();
			if (query.startsWith(QUERY_PREFIX)) {
				String location = query.substring(QUERY_PREFIX.length()).trim();
				createNewContact(location);
			} else {
				String problem = String.format("Error: Found invalid query: %s", query);
				Toast.makeText(this, problem, Toast.LENGTH_LONG).show();
				Log.e(CLASS, problem);
			}
		} else if (type != null && type.equals("text/plain")){
			String location = intent.getStringExtra(Intent.EXTRA_TEXT);
			createNewContact(location);
		} else {
			String problem = String.format("Error: Found invalid intent of scheme '%s' and type '%s'",
					                       intent.getScheme(), intent.getType());
			Toast.makeText(this, problem, Toast.LENGTH_LONG).show();
			Log.e(CLASS, problem);
		}

		Log.d(CLASS, "Done");
		finish();
}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.layout_parser, menu);
		return true;
	}

	private void createNewContact(String location) {
		Intent addContact = new Intent(Intent.ACTION_INSERT);
		addContact.setType(ContactsContract.Contacts.CONTENT_TYPE);

		String delimiter = location.contains("\n") ? "\n" : DELIMITER;

		String[] components = location.split(delimiter);

		for (String component: components) {
			categorizeComponent(component.trim(), addContact);
		}

		startActivity(addContact);

	}

	private void categorizeComponent(String component, Intent contact) {
		Log.d(CLASS, String.format("categorizing '%s'", component));
		if (component.matches(STREET_FORMAT) || component.matches(CITY_FORMAT)) {
			Log.d(CLASS, "\tis street or city");
			addAddressInfo(component, contact);
		} else {
			Log.d(CLASS, "\tis unstructured data");
			addUnstructuredData(component, contact);
		}
	}

	private void addAddressInfo(String info, Intent contact) {
		String addressKey = ContactsContract.Intents.Insert.POSTAL;
		String address = getExistingValue(addressKey, contact);

		address += info;
		contact.putExtra(addressKey, address);
	}

	private void addUnstructuredData(String date, Intent contact) {
		String nameKey = ContactsContract.Intents.Insert.NAME;
		String notesKey = ContactsContract.Intents.Insert.NOTES;

		if (!contact.hasExtra(nameKey)) {
			contact.putExtra(nameKey, date);
		} else {
			String notes = getExistingValue(notesKey, contact);
			notes += date;
			contact.putExtra(notesKey, notes);
		}
	}

	private String getExistingValue(String key, Intent contact) {
		String result = "";

		if (contact.hasExtra(key)) {
			result = contact.getStringExtra(key);
			result += "\n";
		}

		return result;
	}
}
