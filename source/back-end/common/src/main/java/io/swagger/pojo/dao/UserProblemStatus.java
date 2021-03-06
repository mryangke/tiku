package io.swagger.pojo.dao;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Accessors(chain = true)
@Table(name = "user_problem_status")
public class UserProblemStatus extends BasicEntity {

    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 问题id
     */
    @Column(name = "problem_id")
    private Long problemId;
    /**
     * 用户唯一id
     */
    @Column(name = "user_uuid")
    private String userUuid;

    /**
     * 问题状态
     */
    @Column(name = "status")
    private String status;

    /**
     * 传进来的时间
     */
    @Column(name = "date")
    private Date date;

}
