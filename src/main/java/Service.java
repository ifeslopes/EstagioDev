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
public class Service {
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

    private  static Sheets GetInformation() throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        return service;
    }

    public static void UpdateInformation() throws GeneralSecurityException, IOException {


        Myfunction myfunction =new Myfunction();

        Sheets service = GetInformation();

        final String spreadsheetId = "1uff5wJRRA0UtEf_GmBc78ohl9Nc90cfHecbufAdynIs";
        final String range = "engenharia_de_software!B4:F27";

        int num=4;
        Integer classLogTotal=0;


        ValueRange classLog = service.spreadsheets().values().get(spreadsheetId, "engenharia_de_software!A2").execute();

        ValueRange result = service.spreadsheets().values().get(spreadsheetId, range).execute();
        List<List<Object>> values = result.getValues();




        String[] array= classLog.getValues().get(0).get(0).toString().split(" ");
        classLogTotal =Integer.parseInt(array[array.length-1]);


        for(List row : values) {


            double media =myfunction.MediaConverter(""+row.get(2),""+row.get(3), ""+row.get(4));

            if ((media < 5)) {

                myfunction.InsertSpreadsheet(spreadsheetId, service, "Reprovado por nota", "G" +num);
                myfunction.InsertSpreadsheet(spreadsheetId, service, "0", "H" +num);
            }
            else if (media<7){


                myfunction.InsertSpreadsheet(spreadsheetId, service, "Exame Final", "G" +num);
                myfunction.InsertSpreadsheet(spreadsheetId, service, ""+myfunction.PointsForPassing(media), "H" +num);

            }
            else {

                myfunction.InsertSpreadsheet(spreadsheetId, service, "Aprovado", "G" +num);
                myfunction.InsertSpreadsheet(spreadsheetId, service, "0", "H" +num);

            }

            if(classLogTotal * 0.25 < Integer.parseInt(""+row.get(1))){

                myfunction.InsertSpreadsheet(spreadsheetId, service, "Reprovado por Falta", "G" +num);


            }
            num++;


        }
    }

    public static  void  ShowInformartion() throws GeneralSecurityException, IOException {
        Sheets service = GetInformation();

        final String spreadsheetId = "1uff5wJRRA0UtEf_GmBc78ohl9Nc90cfHecbufAdynIs";
        final String range = "engenharia_de_software!A3:H27";
        ValueRange result = service.spreadsheets().values().get(spreadsheetId, range).execute();

        List<List<Object>> values = result.getValues();
        System.out.println("-----------------------------------------------------------------------------");
        System.out.printf("%5s %7s %10s %5s %5s %5s %12s %30s \n", values.get(0).get(0), values.get(0).get(1),values.get(0).get(2),
                values.get(0).get(3),values.get(0).get(4),values.get(0).get(5), values.get(0).get(6), values.get(0).get(7));
        System.out.println();
        System.out.println("-----------------------------------------------------------------------------");

        for(List row : values) {

            System.out.printf(row.get(0).equals("Matricula")?"":
                    "%5s %12s %5s %5s %5s %5s %20s %10s \n",
                    row.get(0), row.get(1),row.get(2),row.get(3),row.get(4), row.get(5), row.get(6),row.get(7));


        }
        System.out.println("-----------------------------------------------------------------------------");


    }

}
