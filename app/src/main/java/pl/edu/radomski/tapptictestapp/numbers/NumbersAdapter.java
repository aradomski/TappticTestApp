package pl.edu.radomski.tapptictestapp.numbers;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import pl.edu.radomski.tapptictestapp.R;
import pl.edu.radomski.tapptictestapp.numbers.data.NumberModel;

/**
 * Created by radomskia on 01/06/2016.
 */
public class NumbersAdapter extends RecyclerView.Adapter<NumbersViewHolder> {
    private List<NumberModel> numberModels = new ArrayList<>();
    private OnElementClicked onElementClicked;
    private int selectedPosition = -1;


    public interface OnElementClicked {
        void onElementClicked(NumberModel numberModel);
    }

    public NumbersAdapter(@NonNull OnElementClicked onElementClicked) {
        this.onElementClicked = onElementClicked;
    }

    public void setNumberModels(List<NumberModel> numberModels) {
        this.numberModels = numberModels;
        notifyDataSetChanged();
    }

    @Override
    public NumbersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.number_item_view, parent, false);
        NumbersViewHolder holder = new NumbersViewHolder(v);
        return holder;
    }


    @Override
    public void onBindViewHolder(final NumbersViewHolder holder, final int position) {
        final NumberModel numberModel = numberModels.get(position);
        holder.getViewBinding().setNumber(numberModel);

        if (selectedPosition == position) {
            holder.getViewBinding().numberItemContainer.setSelected(true);
        } else {
            holder.getViewBinding().numberItemContainer.setSelected(false);
        }

        holder.getViewBinding().numberItemContainer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                if (selectedPosition >= 0) {
                    int lastSelectedPosition = selectedPosition;
                    selectedPosition = holder.getAdapterPosition();
                    notifyItemChanged(lastSelectedPosition);
                }


                onElementClicked.onElementClicked(numberModel);
                holder.getViewBinding().numberItemContainer.setSelected(true);

                if (selectedPosition == -1) {
                    selectedPosition = holder.getAdapterPosition();
                }


            }
        });
        holder.getViewBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return numberModels.size();
    }

    public void setSelectedElement(NumberModel selectedNumberModel) {
        if (selectedNumberModel != null) {
            for (int i = 0; i < numberModels.size(); i++) {
                NumberModel numberModel = numberModels.get(i);
                if (numberModel.equals(selectedNumberModel)) {
                    selectedPosition = i;
                    notifyItemChanged(selectedPosition);
                    break;
                }
            }
        }else{
            notifyItemChanged(selectedPosition);
            selectedPosition=-1;
        }
    }
}
