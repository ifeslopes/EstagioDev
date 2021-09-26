import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Scanner;


public class SheetAndJava {

    public static void main(String... args) throws GeneralSecurityException, IOException, InterruptedException {

        Scanner sc =new Scanner(System.in);
        Service service =new Service();

        String number="";
        boolean menu =true;
        boolean view= false;
        System.out.println("=========== Bem Vindo Sistema De Notas ==============");
        while (menu){
            System.out.println("Aperte o 1 para para cualcular as notas  e salvar online");
            System.out.print((view)?"Aperte o 2 para visualizar os dados dos alunos \n":"");
            System.out.println("Aperte o 0 para  sair: ");


            number = sc.nextLine();
           if(number.equals("1")){
               service.UpdateInformation();
               view = true;
           }
            else if(number.equals("0")){
                menu =false;
                System.out.println("Sair do programa");
            }
            else if(number.equals("2")){
               service.ShowInformartion();
           }else {
               System.out.println("opção escolhida não e valida aperte enter para retornar menu");
               sc.nextLine();
           }

        }



        //service.ShowInformartion();




    }
}