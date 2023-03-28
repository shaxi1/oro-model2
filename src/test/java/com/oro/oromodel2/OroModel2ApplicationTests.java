package com.oro.oromodel2;

import com.oro.oromodel2.enums.TicketType;
import com.oro.oromodel2.model.*;
import com.oro.oromodel2.repository.*;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@SpringBootTest
class OroModel2ApplicationTests {
    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    HallRepository hallRepository;

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    ScreeningRepository screeningRepository;

    @Autowired
    ClientRepository clientRepository;

    @BeforeEach
    void clearDatabase() {
        ticketRepository.deleteAll();
        reservationRepository.deleteAll();
        screeningRepository.deleteAll();
        movieRepository.deleteAll();
        hallRepository.deleteAll();
        clientRepository.deleteAll();
    }

    @Test
    void moviesScreeningsInHall1() {
        Hall hall1 = new Hall();
        hall1.setName("Hall 1");
        long hall1Id = hallRepository.save(hall1).getId();
        Hall hall2 = new Hall();
        hall2.setName("Hall 2");
        long hall2id = hallRepository.save(hall2).getId();

        Movie movie1 = new Movie();
        movie1.setTitle("Avatar");
        movie1.setDuration(120);
        Movie movie2 = new Movie();
        movie2.setTitle("Titanic");
        movie2.setDuration(180);
        Movie movie3 = new Movie();
        movie3.setTitle("The Matrix");
        movie3.setDuration(150);

        movieRepository.save(movie1);
        movieRepository.save(movie2);
        movieRepository.save(movie3);

        /* movies played in Hall 1 */
        Screening screening1 = new Screening();
        screening1.setHall(hall1);
        screening1.setMovie(movie1);
        Screening screening2 = new Screening();
        screening2.setHall(hall1);
        screening2.setMovie(movie2);
        Screening screening5 = new Screening();
        screening5.setHall(hall1);
        screening5.setMovie(movie3);

        /* movies played in Hall 2 */
        Screening screening3 = new Screening();
        screening3.setHall(hall2);
        screening3.setMovie(movie2);
        Screening screening4 = new Screening();
        screening4.setHall(hall2);
        screening4.setMovie(movie3);

        screeningRepository.save(screening1);
        screeningRepository.save(screening2);
        screeningRepository.save(screening3);
        screeningRepository.save(screening4);
        screeningRepository.save(screening5);

        /* movies played in Hall 1 */ // 1.
        log.info("Movies in Hall 1: {}", hallRepository.findAllMoviesInHall(hall1Id));
        List<Movie> moviesInHall1 = hallRepository.findAllMoviesInHall(hall1Id);
        assertEquals(3, moviesInHall1.size());

        /* screenings in Hall 1 */ // 2.
        List<Screening> screeningsInHall1 = hallRepository.findAllScreeningsInHall(hall1Id);
        log.info("Screenings in Hall 1: {}", screeningsInHall1);
        assertEquals(3, screeningsInHall1.size());

        /* screenings for movie Avatar (movie by id) */ // 3.
        List<Screening> screeningsForMovie1 = movieRepository.findAllScreeningsForMovieById(movie1.getId());
        log.info("Screenings for movie Avatar: {}", screeningsForMovie1);
        assertEquals(1, screeningsForMovie1.size());

        /* screenings for movie Titanic (movie by title) */ // 4.
        List<Screening> screeningsForMovie2 = movieRepository.findAllScreeningsForMovieByName(movie2.getTitle());
        log.info("Screenings for movie Titanic: {}", screeningsForMovie2);
        assertEquals(2, screeningsForMovie2.size());

        /* reservations for screening by screening id */ // 5.
        Reservation reservation1 = new Reservation();
        reservation1.setScreening(screening1);
        reservation1.setSeatNumber(1);
        LocalDateTime date = LocalDateTime.now();
        reservation1.setReservationDate(date);
        reservationRepository.save(reservation1);
        // assert
        List<Reservation> reservationsForScreening1 = screeningRepository.findAllReservationsForScreeningById(screening1.getId());
        log.info("Reservations for screening 1: {}", reservationsForScreening1);
        assertEquals(1, reservationsForScreening1.size());

        /* screenings for client1 by client id */ // 6.
        Reservation reservation2 = new Reservation();
        LocalDateTime date2 = LocalDateTime.now().plusDays(2);
        reservation2.setScreening(screening2);
        reservation2.setSeatNumber(2);
        reservationRepository.save(reservation2);

        Client client1 = new Client();
        client1.setName("John");
        client1.setSurname("Smith");
        client1.setLogin("johnsmith");
        client1.setTicketType(TicketType.STUDENT);
        clientRepository.save(client1);
        Client client2 = new Client();
        client2.setName("Jane");
        client2.setSurname("Smith");
        client2.setLogin("janesmith");
        client2.setTicketType(TicketType.ADULT);
        clientRepository.save(client2);

        /* 2 reservations for John Smith */
        reservation1.setClient(client1);
        reservation2.setClient(client1);
        reservationRepository.save(reservation1);
        reservationRepository.save(reservation2);

        // assert
        List<Screening> screeningsForClient1 = clientRepository.findAllScreeningsByClientId(client1.getId());
        log.info("Screenings for client 1: {}", screeningsForClient1);
        assertEquals(2, screeningsForClient1.size());

        /* screenings for client1 by client login */ // 7.
        List<Screening> screeningsForClient1ByLogin = clientRepository.findAllScreeningsByClientLogin(client1.getLogin());
        log.info("Screenings for client 1 by login: {}", screeningsForClient1ByLogin);
        assertEquals(2, screeningsForClient1ByLogin.size());

        /* number of seats taken for a specific movie screening */ // 8.
        // get all screenings for movie Titanic
        List<Screening> screeningsForTitanic = movieRepository.findAllScreeningsForMovieByName(movie2.getTitle());
        // count seats take for screening number 1
        int seatsTakenForScreening1 = reservationRepository.countSeatsTakenForScreening(screeningsForTitanic.get(0).getId());
        log.info("Seats taken for screening 1: {}", seatsTakenForScreening1);
        assertEquals(1, seatsTakenForScreening1);

        /* count all halls that Titanic was played in */ // 9.
        int hallsForTitanic = movieRepository.countHallsForMovie(movie2.getId());
        log.info("Halls for Titanic: {}", hallsForTitanic);
        assertEquals(2, hallsForTitanic);

        /* number of seats bought by a client between certain date period */ // 10.
        LocalDateTime dateFrom = LocalDateTime.now().minusDays(1);
        LocalDateTime dateTo = LocalDateTime.now().plusDays(1); // this date excludes reservation for 2 days from now
        int seatsBoughtByClient1 = reservationRepository.countSeatsBoughtByUserBetweenDates(client1.getId(), dateFrom, dateTo);
        log.info("Seats bought by client 1: {}", seatsBoughtByClient1);
        assertEquals(1, seatsBoughtByClient1);
    }

}
