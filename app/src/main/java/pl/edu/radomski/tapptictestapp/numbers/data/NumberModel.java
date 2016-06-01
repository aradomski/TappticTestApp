package pl.edu.radomski.tapptictestapp.numbers.data;

import android.databinding.BindingAdapter;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import pl.edu.radomski.tapptictestapp.R;

/**
 * Created by radomskia on 01/06/2016.
 */
public class NumberModel implements Parcelable {
    private String name;
    private String image;

    public NumberModel() {
    }

    protected NumberModel(Parcel in) {
        name = in.readString();
        image = in.readString();
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NumberModel)) return false;

        NumberModel that = (NumberModel) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return image != null ? image.equals(that.image) : that.image == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (image != null ? image.hashCode() : 0);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(image);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<NumberModel> CREATOR = new Parcelable.Creator<NumberModel>() {
        @Override
        public NumberModel createFromParcel(Parcel in) {
            return new NumberModel(in);
        }

        @Override
        public NumberModel[] newArray(int size) {
            return new NumberModel[size];
        }
    };
}