package org.zerock.domain;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@ToString(exclude = "replies")
@Entity
@Table(name = "tbl_freeboards")
@EqualsAndHashCode(of = "bno")
public class FreeBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    private String title;
    private String content;
    private String writer;

    @CreationTimestamp
    private Timestamp regdate;

    @UpdateTimestamp
    private  Timestamp updatedate;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL/*, fetch = FetchType.EAGER*/) // 게시글이 댓글에 매여있음. 매여있는 쪽(PK)에서 mappedBy
    List<FreeBoardReply> replies;

}
