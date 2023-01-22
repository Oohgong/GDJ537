<%@ page language="java" contentType="text/html; 
charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>   

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
	<script defer src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    
    <link rel="stylesheet" href="/css/chat/chatroom.css">
    
    
    <link rel="icon" href="/images/favicon.png">
	<title>그룹채팅 💭 </title>
</head>


<body>
    <div id="container" class="container">
		<input type="hidden" id="sessionId" value="${user.id}">
		<input type="hidden" id="roomNum" value="${roomNum}">
	
		<div class="header">
            <div class="header-flex">
                <div class="tinum">
                    <div class="title">
                        <span><strong>${rn}</strong></span>
                    </div>
                    <div class="number">
                        <img src="/img/chatroom-number.png" width="15px" height="15px">
                        <span>${count}</span>
                    </div>
                </div>
            </div>
        </div>
		
		<div id="chating" class="chating">
		
		</div>
		
		<div class="put">
			<div id="msg">
				<input type="hidden" name="userName" id="userName" value="${user.name}">
				<input type="hidden" name="userId" id="userId" value="${user.id}">
				<input type="text" id="inputChat" placeholder="보내실 메시지를 입력하세요.">
				<button onclick="javascript:send();" id="sendBtn" class="sendBtn"><strong>보내기</strong></button>
			</div>
		</div>
	</div>

	<script type="text/javascript" src="/js/messenger/group_chatroom.js"></script>
	<script type="text/javascript">
		wsOpen();
		/* wsEvt(); */
	</script>

</body>

</html>
