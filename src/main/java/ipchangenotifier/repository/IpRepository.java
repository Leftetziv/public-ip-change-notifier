package ipchangenotifier.repository;

import ipchangenotifier.model.Ip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IpRepository extends JpaRepository<Ip, Long> {

    List<Ip> findAllByOrderByIdDesc();

    @Query(value = "SELECT * FROM IP order by CREATED_AT DESC limit 1", nativeQuery = true)
    Ip getLastIp();
}
