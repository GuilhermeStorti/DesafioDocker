package br.com.storti.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.PersistenceConstructor;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@RequiredArgsConstructor(onConstructor = @__(@PersistenceConstructor))
public class BaseModel {

    @Column(name = "created_on")
    public LocalDateTime createdOn;

    @LastModifiedDate
    @Column(name = "updated_on")
    public LocalDateTime updatedOn;

    @PrePersist
    public void prePersist(){
        this.createdOn = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate(){
        this.updatedOn = LocalDateTime.now();
    }
}
