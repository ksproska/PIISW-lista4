package com.capgemini.jpa.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Table(name = "server")
//@SQLDelete(sql = "UPDATE server SET deleted = true WHERE id=?")
//@Where(clause = "deleted=false")
public class Server {

    @Id
    @SequenceGenerator(name = "SERVER_ID_GENERATOR", sequenceName = "SERVER_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SERVER_ID_GENERATOR")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String ip;

    @Version
    @Column
    private Long version;

    @Column
    private LocalDateTime createdDate;

    @Column
    private LocalDateTime lastUpdatedDate;

//    @Column
//    private boolean deleted;

    public Server(String name, String ip) {
        super();
        this.name = name;
        this.ip = ip;
        createdDate = LocalDateTime.now();
        lastUpdatedDate = createdDate;
//        deleted = false;
    }

    @Lock(LockModeType.OPTIMISTIC)
    public Long getVersion(){
        return version;
    }

    @Lock(LockModeType.OPTIMISTIC)
    public LocalDateTime getCreatedDate(){
        return createdDate;
    }

    @Lock(LockModeType.OPTIMISTIC)
    public LocalDateTime getLastUpdateDate(){
        return lastUpdatedDate;
    }

    @PostUpdate
    public void setLastUpdateDate() {
        lastUpdatedDate = LocalDateTime.now();
    }
}
