package org.zerock;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.zerock.domain.Board;
import org.zerock.persistence.BoardRepository;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BoardRepositoryTests {

    @Autowired
    BoardRepository repo;

    @Test
    public void test() {

    }

    @Test
    public void testInsert() {
        Board board = new Board();
        board.setTitle("제목");
        board.setContent("내용");
        board.setWriter("user00");

        repo.save(board);
    }

    @Test
    public void testRead() {
        repo.findById(1L).ifPresent(System.out::println);
    }

    @Test
    public void testUpdate() {

        /*Optional<Board> optionalBoard = repo.findById(1L);

        if (optionalBoard.isPresent()) {
            Board board = optionalBoard.get();

            board.setContent("수정된 내용");
            repo.save(board);
        }*/

        Board board = repo.findById(1L).get();

        board.setContent("수정된 내용");
        repo.save(board);

        /*repo.findById(1L).ifPresent((board -> {
            board.setContent("수정된 내용");
            repo.save(board);
        }));*/
    }
}
