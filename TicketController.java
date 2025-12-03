
package com.train;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/train")
@CrossOrigin(origins = "*")
public class TicketController {

    @PostMapping("/book")
    public ResponseEntity<?> book(@RequestBody Map<String,Object> body) {
        try {
            String name = (String) body.getOrDefault("name", "Unknown");
            int age = ((Number) body.getOrDefault("age", 0)).intValue();
            String berth = (String) body.getOrDefault("berthPreference", "L");

            Passenger p = new Passenger(name, age, berth);
            String result = MainApp.bookTicket(p);
            Map<String,Object> resp = new HashMap<>();
            resp.put("message", result);
            resp.put("passengerId", p.passengerId);
            resp.put("status", p.allocated);
            resp.put("number", p.number);
            return ResponseEntity.ok(resp);
        } catch (Exception ex) {
            return ResponseEntity.status(500).body(Map.of("message", ex.getMessage()));
        }
    }

    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<?> cancel(@PathVariable int id) {
        try {
            if (!TicketBooker.passenger.containsKey(id)) {
                return ResponseEntity.status(400).body(Map.of("message", "No Passenger details available"));
            }
            MainApp.cancel(id);
            return ResponseEntity.ok(Map.of("message", "Cancelled"));
        } catch (Exception ex) {
            return ResponseEntity.status(500).body(Map.of("message", ex.getMessage()));
        }
    }

    @GetMapping("/available")
    public ResponseEntity<?> available() {
        try {
            Map<String,Integer> m = new HashMap<>();
            m.put("Lower", TicketBooker.availableLowerBerths);
            m.put("Middle", TicketBooker.availableMiddleBerths);
            m.put("Upper", TicketBooker.availableUpperBerths);
            m.put("RAC", TicketBooker.availableRACBerths);
            m.put("Waiting", TicketBooker.availableWaitingLists);
            return ResponseEntity.ok(m);
        } catch (Exception ex) {
            return ResponseEntity.status(500).body(Map.of("message", ex.getMessage()));
        }
    }

    @GetMapping("/booked")
    public ResponseEntity<?> booked() {
        try {
            return ResponseEntity.ok(TicketBooker.passenger.values());
        } catch (Exception ex) {
            return ResponseEntity.status(500).body(Map.of("message", ex.getMessage()));
        }
    }
}
