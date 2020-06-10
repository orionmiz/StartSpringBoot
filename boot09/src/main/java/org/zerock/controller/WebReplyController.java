package org.zerock.controller;


import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.domain.WebBoard;
import org.zerock.domain.WebReply;
import org.zerock.persistence.WebReplyRepository;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/replies/*")
@Log
public class WebReplyController {

    /*
    생성자 주입 방식의 장점과 field injection의 단점 (참고)
    https://yaboong.github.io/spring/2019/08/29/why-field-injection-is-bad/
     */

    private final WebReplyRepository repo;

    public WebReplyController(WebReplyRepository repo) {
        this.repo = repo;
    }

    @Transactional
    @PostMapping("/{bno}")
    public ResponseEntity<List<WebReply>> addReply(
            @PathVariable("bno") Long bno,
            @RequestBody WebReply reply) {

        log.info("addReply()..................");
        log.info("BNO: " + bno);
        log.info("REPLY: " + reply);

        WebBoard board = new WebBoard();
        board.setBno(bno);

        reply.setBoard(board);
        repo.save(reply);


        return new ResponseEntity<>(getRepliesOfBoard(board), HttpStatus.CREATED);
    }

    private List<WebReply> getRepliesOfBoard(WebBoard board) throws RuntimeException {
        log.info("getRepliesOfBoard()............");
        return repo.getRepliesOfBoard(board);
    }

    @Transactional
    @DeleteMapping("/{bno}/{rno}")
    public ResponseEntity<List<WebReply>> remove(
            @PathVariable("bno") Long bno,
            @PathVariable("rno") Long rno) {

        WebBoard board = new WebBoard();
        board.setBno(bno);

        repo.deleteById(rno);

        return new ResponseEntity<>(getRepliesOfBoard(board), HttpStatus.OK);
    }

    @Transactional
    @PutMapping("/{bno}")
    public ResponseEntity<List<WebReply>> modify(
            @PathVariable("bno") Long bno,
            @RequestBody WebReply reply) {

        log.info("modify reply: " + reply);

        repo.findById(reply.getRno()).ifPresent(origin -> {
            origin.setReplyText(reply.getReplyText());
            repo.save(origin);
        });

        WebBoard board = new WebBoard();
        board.setBno(bno);

        return new ResponseEntity<>(getRepliesOfBoard(board), HttpStatus.CREATED);
    }

    @GetMapping("/{bno}")
    public ResponseEntity<List<WebReply>> getReplies(@PathVariable("bno") Long bno) {
        log.info("getReplies()..........");

        WebBoard board = new WebBoard();
        board.setBno(bno);

        return new ResponseEntity<>(getRepliesOfBoard(board), HttpStatus.OK);
    }
}
