package com.example.demo.controller;

import com.example.demo.domain.BoardFileDTO;
import com.example.demo.service.BoardFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@Controller
public class BoardFileController {
    @Autowired BoardFileService boardFileService;

    // 파일 다운로드
    @RequestMapping("/boardFile/fileDownload/{fileName:.+}")
    public void fileDownload(@PathVariable String fileName,
                             HttpServletRequest request, HttpServletResponse response) throws IOException {
        boardFileService.fileDownload(fileName, request, response);
    }

    // 파일 개별 삭제
    @GetMapping ("/boardFile/removeBoardFile")
    public String removeBoardFile(Model model
                                    , @RequestParam(value="boardNo", required = true) int boardNo
                                    , @RequestParam(value="boardFileNo", required = true) int boardFileNo) throws Exception {
        int row = boardFileService.removeBoardFile(boardFileNo);
        if(row == 0){
            String msg = URLEncoder.encode("삭제 실패하였습니다.", "UTF-8");
            return "redirect:/boardFile/getBoardFileOne?boardFileNo="+boardFileNo+"&msg="+msg;
        }
        String msg = URLEncoder.encode("파일을 삭제하였습니다.", "UTF-8");
        return "redirect:/board/modifyBoard?boardNo="+boardNo+"&msg="+msg;
    }

    // 파일 개별 수정
    @GetMapping("/boardFile/modifyBoardFile")
    public String modifyBoardFile(Model model
                                    , @RequestParam(value="boardFileNo", required = true) int boardFileNo){
        BoardFileDTO boardFileDTO = boardFileService.getBoardFileOne(boardFileNo);
        model.addAttribute("boardFileDTO", boardFileDTO);

        return "/boardFile/modifyBoardFile";
    }
    @PostMapping("/boardFile/modifyBoardFile")
    public String modifyBoardFile(Model model, HttpServletRequest request, MultipartFile files, BoardFileDTO boardFileDTO){
        String path = request.getServletContext().getRealPath("/boardFile-upload/");

        return "redirect:/";
    }

    // 파일 개별 등록
    @GetMapping("/boardFile/addBoardFile")
    public String addBoardFile(Model model, int boardNo){
        model.addAttribute("boardNo", boardNo);

        return "/boardFile/addBoardFile";
    }
    @PostMapping("/boardFile/addBoardFile")
    public String addBoardFile(Model model, HttpServletRequest request, List<MultipartFile> files, int boardNo) throws Exception {
        String path = request.getServletContext().getRealPath("/boardFile-upload/");

        int row = boardFileService.addBoardFile(files, path, boardNo);
        // row != 0 이면 입력 성공
        if(row == 0){
            String msg = URLEncoder.encode( "등록실패하였습니다.", "UTF-8");
            return "redirect:/board/modifyBoard?boardNo="+boardNo+"&msg="+msg;
        }
        String msg = URLEncoder.encode("파일을 업로드하였습니다.", "UTF-8");
        return "redirect:/board/modifyBoard?boardNo="+boardNo+"&msg="+msg;
    }

    // 파일 상세정보 출력
    @GetMapping("/boardFile/getBoardFileOne")
    public String getBoardFileOne(Model model
                            , @RequestParam(value = "boardNo", required = true) int boardNo
                            , @RequestParam(value = "boardFileNo", required = true) int boardFileNo){
        BoardFileDTO boardFileDTO = boardFileService.getBoardFileOne(boardFileNo);
        model.addAttribute("boardFileDTO", boardFileDTO);
        model.addAttribute("boardNo", boardNo);

        return "/boardFile/getBoardFileOne";
    }
}
