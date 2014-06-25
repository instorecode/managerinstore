<%@tag description="put the tag description here" pageEncoding="UTF-8"%>

<noscript>
<style type="text/css">
    .yesscript { display: none; }
    body { 
        background: url('${url_img}bg-error.jpg')  no-repeat center center fixed;
        background-size: 100% 100%;
    }
    .noscript {
        display: block;
        width: 100%;
        height: 100px;
        padding-top: 5px;
        background-color: rgba(0,0,0,0.2) !important;            
        border-top:1px solid rgba(0,0,0,0.05);
        border-bottom:1px solid rgba(0,0,0,0.05);
        color: #FFF;
        font-size: 14px;
        font-weight: bold;
        text-transform: uppercase;

        position: absolute;
        top: 50%;
        margin-top: -50px;
    }
    .noscript i {
        font-size: 60px;
    }
</style>
<div class="noscript">
    <center>
        <span> 
            <i class="fa fa-exclamation-triangle"></i>  
            <br />
            Ops! È necessário que o JAVASCRIPT esteja HABILITADO em seu navegador!
        </span>
    </center>
</div>
</noscript>