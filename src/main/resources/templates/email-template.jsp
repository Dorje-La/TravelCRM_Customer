<!DOCTYPE html>
<br>
  <head>
    <meta charset="UTF-8" />
    <title>${Subject}</title>
    <style>
      body {
        text-align: center;
        margin-left: 25%;
        margin-right: 25%;
        margin-top: 2%;
        margin-bottom: 0;
        background-color: rgb(253, 253, 253);
        line-height: 1.5;
        font-family: Arial, Helvetica, sans-serif;
        border: solid black 1px;
        padding-bottom: 2%;
      }
      .content {
        text-align: center;
        padding: 0px;
      }
      .header {
        text-align: center;
        margin: 10px 0 0 0;
      }
      h3,
      h2,
      p,
      span {
        font-size: 14px;
      }
      .welcome-text {
        font-size: 20px;
        font-weight: bold;
        margin: 10px 0 0 0;
      }
      img {
        align-items: center;
        max-width: 100px;
        max-height: 100px;
      }
    </style>
  </head>
    <div class="header">${firstName} ${lastName}</div>
    <div class="content">
      <p class="welcome-text">Welcome to Weeky Travels</p>
      Thank you for signing up for Weeky Travels.
      Your new account has been created, and you may now log in.

     <p>Your Login Details:</p>
     <p>Username : ${email}</p>
     <p>Password : ${password}</p>

     <p>Please keep your login information secure and do not share it with anyone.</p>

      If you have any questions or need assistance, our support team is here to help. Feel free to reach out to us at.<br />

      Your privacy is important to us. You can learn more about how we handle your data by visiting our Privacy Policy <a href="https://www.google.com/">google.com</a>.<br />
      Thank you for choosing.<br />
      Best regards,<br />
      Weeky Travels
    </div>
  </body>
</html>
