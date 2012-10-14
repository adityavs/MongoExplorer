package com.innodroid.mongobrowser;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;

public class DocumentDetailActivity extends FragmentActivity implements DocumentDetailFragment.Callbacks {
	private String mCollectionName;
	
	private static final int REQUEST_EDIT_DOCUMENT = 101;
	
    @SuppressLint("NewApi")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_detail);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        	getActionBar().setDisplayHomeAsUpEnabled(true);
        
        mCollectionName = getIntent().getStringExtra(Constants.ARG_COLLECTION_NAME);
        
        if (savedInstanceState == null) {
            DocumentDetailFragment fragment = new DocumentDetailFragment();
            fragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().add(R.id.document_detail_container, fragment).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    		case android.R.id.home:
    			finish();
                return true;
    		default:
               	break;
    	}

        return super.onOptionsItemSelected(item);
    }

	@Override
	public void onEditDocument(int position, String content) {
    	Intent intent = new Intent(this, DocumentEditActivity.class);
    	intent.putExtra(Constants.ARG_DOCUMENT_CONTENT, content);
    	intent.putExtra(Constants.ARG_POSITION, position);
		intent.putExtra(Constants.ARG_COLLECTION_NAME, mCollectionName);
    	startActivityForResult(intent, REQUEST_EDIT_DOCUMENT);
	}
	
	@Override
	protected void onActivityResult(int request, int result, Intent data) {
		if (request == REQUEST_EDIT_DOCUMENT && result == RESULT_OK) {
			DocumentDetailFragment fragment = (DocumentDetailFragment)getSupportFragmentManager().findFragmentById(R.id.document_detail_container);
			fragment.updateContent(data.getStringExtra(Constants.ARG_DOCUMENT_CONTENT));
		}
		else {
			super.onActivityResult(request, result, data);
		}
	}
}
