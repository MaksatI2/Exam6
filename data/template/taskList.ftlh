<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Tasks for ${date}</title>
</head>
<body>
    <h1>Tasks for ${date}</h1>
    <a href="/">Back to Calendar</a>
    <ul>
        <#list tasks as task>
            <li>
                <strong>${task.title}</strong> (${task.type})
                <p>${task.description}</p>
                <form action="/tasks/delete" method="POST">
                    <input type="hidden" name="date" value="${date}">
                    <input type="hidden" name="title" value="${task.title}">
                    <button type="submit">Delete</button>
                </form>
            </li>
        </#list>
    </ul>
    <a href="/tasks/add?date=${date}">Add Task</a>
</body>
</html>