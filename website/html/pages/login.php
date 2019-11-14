<?php
// login and register to the system
require_once'../scripts/sessions.php';
require_once'../includes/curl.php';

$_SESSION['rdir'] = false;

$_SESSION['loginm'] = "";


if(isset($_POST['form-username']))
{
  
	$password = $_POST['form-password'];
  $out = "echo " . $password . ' | sha256sum';
  $hashPass = exec($out);
  $hashPass = explode(' ', $hashPass);
  $hashPass = $hashPass[0];
  $userId = $_POST['form-username'];
  $link = 'http://localhost:8080/RemoteControl/rest/Users/authentication/'.$userId;
  echo $link;
  $headers = array(
    "Authentication:$hashPass",
    "userId:$userId"
  );
   $curl = new curl();
   if($curl->getRequest($link,$headers))
   {
     $_SESSION['isLoggedIn'] = true;
     $_SESSION['id'] = $userId;
     $_SESSION['authString'] = $hashPass;
     $_SESSION['rdir'] = true;
     
   }
   else
   {
     $_SESSION['loginm'] = "Invalid User ID or Password";
   
   }
}	



if ($_SESSION['rdir']  == true) 
{
	header( "refresh:0;url=../pages/index.php" );

}

if ($_SESSION['isLoggedIn']  == true) 
{
	header( "refresh:0;url=../pages/index.php" );

}





?>
<!DOCTYPE html>
<html lang="en">
    
    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Lynchburg College Extron Recording Control Panel</title>

        <!-- CSS -->
        <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
        <link rel="stylesheet" href="../assets/font-awesome/css/font-awesome.min.css">
		<link rel="stylesheet" href="../assets/css/form-elements.css">
        <link rel="stylesheet" href="../assets/css/style.css">

        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
            <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
            <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->

        <!-- Favicon and touch icons -->
        <link rel="shortcut icon" href="assets/ico/favicon.png">
        <link rel="apple-touch-icon-precomposed" sizes="144x144" href="assets/ico/apple-touch-icon-144-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="114x114" href="assets/ico/apple-touch-icon-114-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="72x72" href="assets/ico/apple-touch-icon-72-precomposed.png">
        <link rel="apple-touch-icon-precomposed" href="assets/ico/apple-touch-icon-57-precomposed.png">

    	<link href="../css/bootstrap.css" rel="stylesheet" type="text/css">
		<link href="../css/bootstrap-theme.css" rel="stylesheet" type="text/css">

    </head>

    <body>
        <!-- Top content -->
        <div class="top-content">
        	
            <div class="inner-bg">
                <div class="container">
                  <?php  if ($_SESSION['isLoggedIn'] == TRUE):?>
	                   <div class="row">
	                       <h3><?php if(isset($message)) echo $message ;?></h3>
	                       
	                        <?php endif;?>
	                      	                       
	                        </div>
					<?php  if ($_SESSION['isLoggedIn']!= TRUE):
					?>
				 <center>
                  <fieldset name="Group1">  
                    <div class="row">
                        <div class="col-sm-8 col-sm-offset-2 text">
                            <h1>Remote Control Login</h1>
                            <div class="description">
                            	<p>
	                            	                            	</p>
                            </div>
                        </div>
                    </div>
                   
                    <div class="form-center">
                    <div class="row">
                        <div class="col-md-5 col-md-offset-4"> 
                        	
                        	<div class="form-box">
	                        	<div class="form-top">
	                        	 <?php endif; ?>
	                        	 <?php if ($_SESSION['isLoggedIn'] !=   TRUE): ?>

	                        		<div class="form-top-left">
	                        			<h3>Login to our site</h3>
	                            		<p>Enter Username and password to log on:</p>
	                        		</div>
	                        		<div class="form-top-right">
	                        			<i class="fa fa-key"></i>
	                        		</div>
	                            </div>
	                            					<?php
						   if ($_SESSION['loginm'] != "")
						  	{
						  		echo $_SESSION['loginm'];
						  		$_SESSION['loginm'] = "";	
						  	}
					
					?>


	                            <div class="form-bottom">
				                    <form role="form" action="login.php" name="login" id="login" method="post" class="login-form">
				                    	<div class="form-group">
				                    		<label class="sr-only" for="form-username">email</label>
				                        	<input type="text" name="form-username" placeholder="Username..." class="form-username form-control" id="form-username">
				                        </div>
				                        <div class="form-group">
				                        	<label class="sr-only" for="form-password">Password</label>
				                        	<input type="password" name="form-password" placeholder="Password..." class="form-password form-control" id="form-password">
				                        </div>
				                        <button type="submit" class="btn">Sign in!</button>
				                    </form>
			                    </div>
		                    </div>
		                
		                		                        </div>
	                        
                      </div>
                      </div>
                       
                   <?php endif; ?>  
                     </fieldset>
                     </center>
        </div>
        </div>
		</div>
        <!-- Footer -->
      
        <!-- Javascript -->
        <script src="../assets/js/jquery-1.11.1.min.js"></script>
        <script src="../assets/bootstrap/js/bootstrap.min.js"></script>
        <script src="../assets/js/scripts.js"></script>
        
        <script>
        
        function validate(email)
        {
        	var re = validateEmail(email);
        	if (var == false)
        	{
        	var btn = document.getElementById("reg-button");
        	btn.disabled = true;
        	}
        	else 
        	{
        	var btn = document.getElementById("reg-button");
        	btn.disabled = true;

        	}
        	
        }
        function validateEmail(email) {
  			var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
  			return re.test(email);
		}
        
        
        </script>
        <!--[if lt IE 10]>
            <script src="../assets/js/placeholder.js"></script>
        <![endif]-->

    </body>

</html>
