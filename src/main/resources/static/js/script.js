function toggleVisibility(id1, id2){
    var ele1 = document.getElementById(id1);
    var ele2 = document.getElementById(id2);
    ele1.style.display = "none";
    ele2.style.display = "block";
}
function hide(...args) {
  args.forEach(id => document.getElementById(id).style.display='none')
}
function show(...args) {
  args.forEach(id => document.getElementById(id).style.display='block')
}