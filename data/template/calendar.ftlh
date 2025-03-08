<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Task Calendar</title>
    <link rel="stylesheet" href="/data/css/calendar.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        h1 {
            text-align: center;
            margin: 20px 0;
        }

        .calendar {
    display: grid;
    grid-template-columns: repeat(7, 1fr); /* 7 колонок для дней недели */
    grid-gap: 5px; /* отступы между ячейками */
    width: 80%;
    max-width: 800px;
    margin-top: 20px;
}

.calendar-row {
    display: contents;
}

        .calendar-header {
            display: contents;
        }

        .calendar-cell {
            padding: 20px;
            text-align: center;
            border: 1px solid #ddd;
            background-color: #fff;
        }

        .empty {
            background-color: transparent;
        }

        .today {
            background-color: yellow;
        }

        .calendar-row {
            display: contents;
        }

        .calendar-cell a {
            display: block;
            margin: 10px 0;
            font-size: 16px;
            font-weight: bold;
            color: #333;
            text-decoration: none;
        }

        .calendar-cell a:hover {
            color: #007bff;
        }

        .task-card {
            margin-top: 10px;
            padding: 10px;
            border-radius: 5px;
            color: #fff;
            font-size: 14px;
        }

        .task-title {
            font-weight: bold;
        }

        .task-card:hover {
            opacity: 0.8;
        }
    </style>
</head>
<body>
<h1>Task Calendar for March 2025</h1>
<div class="calendar">
    <div class="calendar-header">
        <div class="calendar-cell">Mon</div>
        <div class="calendar-cell">Tue</div>
        <div class="calendar-cell">Wed</div>
        <div class="calendar-cell">Thu</div>
        <div class="calendar-cell">Fri</div>
        <div class="calendar-cell">Sat</div>
        <div class="calendar-cell">Sun</div>
    </div>
    <#list daysInMonth as day>
    <#if day.dayOfMonth == 1>
    <div class="calendar-row">
        <#list 1..day.dayOfWeek.value-1 as i>
        <div class="calendar-cell empty"></div>
    </#list>
</#if>
<div class="calendar-cell <#if day == today>today</#if>">
    <a href="/tasks?date=${day}">${day.dayOfMonth}</a>
    <#list tasksByDate.getTasksByDate(day) as task>
    <#if task.type??>
    <div class="task-card" style="background-color: ${task.type.color};">
        <span class="task-title">${task.title}</span>
    </div>
    <#else>
    <div class="task-card" style="background-color: #cccccc;">
        <span class="task-title">${task.title}</span>
    </div>
</#if>
</#list>
</div>
<#if day.dayOfWeek.value == 7>
</div>
</#if>
</#list>
</div>
</body>
</html>
