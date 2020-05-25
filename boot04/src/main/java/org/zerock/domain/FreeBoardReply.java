package org.zerock.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@ToString(exclude = "board")
@Entity
@Table(name = "tbl_free_replies",
        indexes = {@Index(unique = false, columnList = "board_bno")}) // 인덱스를 쓰면 실행 계획이 효율적으로 변함
@EqualsAndHashCode(of = "rno")
public class FreeBoardReply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    private String reply;
    private String replier;

    @CreationTimestamp
    private Timestamp regdate;

    @UpdateTimestamp
    private Timestamp updatedate;

    @ManyToOne
    FreeBoard board;
}
