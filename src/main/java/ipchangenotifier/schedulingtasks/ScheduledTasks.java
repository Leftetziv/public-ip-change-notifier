package ipchangenotifier.schedulingtasks;

import ipchangenotifier.model.Ip;
import ipchangenotifier.service.IpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ScheduledTasks {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private IpService ipService;

    @Value( "${mail.mymail}")
    private String emailAddr;

    boolean initialFound=false;

    public void findInitial() {
        try (java.util.Scanner s = new java.util.Scanner(new java.net.URL("https://api.ipify.org").openStream(), "UTF-8").useDelimiter("\\A")) {
            String ipString = s.next();

            //for testing
//            ipString = "-1.-1.-1.-1";

            Ip ip= new Ip();
            ip.setCreatedAt(LocalDateTime.now());
            ip.setLastNoticed(LocalDateTime.now());
            ip.setIp(ipString);
            ipService.addIp((ip));

            initialFound=true;
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    @Scheduled(fixedDelay  = 5000)
    public void scheduleFixedRateTask() {

        if (!initialFound) {
            findInitial();
            return;
        }

        try (java.util.Scanner s = new java.util.Scanner(new java.net.URL("https://api.ipify.org").openStream(), "UTF-8").useDelimiter("\\A")) {
            String currentIpString = s.next();

            Ip lastIp = ipService.getLastIp();
            String lastIpString = lastIp.getIp();

            if (!currentIpString.equals(lastIpString)) {
                Ip newIip= new Ip();
                newIip.setCreatedAt(LocalDateTime.now());
                newIip.setLastNoticed(LocalDateTime.now());
                newIip.setIp(currentIpString);
                ipService.addIp((newIip));

                //send email
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom(emailAddr);
                message.setTo(emailAddr);
                message.setSubject("Server Public IP Changed");
                message.setText("IP has been changed: "+lastIpString+" >> "+currentIpString);
                emailSender.send(message);
            } else {
                lastIp.setLastNoticed(LocalDateTime.now());
                ipService.addIp(lastIp);
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
