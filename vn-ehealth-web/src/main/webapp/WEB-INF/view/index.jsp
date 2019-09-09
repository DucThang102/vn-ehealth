<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%   
  Map[] pages = (Map[]) request.getAttribute("pages");
  String currentPage = (String) request.getAttribute("current_page"); 
  String mode = (String) request.getAttribute("mode");
  String htmlURL = (String) request.getAttribute("html_url");
  if(htmlURL == null) htmlURL = "";
  String pageDetail = "";
%>

<meta th:name="_csrf" th:content="${_csrf.token}" />
<meta th:name="_csrf_header" th:content="${_csrf.headerName}" />


<!DOCTYPE html>
<html lang="en">

<head>

  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>Hệ thống bệnh án điện tử</title>

  <!-- Custom fonts for this template-->
  <link href="/css/fontawesome/all.css" rel="stylesheet" type="text/css">
  <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

  <!-- Custom styles for this template-->
  <link href="/css/bootstrap.min.css" rel="stylesheet">
  <link href="/css/style.css" rel="stylesheet">
  <script src="/js/jquery.min.js"></script>
  <script src="/js/popper.min.js"></script>
  <script src="/js/bootstrap.min.js"></script>
  <script src="http://unpkg.com/vue@latest/dist/vue.js"></script>
  <script src="http://unpkg.com/bootstrap-vue@latest/dist/bootstrap-vue.js"></script>
  <script src="/js/common.js"></script>  

</head>

<body>
  <c:if test='<%= "normal".equals(mode) %>'>
    <form id="logoutForm" method="POST" action="${contextPath}/logout">
      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    </form>
    
    <nav id="topbar" class="navbar navbar-expand bg-xanh">
      <div class="navbar-header1">            
        <a class="navbar-brand1" href="/"><img src="/images/logo.png" height="50" alt=""></a>
        <h5 class="text3">Hệ thống</h5>
        <h4 class="text4 MT0 MB0">Bệnh án điện tử</h4>
      </div>

      <ul class="navbar-nav top-bar ml-auto">      
        <li class="nav-item dropdown no-arrow">
          <a class="nav-link" href="#">          
            <img class="img-profile rounded-circle" src="/images/MediaObj_Placeholder.png">
            <span class="ml-2 d-none d-lg-inline text-white">
              <c:if test="${pageContext.request.userPrincipal.name != null}">
                ${pageContext.request.userPrincipal.name}
              </c:if>
            </span>
          </a>        
        </li>
      
      <li class="nav-item dropdown no-arrow">
            <a class="nav-link" href="#" onclick="localStorage.removeItem('token'); document.forms['logoutForm'].submit()">
                <i class="fas fa-fw fa-sign-out-alt"></i>
            </a>
        </li>

      </ul>

    </nav>
    
    <nav id="menubar" class="navbar navbar-expand-lg navbar-light bg-white p-0">
      <ul class="navbar-nav">
        <li class="nav-item">
          <a class='nav-link' href='/'><i class="fas fa-lg fa-home black"></i></a>
        </li>
        
        <%for(Map pg : pages){
          if(pg.get("id").equals(currentPage)) 
            pageDetail = pg.containsKey("detail")? (String) pg.get("detail") : (String) pg.get("title");

          if(pg.containsKey("children")){
            List<Map> children = (List<Map>) pg.get("children");%>

            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle text-uppercase" href="#" id='<%= pg.get("id") %>_dropdown' data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                  <%= pg.get("title") %>
                </a>

                <div  class="dropdown-menu" aria-labelledby="<%= pg.get("id") %>_dropdown">
                    <% for(int index = 0; index < children.size(); index++) {
                      Map child_page = children.get(index);
                      if(child_page.get("id").equals(currentPage))
                        pageDetail = child_page.containsKey("detail")? (String) child_page.get("detail") : (String) child_page.get("title");%>                  
                        
                          <a class="dropdown-item" href='/-/<%= child_page.get("id") %>/<%= mode %>'>
                            <i class="fas fa-angle-right black"></i> <%= child_page.get("title") %>
                          </a>
                    <%}%>
                </div>
            </li>
          <%}else {%>
            <li class="nav-item">
              <a class="nav-link" href='/-/<%= pg.get("id") %>/<%= mode %>'>
                <%= pg.get("title") %>
              </a>
            </li>
          <%}
        }%>      		  
      </ul>
    </nav>   
    
    <div id="content-wrapper" class="bg-light">
      <div class="font-weight-bold text-uppercase"><%= pageDetail %></div>
      <div id="content" class="bg-white mt-2 pt-3">
          <c:if test='<%= !htmlURL.isEmpty() %>'>
            <c:import charEncoding="UTF-8" url='<%= htmlURL %>' />
          </c:if>      
      </div>
    </div>

    <footer class="sticky-footer bg-light">
      <div class="row">
        <div class="col-8">
        <h4 class="text-H">Bệnh viện Trung ương</h4>
          <ul class="list-inline">
            <li class="list-inline-item"><i class="LineH fa fa-phone pull-left" aria-hidden="true"></i> Hỗ trợ: 0909 999 999</li>
            <li class="list-inline-item"><i class="LineH fa fa-envelope pull-left" aria-hidden="true"></i> info@veig.vn</li>
          </ul>
        </div>
        <div class="col-4">
          <p class="mt-3 mr-3  text-right">Copyright © 2017 Công ty CP VEIG</p>
        </div>
      </div>
    </footer>
  </c:if>

  <c:if test='<%= !"normal".equals(mode) && !htmlURL.isEmpty() %>'>
    <c:import url='<%= htmlURL %>' />
  </c:if>
</body>
</html>
