# API 문서 자동화

### API 문서

API 문서는 개발자간의 원활한 소통을 위해 사용되는 문서이다.  
프론트엔드 개발자가 서버의 API에 대해서 어떻게 호출할 수 있는지? 어떤 응답이 존재하는지? 매번 백엔드 개발자에 물어볼 수 없다.  
또한 API는 클라이언트의 요청에 의해 유지보수되기 때문에 변경사항이 발생할 수 있기 때문에 해당 API에 대한 요청과 응답, 엔드포인트, 파라미터, URL 등 필요한 정보를 담은 명세서를 관리를 유지해야한다.  
  
너무 중요하단 것을 알지만 API 명세를 작성하는 것은 너무 귀찮은 일이다.  
API 문서를 자동화 할 수 있으면 좋지 않을까??  

# Swagger VS Spring REST Docs

## Swagger

컨트롤러 메소드 상단에 어노테이션을 통해 API 기술

- 코드
    ```java
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
    ```

### 장점

1. 컨트롤러단에 어노테이션 몇개만으로 API 문서를 만들어주는 편리한 도구!
2. 생성된 API를 직접 실행해볼수도 있다!

### 단점

1. 문서화하기 위한 코드가 컨트롤러 코드에 포함된다.
    - 가독성 저하
2. API 스펙이 변경되더라도 어노테이션을 수정하지 않으면 자동으로 문서가 수정되지 않는다.
    - 문서와 실제 API 스펙이 일치함을 보장하지 못함!

### 고민점

API를 문서화하는 목적은 API 스펙을 정의한다는 것인데 관리나 수정에 대한 유지보수성이 떨어진다는 의문점…?

사용자가 **API 동작에 대한 테스트가 가능**하다는 점은 매력적이지만 테스트를 하기 위해서는 서버가 항시 구동되고 있어야한다는 점

INSERT또는 DELETE 같은 API는 테스트하기에는 위험해보인다.

(+ 테스트용 DB를 만드는 것도 부담입니다 ㅜㅡㅜ)

## Spring REST Docs

스프링 프레임워크에서 제공하는 API 문서 자동화 도구

테스트 기반, Ascii doc 문서로 API 명세 생성(원한다면 마크다운 형식으로도 문서화 할 수 있다.)

### 장점

1. 테스트 기반의 문서화 작업으로 항상 API 스펙과 일치하는 문서를 만들 수 있다.
    - 테스트가 성공하는 API 단위로 문서가 생성된다.
2. 문서화 코드와 기능 코드가 분리 되어있다.

### 단점

1. 테스트 코드 작성을 위한 테스트에 대한 부담이 늘어난다.
2. 학습 곡선이 높다.

## 테스트 도구

### MockMvc

웹 어플리케이션을 서버에 배포하지 않고도 스프링 MVC 동작을 재연할 수 있는 라이브러리

주로 Controller Layer Unit Test로 사용되며 @WebMvcTest를 통해 Controller Layer(Presentation Layer)의 Bean들만 불러오고 그외 Service Layer의 Bean는 Mock 객체를 통한 단위 테스트를 할 수 있다.

### RestAssured

REST 웹 서비스를 검증하기 위한 라이브러리

주로 End to End Test에 사용되며 @SpringBootTest로 실제 요청을 보내 전체 로직을 테스트 할 수 있다.

어떤 테스트 도구를 사용하는지에는 정답이 없고, 시스템마다 다를 것이라 생각하지만..

Spring Rest Docs를 사용하면서 문서 제작을 위한 지속적인 테스트를 위해서는 개인적으로는 MockMvc를 적용하는 것이 좋아보인다.


# 참고

https://victorydntmd.tistory.com/341 - Swagger를 사용한 API 문서 자동화

https://hudi.blog/spring-rest-docs/ - Spring REST Docs를 사용한 API 문서 자동화

https://techblog.woowahan.com/2597/ - Spring REST Docs 적용

https://jojoldu.tistory.com/289 - 개발자는 마크다운이 편한데 왜 Spring-Rest-Docs에서는 Ascii Doc으로 주로 문서화를 할까?( + 마크다운으로 구현해보기)

https://tecoble.techcourse.co.kr/post/2020-08-19-rest-assured-vs-mock-mvc/ - MockMVC vs RestAssured

https://beomseok95.tistory.com/293 - BDD 가 무엇일까?
