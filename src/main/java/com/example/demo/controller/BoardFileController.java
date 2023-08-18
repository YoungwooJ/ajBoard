package com.example.demo.controller;

import com.example.demo.domain.BoardFileDTO;
import com.example.demo.service.BoardFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
public class BoardFileController {
    @Autowired BoardFileService boardFileService;

    // 파일 다운로드 폼으로 이동
    /*@RequestMapping("/download/fileDownloadListView")
    public ModelAndView fileDownloadList() {
        ModelAndView mv = new ModelAndView();
        // 폴더에 있는 전체 파일 목록 가져오기
        File path = new File("/");
        String[] fileList = path.list();

        mv.addObject("fileList", fileList);
        mv.setViewName("/download/fileDownloadListView");
        return mv;
    }*/
    // 파일 다운로드
    @RequestMapping("/boardFile/fileDownload/{fileName:.+}")
    public void fileDownload(@PathVariable String fileName,
                             HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 웹 어플리케이션 루트에 있는 boardFile-upload 디렉토리를 기준으로 파일 경로 생성
        String relativePath = "/boardFile-upload/";
        String fullPath = request.getServletContext().getRealPath(relativePath);

        // 파일 객체 생성
        File file = new File(fullPath, fileName);

        if (file.exists()) {
            response.setContentType("application/octet-stream");
            response.setContentLength((int) file.length());
            response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString()) + "\"");
            response.setHeader("Content-Transfer-Encoding", "binary");
            // response 객체를 통해서 서버로부터 파일 다운로드

            try (OutputStream os = response.getOutputStream();
                 // 파일 입력 객체 생성
                 FileInputStream fis = new FileInputStream(file)) {
                FileCopyUtils.copy(fis, os);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    // 파일 개별 삭제
    @GetMapping ("/boardFile/removeBoardFile")
    public String removeBoardFile(Model model
                                    , @RequestParam(value="boardNo", required = true) int boardNo
                                    , @RequestParam(value="boardFileNo", required = true) int boardFileNo){
        int row = boardFileService.removeBoardFile(boardFileNo);
        if(row == 0){
            model.addAttribute("msg", "삭제 실패하였습니다.");
            return "redirect:/boardFile/getBoardFileOne?boardFileNo="+boardFileNo;
        }
        return "redirect:/board/modifyBoard?boardNo="+boardNo;
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
    public String addBoardFile(Model model, HttpServletRequest request, List<MultipartFile> files, int boardNo){
        String path = request.getServletContext().getRealPath("/boardFile-upload/");

        int row = boardFileService.addBoardFile(files, path, boardNo);
        // row != 0 이면 입력 성공
        if(row == 0){
            model.addAttribute("msg", "등록실패하였습니다.");
            return "/board/modifyBoard?boardNo="+boardNo;
        }
        return "redirect:/board/modifyBoard?boardNo="+boardNo;
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
