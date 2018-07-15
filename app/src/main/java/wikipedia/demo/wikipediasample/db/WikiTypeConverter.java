package wikipedia.demo.wikipediasample.db;

import android.arch.persistence.room.TypeConverter;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class WikiTypeConverter {

    @TypeConverter
    public static String intListToString(List<Integer> ints) {
        return TextUtils.join(",", ints);
    }

    @TypeConverter
    public static List<Integer> stringToIntList(String data) {
        List<Integer> res = new ArrayList<>();
        for (String d : data.split(",")) {
            res.add(Integer.parseInt(d));
        }
        return res;
    }

    @TypeConverter
    public static String stringListToString(List<String> strings) {
        return TextUtils.join(",", strings);
    }

    @TypeConverter
    public static List<String> stringsToStringList(String data){
        List<String> res = new ArrayList<>();
        for(String d: data.split(",")){
            res.add(d);
        }
        return res;
    }

}
