package pl.edu.radomski.tapptictestapp.numbers.dataprovider;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pl.edu.radomski.tapptictestapp.numbers.data.NumberModel;

/**
 * Created by radomskia on 01/06/2016.
 */
public class ListDownloadLoader extends NetworkLoader<List<NumberModel>> {

    private static final String TAG_IMAGE = "image";
    private static final String TAG_NAME = "name";
    private static final String URL = "http://dev.tapptic.com/test/json.php";

    public ListDownloadLoader(Context context) {
        super(context);
    }


    @Override
    protected String getUrl() {
        return URL;
    }

    @Override
    protected List<NumberModel> parse(String s) throws Exception {
        ArrayList<NumberModel> numberModels = new ArrayList<>();
        NumberModel numberModel;
        JSONArray jsonArray = new JSONArray(s);
        JSONObject numberJsonObject;
        for (int i = 0; i < jsonArray.length(); i++) {
            numberJsonObject = jsonArray.getJSONObject(i);
            numberModel = new NumberModel();

            numberModel.setName(numberJsonObject.getString(TAG_NAME));
            numberModel.setImage(numberJsonObject.getString(TAG_IMAGE));
            numberModels.add(numberModel);
        }

        return numberModels;
    }
}
