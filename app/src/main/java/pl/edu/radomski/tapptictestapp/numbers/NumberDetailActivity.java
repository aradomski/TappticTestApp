package pl.edu.radomski.tapptictestapp.numbers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import pl.edu.radomski.tapptictestapp.BaseActivity;
import pl.edu.radomski.tapptictestapp.R;
import pl.edu.radomski.tapptictestapp.Utils;

/**
 * Created by radomskia on 01/06/2016.
 */
public class NumberDetailActivity extends BaseActivity {


    public static final String DETAIL_BACK_PRESSED = "detailBackPressed";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.number_detail_activity);

        if (!Utils.isNetworkConnectionAvailable(this)) {
            finish();
        }

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putString(NumberDetailFragment.ARG_NUMBER_NAME,
                    getIntent().getStringExtra(NumberDetailFragment.ARG_NUMBER_NAME));
            NumberDetailFragment fragment = new NumberDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction().add(R.id.number_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Intent intent = new Intent();
        intent.putExtra(DETAIL_BACK_PRESSED, false);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(DETAIL_BACK_PRESSED, true);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }
}
