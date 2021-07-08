## {{>path}}

- 현재 머스태치(mustache) 파일을 기준으로 다른 파일을 가져온다. 스프링부트에서 머스태치의 기본 root 경로는 /resources/templates 이다.
- ex) {{>layout/header}}

## {{#List}}

- List를 순회한다. Java의 for문과 동일하게 생각하면 된다.
- ex) {{#posts}}

## {{변수명}}

- List에서 뽑아낸 객체의 필드를 사용한다.
- ex) {{id}}

## {{Object.field}}

- 머스태치는 객체의 필드 접근시 점(Dot)으로 구분한다.
- ex) Post 클래스의  id에 대한 접근 -> {{post.id}}

## readonly

- input 태그에 읽기 가능만 허용하는 속성이다. 수정을 금지하고 싶을 때 지정해준다.



## Update function 추가

```javascript
var main = {
    init : function () {
        var _this = this;

        $('#btn-update').on('click', function () {
            _this.update();
        });
    },
    update : function () {
        var data = {
            title: $('#title').val(),
            content: $('#content').val()
        };

        var id = $('#id').val();

        $.ajax({
            type: 'PUT',
            url: '/api/v1/posts/'+id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('글이 수정되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
};

main.init();
```

- $('#btn-update').on('click')
  - btn-update란 id를 가진 HTMP Element에 click event가 발생할 때 update function을 실행하도록 이벤트를 등록한다.
- update : function()
  - 신규로 추가될 update function이다.
- type: 'PUT'
  - 여러 HTTP Method 중 PUT method를 선택한다.
  - REST 규약에서 CRUD는 다음과 같이 HTTP Method에 매핑된다.
    - 생성 (Create) : POST
    - 읽기 (Read) : GET
    - 수정 (Update) : PUT
    - 삭제 (Delete) : DELETE
- url: 'api/v1/posts/'+id
  - 어느 게시글을 수정할지 URL Path로 구분하기 위해 Path에 id를 추가한다.