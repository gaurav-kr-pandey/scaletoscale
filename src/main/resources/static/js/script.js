function toggleVisibility(id1, id2){
    var ele1 = document.getElementById(id1);
    var ele2 = document.getElementById(id2);
    ele1.style.display = "none";
    ele2.style.display = "block";
}
function hide(...args) {
  args.forEach(id => {
    document.getElementById(id).style.display='none';
    console.log("Hide ID : "+ id);
  });
}
function show(...args) {
  args.forEach(id => {
  document.getElementById(id).style.display='block';
  console.log("Show ID : "+ id);
  });
}

function isNotAuthorized(){
    try{
        var notAuthorized = document.getElementById('isNotAuthorized');
        if(notAuthorized) return true;
    }catch(err){
        return false;
    }
    return false;
}
