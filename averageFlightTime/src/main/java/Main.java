import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Stream;

public class Main {

    private static final String PATH = "data/tickets.json";

    public static void main(String[] args) {
        try {
            StringBuilder stringBuilder = new StringBuilder();

            try (
                    InputStream inputStream = Main.class.getResourceAsStream(PATH);
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

                Stream<String> lines = bufferedReader.lines();
                lines.forEach(l -> stringBuilder.append(l).append("\n"));
            }

            ObjectMapper mapper = new ObjectMapper();
            TicketWrapper ticketWrapper = mapper.readValue(stringBuilder.toString().replaceAll("\uFEFF", ""), TicketWrapper.class);
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
