package org.zerock;

import com.querydsl.core.BooleanBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.zerock.domain.Board;
import org.zerock.domain.QBoard;
import org.zerock.persistence.BoardRepository;

import java.util.Arrays;
import java.util.Collection;

@SpringBootTest
class Boot03ApplicationTests {

    @Autowired
    BoardRepository repo;

    @Test
    void contextLoads() {
    }

    @Test
    public void testInsert200() {

        for (int i = 1; i <= 200; i++) {

            Board board = new Board();
            board.setTitle("제목.." + i);
            board.setContent("내용...." + i + " 채우기 ");
            board.setWriter("user0" + ( i % 10));
            repo.save(board);

        }

    }

    @Test
    public void testByTitle() {
        repo.findBoardByTitle("제목..177").forEach(System.out::println);
    }

    @Test
    public void testByWriter() {
        repo.findBoardByWriter("user00").forEach(System.out::println);
    }

    @Test
    public void testByWriterContaining() {

        repo.findByWriterContaining("05").forEach(System.out::println);


    }

    @Test
    public void testByTitleAndBno() {
        repo.findByTitleContainingAndBnoGreaterThan("5", 50L).forEach(System.out::println);
    }

    /*@Test
    public void testBnoPagingSort() {
        repo.findByBnoGreaterThan(5L, PageRequest.of(0, 10, Sort.Direction.ASC, "bno")).forEach(System.out::println);
    }*/

    @Test
    public void testBnoPagingSort() {
        Page<Board> result = repo.findByBnoGreaterThan(0L, PageRequest.of(0, 10, Sort.Direction.ASC, "bno"));

        System.out.println("PAGE SIZE : " + result.getSize());
        System.out.println("TOTAL PAGES : " + result.getTotalPages());
        System.out.println("TOTAL COUNT : " + result.getTotalElements());
        System.out.println("NEXT : " + result.nextPageable());

        result.getContent().forEach(System.out::println);
    }

    @Test
    public void testByTitle2() {
        repo.findByTitle("17").forEach(System.out::println);
    }

    @Test
    public void testByContent() {
        repo.findByContent("17").forEach(System.out::println);
    }

    @Test
    public void testByWriter2() {
        repo.findByWriter("09").forEach(System.out::println);
    }

    @Test
    public void testByTitle3() {
        repo.findByTitle2("17").forEach(b -> System.out.println(Arrays.toString(b)));
    }

    @Test
    public void testByTitle4() {
        repo.findByTitle3("17").forEach(b -> System.out.println(Arrays.toString(b)));
    }

    @Test
    public void testByPage() {
        repo.findByPage(PageRequest.of(0, 10)).forEach(System.out::println);
    }


    @Test
    public void testPredicate() {
        String type = "t";
        String keyword = "17";

        BooleanBuilder builder = new BooleanBuilder();

        QBoard board = QBoard.board;

        if (type.equals("t")) {
            builder.and(board.title.like("%" + keyword + "%"));
        }

        builder.and(board.bno.gt(0));

        Page<Board> result = repo.findAll(builder, PageRequest.of(0, 10));

        System.out.println("PAGE SIZE : " + result.getSize());
        System.out.println("TOTAL PAGES : " + result.getTotalPages());
        System.out.println("TOTAL COUNT : " + result.getTotalElements());
        System.out.println("NEXT : " + result.nextPageable());

        result.getContent().forEach(System.out::println);
    }
}
