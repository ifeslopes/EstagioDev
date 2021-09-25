import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.IOException;
import java.util.Arrays;

public class Myfunction {

    public static Double MediaConverter(String... note){

        double sum = 0;
        for (String number :note) {
            sum += Double.parseDouble(number);
        }
        return sum / note.length /10;

    }

    public   static void InsertSpreadsheet(String spreadsheetId, Sheets service, String situation,
                                           String cells ) throws IOException {

        ValueRange body = new ValueRange()
                .setValues(
                        Arrays.asList(Arrays.asList(situation)

                        ));
        UpdateValuesResponse resultup = service.spreadsheets()
                .values().update(spreadsheetId, "engenharia_de_software!"+cells, body)
                .setValueInputOption("RAW")
                .execute();
    }

    public static double PointsForPassing(double med){

        double naf = 0;
        while (!(5<=(med+naf)/2)){
            naf+=0.1;

        }
        return Math.ceil(naf);
    }
}
