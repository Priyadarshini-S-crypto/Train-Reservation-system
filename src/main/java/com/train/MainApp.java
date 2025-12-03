
package com.train;

public class MainApp {

    // This is adapted from user's Main.bookTicket and cancel logic.
    public static void cancel(int pId) {
        TicketBooker booker = new TicketBooker();

        if(!TicketBooker.passenger.containsKey(pId)) {
            System.out.println("No Passenger details available");
        }
        else {
            booker.cancelTicket(pId);
        }
    }

    public static String bookTicket(Passenger p) {
        TicketBooker booker= new TicketBooker();

        if(TicketBooker.availableWaitingLists==0) {
            return "No tickets available";
        }

        if((p.berthPreference.equals("L")&& TicketBooker.availableLowerBerths>0) ||
           (p.berthPreference.equals("M")&& TicketBooker.availableMiddleBerths>0) ||
           (p.berthPreference.equals("U")&& TicketBooker.availableUpperBerths>0)){

               System.out.println("Preferred berth Available");

               if(p.berthPreference.equals("L")) {
                   System.out.println("Lower berth given");

                   booker.bookTicket(p,TicketBooker.LowerBerthPositions.get(0),"L");

                   TicketBooker.availableLowerBerths--;
                   TicketBooker.LowerBerthPositions.remove(0);

                   return "Lower berth given. Passenger ID: "+p.passengerId;
               }

               else if(p.berthPreference.equals("M")) {
                   System.out.println("Middle berth Given");

                   booker.bookTicket(p,TicketBooker.MiddleBerthPositions.get(0), "M");

                   TicketBooker.availableMiddleBerths--;
                   TicketBooker.MiddleBerthPositions.remove(0);

                   return "Middle berth given. Passenger ID: "+p.passengerId;
               }

               else if(p.berthPreference.equals("U")) {
                   System.out.println("Upper berth Given");

                   booker.bookTicket(p,TicketBooker.UpperBerthPositions.get(0), "U");

                   TicketBooker.availableUpperBerths--;
                   TicketBooker.UpperBerthPositions.remove(0);

                   return "Upper berth given. Passenger ID: "+p.passengerId;
               }

           }

        else if(TicketBooker.availableLowerBerths>0) {
             System.out.println("Lower berth given");

               booker.bookTicket(p,TicketBooker.LowerBerthPositions.get(0),"L");

               TicketBooker.availableLowerBerths--;
               TicketBooker.LowerBerthPositions.remove(0);
               return "Lower berth given. Passenger ID: "+p.passengerId;
        }

        else if(TicketBooker.availableMiddleBerths>0) {
             System.out.println("Middle berth given");

               booker.bookTicket(p,TicketBooker.MiddleBerthPositions.get(0),"M");

               TicketBooker.availableMiddleBerths--;
               TicketBooker.MiddleBerthPositions.remove(0);
               return "Middle berth given. Passenger ID: "+p.passengerId;
        }

        else if(TicketBooker.availableUpperBerths>0) {
            System.out.println("Upper berth Given");

               booker.bookTicket(p,TicketBooker.UpperBerthPositions.get(0), "U");

               TicketBooker.availableUpperBerths--;
               TicketBooker.UpperBerthPositions.remove(0);
               return "Upper berth given. Passenger ID: "+p.passengerId;
        }

        else if(TicketBooker.availableRACBerths>0) {
            System.out.println("RAC berth given");

            booker.RACbook(p,TicketBooker.RACBerthPositions.get(0),"RAC");

            TicketBooker.availableRACBerths--;
            TicketBooker.RACBerthPositions.remove(0);
            return "Added to RAC. Passenger ID: "+p.passengerId;
        }

        else if(TicketBooker.availableWaitingLists>0) {
            System.out.println("In waiting list");

            booker.waitingList(p,TicketBooker.WaitingListPositions.get(0),"Waiting List");

            TicketBooker.availableWaitingLists--;
            TicketBooker.WaitingListPositions.remove(0);
            return "Added to Waiting List. Passenger ID: "+p.passengerId;
        }
        return "";
    }
}
