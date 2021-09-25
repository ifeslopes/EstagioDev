import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;

import com.google.api.services.sheets.v4.model.ValueRange;


import java.io.*;

import java.security.GeneralSecurityException;
import java.util.*;

public class SheetAndJava {
    private static final String APPLICATION_NAME = "Engenharia de Software - Desafio Leonardo Lopes";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);


    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";


    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = SheetAndJava.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }





    public static void main(String... args) throws IOException, GeneralSecurityException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

        Myfunction myfunction =new Myfunction();

        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        final String spreadsheetId = "1uff5wJRRA0UtEf_GmBc78ohl9Nc90cfHecbufAdynIs";
        final String range = "engenharia_de_software!B4:F24";

        int num=4;
        Integer classLogTotal=0;


        ValueRange classLog = service.spreadsheets().values().get(spreadsheetId, "engenharia_de_software!A2").execute();

        ValueRange result = service.spreadsheets().values().get(spreadsheetId, range).execute();
        List<List<Object>> values = result.getValues();




        String[] array= classLog.getValues().get(0).get(0).toString().split(" ");
        classLogTotal =Integer.parseInt(array[array.length-1]);


        for(List row : values) {

            System.out.print("Nome: "+row.get(0)+" = Faltas: "+row.get(1)+" media: ");
            double media =myfunction.MediaConverter(""+row.get(2),""+row.get(3), ""+row.get(4));

            if ((media < 5)) {
                System.out.print("Nome: "+row.get(0)+" = Faltas: "+row.get(1)+" media: ");
                System.out.println(media);
                myfunction.InsertSpreadsheet(spreadsheetId, service, "Reprovado por nota", "G" +num);
                myfunction.InsertSpreadsheet(spreadsheetId, service, "0", "H" +num);
            }
            else if (media<7){
                System.out.print("Nome: "+row.get(0)+" = Faltas: "+row.get(1)+" media: ");
                System.out.println(media);
                System.out.println("nota para aprovação !!!!! "+myfunction.PointsForPassing(media));
                //InsertSpreadsheet(spreadsheetId, service, "Exame Final", "" + row.get(5));
            }
            else {
                System.out.print("Nome: "+row.get(0)+" = Faltas: "+row.get(1)+" media: ");
                System.out.println(media);
               // InsertSpreadsheet(spreadsheetId, service, "Aprovado", "" + row.get(5));
            }

            if(classLogTotal * 0.25 < Integer.parseInt(""+row.get(1))){
                System.out.print("Nome: "+row.get(0)+" = Faltas: "+row.get(1)+" media: ");
                System.out.println("Reprovado por falta quantidade de faltas= "+row.get(1));
                System.out.println(classLogTotal * 0.25 );

                }
            num++;

            //System.out.println(media);
        }




    }
}