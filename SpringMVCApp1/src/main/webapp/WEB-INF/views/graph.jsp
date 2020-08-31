<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>График</title>
    <style type="text/css">
        .link {
            margin-left: 43%;
            font-size: 24px;
        }

        .chartContainer {
            height: 200px;
            width: 100%;
        }

        @media screen and (max-width: 600px) {
            .link {
                font-size: 12px;
            }

            .chartContainer {
                height: 120px;
                width: 100%;
            }
        }

        @media screen and (min-width: 2000px) {
            .link {
                font-size: 40px;
            }

            .chartContainer {
                height: 1000px;
                width: 100%;
            }
        }
    </style>
    <script type="text/javascript">
        window.onload = function () {
            var dps = [[]];
            var chart = new CanvasJS.Chart("chartContainer", {
                theme: "light2", // "light1", "dark1", "dark2"
                animationEnabled: true,
                zoomEnabled: true,
                title: {
                    text: "График изменения курса валюты"
                },
                data: [{
                    type: "area",
                    dataPoints: dps[0]
                }]
            });

            var xValue;
            var yValue;

            <c:forEach items="${dataPointsList}" var="dataPoints" varStatus="loop">
            <c:forEach items="${dataPoints}" var="dataPoint">

            xValue = "${dataPoint.x}";
            console.log(xValue);
            yValue = parseFloat("${dataPoint.y}");
            dps[parseInt("${loop.index}")].push({
                x: new Date(xValue),
                y: yValue,
            });
            </c:forEach>
            </c:forEach>

            chart.render();
        }
    </script>
</head>
<body>
<div class="chartContainer" id="chartContainer">
    <script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>
</div>
<br>
<a class="link" href="/greeting">Выбрать другую валюту</a>
</body>
</html>
