<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Tasks for ${date}</title>
    <link rel="stylesheet" href="/css/taskList.css">
</head>
<body>
<h1>Tasks for ${date}</h1>
<a href="/calendar">Back to Calendar</a>
<div class="task-list">
    <#list tasks as task>
    <div class="task-card" style="background-color: ${task.type.color};">
        <div class="task-info">
            <strong class="task-title">${task.title}</strong>
            <span class="task-type">(${task.type})</span>
        </div>
        <p class="task-description">${task.description}</p>
        <form action="/tasks/delete" method="POST" class="delete-form">
            <input type="hidden" name="taskId" value="${task.id}">
            <input type="hidden" name="date" value="${date}">
            <button type="submit" class="delete-btn">Delete</button>
        </form>
    </div>
</#list>
</div>
<a href="/tasks/add?date=${date}" class="add-task">Add Task</a>
</body>
</html>
