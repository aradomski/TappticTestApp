package pl.edu.radomski.tapptictestapp.numbers.data;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import pl.edu.radomski.tapptictestapp.R;

/**
 * Created by radomskia on 01/06/2016.
 */
public class NumberDetailModel {
    private String name;
    private String text;
    private String image;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }


    @BindingAdapter({"bind:image"})
    public static void loadImage(ImageView view, String image) {
        Picasso.with(view.getContext())
                .load(image)
                .placeholder(R.mipmap.ic_launcher)
                .into(view);
    }
}
