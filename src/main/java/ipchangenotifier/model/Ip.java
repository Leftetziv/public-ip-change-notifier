package ipchangenotifier.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Ip {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String Ip;

//    @CreatedDate
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss")
    @Column(name = "created_at", updatable = false)
    private LocalDateTime  createdAt;

    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss")
    @Column(name = "last_noticed")
    private LocalDateTime  lastNoticed;

    public Ip() {
    }

    public Ip(String ip) {
        Ip = ip;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIp() {
        return Ip;
    }

    public void setIp(String ip) {
        Ip = ip;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastNoticed() {
        return lastNoticed;
    }

    public void setLastNoticed(LocalDateTime lastNoticed) {
        this.lastNoticed = lastNoticed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ip ip = (Ip) o;
        return Objects.equals(Ip, ip.Ip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Ip);
    }
}
