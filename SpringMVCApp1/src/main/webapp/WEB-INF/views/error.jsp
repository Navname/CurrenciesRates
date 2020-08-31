<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 27.08.2020
  Time: 6:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Ошибка</title>
    <style type="text/css">
        .message {
            margin-top: 0px;
        }
        .link {
            width: 150px;
        }
        .container {
            border-radius: 5px;
            background-color: #f2f2f2;
            padding: 20px;
            width: 200px;
            height: 70px;
            position: fixed;
            top: 50%;
            left: 50%;
            margin-top: -145px;
            margin-left: -145px;
        }
        @media screen and (max-width: 600px) {
            .link {
                width: 150px;
            }

            body .container {
                font-size: 7px;
                width: 90px;
                height: 30px;
                border-radius: 3px;
                padding: 10px;
                margin-left: -15%;
                margin-top: -15%;
            }
        }
        @media screen and (min-width: 2000px) {
            body .container {
                font-size: 50px;
                width: 650px;
                height: 220px;
                border-radius: 30px;
                padding: 90px;
                margin-left: -15%;
                margin-top: -15%;
            }
        }
    </style>
</head>
<body>
<div class="container">
    <h3 class="message">Что-то пошло не так :(</h3>
    <a class="link" href="/greeting">Попробовать ещё раз! :)</a>
</div>
</body>
</html>
