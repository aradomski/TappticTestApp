package pl.edu.radomski.tapptictestapp.numbers;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import pl.edu.radomski.tapptictestapp.BaseFragment;
import pl.edu.radomski.tapptictestapp.R;
import pl.edu.radomski.tapptictestapp.Utils;
import pl.edu.radomski.tapptictestapp.databinding.NumberDetailFragmentBinding;
import pl.edu.radomski.tapptictestapp.numbers.data.NumberDetailModel;
import pl.edu.radomski.tapptictestapp.numbers.dataprovider.DetailDownloadLoader;
import pl.edu.radomski.tapptictestapp.numbers.dataprovider.Response;


/**
 * Created by radomskia on 01/06/2016.
 */
public class NumberDetailFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<Response<NumberDetailModel>> {
    public static final String ARG_NUMBER_NAME = "numberName";
    public static final int DETAIL_LOADER_ID = 123;
    private NumberDetailFragmentBinding dataBinding;
    private String numberName;
    private Loader<Response<NumberDetailModel>> numberDetailModelLoader;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.number_detail_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dataBinding = DataBindingUtil.bind(view);
        if (getArguments().containsKey(ARG_NUMBER_NAME)) {
            numberName = getArguments().getString(ARG_NUMBER_NAME);
            numberDetailModelLoader = getActivity().getSupportLoaderManager().restartLoader(DETAIL_LOADER_ID, null, this);
            numberDetailModelLoader.forceLoad();
        }
    }


    @Override
    public Loader<Response<NumberDetailModel>> onCreateLoader(int id, Bundle args) {
        dataBinding.progress.setVisibility(View.VISIBLE);
        return new DetailDownloadLoader(getContext(), numberName);
    }

    @Override
    public void onLoadFinished(Loader<Response<NumberDetailModel>> loader, Response<NumberDetailModel> data) {
        dataBinding.progress.setVisibility(View.GONE);
        if (data.getException() == null && dataBinding != null) {
            dataBinding.setNumberDetail(data.getData());
            dataBinding.executePendingBindings();
        } else {
            if (data.getException() != null) {
                if (Utils.isNetworkConnectionAvailable(getContext())) {
                    Toast.makeText(getContext(), R.string.error_during_loading_detail, Toast.LENGTH_SHORT).show();
                    data.getException().printStackTrace();
                }
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Response<NumberDetailModel>> loader) {

    }

}
