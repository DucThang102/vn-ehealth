var express = require('express');
 var server = express();
 var ejs = require('ejs'); 
 ejs.open = '<%'; 
 ejs.close = '%>';


 var oneDay = 86400000;
 
server.set("view options", {layout: false});  
server.engine('html', require('ejs').renderFile); 
server.engine('js', require('ejs').renderFile); 
server.engine('css', require('ejs').renderFile); 
server.set('view engine', 'html');
server.set('views', __dirname + "/www");

server.all("*", function(req, res, next) {
    var request = req.params[0];
    if(request == '/') request = '/index.html';
    
    var pos = request.lastIndexOf('.');
    var ext = (pos > 0) ? request.substr(pos+1) : '';
    
    if((request.substr(0, 1) === "/") && (ext === "html" || ext == "js" || ext == "css")) {
        res.render(request.substr(1));
    }else {
        next();
    }

 });

 server.use(express.static(__dirname + '/www', { maxAge: oneDay }));

 server.listen(process.env.PORT || 8080);