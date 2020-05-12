<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>登陆</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
    <link href="https://getbootstrap.com/docs/4.0/examples/signin/signin.css" rel="stylesheet" crossorigin="anonymous">
    <style>
        .error{color: rgba(255, 0, 0, 1)}
    </style>
</head>
<body>
    <div class="container">
        <form class="form-signin" method="post" action="/login/authentication">
            <h2 class="form-signin-heading">Please sign in</h2>
            <#if Session.SPRING_SECURITY_LAST_EXCEPTION?exists>
                <p class="error">${Session.SPRING_SECURITY_LAST_EXCEPTION.message}</p>
            </#if>
            <p>
                <label for="username" class="sr-only">Username</label>
                <input type="text" id="username" name="username" class="form-control" placeholder="Username" required="" autofocus="">
            </p>
            <p>
                <label for="password" class="sr-only">Password</label>
                <input type="password" id="password" name="password" class="form-control" placeholder="Password" required="">
            </p>
            <input name="_csrf" type="hidden" value="b901b99f-5cf2-4c34-ba94-34ffdf3b9d07">
            <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
        </form>
    </div>
<script>
</script>
</body>
</html>