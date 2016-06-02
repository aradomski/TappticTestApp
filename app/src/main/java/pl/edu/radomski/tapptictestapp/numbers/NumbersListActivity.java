package pl.edu.radomski.tapptictestapp.numbers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import pl.edu.radomski.tapptictestapp.BaseActivity;
import pl.edu.radomski.tapptictestapp.R;
import pl.edu.radomski.tapptictestapp.Utils;
import pl.edu.radomski.tapptictestapp.numbers.data.NumberModel;
import pl.edu.radomski.tapptictestapp.numbers.dataprovider.ListDownloadLoader;
import pl.edu.radomski.tapptictestapp.numbers.dataprovider.Response;

/**
 * Created by radomskia on 01/06/2016.
 */
public class NumbersListActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<Response<List<NumberModel>>> {


    private static final int LIST_LOAD_LOADER_ID = 234;
    private static final String SELECTED_NUMBER_MODEL = "selectedNumberModel";
    public static final int REQUEST_CODE = 1213;

    private NumbersAdapter numbersAdapter;
    private boolean hasTwoPanels;
    private NumberModel selectedNumberModel;
    private NumbersAdapter.OnElementClicked onElementClicked = new NumbersAdapter.OnElementClicked() {
        @Override
        public void onElementClicked(NumberModel numberModel) {
            onElementSelected(numberModel);
        }
    };
    private Bundle savedInstanceState;
    private Button retryButton;
    private View progress;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.numbers_list_activity);

        this.savedInstanceState = savedInstanceState;


        RecyclerView numbersRecyclerView = (RecyclerView) findViewById(R.id.numbers_list);
        View numberDetailContainer = findViewById(R.id.number_detail_container);
        retryButton = (Button) findViewById(R.id.retry);
        progress = findViewById(R.id.progress);
        hasTwoPanels = numberDetailContainer != null;

        numbersAdapter = new NumbersAdapter(onElementClicked);
        assert numbersRecyclerView != null;
        numbersRecyclerView.setAdapter(numbersAdapter);

        if (savedInstanceState != null
                && savedInstanceState.containsKey(SELECTED_NUMBER_MODEL)) {
            selectedNumberModel = savedInstanceState.getParcelable(SELECTED_NUMBER_MODEL);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Utils.isNetworkConnectionAvailable(NumbersListActivity.this)) {
            getSupportLoaderManager().initLoader(LIST_LOAD_LOADER_ID, null, this)
                    .forceLoad();
            if (selectedNumberModel != null) {
                onElementSelected(selectedNumberModel);
            }
        } else {
            displayRetryButton();
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(SELECTED_NUMBER_MODEL, selectedNumberModel);
    }

    private void onElementSelected(NumberModel numberModel) {
        selectedNumberModel = numberModel;
        if (hasTwoPanels && numberModel != null) {
            Bundle arguments = new Bundle();
            arguments.putString(NumberDetailFragment.ARG_NUMBER_NAME, numberModel.getName());
            NumberDetailFragment fragment = new NumberDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.number_detail_container, fragment)
                    .commit();
        } else if (numberModel != null) {
            Intent intent = new Intent(NumbersListActivity.this, NumberDetailActivity.class);
            intent.putExtra(NumberDetailFragment.ARG_NUMBER_NAME, numberModel.getName());
            startActivityForResult(intent, REQUEST_CODE);
        }
    }

    @Override
    public Loader<Response<List<NumberModel>>> onCreateLoader(int id, Bundle args) {
        progress.setVisibility(View.VISIBLE);
        return new ListDownloadLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<Response<List<NumberModel>>> loader, Response<List<NumberModel>> data) {
        progress.setVisibility(View.GONE);
        if (data.getException() == null) {
            numbersAdapter.setNumberModels(data.getData());
            numbersAdapter.setSelectedElement(selectedNumberModel);
        } else {
            Toast.makeText(NumbersListActivity.this, data.getException().getMessage(), Toast.LENGTH_SHORT).show();
            data.getException().printStackTrace();
            displayRetryButton();
        }
    }

    @Override
    public void onLoaderReset(Loader<Response<List<NumberModel>>> loader) {

    }

    private void displayRetryButton() {
        retryButton.setVisibility(View.VISIBLE);
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isNetworkConnectionAvailable(NumbersListActivity.this)) {
                    getSupportLoaderManager().restartLoader(LIST_LOAD_LOADER_ID, null, NumbersListActivity.this)
                            .forceLoad();
                    retryButton.setVisibility(View.GONE);
                } else {
                    Toast.makeText(NumbersListActivity.this, R.string.no_network, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && data != null) {
            if (data.getBooleanExtra(NumberDetailActivity.DETAIL_BACK_PRESSED, false)) {
                selectedNumberModel = null;
                numbersAdapter.setSelectedElement(null);
            }
        }
    }

}
