package com.example.apidocsstudy.service;

import com.example.apidocsstudy.dto.BoardDto;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class BoardService {
    public BoardDto selectBoard(String no) {

        BoardDto boardDto = new BoardDto(
                no,
                "노인과 바다",
                "푹 쉬어라, 작은 새야. 그리고 어디든 열심히 날아가서 되든 안되든 모험을 한번 해 보렴. 행운을 잡을 때까지 말이야"
        );

        return boardDto;
    }
}
