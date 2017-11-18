package test;

import model.Participant;
import model.PlannerApp;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ScriptTest {

    public static void main(String[] args) {

        PlannerApp planner = new PlannerApp();
        Scanner in = new Scanner(System.in);
        boolean stop = false;

        while (!stop) {
            System.out.println("Taper 1 : Tester un créneau");
            System.out.println("Taper 2 : Quitter");
            String num = in.next();

            if (num.equals("1")) {

                System.out.println("\nChoisissez les participants (numéros séparés par des virgules) : ");
                for(int i = 0; i < planner.getParticipants().size(); i++) {
                    System.out.println("\n" + i + ": " + planner.getParticipants().get(i).getName());
                }
                String participantsChoice = in.next();


                String[] participantsNumber = participantsChoice.split(",");
                ArrayList<Participant> participants = new ArrayList<>();

                for(int i = 0; i < participantsNumber.length; i++) {
                    participants.add(planner.getParticipants().get(Integer.parseInt(participantsNumber[i])));
                }

                System.out.println("\nChoisissez un creneau (yyyy/mm/dd-hh:mm) :");

                String dateString = in.next();

                DateFormat format = new SimpleDateFormat("yyyy/MM/dd-HH:mm");
                Date date = null;
                try {
                    date = format.parse(dateString);
                }catch (ParseException e) {
                    System.out.println(e.getMessage());
                }

                HashMap<Participant,String> map = planner.participantsStatus(participants,date);
                Iterator it = map.entrySet().iterator();
                while (it.hasNext()) {
                    HashMap.Entry<Participant,String> e = (HashMap.Entry)it.next();
                    System.out.println(e.getKey().getName() + " : " + e.getValue());
                    it.remove(); // avoids a ConcurrentModificationException
                }
                System.out.println("______________");


            }else if (num.equals("2")) {
                stop = true;
            }
        }
    }
}
