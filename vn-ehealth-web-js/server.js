var express = require('express');
var server = express();
var ejs = require('ejs'); 
var cookieParser = require('cookie-parser');
var sync_req = require('sync-request');

ejs.open = '<%'; 
ejs.close = '%>';

var API_URL = 'http://34.69.63.157:8000';
var oneDay = 86400000;
 
server.set("view options", {layout: false});  
server.engine('html', require('ejs').renderFile); 
server.engine('js', require('ejs').renderFile); 
server.engine('css', require('ejs').renderFile); 
server.set('view engine', 'html');
server.set('views', __dirname + "/www");
server.use(cookieParser());

server.all("*", function(req, res, next) {
    var request = req.params[0];
    if(request == '/') request = '/index.html';

    var pos = request.lastIndexOf('.');
    var ext = (pos > 0) ? request.substr(pos+1) : '';
    
    if((request.substr(0, 1) === "/") && (ext === "html")) {
        var username = req.cookies['username'];
        if(username === '' || username === undefined) {
            res.render("login.html");
        }else {
            var page = request.substr(1, pos - 1);
            var check_permission_res = sync_req('GET', `${API_URL}/api/auth/check_page_permission?username=${username}&page=${page}`);
            if(check_permission_res.body.toString('utf-8') !== "true") {
                page = "unauthorized";
            }
            res.render(page + ".html");
        }
    } else if((request.substr(0, 1) === "/") && (ext == "js" || ext == "css")) {
        res.render(request.substr(1));
    }else {
        next();
    }

 });

 server.use(express.static(__dirname + '/www', { maxAge: oneDay }));

 server.listen(process.env.PORT || 8081);