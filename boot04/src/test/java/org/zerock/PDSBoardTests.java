package org.zerock;


import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.zerock.domain.PDSBoard;
import org.zerock.domain.PDSFile;
import org.zerock.persistence.PDSBoardRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Log
@Commit // commit해야 트랜잭션에서 롤백이 안됨
public class PDSBoardTests {

    @Autowired
    PDSBoardRepository repo;


    @Test
    public void testInsertPDS() {
        PDSBoard board = new PDSBoard();

        board.setPname("Document");

        PDSFile file = new PDSFile();
        file.setPdsfile("file1.doc");

        PDSFile file2 = new PDSFile();
        file2.setPdsfile("file2.doc");

        board.setFiles(Arrays.asList(file, file2));

        log.info("try to save pds");

        repo.save(board);
    }

    @Transactional
    @Test
    public void testModifyFileName() {
        log.info("updated : " + repo.updatePDSFile(1L, "updated1.doc"));
    }

    @Transactional
    @Test
    public void testModifyFileName2() {
        Optional<PDSBoard> board = repo.findById(3L);

        board.ifPresent(b -> {

            log.info("데이터가 존재하므로 update 시도");

            PDSFile target = new PDSFile();
            target.setFno(2L);

            int idx = b.getFiles().indexOf(target);

            if (idx > -1) {
                b.getFiles().get(idx).setPdsfile("updated2.doc");
            }

            repo.save(b);

        });

    }

    @Transactional
    @Test
    public void deletePDSFileTest() {
        log.info("DELETE PDSFILE : " + repo.deletePDSFile(2L));
    }


    @Test
    public void insertDummies() {
        List<PDSBoard> list = new ArrayList<>();

        IntStream.range(1,100).forEach(i -> {
            PDSBoard pds = new PDSBoard();
            pds.setPname("자료" + i);

            PDSFile file1 = new PDSFile();
            file1.setPdsfile("file1.doc");

            PDSFile file2 = new PDSFile();
            file2.setPdsfile("file2.doc");

            pds.setFiles(Arrays.asList(file1, file2));

            log.info("try to save pds");

            list.add(pds);

        });

        repo.saveAll(list);
    }


    @Test
    public void testSummary() {
        repo.getSummary().forEach(obj -> {System.out.println(Arrays.toString(obj));});
    }
}
