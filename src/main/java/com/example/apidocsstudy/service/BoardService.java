package com.example.apidocsstudy.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class BoardService {
    public Map<String, String> selectBoard(String no) {
        Map<String, String> map = new HashMap<>();
        map.put("BoardNo", no);
        map.put("BoardTitle", "노인과 바다");
        map.put("BoardContent", "푹 쉬어라, 작은 새야. 그리고 어디든 열심히 날아가서 되든 안되든 모험을 한번 해 보렴. 행운을 잡을 때까지 말이야");

        return map;
    }
}
