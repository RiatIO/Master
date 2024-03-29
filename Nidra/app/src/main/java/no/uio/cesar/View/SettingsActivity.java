package no.uio.cesar.View;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.provider.Settings;
import android.view.MenuItem;

import java.util.Locale;

import no.uio.cesar.Model.User;
import no.uio.cesar.R;
import no.uio.cesar.ViewModel.UserViewModel;

public class SettingsActivity extends AppCompatPreferenceActivity {
    private static final String TAG = SettingsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // load settings fragment
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MainPreferenceFragment()).commit();
    }

    public static class MainPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_main);

            populateUserProfile();

            // feedback preference click listener
            Preference myPref = findPreference(getString(R.string.key_send_feedback));
            myPref.setOnPreferenceClickListener(preference -> {
                sendFeedback(getActivity());
                return true;
            });

            Preference storagePermission = findPreference(getString(R.string.key_permission_storage));
            storagePermission.setOnPreferenceClickListener(preference -> {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
                return true;
            });
        }

        private void populateUserProfile() {
            UserViewModel userViewModel = new UserViewModel(getActivity());
            User user = userViewModel.getUser();

            Preference name = findPreference(getString(R.string.key_profile_name));
            Preference age = findPreference(getString(R.string.key_profile_age));
            Preference gender = findPreference(getString(R.string.key_profile_gender));
            Preference height = findPreference(getString(R.string.key_profile_height));
            Preference weight = findPreference(getString(R.string.key_profile_weight));

            name.setSummary(user.getName());
            age.setSummary(Integer.toString(user.getAge()));
            gender.setSummary(user.getGender());
            height.setSummary(String.format(Locale.getDefault(), "%d cm", user.getHeight()));
            weight.setSummary(String.format(Locale.getDefault(), "%d kg", user.getWeight()));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Email client intent to send support mail
     * Appends the necessary device information to email body
     * useful when providing support
     */
    public static void sendFeedback(Context context) {
        String body = null;
        try {
            body = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            body = "\n\n-----------------------------\nPlease don't remove this information\n Device OS: Android \n Device OS version: " +
                    Build.VERSION.RELEASE + "\n App Version: " + body + "\n Device Brand: " + Build.BRAND +
                    "\n Device Model: " + Build.MODEL + "\n Device Manufacturer: " + Build.MANUFACTURER;
        } catch (PackageManager.NameNotFoundException ignored) { }

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"test@email.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Query from Nidra");
        intent.putExtra(Intent.EXTRA_TEXT, body);
        context.startActivity(Intent.createChooser(intent, context.getString(R.string.choose_email_client)));
    }
}