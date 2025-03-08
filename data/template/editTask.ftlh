<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Edit Task</title>
    <link rel="stylesheet" href="/css/editTasks.css">
</head>
<body>
<h1>Edit Task</h1>
<a href="/tasks?date=${task.date}" class="back-link">Back to Tasks</a>
<div class="task-form">
    <form action="/tasks/edit" method="post">
        <input type="hidden" name="id" value="${task.id}">
        <input type="hidden" name="date" value="${task.date}">

        <label for="title">Title:</label>
        <input type="text" id="title" name="title" value="${task.title}" required>

        <label for="description">Description:</label>
        <textarea id="description" name="description">${task.description}</textarea>

        <label for="type">Type:</label>
        <select id="type" name="type">
            <option value="REGULAR" <#if task.type == "REGULAR">selected</#if>>Regular</option>
        <option value="URGENT" <#if task.type == "URGENT">selected</#if>>Urgent</option>
    <option value="WORK" <#if task.type == "WORK">selected</#if>>Work</option>
<option value="SHOPPING" <#if task.type == "SHOPPING">selected</#if>>Shopping</option>
<option value="OTHER" <#if task.type == "OTHER">selected</#if>>Other</option>
</select>

<button type="submit" class="save-btn">Save</button>
<a href="/tasks?date=${task.date}" class="cancel-btn">Cancel</a>
</form>
</div>
</body>
</html>