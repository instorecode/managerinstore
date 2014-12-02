<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>LOGS DA ULTIMA EXPORTAÇÃO DE MÚSICA</h1>
        <hr />
        <div class="container">
            <div class="diva">
                <div class="tt">Arquivos encontrados</div>
                <ul>
                    <c:forEach items="${lines_file_exists}" var="line">
                        <li>${line}</li>
                    </c:forEach>
                </ul>
            </div>
            <div class="divb">
                <div class="tt">Arquivos não encontrados</div>
                <ul>
                    <c:forEach items="${lines_file_not_exists}" var="line">
                        <li>${line}</li>
                    </c:forEach>
                </ul>
            </div>
        </div>
        <style type="text/css">
            .diva {
                width: 49%;
                float: left;
            }
            .divb {
                width: 49%;
                float: right;
            }
            .tt {
                padding: 10px;
                background-color: #c3c3c3;
            }
            ul { list-style: none; margin-left: -40px;}
            ul li { padding: 2px; margin-top: 2px; font-family: Arial; font-size: 11px;}
/*            .diva ul li { background-color: green; color: whitesmoke; }
            .divb ul li { background-color: red; color: whitesmoke; }*/
            .diva .tt { background-color: green; color: whitesmoke; }
            .divb .tt { background-color: red; color: whitesmoke; }
        </style>
    </body>
</html>
