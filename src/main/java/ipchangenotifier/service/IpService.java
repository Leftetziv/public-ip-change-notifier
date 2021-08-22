package ipchangenotifier.service;

import ipchangenotifier.model.Ip;
import ipchangenotifier.repository.IpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IpService {

    @Autowired
    private IpRepository ipRepository;

    public List<Ip> list() {
        return ipRepository.findAllByOrderByIdDesc();
    }

    public void addIp(Ip ip) {
        ipRepository.save(ip);
    }

    public Ip getLastIp() {
        return ipRepository.getLastIp();
    }
}
