package com.example.apidocsstudy.controller;

import com.example.apidocsstudy.dto.BoardDto;
import com.example.apidocsstudy.service.BoardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;

@AutoConfigureRestDocs // 일단 Auto설정 -> 커스텀 필요하면 별도로 설정하시공~
@WebMvcTest(BoardController.class)
public class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean // BoardController가 의존하는 빈 모킹
    private BoardService boardService;


    static private String no = "1";
    static private BoardDto result = new BoardDto(
            no,
            "노인과 바다",
            "푹 쉬어라, 작은 새야. 그리고 어디든 열심히 날아가서 되든 안되든 모험을 한번 해 보렴. 행운을 잡을 때까지 말이야"
    );

    @Test
    @DisplayName("게시글 번호 1번 가져오기~")
    void findBoard() throws Exception {
        // given
        given(boardService.selectBoard(no))
                .willReturn(result);

        // when & then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/board?no=" + no)) // Mock ㄴㄴ, RestDoc ㅇㅇ -> If you are using MockMvc did you use RestDocumentationRequestBuilders to build the request? 에러 발생하는 경우
                .andDo(MockMvcResultHandlers.print())
                // 직접 수작업으로 매핑해주는 방법
                .andDo(
                        document("board-select"
                                ,
                                requestParameters( // 파라미터~
                                        parameterWithName("no").description("게시글 번호")
                                )
                                , responseFields( // 응답~
                                        fieldWithPath("boardNo").type(JsonFieldType.STRING).description("게시글 번호"),
                                        fieldWithPath("boardTitle").type(JsonFieldType.STRING).description("게시글 제목"),
                                        fieldWithPath("boardContent").type(JsonFieldType.STRING).description("게시글 내용")
                                )
                        )
                )
                // spring rest docs 의 도움을 받아 생성하는 법
                .andDo(document(
                        "board/selectBoard" // {class name}/{method name}
                        , preprocessRequest(prettyPrint()) // 예쁘게 프린팅 ㅋ
                        , preprocessResponse(prettyPrint())
                ))


                .andExpect(MockMvcResultMatchers.status().isOk())
        ;

    }
}
