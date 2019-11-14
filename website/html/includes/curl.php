<?php 
class curl{

  function getRequest($link, $headerArray)
  {
    $curl_h = curl_init($link);

    curl_setopt($curl_h, CURLOPT_HTTPHEADER,
      $headerArray
    );

    # do not output, but store to variable
    curl_setopt($curl_h, CURLOPT_RETURNTRANSFER, true);

    $response = curl_exec($curl_h);
    curl_close($curl_h);
    if ($response == "Found Password")
    {
      return true;
    }
    else
    {
      return false;
    }
  
  }
function getRequest2($link, $headerArray)
  {
    $curl_h = curl_init($link);

    curl_setopt($curl_h, CURLOPT_HTTPHEADER,
      $headerArray
    );

    # do not output, but store to variable
    curl_setopt($curl_h, CURLOPT_RETURNTRANSFER, true);

    $response = curl_exec($curl_h);
    curl_close($curl_h);
    return $response;
  
  }

}
?>