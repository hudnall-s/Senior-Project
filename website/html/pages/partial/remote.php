<?php
 
require_once("../includes/curl.php");
	class Remote{
		
		private function getRemoteBrands(){
       $link = "http://10.0.0.7:8080/RemoteControl/rest/lirc/remotes";
       $curl = new Curl();
       $remotes = $curl->getRequest2($link, "");
       $remotes = explode("[", $remotes);
       $remotes = explode(']', $remotes[1]);
       $remotes = explode(',', $remotes[0]);
       var_dump($remotes);
       foreach($remotes as $r)
       {
       ?>
          <option value="<?php echo strtolower($r); ?>"><?php echo explode('"', $r)[1]; ?></option>
       <?php 
       }
       
   
   
     }
		function printRemote(){
			echo <<<EOL
<div>			
<table class="table">
  <tr class="row">
    <td class="col-sm-1"> <button type="button" class="btn btn-secondary">Input </button>  </td>
    <td class="col-sm-1"></td>
    <td class="col-sm-1"> <button type="button" class="btn btn-Danger">Power</button></td>
  </tr>
  <tr class="row">
    <td><button type="button" class="btn btn-secondary">1</button> </td>
    <td><button type="button" class="btn btn-secondary">2</button> </td>
    <td><button type="button" class="btn btn-secondary">3</button> </td>
  </tr>
  <tr class="row">
    <td><button type="button" class="btn btn-secondary">4</button> </td>
    <td><button type="button" class="btn btn-secondary">5</button> </td>
    <td><button type="button" class="btn btn-secondary">6</button> </td>
  </tr >
  <tr class="row">
    <td><button type="button" class="btn btn-secondary">7</button> </td>
    <td><button type="button" class="btn btn-secondary">8</button> </td>
    <td><button type="button" class="btn btn-secondary">9</button> </td>
  </tr>
  <tr class="row">
    <td><button type="button" class="btn btn-secondary">ESC</button> </td>
    <td><button type="button" class="btn btn-secondary">0</button> </td>
    <td><button type="button" class="btn btn-secondary">MENU</button> </td>
  </tr>
  <tr class="row">
    <td><button type="button" class="btn btn-secondary">&nbsp;&nbsp;&nbsp;VOL UP &nbsp;&nbsp;&nbsp;</button> </td>
    <td></td>
    <td><button type="button" class="btn btn-secondary">&nbsp;&nbsp;&nbsp;CH UP&nbsp;&nbsp;&nbsp;</button> </td>
  </tr>
   <tr class="row">
    <td><button type="button" class="btn btn-secondary">VOL DOWN</button> </td>
    <td><button type="button" class="btn btn-secondary">UP</button> </td>
    <td><button type="button" class="btn btn-secondary">CH DOWN</button> </td>
  </tr>
   <tr class="row">
   
    <td><button type="button" class="btn btn-secondary">LEFT</button> </td>
	<td><button type="button" class="btn btn-secondary">ENTER</button> </td>
	<td><button type="button" class="btn btn-secondary">RIGHT</button> </td>
  </tr>
    </tr>
   <tr class="row">
	<td></td>
	<td><button type="button" class="btn btn-secondary">DOWN</button> </td>
	<td></td>
  </tr>
 
</table>
</div>
<div>
<center>
<select>
        <option selected="selected">Choose a Remote</option> 
EOL;

  $this->getRemoteBrands();
  echo <<<EOL
  </select>
  </center>
</div>
EOL;
		}
	}

?>




