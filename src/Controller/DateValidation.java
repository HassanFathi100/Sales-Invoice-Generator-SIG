package Controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateValidation {
    public boolean dateValidation(String date){
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date tempDate = null;
        df.setLenient(false);

        try {
            tempDate = df.parse(date);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
