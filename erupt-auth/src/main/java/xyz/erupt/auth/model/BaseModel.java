package xyz.erupt.auth.model;

import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.EruptField;

import javax.persistence.*;

/**
 * Created by liyuepeng on 10/11/18.
 */
@Getter
@Setter
@MappedSuperclass
public class BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    @EruptField
    private Long id;
}
