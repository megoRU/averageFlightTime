import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class JsonProcessor {

    private final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yy HH:mm");

    public List<Integer> getTimeOfFly(Ticket[] ticketArray) {
        List<Integer> timeArrayMinutes = new ArrayList<>();

        try {
            for (Ticket ticket : ticketArray) {
                String departureDateTime = ticket.getDeparture_date() + " " + ticket.getDeparture_time();
                String arrivalDateTime = ticket.getArrival_date() + " " + ticket.getArrival_time();

                Timestamp departureTimestamp = new Timestamp(dateFormat.parse(departureDateTime).getTime());
                Timestamp arrivalTimestamp = new Timestamp(dateFormat.parse(arrivalDateTime).getTime());

                timeArrayMinutes.add(
                        (int) TimeUnit.MILLISECONDS
                                .toMinutes(arrivalTimestamp.getTime() - departureTimestamp.getTime()));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return timeArrayMinutes;
    }

    public double getAverageTimeOfFly(List<Integer> timeOfFlyList) {
        Integer sum = 0;
        if (!timeOfFlyList.isEmpty()) {
            for (Integer time : timeOfFlyList) {
                sum += time;
            }
            return sum.doubleValue() / timeOfFlyList.size();
        }
        return sum;
    }

    public double getPercentile(List<Integer> timeOfFlyList, int percentile) {
        Collections.sort(timeOfFlyList);
        int index = (int) Math.ceil(((double) percentile / 100) * timeOfFlyList.size());

        return timeOfFlyList.get(index - 1);
    }
}
