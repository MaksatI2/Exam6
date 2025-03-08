<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Add Task</title>
    <link rel="stylesheet" href="/css/calendar.css">
</head>
<body>
<div class="container">
    <h1>Add Task for ${date}</h1>
    <form action="/tasks/add" method="post">
        <input type="hidden" name="date" value="${date}">

        <label for="title">Title:</label>
        <input type="text" id="title" name="title" required>

        <label for="description">Description:</label>
        <textarea id="description" name="description" required></textarea>

        <label for="type">Task Type:</label>
        <select id="type" name="type">
            <option value="REGULAR">Regular</option>
            <option value="URGENT">Urgent</option>
            <option value="WORK">Work</option>
            <option value="SHOPPING">Shopping</option>
            <option value="OTHER">Other</option>
        </select>

        <button type="submit">Add Task</button>
    </form>
    <br>
    <a href="/">Back to Calendar</a>
</div>
</body>
</html>
