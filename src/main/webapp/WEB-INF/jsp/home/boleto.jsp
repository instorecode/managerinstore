<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set scope="session" var="url" value="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}"></c:set>
<c:set scope="session" var="url_resources" value="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/resources/"></c:set>
<c:set scope="session" var="url_css" value="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/resources/css/"></c:set>
<c:set scope="session" var="url_js" value="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/resources/js/"></c:set>
<c:set scope="session" var="url_img" value="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/resources/img/"></c:set>
<c:set scope="session" var="url_cz" value="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/resources/cz/"></c:set>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="pt" lang="pt">
    <head>
        <!-- Sistema para gera&ccedil;&atilde;o de boletos. http://www.boletobancario.com - Licenciado para: www.boletobancario.com (Polvo Digital). Vers&atilde;o: 3.7.0 (10/06/2013) -->
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
        <title>Boleto Bradesco</title>

        <style type="text/css">
            body
            {
                background-color: #FFF;
                color: #000;
                cursor: default;
                font: 8px Verdana, Arial, Helvetica, sans-serif !important;
                width: 800px;
            }
            @page
            {
                margin-bottom: 1cm;
                margin-left: 0.5cm;
                margin-right: 0.5cm;
                margin-top: 1cm;
            }
            @media screen
            {
                body
                {
                    margin: 10px;
                }
            }
            h1
            {
                float: left;
                font-size: 15px;
                margin: 0px;
                text-align: left;
                margin-top: 4px;
            }
            h2
            {
                float: right;
                font-size: 12px;
                margin: 0px;
                text-align: right;
                vertical-align: bottom;
                margin-top: 4px;
            }
            hr
            {
                border-top: 1px dashed #000;
                border-width: 0;
                clear: left;
                float: left;
                height: 1px;
                margin: 10px 0px;
                width: 100%;
            }
            hr.solid
            {
                border-top: 1px solid #000;
                margin: 2px 0;
            }
            p.aut
            {
                float: right;
                font-size: 10px;
                margin: 0;
                text-align: right;
                width: 100%;
            }
            table
            {
                border-collapse: collapse;
                float: left;
                width: 100%;
            }
            table tr td
            {
                font-size: 11px;
            }
            table.tab
            {
                border: 1px solid #000;
            }
            table.tab td
            {
                border: 1px solid #000;
                padding: 0px 2px 0px 2px;
                text-align: left;
                vertical-align: top;
            }
            table.rec td
            {
                width: 25%;
            }
            table.tab td span, table.infRec td span
            {
                display: block;
                font-size: 10px;
                padding: 0px 5px 0px 5px;
            }
            table.tab td span.vV
            {
                font-size: 12px;
                font-weight: bold;
                text-align: center;
            }
            table.infRec td
            {
                border: 0 !important;
                vertical-align: top;
            }
            table.infRec td.sacAv
            {
                width: 60px;
            }
            table.infRec td.autMecF
            {
                text-align: right;
            }
            table.infRec td.codBaixa
            {
                text-align: right;
                vertical-align: bottom;
            }
            table.tab table.infRec td
            {
                height: auto;
                padding-bottom: 0px !important;
            }
            table .lD
            {
                text-align: right !important;
            }
            div.barra
            {
                display: inline;
                float: left;
                margin: 5px 0px 15px 20px;
                width: 600px;
            }
            div.barra div
            {
                display: block;
                float: left;
                height: 50px;
                width: 0px;
            }
            div.b0
            {
                border-right: 1px solid #FFF;
            }
            div.b1
            {
                border-right: 3px solid #FFF;
            }
            div.p0
            {
                border-right: 1px solid #000;
            }
            div.p1
            {
                border-right: 3px solid #000;
            }
            div.break
            {
                page-break-after: always;
            }
            div.custom, div.mestre
            {
                clear: left;
                display: block;
                width: 100%;
            }
            div.custom
            {
                font-size: 12px;
            }
            div.mestre
            {
                border: 1px solid #FFF;
            }
            .logoBanco {
                max-width: 24px;
                float: left;
                margin-bottom: 5px;
                margin-right: 5px;
            }
        </style>
    </head>
    <body>
        <div class="mestre">
            <img class="logoBanco" src="${url_img}/logoBancoBradesco.png" /> <h1>Bradesco | 237-2 |</h1>
            <h2>23790.12301 60000.000053 25000.456704 7 61330000013580</h2>
            <table class="tab ficha">
                <tr>
                    <td colspan="5">Local de Pagamento<br /><span> <b>Pagável Preferencialmente na rede Bradesco ou no Bradesco Expresso</b> </span></td>
                    <td>Vencimento<br /><span class="vV lD">23/07/2014</span></td>
                </tr>
                <tr>
                    <td colspan="5">Benefici&aacute;rio<br /><span> <b> INSTORE AUDIOVISUAL LTDA EPP</b> </span></td>
                    <td>Ag&ecirc;ncia/C&oacute;digo do Benefici&aacute;rio<br /><span class="lD"><b>0123-6/0004567-5</b></span></td>
                </tr>
                <tr>
                    <td>
                        Dara do Doc.
                        <br />
                        <span class="lD"><b> 01/01/2014 </b></span>
                    </td>
                    <td>
                        Nº do documento
                        <br />
                        <span class="lD"><b> 000001545 </b></span>
                    </td>
                    <td>
                        Espécie doc.
                        <br />
                        <span class="lD"><b> DS </b></span>
                    </td>
                    <td>
                        Aceite
                        <br />
                        <span class="lD"><b> N </b></span>
                    </td>
                    <td>
                        Data Proces
                        <br />
                        <span class="lD"><b> 01/01/2014</b></span>
                    </td>
                    <td>
                        Nosso Número
                        <br />
                        <span class="lD"><b>06/00000000525-P</b></span>
                    </td>
                </tr>
                <tr>
                    <td>
                        Uso do Banco
                        <br />
                        <span class="lD"><b>  </b></span>
                    </td>
                    <td>
                        Carteira
                        <br />
                        <span class="lD"><b> 9 </b></span>
                    </td>
                    <td>
                        Espécie
                        <br />
                        <span class="lD"><b> R$ </b></span>
                    </td>
                    <td>
                        Quantidade
                        <br />
                        <span class="lD"><b>  </b></span>
                    </td>
                    <td>
                        Valor
                        <br />
                        <span class="lD"><b> </b></span>
                    </td>
                    <td>
                        (=)Valor do documento
                        <br />
                        <span class="lD"><b>77,11</b></span>
                    </td>
                </tr>


                <tr>
                    <td colspan="6" rowspan="4">
                        <table class="infRec">
                            <tr>
                                <td class="sacAv">Pagador</td>
                                <td colspan="3"><span><b>Beltrano de Tal - CPF/CNPJ: 777.999.333-88<br />R. Silas Salazar, 768 - 8&ordm; Andar - 12345-678 S&atilde;o Paulo-SP<br />&nbsp;</b></span></td>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                            </tr>
                            <tr>
                                <td class="sacAv">Sacador/Avalista</td>
                                <td colspan="2"><span>&nbsp;</span></td>
                                <td class="codBaixa">
                                    <img src="${url_img}/iso9001.png" style="max-width: 56px; margin-bottom: 10px; margin-right: 10px; margin-top: -30px;" />
                                    <br />
                                    <b style="font-size: 12px;">Recibo do Pagador</b>
                                    <br />
                                    <br />
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table >
            <hr />
            <hr style="border-top: 2px dashed #000;"/>
            <hr />

            <img class="logoBanco" src="${url_img}/logoBancoBradesco.png" />  <h1>Bradesco | 237-2 |</h1>
            <h2>23790.12301 60000.000053 25000.456704 7 61330000013580</h2>
            <table class="tab ficha">
                <tr>
                    <td colspan="5">Local de Pagamento<br /><span> <b>Pagável Preferencialmente na rede Bradesco ou no Bradesco Expresso</b> </span></td>
                    <td>Vencimento<br /><span class="vV lD">23/07/2014</span></td>
                </tr>
                <tr>
                    <td colspan="5">Benefici&aacute;rio<br /><span> <b> INSTORE AUDIOVISUAL LTDA EPP</b> </span></td>
                    <td>Ag&ecirc;ncia/C&oacute;digo do Benefici&aacute;rio<br /><span class="lD"><b>0123-6/0004567-5</b></span></td>
                </tr>
                <tr>
                    <td>
                        Dara do Doc.
                        <br />
                        <span class="lD"><b> 01/01/2014 </b></span>
                    </td>
                    <td>
                        Nº do documento
                        <br />
                        <span class="lD"><b> 000001545 </b></span>
                    </td>
                    <td>
                        Espécie doc.
                        <br />
                        <span class="lD"><b> DS </b></span>
                    </td>
                    <td>
                        Aceite
                        <br />
                        <span class="lD"><b> N </b></span>
                    </td>
                    <td>
                        Data Proces
                        <br />
                        <span class="lD"><b> 01/01/2014</b></span>
                    </td>
                    <td>
                        Nosso Número
                        <br />
                        <span class="lD"><b>06/00000000525-P</b></span>
                    </td>
                </tr>
                <tr>
                    <td>
                        Uso do Banco
                        <br />
                        <span class="lD"><b>  </b></span>
                    </td>
                    <td>
                        Carteira
                        <br />
                        <span class="lD"><b> 9 </b></span>
                    </td>
                    <td>
                        Espécie
                        <br />
                        <span class="lD"><b> R$ </b></span>
                    </td>
                    <td>
                        Quantidade
                        <br />
                        <span class="lD"><b>  </b></span>
                    </td>
                    <td>
                        Valor
                        <br />
                        <span class="lD"><b> </b></span>
                    </td>
                    <td>
                        (=)Valor do documento
                        <br />
                        <span class="lD"><b>77,11</b></span>
                    </td>
                </tr>


                <tr>
                    <td colspan="5" rowspan="5">
                        <table class="infRec">
                            <tr>
                                <td colspan="7" class="sacAv">Instruçoes (Texto de responsabilidade do beneficiário)</td>
                                <td ></td>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                            </tr>
                            <tr>
                                <td class="sacAv">
                                    <b>
                                        ** VALORES EXPRESSOS EM REAIS ****
                                        MORADIA/COM.PERMANENC...........00,7
                                        APOS 28.05.2014 MULTA............1,54
                                    </b>
                                </td>
                                <td colspan="2"><span>&nbsp;</span></td>
                                <td class="codBaixa">
                                </td>
                            </tr>
                        </table>
                    </td>
                    <td>(-) Desconto/Abatimento
                        <br />
                        <span class="lD">&nbsp;</span>
                    </td>
                </tr>

                <tr>
                    <td>(-) Outras Deduções
                        <br />
                        <span class="lD">&nbsp;</span>
                    </td>
                </tr>
                <tr>
                    <td>(+) Mora/Multa
                        <br />
                        <span class="lD">&nbsp;</span>
                    </td>
                </tr>
                <tr>
                    <td>(+) Outros Acrécimos
                        <br />
                        <span class="lD">&nbsp;</span>
                    </td>
                </tr>
                <tr>
                    <td>(=) Valor Cobrado
                        <br />
                        <span class="lD">&nbsp;</span>
                    </td>
                </tr>

                <tr>
                    <td colspan="6" rowspan="4">
                        <table class="infRec">
                            <tr>
                                <td class="sacAv">Pagador</td>
                                <td colspan="3"><span><b>Beltrano de Tal - CPF/CNPJ: 777.999.333-88<br />R. Silas Salazar, 768 - 8&ordm; Andar - 12345-678 S&atilde;o Paulo-SP<br />&nbsp;</b></span></td>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                            </tr>
                            <tr>
                                <td class="sacAv">Sacador/Avalista</td>
                                <td colspan="2"><span>&nbsp;</span></td>
                                <td class="codBaixa">
                                    <img src="${url_img}/iso9001.png" style="max-width: 56px; margin-bottom: 10px; margin-right: 10px; margin-top: -30px;" />
                                    <br />
                                    <b style="font-size: 12px;">Ficha de Compensação</b>
                                    <br />
                                    <br />
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>

            </table >
            <p class="aut">Autentica&ccedil;&atilde;o Mec&acirc;nica - Ficha de Compensa&ccedil;&atilde;o</p>
            <div class="barra">
                <div class="p0"></div>
                <div class="b0"></div>
                <div class="p0"></div>
                <div class="b0"></div>
                <div class="p0"></div>
                <div class="b1"></div>
                <div class="p1"></div>
                <div class="b1"></div>
                <div class="p0"></div>
                <div class="b0"></div>
                <div class="p0"></div>
                <div class="b0"></div>
                <div class="p1"></div>
                <div class="b0"></div>
                <div class="p0"></div>
                <div class="b0"></div>
                <div class="p0"></div>
                <div class="b1"></div>
                <div class="p0"></div>
                <div class="b0"></div>
                <div class="p1"></div>
                <div class="b1"></div>
                <div class="p1"></div>
                <div class="b0"></div>
                <div class="p0"></div>
                <div class="b0"></div>
                <div class="p0"></div>
                <div class="b1"></div>
                <div class="p0"></div>
                <div class="b1"></div>
                <div class="p1"></div>
                <div class="b0"></div>
                <div class="p1"></div>
                <div class="b0"></div>
                <div class="p1"></div>
                <div class="b1"></div>
                <div class="p0"></div>
                <div class="b1"></div>
                <div class="p0"></div>
                <div class="b0"></div>
                <div class="p0"></div>
                <div class="b0"></div>
                <div class="p1"></div>
                <div class="b0"></div>
                <div class="p1"></div>
                <div class="b0"></div>
                <div class="p1"></div>
                <div class="b0"></div>
                <div class="p0"></div>
                <div class="b1"></div>
                <div class="p0"></div>
                <div class="b1"></div>
                <div class="p0"></div>
                <div class="b0"></div>
                <div class="p0"></div>
                <div class="b0"></div>
                <div class="p0"></div>
                <div class="b0"></div>
                <div class="p1"></div>
                <div class="b1"></div>
                <div class="p1"></div>
                <div class="b1"></div>
                <div class="p0"></div>
                <div class="b0"></div>
                <div class="p0"></div>
                <div class="b0"></div>
                <div class="p0"></div>
                <div class="b0"></div>
                <div class="p1"></div>
                <div class="b1"></div>
                <div class="p1"></div>
                <div class="b1"></div>
                <div class="p0"></div>
                <div class="b0"></div>
                <div class="p1"></div>
                <div class="b1"></div>
                <div class="p0"></div>
                <div class="b1"></div>
                <div class="p0"></div>
                <div class="b0"></div>
                <div class="p0"></div>
                <div class="b0"></div>
                <div class="p1"></div>
                <div class="b0"></div>
                <div class="p1"></div>
                <div class="b1"></div>
                <div class="p0"></div>
                <div class="b0"></div>
                <div class="p1"></div>
                <div class="b0"></div>
                <div class="p0"></div>
                <div class="b1"></div>
                <div class="p0"></div>
                <div class="b0"></div>
                <div class="p0"></div>
                <div class="b0"></div>
                <div class="p0"></div>
                <div class="b0"></div>
                <div class="p1"></div>
                <div class="b1"></div>
                <div class="p1"></div>
                <div class="b1"></div>
                <div class="p0"></div>
                <div class="b0"></div>
                <div class="p1"></div>
                <div class="b0"></div>
                <div class="p0"></div>
                <div class="b1"></div>
                <div class="p0"></div>
                <div class="b0"></div>
                <div class="p0"></div>
                <div class="b0"></div>
                <div class="p1"></div>
                <div class="b1"></div>
                <div class="p1"></div>
                <div class="b0"></div>
                <div class="p1"></div>
                <div class="b0"></div>
                <div class="p0"></div>
                <div class="b1"></div>
                <div class="p0"></div>
                <div class="b1"></div>
                <div class="p0"></div>
                <div class="b0"></div>
                <div class="p0"></div>
                <div class="b0"></div>
                <div class="p1"></div>
                <div class="b0"></div>
                <div class="p1"></div>
                <div class="b1"></div>
                <div class="p0"></div>
                <div class="b1"></div>
                <div class="p0"></div>
                <div class="b0"></div>
                <div class="p0"></div>
                <div class="b0"></div>
                <div class="p0"></div>
                <div class="b0"></div>
                <div class="p1"></div>
                <div class="b1"></div>
                <div class="p1"></div>
                <div class="b1"></div>
                <div class="p0"></div>
                <div class="b0"></div>
                <div class="p0"></div>
                <div class="b0"></div>
                <div class="p0"></div>
                <div class="b0"></div>
                <div class="p1"></div>
                <div class="b1"></div>
                <div class="p1"></div>
                <div class="b1"></div>
                <div class="p0"></div>
                <div class="b0"></div>
                <div class="p0"></div>
                <div class="b0"></div>
                <div class="p0"></div>
                <div class="b0"></div>
                <div class="p1"></div>
                <div class="b1"></div>
                <div class="p1"></div>
                <div class="b1"></div>
                <div class="p0"></div>
                <div class="b0"></div>
                <div class="p0"></div>
                <div class="b1"></div>
                <div class="p0"></div>
                <div class="b0"></div>
                <div class="p1"></div>
                <div class="b1"></div>
                <div class="p1"></div>
                <div class="b0"></div>
                <div class="p0"></div>
                <div class="b0"></div>
                <div class="p0"></div>
                <div class="b1"></div>
                <div class="p1"></div>
                <div class="b0"></div>
                <div class="p0"></div>
                <div class="b1"></div>
                <div class="p0"></div>
                <div class="b0"></div>
                <div class="p1"></div>
                <div class="b0"></div>
                <div class="p0"></div>
                <div class="b0"></div>
                <div class="p0"></div>
                <div class="b0"></div>
                <div class="p1"></div>
                <div class="b1"></div>
                <div class="p1"></div>
                <div class="b1"></div>
                <div class="p0"></div>
                <div class="b0"></div>
                <div class="p0"></div>
                <div class="b0"></div>
                <div class="p0"></div>
                <div class="b0"></div>
                <div class="p1"></div>
                <div class="b1"></div>
                <div class="p1"></div>
                <div class="b0"></div>
                <div class="p0"></div>
                <div class="b1"></div>
                <div class="p1"></div>
                <div class="b0"></div>
                <div class="p0"></div>
                <div class="b1"></div>
                <div class="p1"></div>
                <div class="b1"></div>
                <div class="p0"></div>
                <div class="b0"></div>
                <div class="p0"></div>
                <div class="b0"></div>
                <div class="p0"></div>
                <div class="b0"></div>
                <div class="p0"></div>
                <div class="b0"></div>
                <div class="p0"></div>
                <div class="b1"></div>
                <div class="p1"></div>
                <div class="b1"></div>
                <div class="p1"></div>
                <div class="b0"></div>
                <div class="p1"></div>
                <div class="b0"></div>
                <div class="p0"></div>
                <div class="b1"></div>
            </div>
            <hr />
        </div>
    </body>
</html>


