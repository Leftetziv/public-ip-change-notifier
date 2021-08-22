package ipchangenotifier.controller;

import ipchangenotifier.model.Ip;
import ipchangenotifier.service.IpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MainController {

    @Autowired
    private IpService ipService;

    @GetMapping(path = "/ip")
    ResponseEntity<List<Ip>> getAllIps() {
        return new ResponseEntity<>(ipService.list(), HttpStatus.OK);
    }
}
