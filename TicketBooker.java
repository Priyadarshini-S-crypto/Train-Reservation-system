
package com.train;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class TicketBooker {

    static int availableLowerBerths=1;
    static int availableUpperBerths=1;
    static int availableMiddleBerths=1;
    static int availableRACBerths=1;
    static int availableWaitingLists=1;

    static Queue<Integer> RAClist = new LinkedList<Integer>();
    static Queue<Integer> WaitingLists = new LinkedList<Integer>();
    static List<Integer> BookedList = new ArrayList<Integer>();

    static List<Integer> LowerBerthPositions = new ArrayList<>(Arrays.asList(1));
    static List<Integer> UpperBerthPositions = new ArrayList<>(Arrays.asList(1));
    static List<Integer> MiddleBerthPositions = new ArrayList<>(Arrays.asList(1));
    static List<Integer> RACBerthPositions = new ArrayList<>(Arrays.asList(1));
    static List<Integer> WaitingListPositions = new ArrayList<>(Arrays.asList(1));

    static Map<Integer,Passenger> passenger = new HashMap<>();


    public void bookTicket(Passenger p, Integer berthinfo, String berth) {
        p.allocated=berth;
        p.number=berthinfo;

        passenger.put(p.passengerId, p);

        BookedList.add(p.passengerId);
        System.out.println("---Booked Successfully---");

    }


    public void RACbook(Passenger p, Integer berthinfo, String berth) {
        p.allocated=berth;
        p.number=berthinfo;

        passenger.put(p.passengerId, p);

        RAClist.add(p.passengerId);
        System.out.println("---added to RAC Successfully---");

    }


    public void waitingList(Passenger p, Integer berthinfo, String berth) {
        p.allocated=berth;
        p.number=berthinfo;

        passenger.put(p.passengerId, p);

        WaitingLists.add(p.passengerId);
        System.out.println("---added to waiting list---");

    }


    public void cancelTicket(int pId) {
        Passenger p = passenger.get(pId);

        passenger.remove(pId);

        BookedList.remove(Integer.valueOf(pId));

        int bookedPosition = p.number;

        System.out.println("----Cancellation done");

        if(p.allocated.equals("L")) {
            availableLowerBerths++;
            LowerBerthPositions.add(bookedPosition);
        }

        else if(p.allocated.equals("M")) {
            availableMiddleBerths++;
            MiddleBerthPositions.add(bookedPosition);
        }
        else if(p.allocated.equals("U")) {
            availableUpperBerths++;
            UpperBerthPositions.add(bookedPosition);
        }

        if(RAClist.size()>0) {
            Passenger passengerRac= passenger.get(RAClist.poll());
            int id = passengerRac.passengerId;

            RAClist.remove(id);
            RACBerthPositions.add(passengerRac.number);
            availableRACBerths++;

            if(WaitingLists.size()>0) {
                Passenger passengerWait = passenger.get(WaitingLists.poll());
                int wid = passengerWait.passengerId;

                WaitingLists.remove(wid);
                availableWaitingLists++;
                WaitingListPositions.add(passengerWait.number);

                passengerWait.number= RACBerthPositions.get(0);
                RACBerthPositions.remove(0);
                availableRACBerths--;
                RAClist.add(passengerWait.number);

            }
            MainApp.bookTicket(passengerRac);
        }
    }


    public static void availableTickets() {
        System.out.println("Available tickets in Lower Berth: "+availableLowerBerths);
        System.out.println("Available tickets in Middle Berth: "+availableMiddleBerths);
        System.out.println("Available tickets in Upper Berth: "+availableUpperBerths);
        System.out.println("Available tickets in RAC: "+availableRACBerths);
        System.out.println("Available tickets in Waiting list: "+availableWaitingLists);

    }


    public static void bookedTickets() {
        if(passenger.isEmpty()) {
            System.out.println("No Passenger details");
        }
        else {
            System.out.println("Passenger details who booked tickets: ");
            for(Passenger p: passenger.values()) {
                System.out.println("Passenger ID: "+p.passengerId);
                System.out.println("Passenger Name: "+p.name);
                System.out.println("Passenger Age: "+p.age);
                System.out.println("Status: "+p.allocated+" "+p.number);
            }
        }


    }

}
