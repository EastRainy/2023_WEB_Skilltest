package com.practice.Skilltest.board.controller;

import com.practice.Skilltest.board.dto.BoardDto;
import com.practice.Skilltest.board.service.BoardService;
import com.practice.Skilltest.board.service.PageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Controller
@RequiredArgsConstructor
public class BoardController {

    @Autowired
    private final BoardService boardService;
    @Autowired
    private final PageService pageService;

    @RequestMapping(method = RequestMethod.GET, path = "/board")
    public String board(){
        return "redirect:/board/1";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/board/{page}")
    public String viewPage(@PathVariable("page") long page, Model model){

        if(!pageService.checkValid(page)){return "html/error/wrongaccess";}

        model.addAttribute("resultList",pageService.selectedPageList(page));
        long[] pageRange = pageService.pageRange(page);
        model.addAttribute("startRange",pageRange[0]);
        model.addAttribute("endRange",pageRange[1]);
        model.addAttribute("crrPage", page);
        model.addAttribute("haveNext", pageService.haveNext(page));

        return "html/board/boardmain";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/board/view/{id}")
    public String viewBoard(@PathVariable("id") long id, Model model){
        model.addAttribute("result", boardService.viewOne(id));
        model.addAttribute("id",id);
        return "html/board/boardview";
    }


    //단순신규생성
    @RequestMapping(method = RequestMethod.GET, path = "/board/new")
    public String newBoardGet(){
        return "html/board/boardnew";
    }
    @PostMapping(path = "/board/new")
    @ResponseBody
    public ResponseEntity<?> newBoardPost(BoardDto req){
        HttpHeaders h = new HttpHeaders();
        long dest = boardService.newBoard(req);

        h.setLocation(URI.create("/board/view/"+dest));
        return new ResponseEntity<>(h, HttpStatus.MOVED_PERMANENTLY);
    }
    //기존수정
    @GetMapping(path = "/board/{id}/modifying")
    public String modifyingBoardGet(@PathVariable("id") long id, Model model){
        BoardDto result = boardService.viewOne(id);
        model.addAttribute("id", id);
        model.addAttribute("req", result);
        return "html/board/boardmodifying";
    }
    @PostMapping(path ="/board/{id}/modifying")
    @ResponseBody
    public ResponseEntity<?> modifyingBoardPost(@PathVariable("id") long id, BoardDto req, Model model){

        req.setBoard_id(id);
        boardService.modifyBoard(req);

        HttpHeaders h = new HttpHeaders();
        h.setLocation(URI.create("/board/view/"+id));
        return new ResponseEntity<>(h, HttpStatus.MOVED_PERMANENTLY);
    }
    //게시글 삭제
    @GetMapping(path="/board/{id}/delete")
    public String deleteBoard(@PathVariable("id") long id){
        boardService.deleteBoard(id);
        return "redirect:/board";
    }
}
