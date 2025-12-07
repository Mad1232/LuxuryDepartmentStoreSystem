package service;

import model.Reservation;
import util.FileHandler;
import java.util.*;

public class ReservationService {

    private List<Reservation> reservations = new ArrayList<>();
    private int nextId = 1;
    private static final String FILE_PATH = "data/reservations.txt";

    public ReservationService() {
        reservations = FileHandler.readReservations(FILE_PATH);
        // Update nextId so new reservations don't conflict
        for (Reservation r : reservations) {
            if (r.getId() >= nextId) {
                nextId = r.getId() + 1;
            }
        }
    }

    public void addReservation(Reservation r) {
        reservations.add(r);
        FileHandler.writeLine(FILE_PATH, r.toString()); // save to file
        nextId++;
    }

    public Reservation createReservation(int productId, String customerName) {
        String today = java.time.LocalDate.now().toString();
        Reservation r = new Reservation(nextId, productId, customerName, today);
        addReservation(r);
        return r;
    }

    public List<Reservation> getReservationsForProduct(int productId) {
        List<Reservation> list = new ArrayList<>();
        for (Reservation r : reservations) {
            if (r.getProductId() == productId) list.add(r);
        }
        return list;
    }

    public List<Reservation> getAllReservations() {
        return reservations;
    }
}

