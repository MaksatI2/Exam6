<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Task Calendar</title>
    <link rel="stylesheet" href="/css/calendar.css">
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
    <#if day.dayOfMonth == 1 || day.dayOfWeek.value == 1>
    <div class="calendar-row">
    </#if>

    <#if day.dayOfMonth == 1>
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
