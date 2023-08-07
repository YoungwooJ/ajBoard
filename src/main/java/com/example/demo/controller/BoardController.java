package com.example.demo.controller;

import com.example.demo.domain.BoardDTO;
import com.example.demo.domain.MemberDTO;
import com.example.demo.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class BoardController {
    @Autowired BoardService boardService;

    // 게시판 상세정보 조회
    @GetMapping("/board/getBoardOne")
    public String getBoardOne(Model model
                                , @RequestParam(value = "boardNo", required = true) int boardNo) {
        Map<String, Object> map = boardService.getBoardOne(boardNo);
        model.addAttribute("board", map);

        return "/board/getBoardOne";
    }

    // 게시판 리스트 조회
    @GetMapping("/board/getBoardList")
    public String getBoardList(Model model) {

        List<Map<String, Object>> boardList = boardService.getBoardList();
        model.addAttribute("boardList", boardList);

        /*for(Map<String, Object> m : boardList ) {
            System.out.println("m : " + m);
            BoardDTO board = new BoardDTO();
            board.setBoardNo((int)m.get("boardNo"));
            board.setTitle((String)m.get("title"));
            board.setWriter((String)m.get("writer"));
            board.setCreatedate((String)m.get("createdate"));
            board.setUpdatedate((String)m.get("updatedate"));

            ArrayList<BoardDTO> dtoList = new ArrayList<BoardDTO>();
            dtoList.add(board);
            model.addAttribute("dtoList", dtoList);
        }*/

        return "/board/getBoardList";
    }

    // 조회수 증가

    // 게시판 검색

    // 댓글, 대댓글

    // 파일 업로드, 다운로드

    // 게시글 입력
    @GetMapping("/board/addBoard")
    public String addBoard(HttpSession session, Model model) {
        MemberDTO loginMember = (MemberDTO)session.getAttribute("loginMember");
        model.addAttribute("loginMember", loginMember);

        return "/board/addBoard";
    }
    @PostMapping("/board/addBoard")
    public String addBoard(Model model, BoardDTO boardDTO) {

        int row = boardService.addBoard(boardDTO);
        // row != 0 이면 입력 성공
        if(row == 0) {
            model.addAttribute("msg", "등록 실패하였습니다.");
            return "redirect:/board/addBoard?boardNo="+boardDTO.getBoardNo();
        }

        return "redirect:/board/getBoardList";
    }

    // 게시글 수정
    @GetMapping("/board/modifyBoard")
    public String modifyBoard(Model model
                                , @RequestParam(value = "boardNo", required = true) int boardNo) {

        Map<String, Object> board = boardService.getBoardOne(boardNo);
        model.addAttribute("board", board);

        return "/board/modifyBoard";
    }
    @PostMapping("/board/modifyBoard")
    public String modifyBoard(Model model, BoardDTO boardDTO) {

        int row = boardService.modifyBoard(boardDTO);
        // row != 0 이면 수정 성공
        if(row == 0) {
            model.addAttribute("msg", "수정 실패하였습니다.");
            return "redirect:/board/modifyBoard?boardNo="+boardDTO.getBoardNo();
        }

        return "redirect:/board/getBoardList";
    }

    // 게시글 삭제
    @GetMapping("/board/removeBoard")
    public String removeBoard(Model model
                                , @RequestParam(value = "boardNo", required = true) int boardNo) {

        int row = boardService.removeBoard(boardNo);
        if(row == 0) {
            model.addAttribute("msg", "삭제 실패하였습니다.");
            return "redirect:/board/getBoardOne?boardNo="+boardNo;
        }

        return "redirect:/board/getBoardList";
    }

}
