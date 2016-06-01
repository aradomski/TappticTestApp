package pl.edu.radomski.tapptictestapp.numbers;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import pl.edu.radomski.tapptictestapp.databinding.NumberItemViewBinding;

/**
 * Created by radomskia on 01/06/2016.
 */
public class NumbersViewHolder extends RecyclerView.ViewHolder {


    private final NumberItemViewBinding viewBinding;

    public NumbersViewHolder(View itemView) {
        super(itemView);
        viewBinding = NumberItemViewBinding.bind(itemView);
    }

    public NumberItemViewBinding getViewBinding() {
        return viewBinding;
    }
}
