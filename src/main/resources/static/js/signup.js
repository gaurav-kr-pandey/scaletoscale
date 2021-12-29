i = 60;
function onTimer(timerId,btnId) {
    var otpBtnTimer = document.getElementById(timerId);
    var otpBtn = document.getElementById(btnId);
    otpBtnTimer.innerHTML = 'OTP sent to your mail, please use that OTP to verify your mail id. You can resend OTP in <strong>' +i+ '</strong> seconds';
    i--;
  if (i < 0) {
    otpBtn.style.display = "block";
    otpBtnTimer.style.display = "none";
    i=60;
  }
  else {
    otpBtn.style.display = "none";
    otpBtnTimer.style.display = "block";
    setTimeout(function() { onTimer(timerId,btnId); }, 1000);
  }
}
function sendOtp(emailInput,timerId,btnId) {
  const xhttp = new XMLHttpRequest();
  var email = document.getElementById(emailInput).value;
  console.log(email+' : '+validateEmail(email));
  if(validateEmail(email))
    xhttp.open("GET", "/email/otp?email="+email);
  else
    alert("Invalid Email "+email);
  xhttp.send();
  if(i<60){
    i=0;
    i=60;
  }
  onTimer(timerId,btnId);
}

function validateEmail(email) {
  const re = /\S+@\S+\.\S+/;
  return re.test(email);
}

function f1() {
     try{
        var ele = document.getElementsByTagName("BODY")[0];
        var id1 = ele.getAttribute('data-id1');
        var id2 = ele.getAttribute('data-id2');
        var id3 = ele.getAttribute('data-id3');
        var id4 = ele.getAttribute('data-id4');
        console.log(id1+' '+id2+' '+id3+' '+id4);
        toggleForm(id1,id2,id3,id4);
        document.getElementById('myOtpTimer').style.display = "none";
        document.getElementById('fpOtpTimer').style.display = "none";
     }catch(e){
        console.log(e);
     }
 }

 function toggleForm(id1,id2,id3,id4) {
    var x = document.getElementById(id1);
    var y = document.getElementById(id2);
    var i = document.getElementById(id3);
    var j = document.getElementById(id4);
    x.style.display = "block";
    y.style.display = "none";
    i.setAttribute("class", "btn btn-lg btn-dark");
    j.setAttribute("class", "btn btn-lg btn-outline-dark");
 }
   