<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="instore" tagdir="/WEB-INF/tags/" %> 
<%@ taglib prefix="cf" uri="CustomFunctions" %> 
<instore:template isGrid="false">
    <jsp:attribute name="submenu">
        <a href="${url}/audiostore-musica" class="btn btn-default"> <i class="fa fa-hand-o-left"></i> Vozes </a>
    </jsp:attribute>

    <jsp:body>
        <form d="cad_cliente" method="POST" data-form="true" data-success-url="${url}/audiostore-musica">
            <input type="hidden" name="audiostoreMusicaBean.id" value="${audiostoreMusicaBean.id}" />

            <div class="row">
                <div class="col-md-4">
                    <div class="form-group">
                        <label>Titulo</label>
                        <input type="text" name="audiostoreMusicaBean.nome" class="form-control" placeholder="Nome"  
                               data-rule-required="true" 
                               data-rule-minlength="3"
                               data-rule-maxlength="30" value="${audiostoreMusicaBean.nome}">
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="form-group">
                        <label>Arquivo</label>
                        <input type="text" name="audiostoreMusicaBean.nome" class="form-control" placeholder="Nome"  
                               data-rule-required="true" 
                               data-rule-minlength="3"
                               data-rule-maxlength="30" value="${audiostoreMusicaBean.nome}">
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="form-group">
                        <label>Gravadora</label>
                        <br />
                        <select data-selectradio="true" class="form-control" name="audiostoreMusicaBean.gravadora.id" data-rule-required="true" >
                            <c:forEach items="${gravadoraBeanList}" var="gravadora">
                                <option value="${gravadora.id}" ${gravadora.id eq audiostoreMusicaBean.gravadora.id ? 'selected="selected"' : ''}>${gravadora.nome}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>

            <br />

            <b>Categorias</b>
            <hr />

            <div class="row">
                <div class="col-md-4">
                    <div class="form-group">
                        <label>Primária</label> 
                        <br />
                        <select data-selectradio="true"  class="form-control" name="audiostoreMusicaBean.categoria1.codigo" data-rule-required="true" >
                            <c:forEach items="${categoriaBeanList}" var="cat">
                                <option value="${cat.codigo}">${cat.categoria}</option> 
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="form-group">
                        <label>Secundário</label> 
                        <br />
                        <select data-selectradio="true"  class="form-control" name="audiostoreMusicaBean.categoria2.codigo" data-rule-required="true" >
                            <c:forEach items="${categoriaBeanList}" var="cat">
                                <option value="${cat.codigo}">${cat.categoria}</option> 
                            </c:forEach>
                        </select>
                    </div>
                </div>

            </div>

            <div class="row">
                <div class="col-md-4">
                    <div class="form-group">
                        <label>Dias execução <i>(Primária)</i></label>
                        <input type="text" name="audiostoreMusicaBean.diaExecucao1" class="form-control" placeholder="Nome"  
                               data-rule-required="true" 
                               data-mask="?99999" value="${audiostoreMusicaBean.diaExecucao1}">
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="form-group">
                        <label>Dias execução <i>(Secundário)</i></label>
                        <input type="text" name="audiostoreMusicaBean.diaExecucao2" class="form-control" placeholder="Nome"  
                               data-rule-required="true" 
                               data-mask="?99999" value="${audiostoreMusicaBean.diaExecucao2}">
                    </div>
                </div>
            </div>

            <br />
            <hr />

            <div class="row">
                <div class="col-md-2">
                    <div class="form-group">
                        <label>CrossOver</label> 
                        <br />
                        <select data-selectradio="true"  class="form-control" name="audiostoreMusicaBean.crossover" data-rule-required="true" >
                            <option value="${true}">Sim</option> 
                            <option value="${false}">Não</option> 
                        </select>
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="form-group">
                        <label>Data vencimendo crossover</label>
                        <input type="text" name="audiostoreMusicaBean.dataVencimentoCrossover" class="form-control datepicker" placeholder="Nome"  
                               data-rule-required="true" 
                               data-mask="99/99/9999" value="${audiostoreMusicaBean.dataVencimentoCrossover}">
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
                               data-rule-maxlength="30" value="${audiostoreMusicaBean.nome}">
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="form-group">
                        <label>Tipo</label> 
                        <br />
                        <select data-selectradio="true"  class="form-control" name="audiostoreMusicaBean.tipoInterprete" data-rule-required="true" >
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

                <div class="col-md-3">
                    <div class="form-group">
                        <label></label>
                        <input type="text" name="audiostoreMusicaBean.afinidade1" class="form-control" placeholder="primário"  
                                value="${audiostoreMusicaBean.afinidade1}">
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="form-group">
                        <label></label>
                        <input type="text" name="audiostoreMusicaBean.afinidade2" class="form-control" placeholder="secundário"  
                                value="${audiostoreMusicaBean.afinidade2}">
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="form-group">
                        <label></label>
                        <input type="text" name="audiostoreMusicaBean.afinidade3" class="form-control" placeholder="terciário"  
                                value="${audiostoreMusicaBean.afinidade3}">
                    </div>
                </div>
                <div class="col-md-3">
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
                        <input type="text" name="audiostoreMusicaBean.tempoTotal" class="form-control" placeholder="Nome"  
                               data-clock="true" data-clock-format="HH:mm" value="${audiostoreMusicaBean.tempoTotal}">
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="form-group">
                        <label>Quantidade </label>

                        <input type="text" name="audiostoreMusicaBean.qtdePlayer" class="form-control span2" placeholder="Quantidade máxima de execuções no dia)"  
                               data-rule-required="true" 
                               data-mask="?9999999999" value="${audiostoreMusicaBean.qtdePlayer}">
                    </div>
                </div>

                <div class="col-md-2">
                    <div class="form-group">
                        <label>Ultima  execução</label>
                        <input type="text" name="audiostoreMusicaBean.ultimaExecucao" class="form-control datepicker" placeholder="Nome"  
                               data-mask="99/99/9999"
                               data-rule-maxlength="30" value="${audiostoreMusicaBean.ultimaExecucao}">
                    </div>
                </div>

                <div class="col-md-2">
                    <div class="form-group">
                        <label>Frame inicial</label>
                        <input type="text" name="audiostoreMusicaBean.nome" class="form-control" placeholder="Nome"  
                               data-rule-required="true" 
                               data-mask="?9999999999" value="${audiostoreMusicaBean.nome}">
                    </div>
                </div>

                <div class="col-md-2">
                    <div class="form-group">
                        <label>Frame final</label>
                        <input type="text" name="audiostoreMusicaBean.nome" class="form-control" placeholder="Nome"  
                               data-rule-required="true" 
                               data-mask="?9999999999" value="${audiostoreMusicaBean.nome}">
                    </div>
                </div>
            </div>

            <br />
            <hr />

            <div class="row">
                <div class="col-md-2">
                    <div class="form-group">
                        <label>Data de vencimento</label>
                        <input type="text" name="audiostoreMusicaBean.dataVencimento" class="form-control datepicker" placeholder="Nome"  
                               data-rule-required="true" 
                               data-mask="99/99/9999" value="${audiostoreMusicaBean.dataVencimento}">
                    </div>
                </div>
                <div class="col-md-2">
                    <div class="form-group">
                        <label>Data de inclusao</label>
                        <input type="text" name="audiostoreMusicaBean.data" class="form-control datepicker" placeholder="Nome"  
                               data-rule-required="true" 
                               data-mask="99/99/9999"value="${audiostoreMusicaBean.data}">
                    </div>
                </div>

                <div class="col-md-1">
                    <div class="form-group">
                        <label>Tipo</label> 
                        <br />
                        <select data-selectradio="true"  class="form-control" name="audiostoreMusicaBean.cut" data-rule-required="true" >
                            <option value="${true}">Cut</option> 
                            <option value="${false}">FadeOut</option> 
                        </select>
                    </div>
                </div>

                <div class="col-md-2">
                    <div class="form-group">
                        <label>Velocidade</label> 
                        <br />
                        <select data-selectradio="true"  class="form-control" name="audiostoreMusicaBean.velocidade" data-rule-required="true" >
                            <option value="1">Lento</option> 
                            <option value="2">Normal</option> 
                            <option value="3">Rápido</option> 
                        </select>
                    </div>
                </div>

                <div class="col-md-2">
                    <div class="form-group">
                        <label>Ano de gravação</label> 
                        <br />
                        <select data-selectradio="true"  class="form-control" name="audiostoreMusicaBean.anoGravacao" data-rule-required="true" >
                            <c:forEach begin="1980" end="2014" varStatus="vs">
                                <option value="${vs.index}">${vs.index}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>

            <button type="submit" class="btn btn-default">
                <i class="fa fa-save"></i> Salvar
            </button>
        </form>

    </jsp:body>
</instore:template>