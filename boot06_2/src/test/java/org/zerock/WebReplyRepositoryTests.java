package org.zerock;

import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.zerock.domain.WebBoard;
import org.zerock.domain.WebReply;
import org.zerock.persistence.WebReplyRepository;

import java.util.Arrays;
import java.util.stream.IntStream;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Log
@Commit
public class WebReplyRepositoryTests {

    @Autowired
    WebReplyRepository repo;

    @Test
    public void InsertDummies() {

        Long[] arr = {307L, 306L};

        Arrays.stream(arr).forEach(num -> {
            WebBoard board = new WebBoard();
            board.setBno(num);
            IntStream.range(0, 10).forEach(i -> {
                WebReply reply = new WebReply();
                reply.setReplyText("댓글내용" + i);
                reply.setReplyer("댓글작성자" + i);
                reply.setBoard(board);
                repo.save(reply);
            });
        });

    }
}
