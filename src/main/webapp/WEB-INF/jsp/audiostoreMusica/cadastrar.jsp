<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<%@ taglib prefix="cf" uri="CustomFunctions" %> 
<instore:template isGrid="false">
    <jsp:attribute name="submenu">
        <a href="${url}/audiostore-musica" class="btn btn-default"> <i class="fa fa-hand-o-left"></i> Musicas </a>
    </jsp:attribute>

    <jsp:body>



        <script type="text/javascript">
            jQuery(document).ready(function() {
                jQuery('.arquivosDeMusica').on('change', function() {
                    var valor = jQuery(this).val();
                    valor = valor.split('[|||SEPARADOR|||]');

                    jQuery('[name="audiostoreMusicaBean.titulo"]').val(valor[0]);
                    jQuery('[name="audiostoreMusicaBean.arquivo"]').val(valor[1]);
                });
            });
        </script>
        <form d="cad_cliente" method="POST" data-form="true" data-success-url="${url}/audiostore-musica">
            <input type="hidden" name="audiostoreMusicaBean.id" value="${audiostoreMusicaBean.id}" />

            <div class="row">

                <div class="col-md-6">
                    <div class="form-group">
                        <label>Importar</label>
                        <br />
                        <select  class="select2"  data-rule-required="true">
                            <c:forEach items="${arquivoMusicaList}" var="musica">
                                <option value="${musica.nome}[|||SEPARADOR|||]${musica.caminho}" >${musica.nome}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label>Titulo</label>
                        <input type="text" name="audiostoreMusicaBean.titulo" class="form-control" placeholder="Nome"  
                               data-rule-required="true" 
                               data-rule-minlength="3"
                               data-rule-maxlength="30" value="${audiostoreMusicaBean.titulo}">
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label>Arquivo</label>
                        <input type="text" name="audiostoreMusicaBean.arquivo" class="form-control" placeholder="Nome"  
                               data-rule-required="true" 
                               data-rule-minlength="3"
                               data-rule-maxlength="30" value="${audiostoreMusicaBean.arquivo}">
                    </div>
                </div>

            </div>


            <div class="row">
                <div class="col-md-4">
                    <div class="form-group">
                        <label>Gravadora</label>
                        <br />
                        <select class="select2" name="audiostoreMusicaBean.gravadora.id" data-rule-required="true" >
                            <c:forEach items="${gravadoraBeanList}" var="gravadora">
                                <option value="${gravadora.id}" ${gravadora.id eq audiostoreMusicaBean.gravadora.id ? 'selected="selected"' : ''}>${gravadora.nome}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="form-group">
                        <label>Categoria Primária</label> 
                        <br />
                        <select  class="select2" name="audiostoreMusicaBean.categoria1.codigo" data-rule-required="true" >
                            <c:forEach items="${categoriaBeanList}" var="cat">
                                <option value="${cat.codigo}" ${audiostoreMusicaBean.categoria1.codigo eq cat.codigo ? 'selected="selected"':''}>${cat.categoria}</option> 
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="form-group">
                        <label>Categoria Secundário</label> 
                        <br />
                        <select class="select2" name="audiostoreMusicaBean.categoria2.codigo" data-rule-required="true" >
                            <c:forEach items="${categoriaBeanList}" var="cat">
                                <option value="${cat.codigo}" ${audiostoreMusicaBean.categoria2.codigo eq cat.codigo ? 'selected="selected"':''}>${cat.categoria}</option> 
                            </c:forEach>
                        </select>
                    </div>
                </div>

            </div>

            <div class="row">
                <div class="col-md-4">
                    <div class="form-group">
                        <label>Dias execução <i>(Primária)</i></label>
                        <input type="text" name="audiostoreMusicaBean.diasExecucao1" class="form-control" placeholder="Nome"  
                               data-rule-required="true" 
                               data-rule-number="true" value="${audiostoreMusicaBean.diasExecucao1}">
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="form-group">
                        <label>Dias execução <i>(Secundário)</i></label>
                        <input type="text" name="audiostoreMusicaBean.diasExecucao2" class="form-control" placeholder="Nome"  
                               data-rule-required="true" 
                               data-rule-number="true" value="${audiostoreMusicaBean.diasExecucao2}">
                    </div>
                </div>
            </div>

            <br />
            <hr />

            <div class="row">
                <div class="col-md-3">
                    <div class="form-group">                        
                        <label>Renovação automatica do contrato</label>
                        <br />
                        <label class="radio-inline"> <input type="radio" class="icheck"  name="audiostoreMusicaBean.crossover" id="optionsRadios1" value="${true}" ${audiostoreMusicaBean.crossover ? 'checked="checked"' : ''} >&nbsp;Sim </label>
                        <label class="radio-inline"> <input type="radio" class="icheck"  name="audiostoreMusicaBean.crossover" id="optionsRadios1" value="${false}"  ${not audiostoreMusicaBean.crossover ? 'checked="checked"' : ''}>&nbsp;Não </label>
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label>Data vencimendo crossover</label>
                        <div class="input-group date datetime" data-min-view="2" data-date-format="dd/mm/yyyy">
                            <input type="text" name="audiostoreMusicaBean.dataVencimentoCrossover" class="form-control datepicker" placeholder="Nome"  
                                   data-rule-required="true" 
                                   data-mask="99/99/9999" value="${audiostoreMusicaBean.dataVencimentoCrossover}">
                            <span class="input-group-addon btn btn-primary"><span class="glyphicon glyphicon-th"></span></span>
                        </div>
                    </div>
                </div>
            </div>


            <br />

            <b>Intérprete</b>
            <hr />

            <div class="row">
                <div class="col-md-4">
                    <div class="form-group">
                        <label>Nome </label>
                        <input type="text" name="audiostoreMusicaBean.interprete" class="form-control" placeholder="Nome"  
                               data-rule-required="true" 
                               data-rule-minlength="3"
                               data-rule-maxlength="30" value="${audiostoreMusicaBean.interprete}">
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="form-group">
                        <label>Tipo</label> 
                        <br />
                        <select class="select2" name="audiostoreMusicaBean.tipoInterprete" data-rule-required="true" >
                            <option value="1" ${audiostoreMusicaBean.tipoInterprete eq 1 ? 'selected="selected"' : ''} >Masculino</option> 
                            <option value="2" ${audiostoreMusicaBean.tipoInterprete eq 2 ? 'selected="selected"' : ''} >Feminino</option> 
                            <option value="3" ${audiostoreMusicaBean.tipoInterprete eq 3 ? 'selected="selected"' : ''} >Grupo</option> 
                            <option value="4" ${audiostoreMusicaBean.tipoInterprete eq 4 ? 'selected="selected"' : ''} >Instrumental</option> 
                            <option value="5" ${audiostoreMusicaBean.tipoInterprete eq 5 ? 'selected="selected"' : ''} >Jingle</option> 
                        </select>
                    </div>
                </div>

                <div class="col-md-4"></div>
                <div class="col-md-12">
                    <br />
                    <b> Afinidades/Informações para streaming </b>
                    <hr />
                </div>

                <div class="col-md-12">
                    <div class="form-group">
                        <label></label>
                        <input type="text" name="audiostoreMusicaBean.afinidade1" class="form-control" placeholder="primário"  
                               value="${audiostoreMusicaBean.afinidade1}">
                    </div>
                </div>
                <div class="col-md-12">
                    <div class="form-group">
                        <label></label>
                        <input type="text" name="audiostoreMusicaBean.afinidade2" class="form-control" placeholder="secundário"  
                               value="${audiostoreMusicaBean.afinidade2}">
                    </div>
                </div>
                <div class="col-md-12">
                    <div class="form-group">
                        <label></label>
                        <input type="text" name="audiostoreMusicaBean.afinidade3" class="form-control" placeholder="terciário"  
                               value="${audiostoreMusicaBean.afinidade3}">
                    </div>
                </div>
                <div class="col-md-12">
                    <div class="form-group">
                        <label></label>
                        <input type="text" name="audiostoreMusicaBean.afinidade4" class="form-control" placeholder="quaternário"  
                               value="${audiostoreMusicaBean.afinidade4}">
                    </div>
                </div>
            </div>

            <br />
            <hr />

            <div class="row">
                <div class="col-md-2">
                    <div class="form-group">
                        <label>Tempo</label>
                        <input type="text" name="tempoTotal" class="form-control" placeholder="Nome"  
                               data-mask="99:99:99"  
                               data-rule-required="true" 
                               value="${audiostoreMusicaBean.tempoTotal}">
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="form-group">
                        <label>Quantidade </label>

                        <input type="text" name="audiostoreMusicaBean.qtdePlayer" class="form-control span2" placeholder="Quantidade máxima de execuções no dia)"  
                               data-rule-required="true" 
                               data-rule-number="true" value="${audiostoreMusicaBean.qtdePlayer}">
                    </div>
                </div>

                <div class="col-md-2">
                    <div class="form-group">
                        <label>Ultima  execução</label>
                        <div class="input-group date datetime" data-min-view="2" data-date-format="dd/mm/yyyy">
                            <input type="text" name="audiostoreMusicaBean.ultimaExecucao" class="form-control datepicker" placeholder="Nome"  
                                   data-mask="99/99/9999"
                                   data-rule-maxlength="30" value="${audiostoreMusicaBean.ultimaExecucao}">
                            <span class="input-group-addon btn btn-primary"><span class="glyphicon glyphicon-th"></span></span>
                        </div>
                    </div>
                </div>

                <div class="col-md-2">
                    <div class="form-group">
                        <label>Frame inicial</label>
                        <input type="text" name="audiostoreMusicaBean.frameInicio" class="form-control" placeholder="Nome"  
                               data-rule-required="true" 
                               data-rule-number="true" value="${audiostoreMusicaBean.frameInicio}">
                    </div>
                </div>

                <div class="col-md-2">
                    <div class="form-group">
                        <label>Frame final</label>
                        <input type="text" name="audiostoreMusicaBean.frameFinal" class="form-control" placeholder="Nome"  
                               data-rule-required="true" 
                               data-rule-number="true" value="${audiostoreMusicaBean.frameFinal}">
                    </div>
                </div>
            </div>

            <br />
            <hr />

            <div class="row">
                <div class="col-md-2">
                    <div class="form-group">
                        <label>Data de vencimento</label>
                        <div class="input-group date datetime" data-min-view="2" data-date-format="dd/mm/yyyy">
                            <input type="text" name="audiostoreMusicaBean.dataVencimento" class="form-control datepicker" placeholder="Nome"  
                                   data-rule-required="true" 
                                   data-mask="99/99/9999" value="${cf:dateFormat(audiostoreMusicaBean.dataVencimento, "dd/MM/yyyy")}">
                            <span class="input-group-addon btn btn-primary"><span class="glyphicon glyphicon-th"></span></span>
                        </div>
                    </div>
                </div>
                <div class="col-md-2">
                    <div class="form-group">
                        <label>Data de inclusao</label>

                        <div class="input-group date datetime" data-min-view="2" data-date-format="dd/mm/yyyy">
                            <input type="text" name="audiostoreMusicaBean.data" class="form-control datepicker" placeholder="Nome"  
                                   data-rule-required="true" 
                                   data-mask="99/99/9999"value="${cf:dateFormat(audiostoreMusicaBean.data, "dd/MM/yyyy")}">
                            <span class="input-group-addon btn btn-primary"><span class="glyphicon glyphicon-th"></span></span>
                        </div>
                    </div>
                </div>



                <div class="col-md-2">
                    <div class="form-group">
                        <label>Velocidade</label> 
                        <br />
                        <select class="select2" name="audiostoreMusicaBean.velocidade" data-rule-required="true" >
                            <option value="1" ${audiostoreMusicaBean.velocidade eq 1 ? 'selected="selected"':''} >Lento</option> 
                            <option value="2" ${audiostoreMusicaBean.velocidade eq 2 ? 'selected="selected"':''}>Normal</option> 
                            <option value="3" ${audiostoreMusicaBean.velocidade eq 3 ? 'selected="selected"':''}>Rápido</option> 
                        </select>
                    </div>
                </div>

                <div class="col-md-2">
                    <div class="form-group">
                        <label>Ano de gravação</label> 
                        <br />
                        <select class="select2" name="audiostoreMusicaBean.anoGravacao" data-rule-required="true" >
                            <c:forEach begin="1980" end="2014" varStatus="vs">
                                <option value="${vs.index}" ${audiostoreMusicaBean.anoGravacao eq vs.index ? 'selected="selected"':''}>${vs.index}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group">
                        <label>Tipo</label> 
                        <label class="radio-inline"> <input type="radio" class="icheck"  name="audiostoreMusicaBean.cut" id="optionsRadios1" value="${true}" ${audiostoreMusicaBean.cut ? 'checked="checked"' : ''} >&nbsp;Cut </label>
                        <label class="radio-inline"> <input type="radio" class="icheck"  name="audiostoreMusicaBean.cut" id="optionsRadios1" value="${false}"  ${not audiostoreMusicaBean.cut ? 'checked="checked"' : ''}>&nbsp;FadeOut </label>
                    </div>
                </div>
            </div>

            <button type="submit" class="btn btn-default">
                <i class="fa fa-save"></i> Salvar
            </button>
        </form>

    </jsp:body>
</instore:template>