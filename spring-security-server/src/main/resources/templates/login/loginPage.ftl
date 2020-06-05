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
        .error{
            color: rgba(255, 0, 0, 1)
        }
        #verification-code{
            width: 69%;
        }
        .code-group{
            display: flex;
        }
        .verification-image{
            width: 85px;
            height: 30px;
            margin-left: 5px;
            margin-top: 5px;
        }
        #remember-me{
            width: 7%;
        }
        .remember{
            font-size: 14px;
            margin-top: -5px;
        }
        .sms-group{
            position: relative;
        }
        .send{
            width: 100px;
            display: inline-block;
            position: absolute;
            left: 310px;
            top: 10px;
        }
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
            <p class="code-group">
                <label for="verification-code" class="sr-only">Verificationcode</label>
                <input type="text" id="verification-code" name="verification-code" class="form-control" placeholder="Verification-code" required="">
                <img src="/login/verification/code" class="verification-image">
            </p>
            <p class="code-group">
                <label for="remember-me" class="sr-only">Rememberme</label>
                <input type="checkbox" id="remember-me" name="remember-me" class="form-control">
                <span class="remember">记住我</span>
            </p>
            <input name="_csrf" type="hidden" value="b901b99f-5cf2-4c34-ba94-34ffdf3b9d07">
            <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
        </form>
    </div>

    <!-- 短信登陆 -->
    <div class="container">
        <form class="form-signin" method="post" action="/login/mobile">
            <h2 class="form-signin-heading">Please sign in</h2>
            <#if Session.SPRING_SECURITY_LAST_EXCEPTION?exists>
                <p class="error">${Session.SPRING_SECURITY_LAST_EXCEPTION.message}</p>
            </#if>
            <p>
                <label for="mobile" class="sr-only">Mobile</label>
                <input type="text" id="mobile" name="mobile" class="form-control" placeholder="Mobile" required="" autofocus="">
            </p>
            <p class="sms-group">
                <label for="smsCode" class="sr-only">SmsCode</label>
                <input type="text" id="smsCode" name="smsCode" class="form-control" placeholder="SmsCode" required="">
                <a href="javascript:void (0)" class="send">发送验证码</a>
            </p>
            <input name="_csrf" type="hidden" value="b901b99f-5cf2-4c34-ba94-34ffdf3b9d07">
            <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
        </form>
    </div>
    <script src="http://libs.baidu.com/jquery/1.9.1/jquery.min.js"></script>
    <script>
        (function (win, callback) {
            callback(win);
        }(window, function (win) {
            let method = {
                argument: {
                },
                sendSmsCode: function () {
                    let send = document.querySelector('.send');
                    send.onclick = function () {
                        console.log($('#mobile').val())
                        $.ajax({
                            type: 'GET',
                            url: '/login/sms/code',
                            data: {'mobile': $('#mobile').val()},
                            success: function (response) {

                            }
                        });
                    }
                },
                execute: function () {
                    this.sendSmsCode();
                }
            }

            method.execute();


        }));
    </script>
</body>
</html>