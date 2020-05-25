package org.zerock;

import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.domain.FreeBoard;
import org.zerock.domain.FreeBoardReply;
import org.zerock.persistence.FreeBoardReplyRepository;
import org.zerock.persistence.FreeBoardRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Log
@Commit
public class FreeBoardTests {
    @Autowired
    FreeBoardRepository b_repo;

    @Autowired
    FreeBoardReplyRepository r_repo;

    @Test
    public void insertDummies() {

        IntStream.range(1, 201).forEach(i -> {

            FreeBoard board = new FreeBoard();
            board.setTitle("This is Board " + i);
            board.setContent("Content.. " + i);
            board.setWriter("user" + (i % 10));

            b_repo.save(board);

        });

    }

    @Test
    public void insertReply1Way() {
        FreeBoardReply reply = new FreeBoardReply();

        reply.setReply("테스트");
        reply.setReplier("테스트");

        FreeBoard board = new FreeBoard();
        board.setBno(199L);
        reply.setBoard(board);

        r_repo.save(reply);
    }

    @Test
    @Transactional
    public void insertReply2Way() {
        Optional<FreeBoard> result = b_repo.findById(199L);

        result.ifPresent(board -> {
            List<FreeBoardReply> list = board.getReplies();

            FreeBoardReply reply = new FreeBoardReply();

            reply.setReply("테스트2");
            reply.setReplier("테스트2");
            reply.setBoard(board);

            list.add(reply);

            // board.setReplies(list); 굳이 필요한가?

            b_repo.save(board);
        });
    }

    @Test
    @Transactional
    public void  testList1() {

        /*
        * 1. Lazy fetch -> Transactional 필요
        * 2. Eager fetch -> 필요하지 않음
        * 3. 둘다 성능 안좋은건 마찬가지
        * */

        Pageable page = PageRequest.of(0, 10, Sort.Direction.DESC, "bno");

        b_repo.findByBnoGreaterThan(0L, page).forEach(board -> {
            log.info(board.getBno() + ": " + board.getTitle() + ": " + board.getReplies().size());
        });



    }

    @Test
    public void testList2() {
        b_repo.getPage(PageRequest.of(0, 10, Sort.Direction.DESC, "bno")).forEach(arr -> {
            log.info(Arrays.toString(arr));
        });
    }

}
