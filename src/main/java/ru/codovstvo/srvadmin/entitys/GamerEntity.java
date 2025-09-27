package ru.codovstvo.srvadmin.entitys;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.codovstvo.srvadmin.dto.GamerDTO;

@Data
@Entity
@Getter
@Setter
@NoArgsConstructor
public class GamerEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    public int amountPurchases;
    public int scoreEvent;
    public String mark;
    public String frameAwatar;
    public String userName;
    public String avatar;
    public String platform;
    public Long installDate;
    public Long lastSessionDate;

    public GamerEntity(GamerDTO dto) {
        //можно дописать любую логику перед сохранением переменной
        this.amountPurchases = dto.amountPurchases;
        this.scoreEvent = dto.scoreEvent;
        this.mark = dto.mark;
        this.frameAwatar = dto.frameAwatar;
        this.userName =  dto.userName;
        this.avatar = dto.avatar;

        String platform = dto.platform + "World";
        this.platform = platform;
        this.installDate = dto.installDate;
        this.lastSessionDate = dto.lastSessionDate;
    }

}
