package com.example.apidocsstudy.dto;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BoardDto {
    private String BoardNo;
    private String BoardTitle;
    private String BoardContent;
}
