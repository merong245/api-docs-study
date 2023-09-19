package com.example.apidocsstudy.controller;

import com.example.apidocsstudy.service.BoardService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


/*
    Swagger 리소스임을 명시
    value : 태그 작성
    tags : 여러개 태그 정의 가능
 */
@Api(value = "BoardController V1")
@RestController
@RequestMapping("/api/v1/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    /*
        수행하는 API Operation 선언
        value : API에 대한 간략한 설명
        notes : 자세한 설명
     */
    @ApiOperation(value = "select", notes = "검색 테스트입니다.")

    /*
        Operation에서 가능한 응답
        code : 응답코드
        message : 응답에 대한 설명
        responseHeaders : 헤더 추가
     */
    @ApiResponses({
            @ApiResponse(code = 200, message = "{\n" +
                    "\"BoardNo\": \"게시글 번호\",\n" +
                    "\"BoardTitle\": \"게시글 제목\",\n" +
                    "\"BoardContent\": \"게시글 내용\"\n" +
                    "}"),
            @ApiResponse(code = 404, message = "페이지를 못찾겠어여 ㅜㅜ"),
            @ApiResponse(code = 500, message = "아무래도 서버에러인거 같아요")
    })
    @GetMapping("/")
    public Map<String, String> selectBoard(
            /*
                파라미터에 대한 정보 작성
                value : 파라미터 정보
                required : 필수 정보인가?
                example : 예시 값
             */
            @ApiParam(value = "게시글 번호", required = true, example = "1")
            @RequestParam String no) {
        return boardService.selectBoard(no);
    }
}
