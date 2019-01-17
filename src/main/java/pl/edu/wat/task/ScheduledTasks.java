package pl.edu.wat.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.edu.wat.model.User;
import pl.edu.wat.model.Visit;
import pl.edu.wat.repository.UserRepository;
import pl.edu.wat.repository.VisitRepository;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Paweł Skrzypkowski
 * Wojskowa Akademia Techniczna im. Jarosława Dąbrowskiego, Warszawa 2018.
 */

@Component
public class ScheduledTasks {
    @Autowired
    VisitRepository visitRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    public JavaMailSender emailSender;

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final DateTimeFormatter patientDateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    private static final String SUBJECT = "Powiadomienie o zbliżającej się wizycie";

    //alert patient every 30 mins
    @Scheduled(fixedDelay = 1800000, initialDelay = 5000)
    public void alertPatient() {
        LocalDateTime maxDate = LocalDateTime.now().plusDays(1);
        List<Visit> visitsToAlertPatients = visitRepository.getVisitsToAlertPatients(maxDate);
        Map<Visit, User> visitsToAlert = visitsToAlertPatients.stream().map(v -> new AbstractMap.SimpleEntry<>(v, userRepository.findByVisitsAndSpecializationIsNull(v)))
                .filter(entry -> entry.getValue() != null && entry.getValue().getEmail() != null && entry.getValue().getEmail().length() > 0).collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));
        logger.info("Start alerting patients. Patients to alert: " + visitsToAlert.size() + ". TIME: " + dateTimeFormatter.format(ZonedDateTime.now()));
        for(Map.Entry<Visit, User> entry : visitsToAlert.entrySet()){
            try {
                sendMessage(entry.getValue().getEmail(), "Przypominamy o zbliżającej się wizycie w szpitalu. Data wizyty: " + patientDateTimeFormatter.format(entry.getKey().getVisitDate()));
                logger.info("Patient successfully alerted: " + entry.getValue().getFullname() + ". TIME: " + dateTimeFormatter.format(ZonedDateTime.now()));
                visitRepository.setPatientAlertedToTrue(entry.getKey().getId());
            } catch (Exception e) {
                logger.info("Patient not successfully alerted: " + entry.getValue().getFullname() + ". TIME: " + dateTimeFormatter.format(ZonedDateTime.now()) + ". Error: " + e.getMessage());
            }
        }
    }

    private String sendMessage(String to, String text) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(SUBJECT);
        helper.setText(text, true);
        emailSender.send(message);
        return "success";
    }
}
