import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.List;


public class Main {

    public static void main(String[] args) {
        try {
            File file = new File("data/tickets.json");

            ObjectMapper mapper = new ObjectMapper();
            TicketWrapper ticketWrapper = mapper.readValue(file, TicketWrapper.class);
            Ticket[] ticketArray = ticketWrapper.getTickets();

            JsonProcessor jsonProcessor = new JsonProcessor();
            List<Integer> list = jsonProcessor.getTimeOfFly(ticketArray);

            System.out.println("Среднее время перелета Владивосток - Тель-Авив составляет: " + jsonProcessor.getAverageTimeOfFly(list) + " минут");
            System.out.println("90-ый процентиль времени полета составляет: " + jsonProcessor.getPercentile(list, 90));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
