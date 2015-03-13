<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<instore:template  menucolapse="false">
    <jsp:body>        
        <script type="text/javascript" charset="utf-8"  src="${url_js}angular.min.js"></script>
        <style>
            body {
                overflow: hidden;
            }

            .sidebar {
                display: block;
                width: 200px; 
                margin-top: -15px;
                margin-left: -30px;
                background-color: white;
                float: left;
            }

            .sidebarContent {
                display: block;
                width: 200px; 
                margin-top: -15px;
                margin-left: 10px;
                overflow-y: auto;
                overflow-x: hidden;
                float: left;
            }

            .sidebar h1 {
                display: block;
                width: 200px;
                font-size: 14px;
                line-height: 40px;
                text-indent: 20px;
                border-bottom: 1px solid #ebebeb;
            }

            .sidebar ul { list-style: none; margin-left: -40px; }
            .sidebar ul li { 
                margin-left: 20px; 
                padding: 4px; 
            }

            .sidebar ul li a { 
                color: #2494F2;
                font-weight: bold; 
            }

            .txtPF {
                display: block;
                display: block;
                width: 180px;
                height: 50px;
                padding: 20px;
                font-size: 12px;
                text-align: center;
                margin-top: 0px;
                margin-left: -20px;
                line-height: 0px;
                color: #73B843;
                font-weight: bold;
            }
            
            .well2 {
                display: block;
                width: 180px;
                height: 100px;
                
                padding: 20px;
                
                background-color: #FFF;
                margin-top: 40px;
            }
            
            .well2 i {
                display: block;
                width: 180px;
                height: 50px; 
                padding: 20px;
                font-size: 24px;
                text-align: center;
                margin-left: -20px;
                margin-top: -20px;
                color: #ef3e3e;
            }
        </style>
        <div ng-app="SidebarApp" ng-controller="SidebarController">
            <div class="sidebar">
                <h1>Meus perfis</h1>
                <ul>
                    <li ng-repeat="p in perfilList" ng-click="selecionarPerfil($index);">
                        <a href="#"><i class="fa fa-files-o"></i>&nbsp;&nbsp;{{p.nome}}</a>
                    </li>
                </ul>
            </div>

            <div class="sidebarContent">
                <div class="row">
                    <div class="col-md-2" ng-repeat="f in funcionalidades">
                        <a href="#">
                            <div class="well2">
                                <i class="fa {{f.icone}}"></i> <span class="txtPF">{{f.nome}}</span>
                            </div>
                        </a>
                    </div>
                </div>
                
                <div ng-if="funcionalidades.length <= 0" style="color: #000;"> Nenhuma permissão atribuida a este perfil </div>
            </div>
        </div>


        <script type="text/javascript">
            var angular = angular.module('SidebarApp', []);
            angular.controller('SidebarController', function ($scope) {
                $scope.perfilList = new Array();
                <c:forEach items="${perfis}" var="p">
                    $scope.perfilList.push({
                        nome: '${p.nome}',
                        funcionalidades: [
                            <c:forEach items="${p.funcionalidadeBeanList}" var="f">
                                    {mid: '${f.mappingId}', nome: '${f.nome}', icone: '${f.icone}'},
                            </c:forEach>
                        ]
                    });
                </c:forEach> 
                $scope.funcionalidades = new Array();
                $scope.selecionarPerfil = function (index) {
                    $scope.funcionalidades = $scope.perfilList[index].funcionalidades;
                }
            });
        </script>
        <script type="text/javascript">
            jQuery(document).ready(function () {
                function autosize() {
                    var h = jQuery(window).height();
                    var w = jQuery(window).width();
                    jQuery('.sidebar').css({
                        'height': (h - 130) + 'px'
                    });

                    jQuery('.sidebarContent').css({
                        'width': (w - 240) + 'px',
                        'height': (h - 130) + 'px'
                    });
                }
                autosize();
                jQuery(window).resize(function () {
                    autosize();
                });
            });
        </script>
    </jsp:body>
</instore:template>