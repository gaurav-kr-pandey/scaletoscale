function init() {
    try {
        save();
        copy();
        inspect();
    } catch (e) {
        log("Error while initializing : " + e);
    }
}

function save() {
    log('Saving...');
    document.addEventListener("keydown", function(t) {
            "s" === t.key && (navigator.platform.match("Mac") ? t.metaKey : t.ctrlKey) && t.preventDefault()
    }, false);
}

function inspect() {
    log('Inspecting...');
    document.oncontextmenu = function(e) {
       return false;
   }
   document.addEventListener("keydown", function(event) {
       event.ctrlKey && event.shiftKey && event.keyCode==73
   }, false);
}

function copy() {
    log('Copying...');
    window.addEventListener('selectstart', function(e) {
        e.preventDefault();
    });
}

function log(str){
    console.log(str);
}