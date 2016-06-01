package pl.edu.radomski.tapptictestapp.numbers.dataprovider;

import android.content.Context;

import org.json.JSONObject;

import pl.edu.radomski.tapptictestapp.numbers.data.NumberDetailModel;

/**
 * Created by radomskia on 01/06/2016.
 */
public class DetailDownloadLoader extends NetworkLoader<NumberDetailModel> {

    private static final String NAME = "name";
    private static final String TEXT = "text";
    private static final String IMAGE = "image";
    private static final String URL = "http://dev.tapptic.com/test/json.php?name=";
    private String selectedName;

    public DetailDownloadLoader(Context context, String selectedName) {
        super(context);
        this.selectedName = selectedName;
    }

    @Override
    protected String getUrl() {
        return URL + selectedName;
    }

    @Override
    protected NumberDetailModel parse(String s) throws Exception {
        NumberDetailModel numberDetailModel = new NumberDetailModel();

        JSONObject jsonObject = new JSONObject(s);
        numberDetailModel.setName(jsonObject.getString(NAME));
        numberDetailModel.setText(jsonObject.getString(TEXT));
        numberDetailModel.setImage(jsonObject.getString(IMAGE));

        return numberDetailModel;
    }
}
