package org.zerock.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.zerock.domain.Board;

import java.util.Collection;
import java.util.List;

public interface BoardRepository extends CrudRepository<Board, Long>, QuerydslPredicateExecutor<Board> {

    List<Board> findBoardByTitle(String title);

    Collection<Board> findBoardByWriter(String writer);

    //List<Board> findBoardByWriterLike(String like); // like
    //List<Board> findBoardByWriterStartingWith(String like); // like + %
    //List<Board> findBoardByWriterEndingWith(String like); // % + like
    List<Board> findByWriterContaining(String like); // % + like + %

    List<Board> findByTitleContainingOrContentContaining(String title, String content);

    List<Board> findByTitleContainingAndBnoGreaterThan(String title, Long bno);

    List<Board> findByBnoGreaterThanOrderByBnoDesc(Long bno, Pageable paging);

    //List<Board> findByBnoGreaterThan(Long bno, Pageable paging);

    Page<Board> findByBnoGreaterThan(Long bno, Pageable paging);

    @Query("SELECT b FROM Board b WHERE b.title LIKE %?1% AND b.bno > 0 ORDER BY b.bno DESC")
    List<Board> findByTitle(String title);

    @Query("SELECT b FROM Board b WHERE b.content LIKE %:content% AND b.bno > 0 ORDER BY b.bno DESC")
    List<Board> findByContent(@Param("content") String content);

    @Query("SELECT b FROM #{#entityName} b WHERE b.writer LIKE %?1% AND b.bno > 0 ORDER BY b.bno desc")
    List<Board> findByWriter(String writer);

    @Query("SELECT b.bno, b.title, b.writer, b.regdate FROM Board b WHERE b.bno > 0 AND b.title LIKE %?1% ORDER BY b.bno DESC")
    List<Object[]> findByTitle2(String title);

    @Query(value="select bno, title, writer, regdate from tbl_boards where bno > 0 and title like concat('%', ?1, '%') order by bno desc", nativeQuery = true)
    List<Object[]> findByTitle3(String title);

    @Query("select b from Board b where b.bno > 0 order by b.bno desc")
    public List<Board> findByPage(Pageable pageable);

}
