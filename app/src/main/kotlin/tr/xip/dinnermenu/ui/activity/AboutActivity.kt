package tr.xip.dinnermenu.ui.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_about.*
import kotlinx.android.synthetic.main.view_webview.view.*

import tr.xip.dinnermenu.R

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        /* App version */
        try {
            version.text = "v" + packageManager.getPackageInfo(packageName, 0).versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            version.text = "v?"
        }

        /* Feedback */
        val contanctDeveloper = findViewById(R.id.contactDeveloper)
        contanctDeveloper.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", "x.ihsan@gmail.com", null))
            val addresses = arrayOf("x.ihsan@gmail.com")
            intent.putExtra(Intent.EXTRA_EMAIL, addresses)
            startActivity(Intent.createChooser(intent, getString(R.string.title_contact_developer)))
        }

        /* Open source licenses dialog */
        openSourceLicenses.setOnClickListener {
            val view = layoutInflater.inflate(R.layout.view_webview, null)
            val dialog = AlertDialog.Builder(this)
                    .setTitle(R.string.title_open_source_licenses)
                    .setPositiveButton(R.string.action_ok, null)
                    .setView(view)
                    .create()
            view.webview.loadUrl("file:///android_asset/licenses.html")
            dialog.show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}