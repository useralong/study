<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- 引入JSTL核心标签库，我们才能使用JSTL标签 core --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
 <head>
     <title>Title</title>
 </head>
 <body>

 <%-- 存储一个list集合到 request请求中 --%>
 <%
     ArrayList<String> people = new ArrayList<>();
     people.add(0,"张三");
     people.add(1,"李四");
     people.add(2,"赵六");
     request.setAttribute("list",people);
 %>

 <%-- 使用 <c:forEach> 获取请求中的list集合并遍历
    var 每一次便利出来的对象
    items 要便利的数组、或集合
    begin 从哪里开始
    end 哪里结束
    step 步长
  --%>
    <c:forEach var="people" items="${list}">
        <c:out value="${people}"/> <br>
    </c:forEach>

 <hr>

<c:forEach begin="0" end="3" step="1" var="people" items="${list}">
    <c:out value="${people}"/> <br>
</c:forEach>

 </body>
</html>
