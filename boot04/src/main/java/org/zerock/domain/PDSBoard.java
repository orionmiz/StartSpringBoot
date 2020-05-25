package org.zerock.domain;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@ToString(exclude = "files")
@Table(name="tbl_pds")
@EqualsAndHashCode(of="pid")
public class PDSBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pid;

    private String pname;
    private String pwriter;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="pdsno") // pdsfile 테이블에 pdsno 컬럼 추가
    private List<PDSFile> files;
}
