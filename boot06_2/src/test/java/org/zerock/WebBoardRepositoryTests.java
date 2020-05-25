package org.zerock;

import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.zerock.domain.WebBoard;
import org.zerock.persistence.WebBoardRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Log
@Commit
public class WebBoardRepositoryTests {

    @Autowired
    private WebBoardRepository repo;

    @Test
    public void insertBoardDummies() {

        List<WebBoard> list = new ArrayList<WebBoard>();

        IntStream.range(0, 300).forEach(i -> {
            WebBoard board = new WebBoard();

            board.setTitle("Sample Board Title " + i);
            board.setContent("Content Sample ..." + i);
            board.setWriter("user0" + (i % 10));

            list.add(board);
        });

        repo.saveAll(list);
    }

    @Test
    public void testList1() {
        Pageable pageable = PageRequest.of(0, 20, Sort.Direction.DESC, "bno");

        Page<WebBoard> result = repo.findAll(repo.makePredicate(null, null), pageable);

        log.info("PAGE : " + result.getPageable());

        log.info("----------------------------------------------");

        result.getContent().forEach(board -> log.info(board.toString()));
    }

    @Test
    public void testList2() {
        Pageable pageable = PageRequest.of(0, 20, Sort.Direction.DESC, "bno");

        Page<WebBoard> result = repo.findAll(repo.makePredicate("t", "10"), pageable);

        log.info("PAGE : " + result.getPageable());

        log.info("----------------------------------------------");

        result.getContent().forEach(board -> log.info(board.toString()));
    }
}
