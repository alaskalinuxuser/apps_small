/*  Copyright 2017 by AlaskaLinuxUser (https://thealaskalinuxuser.wordpress.com)
*
*   Licensed under the Apache License, Version 2.0 (the "License");
*   you may not use this file except in compliance with the License.
*   You may obtain a copy of the License at
*
*       http://www.apache.org/licenses/LICENSE-2.0
*
*   Unless required by applicable law or agreed to in writing, software
*   distributed under the License is distributed on an "AS IS" BASIS,
*   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*   See the License for the specific language governing permissions and
*   limitations under the License.
*/
package com.alaskalinuxuser.webviewsfordummies;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    // Declare your webview ...
    WebView myWebView;
    // Declare our boolean for switching.
    Boolean webViewTrue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set our boolean to true, as we are displaying a website first.
        webViewTrue = true;

        // To identify my webview that I am going to put the website or data on.
        myWebView = (WebView)findViewById(R.id.myWebView);

        // To enable javascript.
        myWebView.getSettings().setJavaScriptEnabled(true);

        // To keep it here in our mini webview browser
        myWebView.setWebViewClient(new WebViewClient());

        // To load which webpage, in this case, mine.
        myWebView.loadUrl("https://thealaskalinuxuser.wordpress.com");

        // So, by clicking on the FAB, you can swap between webview and dataview.
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Switched between webview and dataview.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                // If we are in webview mode.
                if (webViewTrue) {

                    // Then switch to dataview mode.
                    webViewTrue = false;

                    // You can load any data you make, you just need data, type, and encoding.
                    String myEncoding = "UTF-8";
                    String myType = "text/html";
                    String myData = "<html><body><h1>Check this out!</h1><p>This is just demonstrating how to load your own 'data'.</p><h2>More info!</h2><p>Feel free to test this out with some code edits.</p></body></html>";

                    // And pass that to our webview.
                    myWebView.loadData(myData, myType, myEncoding);

                    // Else, if you are in dataview, switch to web view.
                } else {

                    // Set our boolean back to true.
                    webViewTrue = true;

                    // To load which webpage, in this case, mine.
                    myWebView.loadUrl("https://thealaskalinuxuser.wordpress.com");

                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
